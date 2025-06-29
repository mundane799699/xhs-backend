package com.mundane.mail.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "tb_redbook_like_parse_task")
public class RedBookLikeParseTaskEntity {

    @Id
    private String userId;
    // 0:未开始 1：进行中 2:已完成
    private Integer status;
    private String progress;
    private Date createTime;
    private Date updateTime;
}
