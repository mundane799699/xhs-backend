package com.mundane.mail.controller;

import com.mundane.mail.pojo.Response;
import com.mundane.mail.service.OrderService;
import com.mundane.mail.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/mail/redbook/order")
public class RedBookOrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/createPayUrl")
    public Response<OrderRespVo> createPayUrl(@RequestBody OrderCreateVo vo) {
        OrderRespVo result = orderService.createPayUrl(vo);
        return Response.success(result);
    }

    @GetMapping("/queryOrderStatus")
    public Response<QueryOrderStatusRespVo> queryOrderStatus(@RequestParam("outTradeNo") String outTradeNo) {
        QueryOrderStatusRespVo result = null;
        try {
            result = orderService.queryOrderStatus(outTradeNo);
        } catch (Exception e) {
            result = new QueryOrderStatusRespVo();
            result.setActive(false);
        }
        return Response.success(result);
    }

    @GetMapping("/queryActiveStatus")
    public Response<ActiveStatusVo> queryActiveStatus(@RequestParam(value = "clientId", required = false) String clientId, @RequestParam(value = "userId", required = false) String userId) {
        return Response.success(orderService.queryActiveStatus(clientId, userId));
    }
}
