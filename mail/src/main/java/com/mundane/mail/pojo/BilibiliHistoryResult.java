package com.mundane.mail.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BilibiliHistoryResult {


    @JsonProperty("code")
    private Integer code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("ttl")
    private Integer ttl;
    @JsonProperty("data")
    private DataDTO data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("cursor")
        private CursorDTO cursor;
        @JsonProperty("tab")
        private List<TabDTO> tab;
        @JsonProperty("list")
        private List<ListDTO> list;

        @NoArgsConstructor
        @Data
        public static class CursorDTO {
            @JsonProperty("max")
            private Integer max;
            @JsonProperty("view_at")
            private Integer viewAt;
            @JsonProperty("business")
            private String business;
            @JsonProperty("ps")
            private Integer ps;
        }

        @NoArgsConstructor
        @Data
        public static class TabDTO {
            @JsonProperty("type")
            private String type;
            @JsonProperty("name")
            private String name;
        }

        @NoArgsConstructor
        @Data
        public static class ListDTO {
            @JsonProperty("title")
            private String title;
            @JsonProperty("long_title")
            private String longTitle;
            @JsonProperty("cover")
            private String cover;
            @JsonProperty("covers")
            private Object covers;
            @JsonProperty("uri")
            private String uri;
            @JsonProperty("history")
            private HistoryDTO history;
            @JsonProperty("videos")
            private Integer videos;
            @JsonProperty("author_name")
            private String authorName;
            @JsonProperty("author_face")
            private String authorFace;
            @JsonProperty("author_mid")
            private Integer authorMid;
            @JsonProperty("view_at")
            private Long viewAt;
            @JsonProperty("progress")
            private Integer progress;
            @JsonProperty("badge")
            private String badge;
            @JsonProperty("show_title")
            private String showTitle;
            @JsonProperty("duration")
            private Integer duration;
            @JsonProperty("current")
            private String current;
            @JsonProperty("total")
            private Integer total;
            @JsonProperty("new_desc")
            private String newDesc;
            @JsonProperty("is_finish")
            private Integer isFinish;
            @JsonProperty("is_fav")
            private Integer isFav;
            @JsonProperty("kid")
            private Integer kid;
            @JsonProperty("tag_name")
            private String tagName;
            @JsonProperty("live_status")
            private Integer liveStatus;

            @NoArgsConstructor
            @Data
            public static class HistoryDTO {
                @JsonProperty("oid")
                private Integer oid;
                @JsonProperty("epid")
                private Integer epid;
                @JsonProperty("bvid")
                private String bvid;
                @JsonProperty("page")
                private Integer page;
                @JsonProperty("cid")
                private Integer cid;
                @JsonProperty("part")
                private String part;
                @JsonProperty("business")
                private String business;
                @JsonProperty("dt")
                private Integer dt;
            }
        }
    }
}
