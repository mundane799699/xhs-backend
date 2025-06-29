package com.mundane.mail.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mundane.mail.config.VideoParseConfig;
import com.mundane.mail.config.XhsConfig;
import com.mundane.mail.constant.VideoHubConstants;
import com.mundane.mail.dto.RedBookCollectDto;
import com.mundane.mail.dto.RedBookImageDto;
import com.mundane.mail.entity.RedBookNoteEntity;
import com.mundane.mail.entity.RedBookNoteImgEntity;
import com.mundane.mail.entity.RedBookNoteVideoEntity;
import com.mundane.mail.entity.RedBookTagEntity;
import com.mundane.mail.mapper.RedBookNoteImgMapper;
import com.mundane.mail.mapper.RedBookNoteMapper;
import com.mundane.mail.mapper.RedBookNoteVideoMapper;
import com.mundane.mail.pojo.*;
import com.mundane.mail.utils.RedBookUtils;
import com.mundane.mail.utils.RegexUtil;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@EnableConfigurationProperties(XhsConfig.class)
public class RedBookNoteService {
    @Autowired
    private FreeMarkerConfigurer freemarkerConfig;

    @Autowired
    private RedBookNoteMapper redBookNoteMapper;

    @Autowired
    private RedBookTagService tagService;

    @Autowired
    private RedBookNoteImgService redBookNoteImgService;

    @Autowired
    private RedBookNoteVideoService redBookNoteVideoService;

    @Autowired
    private RedBookNoteImgMapper redBookNoteImgMapper;

    @Autowired
    private RedBookNoteVideoMapper redBookNoteVideoMapper;

    @Autowired
    private XhsConfig xhsConfig;

    
    public void saveNote(RedBookNoteRequest request) {
        RedBookNoteResult result = JSONUtil.toBean(request.getResult(), RedBookNoteResult.class);
        for (RedBookNoteResult.DataBean.ItemsBean item : result.getData().getItems()) {
            RedBookNoteEntity entity = new RedBookNoteEntity();
            RedBookNoteResult.DataBean.ItemsBean.NoteCardBean noteCard = item.getNoteCard();
            entity.setNoteId(noteCard.getNoteId());
            entity.setTitle(noteCard.getTitle());
            entity.setType(noteCard.getType());
            entity.setAuthorId(noteCard.getUser().getUserId());
            entity.setAuthorName(noteCard.getUser().getNickname());
            entity.setNoteDesc(noteCard.getDesc());

            if (!noteCard.getTagList().isEmpty()) {
                List<RedBookTagEntity> tagEntityList = new ArrayList<>();
                for (RedBookNoteResult.DataBean.ItemsBean.NoteCardBean.TagListBean tag : noteCard.getTagList()) {
                    RedBookTagEntity tagEntity = new RedBookTagEntity();
                    tagEntity.setId(tag.getId());
                    tagEntity.setName(tag.getName());
                    tagEntity.setType(tag.getType());
                    tagEntityList.add(tagEntity);
                }
                List<String> tagList = getTags(noteCard.getTagList());
                entity.setTags(JSONUtil.toJsonStr(tagList));
                tagService.saveTagList(tagEntityList);
            }
            if (!noteCard.getImageList().isEmpty()) {
                List<RedBookNoteImgEntity> noteImgList = new ArrayList<>();
                for (int i = 0; i < noteCard.getImageList().size(); i++) {
                    RedBookNoteResult.DataBean.ItemsBean.NoteCardBean.ImageListBean imageItem = noteCard.getImageList().get(i);
                    RedBookNoteImgEntity noteImgEntity = new RedBookNoteImgEntity();
                    noteImgEntity.setFileId(imageItem.getFileId());
                    noteImgEntity.setImgIndex(i);
                    noteImgEntity.setNoteId(noteCard.getNoteId());
                    noteImgEntity.setHeight(imageItem.getHeight());
                    noteImgEntity.setWidth(imageItem.getWidth());
                    List<RedBookNoteResult.DataBean.ItemsBean.NoteCardBean.ImageListBean.ImageInfo> infoList = imageItem.getInfoList();
                    noteImgEntity.setUrl(infoList.get(infoList.size() - 1).getUrl());
                    noteImgList.add(noteImgEntity);
                }
                // 先删除之前的关联图片
                redBookNoteImgService.deleteByNoteId(noteCard.getNoteId());
                redBookNoteImgService.saveImgList(noteImgList);
            }
            RedBookNoteResult.DataBean.ItemsBean.NoteCardBean.VideoBeanX video = noteCard.getVideo();
            if (video != null) {
                RedBookNoteVideoEntity videoEntity = new RedBookNoteVideoEntity();
                videoEntity.setVideoId(video.getMedia().getVideoId().toString());
                videoEntity.setNoteId(noteCard.getNoteId());
                List<RedBookNoteResult.DataBean.ItemsBean.NoteCardBean.VideoBeanX.MediaBean.StreamBean.H264Bean> h264 = video.getMedia().getStream().getH264();
                if (CollectionUtil.isNotEmpty(h264)) {
                    videoEntity.setMasterUrl(h264.get(0).getMasterUrl());
                    videoEntity.setBackupUrls(JSONUtil.toJsonStr(h264.get(0).getBackupUrls()));
                    videoEntity.setDuration(h264.get(0).getDuration());
                    redBookNoteVideoService.save(videoEntity);
                } else {
                    log.error("没有h264的信息, noteId: {}", noteCard.getNoteId());
                }
            }

            RedBookNoteResult.DataBean.ItemsBean.NoteCardBean.InteractInfoBean interactInfo = noteCard.getInteractInfo();
            entity.setCollectedCount(Integer.valueOf(interactInfo.getCollectedCount()));
            entity.setLikedCount(Integer.valueOf(interactInfo.getLikedCount()));
            entity.setCommentCount(Integer.valueOf(interactInfo.getCommentCount()));
            entity.setUpdateTime(new Date());
            entity.setStatus(0);

            redBookNoteMapper.addOrUpdate(entity);

        }
    }

    public void getDetail(RedBookCollectDto noteDto) throws Exception {
        String url = "https://www.xiaohongshu.com/explore/" + noteDto.getNoteId();
        Document doc = Jsoup
                .connect(url)
                .userAgent(VideoHubConstants.USER_AGENT)
                .header("Cookie", VideoHubConstants.getCookie())
                .get();

//        List<RedBookImageDto> picPathList = new ArrayList<>();
        String match = "window.__INITIAL_STATE__";
        for (Element script : doc.select("script")) {
            String scriptData = script.data();
            if (!scriptData.contains(match)) {
                continue;
            }
            log.info("开始解析笔记：{}", "https://www.xiaohongshu.com/explore/" + noteDto.getNoteId());
            String jsonString = scriptData.substring(scriptData.indexOf("=") + 1);
            JSONObject jsonObject = JSONUtil.parseObj(jsonString);
            JSONObject object = jsonObject.getJSONObject("note");
            String firstNoteId = object.getStr("firstNoteId");
            RedBookNoteEntity entity = new RedBookNoteEntity();

            if (StringUtils.isEmpty(firstNoteId)) {
                log.info("笔记已失效：{}", "https://www.xiaohongshu.com/explore/" + noteDto.getNoteId());
                entity.setNoteId(noteDto.getNoteId());
                entity.setUpdateTime(new Date());
                entity.setStatus(1);
                redBookNoteMapper.addOrUpdate(entity);
                return;
            }
            log.info("笔记有效：{}", "https://www.xiaohongshu.com/explore/" + noteDto.getNoteId());
            JSONObject noteDetailMap = object.getJSONObject("noteDetailMap");
            JSONObject firstNote = noteDetailMap.getJSONObject(firstNoteId);
            JSONObject noteJsonObj = firstNote.getJSONObject("note");
            String noteStr = noteJsonObj.toString();
            RedBookNoteParseDetailResult note = JSONUtil.toBean(noteStr, RedBookNoteParseDetailResult.class);
            RedBookNoteParseDetailResult.UserDTO user = note.getUser();
            String userId = user.getUserId();
            String nickname = user.getNickname();

            String noteId = note.getNoteId();
            String title = note.getTitle();
            String desc = note.getDesc();
            String type = note.getType();

            List<RedBookNoteParseDetailResult.TagListDTO> tagList = note.getTagList();
            if (!tagList.isEmpty()) {
                List<RedBookTagEntity> tagEntityList = new ArrayList<>();

                List<String> tags = new ArrayList<>();
                for (RedBookNoteParseDetailResult.TagListDTO tag : tagList) {
                    RedBookTagEntity tagEntity = new RedBookTagEntity();
                    tagEntity.setId(tag.getId());
                    tagEntity.setName(tag.getName());
                    tagEntity.setType(tag.getType());
                    tagEntityList.add(tagEntity);
                    tags.add("#" + tag.getName());
                }
                entity.setTags(JSONUtil.toJsonStr(tags));
                tagService.saveTagList(tagEntityList);
            }
            // 保存图片链接
            if (!note.getImageList().isEmpty()) {
                List<RedBookNoteImgEntity> noteImgList = new ArrayList<>();
                for (int i = 0; i < note.getImageList().size(); i++) {
                    RedBookNoteParseDetailResult.ImageListDTO imageItem = note.getImageList().get(i);
                    RedBookNoteImgEntity noteImgEntity = new RedBookNoteImgEntity();
                    noteImgEntity.setFileId(imageItem.getFileId());
                    noteImgEntity.setImgIndex(i);
                    noteImgEntity.setNoteId(noteId);
                    noteImgEntity.setHeight(imageItem.getHeight());
                    noteImgEntity.setWidth(imageItem.getWidth());
                    noteImgEntity.setUrl(imageItem.getUrlDefault());
                    noteImgList.add(noteImgEntity);
                }
                // 先删除之前的关联图片
                redBookNoteImgService.deleteByNoteId(noteId);
                redBookNoteImgService.saveImgList(noteImgList);

//                // 下载图片
//                File imageDir = new File(xhsConfig.getImgFolderPath());
//                if (!imageDir.exists()) {
//                    imageDir.mkdirs();
//                }
//                for (int i = 0; i < note.getImageList().size(); i++) {
//                    RedBookNoteParseDetailResult.ImageListDTO image = note.getImageList().get(i);
//                    String imageUrl = image.getUrlDefault();
//                    String fileName = noteId + "_" + (i + 1) + "." + "jpg";
//                    String picPath = download(imageUrl, fileName, imageDir);
//                    if (StringUtils.isNotEmpty(picPath)) {
//                        picPathList.add(new RedBookImageDto(fileName, picPath));
//                    }
//                }
            }
            // 保存视频链接
            RedBookNoteParseDetailResult.Video video = note.getVideo();
            if (video != null) {
                RedBookNoteVideoEntity videoEntity = new RedBookNoteVideoEntity();
                videoEntity.setVideoId(video.getMedia().getVideoId());
                videoEntity.setNoteId(noteId);
                List<RedBookNoteParseDetailResult.Video.MediaDTO.StreamDTO.H264DTO> h264 = video.getMedia().getStream().getH264();
                if (CollectionUtil.isNotEmpty(h264)) {
                    videoEntity.setMasterUrl(h264.get(0).getMasterUrl());
                    videoEntity.setBackupUrls(JSONUtil.toJsonStr(h264.get(0).getBackupUrls()));
                    videoEntity.setDuration(h264.get(0).getDuration());
                    redBookNoteVideoService.save(videoEntity);
                } else {
                    log.error("没有h264的信息, noteId: {}", noteId);
                }
            }

            entity.setNoteId(noteId);
            entity.setTitle(title);
            entity.setAuthorId(userId);
            entity.setAuthorName(nickname);
            entity.setNoteDesc(desc);
            entity.setType(type);
            entity.setUpdateTime(new Date());
//            entity.setPicPathList(JSONUtil.toJsonStr(picPathList));
            entity.setStatus(0);

            redBookNoteMapper.addOrUpdate(entity);
        }
    }

    private static String download(String url, String fileName, File folder) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestProperty("User-Agent", VideoHubConstants.USER_AGENT);

            File imageFile = new File(folder, fileName);
            if (imageFile.exists()) {
                log.info("图片已存在", imageFile.getName());
                return imageFile.getAbsolutePath();
            }
            log.info("imageFile = {}", imageFile.getAbsolutePath());
            IoUtil.copy(connection.getInputStream(), new FileOutputStream(imageFile));
            return imageFile.getAbsolutePath();
        } catch (Exception e) {
            log.error("文件下载错误！", e);
            return null;
        }
    }

    public void downloadNoteMd(RedBookNoteExportMdVo vo, HttpServletResponse response) throws Exception {
        RedBookNoteEntity note = redBookNoteMapper.selectByPrimaryKey(vo.getNoteId());
        if (note == null) {
            return;
        }
        String fileName = note.getTitle();
        fileName = FileNameUtil.cleanInvalid(fileName);
        if (StringUtils.isEmpty(fileName)) {
            fileName = note.getNoteId();
        }
        fileName = fileName + ".md";
        List<RedBookNoteImgEntity> imgEntityList = redBookNoteImgMapper.selectByNoteId(vo.getNoteId());
        List<String> images = imgEntityList.stream().map(item -> item.getUrl()).collect(Collectors.toList());
        RedBookNoteVideoEntity record = new RedBookNoteVideoEntity();
        record.setNoteId(vo.getNoteId());
        RedBookNoteVideoEntity video = redBookNoteVideoMapper.selectOne(record);
        File mdFile = new File(xhsConfig.getResPath(), fileName);
        if (mdFile.exists()) {
            mdFile.delete();
            mdFile.createNewFile();
        }
        Map<String, Object> model = new HashMap<>();
        model.put("title", note.getTitle());
        model.put("noteId", note.getNoteId());
        model.put("authorName", note.getAuthorName());
        model.put("authorId", note.getAuthorId());
        model.put("desc", note.getNoteDesc());
        model.put("tags", RedBookUtils.getTagList(note.getTags()));
        model.put("images", images);
        if (video != null) {
            model.put("videoUrl", video.getMasterUrl());
        }
        Template template = freemarkerConfig.getConfiguration().getTemplate("md.ftl");
        Writer out = new FileWriter(mdFile);
        template.process(model, out);
        out.close();
        BufferedInputStream inputStream = FileUtil.getInputStream(mdFile);
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition", "attachment;filename=" + encodedFileName);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        ServletOutputStream outputStream = response.getOutputStream();
        IoUtil.copy(inputStream, outputStream);
        IoUtil.close(inputStream);
        IoUtil.close(outputStream);
        FileUtils.deleteQuietly(mdFile);
    }

    private List<String> getTags(List<RedBookNoteResult.DataBean.ItemsBean.NoteCardBean.TagListBean> tagList) {
        List<String> list = new ArrayList<>();
        for (RedBookNoteResult.DataBean.ItemsBean.NoteCardBean.TagListBean tag : tagList) {
            list.add("#" + tag.getName());
        }
        return list;
    }

    public RedBookNoteMediaInfo getMediaInfo(RedBookNoteExportMdVo vo) {

        RedBookNoteMediaInfo mediaInfo = new RedBookNoteMediaInfo();
        RedBookNoteEntity note = redBookNoteMapper.selectByPrimaryKey(vo.getNoteId());

        String title = FileNameUtil.cleanInvalid(note.getTitle());
        if (StringUtils.isEmpty(title)) {
            title = note.getNoteId();
        }
        mediaInfo.setTitle(title);

        List<RedBookNoteImgEntity> imgEntityList = redBookNoteImgMapper.selectByNoteId(vo.getNoteId());
        if (CollectionUtil.isNotEmpty(imgEntityList)) {
            List<String> imageUrls = imgEntityList.stream().map(item -> item.getUrl()).collect(Collectors.toList());
            mediaInfo.setImageUrls(imageUrls);
        }

        RedBookNoteVideoEntity record = new RedBookNoteVideoEntity();
        record.setNoteId(vo.getNoteId());
        RedBookNoteVideoEntity video = redBookNoteVideoMapper.selectOne(record);
        if (video != null) {
            mediaInfo.setVideoUrl(video.getMasterUrl());
        }
        return mediaInfo;
    }
}
