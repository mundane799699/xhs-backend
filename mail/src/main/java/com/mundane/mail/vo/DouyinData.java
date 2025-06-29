package com.mundane.mail.vo;

import lombok.Data;

import java.util.List;

@Data
public class DouyinData {
    private String videoUrl;
    private String cover;
    private String title;
    private List<String> imageList;
    private String type;
}
