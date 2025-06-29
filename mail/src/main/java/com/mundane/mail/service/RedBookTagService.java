package com.mundane.mail.service;

import com.mundane.mail.entity.RedBookTagEntity;
import com.mundane.mail.mapper.RedBookTagMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class RedBookTagService {
    @Autowired
    private RedBookTagMapper redBookNoteTagMapper;

    
    public void saveTagList(List<RedBookTagEntity> list) {
        redBookNoteTagMapper.batchAddOrUpdate(list);
    }
}
