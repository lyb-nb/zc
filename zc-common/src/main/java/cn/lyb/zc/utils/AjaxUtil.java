package cn.lyb.zc.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lyb
 * @since 2019/8/26 19:21
 */
public class AjaxUtil {
    /**
     * 用于判断一个请求是否是异步请求
     *
     * @param request
     * @return
     */
    public static boolean checkAsyncRequest(HttpServletRequest request) {

        // 1.获取相应请求消息头
        String accept = request.getHeader("Accept");
        String xRequested = request.getHeader("X-Requested-With");

        // 2.判断请求消息头数据中是否包含目标特征
        if ((StringUtils.isNotEmpty(accept) && accept.contains("application/json")) ||
                (StringUtils.isNotEmpty(xRequested) && xRequested.contains("XMLHttpRequest"))) {
            return true;
        }

        return false;
    }

}
