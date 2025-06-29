package com.mundane.mail.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedBookNoteMediaInfo {

    private String title;

    private List<String> imageUrls;

    private String videoUrl;
}
