package cn.lyb.zc.interceptor;

import cn.lyb.zc.common.Constant;
import cn.lyb.zc.entity.ResultEntity;
import cn.lyb.zc.utils.AjaxUtil;
import com.google.gson.Gson;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author lyb
 * @since 2019/8/8 16:05
 */
public class LoginInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        Object admin = session.getAttribute(Constant.ATTR_NAME_LOGIN_ADMIN);
        if (null == admin) {
            // 进一步判断当前请求是否是异步请求
            boolean checkAsyncRequestResult = AjaxUtil.checkAsyncRequest(request);
            if (checkAsyncRequestResult) {
                // 为异步请求的响应创建ResultEntity对象
                ResultEntity<String> resultEntity = ResultEntity.fail(ResultEntity.NO_DATA, Constant.MESSAGE_ACCESS_DENIED);
                // 创建Gson对象
                Gson gson = new Gson();
                // 将ResultEntity对象转换为JSON字符串
                String json = gson.toJson(resultEntity);
                // 设置响应的内容类型
                response.setContentType("application/json;charset=UTF-8");
                // 将JSON字符串作为响应数据返回
                response.getWriter().write(json);
                // 表示不能放行，后续操作不执行
                return false;
            }
            session.setAttribute(Constant.ATTR_NAME_MESSAGE, Constant.MESSAGE_ACCESS_DENIED);
            request.getRequestDispatcher("/index-page.jsp").forward(request, response);
            return false;
        }
        return true;
    }
}
