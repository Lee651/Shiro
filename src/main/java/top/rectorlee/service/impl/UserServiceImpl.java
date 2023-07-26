package top.rectorlee.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.rectorlee.entity.User;
import top.rectorlee.mapper.UserMapper;
import top.rectorlee.service.UserService;

import javax.servlet.http.*;
import java.util.List;

/**
 * @description:
 * @author: Lee
 * @date: 2023-07-25 19:17:13
 * @version: 1.0
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByUserName(String userName) {
        return userMapper.getUserByUserName(userName);
    }

    @Override
    public String login(String userName, String password, boolean rememberMe, HttpSession session) {
        // 获取Subject对象
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password, rememberMe);
        try {
            subject.login(token);
            log.info("登录成功");

            session.setAttribute("user", token.getPrincipal().toString());

            return "info";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            log.error("登录失败");

            return "登录失败";
        }
    }

    @Override
    public List<String> getRoleNameByUserName(String userName) {
        return userMapper.getRoleNameByUserName(userName);
    }

    @Override
    public List<String> getPermissionByRoleNames(List<String> roleNames) {
        return userMapper.getPermissionByRoleNames(roleNames);
    }
}
