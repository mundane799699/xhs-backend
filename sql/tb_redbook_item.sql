CREATE TABLE IF NOT EXISTS `tb_redbook_item` (
`id` int NOT NULL COMMENT 'id',
`name` varchar(256) DEFAULT NULL COMMENT '商品名称',
`discount_price` decimal(20,2) DEFAULT NULL COMMENT '特价',
`original_price` decimal(20,2) DEFAULT NULL COMMENT '原价',
`status` int(1) DEFAULT 1 COMMENT '状态：0、失效 1、有效',
`days` int DEFAULT NULL COMMENT '有效期，单位：天，-1表示永久',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='RedArchive商品表';