package com.mundane.mail.service;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mundane.mail.dto.RedBookCollectDto;
import com.mundane.mail.entity.BilibiliHistoryEntity;
import com.mundane.mail.entity.RedBookCollectEntity;
import com.mundane.mail.mapper.BilibiliHistoryMapper;
import com.mundane.mail.pojo.BilibiliHistoryResult;
import com.mundane.mail.pojo.RedBookCollectResult;
import com.mundane.mail.utils.RedBookUtils;
import com.mundane.mail.vo.BilibiliHistoryRequestVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class BilibiliHistoryService {

    @Autowired
    private BilibiliHistoryMapper bilibiliHistoryMapper;
    public void save(BilibiliHistoryRequestVo request) {
        log.info("userId = {}", request.getUserId());
        if (StringUtils.isEmpty(request.getUserId())) {
            throw new RuntimeException("userId不能为空");
        }
        BilibiliHistoryResult result = JSONUtil.toBean(request.getResult(), BilibiliHistoryResult.class);
        List<BilibiliHistoryEntity> entityList = new ArrayList<>();
        List<BilibiliHistoryResult.DataDTO.ListDTO> list = result.getData().getList();
        Date now = new Date();
        for (BilibiliHistoryResult.DataDTO.ListDTO item : list) {
            BilibiliHistoryEntity entity = new BilibiliHistoryEntity();
            entity.setUserId(request.getUserId());
            entity.setTitle(item.getTitle());
            entity.setBvid(item.getHistory().getBvid());
            entity.setCover(item.getCover());
            entity.setAuthorName(item.getAuthorName());
            entity.setAuthorMid(String.valueOf(item.getAuthorMid()));
            entity.setProgress(item.getProgress());
            entity.setIsFav(item.getIsFav());
            entity.setIsFinish(item.getIsFinish());
            entity.setVideos(item.getVideos());
            entity.setLiveStatus(item.getLiveStatus());
            entity.setTagName(item.getTagName());
            entity.setViewAt(item.getViewAt());
            entity.setCreateTime(now);
            entity.setUpdateTime(now);
            entityList.add(entity);
        }
        bilibiliHistoryMapper.batchAddOrUpdate(entityList);
    }

    public PageInfo queryByUserId(String userId, String title, String authorName, Integer pageNum, Integer pageSize) {
        if (StringUtils.isEmpty(userId)) {
            log.error("userId不能为空");
            throw new RuntimeException("userId不能为空");
        }
        PageHelper.startPage(pageNum, pageSize);
        List<BilibiliHistoryEntity> notes = bilibiliHistoryMapper.queryByUserId(userId, title, authorName);
        PageInfo<BilibiliHistoryEntity> info = new PageInfo<>(notes);
        return info;
    }
}
