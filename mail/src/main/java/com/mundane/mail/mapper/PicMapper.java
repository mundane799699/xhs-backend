package com.mundane.mail.mapper;

import com.mundane.mail.common.BaseIdListMapper;
import com.mundane.mail.entity.Picture;
import org.apache.ibatis.annotations.Param;

public interface PicMapper extends BaseIdListMapper<Picture> {

    Picture findById(@Param("id") Integer id);
}
