package com.mundane.mail.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



@NoArgsConstructor
@AllArgsConstructor
@Data
public class RedBookNoteResult {


    /**
     * code : 0
     * success : true
     * msg : 成功
     * data : {"cursor_score":"","items":[{"id":"64db49dc000000000302290d","model_type":"note","note_card":{"interact_info":{"liked":false,"liked_count":"3","collected":false,"collected_count":"1","comment_count":"2","share_count":"0","followed":false},"share_info":{"un_share":false},"note_id":"64db49dc000000000302290d","title":"💥💡海鲜市场的卖家神器","image_list":[{"file_id":"85e608b7-b761-7b35-a87a-9c423098617f","height":2560,"width":1920,"url":"https://sns-img-hw.xhscdn.com/85e608b7-b761-7b35-a87a-9c423098617f","trace_id":"1040g00830npr0asf085g5ns2fqgg8uqrj02tlno"}],"tag_list":[{"id":"5c524df4000000001c025b44","name":"搞钱","type":"topic"},{"id":"5bea7a739ef9730001934b7f","name":"副业","type":"topic"},{"name":"海鲜市场","type":"topic","id":"5deb9eaa00000000010079a4"},{"id":"5333ba07b4c4d64e7c65bd8c","name":"二手闲置","type":"topic"},{"id":"5bf52cadd315850001a0a204","name":"闲鱼","type":"topic"},{"type":"topic","id":"5322f42db4c4d65b568f09af","name":"闲置"},{"name":"实用APP安利","type":"topic","id":"58fad446f5a263395c15f69c"},{"id":"6219c693000000000101cd3c","name":"消息提醒","type":"topic"},{"type":"topic","id":"625628150000000001008ba2","name":"手机通知"},{"type":"topic","id":"5be3ab0065a680000120a5ee","name":"手机app"},{"id":"5eb620b50000000001000d31","name":"咸鱼奇葩卖家","type":"topic"},{"id":"61190909000000000101d981","name":"闲鱼神仙卖家","type":"topic"},{"type":"topic","id":"5cdd851e000000000e0119ea","name":"实用工具"},{"type":"topic","id":"5c1667820000000003013b14","name":"卖家"}],"last_update_time":1692092892000,"ip_location":"浙江","type":"video","desc":"🍀今天我要种草的是朋友开发的一款\u201c海鲜市场\u201d的通知工具，是一个安卓app。朋友是一个安卓程序员，在他自己做二手副业的过程中，他总是因为手机系统自带的通知消息提醒很弱，经常错过消息，导致买家去别的店铺成交。尤其是对一些个人卖家而言。\n\t\n😄而这个app则可以在海鲜市场发来通知的时候，发出自定义的音乐和震动，效果可以看视频演示。这样就可以让你迅速的时间内得知是\u201c海鲜市场\u201d的消息，然后去处理。\n\t\n⏳这个软件是他花了时间开发的，是付费的，但是不贵。有需要的朋友可以买。没有需要只是有点感兴趣的就不用去问了。\n\t\n👉朋友主页@Choshim\n\t\n             ","user":{"user_id":"5f827ea10000000001007b5b","nickname":"理想是自由","avatar":"https://sns-avatar-qc.xhscdn.com/avatar/5f827ea10000000001007b5b.jpg"},"video":{"consumer":{"origin_video_key":"pre_post/1040g0cg30npppjff044g5ns2fqgg8uqr2386ee8"},"media":{"video":{"md5":"beb4f3e2c0ff010073155e5d2677ffd2","hdr_type":0,"drm_type":0,"stream_types":[258],"biz_name":110,"biz_id":"280590187074824461","duration":30},"stream":{"h265":[],"av1":[],"h264":[{"vmaf":-1,"quality_type":"HD","default_stream":0,"height":1564,"video_codec":"h264","audio_duration":29792,"audio_channels":2,"hdr_type":0,"width":720,"audio_bitrate":56041,"rotate":0,"backup_urls":["http://sns-video-bd.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4","http://sns-video-qc.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4?sign=4cc9b2422b380348dcee13a7b891d406&t=64e23754","http://sns-video-al.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4","http://sns-video-hw.xhscdn.net/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4"],"ssim":0,"psnr":0,"stream_type":258,"avg_bitrate":207178,"video_bitrate":144371,"video_duration":29833,"size":772620,"volume":0,"audio_codec":"aac","weight":62,"format":"mp4","duration":29834,"master_url":"http://sns-video-hw.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4","stream_desc":"X264_MP4","fps":30}]},"video_id":136474990905740827},"image":{"first_frame_fileid":"110/0/01e4db47f9a94e1b00100000000189f898890f_0.jpg","thumbnail_fileid":"110/0/01e4db47f9a94e1b00100000000189f8988a9a_0.webp"},"capa":{"duration":29}},"time":1692092892000,"at_user_list":[{"user_id":"5b4959f7e8ac2b38b09c20f7","nickname":"Choshim"}]}}],"current_time":1692451637355}
     */

    private Integer code;
    private Boolean success;
    private String msg;
    private DataBean data;

    @NoArgsConstructor
    @Data
    public static class DataBean {
        /**
         * cursor_score :
         * items : [{"id":"64db49dc000000000302290d","model_type":"note","note_card":{"interact_info":{"liked":false,"liked_count":"3","collected":false,"collected_count":"1","comment_count":"2","share_count":"0","followed":false},"share_info":{"un_share":false},"note_id":"64db49dc000000000302290d","title":"💥💡海鲜市场的卖家神器","image_list":[{"file_id":"85e608b7-b761-7b35-a87a-9c423098617f","height":2560,"width":1920,"url":"https://sns-img-hw.xhscdn.com/85e608b7-b761-7b35-a87a-9c423098617f","trace_id":"1040g00830npr0asf085g5ns2fqgg8uqrj02tlno"}],"tag_list":[{"id":"5c524df4000000001c025b44","name":"搞钱","type":"topic"},{"id":"5bea7a739ef9730001934b7f","name":"副业","type":"topic"},{"name":"海鲜市场","type":"topic","id":"5deb9eaa00000000010079a4"},{"id":"5333ba07b4c4d64e7c65bd8c","name":"二手闲置","type":"topic"},{"id":"5bf52cadd315850001a0a204","name":"闲鱼","type":"topic"},{"type":"topic","id":"5322f42db4c4d65b568f09af","name":"闲置"},{"name":"实用APP安利","type":"topic","id":"58fad446f5a263395c15f69c"},{"id":"6219c693000000000101cd3c","name":"消息提醒","type":"topic"},{"type":"topic","id":"625628150000000001008ba2","name":"手机通知"},{"type":"topic","id":"5be3ab0065a680000120a5ee","name":"手机app"},{"id":"5eb620b50000000001000d31","name":"咸鱼奇葩卖家","type":"topic"},{"id":"61190909000000000101d981","name":"闲鱼神仙卖家","type":"topic"},{"type":"topic","id":"5cdd851e000000000e0119ea","name":"实用工具"},{"type":"topic","id":"5c1667820000000003013b14","name":"卖家"}],"last_update_time":1692092892000,"ip_location":"浙江","type":"video","desc":"🍀今天我要种草的是朋友开发的一款\u201c海鲜市场\u201d的通知工具，是一个安卓app。朋友是一个安卓程序员，在他自己做二手副业的过程中，他总是因为手机系统自带的通知消息提醒很弱，经常错过消息，导致买家去别的店铺成交。尤其是对一些个人卖家而言。\n\t\n😄而这个app则可以在海鲜市场发来通知的时候，发出自定义的音乐和震动，效果可以看视频演示。这样就可以让你迅速的时间内得知是\u201c海鲜市场\u201d的消息，然后去处理。\n\t\n⏳这个软件是他花了时间开发的，是付费的，但是不贵。有需要的朋友可以买。没有需要只是有点感兴趣的就不用去问了。\n\t\n👉朋友主页@Choshim\n\t\n             ","user":{"user_id":"5f827ea10000000001007b5b","nickname":"理想是自由","avatar":"https://sns-avatar-qc.xhscdn.com/avatar/5f827ea10000000001007b5b.jpg"},"video":{"consumer":{"origin_video_key":"pre_post/1040g0cg30npppjff044g5ns2fqgg8uqr2386ee8"},"media":{"video":{"md5":"beb4f3e2c0ff010073155e5d2677ffd2","hdr_type":0,"drm_type":0,"stream_types":[258],"biz_name":110,"biz_id":"280590187074824461","duration":30},"stream":{"h265":[],"av1":[],"h264":[{"vmaf":-1,"quality_type":"HD","default_stream":0,"height":1564,"video_codec":"h264","audio_duration":29792,"audio_channels":2,"hdr_type":0,"width":720,"audio_bitrate":56041,"rotate":0,"backup_urls":["http://sns-video-bd.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4","http://sns-video-qc.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4?sign=4cc9b2422b380348dcee13a7b891d406&t=64e23754","http://sns-video-al.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4","http://sns-video-hw.xhscdn.net/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4"],"ssim":0,"psnr":0,"stream_type":258,"avg_bitrate":207178,"video_bitrate":144371,"video_duration":29833,"size":772620,"volume":0,"audio_codec":"aac","weight":62,"format":"mp4","duration":29834,"master_url":"http://sns-video-hw.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4","stream_desc":"X264_MP4","fps":30}]},"video_id":136474990905740827},"image":{"first_frame_fileid":"110/0/01e4db47f9a94e1b00100000000189f898890f_0.jpg","thumbnail_fileid":"110/0/01e4db47f9a94e1b00100000000189f8988a9a_0.webp"},"capa":{"duration":29}},"time":1692092892000,"at_user_list":[{"user_id":"5b4959f7e8ac2b38b09c20f7","nickname":"Choshim"}]}}]
         * current_time : 1692451637355
         */

        private String cursorScore;
        private Long currentTime;
        private List<ItemsBean> items;

        @NoArgsConstructor
        @Data
        public static class ItemsBean {
            /**
             * id : 64db49dc000000000302290d
             * model_type : note
             * note_card : {"interact_info":{"liked":false,"liked_count":"3","collected":false,"collected_count":"1","comment_count":"2","share_count":"0","followed":false},"share_info":{"un_share":false},"note_id":"64db49dc000000000302290d","title":"💥💡海鲜市场的卖家神器","image_list":[{"file_id":"85e608b7-b761-7b35-a87a-9c423098617f","height":2560,"width":1920,"url":"https://sns-img-hw.xhscdn.com/85e608b7-b761-7b35-a87a-9c423098617f","trace_id":"1040g00830npr0asf085g5ns2fqgg8uqrj02tlno"}],"tag_list":[{"id":"5c524df4000000001c025b44","name":"搞钱","type":"topic"},{"id":"5bea7a739ef9730001934b7f","name":"副业","type":"topic"},{"name":"海鲜市场","type":"topic","id":"5deb9eaa00000000010079a4"},{"id":"5333ba07b4c4d64e7c65bd8c","name":"二手闲置","type":"topic"},{"id":"5bf52cadd315850001a0a204","name":"闲鱼","type":"topic"},{"type":"topic","id":"5322f42db4c4d65b568f09af","name":"闲置"},{"name":"实用APP安利","type":"topic","id":"58fad446f5a263395c15f69c"},{"id":"6219c693000000000101cd3c","name":"消息提醒","type":"topic"},{"type":"topic","id":"625628150000000001008ba2","name":"手机通知"},{"type":"topic","id":"5be3ab0065a680000120a5ee","name":"手机app"},{"id":"5eb620b50000000001000d31","name":"咸鱼奇葩卖家","type":"topic"},{"id":"61190909000000000101d981","name":"闲鱼神仙卖家","type":"topic"},{"type":"topic","id":"5cdd851e000000000e0119ea","name":"实用工具"},{"type":"topic","id":"5c1667820000000003013b14","name":"卖家"}],"last_update_time":1692092892000,"ip_location":"浙江","type":"video","desc":"🍀今天我要种草的是朋友开发的一款\u201c海鲜市场\u201d的通知工具，是一个安卓app。朋友是一个安卓程序员，在他自己做二手副业的过程中，他总是因为手机系统自带的通知消息提醒很弱，经常错过消息，导致买家去别的店铺成交。尤其是对一些个人卖家而言。\n\t\n😄而这个app则可以在海鲜市场发来通知的时候，发出自定义的音乐和震动，效果可以看视频演示。这样就可以让你迅速的时间内得知是\u201c海鲜市场\u201d的消息，然后去处理。\n\t\n⏳这个软件是他花了时间开发的，是付费的，但是不贵。有需要的朋友可以买。没有需要只是有点感兴趣的就不用去问了。\n\t\n👉朋友主页@Choshim\n\t\n             ","user":{"user_id":"5f827ea10000000001007b5b","nickname":"理想是自由","avatar":"https://sns-avatar-qc.xhscdn.com/avatar/5f827ea10000000001007b5b.jpg"},"video":{"consumer":{"origin_video_key":"pre_post/1040g0cg30npppjff044g5ns2fqgg8uqr2386ee8"},"media":{"video":{"md5":"beb4f3e2c0ff010073155e5d2677ffd2","hdr_type":0,"drm_type":0,"stream_types":[258],"biz_name":110,"biz_id":"280590187074824461","duration":30},"stream":{"h265":[],"av1":[],"h264":[{"vmaf":-1,"quality_type":"HD","default_stream":0,"height":1564,"video_codec":"h264","audio_duration":29792,"audio_channels":2,"hdr_type":0,"width":720,"audio_bitrate":56041,"rotate":0,"backup_urls":["http://sns-video-bd.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4","http://sns-video-qc.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4?sign=4cc9b2422b380348dcee13a7b891d406&t=64e23754","http://sns-video-al.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4","http://sns-video-hw.xhscdn.net/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4"],"ssim":0,"psnr":0,"stream_type":258,"avg_bitrate":207178,"video_bitrate":144371,"video_duration":29833,"size":772620,"volume":0,"audio_codec":"aac","weight":62,"format":"mp4","duration":29834,"master_url":"http://sns-video-hw.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4","stream_desc":"X264_MP4","fps":30}]},"video_id":136474990905740827},"image":{"first_frame_fileid":"110/0/01e4db47f9a94e1b00100000000189f898890f_0.jpg","thumbnail_fileid":"110/0/01e4db47f9a94e1b00100000000189f8988a9a_0.webp"},"capa":{"duration":29}},"time":1692092892000,"at_user_list":[{"user_id":"5b4959f7e8ac2b38b09c20f7","nickname":"Choshim"}]}
             */

            private String id;
            private String modelType;
            private NoteCardBean noteCard;

            @NoArgsConstructor
            @Data
            public static class NoteCardBean {
                /**
                 * interact_info : {"liked":false,"liked_count":"3","collected":false,"collected_count":"1","comment_count":"2","share_count":"0","followed":false}
                 * share_info : {"un_share":false}
                 * note_id : 64db49dc000000000302290d
                 * title : 💥💡海鲜市场的卖家神器
                 * image_list : [{"file_id":"85e608b7-b761-7b35-a87a-9c423098617f","height":2560,"width":1920,"url":"https://sns-img-hw.xhscdn.com/85e608b7-b761-7b35-a87a-9c423098617f","trace_id":"1040g00830npr0asf085g5ns2fqgg8uqrj02tlno"}]
                 * tag_list : [{"id":"5c524df4000000001c025b44","name":"搞钱","type":"topic"},{"id":"5bea7a739ef9730001934b7f","name":"副业","type":"topic"},{"name":"海鲜市场","type":"topic","id":"5deb9eaa00000000010079a4"},{"id":"5333ba07b4c4d64e7c65bd8c","name":"二手闲置","type":"topic"},{"id":"5bf52cadd315850001a0a204","name":"闲鱼","type":"topic"},{"type":"topic","id":"5322f42db4c4d65b568f09af","name":"闲置"},{"name":"实用APP安利","type":"topic","id":"58fad446f5a263395c15f69c"},{"id":"6219c693000000000101cd3c","name":"消息提醒","type":"topic"},{"type":"topic","id":"625628150000000001008ba2","name":"手机通知"},{"type":"topic","id":"5be3ab0065a680000120a5ee","name":"手机app"},{"id":"5eb620b50000000001000d31","name":"咸鱼奇葩卖家","type":"topic"},{"id":"61190909000000000101d981","name":"闲鱼神仙卖家","type":"topic"},{"type":"topic","id":"5cdd851e000000000e0119ea","name":"实用工具"},{"type":"topic","id":"5c1667820000000003013b14","name":"卖家"}]
                 * last_update_time : 1692092892000
                 * ip_location : 浙江
                 * type : video
                 * desc : 🍀今天我要种草的是朋友开发的一款“海鲜市场”的通知工具，是一个安卓app。朋友是一个安卓程序员，在他自己做二手副业的过程中，他总是因为手机系统自带的通知消息提醒很弱，经常错过消息，导致买家去别的店铺成交。尤其是对一些个人卖家而言。

                 😄而这个app则可以在海鲜市场发来通知的时候，发出自定义的音乐和震动，效果可以看视频演示。这样就可以让你迅速的时间内得知是“海鲜市场”的消息，然后去处理。

                 ⏳这个软件是他花了时间开发的，是付费的，但是不贵。有需要的朋友可以买。没有需要只是有点感兴趣的就不用去问了。

                 👉朋友主页@Choshim
                 * user : {"user_id":"5f827ea10000000001007b5b","nickname":"理想是自由","avatar":"https://sns-avatar-qc.xhscdn.com/avatar/5f827ea10000000001007b5b.jpg"}
                 * video : {"consumer":{"origin_video_key":"pre_post/1040g0cg30npppjff044g5ns2fqgg8uqr2386ee8"},"media":{"video":{"md5":"beb4f3e2c0ff010073155e5d2677ffd2","hdr_type":0,"drm_type":0,"stream_types":[258],"biz_name":110,"biz_id":"280590187074824461","duration":30},"stream":{"h265":[],"av1":[],"h264":[{"vmaf":-1,"quality_type":"HD","default_stream":0,"height":1564,"video_codec":"h264","audio_duration":29792,"audio_channels":2,"hdr_type":0,"width":720,"audio_bitrate":56041,"rotate":0,"backup_urls":["http://sns-video-bd.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4","http://sns-video-qc.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4?sign=4cc9b2422b380348dcee13a7b891d406&t=64e23754","http://sns-video-al.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4","http://sns-video-hw.xhscdn.net/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4"],"ssim":0,"psnr":0,"stream_type":258,"avg_bitrate":207178,"video_bitrate":144371,"video_duration":29833,"size":772620,"volume":0,"audio_codec":"aac","weight":62,"format":"mp4","duration":29834,"master_url":"http://sns-video-hw.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4","stream_desc":"X264_MP4","fps":30}]},"video_id":136474990905740827},"image":{"first_frame_fileid":"110/0/01e4db47f9a94e1b00100000000189f898890f_0.jpg","thumbnail_fileid":"110/0/01e4db47f9a94e1b00100000000189f8988a9a_0.webp"},"capa":{"duration":29}}
                 * time : 1692092892000
                 * at_user_list : [{"user_id":"5b4959f7e8ac2b38b09c20f7","nickname":"Choshim"}]
                 */

                private InteractInfoBean interactInfo;
                private ShareInfoBean shareInfo;
                private String noteId;
                private String title;
                private long lastUpdateTime;
                private String ipLocation;
                private String type;
                private String desc;
                private UserBean user;
                private VideoBeanX video;
                private long time;
                private List<ImageListBean> imageList;
                private List<TagListBean> tagList;
                private List<AtUserListBean> atUserList;

                @NoArgsConstructor
                @Data
                public static class InteractInfoBean {
                    /**
                     * liked : false
                     * liked_count : 3
                     * collected : false
                     * collected_count : 1
                     * comment_count : 2
                     * share_count : 0
                     * followed : false
                     */

                    private boolean liked;
                    private String likedCount;
                    private boolean collected;
                    private String collectedCount;
                    private String commentCount;
                    private String shareCount;
                    private boolean followed;
                }

                @NoArgsConstructor
                @Data
                public static class ShareInfoBean {
                    /**
                     * un_share : false
                     */

                    private boolean unShare;
                }

                @NoArgsConstructor
                @Data
                public static class UserBean {
                    /**
                     * user_id : 5f827ea10000000001007b5b
                     * nickname : 理想是自由
                     * avatar : https://sns-avatar-qc.xhscdn.com/avatar/5f827ea10000000001007b5b.jpg
                     */

                    private String userId;
                    private String nickname;
                    private String avatar;
                }

                @NoArgsConstructor
                @Data
                public static class VideoBeanX {
                    /**
                     * consumer : {"origin_video_key":"pre_post/1040g0cg30npppjff044g5ns2fqgg8uqr2386ee8"}
                     * media : {"video":{"md5":"beb4f3e2c0ff010073155e5d2677ffd2","hdr_type":0,"drm_type":0,"stream_types":[258],"biz_name":110,"biz_id":"280590187074824461","duration":30},"stream":{"h265":[],"av1":[],"h264":[{"vmaf":-1,"quality_type":"HD","default_stream":0,"height":1564,"video_codec":"h264","audio_duration":29792,"audio_channels":2,"hdr_type":0,"width":720,"audio_bitrate":56041,"rotate":0,"backup_urls":["http://sns-video-bd.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4","http://sns-video-qc.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4?sign=4cc9b2422b380348dcee13a7b891d406&t=64e23754","http://sns-video-al.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4","http://sns-video-hw.xhscdn.net/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4"],"ssim":0,"psnr":0,"stream_type":258,"avg_bitrate":207178,"video_bitrate":144371,"video_duration":29833,"size":772620,"volume":0,"audio_codec":"aac","weight":62,"format":"mp4","duration":29834,"master_url":"http://sns-video-hw.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4","stream_desc":"X264_MP4","fps":30}]},"video_id":136474990905740827}
                     * image : {"first_frame_fileid":"110/0/01e4db47f9a94e1b00100000000189f898890f_0.jpg","thumbnail_fileid":"110/0/01e4db47f9a94e1b00100000000189f8988a9a_0.webp"}
                     * capa : {"duration":29}
                     */

                    private ConsumerBean consumer;
                    private MediaBean media;
                    private ImageBean image;
                    private CapaBean capa;

                    @NoArgsConstructor
                    @Data
                    public static class ConsumerBean {
                        /**
                         * origin_video_key : pre_post/1040g0cg30npppjff044g5ns2fqgg8uqr2386ee8
                         */

                        private String originVideoKey;
                    }

                    @NoArgsConstructor
                    @Data
                    public static class MediaBean {
                        /**
                         * video : {"md5":"beb4f3e2c0ff010073155e5d2677ffd2","hdr_type":0,"drm_type":0,"stream_types":[258],"biz_name":110,"biz_id":"280590187074824461","duration":30}
                         * stream : {"h265":[],"av1":[],"h264":[{"vmaf":-1,"quality_type":"HD","default_stream":0,"height":1564,"video_codec":"h264","audio_duration":29792,"audio_channels":2,"hdr_type":0,"width":720,"audio_bitrate":56041,"rotate":0,"backup_urls":["http://sns-video-bd.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4","http://sns-video-qc.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4?sign=4cc9b2422b380348dcee13a7b891d406&t=64e23754","http://sns-video-al.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4","http://sns-video-hw.xhscdn.net/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4"],"ssim":0,"psnr":0,"stream_type":258,"avg_bitrate":207178,"video_bitrate":144371,"video_duration":29833,"size":772620,"volume":0,"audio_codec":"aac","weight":62,"format":"mp4","duration":29834,"master_url":"http://sns-video-hw.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4","stream_desc":"X264_MP4","fps":30}]}
                         * video_id : 136474990905740827
                         */

                        private VideoBean video;
                        private StreamBean stream;
                        private Long videoId;

                        @NoArgsConstructor
                        @Data
                        public static class VideoBean {
                            /**
                             * md5 : beb4f3e2c0ff010073155e5d2677ffd2
                             * hdr_type : 0
                             * drm_type : 0
                             * stream_types : [258]
                             * biz_name : 110
                             * biz_id : 280590187074824461
                             * duration : 30
                             */

                            private String md5;
                            private int hdrType;
                            private int drmType;
                            private int bizName;
                            private String bizId;
                            private int duration;
                            private List<Integer> streamTypes;
                        }

                        @NoArgsConstructor
                        @Data
                        public static class StreamBean {
                            private List<?> h265;
                            private List<?> av1;
                            private List<H264Bean> h264;

                            @NoArgsConstructor
                            @Data
                            public static class H264Bean {
                                /**
                                 * vmaf : -1
                                 * quality_type : HD
                                 * default_stream : 0
                                 * height : 1564
                                 * video_codec : h264
                                 * audio_duration : 29792
                                 * audio_channels : 2
                                 * hdr_type : 0
                                 * width : 720
                                 * audio_bitrate : 56041
                                 * rotate : 0
                                 * backup_urls : ["http://sns-video-bd.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4","http://sns-video-qc.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4?sign=4cc9b2422b380348dcee13a7b891d406&t=64e23754","http://sns-video-al.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4","http://sns-video-hw.xhscdn.net/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4"]
                                 * ssim : 0
                                 * psnr : 0
                                 * stream_type : 258
                                 * avg_bitrate : 207178
                                 * video_bitrate : 144371
                                 * video_duration : 29833
                                 * size : 772620
                                 * volume : 0
                                 * audio_codec : aac
                                 * weight : 62
                                 * format : mp4
                                 * duration : 29834
                                 * master_url : http://sns-video-hw.xhscdn.com/stream/110/258/01e4db47f9a94e1b0103730389f898d9ca_258.mp4
                                 * stream_desc : X264_MP4
                                 * fps : 30
                                 */

                                private int vmaf;
                                private String qualityType;
                                private int defaultStream;
                                private int height;
                                private String videoCodec;
                                private int audioDuration;
                                private int audioChannels;
                                private int hdrType;
                                private int width;
                                private int audioBitrate;
                                private int rotate;
                                private int ssim;
                                private int psnr;
                                private int streamType;
                                private int avgBitrate;
                                private int videoBitrate;
                                private int videoDuration;
                                private int size;
                                private int volume;
                                private String audioCodec;
                                private int weight;
                                private String format;
                                private int duration;
                                private String masterUrl;
                                private String streamDesc;
                                private int fps;
                                private List<String> backupUrls;
                            }
                        }
                    }

                    @NoArgsConstructor
                    @Data
                    public static class ImageBean {
                        /**
                         * first_frame_fileid : 110/0/01e4db47f9a94e1b00100000000189f898890f_0.jpg
                         * thumbnail_fileid : 110/0/01e4db47f9a94e1b00100000000189f8988a9a_0.webp
                         */

                        private String firstFrameFileid;
                        private String thumbnailFileid;
                    }

                    @NoArgsConstructor
                    @Data
                    public static class CapaBean {
                        /**
                         * duration : 29
                         */

                        private int duration;
                    }
                }

                @NoArgsConstructor
                @Data
                public static class ImageListBean {
                    /**
                     * file_id : 85e608b7-b761-7b35-a87a-9c423098617f
                     * height : 2560
                     * width : 1920
                     * url : https://sns-img-hw.xhscdn.com/85e608b7-b761-7b35-a87a-9c423098617f
                     * trace_id : 1040g00830npr0asf085g5ns2fqgg8uqrj02tlno
                     */

                    private String fileId;
                    private int height;
                    private int width;
                    private List<ImageInfo> infoList;
                    private String traceId;

                    @Data
                    @NoArgsConstructor
                    @AllArgsConstructor
                    public static class ImageInfo {
                        private String imageScene;

                        private String url;
                    }
                }

                @NoArgsConstructor
                @Data
                public static class TagListBean {
                    /**
                     * id : 5c524df4000000001c025b44
                     * name : 搞钱
                     * type : topic
                     */

                    private String id;
                    private String name;
                    private String type;
                }

                @NoArgsConstructor
                @Data
                public static class AtUserListBean {
                    /**
                     * user_id : 5b4959f7e8ac2b38b09c20f7
                     * nickname : Choshim
                     */

                    private String userId;
                    private String nickname;
                }
            }
        }
    }
}
