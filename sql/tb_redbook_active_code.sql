CREATE TABLE IF NOT EXISTS `tb_redbook_active_code` (
`active_code` varchar(256) NOT NULL COMMENT '激活码',
`status` int(1) DEFAULT 0 COMMENT '状态：0、未使用 1、已购买未使用 2、已使用 3、已失效',
`item_type` int(1) DEFAULT NULL COMMENT '商品类型',
`update_time` datetime DEFAULT NULL COMMENT '更新时间',
`create_time` datetime DEFAULT NULL COMMENT '创建时间',
PRIMARY KEY (`active_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='RedArchive激活码表';