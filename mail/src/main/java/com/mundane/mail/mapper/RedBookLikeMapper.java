package com.mundane.mail.mapper;

import com.mundane.mail.common.BaseMapper;
import com.mundane.mail.dto.RedBookLikeDto;
import com.mundane.mail.entity.RedBookCollectEntity;
import com.mundane.mail.entity.RedBookLikeEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RedBookLikeMapper extends BaseMapper<RedBookLikeEntity> {

    void batchAddOrUpdate(List<RedBookLikeEntity> list);

    List<RedBookLikeDto> queryByUserId(@Param("userId") String userId,
                                       @Param("displayTitle") String displayTitle,
                                       @Param("ownerNickname") String ownerNickname);

    List<RedBookLikeEntity> queryAllByUserId(@Param("userId") String userId);

    void deleteByUserId(@Param("userId") String userId);
}
