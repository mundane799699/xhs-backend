package com.mundane.mail;

import cn.hutool.json.JSONUtil;
import com.mundane.mail.entity.*;
import com.mundane.mail.mapper.RedBookCollectMapper;
import com.mundane.mail.mapper.RedBookNoteMapper;
import com.mundane.mail.pojo.RedBookNoteResult;
import com.mundane.mail.service.RedBookNoteImgService;
import com.mundane.mail.service.RedBookNoteVideoService;
import com.mundane.mail.service.RedBookTagService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@Slf4j
public class RedBookServiceTests {

    @Autowired
    private RedBookCollectMapper redBookCollectMapper;

    @Autowired
    private RedBookNoteMapper redBookNoteMapper;

    @Autowired
    private RedBookTagService redBookTagService;

    @Autowired
    private RedBookNoteImgService redBookNoteImgService;

    @Autowired
    private RedBookNoteVideoService redBookNoteVideoService;

    @Test
    public void testIncert() {
        RedBookCollectEntity entity = new RedBookCollectEntity();
        entity.setUserId("5f827ea10000000001007b5b");
        entity.setNoteId("6434ddd60000000013010e3f");
        entity.setOwnerId("62f447b7000000001f0176cf");
        entity.setOwnerNickname("那个大飞哥");
        entity.setOwnerAvatar("https://sns-avatar-qc.xhscdn.com/avatar/64280ed3609ee59967b89302.jpg?imageView2/2/w/120/format/jpg");
        entity.setCoverUrl("https://sns-img-hw.xhscdn.com/1000g0082at99g0ags0005onk8urnqtmfv35e0po");
        entity.setLiked(true);
        entity.setDisplayTitle("1.8万美金编程培训班有必要吗");
        entity.setType("normal");
        redBookCollectMapper.insert(entity);
    }

    @Test
    public void testSelect() {
        RedBookCollectEntity result = redBookCollectMapper.findByNoteId("6434ddd60000000013010e3f");
        log.info("result = {}", result);
    }

    @Test
    public void testBatchInsertOrUpdate() {
        List<RedBookCollectEntity> list = new ArrayList<>();
        RedBookCollectEntity entity = new RedBookCollectEntity();
        entity.setUserId("5f827ea10000000001007b5b");
        entity.setNoteId("6434ddd60000000013010e3f");
        entity.setOwnerId("62f447b7000000001f0176cf");
        entity.setOwnerNickname("hehehehhe");
        entity.setOwnerAvatar("https://sns-avatar-qc.xhscdn.com/avatar/64280ed3609ee59967b89302.jpg?imageView2/2/w/120/format/jpg");
        entity.setCoverUrl("https://sns-img-hw.xhscdn.com/1000g0082at99g0ags0005onk8urnqtmfv35e0po");
        entity.setLiked(false);
        entity.setLikedCount("35");
        entity.setDisplayTitle("rrrrr");
        entity.setType("video");
        list.add(entity);
        redBookCollectMapper.batchAddOrUpdate(list);
    }

    @Test
    public void testSelectAll() {
        List<RedBookCollectEntity> list = redBookCollectMapper.queryAllByUserId("123");
        log.info("list = {}", list);
    }

    @Test
    public void testSaveNote() {
        RedBookNoteEntity entity = new RedBookNoteEntity();
        entity.setNoteId("2343");
        entity.setAuthorId("abcd");
        entity.setAuthorName("哈哈哈");
        entity.setTitle("更新");
        entity.setType("normal");
        entity.setNoteDesc("更新");

        List<RedBookNoteResult.DataBean.ItemsBean.NoteCardBean.TagListBean> tagList = new ArrayList<>();
        RedBookNoteResult.DataBean.ItemsBean.NoteCardBean.TagListBean tag = new RedBookNoteResult.DataBean.ItemsBean.NoteCardBean.TagListBean();
        tag.setType("topic");
        tag.setId("5fjaiidjfadjf");
        tag.setName("励志");
        tagList.add(tag);
        entity.setTags(JSONUtil.toJsonStr(tagList));
        entity.setCollectedCount(1);
        entity.setLikedCount(2);
        entity.setCommentCount(3);
        entity.setUpdateTime(new Date());

        redBookNoteMapper.addOrUpdate(entity);
    }

    @Test
    public void testSaveTag() {
        List<RedBookTagEntity> list = new ArrayList<>();
        RedBookTagEntity tag1 = new RedBookTagEntity();
        tag1.setId("1111");
        tag1.setName("哈哈哈哈");
        tag1.setType("topic");
        RedBookTagEntity tag2 = new RedBookTagEntity();
        tag2.setId("2222");
        tag2.setName("嘿嘿嘿嘿");
        tag2.setType("topic");
        list.add(tag1);
        list.add(tag2);
        redBookTagService.saveTagList(list);
    }

    @Test
    public void testSaveImgList() {
        List<RedBookNoteImgEntity> list = new ArrayList<>();
        RedBookNoteImgEntity img1 = new RedBookNoteImgEntity();
        img1.setUrl("111111111");
        img1.setImgIndex(222);
        img1.setWidth(80);
        img1.setHeight(500);
        img1.setNoteId("sssssssssssss");
        img1.setFileId("111111111");

        RedBookNoteImgEntity img2 = new RedBookNoteImgEntity();
        img2.setUrl("222222222");
        img2.setImgIndex(333);
        img2.setWidth(100);
        img2.setHeight(300);
        img2.setNoteId("sssssssssssss");
        img2.setFileId("22222222222");

        list.add(img1);
        list.add(img2);
        redBookNoteImgService.saveImgList(list);
    }

    @Test
    public void testSaveVideo() {
        RedBookNoteVideoEntity video = new RedBookNoteVideoEntity();
        video.setDuration(5000);
        List<String> backupUrls = new ArrayList<>();
        backupUrls.add("1111111");
        backupUrls.add("2222222");
        backupUrls.add("3333333");

        video.setBackupUrls(JSONUtil.toJsonStr(backupUrls));
        video.setVideoId("1232342");
        video.setMasterUrl("master_url");
        video.setNoteId("22334455");

        redBookNoteVideoService.save(video);

    }
}
