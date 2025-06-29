package com.mundane.mail.service;

import com.mundane.mail.entity.RedBookNoteImgEntity;
import com.mundane.mail.mapper.RedBookNoteImgMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class RedBookNoteImgService {

    @Autowired
    private RedBookNoteImgMapper redBookNoteImgMapper;


    @Transactional(rollbackFor = Exception.class)
    public void saveImgList(List<RedBookNoteImgEntity> list) {
        redBookNoteImgMapper.batchAddOrUpdate(list);
    }

    
    @Transactional(rollbackFor = Exception.class)
    public void deleteByNoteId(String noteId) {
        redBookNoteImgMapper.deleteByNoteId(noteId);
    }
}
