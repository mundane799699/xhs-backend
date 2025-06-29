package com.mundane.mail.entity;

import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "tb_redbook_collect")
public class RedBookCollectEntity {
    private String userId;
    private String noteId;
    private String xsecToken;
    private String displayTitle;
    private String type;
    private String coverUrl;
    private String ownerId;
    private String ownerNickname;
    private String ownerAvatar;
    private Boolean liked;
    private String likedCount;
    private Integer patchIndex;
    private Integer collectIndex;
    private Date createTime;
    private Date updateTime;
    private Date createDate;
}
