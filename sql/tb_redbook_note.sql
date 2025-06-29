CREATE TABLE IF NOT EXISTS tb_redbook_note (
    `note_id` varchar(255) PRIMARY KEY COMMENT '笔记id',
	`author_id` varchar(255) comment '笔记作者id',
	`author_name` varchar(255) comment '笔记作者名字',
	`title` varchar(255) comment '笔记标题',
	`type` varchar(255) comment '笔记类型',
    `note_desc` TEXT COMMENT '描述',
    `tags` TEXT COMMENT '笔记标签的json',
    `liked_count` INT DEFAULT 0 COMMENT '点赞数',
    `collected_count` INT DEFAULT 0 COMMENT '收藏数',
    `comment_count` INT DEFAULT 0 COMMENT '评论数',
    `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB CHARACTER SET utf8mb4 COMMENT='小红书笔记表';