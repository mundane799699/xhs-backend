package com.mundane.mail;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mundane.mail.constant.VideoHubConstants;
import com.mundane.mail.dto.RedBookCollectDto;
import com.mundane.mail.dto.RedBookImageDto;
import com.mundane.mail.entity.RedBookNoteEntity;
import com.mundane.mail.service.RedBookCollectService;
import com.mundane.mail.service.RedBookNoteService;
import com.mundane.mail.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@Slf4j
public class ParseNoteDetailTests {
    @Autowired
    private RedBookNoteService redBookNoteService;

    @Test
    public void testParse() throws Exception {
        RedBookCollectDto dto = new RedBookCollectDto();
//        dto.setNoteId("66118a92000000001b010d2b");
        // https://www.xiaohongshu.com/explore/6571d0760000000009020f0a
        // https://www.xiaohongshu.com/explore/6617e959000000000401a35e
        dto.setNoteId("6571d0760000000009020f0a");
        redBookNoteService.getDetail(dto);
        Thread.sleep(5000);
        redBookNoteService.getDetail(dto);
        Thread.sleep(5000);
        redBookNoteService.getDetail(dto);
    }

    @Test
    public void testCookie() throws Exception {
        String url = "https://www.xiaohongshu.com/explore/6571d0760000000009020f0a";
        Document doc = Jsoup
                .connect(url)
                .userAgent(VideoHubConstants.USER_AGENT)
                .header("Cookie", VideoHubConstants.cookies[0])
                .get();

        String match = "window.__INITIAL_STATE__";
        for (Element script : doc.select("script")) {
            String scriptData = script.data();
            if (!scriptData.contains(match)) {
                continue;
            }
            log.info("开始解析笔记：{}", url);
            String jsonString = scriptData.substring(scriptData.indexOf("=") + 1);
            JSONObject jsonObject = JSONUtil.parseObj(jsonString);
            JSONObject object = jsonObject.getJSONObject("note");
            String firstNoteId = object.getStr("firstNoteId");

            if (StringUtils.isEmpty(firstNoteId)) {
                log.info("笔记已失效：{}", url);
                return;
            }
            log.info("笔记有效：{}", url);
        }
    }

    @Test
    public void testConvert() {
        String str = "[{\"fileName\":\"6295c5e2000000002103f4e9_1.jpg\",\"localPath\":\"D:\\\\project\\\\IdeaProjects\\\\myprojects\\\\images\\\\6295c5e2000000002103f4e9_1.jpg\"}]";

        List<RedBookImageDto> picPathList = JsonUtils.toList(str, RedBookImageDto.class);
        log.info("picPathList = {}", picPathList);
    }
}
