create table `s_dic`
(
 `ename` varchar(32) not null comment '字典项',
 `cname` varchar(32) not null comment '中文解释',
 `opttype` varchar(32) not null comment '字典类型',
 `memo` varchar(100) comment '备注',
 primary key (`ename`,`opttype`)
);
insert into s_dic (`ename`,`cname`,`opttype`,`memo`) values ('private','私有','AUTHORIZE','权限');
insert into s_dic (`ename`,`cname`,`opttype`,`memo`) values ('public','公开','AUTHORIZE','权限');
insert into s_dic (`ename`,`cname`,`opttype`,`memo`) values ('protected','受保护','AUTHORIZE','权限');
