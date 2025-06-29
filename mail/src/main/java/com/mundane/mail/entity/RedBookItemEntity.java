package com.mundane.mail.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "tb_redbook_item")
public class RedBookItemEntity {
    @Id
    private Integer id;
    private String name;
    private BigDecimal discountPrice;
    private BigDecimal originalPrice;
    private Integer status;
    private Integer days;
    private String membershipType;
}
