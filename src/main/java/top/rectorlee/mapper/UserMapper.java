package top.rectorlee.mapper;

import top.rectorlee.entity.User;

import java.util.List;

public interface UserMapper {
    User getUserByUserName(String userName);

    List<String> getRoleNameByUserName(String userName);

    List<String> getPermissionByRoleNames(List<String> roleNames);
}
