package cn.lyb.zc.exception;

import cn.lyb.zc.common.Constant;
import cn.lyb.zc.entity.ResultEntity;
import cn.lyb.zc.utils.AjaxUtil;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lyb
 * @since 2019/8/8 15:32
 */
@ControllerAdvice
public class ZcExceptionResolever {
    @ExceptionHandler(value = Exception.class)
    public ModelAndView catchException(Exception exception, HttpServletRequest request,
                                       HttpServletResponse response) throws IOException {
        boolean checkAsyncRequest = AjaxUtil.checkAsyncRequest(request);
        if (checkAsyncRequest) {
            // 根据异常类型在常量中的映射，使用比较友好的文字显示错误提示消息
            String exceptionClassName = exception.getClass().getName();
            String message = Constant.EXCEPTION_MESSAGE_MAP.get(exceptionClassName);
            if (message == null) {
                message = "系统未知错误";
            }
            ResultEntity resultEntity = ResultEntity.fail(ResultEntity.NO_DATA, message);
            Gson gson = new Gson();
            String json = gson.toJson(resultEntity);
            // 5.将json作为响应数据返回给浏览器
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(json);
            return null;
        }
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.setViewName("system-error");
        return mav;
    }

}
