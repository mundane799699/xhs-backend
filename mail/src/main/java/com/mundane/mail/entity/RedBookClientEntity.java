package com.mundane.mail.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "tb_redbook_client")
public class RedBookClientEntity {
    @Id
    private String id;
    private String outTradeNo;
    private String activeCode;
    // 支付类型: 1、微信支付 2、激活码支付
    private Integer payType;
    private String clientId;
    // 0、失效 1、有效
    private Integer status;
    private String endDate;
    private Date updateTime;
    private Date createTime;
}
