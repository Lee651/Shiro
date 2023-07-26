package top.rectorlee.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 权限异常类
 * @author: Lee
 * @date: 2023-07-26 1:41:38
 * @version: 1.0
 */
@Slf4j
@ControllerAdvice
public class PermissionException {
    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public String authorizationException(Exception e) {
        log.info("认证失败");
        return "认证失败";
    }

    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public String unauthorizedException(Exception e) {
        log.info("无权限");
        return "无权限";
    }
}
