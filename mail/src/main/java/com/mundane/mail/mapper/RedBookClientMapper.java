package com.mundane.mail.mapper;

import com.mundane.mail.common.BaseMapper;
import com.mundane.mail.entity.RedBookClientEntity;
import com.mundane.mail.entity.RedBookOrderEntity;

public interface RedBookClientMapper extends BaseMapper<RedBookClientEntity> {


    RedBookClientEntity selectByClientId(String clientId, String userId);

    RedBookClientEntity selectByOutTradeNo(String outTradeNo);
}
