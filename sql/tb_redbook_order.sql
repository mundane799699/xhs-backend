CREATE TABLE IF NOT EXISTS `tb_redbook_order` (
    `out_trade_no` varchar(256) NOT NULL COMMENT '商户订单号',
    `order_no` varchar(256) DEFAULT NULL COMMENT '系统订单号',
    `pay_no` varchar(256) DEFAULT NULL COMMENT '交易单号',
    `client_id` varchar(256) NOT NULL COMMENT '机器唯一标识',
    `user_id` varchar(256) DEFAULT NULL COMMENT '用户唯一标识',
    `total_pay` decimal(20,2) DEFAULT NULL COMMENT '支付金额',
    `item_type` int DEFAULT NULL COMMENT '商品类型',
    `status` int(1) DEFAULT 0 COMMENT '状态：0、未付款 1、已付款',
    `update_time` datetime DEFAULT NULL COMMENT '订单更新时间',
    `create_time` datetime DEFAULT NULL COMMENT '订单创建时间',
    PRIMARY KEY (`out_trade_no`),
    KEY `create_time` (`create_time`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='RedArchive订单表';