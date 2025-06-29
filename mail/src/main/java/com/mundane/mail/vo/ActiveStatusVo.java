package com.mundane.mail.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveStatusVo {

    private Integer status;

    private String endDate;

    private String userId;


}
