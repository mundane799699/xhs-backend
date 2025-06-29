package com.mundane.mail.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Table(name = "tb_redbook_active_code")
public class RedBookActiveCodeEntity {
    @Id
    private String activeCode;
    // 0、未使用 1、已购买未使用 2、已使用 3、已失效
    private Integer status;
    private Integer itemType;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;
}
