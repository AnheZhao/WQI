
CREATE TABLE `whois_query_record` (
  `wqr_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录Id',
  `wqr_domain` varchar(255) DEFAULT NULL COMMENT '域名',
  `wqr_prefix` varchar(50) DEFAULT NULL COMMENT '前缀',
  `wqr_Suffix` varchar(20) DEFAULT NULL COMMENT '后缀',
  `wqr_useTime` int(11) DEFAULT NULL COMMENT '消耗时间',
  `wqr_status` int(5) DEFAULT NULL COMMENT '状态（-1，失败；1，成功）',
  `wqr_ipAddress` varchar(20) DEFAULT NULL COMMENT 'ip地址',
  PRIMARY KEY (`wqr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='Whois query record\r\n域名查询记录表'