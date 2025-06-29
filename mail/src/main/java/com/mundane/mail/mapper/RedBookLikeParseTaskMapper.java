package com.mundane.mail.mapper;

import com.mundane.mail.common.BaseMapper;
import com.mundane.mail.entity.RedBookLikeParseTaskEntity;

public interface RedBookLikeParseTaskMapper extends BaseMapper<RedBookLikeParseTaskEntity> {
    void addOrUpdate(RedBookLikeParseTaskEntity entity);
}
