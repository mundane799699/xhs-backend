package com.mundane.mail.mapper;

import com.mundane.mail.common.BaseMapper;
import com.mundane.mail.entity.RedBookCollectParseTaskEntity;

public interface RedBookCollectParseTaskMapper extends BaseMapper<RedBookCollectParseTaskEntity> {
    void addOrUpdate(RedBookCollectParseTaskEntity entity);
}
