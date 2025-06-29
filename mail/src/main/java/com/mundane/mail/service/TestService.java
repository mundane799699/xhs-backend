package com.mundane.mail.service;

import cn.hutool.json.JSONUtil;
import com.mundane.mail.dto.RedBookImageDto;
import com.mundane.mail.entity.RedBookNoteEntity;
import com.mundane.mail.mapper.RedBookNoteMapper;
import com.mundane.mail.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    private RedBookNoteMapper redBookNoteMapper;
    public void init() {
        List<RedBookNoteEntity> list = redBookNoteMapper.selectPicPath();
        for (RedBookNoteEntity entity : list) {

            String picPathListStr = entity.getPicPathList();
            if (StringUtils.isNotEmpty(picPathListStr)) {
                List<RedBookImageDto> picPathList = JsonUtils.toList(picPathListStr, RedBookImageDto.class);
                for (RedBookImageDto dto : picPathList) {
                    String localPath = dto.getLocalPath();
                    dto.setLocalPath(localPath.replace("springboot", "xhs-res"));
                }
                entity.setPicPathList(JSONUtil.toJsonStr(picPathList));
                redBookNoteMapper.updatePicPath(entity);
            }
        }

    }
}
