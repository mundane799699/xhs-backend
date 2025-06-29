package com.mundane.mail.controller;

import com.github.pagehelper.PageInfo;
import com.mundane.mail.entity.RedBookCollectParseTaskEntity;
import com.mundane.mail.entity.RedBookLikeParseTaskEntity;
import com.mundane.mail.pojo.RedBookCollectRequest;
import com.mundane.mail.pojo.RedBookLikeRequest;
import com.mundane.mail.pojo.Response;
import com.mundane.mail.service.RedBookLikeService;
import com.mundane.mail.vo.RedBookCollectExportExcelVo;
import com.mundane.mail.vo.RedBookCollectExportHtmlVo;
import com.mundane.mail.vo.RedBookDeleteRequestVo;
import com.mundane.mail.vo.RedBookParseDetailReuestVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/mail/redbook/like")
public class RedBookLikeController {

    @Autowired
    private RedBookLikeService likeService;

    @PostMapping("/save")
    public Response collectReceive(@RequestBody RedBookLikeRequest request) {

        try {
            likeService.save(request);
            return Response.success();
        } catch (Exception e) {
            log.error("保存点赞发生错误", e);
            return Response.fail(e.getMessage());
        }
    }

    @GetMapping("/query")
    public Response<PageInfo> query(@RequestParam("userId") String userId,
                                    @RequestParam(value = "displayTitle", required = false) String displayTitle,
                                    @RequestParam(value = "ownerNickname", required = false) String ownerNickname,
                                    @RequestParam("pageNum") Integer pageNum,
                                    @RequestParam("pageSize") Integer pageSize) {
        try {
            return Response.success(likeService.queryByUserId(userId, displayTitle, ownerNickname, pageNum, pageSize));
        } catch (Exception e) {
            log.error("queryByUserId发生错误", e);
            return Response.fail(e.getMessage());
        }
    }


    @PostMapping(value = "/exportexcel", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadExcel(@RequestBody RedBookCollectExportExcelVo vo, HttpServletResponse response) {
        try {
            likeService.downloadExcel(vo, response);
        } catch (Exception e) {
            log.error("导出收藏至excel发生错误", e);
        }
    }


    @PostMapping("/exportHtml")
    public void downloadHtml(@RequestBody RedBookCollectExportHtmlVo vo, HttpServletResponse response) {
        try {
            likeService.downloadHtml(vo, response);
        } catch (Exception e) {
            log.error("下载html文件发生错误", e);
        }
    }

    @PostMapping("/deleteByUserId")
    public Response delete(@RequestBody RedBookDeleteRequestVo vo) {
        try {
            likeService.deleteByUserId(vo.getUserId());
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
        RedBookLikeParseTaskEntity entity = likeService.queryTask(userId);
        if (entity != null && entity.getStatus() == 1) {
            log.error("任务正在进行中，请稍后再试");
            return Response.fail("任务正在进行中，请稍后再试");
        }
        likeService.initParseTask(userId);
        likeService.parseDetail(vo);
        return Response.success();
    }

    @GetMapping("/queryParseDetail")
    public Response queryParseDetail(@RequestParam("userId") String userId) {
        try {
            return Response.success(likeService.queryParseDetail(userId));
        } catch (Exception e) {
            log.error("queryParseDetail发生错误", e);
            return Response.fail(e.getMessage());
        }
    }


}
