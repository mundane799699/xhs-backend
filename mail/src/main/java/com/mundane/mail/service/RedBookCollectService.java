package com.mundane.mail.service;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mundane.mail.config.XhsConfig;
import com.mundane.mail.dto.RedBookCollectDto;
import com.mundane.mail.dto.RedBookImageDto;
import com.mundane.mail.entity.*;
import com.mundane.mail.mapper.*;
import com.mundane.mail.pojo.RedBookCollectRequest;
import com.mundane.mail.pojo.RedBookCollectResult;
import com.mundane.mail.utils.JsonUtils;
import com.mundane.mail.utils.RedBookUtils;
import com.mundane.mail.vo.RedBookCollectExportExcelVo;
import com.mundane.mail.vo.RedBookCollectExportHtmlVo;
import com.mundane.mail.vo.RedBookCollectExportZipVo;
import com.mundane.mail.vo.RedBookParseDetailReuestVo;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@EnableConfigurationProperties(XhsConfig.class)
public class RedBookCollectService {

    @Autowired
    private RedBookCollectMapper redBookCollectMapper;

    @Autowired
    private FreeMarkerConfigurer freemarkerConfig;

    @Autowired
    private RedBookCollectParseTaskMapper parseTaskMapper;

    @Autowired
    private RedBookNoteService redBookNoteService;

    @Autowired
    private RedBookNoteImgMapper redBookNoteImgMapper;


    @Autowired
    private RedBookNoteVideoMapper redBookNoteVideoMapper;

    @Autowired
    private XhsConfig xhsConfig;


    
    public void save(RedBookCollectRequest request) {
        if (StringUtils.isEmpty(request.getUserId())) {
            throw new RuntimeException("userId不能为空");
        }
        RedBookCollectResult result = JSONUtil.toBean(request.getResult(), RedBookCollectResult.class);
        List<RedBookCollectEntity> entityList = new ArrayList<>();
        List<RedBookCollectResult.DataBean.NotesBean> notes = result.getData().getNotes();
        for (int i = 0; i < notes.size(); i++) {
            RedBookCollectResult.DataBean.NotesBean note = notes.get(i);
            RedBookCollectEntity entity = new RedBookCollectEntity();
            entity.setUserId(request.getUserId());
            entity.setNoteId(note.getNoteId());
            entity.setDisplayTitle(note.getDisplayTitle());
            entity.setType(note.getType());
            entity.setCollectIndex(i);
            entity.setPatchIndex(request.getPatchIndex());
            List<RedBookCollectResult.DataBean.NotesBean.CoverBean.ImageInfo> infoList = note.getCover().getInfoList();
            entity.setCoverUrl(infoList.get(infoList.size() - 1).getUrl());
            entity.setOwnerId(note.getUser().getUserId());
            entity.setOwnerAvatar(note.getUser().getAvatar());
            entity.setOwnerNickname(note.getUser().getNickname());
            entity.setLiked(note.getInteractInfo().getLiked());
            entity.setLikedCount(note.getInteractInfo().getLikedCount());
            entityList.add(entity);
        }
        redBookCollectMapper.batchAddOrUpdate(entityList);
    }

    public void downloadExcel(RedBookCollectExportExcelVo vo, HttpServletResponse response) {
        if (StringUtils.isEmpty(vo.getUserId())) {
            log.error("userId不能为空");
            throw new RuntimeException("userId不能为空");
        }
        String[] titles = new String[]{"标题", "笔记链接", "笔记内容", "作者", "封面地址", "点赞数"};
        List<RedBookCollectDto> notes = redBookCollectMapper.queryByUserId(vo.getUserId(), vo.getDisplayTitle(), vo.getOwnerNickname());
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
            RedBookCollectDto note = notes.get(i);
            XSSFRow row = sheet.createRow(i + 1);

            XSSFCell cell0 = row.createCell(0);
            cell0.setCellValue(note.getDisplayTitle());

            XSSFCell cell1 = row.createCell(1);
            String noteAddress = "https://www.xiaohongshu.com/explore/" + note.getNoteId();
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
            String fileName = "小红书收藏" + vo.getUserId() + ".xlsx";
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
        if (StringUtils.isEmpty(vo.getUserId())) {
            log.error("userId不能为空");
            throw new RuntimeException("userId不能为空");
        }
        String fileName = "小红书收藏" + vo.getUserId() + ".html";
        File htmlFile = new File(fileName);
        if (htmlFile.exists()) {
            htmlFile.delete();
            htmlFile.createNewFile();
        }
        // Generate the HTML using Freemarker
        Map<String, Object> model = new HashMap<>();
        List<RedBookCollectDto> notes = redBookCollectMapper.queryByUserId(vo.getUserId(), vo.getDisplayTitle(), vo.getOwnerNickname());
        for (RedBookCollectDto note : notes) {
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


    public PageInfo queryByUserId(String userId, String displayTitle, String ownerNickname, Integer pageNum, Integer pageSize) {
        if (StringUtils.isEmpty(userId)) {
            log.error("userId不能为空");
            throw new RuntimeException("userId不能为空");
        }
        PageHelper.startPage(pageNum, pageSize);
        List<RedBookCollectDto> notes = redBookCollectMapper.queryByUserId(userId, displayTitle, ownerNickname);
        for (RedBookCollectDto note : notes) {
            String type = note.getType();
            if ("normal".equals(type)) {
                note.setType("普通");
            } else if ("video".equals(type)) {
                note.setType("视频");
            }
            String tagList = RedBookUtils.getTagList(note.getTags());
            note.setTagList(tagList);
        }
        PageInfo<RedBookCollectDto> info = new PageInfo<>(notes);
        return info;
    }


    
    public void deleteByUserId(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new RuntimeException("userId不能为空");
        }
        if (StringUtils.equals("5f827ea10000000001007b5b", userId)) {
            throw new RuntimeException("你正在删除管理员的数据，请联系管理员");
        }
        redBookCollectMapper.deleteByUserId(userId);
    }


    @Async("taskExecutor")
    public void forceParseDetail(RedBookParseDetailReuestVo vo) {
        String userId = vo.getUserId();
        try {
            log.info("currentThread = {}", Thread.currentThread().getName());
            List<RedBookCollectDto> notes = redBookCollectMapper.queryByUserId(userId, null, null);
            for (int i = 0; i < notes.size(); i++) {
                saveDetail(notes, i, userId);
            }
            log.info("强制解析详情完成, userId = {}", userId);
        } catch (Exception e) {
            log.error("forceParseDetail发生错误", e);
        }
        // 任务完成之后更新状态
        RedBookCollectParseTaskEntity entity = new RedBookCollectParseTaskEntity();
        entity.setUserId(userId);
        entity.setStatus(2);
        entity.setProgress("完成");
        parseTaskMapper.addOrUpdate(entity);
    }

    @Async("taskExecutor")
    public void parseDetail(RedBookParseDetailReuestVo vo) {
        String userId = vo.getUserId();
        try {
            log.info("currentThread = {}", Thread.currentThread().getName());
            List<RedBookCollectDto> notes = redBookCollectMapper.queryByUserId(userId, null, null);
            List<RedBookCollectDto> filterList = notes.stream().filter(item -> StringUtils.isEmpty(item.getDetailId()) || StringUtils.isEmpty(item.getPicPathList())).collect(Collectors.toList());
            for (int i = 0; i < filterList.size(); i++) {
                saveDetail(filterList, i, userId);
            }
            log.info("解析详情完成, userId = {}", userId);
        } catch (Exception e) {
            log.error("parseDetail发生错误", e);
        }
        // 任务完成之后更新状态
        RedBookCollectParseTaskEntity entity = new RedBookCollectParseTaskEntity();
        entity.setUserId(userId);
        entity.setStatus(2);
        entity.setProgress("完成");
        parseTaskMapper.addOrUpdate(entity);
    }

    private void saveDetail(List<RedBookCollectDto> filterList, int i, String userId) {
        RedBookCollectDto note = filterList.get(i);
        try {
            redBookNoteService.getDetail(note);
            updateProgress(userId, i, filterList.size());
            Thread.sleep(2000);
        } catch (Exception e) {
            log.error("解析笔记详情发生错误：{}", "https://www.xiaohongshu.com/explore/" + note.getNoteId(), e);
        }
    }

    private void updateProgress(String userId, int i, int size) {
        RedBookCollectParseTaskEntity entity = new RedBookCollectParseTaskEntity();
        entity.setUserId(userId);
        entity.setStatus(1);
        entity.setProgress((i + 1) + "/" + size);
        parseTaskMapper.addOrUpdate(entity);
    }

    public RedBookCollectParseTaskEntity queryTask(String userId) {
        return parseTaskMapper.selectByPrimaryKey(userId);
    }


    public void initParseTask(String userId) {
        RedBookCollectParseTaskEntity entity = new RedBookCollectParseTaskEntity();
        entity.setUserId(userId);
        entity.setStatus(1);
        entity.setProgress("0/0");
        parseTaskMapper.addOrUpdate(entity);
    }

    public String queryParseDetail(String userId) {
        RedBookCollectParseTaskEntity result = parseTaskMapper.selectByPrimaryKey(userId);
        if (result == null) {
            return "找不到该任务，请确认userId是否正确";
        }
        return result.getProgress();
    }

    public void downloadZip(RedBookCollectExportZipVo vo, HttpServletResponse response) throws Exception {
        if (StringUtils.isEmpty(vo.getUserId())) {
            log.error("userId不能为空");
            throw new RuntimeException("userId不能为空");
        }
        String folderName = "小红书收藏" + vo.getUserId();
        File userFolder = new File(xhsConfig.getResPath(), folderName);
        // 如果用户文件夹已存在, 先删除再创建新的
        if (userFolder.exists()) {
            FileUtil.del(userFolder);
        }
        userFolder.mkdirs();
        List<RedBookCollectDto> notes = redBookCollectMapper.queryByUserId(vo.getUserId(), vo.getDisplayTitle(), vo.getOwnerNickname());
        for (RedBookCollectDto note : notes) {
            String detailId = note.getDetailId();
            if (StringUtils.isEmpty(detailId)) {
                continue;
            }
            try {
                saveMdFileToFolder(note, userFolder);
            } catch (Exception e) {
                log.error("保存md文件发生错误", e);
            }
        }

        File zipFile = new File(folderName + ".zip");

        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipArchiveOutputStream zos = new ZipArchiveOutputStream(fos)) {

            zipDirectory(userFolder, userFolder.getName(), zos);
        }

        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(zipFile.getName(), "UTF-8"));
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

        FileUtils.copyFile(zipFile, response.getOutputStream());
        response.flushBuffer();

        FileUtils.deleteQuietly(userFolder);
        FileUtils.deleteQuietly(zipFile);

    }

    private void saveMdFileToFolder(RedBookCollectDto note, File folder) throws Exception {
        String fileName = note.getDisplayTitle();
        fileName = FileNameUtil.cleanInvalid(fileName);
        if (StringUtils.isEmpty(fileName)) {
            fileName = note.getNoteId();
        }
        fileName = fileName + ".md";

        List<RedBookNoteImgEntity> imgEntityList = redBookNoteImgMapper.selectByNoteId(note.getDetailId());
        List<String> images = imgEntityList.stream().map(item -> item.getUrl()).collect(Collectors.toList());
        RedBookNoteVideoEntity record = new RedBookNoteVideoEntity();
        record.setNoteId(note.getDetailId());
        RedBookNoteVideoEntity video = redBookNoteVideoMapper.selectOne(record);
        File mdFile = new File(folder, fileName);
        if (mdFile.exists()) {
            mdFile.delete();
            mdFile.createNewFile();
        }
        Map<String, Object> model = new HashMap<>();
        model.put("title", note.getDisplayTitle());
        model.put("noteId", note.getNoteId());
        model.put("authorName", note.getOwnerNickname());
        model.put("authorId", note.getOwnerId());
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
    }

    public void downloadZip2(RedBookCollectExportZipVo vo, HttpServletResponse response) throws Exception {
        if (StringUtils.isEmpty(vo.getUserId())) {
            log.error("userId不能为空");
            throw new RuntimeException("userId不能为空");
        }
        String folderName = "小红书收藏" + vo.getUserId();
        File userFolder = new File(xhsConfig.getResPath(), folderName);
        // 如果用户文件夹已存在, 先删除再创建新的
        if (userFolder.exists()) {
            userFolder.delete();
        }
        userFolder.mkdirs();
        File imagesFolder = new File(userFolder, "images");
        if (!imagesFolder.exists()) {
            imagesFolder.mkdirs();
        }
        List<RedBookCollectDto> notes = redBookCollectMapper.queryByUserId(vo.getUserId(), vo.getDisplayTitle(), vo.getOwnerNickname());
        for (RedBookCollectDto note : notes) {
            String detailId = note.getDetailId();
            if (StringUtils.isEmpty(detailId)) {
                continue;
            }
            try {
                saveMdFileAndImgToFolder(note, userFolder, imagesFolder);
            } catch (Exception e) {
                log.error("保存md文件发生错误", e);
            }
        }

        File zipFile = new File(folderName + ".zip");

        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipArchiveOutputStream zos = new ZipArchiveOutputStream(fos)) {

            zipDirectory(userFolder, userFolder.getName(), zos);
        }

        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(zipFile.getName(), "UTF-8"));
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

        FileUtils.copyFile(zipFile, response.getOutputStream());
        response.flushBuffer();

        FileUtils.deleteQuietly(userFolder);
        FileUtils.deleteQuietly(zipFile);

    }

    private void zipDirectory(File directory, String parent, ZipArchiveOutputStream zos) throws IOException {
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                zipDirectory(file, parent + "/" + file.getName(), zos);
            } else {
                ArchiveEntry entry = zos.createArchiveEntry(file, parent + "/" + file.getName());
                zos.putArchiveEntry(entry);
                FileUtils.copyFile(file, zos);
                zos.closeArchiveEntry();
            }
        }
    }

    private void saveMdFileAndImgToFolder(RedBookCollectDto note, File userFolder, File imagesFolder) throws Exception {
        String fileName = note.getDisplayTitle();
        fileName = FileNameUtil.cleanInvalid(fileName);
        if (StringUtils.isEmpty(fileName)) {
            fileName = note.getNoteId();
        }
        fileName = fileName + ".md";
        File mdFile = new File(userFolder, fileName);
        if (mdFile.exists()) {
            return;
        }
        mdFile.createNewFile();
        String picPathListStr = note.getPicPathList();
        List<String> images = new ArrayList<>();
        if (StringUtils.isNotEmpty(picPathListStr)) {
            List<RedBookImageDto> picPathList = JsonUtils.toList(picPathListStr, RedBookImageDto.class);
            // 将图片copy到imagesFolder
            copyImageFile(imagesFolder, picPathList);
            // 记录图片路径，后面写入到markdown里
            images = picPathList.stream().map(item -> item.getFileName()).collect(Collectors.toList());
        }
        RedBookNoteVideoEntity record = new RedBookNoteVideoEntity();
        record.setNoteId(note.getDetailId());
        RedBookNoteVideoEntity video = redBookNoteVideoMapper.selectOne(record);
        Map<String, Object> model = new HashMap<>();
        model.put("title", note.getDisplayTitle());
        model.put("noteId", note.getNoteId());
        model.put("authorName", note.getOwnerNickname());
        model.put("authorId", note.getOwnerId());
        model.put("desc", note.getNoteDesc());
        model.put("tags", RedBookUtils.getTagList(note.getTags()));
        model.put("images", images);
        if (video != null) {
            model.put("videoUrl", video.getMasterUrl());
        }
        Template template = freemarkerConfig.getConfiguration().getTemplate("zip_md.ftl");
        Writer out = new FileWriter(mdFile);
        template.process(model, out);
        out.close();
    }

    private void copyImageFile(File imagesFolder, List<RedBookImageDto> picPathList) {
        for (RedBookImageDto dto : picPathList) {
            File targetFile = new File(imagesFolder, dto.getFileName());
            if (targetFile.exists()) {
                continue;
            }
            File sourceFile = new File(dto.getLocalPath());
            if (!sourceFile.exists()) {
                continue;
            }
            FileUtil.copy(sourceFile, targetFile, true);
        }
    }
}
