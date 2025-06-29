CREATE TABLE IF NOT EXISTS tb_redbook_note_img (
    `file_id` varchar(255) COMMENT '文件id',
	`note_id` varchar(255) COMMENT '笔记id',
	`url` varchar(255) PRIMARY KEY comment '图片链接',
	`img_index` int comment '图片在笔记中的索引',
	`height` int comment '高度',
	`width` int comment '宽度',
    `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	INDEX idx_note_id (`note_id`)
) ENGINE=InnoDB CHARACTER SET utf8mb4 COMMENT='小红书笔记-图片链接表';