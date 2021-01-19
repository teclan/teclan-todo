create table `file_push`
(
 `id` varchar(32) not null comment '推送ID',
 `remote` varchar(32) comment '客户端',
 `src` varchar(2000) not null comment '中文解释',
 `dst` varchar(2000) not null comment '字典类型',
 `memo` varchar(100) comment '备注',
 primary key (`id`)
);

