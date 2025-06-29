CREATE TABLE IF NOT EXISTS `tb_wx_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `session_key` varchar(255) DEFAULT '' COMMENT '会话秘钥',
  `openid` varchar(255) DEFAULT '' COMMENT '用户唯一标识',
  `unionid` varchar(255) DEFAULT '' COMMENT '开放平台的用户唯一标识',
  `avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '头像',
  `gender` tinyint(3) unsigned DEFAULT NULL COMMENT '性别（1男|0女）',
  `country` varchar(255) NOT NULL DEFAULT '' COMMENT '国别',
  `province` varchar(255) NOT NULL DEFAULT '' COMMENT '省份',
  `city` varchar(255) NOT NULL DEFAULT '' COMMENT '城市',
  `create_time` timestamp not null default current_timestamp comment '创建时间',
  `update_time` timestamp not null default current_timestamp on update current_timestamp comment '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `users_openid_unique` (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='微信小程序用户表';