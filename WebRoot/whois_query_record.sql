
CREATE TABLE `whois_query_record` (
  `wqr_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '��¼Id',
  `wqr_domain` varchar(255) DEFAULT NULL COMMENT '����',
  `wqr_prefix` varchar(50) DEFAULT NULL COMMENT 'ǰ׺',
  `wqr_Suffix` varchar(20) DEFAULT NULL COMMENT '��׺',
  `wqr_useTime` int(11) DEFAULT NULL COMMENT '����ʱ��',
  `wqr_status` int(5) DEFAULT NULL COMMENT '״̬��-1��ʧ�ܣ�1���ɹ���',
  `wqr_ipAddress` varchar(20) DEFAULT NULL COMMENT 'ip��ַ',
  PRIMARY KEY (`wqr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='Whois query record\r\n������ѯ��¼��'