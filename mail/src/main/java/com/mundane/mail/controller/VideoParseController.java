package com.mundane.mail.controller;

import cn.hutool.json.JSONObject;
import com.mundane.mail.pojo.Response;
import com.mundane.mail.service.VideoParseService;
import com.mundane.mail.vo.DouyinData;
import com.mundane.mail.vo.VideoParseReqVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/mail/videoparse")
public class VideoParseController {

    @Autowired
    private VideoParseService videoParseService;

    @PostMapping("/douyinParse")
    public Response<DouyinData> douyinParse(@RequestBody VideoParseReqVo vo) {
        try {
            DouyinData douyinData = videoParseService.parseDouyin(vo.getText());
            return Response.success(douyinData);
        } catch (Exception e) {
            log.error("解析视频发生错误", e);
            return Response.fail(e.getMessage());
        }
    }

    @PostMapping("/parse")
    public Response<JSONObject> parse(@RequestBody VideoParseReqVo vo) {
        try {
            JSONObject data = videoParseService.parse(vo.getText());
            return Response.success(data);
        } catch (Exception e) {
            log.error("解析视频发生错误", e);
            return Response.fail(e.getMessage());
        }
    }
}
