package cn.lyb.zc.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author lyb
 * @since 2019/8/8 15:32
 */
@ControllerAdvice
public class ZcExceptionResolever {
    @ExceptionHandler(value = Exception.class)
    public ModelAndView catchException(Exception exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.setViewName("system-error");
        return mav;
    }

}
