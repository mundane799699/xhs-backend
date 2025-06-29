package com.mundane.mail.mapper;

import com.mundane.mail.common.BaseMapper;
import com.mundane.mail.entity.WxUserEntity;
import org.apache.ibatis.annotations.Param;

public interface WxUserMapper extends BaseMapper<WxUserEntity> {

    WxUserEntity findByOpenId(@Param("openId") String openId);
}
