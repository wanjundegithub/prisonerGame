create database `prisoner`;



CREATE TABLE `user` (
`id` int NOT NULL AUTO_INCREMENT COMMENT '用户表id',
`nick_name` varchar(30) NOT NULL COMMENT '昵称',
`user_name` varchar(30) NOT NULL COMMENT '用户名-学号',
`password` varchar(30) NOT NULL COMMENT '初始密码-学号',
`role` varchar(30) NOT NULL COMMENT '权限表角色，仅有管理员角色admin和normal',
`class_name` varchar(30) NOT NULL COMMENT '班级',
`create_by` varchar(20) NOT NULL COMMENT '创建人',
`create_date` timestamp NOT NULL COMMENT '创建时间',
`last_update_by` varchar(20) NOT NULL COMMENT '更新人',
`last_update_date` timestamp NOT NULL COMMENT '更新时间',
`delete_flag` smallint NOT NULL COMMENT '0代表未删除 1代表删除',
PRIMARY KEY (`id`),
UNIQUE KEY `unique_user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


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