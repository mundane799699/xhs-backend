package com.mundane.mail.controller;

import com.mundane.mail.entity.RedBookItemEntity;
import com.mundane.mail.pojo.Response;
import com.mundane.mail.service.RedBookItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mail/redbook/item")
public class RedBookItemController {

    @Autowired
    private RedBookItemService redBookItemService;

    @GetMapping("/list")
    public Response<List<RedBookItemEntity>> queryList() {
        return Response.success(redBookItemService.queryList());
    }
}
