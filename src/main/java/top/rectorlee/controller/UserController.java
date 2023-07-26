package top.rectorlee.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.rectorlee.service.UserService;

import javax.servlet.http.HttpSession;

/**
 * @description:
 * @author: Lee
 * @date: 2023-07-25 19:16:03
 * @version: 1.0
 */
@Slf4j
@Controller
@RequestMapping("/shiro")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login1")
    public String login(String userName, String password, boolean rememberMe, HttpSession session) {
        return userService.login(userName, password, rememberMe, session);
    }

    @RequiresRoles("admin")
    @GetMapping("/roles")
    @ResponseBody
    public String roles() {
        log.info("角色校验成功");
        return "角色校验成功";
    }

    @RequiresPermissions("user:add")
    @GetMapping("/permission")
    @ResponseBody
    public String addUser() {
        log.info("权限校验成功");

        return "权限校验成功";
    }
}
