package com.mundane.mail.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Table(name = "tb_redbook_order")
public class RedBookOrderEntity {
    @Id
    private String outTradeNo;
    private String orderNo;
    private String payNo;
    private String clientId;
    // tb_redbook_client表中的id
    private String userId;

    private Long userIdNew;
    private BigDecimal totalPay;
    // 商品类型
    private Integer itemType;
    // 0、未付款 1、已付款
    private Integer status;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;
}
