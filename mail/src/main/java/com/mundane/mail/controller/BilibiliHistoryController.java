package com.mundane.mail.controller;

import com.github.pagehelper.PageInfo;
import com.mundane.mail.pojo.RedBookCollectRequest;
import com.mundane.mail.pojo.Response;
import com.mundane.mail.service.BilibiliHistoryService;
import com.mundane.mail.vo.BilibiliHistoryRequestVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/mail/bilibili/history")
public class BilibiliHistoryController {

    @Autowired
    private BilibiliHistoryService bilibiliHistoryService;

    @PostMapping("/save")
    public Response collectReceive(@RequestBody BilibiliHistoryRequestVo request) {
        try {
            bilibiliHistoryService.save(request);
            return Response.success();
        } catch (Exception e) {
            log.error("保存历史发生错误", e);
            return Response.fail(e.getMessage());
        }
    }

    @GetMapping("/query")
    public Response<PageInfo> query(@RequestParam("userId") String userId,
                                    @RequestParam(value = "title", required = false) String title,
                                    @RequestParam(value = "authorName", required = false) String authorName,
                                    @RequestParam("pageNum") Integer pageNum,
                                    @RequestParam("pageSize") Integer pageSize) {
        try {
            return Response.success(bilibiliHistoryService.queryByUserId(userId, title, authorName, pageNum, pageSize));
        } catch (Exception e) {
            log.error("queryByUserId发生错误", e);
            return Response.fail(e.getMessage());
        }
    }
}
