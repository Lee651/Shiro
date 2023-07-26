package top.rectorlee.service;

import top.rectorlee.entity.User;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {
    User getUserByUserName(String userName);

    String login(String userName, String password, boolean rememberMe, HttpSession session);

    List<String> getRoleNameByUserName(String userName);

    List<String> getPermissionByRoleNames(List<String> roleNames);
}
