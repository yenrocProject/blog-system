DROP TABLE IF EXISTS demo;
CREATE TABLE demo (
  `id` varchar(36) CHARACTER SET utf8 NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '姓名',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `salary` decimal(7,2) DEFAULT NULL COMMENT '工资',
  `birthDay` datetime DEFAULT NULL COMMENT '出生日期',
  `created_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建用户',
  `created_date` datetime DEFAULT NULL COMMENT '创建时间',
  `last_modified_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新用户',
  `last_modified_date` datetime DEFAULT NULL COMMENT '更新时间',
  `is_del` tinyint(4) DEFAULT '0' COMMENT '是否删除(0_false,1_true)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='人员';