package top.rectorlee.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.rectorlee.constant.SystemConstant;

import java.io.InputStream;

/**
 * @description: Shiro配置类
 * @author: Lee
 * @date: 2023-07-25 19:37:45
 * @version: 1.0
 */
@Configuration
public class ShiroConfig {
    @Autowired
    private CustomRealm customRealm;

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 设置加密方式
        matcher.setHashAlgorithmName(SystemConstant.ENCRYPTION_TYPE);
        // 设置加密次数
        matcher.setHashIterations(SystemConstant.ENCRYPTION_NUMBER);
        customRealm.setCredentialsMatcher(matcher);
        defaultWebSecurityManager.setRealm(customRealm);
        // 设置是否记住我
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
        // 设置ehcache缓存
        defaultWebSecurityManager.setCacheManager(getEhCaCheManager());

        return defaultWebSecurityManager;
    }

    private CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        SimpleCookie cookie = new SimpleCookie(SystemConstant.REMEMBERME);
        cookie.setPath(SystemConstant.COOKIE_PATH);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(SystemConstant.COOKIE_MAX_AGE);

        cookieRememberMeManager.setCookie(cookie);
        cookieRememberMeManager.setCipherKey(SystemConstant.CIPHER_KEY.getBytes());

        return cookieRememberMeManager;
    }

    private EhCacheManager getEhCaCheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        InputStream inputStream = null;

        try {
            inputStream = ResourceUtils.getInputStreamForPath(SystemConstant.EHCACHE_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }

        CacheManager cacheManager = new CacheManager(inputStream);
        ehCacheManager.setCacheManager(cacheManager);

        return ehCacheManager;

    }

    // 配置Shiro内置过滤器的拦截范围
    @Bean
    public DefaultShiroFilterChainDefinition defaultShiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        // 设置不认证可以访问的资源
        definition.addPathDefinition(SystemConstant.LOGIN_PATH, SystemConstant.PERMISSION_TYPE_1);
        definition.addPathDefinition(SystemConstant.LOGIN_PAGE_PATH, SystemConstant.PERMISSION_TYPE_1);
        // 退出登录
        definition.addPathDefinition(SystemConstant.LOGOUT_PATH, SystemConstant.PERMISSION_TYPE_2);
        // 设置需要进行登录认证的拦截范围
        definition.addPathDefinition(SystemConstant.INTERCEPT_PATH, SystemConstant.PERMISSION_TYPE_3);
        // 添加存在用户的过滤器(rememberMe)
        definition.addPathDefinition(SystemConstant.INTERCEPT_PATH, SystemConstant.PERMISSION_TYPE_4);

        return definition;
    }

    // 前端解析Shiro:标签用
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }
}
