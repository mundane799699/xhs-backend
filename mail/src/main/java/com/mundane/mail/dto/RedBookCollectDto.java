package com.mundane.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedBookCollectDto {
    private String userId;
    private String noteId;
    private String displayTitle;
    private String type;
    private String coverUrl;
    private String ownerId;
    private String ownerNickname;
    private String ownerAvatar;
    private Boolean liked;
    private Integer likedCount;

    private String tagList;
    private String detailId;
    private String noteDesc;
    private String tags;
    private Integer status;
    private String picPathList;
}
