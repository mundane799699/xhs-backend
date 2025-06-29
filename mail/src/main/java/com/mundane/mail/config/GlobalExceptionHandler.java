package com.mundane.mail.config;

import cn.dev33.satoken.exception.NotLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.mundane.mail.pojo.Response;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Response<String> handleException(Exception e) {
        log.error("捕获错误", e);
        return Response.fail(500, "服务器内部错误: " + e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Response<String> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("捕获错误", e);
        return Response.fail(400, "请求参数错误: " + e.getMessage());
    }

    @ExceptionHandler(NotLoginException.class)
    public Response<String> handleNotLoginException(NotLoginException e) {
        log.error("捕获错误", e);
        return Response.fail(401, "未登录: " + e.getMessage());
    }
}
