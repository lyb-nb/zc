package cn.lyb.zc.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lyb
 * @since 2019/8/8 14:53
 */
public class Constant {
    public static final String ATTR_NAME_MESSAGE = "MESSAGE";
    public static final String ATTR_NAME_LOGIN_ADMIN = "LOGIN-ADMIN";
    public static final String ATTR_NAME_PAGE_INFO = "PAGE-INFO";

    public static final String MESSAGE_LOGIN_FAILED = "登录账号或密码不正确！请核对后再登录！";
    public static final String MESSAGE_CODE_INVALID = "明文不是有效字符串，请核对后再操作！";

    public static final String MESSAGE_ACCESS_DENIED = "拒绝访问,请登录后再次尝试";
    public static final String DUPLICATE_LOGINACCT = "账户已经存在,请重新设置";

    public static final Map<String, String> EXCEPTION_MESSAGE_MAP = new HashMap<>();

    static {
        EXCEPTION_MESSAGE_MAP.put("java.lang.ArithmeticException", "系统在进行数学运算时发生错误");
        EXCEPTION_MESSAGE_MAP.put("java.lang.RuntimeException", "系统在运行时发生错误");
        EXCEPTION_MESSAGE_MAP.put("cn.lyb.zc.exception.LoginException", "登录过程中运行错误");
    }

}
