CREATE TABLE IF NOT EXISTS `tb_bilibili_history` (
    `user_id` varchar(255) NOT NULL COMMENT '用户id',
    `bvid` varchar(255) NOT NULL COMMENT 'bv号',
    `title` varchar(255) DEFAULT NULL COMMENT '标题',
    `cover` varchar(255) DEFAULT NULL COMMENT '封面地址',
    `author_name` varchar(255) DEFAULT NULL COMMENT '作者名称',
    `author_mid` varchar(255) DEFAULT NULL COMMENT '作者空间id',
    `progress` int DEFAULT 0 COMMENT '点赞数',
    `is_finish` int DEFAULT 0 COMMENT '是否完成，0：未完成，1：已完成',
    `is_fav` int DEFAULT 0 COMMENT '是否收藏，0：未收藏，1：已收藏',
    `videos` int DEFAULT 0 COMMENT '是否收藏，0：直播，1：视频，100：合集',
    `live_status` int DEFAULT 0 COMMENT '是否正在直播',
    `tag_name` varchar(255) DEFAULT NULL COMMENT '标签',
    `view_at` bigint default null comment '最后一次看该视频的时间',
    `create_time` datetime not null comment '创建时间',
    `update_time` datetime not null comment '修改时间',
    PRIMARY KEY (`user_id`,`bvid`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='bilibili历史记录表';