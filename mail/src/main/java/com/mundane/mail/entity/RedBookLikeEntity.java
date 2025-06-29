package com.mundane.mail.entity;

import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "tb_redbook_like")
public class RedBookLikeEntity {
    private String userId;
    private String noteId;
    private String displayTitle;
    private String type;
    private String coverUrl;
    private String ownerId;
    private String ownerNickname;
    private String ownerAvatar;
    private Boolean liked;
    private String likedCount;
    private Date createTime;
    private Date updateTime;
    private Date createDate;
}
