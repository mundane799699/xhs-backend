package com.mundane.mail.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientStatusVo {
    private Integer status;
    private String endDate;

    private String userId;
}
