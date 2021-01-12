CREATE TABLE `user_info` (
  `id` int(11) NOT NULL auto_increment primary key comment 'ID',
  `account` varchar(50) not null comment '账号',
  `name` varchar(32) DEFAULT NULL comment '名字',
  `password` varchar(64) comment '登录密码',
  `id_card` varchar(32) comment '身份证号',
  `age` int comment '年龄',
  `phone` varchar(16) comment '电话',
  `role` varchar(32) comment '角色，superadmin/admin/general:超级管理员/管理员/普通用户',
  `limited_period_from` datetime comment '证件有效期起始时间',
  `limited_period_to` datetime comment '证件有效期截止时间',
  `account_period_to` datetime comment '账户有效期截止时间',
  `token` varchar(32) comment 'token',
  `last_time` datetime comment '最后操作时间',
  `create_time` datetime comment '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




