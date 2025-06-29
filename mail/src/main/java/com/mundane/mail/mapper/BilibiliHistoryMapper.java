package com.mundane.mail.mapper;

import com.mundane.mail.common.BaseMapper;
import com.mundane.mail.entity.BilibiliHistoryEntity;
import com.mundane.mail.entity.RedBookCollectEntity;

import java.util.List;

public interface BilibiliHistoryMapper extends BaseMapper<BilibiliHistoryEntity> {

    void batchAddOrUpdate(List<BilibiliHistoryEntity> list);

    List<BilibiliHistoryEntity> queryByUserId(String userId, String title, String authorName);
}
