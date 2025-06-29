package com.mundane.mail.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.*;
import com.mundane.mail.config.JwtProperties;
import com.mundane.mail.config.VideoParseConfig;
import com.mundane.mail.utils.RegexUtil;
import com.mundane.mail.vo.DouyinData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@EnableConfigurationProperties(VideoParseConfig.class)
public class VideoParseService {

    @Autowired
    private VideoParseConfig prop;


    public DouyinData parseDouyin(String text) {
        String url = RegexUtil.parseUrl(text);
        log.info("url = {}", url);
        String location = getHeader(url, "Location");
        String id = RegexUtil.parseByRegex(location, "\\d+");
        log.info("id = {}", id);
//        String xbogusUrl = "https://tiktok.iculture.cc/X-Bogus";
        String xbogusUrl = "http://localhost:8787";

        JSONObject data = new JSONObject();
        data.set("url", "https://www.douyin.com/aweme/v1/web/aweme/detail/?aweme_id=" + id + "&aid=1128&version_name=23.5.0&device_platform=android&os_version=2333");
        data.set("userAgent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");

        HttpResponse response = HttpRequest.post(xbogusUrl)
                .header("Content-Type", "application/json")
                .body(data.toString())
                .execute();

        JSONObject resJson = JSONUtil.parseObj(response.body());
        url = resJson.getJSONObject("data").getStr("url");
        String msToken = getMsToken();

        HttpResponse request = HttpRequest.get(url)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36")
                .header("Referer", "https://www.douyin.com/")
                .header("Cookie", "msToken=" + msToken + ";odin_tt=324fb4ea4a89c0c05827e18a1ed9cf9bf8a17f7705fcc793fec935b637867e2a5a9b8168c885554d029919117a18ba69; ttwid=1%7CWBuxH_bhbuTENNtACXoesI5QHV2Dt9-vkMGVHSRRbgY%7C1677118712%7C1d87ba1ea2cdf05d80204aea2e1036451dae638e7765b8a4d59d87fa05dd39ff; bd_ticket_guard_client_data=eyJiZC10aWNrZXQtZ3VhcmQtdmVyc2lvbiI6MiwiYmQtdGlja2V0LWd1YXJkLWNsaWVudC1jc3IiOiItLS0tLUJFR0lOIENFUlRJRklDQVRFIFJFUVVFU1QtLS0tLVxyXG5NSUlCRFRDQnRRSUJBREFuTVFzd0NRWURWUVFHRXdKRFRqRVlNQllHQTFVRUF3d1BZbVJmZEdsamEyVjBYMmQxXHJcbllYSmtNRmt3RXdZSEtvWkl6ajBDQVFZSUtvWkl6ajBEQVFjRFFnQUVKUDZzbjNLRlFBNUROSEcyK2F4bXAwNG5cclxud1hBSTZDU1IyZW1sVUE5QTZ4aGQzbVlPUlI4NVRLZ2tXd1FJSmp3Nyszdnc0Z2NNRG5iOTRoS3MvSjFJc3FBc1xyXG5NQ29HQ1NxR1NJYjNEUUVKRGpFZE1Cc3dHUVlEVlIwUkJCSXdFSUlPZDNkM0xtUnZkWGxwYmk1amIyMHdDZ1lJXHJcbktvWkl6ajBFQXdJRFJ3QXdSQUlnVmJkWTI0c0RYS0c0S2h3WlBmOHpxVDRBU0ROamNUb2FFRi9MQnd2QS8xSUNcclxuSURiVmZCUk1PQVB5cWJkcytld1QwSDZqdDg1czZZTVNVZEo5Z2dmOWlmeTBcclxuLS0tLS1FTkQgQ0VSVElGSUNBVEUgUkVRVUVTVC0tLS0tXHJcbiJ9")
                .execute();

        String result = request.body();
        JSONObject finalResult = JSONUtil.parseObj(result);
        JSONObject awemeDetail = finalResult.getJSONObject("aweme_detail");
        JSONObject video = awemeDetail.getJSONObject("video");
        DouyinData douyinData = new DouyinData();

        if (isNull(awemeDetail, "images")) {
            douyinData.setType("video");
            String videoUrl = video.getJSONObject("play_addr").getJSONArray("url_list").get(0).toString();
            douyinData.setVideoUrl(videoUrl);
        } else {
            douyinData.setType("image");
            JSONArray images = awemeDetail.getJSONArray("images");
            List<String> imageList = new ArrayList<>();
            for (int i = 0; i < images.size(); i++) {
                JSONObject image = (JSONObject) images.get(i);
                JSONArray urlList = image.getJSONArray("url_list");
                imageList.add(urlList.get(urlList.size() - 1).toString());
            }
            douyinData.setImageList(imageList);
        }
        JSONArray originCover = video.getJSONObject("origin_cover").getJSONArray("url_list");
        String cover = originCover.get(originCover.size() - 1).toString();
        String title = awemeDetail.getStr("desc");
        douyinData.setCover(cover);
        douyinData.setTitle(title);
        return douyinData;
    }

    private boolean isNull(JSONObject obj, String key) {
        return JSONNull.NULL.equals(obj.get(key));
    }

    private String getMsToken() {
        String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 107; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        String msToken = sb.toString();
        return msToken;
    }

    private String getHeader(String url, String key) {
        HttpResponse response = HttpRequest.get(url)
                .execute();

        String value = response.header(key);
        return value;
    }


    public JSONObject parse(String text) {
        String url = RegexUtil.parseUrl(text);
        log.info("url = {}", url);
        HttpRequest request = HttpRequest.get("https://qsy.ludeqi.com/api/dsp/" + prop.getClientSecretKey() + "/" + prop.getClientId() + "/?url=" + url);

        HttpResponse response = request.execute();

        JSONObject resJson = JSONUtil.parseObj(response.body());
        String code = resJson.getStr("code");
        String msg = resJson.getStr("msg");
        if (!"200".equals(code)) {
            throw new RuntimeException("解析失败：" + msg);
        }
        JSONObject data = resJson.getJSONObject("data");
        return data;
    }
}
