package com.mundane.mail.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class RedBookNoteParseDetailResult {


    private ShareInfoDTO shareInfo;
    private InteractInfoDTO interactInfo;
    private String noteId;
    private String title;
    private String type;
    private List<TagListDTO> tagList;
    private List<?> atUserList;
    private Long time;
    private UserDTO user;
    private List<ImageListDTO> imageList;
    private Video video;
    private String desc;
    private Long lastUpdateTime;

    @NoArgsConstructor
    @Data
    public static class Video {

        private ImageDTO image;
        private CapaDTO capa;
        private MediaDTO media;
        private ConsumerDTO consumer;

        @NoArgsConstructor
        @Data
        public static class ImageDTO {
            private String firstFrameFileid;
            private String thumbnailFileid;
        }

        @NoArgsConstructor
        @Data
        public static class CapaDTO {
            private Integer duration;
        }

        @NoArgsConstructor
        @Data
        public static class MediaDTO {
            private String videoId;
            private MediaDTO.VideoDTO video;
            private MediaDTO.StreamDTO stream;

            @NoArgsConstructor
            @Data
            public static class VideoDTO {
                private Integer drmType;
                private Integer hdrType;
                private Integer duration;
                private List<Integer> streamTypes;
                private Integer bizName;
                private String bizId;
                private String md5;
            }

            @NoArgsConstructor
            @Data
            public static class StreamDTO {
                private List<H264DTO> h264;
                private List<?> h265;
                private List<?> av1;

                @NoArgsConstructor
                @Data
                public static class H264DTO {
                    private Integer rotate;
                    private Integer hdrType;
                    private Integer duration;
                    private Integer videoBitrate;
                    private List<String> backupUrls;
                    private Integer streamType;
                    private Integer audioDuration;
                    private Integer height;
                    private String videoCodec;
                    private Integer avgBitrate;
                    private Integer fps;
                    private String format;
                    private Integer weight;
                    private Integer defaultStream;
                    private Integer vmaf;
                    private String audioCodec;
                    private Integer audioBitrate;
                    private Integer volume;
                    private Integer ssim;
                    private String streamDesc;
                    private Integer psnr;
                    private Integer videoDuration;
                    private Integer audioChannels;
                    private Integer size;
                    private String masterUrl;
                    private Integer width;
                    private String qualityType;
                }
            }
        }

        @NoArgsConstructor
        @Data
        public static class ConsumerDTO {
            private String originVideoKey;
        }
    }

    @NoArgsConstructor
    @Data
    public static class ShareInfoDTO {
        private Boolean unShare;
    }

    @NoArgsConstructor
    @Data
    public static class InteractInfoDTO {
        private Boolean collected;
        private String likedCount;
        private Boolean followed;
        private Boolean liked;
        private String relation;
        private String commentCount;
        private String shareCount;
        private String collectedCount;
    }

    @NoArgsConstructor
    @Data
    public static class UserDTO {
        private String avatar;
        private String userId;
        private String nickname;
    }

    @NoArgsConstructor
    @Data
    public static class TagListDTO {
        private String type;
        private String name;
        private String id;
    }

    @NoArgsConstructor
    @Data
    public static class ImageListDTO {
        private String traceId;
        private String urlDefault;
        private String urlPre;
        private String url;
        private Integer width;
        private List<InfoListDTO> infoList;
        private String fileId;
        private Integer height;

        @NoArgsConstructor
        @Data
        public static class InfoListDTO {
            private String imageScene;
            private String url;
        }
    }
}
