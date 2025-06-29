CREATE TABLE IF NOT EXISTS `tb_redbook_collect` (
  `user_id` varchar(255) NOT NULL COMMENT '收藏用户id',
  `note_id` varchar(255) NOT NULL COMMENT '笔记id',
  `display_title` varchar(255) DEFAULT NULL COMMENT '笔记标题',
  `type` varchar(255) DEFAULT NULL COMMENT '笔记类型',
  `cover_url` varchar(255) DEFAULT NULL COMMENT '笔记封面地址',
  `owner_id` varchar(255) DEFAULT NULL COMMENT '笔记创建者id',
  `owner_nickname` varchar(255) DEFAULT NULL COMMENT '笔记创建者昵称',
  `owner_avatar` varchar(255) DEFAULT NULL COMMENT '笔记创建者头像',
  `liked` tinyint(1) DEFAULT 0 COMMENT '是否点赞',
  `liked_count` int(10) unsigned DEFAULT 0 COMMENT '点赞数',
  `patch_index` int comment '第几批',
  `collect_index` int comment '该条笔记在同批次的笔记中处于第几个',
  `create_date` date not null comment '创建日期',
  `create_time` timestamp not null default current_timestamp comment '创建时间',
  `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
  PRIMARY KEY (`user_id`,`note_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='小红书收藏信息表';