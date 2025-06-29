package com.mundane.mail.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "tb_redbook_note_video")
public class RedBookNoteVideoEntity {
    @Id
    private String videoId;
    private String noteId;
    private String masterUrl;
    private Integer duration;
    private String backupUrls;
    private Date createTime;
    private Date updateTime;
}
