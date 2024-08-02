create database `prisoner`;

create table `prisoner`.`permission`
(
    `id` int(11) not null primary key auto_increment comment '权限表id',
    `role` varchar(30) not null comment '权限表角色，仅有管理员角色admin和normal',
    `school_number` varchar(30) not null comment '学号',
    `create_by` varchar(20) not null comment '创建人',
    `create_date` timestamp not null comment '创建时间',
    `last_update_by` varchar(20) not null comment '更新人',
    `last_update_date` timestamp not null comment '更新时间',
    `delete_flag` smallint(1) not null comment '0代表未删除 1代表删除'
)engine = InnoDB, charset = utf8mb4;

/*
-- Query: SELECT * FROM prisoner.permission
LIMIT 0, 1000

-- Date: 2024-07-31 08:24
*/
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (1,'normal','U202414200','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (2,'normal','U202414201','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (3,'normal','U202414202','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (4,'normal','U202414203','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (5,'normal','U202414204','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (6,'normal','U202414205','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (7,'normal','U202414206','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (8,'normal','U202414207','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (9,'normal','U202414208','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (10,'normal','U202414209','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (11,'normal','U202414210','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (12,'normal','U202414211','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (13,'normal','U202414212','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (14,'normal','U202414213','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (15,'normal','U202414214','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (16,'normal','U202414215','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (17,'normal','U202414216','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (18,'normal','U202414217','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (19,'normal','U202414218','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (20,'normal','U202414219','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (21,'normal','U202414220','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (22,'normal','U202414221','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (23,'normal','U202414222','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (24,'normal','U202414223','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (25,'normal','U202414224','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (26,'normal','U202414225','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (27,'normal','U202414226','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (28,'normal','U202414227','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (29,'normal','U202414228','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (30,'normal','U202414229','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (31,'normal','U202414230','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (32,'normal','U202414231','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (33,'normal','U202414232','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (34,'normal','U202414233','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (35,'normal','U202414234','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (36,'normal','U202414235','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (37,'normal','U202414236','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (38,'normal','U202414237','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (39,'normal','U202414238','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (40,'normal','U202414239','user','2024-07-31 00:19:48','user','2024-07-31 00:19:48',0);
INSERT INTO `` (`id`,`role`,`school_number`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (64,'admin','T202314200','user','2024-07-31 00:20:49','user','2024-07-31 00:20:49',0);


create table `prisoner`.`user`
(
    `id` int(11) not null primary key auto_increment comment '用户表id',
    `nick_name` varchar(30) not null comment '昵称',
    `user_name` varchar(30) not null comment '用户名-学号',
    `password` varchar(30) not null comment '初始密码-学号',
    `create_by` varchar(20) not null comment '创建人',
    `create_date` timestamp not null comment '创建时间',
    `last_update_by` varchar(20) not null comment '更新人',
    `last_update_date` timestamp not null comment '更新时间',
    `delete_flag` smallint(1) not null comment '0代表未删除 1代表删除'
)engine = InnoDB, charset = utf8mb4;

/*
-- Query: SELECT * FROM prisoner.user
LIMIT 0, 1000

-- Date: 2024-07-31 08:24
*/
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (1,'User 1','U202414200','U202414200','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (2,'User 2','U202414201','U202414201','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (3,'User 3','U202414202','U202414202','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (4,'User 4','U202414203','U202414203','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (5,'User 5','U202414204','U202414204','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (6,'User 6','U202414205','U202414205','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (7,'User 7','U202414206','U202414206','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (8,'User 8','U202414207','U202414207','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (9,'User 9','U202414208','U202414208','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (10,'User 10','U202414209','U202414209','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (11,'User 11','U202414210','U202414210','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (12,'User 12','U202414211','U202414211','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (13,'User 13','U202414212','U202414212','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (14,'User 14','U202414213','U202414213','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (15,'User 15','U202414214','U202414214','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (16,'User 16','U202414215','U202414215','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (17,'User 17','U202414216','U202414216','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (18,'User 18','U202414217','U202414217','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (19,'User 19','U202414218','U202414218','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (20,'User 20','U202414219','U202414219','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (21,'User 21','U202414220','U202414220','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (22,'User 22','U202414221','U202414221','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (23,'User 23','U202414222','U202414222','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (24,'User 24','U202414223','U202414223','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (25,'User 25','U202414224','U202414224','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (26,'User 26','U202414225','U202414225','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (27,'User 27','U202414226','U202414226','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (28,'User 28','U202414227','U202414227','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (29,'User 29','U202414228','U202414228','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (30,'User 30','U202414229','U202414229','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (31,'User 31','U202414230','U202414230','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (32,'User 32','U202414231','U202414231','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (33,'User 33','U202414232','U202414232','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (34,'User 34','U202414233','U202414233','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (35,'User 35','U202414234','U202414234','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (36,'User 36','U202414235','U202414235','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (37,'User 37','U202414236','U202414236','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (38,'User 38','U202414237','U202414237','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (39,'User 39','U202414238','U202414238','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (40,'User 40','U202414239','U202414239','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `` (`id`,`nick_name`,`user_name`,`password`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (41,'Admin 1','T202314200','T202314200','user','2024-07-31 00:22:38','user','2024-07-31 00:22:38',0);

create table `prisoner`.`game`
(
    `game_id` int(11) not null primary key auto_increment comment '游戏表id',
    `alive_flag` int(11) not null comment '存活标识 0:代表初始状态 1代表启动状态 2代表停止状态',
    `create_by` varchar(20) not null comment '创建人',
    `create_date` timestamp not null comment '创建时间',
    `last_update_by` varchar(20) not null comment '更新人',
    `last_update_date` timestamp not null comment '更新时间',
    `delete_flag` smallint(1) not null comment '0代表未删除 1代表删除'
)engine = InnoDB, charset = utf8mb4;

INSERT INTO `` (`game_id`,`alive_flag`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (1,2,'admin','2024-07-31 13:46:29','admin','2024-07-31 13:49:51',0);
INSERT INTO `` (`game_id`,`alive_flag`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (2,2,'admin','2024-07-31 13:50:50','admin','2024-07-31 13:51:24',0);

create table `prisoner`.`group`
(
    `id` int(11) not null primary key auto_increment comment '分组id',
    `group_sequence` int(11) not null comment '分组序列号 由参与的人数决定',
    `game_id` int(11) not null comment '游戏id',
    `user_id_first` varchar(50) not null comment '分组内的第一个用户id',
    `user_id_second` varchar(50) not null comment '分组内的第二个用户id',
    `create_by` varchar(20) not null comment '创建人',
    `create_date` timestamp not null comment '创建时间',
    `last_update_by` varchar(20) not null comment '更新人',
    `last_update_date` timestamp not null comment '更新时间',
    `delete_flag` smallint(1) not null comment '0代表未删除 1代表删除',
    index `index_01`(`group_sequence`,`game_id`, `user_id_first`, `user_id_second`)
)engine = InnoDB, charset = utf8mb4;


create table `prisoner`.`option`
(
    `id` int(11) not null primary key auto_increment comment '分组id',
    `game_id` int(11) not null comment '游戏id',
    `group_id` int(11) not null comment '分组id',
    `user_id` int(11) not null comment '用户id',
    `select_option` int(11) not null comment '用户的选择 0代表未选择 1代表选择合作 2代表选择背叛',
    `create_by` varchar(20) not null comment '创建人',
    `create_date` timestamp not null comment '创建时间',
    `last_update_by` varchar(20) not null comment '更新人',
    `last_update_date` timestamp not null comment '更新时间',
    `delete_flag` smallint(1) not null comment '0代表未删除 1代表删除',
    index `index_01`(`game_id`, `group_id`, `user_id`)
)engine = InnoDB, charset = utf8mb4, comment='用户提交的选项表';


create table `prisoner`.`score`
(
    `id` int(11) not null primary key auto_increment comment '分组id',
    `game_id` int(11) not null comment '游戏id',
    `group_id` int(11) not null comment '分组id',
    `user_id` int(11) not null comment '用户id',
    `option_value` varchar(20) not null comment '用户的选择 未选择 选择合作 选择背叛',
    `score` int(11) not null comment '所得分数',
    `create_by` varchar(20) not null comment '创建人',
    `create_date` timestamp not null comment '创建时间',
    `last_update_by` varchar(20) not null comment '更新人',
    `last_update_date` timestamp not null comment '更新时间',
    `delete_flag` smallint(1) not null comment '0代表未删除 1代表删除',
    index `index_01`(`game_id`, `group_id`, `user_id`)
)engine = InnoDB, charset = utf8mb4, comment='当前游戏下的所有分数';