package top.rectorlee.constant;

/**
 * @description: 系统常量类
 * @author: Lee
 * @date: 2023-07-25 20:34:48
 * @version: 1.0
 */
public class SystemConstant {
    // 加盐
    public static final String SALT = "salt";

    // 加密方式
    public static final String ENCRYPTION_TYPE = "MD5";

    // 加密次数
    public static final Integer ENCRYPTION_NUMBER = 3;

    // rememberMe
    public static final String REMEMBERME = "rememberMe";

    // cookie路径
    public static final String COOKIE_PATH = "/";

    // cookie有效时间
    public static final Integer COOKIE_MAX_AGE = 30 * 24 * 60 * 60;

    public static final String CIPHER_KEY = "1234567890987654";

    // ehCache配置文件路径
    public static final String EHCACHE_PATH = "classpath:ehcache/ehcache-shiro.xml";

    // 跳转到登录页面路径
    public static final String LOGIN_PAGE_PATH = "/shiro/login";

    // 登录接口路径
    public static final String LOGIN_PATH = "/shiro/login1";

    // 退出登录路径
    public static final String LOGOUT_PATH = "/logout";

    // 登录认证拦截路径
    public static final String INTERCEPT_PATH = "/**";

    // 认证类型
    public static final String PERMISSION_TYPE_1 = "anon";

    public static final String PERMISSION_TYPE_2 = "logout";

    public static final String PERMISSION_TYPE_3 = "authc";

    public static final String PERMISSION_TYPE_4 = "user";
}
