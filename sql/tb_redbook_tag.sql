CREATE TABLE IF NOT EXISTS tb_redbook_tag (
    `id` varchar(255) PRIMARY KEY COMMENT '标签id',
	`name` varchar(255) comment '标签名称',
	`type` varchar(255) comment '标签类型',
    `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB CHARACTER SET utf8mb4 COMMENT='小红书标签表';