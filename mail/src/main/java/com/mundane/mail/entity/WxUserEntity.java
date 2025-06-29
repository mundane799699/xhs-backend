package com.mundane.mail.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "tb_wx_user")
public class WxUserEntity {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String name;
    private String email;
    private String sessionKey;
    private String openid;
    private String unionid;
    private String avatar;
    private Integer gender;
    private String country;
    private String province;
    private String city;
    private Date createTime;
    private Date updateTime;
}
