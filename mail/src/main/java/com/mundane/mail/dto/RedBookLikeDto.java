package com.mundane.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedBookLikeDto {
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

    private String tagList;
    private String detailId;
    private String noteDesc;
    private String tags;
    private Integer status;
}
