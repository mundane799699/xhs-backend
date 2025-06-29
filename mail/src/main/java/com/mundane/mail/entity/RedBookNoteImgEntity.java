package com.mundane.mail.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "tb_redbook_note_img")
public class RedBookNoteImgEntity {
    @Id
    private String url;
    private String fileId;
    private String noteId;
    // 图片在笔记中的顺序
    private Integer imgIndex;
    private Integer height;
    private Integer width;
    private Date createTime;
    private Date updateTime;
}
