CREATE TABLE `log` (
  `id` varchar(32) NOT NULL primary key comment 'ID',
  `session_id` varchar(32) not null comment '会话ID',
  `url` varchar(2000) DEFAULT NULL comment 'url',
  `header` varchar(2000) DEFAULT NULL comment '请求头信息',
  `parameter` text comment '参数',
  `status` varchar(32) comment '状态',
  `result` text comment '结果',
  `created_at` datetime comment '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




