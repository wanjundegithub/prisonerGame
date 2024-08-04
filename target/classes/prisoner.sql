create database `prisoner`;



CREATE TABLE `prisoner`.`user` (
`id` int NOT NULL AUTO_INCREMENT COMMENT '用户表id',
`nick_name` varchar(30) NOT NULL COMMENT '昵称',
`user_name` varchar(30) NOT NULL COMMENT '用户名-学号',
`password` varchar(30) NOT NULL COMMENT '初始密码-学号',
`role` varchar(30) NOT NULL COMMENT '权限表角色，仅有管理员角色admin和normal',
`create_by` varchar(20) NOT NULL COMMENT '创建人',
`create_date` timestamp NOT NULL COMMENT '创建时间',
`last_update_by` varchar(20) NOT NULL COMMENT '更新人',
`last_update_date` timestamp NOT NULL COMMENT '更新时间',
`delete_flag` smallint NOT NULL COMMENT '0代表未删除 1代表删除',
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (1,'User 1','U202414200','U202414200','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (2,'User 2','U202414201','U202414201','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (3,'User 3','U202414202','U202414202','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (4,'User 4','U202414203','U202414203','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (5,'User 5','U202414204','U202414204','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (6,'User 6','U202414205','U202414205','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (7,'User 7','U202414206','U202414206','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (8,'User 8','U202414207','U202414207','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (9,'User 9','U202414208','U202414208','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (10,'User 10','U202414209','U202414209','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (11,'User 11','U202414210','U202414210','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (12,'User 12','U202414211','U202414211','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (13,'User 13','U202414212','U202414212','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (14,'User 14','U202414213','U202414213','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (15,'User 15','U202414214','U202414214','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (16,'User 16','U202414215','U202414215','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (17,'User 17','U202414216','U202414216','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (18,'User 18','U202414217','U202414217','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (19,'User 19','U202414218','U202414218','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (20,'User 20','U202414219','U202414219','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (21,'User 21','U202414220','U202414220','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (22,'User 22','U202414221','U202414221','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (23,'User 23','U202414222','U202414222','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (24,'User 24','U202414223','U202414223','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (25,'User 25','U202414224','U202414224','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (26,'User 26','U202414225','U202414225','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (27,'User 27','U202414226','U202414226','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (28,'User 28','U202414227','U202414227','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (29,'User 29','U202414228','U202414228','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (30,'User 30','U202414229','U202414229','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (31,'User 31','U202414230','U202414230','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (32,'User 32','U202414231','U202414231','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (33,'User 33','U202414232','U202414232','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (34,'User 34','U202414233','U202414233','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (35,'User 35','U202414234','U202414234','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (36,'User 36','U202414235','U202414235','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (37,'User 37','U202414236','U202414236','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (38,'User 38','U202414237','U202414237','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (39,'User 39','U202414238','U202414238','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (40,'User 40','U202414239','U202414239','normal','user','2024-07-31 00:11:46','user','2024-07-31 00:11:46',0);
INSERT INTO `prisoner`.`user` (`id`,`nick_name`,`user_name`,`password`,`role`,`create_by`,`create_date`,`last_update_by`,`last_update_date`,`delete_flag`) VALUES (41,'Admin 1','T202314200','T202314200','admin','user','2024-07-31 00:22:38','user','2024-07-31 00:22:38',0);


create table `prisoner`.`game`
(
    `game_id` int(11) not null primary key auto_increment comment '游戏表id',
    `alive_flag` int(11) not null comment '存活标识 0:代表初始状态 1代表启动状态 2代表停止状态 3代表无效状态',
    `create_by` varchar(20) not null comment '创建人',
    `create_date` timestamp not null comment '创建时间',
    `last_update_by` varchar(20) not null comment '更新人',
    `last_update_date` timestamp not null comment '更新时间',
    `delete_flag` smallint(1) not null comment '0代表未删除 1代表删除'
)engine = InnoDB, charset = utf8mb4;


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