package com.mundane.mail.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.mundane.mail.vo.LoginRequest;
import com.mundane.mail.dto.UserInfo;
import com.mundane.mail.vo.RegisterRequest;
import com.mundane.mail.pojo.Response;
import com.mundane.mail.service.AuthService;
import com.mundane.mail.vo.UserInfoRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/mail/user")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Response<UserInfo> login(@RequestBody LoginRequest vo) {
        UserInfo userInfo = authService.login(vo);
        return Response.success(userInfo);
    }

    @GetMapping("/userInfo")
    public Response<UserInfoRespVo> userInfo() {
        boolean isLogin = StpUtil.isLogin();
        UserInfoRespVo vo = new UserInfoRespVo();
        vo.setHasLogin(isLogin);
        if (isLogin) {
            UserInfo userInfo = authService.getUserInfo();
            vo.setUserInfo(userInfo);
        }
        return Response.success(vo);
    }

    @PostMapping("/register")
    public Response register(@RequestBody RegisterRequest vo) {
        authService.register(vo);
        return Response.success();
    }

    @PostMapping("/logout")
    public Response logout() {
        authService.logout();
        return Response.success();
    }
}
