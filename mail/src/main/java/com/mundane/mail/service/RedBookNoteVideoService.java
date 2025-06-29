package com.mundane.mail.service;

import com.mundane.mail.entity.RedBookNoteVideoEntity;
import com.mundane.mail.mapper.RedBookNoteVideoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class RedBookNoteVideoService {

    @Autowired
    private RedBookNoteVideoMapper redBookNoteVideoMapper;

    
    public void save(RedBookNoteVideoEntity entity) {
        redBookNoteVideoMapper.addOrUpdate(entity);
    }
}
