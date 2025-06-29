package com.mundane.mail.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "tb_redbook_tag")
public class RedBookTagEntity {
    @Id
    private String id;
    private String name;
    private String type;
    private Date createTime;
    private Date updateTime;
}
