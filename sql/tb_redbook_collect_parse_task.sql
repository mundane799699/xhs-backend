CREATE TABLE IF NOT EXISTS tb_redbook_collect_parse_task (
    `user_id` varchar(255) PRIMARY KEY COMMENT '收藏用户id',
    `status` INT DEFAULT 0 COMMENT '任务状态',
    `progress` varchar(255) comment '任务进度',
    `create_time` timestamp default current_timestamp comment '创建时间',
    `update_time` timestamp default current_timestamp on update current_timestamp comment '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='小红书收藏解析任务表';