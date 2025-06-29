CREATE TABLE IF NOT EXISTS tb_redbook_note_video (
    `video_id` varchar(255) PRIMARY KEY COMMENT '视频id',
	`note_id` varchar(255) COMMENT '笔记id',
	`master_url` varchar(255) comment '视频主链接',
	`duration` int comment '时长',
	`backup_urls` text comment '备份链接的json',
    `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	INDEX idx_note_id (`note_id`)
) ENGINE=InnoDB CHARACTER SET utf8mb4 COMMENT='小红书笔记-视频链接表';