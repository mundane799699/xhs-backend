package com.mundane.mail.controller;

import com.github.pagehelper.PageInfo;
import com.mundane.mail.entity.RedBookCollectParseTaskEntity;
import com.mundane.mail.pojo.RedBookCollectRequest;
import com.mundane.mail.pojo.Response;
import com.mundane.mail.service.RedBookCollectService;
import com.mundane.mail.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/mail/redbook/collect")
public class RedBookCollectController {

    @Autowired
    private RedBookCollectService collectService;

    @PostMapping("/save")
    public Response collectReceive(@RequestBody RedBookCollectRequest request) {

        try {
            collectService.save(request);
            return Response.success();
        } catch (Exception e) {
            log.error("保存收藏发生错误", e);
            return Response.fail(e.getMessage());
        }
    }

    @PostMapping(value = "/exportexcel", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadExcel(@RequestBody RedBookCollectExportExcelVo vo, HttpServletResponse response) {
        try {
            collectService.downloadExcel(vo, response);
        } catch (Exception e) {
            log.error("导出收藏至excel发生错误", e);
        }
    }

    @PostMapping("/exportHtml")
    public void downloadHtml(@RequestBody RedBookCollectExportHtmlVo vo, HttpServletResponse response) {
        try {
            collectService.downloadHtml(vo, response);
        } catch (Exception e) {
            log.error("下载html文件发生错误", e);
        }
    }


    @GetMapping("/query")
    public Response<PageInfo> query(@RequestParam("userId") String userId,
                                    @RequestParam(value = "displayTitle", required = false) String displayTitle,
                                    @RequestParam(value = "ownerNickname", required = false) String ownerNickname,
                                    @RequestParam("pageNum") Integer pageNum,
                                    @RequestParam("pageSize") Integer pageSize) {
        try {
            return Response.success(collectService.queryByUserId(userId, displayTitle, ownerNickname, pageNum, pageSize));
        } catch (Exception e) {
            log.error("queryByUserId发生错误", e);
            return Response.fail(e.getMessage());
        }
    }

    @PostMapping("/deleteByUserId")
    public Response delete(@RequestBody RedBookDeleteRequestVo vo) {
        try {
            collectService.deleteByUserId(vo.getUserId());
            return Response.success();
        } catch (Exception e) {
            log.error("deleteByUserId发生错误", e);
            return Response.fail(e.getMessage());
        }
    }

    @PostMapping("/parseDetail")
    public Response parseDetail(@RequestBody RedBookParseDetailReuestVo vo) {
        String userId = vo.getUserId();
        if (StringUtils.isEmpty(userId)) {
            log.error("userId不能为空");
            return Response.fail("userId不能为空");
        }
        RedBookCollectParseTaskEntity entity = collectService.queryTask(userId);
        if (entity != null && entity.getStatus() == 1) {
            log.error("任务正在进行中，请稍后再试");
            return Response.fail("任务正在进行中，请稍后再试");
        }
        collectService.initParseTask(userId);
        collectService.parseDetail(vo);
        return Response.success();
    }

    @PostMapping("/forceParseDetail")
    public Response forceParseDetail(@RequestBody RedBookParseDetailReuestVo vo) {
        String userId = vo.getUserId();
        if (StringUtils.isEmpty(userId)) {
            log.error("userId不能为空");
            return Response.fail("userId不能为空");
        }
        RedBookCollectParseTaskEntity entity = collectService.queryTask(userId);
        if (entity != null && entity.getStatus() == 1) {
            log.error("任务正在进行中，请稍后再试");
            return Response.fail("任务正在进行中，请稍后再试");
        }
        collectService.initParseTask(userId);
        collectService.forceParseDetail(vo);
        return Response.success();
    }

    @GetMapping("/queryParseDetail")
    public Response queryParseDetail(@RequestParam("userId") String userId) {
        try {
            return Response.success(collectService.queryParseDetail(userId));
        } catch (Exception e) {
            log.error("queryParseDetail发生错误", e);
            return Response.fail(e.getMessage());
        }
    }

    @PostMapping(value = "/downloadzip", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadZip(@RequestBody RedBookCollectExportZipVo  vo, HttpServletResponse response) {
        try {
            collectService.downloadZip(vo, response);
        } catch (Exception e) {
            log.error("导出收藏zip发生错误", e);
        }
    }

    @PostMapping(value = "/downloadzip2", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadZip2(@RequestBody RedBookCollectExportZipVo  vo, HttpServletResponse response) {
        try {
            collectService.downloadZip2(vo, response);
        } catch (Exception e) {
            log.error("导出收藏zip发生错误", e);
        }
    }


}
