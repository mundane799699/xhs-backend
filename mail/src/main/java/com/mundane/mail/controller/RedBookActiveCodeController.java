package com.mundane.mail.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.mundane.mail.pojo.Response;
import com.mundane.mail.service.RedBookActiveCodeService;
import com.mundane.mail.vo.ClientStatusVo;
import com.mundane.mail.vo.QueryOrderStatusRespVo;
import com.mundane.mail.vo.RedBookActiveCodeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/mail/redbook/activeCode")
public class RedBookActiveCodeController {

    @Autowired
    private RedBookActiveCodeService activeCodeService;


    @GetMapping("/generate")
    public Response generate() {
        activeCodeService.generate();
        return Response.success();
    }

    @PostMapping("/active")
    public Response<QueryOrderStatusRespVo> active(@RequestBody RedBookActiveCodeVo vo) {
        StpUtil.checkLogin();
        QueryOrderStatusRespVo result = activeCodeService.active(vo.getActiveCode());
        return Response.success(result);
    }
}
