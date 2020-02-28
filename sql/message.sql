DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `user_id_send` bigint(20) DEFAULT NULL COMMENT '发送方id',
  `user_name_send` varchar(32) DEFAULT NULL COMMENT '发送方name',
  `user_id_accept` bigint(20) DEFAULT NULL COMMENT '接收方id',
  `mess_content` varchar(512) DEFAULT NULL COMMENT '消息内容',
  `read_status` int(4) DEFAULT NULL COMMENT '阅读状态  0 未读 1 已读',
  `module_type_code` varchar(32) DEFAULT NULL COMMENT '模块类别code',
  `module_type_name` varchar(32) DEFAULT NULL COMMENT '模块类别name',
  `module_path` varchar(214) DEFAULT NULL COMMENT '模块路径',
  `init_code` varchar(64) DEFAULT NULL COMMENT '单据编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='销售发货单';