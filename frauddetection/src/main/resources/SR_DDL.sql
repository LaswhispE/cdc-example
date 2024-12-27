CREATE DATABASE IF NOT EXISTS test;

CREATE TABLE IF NOT EXISTS `test`.`user` (
                        `id` bigint(20) NOT NULL COMMENT "ID",
                        `username` varchar(65533) NULL COMMENT "用户名",
                        `password` varchar(65533) NULL COMMENT "密码"
) ENGINE=OLAP
PRIMARY KEY(`id`)
COMMENT "用户信息表"
DISTRIBUTED BY HASH(`id`) BUCKETS 1
PROPERTIES (
"replication_num" = "1"
);

CREATE TABLE IF NOT EXISTS `test`.`product` (
                               `_id` varchar(65533) NOT NULL COMMENT "分布式ID",
                               `product_id` bigint(20) NULL COMMENT "产品编号",
                               `product_info` varchar(65533) NULL COMMENT "产品详情"
) ENGINE=OLAP
PRIMARY KEY(`id`)
COMMENT "产品信息表"
DISTRIBUTED BY HASH(`id`) BUCKETS 1
PROPERTIES (
"replication_num" = "1"
);