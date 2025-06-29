package com.mundane.mail.mapper;

import com.mundane.mail.common.BaseMapper;
import com.mundane.mail.entity.RedBookNoteImgEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RedBookNoteImgMapper extends BaseMapper<RedBookNoteImgEntity> {

    void batchAddOrUpdate(List<RedBookNoteImgEntity> list);

    void deleteByNoteId(@Param("noteId") String noteId);

    List<RedBookNoteImgEntity> selectByNoteId(@Param("noteId") String noteId);
}
