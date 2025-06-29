package com.mundane.mail.mapper;

import com.mundane.mail.common.BaseMapper;
import com.mundane.mail.entity.RedBookTagEntity;

import java.util.List;

public interface RedBookTagMapper extends BaseMapper<RedBookTagEntity> {

    void batchAddOrUpdate(List<RedBookTagEntity> list);
}
