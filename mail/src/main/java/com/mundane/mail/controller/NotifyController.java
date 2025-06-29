package com.mundane.mail.controller;

import com.mundane.mail.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/mail")
public class NotifyController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/wxpay/notify")
    public String notify(@RequestParam Map<String, String> result) {
        orderService.handleNotify(result);

        log.info("[支付回调] 接收微信支付回调, 结果:{}", result);
        return "SUCCESS";
    }
}
