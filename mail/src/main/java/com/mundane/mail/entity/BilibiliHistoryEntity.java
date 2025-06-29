package com.mundane.mail.entity;

import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "tb_bilibili_history")
public class BilibiliHistoryEntity {
    private String userId;
    private String bvid;
    private String title;
    private String cover;
    private String authorName;
    private String authorMid;
    private Integer progress;
    private Integer isFinish;
    private Integer isFav;
    private Integer videos;
    private Integer liveStatus;
    private String tagName;
    private Long viewAt;
    private Date createTime;
    private Date updateTime;
}
