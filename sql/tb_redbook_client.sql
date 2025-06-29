CREATE TABLE IF NOT EXISTS `tb_redbook_client` (
`id` varchar(256) NOT NULL COMMENT 'id',
`out_trade_no` varchar(256) DEFAULT NULL COMMENT '商户订单号',
`active_code` varchar(256) DEFAULT NULL COMMENT '激活码',
`pay_type` int(1) DEFAULT NULL COMMENT '支付类型: 1、微信支付 2、激活码支付',
`client_id` varchar(256) NOT NULL COMMENT '设备标识',
`status` int(1) DEFAULT 1 COMMENT '状态：0、失效 1、有效',
`end_date` varchar(20) DEFAULT NULL COMMENT '会员到期时间, -1表示永久会员',
`update_time` datetime DEFAULT NULL COMMENT '更新时间',
`create_time` datetime DEFAULT NULL COMMENT '创建时间',
PRIMARY KEY (`id`),
KEY `client_id` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='RedArchive会员表';