CREATE TABLE `tb_redarchive_user` (
`user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
`user_name` varchar(30) NOT NULL COMMENT '用户账号',
`email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
`password` varchar(100) DEFAULT '' COMMENT '密码',
`status` char(1) DEFAULT '1' COMMENT '帐号状态（0停用 1正常）',
`create_time` datetime DEFAULT NULL COMMENT '创建时间',
`update_time` datetime DEFAULT NULL COMMENT '更新时间',
`membership_type` varchar(20) DEFAULT 'FREE' COMMENT '会员类型（FREE, MONTHLY, YEARLY, LIFETIME, TRIAL）',
`membership_expiry` datetime DEFAULT NULL COMMENT '会员到期时间',
PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='RedArchive用户信息表';