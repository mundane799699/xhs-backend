package com.mundane.mail.controller;

import com.mundane.mail.pojo.*;
import com.mundane.mail.service.RedBookNoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/mail/redbook/note")
public class RedBookNoteController {

    @Autowired
    private RedBookNoteService redBookNoteService;

    @PostMapping("/save")
    public Response saveNote(@RequestBody RedBookNoteRequest request) {

        try {
            redBookNoteService.saveNote(request);
            return Response.success();
        } catch (Exception e) {
            log.error("保存笔记发生错误", e);
            return Response.fail(e.getMessage());
        }
    }

    @PostMapping("/exportNoteMd")
    public void downloadNoteMd(@RequestBody RedBookNoteExportMdVo vo, HttpServletResponse response) {
        try {
            redBookNoteService.downloadNoteMd(vo, response);
        } catch (Exception e) {
            log.error("下载md文件发生错误", e);
        }
    }

    @PostMapping("/getMediaInfo")
    public Response getMediaInfo(@RequestBody RedBookNoteExportMdVo vo){
        try {
            RedBookNoteMediaInfo mediaInfo = redBookNoteService.getMediaInfo(vo);
            return Response.success(mediaInfo);
        } catch (Exception e) {
            log.error("获取图片和视频信息发生错误", e);
            return Response.fail(e.getMessage());
        }
    }
}
