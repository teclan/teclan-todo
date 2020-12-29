CREATE TABLE `file_mgr` (
  `id` int NOT NULL auto_increment primary key comment 'ID',
  `filename` varchar(2000) NOT NULL comment '文件名',
  `absolute_path` varchar(2000) DEFAULT NULL comment '绝对路径',
  `size` int DEFAULT NULL comment '文件大小',
  `owner` varchar(32) comment '所有者',
  `created_at` varchar(20) comment '创建时间',
  `updated_at` varchar(20) comment '最后修改时间',
  `authorize` varchar(10)  comment '权限'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




