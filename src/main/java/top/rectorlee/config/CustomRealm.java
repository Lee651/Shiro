package top.rectorlee.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.rectorlee.constant.SystemConstant;
import top.rectorlee.entity.User;
import top.rectorlee.service.UserService;

import java.util.List;
import java.util.Objects;

/**
 * @description: 自定义realm类
 * @author: Lee
 * @date: 2023-07-25 19:38:05
 * @version: 1.0
 */
@Slf4j
@Component
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    protected UserService userService;

    /**
     * 自定义授权方法
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("进入自定义授权方法");
        String userName = principalCollection.getPrimaryPrincipal().toString();
        // 根据用户名查询角色信息
        List<String> roles = userService.getRoleNameByUserName(userName);
        log.info("用户角色信息为: {}", roles.toString());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roles);
        if (CollectionUtils.isNotEmpty(roles)) {
            // 根据角色名称查询权限信息
            List<String> permissions = userService.getPermissionByRoleNames(roles);
            log.info("用户权限信息为: {}", permissions.toString());

            info.addStringPermissions(permissions);
        }

        return info;
    }

    /**
     * 自定义登录认证方法
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("进入自定义登录认证方法");
        // 获取用户身份信息
        String userName = authenticationToken.getPrincipal().toString();

        User user = userService.getUserByUserName(userName);
        if (Objects.nonNull(user)) {
            AuthenticationInfo info = new SimpleAuthenticationInfo(
                    authenticationToken.getPrincipal(),
                    user.getPassword(),
                    ByteSource.Util.bytes(SystemConstant.SALT),
                    userName
            );

            log.info("认证成功");
            return info;
        }

        log.info("认证失败");
        return null;
    }
}
