package cn.lyb.zc.interceptor;

import cn.lyb.zc.common.Constant;
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
            session.setAttribute(Constant.ATTR_NAME_MESSAGE, Constant.MESSAGE_ACCESS_DENIED);
            request.getRequestDispatcher("/index-page.jsp").forward(request, response);
            return false;
        }
        return true;
    }
}
