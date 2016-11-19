-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.5.25 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.1.0.4867
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 frame 的数据库结构
DROP DATABASE IF EXISTS `jfinal_basic`;
CREATE DATABASE IF NOT EXISTS `jfinal_basic` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `jfinal_basic`;


-- 导出  表 frame.sys_button 结构
DROP TABLE IF EXISTS `sys_button`;
CREATE TABLE IF NOT EXISTS `sys_button` (
  `id` int(6) NOT NULL AUTO_INCREMENT COMMENT '功能Id',
  `menuId` int(3) NOT NULL COMMENT '菜单Id',
  `name` varchar(32) NOT NULL COMMENT '显示名称',
  `value` varchar(32) NOT NULL COMMENT '功能值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 COMMENT='功能按钮';

-- 正在导出表  frame.sys_button 的数据：~15 rows (大约)
DELETE FROM `sys_button`;
/*!40000 ALTER TABLE `sys_button` DISABLE KEYS */;
INSERT INTO `sys_button` (`id`, `menuId`, `name`, `value`) VALUES
	(33, 2, '授权', 'RoleController.permissionview'),
	(34, 2, '添加', 'RoleController.addview'),
	(35, 2, '修改', 'RoleController.editview'),
	(36, 2, '修改状态', 'RoleController.editstatus'),
	(37, 2, '删除', 'RoleController.delview'),
	(38, 3, '删除', 'UserController.delete'),
	(39, 3, '添加', 'UserController.addview'),
	(40, 3, '修改', 'UserController.editview'),
	(41, 3, '修改状态', 'UserController.upstatus'),
	(50, 4, '添加', 'MenuController.addview'),
	(51, 4, '修改', 'MenuController.editview'),
	(52, 4, '修改状态', 'MenuController.editstatus'),
	(53, 4, '删除', 'MenuController.delview'),
	(54, 6, '删除', 'BtnLogController.delbotton'),
	(55, 7, '删除', 'LoginLogController.dellogin');
/*!40000 ALTER TABLE `sys_button` ENABLE KEYS */;


-- 导出  表 frame.sys_button_log 结构
DROP TABLE IF EXISTS `sys_button_log`;
CREATE TABLE IF NOT EXISTS `sys_button_log` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '操作日志ID',
  `userId` bigint(11) DEFAULT NULL COMMENT '用户ID',
  `methodClass` varchar(255) DEFAULT NULL COMMENT '操作类',
  `methodName` varchar(128) DEFAULT NULL COMMENT '操作方法名称',
  `methodPath` varchar(511) DEFAULT NULL COMMENT '请求方法地址',
  `methodParam` varchar(2000) DEFAULT NULL COMMENT '请求参数',
  `operaTime` bigint(13) NOT NULL COMMENT '操作时间',
  `logIp` varchar(31) DEFAULT NULL COMMENT '操作Ip',
  `logDesc` varchar(1000) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志表';

-- 正在导出表  frame.sys_button_log 的数据：~0 rows (大约)
DELETE FROM `sys_button_log`;
/*!40000 ALTER TABLE `sys_button_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_button_log` ENABLE KEYS */;


-- 导出  表 frame.sys_login_log 结构
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE IF NOT EXISTS `sys_login_log` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `userId` bigint(11) NOT NULL COMMENT '用户ID',
  `loginTime` bigint(13) NOT NULL COMMENT '登录时间',
  `logoutTime` bigint(13) DEFAULT NULL COMMENT '退出时间',
  `logIp` varchar(32) NOT NULL COMMENT '登录Ip',
  `lastlogTime` bigint(13) DEFAULT NULL COMMENT '上次登录时间',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '状态;1在线0下线',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='登录日志表';

-- 正在导出表  frame.sys_login_log 的数据：~36 rows (大约)
DELETE FROM `sys_login_log`;
/*!40000 ALTER TABLE `sys_login_log` DISABLE KEYS */;
INSERT INTO `sys_login_log` (`id`, `userId`, `loginTime`, `logoutTime`, `logIp`, `lastlogTime`, `status`) VALUES
	(7, -1, 1429679906717, NULL, '0:0:0:0:0:0:0:1', 0, 1),
	(9, -1, 1429681428979, NULL, '0:0:0:0:0:0:0:1', 1429679906717, 1),
	(10, -1, 1429683799592, NULL, '0:0:0:0:0:0:0:1', 1429681428979, 1),
	(11, -1, 1429758893362, NULL, '0:0:0:0:0:0:0:1', 1429683799592, 1),
	(12, -1, 1429769367034, NULL, '0:0:0:0:0:0:0:1', 1429758893362, 1),
	(13, -1, 1429777412607, NULL, '0:0:0:0:0:0:0:1', 1429769367034, 1),
	(14, -1, 1429784229250, NULL, '0:0:0:0:0:0:0:1', 1429777412607, 1),
	(15, -1, 1430105249463, NULL, '0:0:0:0:0:0:0:1', 1429784229250, 1),
	(16, -1, 1430191392739, NULL, '0:0:0:0:0:0:0:1', 1430105249463, 1),
	(17, -1, 1430191634606, NULL, '0:0:0:0:0:0:0:1', 1430191392739, 1),
	(18, -1, 1430192084427, NULL, '0:0:0:0:0:0:0:1', 1430191634606, 1),
	(19, -1, 1430196863494, NULL, '0:0:0:0:0:0:0:1', 1430192084427, 1),
	(20, -1, 1430196920733, NULL, '0:0:0:0:0:0:0:1', 1430196863494, 1),
	(21, -1, 1430198433233, NULL, '0:0:0:0:0:0:0:1', 1430196920733, 1),
	(22, -1, 1430198570384, NULL, '0:0:0:0:0:0:0:1', 1430198433233, 1),
	(23, -1, 1430198800545, NULL, '0:0:0:0:0:0:0:1', 1430198570384, 1),
	(24, -1, 1430198896866, NULL, '0:0:0:0:0:0:0:1', 1430198800545, 1),
	(25, -1, 1430199285822, NULL, '0:0:0:0:0:0:0:1', 1430198896866, 1),
	(26, -1, 1430199423581, NULL, '0:0:0:0:0:0:0:1', 1430199285822, 1),
	(27, -1, 1430199841740, NULL, '0:0:0:0:0:0:0:1', 1430199423581, 1),
	(28, -1, 1430199895707, NULL, '0:0:0:0:0:0:0:1', 1430199841740, 1),
	(29, 9, 1430224569529, NULL, '0:0:0:0:0:0:0:1', 0, 1),
	(30, 9, 1430226437339, NULL, '0:0:0:0:0:0:0:1', 1430224569529, 1),
	(31, 9, 1430226559137, NULL, '0:0:0:0:0:0:0:1', 1430226437339, 1),
	(32, 9, 1430270176496, NULL, '0:0:0:0:0:0:0:1', 1430226559137, 1),
	(33, 9, 1430272453489, NULL, '0:0:0:0:0:0:0:1', 1430270176496, 1),
	(34, 9, 1430273030407, NULL, '0:0:0:0:0:0:0:1', 1430272453489, 1),
	(35, 9, 1430273130310, NULL, '0:0:0:0:0:0:0:1', 1430273030407, 1),
	(36, 9, 1430273924571, NULL, '0:0:0:0:0:0:0:1', 1430273130310, 1),
	(37, 9, 1430277596042, NULL, '0:0:0:0:0:0:0:1', 1430273924571, 1),
	(38, 9, 1430277696803, NULL, '0:0:0:0:0:0:0:1', 1430277596042, 1),
	(39, 9, 1430280691905, NULL, '0:0:0:0:0:0:0:1', 1430277696803, 1),
	(40, 9, 1430288112034, NULL, '0:0:0:0:0:0:0:1', 1430280691905, 1),
	(41, 9, 1430288208360, NULL, '0:0:0:0:0:0:0:1', 1430288112034, 1),
	(42, 9, 1430298288301, NULL, '0:0:0:0:0:0:0:1', 1430288208360, 1),
	(43, 9, 1430298410509, NULL, '0:0:0:0:0:0:0:1', 1430298288301, 1);
/*!40000 ALTER TABLE `sys_login_log` ENABLE KEYS */;


-- 导出  表 frame.sys_menu 结构
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE IF NOT EXISTS `sys_menu` (
  `id` int(3) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `href` varchar(64) DEFAULT NULL COMMENT 'url地址',
  `icon` varchar(64) DEFAULT NULL COMMENT '图标',
  `description` varchar(64) DEFAULT NULL COMMENT '描述',
  `parentId` int(3) NOT NULL DEFAULT '-1' COMMENT '父级',
  `createTime` bigint(13) NOT NULL COMMENT '创建时间',
  `isUsed` int(1) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `sortNo` int(8) DEFAULT NULL COMMENT '排序号',
  `permission` varchar(64) DEFAULT NULL COMMENT '菜单权限值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- 正在导出表  frame.sys_menu 的数据：~7 rows (大约)
DELETE FROM `sys_menu`;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` (`id`, `name`, `href`, `icon`, `description`, `parentId`, `createTime`, `isUsed`, `sortNo`, `permission`) VALUES
	(1, '系统管理', NULL, 'am-icon-wrench ', NULL, -1, 1407140785011, 1, 99, '1'),
	(2, '角色列表', '/sys/role/', 'am-icon-user-secret ', '角色列表', 1, 1407141914856, 0, 2, 'RoleController'),
	(3, '用户列表', '/sys/user/', 'am-icon-male ', '用户列表', 1, 1407141964456, 1, 1, 'UserController'),
	(4, '菜单列表', '/sys/menu/', 'am-icon-reorder  ', '菜单列表', 1, 1407142023060, 1, 3, 'MenuController'),
	(5, '日志管理', NULL, 'am-icon-pencil-square-o ', NULL, -1, 1409642077554, 1, 100, '1'),
	(6, '操作日志', '/sys/log/btn', 'am-icon-pencil  ', NULL, 5, 1409642132551, 1, 1, 'BtnLogController'),
	(7, '登录日志', '/sys/log/login', 'am-icon-pencil-square  ', '登录日志', 5, 1409648279391, 1, 2, 'LoginLogController');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;


-- 导出  表 frame.sys_role 结构
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` int(4) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `roleCode` varchar(32) DEFAULT NULL COMMENT '角色编号',
  `roleName` varchar(32) NOT NULL COMMENT '角色名称',
  `isUsed` int(1) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `createTime` bigint(13) NOT NULL COMMENT '创建时间',
  `parentId` int(4) NOT NULL COMMENT '父级角色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- 正在导出表  frame.sys_role 的数据：~3 rows (大约)
DELETE FROM `sys_role`;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` (`id`, `roleCode`, `roleName`, `isUsed`, `createTime`, `parentId`) VALUES
	(1, 'root', 'root', 1, 1429680662074, -1),
	(2, 'admin', 'admin', 0, 1429680662074, 1),
	(4, '13', '13', 1, 1429681603916, -1);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;


-- 导出  表 frame.sys_role_permission 结构
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE IF NOT EXISTS `sys_role_permission` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `roleId` int(4) NOT NULL COMMENT '角色Id',
  `permission` varchar(64) NOT NULL COMMENT '权限值（菜单和功能）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8 COMMENT='角色权限';

-- 正在导出表  frame.sys_role_permission 的数据：~40 rows (大约)
DELETE FROM `sys_role_permission`;
/*!40000 ALTER TABLE `sys_role_permission` DISABLE KEYS */;
INSERT INTO `sys_role_permission` (`id`, `roleId`, `permission`) VALUES
	(69, 2, '1'),
	(70, 2, 'RoleController'),
	(71, 2, 'RoleController.permissionview'),
	(72, 2, 'RoleController.addview'),
	(73, 2, 'RoleController.editview'),
	(74, 2, 'RoleController.editstatus'),
	(75, 2, 'RoleController.delview'),
	(76, 2, 'UserController'),
	(77, 2, 'UserController.delete'),
	(78, 2, 'UserController.addview'),
	(79, 2, 'UserController.editview'),
	(80, 2, 'UserController.upstatus'),
	(81, 2, 'MenuController'),
	(82, 2, 'MenuController.addview'),
	(83, 2, 'MenuController.editview'),
	(84, 2, 'MenuController.editstatus'),
	(85, 2, 'MenuController.delview'),
	(86, 2, '1'),
	(87, 1, '1'),
	(88, 1, 'RoleController'),
	(89, 1, 'RoleController.permissionview'),
	(90, 1, 'RoleController.addview'),
	(91, 1, 'RoleController.editview'),
	(92, 1, 'RoleController.editstatus'),
	(93, 1, 'RoleController.delview'),
	(94, 1, 'UserController'),
	(95, 1, 'UserController.delete'),
	(96, 1, 'UserController.addview'),
	(97, 1, 'UserController.editview'),
	(98, 1, 'UserController.upstatus'),
	(99, 1, 'MenuController'),
	(100, 1, 'MenuController.addview'),
	(101, 1, 'MenuController.editview'),
	(102, 1, 'MenuController.editstatus'),
	(103, 1, 'MenuController.delview'),
	(104, 1, '1'),
	(105, 1, 'BtnLogController'),
	(106, 1, 'BtnLogController.delbotton'),
	(107, 1, 'LoginLogController'),
	(108, 1, 'LoginLogController.dellogin');
/*!40000 ALTER TABLE `sys_role_permission` ENABLE KEYS */;


-- 导出  表 frame.sys_user 结构
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` int(8) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `roleId` int(8) DEFAULT NULL,
  `userName` varchar(64) NOT NULL COMMENT '用户名称',
  `passWord` varchar(255) DEFAULT NULL COMMENT '用户密码',
  `createTime` bigint(13) DEFAULT NULL COMMENT '创建时间',
  `sex` int(1) DEFAULT '1' COMMENT '性别;0女1男',
  `birthday` varchar(16) DEFAULT NULL COMMENT '生日',
  `idCard` varchar(32) DEFAULT NULL COMMENT '身份证',
  `mobile` bigint(11) DEFAULT NULL COMMENT '手机',
  `phone` varchar(16) DEFAULT NULL COMMENT '电话',
  `email` varchar(64) DEFAULT NULL COMMENT '电子邮件',
  `addr` varchar(128) DEFAULT NULL COMMENT '地址',
  `createId` bigint(11) DEFAULT NULL COMMENT '创建者ID',
  `createIp` varchar(32) DEFAULT NULL COMMENT '创建IP',
  `operateId` bigint(11) DEFAULT NULL COMMENT '操作人',
  `opetateTime` bigint(13) DEFAULT NULL COMMENT '操作时间',
  `isDefPassWord` int(1) DEFAULT '0' COMMENT '是否默认0是1否',
  `isUsed` int(1) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `sortNo` int(8) DEFAULT NULL COMMENT '排序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- 正在导出表  frame.sys_user 的数据：~5 rows (大约)
DELETE FROM `sys_user`;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`id`, `roleId`, `userName`, `passWord`, `createTime`, `sex`, `birthday`, `idCard`, `mobile`, `phone`, `email`, `addr`, `createId`, `createIp`, `operateId`, `opetateTime`, `isDefPassWord`, `isUsed`, `sortNo`) VALUES
	(9, 1, 'root', '14e1b600b1fd579f47433b88e8d85291', 1406691374696, 1, NULL, NULL, 0, NULL, NULL, NULL, -1, '0:0:0:0:0:0:0:1', 9, 1429494461796, 1, 1, 1),
	(12, 1, 'user03', '123456', 1428396925659, 1, NULL, '', 15826008617, NULL, '', '', 9, '0:0:0:0:0:0:0:1', 9, 1428397051036, 1, 1, 1),
	(36, 4, '45', '1', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, NULL),
	(37, 4, '46', '1', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 0, NULL),
	(38, 4, '47', '1', NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, 1, NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
