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


DROP TABLE IF EXISTS sys_table_config;
create table sys_table_config
(
    id varchar(36) not null,
    table_type varchar(10) null COMMENT '创建时间',
    namespace varchar(255) null COMMENT '空间路径',
    table_name varchar(255) null COMMENT '表名',
    name varchar(50) null COMMENT '表对象名',
    name_desc varchar(50) null COMMENT '描述',
    ver int null COMMENT '创建时间',
    `desc` varchar(255) null COMMENT '创建时间',
    `created_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建用户',
    `created_date` datetime DEFAULT NULL COMMENT '创建时间',
    `last_modified_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新用户',
    `last_modified_date` datetime DEFAULT NULL COMMENT '更新时间',
    `is_del` tinyint(4) DEFAULT '0' COMMENT '是否删除(0_false,1_true)',
    constraint sys_table_config_pk
        primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin comment '动态表配置';

DROP TABLE IF EXISTS sys_table_detail;
create table sys_table_detail
(
    id varchar(36) null,
    table_config_id varchar(36) null COMMENT '创建时间',
    column_name varchar(255) null COMMENT '数据库字段名',
    column_type varchar(255) null COMMENT '数据库字段类型',
    column_comment varchar(255) null COMMENT '字段描述',
    is_not_null tinyint(4) default '0' COMMENT '是否为空',
    default_value varchar(255) null COMMENT '默认值',
    field_name varchar(255) null COMMENT '数据字段名称',
    field_type varchar(255) null COMMENT '数据类型',
    `created_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建用户',
    `created_date` datetime DEFAULT NULL COMMENT '创建时间',
    `last_modified_by` varchar(36) CHARACTER SET utf8 DEFAULT NULL COMMENT '更新用户',
    `last_modified_date` datetime DEFAULT NULL COMMENT '更新时间',
    `is_del` tinyint(4) DEFAULT '0' COMMENT '是否删除(0_false,1_true)',
    constraint sys_table_config_pk
        primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin  comment '表字段配置表';

DROP TABLE IF EXISTS sys_del_data;
create table sys_del_data
(
    id                 varchar(36) charset utf8 not null comment '主键' primary key,
    table_name         varchar(50) charset utf8 null comment '表明',
    data_info          text                     not null,
    created_by         varchar(36) charset utf8 null comment '创建用户',
    created_date       datetime                 null comment '创建时间',
    last_modified_by   varchar(36) charset utf8 null comment '更新用户',
    last_modified_date datetime                 null comment '更新时间',
    is_del             tinyint default 0        null comment '是否删除(0_false,1_true)'
)
    comment '人员' collate = utf8_bin;

