package com.mundane.mail.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "tb_redbook_note")
public class RedBookNoteEntity {

    @Id
    private String noteId;
    private String authorId;
    private String authorName;
    private String title;
    private String type;
    private String noteDesc;
    private String tags;
    private Integer likedCount;
    private Integer collectedCount;
    private Integer commentCount;
    private Date createTime;
    private Date updateTime;
    // 0:有效 1：失效
    private Integer status;
    private String picPathList;
}
