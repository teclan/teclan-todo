CREATE TABLE `log` (
  `id` varchar(32) NOT NULL primary key comment 'ID',
  `session_id` varchar(32) NOT NULL comment '会话ID',
  `host` varchar(32) DEFAULT NULL comment '主机',
  `port` int DEFAULT NULL comment '端口号',
  `url` varchar(2000) DEFAULT NULL comment 'url',
   user varchar(50)  null comment '账号',
  `header` varchar(2000) DEFAULT NULL comment '请求头信息',
  `parameter` text comment '参数',
  `status` varchar(32) comment '状态',
  `result` LONGTEXT comment '结果',
  `created_at` varchar(20) comment '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




