package com.mundane.mail.mapper;

import com.mundane.mail.common.BaseMapper;
import com.mundane.mail.entity.RedBookNoteEntity;

import java.util.List;

public interface RedBookNoteMapper extends BaseMapper<RedBookNoteEntity> {
    void addOrUpdate(RedBookNoteEntity item);

    List<RedBookNoteEntity> selectPicPath();

    void updatePicPath(RedBookNoteEntity item);
}
