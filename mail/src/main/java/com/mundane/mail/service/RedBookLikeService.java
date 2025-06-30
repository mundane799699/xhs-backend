package com.mundane.mail.service;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mundane.mail.constant.VideoHubConstants;
import com.mundane.mail.dto.RedBookLikeDto;
import com.mundane.mail.entity.*;
import com.mundane.mail.mapper.RedBookLikeMapper;
import com.mundane.mail.mapper.RedBookLikeParseTaskMapper;
import com.mundane.mail.mapper.RedBookNoteMapper;
import com.mundane.mail.pojo.RedBookCollectResult;
import com.mundane.mail.pojo.RedBookLikeRequest;
import com.mundane.mail.pojo.RedBookNoteParseDetailResult;
import com.mundane.mail.utils.RedBookUtils;
import com.mundane.mail.vo.RedBookCollectExportExcelVo;
import com.mundane.mail.vo.RedBookCollectExportHtmlVo;
import com.mundane.mail.vo.RedBookParseDetailReuestVo;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@Service
@Slf4j
public class RedBookLikeService {

    @Autowired
    private RedBookLikeMapper redBookLikeMapper;

    @Autowired
    private FreeMarkerConfigurer freemarkerConfig;

    @Autowired
    private RedBookLikeParseTaskMapper parseTaskMapper;

    @Autowired
    private RedBookNoteMapper redBookNoteMapper;

    @Autowired
    private RedBookTagService tagService;

    @Autowired
    private RedBookNoteImgService redBookNoteImgService;


    public void save(RedBookLikeRequest request) {
        RedBookCollectResult result = JSONUtil.toBean(request.getResult(), RedBookCollectResult.class);
        List<RedBookCollectResult.DataBean.NotesBean> notes = result.getData().getNotes();
        List<RedBookLikeEntity> entityList = new ArrayList<>();
        for (RedBookCollectResult.DataBean.NotesBean note : notes) {
            RedBookLikeEntity entity = new RedBookLikeEntity();
            entity.setUserId(request.getUserId());
            entity.setNoteId(note.getNoteId());
            entity.setXsecToken(note.getXsecToken());
            entity.setDisplayTitle(note.getDisplayTitle());
            entity.setType(note.getType());
            List<RedBookCollectResult.DataBean.NotesBean.CoverBean.ImageInfo> infoList = note.getCover().getInfoList();
            entity.setCoverUrl(infoList.get(infoList.size() - 1).getUrl());
            entity.setOwnerId(note.getUser().getUserId());
            entity.setOwnerAvatar(note.getUser().getAvatar());
            entity.setOwnerNickname(note.getUser().getNickname());
            entity.setLiked(note.getInteractInfo().getLiked());
            entity.setLikedCount(note.getInteractInfo().getLikedCount());
            entityList.add(entity);
        }
        redBookLikeMapper.batchAddOrUpdate(entityList);
    }

    public PageInfo queryByUserId(String userId, String displayTitle, String ownerNickname, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<RedBookLikeDto> notes = redBookLikeMapper.queryByUserId(userId, displayTitle, ownerNickname);
        for (RedBookLikeDto note : notes) {
            String type = note.getType();
            if ("normal".equals(type)) {
                note.setType("普通");
            } else if ("video".equals(type)) {
                note.setType("视频");
            }
            String tagList = RedBookUtils.getTagList(note.getTags());
            note.setTagList(tagList);
        }
        PageInfo<RedBookLikeDto> info = new PageInfo<>(notes);
        return info;
    }

    public void downloadExcel(RedBookCollectExportExcelVo vo, HttpServletResponse response) {
        String[] titles = new String[]{"标题", "笔记链接", "笔记内容", "作者", "封面地址", "点赞数"};
        List<RedBookLikeDto> notes = redBookLikeMapper.queryByUserId(vo.getUserId(), vo.getDisplayTitle(), vo.getOwnerNickname());
        //创建Excel文档
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建sheet页
        XSSFSheet sheet = workbook.createSheet("sheet1");
        sheet.setColumnWidth(0, 80 * 256);
        sheet.setColumnWidth(1, 60 * 256);
        sheet.setColumnWidth(2, 110 * 256);
        sheet.setColumnWidth(3, 110 * 256);
        sheet.setColumnWidth(4, 60 * 256);
        sheet.setColumnWidth(5, 60 * 256);
        // 冻结第一行
        sheet.createFreezePane(0, 1, 0, 1);
        //创建第一行 通常第一行作为 数据表头
        XSSFRow titleRow = sheet.createRow(0);

        // 创建一个字体对象
        Font boldFont = workbook.createFont();
        boldFont.setBold(true); // 将字体加粗
        // 创建一个单元格样式对象，并将字体对象设置为其中的一部分
        CellStyle boldStyle = workbook.createCellStyle();
        boldStyle.setFont(boldFont);
        //设置 第一行的列数据
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = titleRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(boldStyle);
        }

        CreationHelper createHelper = workbook.getCreationHelper();
        // 创建超链接样式
        CellStyle hlinkStyle = workbook.createCellStyle();
        Font hlinkFont = workbook.createFont();
        hlinkFont.setUnderline(Font.U_SINGLE);
        hlinkFont.setColor(IndexedColors.BLUE.getIndex());
        hlinkStyle.setFont(hlinkFont);

        for (int i = 0; i < notes.size(); i++) {
            RedBookLikeDto note = notes.get(i);
            XSSFRow row = sheet.createRow(i + 1);

            XSSFCell cell0 = row.createCell(0);
            cell0.setCellValue(note.getDisplayTitle());

            XSSFCell cell1 = row.createCell(1);
            // https://www.xiaohongshu.com/explore/6857d470000000000b01d330?xsec_token=ABjkBbxIaf9xNTNc1e1QGlKITURpBbNrODU9sSk-pGr9A=&xsec_source=pc_like
            String noteAddress = "https://www.xiaohongshu.com/explore/" + note.getNoteId() + "?xsec_token=" + note.getXsecToken() + "&xsec_source=pc_like";
            Hyperlink noteLink = createHelper.createHyperlink(HyperlinkType.URL);
            noteLink.setAddress(noteAddress);
            cell1.setHyperlink(noteLink);
            cell1.setCellValue(noteAddress);
            cell1.setCellStyle(hlinkStyle);

            XSSFCell cell2 = row.createCell(2);
            cell2.setCellValue(note.getNoteDesc());


            XSSFCell cell3 = row.createCell(3);
            cell3.setCellValue(note.getOwnerNickname());
            Hyperlink authorLink = createHelper.createHyperlink(HyperlinkType.URL);
            authorLink.setAddress("https://www.xiaohongshu.com/user/profile/" + note.getOwnerId());
            cell3.setHyperlink(authorLink);
            cell3.setCellStyle(hlinkStyle);

            XSSFCell cell4 = row.createCell(4);
            String coverUrl = note.getCoverUrl() + "?imageView2/2/w/640/format/webp";
            Hyperlink coverImgLink = createHelper.createHyperlink(HyperlinkType.URL);
            coverImgLink.setAddress(coverUrl);
            cell4.setHyperlink(coverImgLink);
            cell4.setCellValue(coverUrl);
            cell4.setCellStyle(hlinkStyle);

            XSSFCell cell5 = row.createCell(5);
            cell5.setCellValue(note.getLikedCount());
        }

        OutputStream outputStream = null;
        try {
            String fileName = "小红书点赞" + vo.getUserId() + ".xlsx";
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IoUtil.close(outputStream);
        }

    }

    public void downloadHtml(RedBookCollectExportHtmlVo vo, HttpServletResponse response) throws Exception {
        // Create a new file to store the generated HTML
        String fileName = "小红书点赞" + vo.getUserId() + ".html";
        File htmlFile = new File(fileName);
        if (htmlFile.exists()) {
            htmlFile.delete();
            htmlFile.createNewFile();
        }
        // Generate the HTML using Freemarker
        Map<String, Object> model = new HashMap<>();
        List<RedBookLikeDto> notes = redBookLikeMapper.queryByUserId(vo.getUserId(), vo.getDisplayTitle(), vo.getOwnerNickname());
        for (RedBookLikeDto note : notes) {
            String type = note.getType();
            if ("normal".equals(type)) {
                note.setType("普通");
            } else if ("video".equals(type)) {
                note.setType("视频");
            }
        }
        model.put("notes", notes);
        Template template = freemarkerConfig.getConfiguration().getTemplate("html.ftl");
        Writer out = new FileWriter(htmlFile);
        template.process(model, out);
        out.close();
        log.info("htmlFile path = {}", htmlFile.getAbsolutePath());

        BufferedInputStream inputStream = FileUtil.getInputStream(htmlFile);
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        ServletOutputStream outputStream = response.getOutputStream();
        IoUtil.copy(inputStream, outputStream);
        IoUtil.close(inputStream);
        IoUtil.close(outputStream);
    }


    public void deleteByUserId(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new RuntimeException("userId不能为空");
        }
        if (StringUtils.equals("5f827ea10000000001007b5b", userId)) {
            throw new RuntimeException("你正在删除管理员的数据，请联系管理员");
        }
        redBookLikeMapper.deleteByUserId(userId);
    }

    @Async("taskExecutor")
    public void parseDetail(RedBookParseDetailReuestVo vo) {
        log.info("currentThread = {}", Thread.currentThread().getName());
        String userId = vo.getUserId();
        List<RedBookLikeDto> notes = redBookLikeMapper.queryByUserId(userId, null, null);

        for (int i = 0; i < notes.size(); i++) {
            RedBookLikeDto note = notes.get(i);
            String detailId = note.getDetailId();
            if (StringUtils.isEmpty(detailId)) {
                try {
                    getDetail(note);
                    updateProgress(userId, i, notes.size());
                    Thread.sleep(2000);
                } catch (Exception e) {
                    log.error("解析笔记详情发生错误：{}", "https://www.xiaohongshu.com/explore/" + note.getNoteId(), e);
                }
            }
        }
        // 任务完成之后更新状态
        RedBookLikeParseTaskEntity entity = new RedBookLikeParseTaskEntity();
        entity.setUserId(userId);
        entity.setStatus(2);
        entity.setProgress("完成");
        parseTaskMapper.addOrUpdate(entity);
    }

    private void updateProgress(String userId, int i, int size) {
        RedBookLikeParseTaskEntity entity = new RedBookLikeParseTaskEntity();
        entity.setUserId(userId);
        entity.setStatus(1);
        entity.setProgress((i + 1) + "/" + size);
        parseTaskMapper.addOrUpdate(entity);
    }

    public RedBookLikeParseTaskEntity queryTask(String userId) {
        return parseTaskMapper.selectByPrimaryKey(userId);
    }

    private void getDetail(RedBookLikeDto noteDto) throws Exception {
        String url = "https://www.xiaohongshu.com/explore/" + noteDto.getNoteId();
        Document doc = Jsoup.connect(url).userAgent(VideoHubConstants.USER_AGENT).get();

        String match = "window.__INITIAL_STATE__";
        for (Element script : doc.select("script")) {
            String scriptData = script.data();
            if (!scriptData.contains(match)) {
                continue;
            }

            String jsonString = scriptData.substring(scriptData.indexOf("=") + 1);
            log.info("发现笔记信息~~~~");
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
            }

            entity.setNoteId(noteId);
            entity.setTitle(title);
            entity.setAuthorId(userId);
            entity.setAuthorName(nickname);
            entity.setNoteDesc(desc);
            entity.setType(type);
            entity.setUpdateTime(new Date());
            entity.setStatus(0);

            redBookNoteMapper.addOrUpdate(entity);
        }
    }

    public void initParseTask(String userId) {
        RedBookLikeParseTaskEntity entity = new RedBookLikeParseTaskEntity();
        entity.setUserId(userId);
        entity.setStatus(1);
        entity.setProgress("0/0");
        parseTaskMapper.addOrUpdate(entity);
    }

    public String queryParseDetail(String userId) {
        RedBookLikeParseTaskEntity result = parseTaskMapper.selectByPrimaryKey(userId);
        if (result == null) {
            return "找不到该任务，请确认userId是否正确";
        }
        return result.getProgress();
    }
}
