package com.mundane.mail.constant;

/**
 * 常量
 */
public class VideoHubConstants {
    // 文件下载路径，默认为项目同级目录下
    public static final String VIDEOHUB_FILEPATH = "D:\\videoHubDownload\\";

    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36";
    public static final String COOKIE = "abRequestId=94f8e037-0bd7-5905-b55c-87777b603cfc; a1=18ed781490dh2ez3hik35jwu2xvddbnl9osopwhsb50000553265; webId=82fba100cecb33985dca8960adb928a5; gid=yYdfWYy4dfjSyYdfWYy4jlEx8fxJduqx3Tq2MMDVE7JChl28ffDI1U88822qJK28dK0iifS4; xsecappid=xhs-pc-web; web_session=040069b533bb7ff76281626716344b07078bd0; acw_tc=5345ef720e9dd57c55e0ec8a351efcd564ba7dd8694c18bc249b7db22034e541; websectiga=984412fef754c018e472127b8effd174be8a5d51061c991aadd200c69a2801d6; sec_poison_id=113f7a9a-05a2-44b1-abf9-db0633b2f836; webBuild=4.13.1";

    // 小红书笔记匹配正则
    public static final String REDBOOK_NODE_REGEX_01 = "http://xhslink\\.com/\\w+";
    public static final String REDBOOK_NODE_REGEX_02 = "https://www.xiaohongshu.com/explore/([a-zA-Z0-9]+)";
    public static final String REDBOOK_NODE_REGEX_03 = "https://www.xiaohongshu.com/discovery/item/([a-zA-Z0-9]+)";

    // 小红书用户匹配正则
    public static final String REDBOOK_USER_REGEX_01 = "https://www.xiaohongshu.com/user/profile/([a-zA-Z0-9]+)";

    public static final String REDBOOK_NODE_REGEX_EXPLORE = "https://www.xiaohongshu.com/explore/";

    public static final String IMAGE_PREFIX = "https://sns-img-qc.xhscdn.com/";
    public static final String VIDEO_PREFIX = "http://sns-video-qc.xhscdn.com/";

    private static int currentIndex = 0;

    public static String[] cookies = {
            "abRequestId=94f8e037-0bd7-5905-b55c-87777b603cfc; a1=18ed781490dh2ez3hik35jwu2xvddbnl9osopwhsb50000553265; webId=82fba100cecb33985dca8960adb928a5; gid=yYdfWYy4dfjSyYdfWYy4jlEx8fxJduqx3Tq2MMDVE7JChl28ffDI1U88822qJK28dK0iifS4; xsecappid=xhs-pc-web; web_session=040069b533bb7ff76281626716344b07078bd0; acw_tc=5345ef720e9dd57c55e0ec8a351efcd564ba7dd8694c18bc249b7db22034e541; websectiga=984412fef754c018e472127b8effd174be8a5d51061c991aadd200c69a2801d6; sec_poison_id=113f7a9a-05a2-44b1-abf9-db0633b2f836; webBuild=4.13.1",
            "abRequestId=bed90be7-1784-59a2-9f77-5227b84872af; a1=18ef9633aaahspwxt368hlq7i2iwf7o7mfg4zwbgx50000200903; webId=9c7e966b4125629920bebbd9b04e48b3; gid=yYdijKq4446WyYdijKqq0iED00xMUEC9qKYxkkC1AW3J3428EiWlWu888J88j8q82j4KjfdD; web_session=0400698ef63de9562b0d60a411344bb9a66269; acw_tc=172bb4a64e504bd3413a1ef620a41477049fae08d69dfe0d6c404516e8684077; websectiga=a9bdcaed0af874f3a1431e94fbea410e8f738542fbb02df1e8e30c29ef3d91ac; sec_poison_id=fda12e93-4842-4bf8-92ed-abe487910edf; webBuild=4.14.1; xsecappid=xhs-pc-web; unread={%22ub%22:%22662b2dd0000000001c00888f%22%2C%22ue%22:%22662a27a100000000010057fe%22%2C%22uc%22:19}"
    };

    public static String getCookie() {
        String cookie = cookies[currentIndex];
        currentIndex = (currentIndex + 1) % cookies.length;
        System.out.println("cookie = " + cookie);
        return cookie;
    }

}
