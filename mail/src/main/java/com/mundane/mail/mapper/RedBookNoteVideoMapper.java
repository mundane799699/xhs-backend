package com.mundane.mail.mapper;

import com.mundane.mail.common.BaseMapper;
import com.mundane.mail.entity.RedBookNoteVideoEntity;


public interface RedBookNoteVideoMapper extends BaseMapper<RedBookNoteVideoEntity> {

    void addOrUpdate(RedBookNoteVideoEntity item);
}
