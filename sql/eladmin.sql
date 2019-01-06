/*
 Navicat MySQL Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50559
 Source Host           : localhost:3306
 Source Schema         : eladmin

 Target Server Type    : MySQL
 Target Server Version : 50559
 File Encoding         : 65001

 Date: 06/01/2019 13:09:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for alipay_config
-- ----------------------------
DROP TABLE IF EXISTS `alipay_config`;
CREATE TABLE `alipay_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `appID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `charset` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型 固定格式json',
  `gatewayUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `notifyUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `privateKey` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `publicKey` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `returnUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `signType` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sysServiceProviderId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of alipay_config
-- ----------------------------
INSERT INTO `alipay_config` VALUES (1, '2016091700532697', 'utf-8', 'JSON', 'https://openapi.alipaydev.com/gateway.do', 'http://api.auauz.net/api/aliPay/notify', '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', 'http://api.auauz.ne/api/aliPay/return', 'RSA2', '2088102176044281');

-- ----------------------------
-- Table structure for email_config
-- ----------------------------
DROP TABLE IF EXISTS `email_config`;
CREATE TABLE `email_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fromUser` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `host` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pass` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `port` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `exceptionDetail` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `logType` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `params` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `requestIp` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4553 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NULL DEFAULT NULL,
  `iFrame` bit(1) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pid` bigint(20) NOT NULL,
  `sort` bigint(20) NOT NULL,
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '2018-12-18 15:11:29', b'0', '系统管理', NULL, 0, 1, 'system', 'system');
INSERT INTO `menu` VALUES (2, '2018-12-18 15:14:44', b'0', '用户管理', 'system/user/index', 1, 2, 'peoples', 'user');
INSERT INTO `menu` VALUES (3, '2018-12-18 15:16:07', b'0', '角色管理', 'system/role/index', 1, 3, 'role', 'role');
INSERT INTO `menu` VALUES (4, '2018-12-18 15:16:45', b'0', '权限管理', 'system/permission/index', 1, 4, 'permission', 'permission');
INSERT INTO `menu` VALUES (5, '2018-12-18 15:17:28', b'0', '菜单管理', 'system/menu/index', 1, 5, 'menu', 'menu');
INSERT INTO `menu` VALUES (6, '2018-12-18 15:17:48', b'0', '系统监控', NULL, 0, 10, 'monitor', 'monitor');
INSERT INTO `menu` VALUES (7, '2018-12-18 15:18:26', b'0', '系统日志', 'monitor/log/index', 6, 11, 'log', 'logs');
INSERT INTO `menu` VALUES (8, '2018-12-18 15:19:01', b'0', '系统缓存', 'monitor/redis/index', 6, 12, 'redis', 'redis');
INSERT INTO `menu` VALUES (9, '2018-12-18 15:19:34', b'1', 'SQL监控', NULL, 6, 14, 'sqlMonitor', 'http://localhost:8000/druid');
INSERT INTO `menu` VALUES (10, '2018-12-19 13:38:16', b'0', '组件管理', NULL, 0, 50, 'zujian', 'components');
INSERT INTO `menu` VALUES (11, '2018-12-19 13:38:49', b'0', '图标库', 'components/IconSelect', 10, 51, 'icon', 'icon');
INSERT INTO `menu` VALUES (12, '2018-12-24 20:37:35', b'0', '实时控制台', 'monitor/log/msg', 6, 13, 'codeConsole', 'msg');
INSERT INTO `menu` VALUES (13, '2018-12-27 10:11:26', b'0', '三方工具', '', 0, 20, 'tools', 'tools');
INSERT INTO `menu` VALUES (14, '2018-12-27 10:13:09', b'0', '邮件工具', 'tools/email/index', 13, 21, 'email', 'email');
INSERT INTO `menu` VALUES (15, '2018-12-27 11:58:25', b'0', '富文本', 'components/Editor', 10, 52, 'fwb', 'tinymce');
INSERT INTO `menu` VALUES (16, '2018-12-28 09:36:53', b'0', 'SM.MS图床', 'tools/picture/index', 13, 22, 'image', 'pictures');
INSERT INTO `menu` VALUES (17, '2018-12-28 15:09:49', b'1', '项目地址', '', 0, 0, 'github', 'https://github.com/elunez/eladmin');
INSERT INTO `menu` VALUES (18, '2018-12-31 11:12:15', b'0', '七牛云存储', 'tools/qiniu/index', 13, 23, 'qiniu', 'qiniu');
INSERT INTO `menu` VALUES (19, '2018-12-31 14:52:38', b'0', '支付宝工具', 'tools/aliPay/index', 13, 24, 'alipay', 'aliPay');
INSERT INTO `menu` VALUES (21, '2019-01-04 16:22:03', b'0', '多级菜单', '', 0, 900, 'menu', 'menu1');
INSERT INTO `menu` VALUES (22, '2019-01-04 16:23:29', b'0', '二级菜单1', '', 21, 999, 'menu', 'menu1-1');
INSERT INTO `menu` VALUES (23, '2019-01-04 16:23:57', b'0', '二级菜单2', '', 21, 999, 'menu', 'menu1-2');
INSERT INTO `menu` VALUES (24, '2019-01-04 16:24:48', b'1', '三级菜单', '', 22, 999, 'chain', 'https://github.com/elunez/eladmin');

-- ----------------------------
-- Table structure for menus_roles
-- ----------------------------
DROP TABLE IF EXISTS `menus_roles`;
CREATE TABLE `menus_roles`  (
  `menu_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`menu_id`, `role_id`) USING BTREE,
  INDEX `FKcngg2qadojhi3a651a5adkvbq`(`role_id`) USING BTREE,
  CONSTRAINT `FKcngg2qadojhi3a651a5adkvbq` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKq1knxf8ykt26we8k331naabjx` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of menus_roles
-- ----------------------------
INSERT INTO `menus_roles` VALUES (1, 1);
INSERT INTO `menus_roles` VALUES (2, 1);
INSERT INTO `menus_roles` VALUES (3, 1);
INSERT INTO `menus_roles` VALUES (4, 1);
INSERT INTO `menus_roles` VALUES (5, 1);
INSERT INTO `menus_roles` VALUES (6, 1);
INSERT INTO `menus_roles` VALUES (7, 1);
INSERT INTO `menus_roles` VALUES (8, 1);
INSERT INTO `menus_roles` VALUES (9, 1);
INSERT INTO `menus_roles` VALUES (10, 1);
INSERT INTO `menus_roles` VALUES (11, 1);
INSERT INTO `menus_roles` VALUES (12, 1);
INSERT INTO `menus_roles` VALUES (13, 1);
INSERT INTO `menus_roles` VALUES (14, 1);
INSERT INTO `menus_roles` VALUES (15, 1);
INSERT INTO `menus_roles` VALUES (16, 1);
INSERT INTO `menus_roles` VALUES (17, 1);
INSERT INTO `menus_roles` VALUES (18, 1);
INSERT INTO `menus_roles` VALUES (19, 1);
INSERT INTO `menus_roles` VALUES (21, 1);
INSERT INTO `menus_roles` VALUES (22, 1);
INSERT INTO `menus_roles` VALUES (23, 1);
INSERT INTO `menus_roles` VALUES (24, 1);
INSERT INTO `menus_roles` VALUES (1, 2);
INSERT INTO `menus_roles` VALUES (2, 2);
INSERT INTO `menus_roles` VALUES (3, 2);
INSERT INTO `menus_roles` VALUES (4, 2);
INSERT INTO `menus_roles` VALUES (5, 2);
INSERT INTO `menus_roles` VALUES (6, 2);
INSERT INTO `menus_roles` VALUES (9, 2);
INSERT INTO `menus_roles` VALUES (12, 2);
INSERT INTO `menus_roles` VALUES (13, 2);
INSERT INTO `menus_roles` VALUES (14, 2);
INSERT INTO `menus_roles` VALUES (16, 2);
INSERT INTO `menus_roles` VALUES (17, 2);
INSERT INTO `menus_roles` VALUES (18, 2);
INSERT INTO `menus_roles` VALUES (19, 2);
INSERT INTO `menus_roles` VALUES (21, 2);
INSERT INTO `menus_roles` VALUES (22, 2);
INSERT INTO `menus_roles` VALUES (23, 2);
INSERT INTO `menus_roles` VALUES (24, 2);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `alias` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `createTime` datetime NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pid` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, '超级管理员', '2018-12-03 12:27:48', 'ADMIN', 0);
INSERT INTO `permission` VALUES (2, '用户管理', '2018-12-03 12:28:19', 'USER_ALL', 0);
INSERT INTO `permission` VALUES (3, '用户查询', '2018-12-03 12:31:35', 'USER_SELECT', 2);
INSERT INTO `permission` VALUES (4, '用户创建', '2018-12-03 12:31:35', 'USER_CREATE', 2);
INSERT INTO `permission` VALUES (5, '用户编辑', '2018-12-03 12:31:35', 'USER_EDIT', 2);
INSERT INTO `permission` VALUES (6, '用户删除', '2018-12-03 12:31:35', 'USER_DELETE', 2);
INSERT INTO `permission` VALUES (7, '角色管理', '2018-12-03 12:28:19', 'ROLES_ALL', 0);
INSERT INTO `permission` VALUES (8, '角色查询', '2018-12-03 12:31:35', 'ROLES_SELECT', 7);
INSERT INTO `permission` VALUES (10, '角色创建', '2018-12-09 20:10:16', 'ROLES_CREATE', 7);
INSERT INTO `permission` VALUES (11, '角色编辑', '2018-12-09 20:10:42', 'ROLES_EDIT', 7);
INSERT INTO `permission` VALUES (12, '角色删除', '2018-12-09 20:11:07', 'ROLES_DELETE', 7);
INSERT INTO `permission` VALUES (13, '权限管理', '2018-12-09 20:11:37', 'PERMISSION_ALL', 0);
INSERT INTO `permission` VALUES (14, '权限查询', '2018-12-09 20:11:55', 'PERMISSION_SELECT', 13);
INSERT INTO `permission` VALUES (15, '权限创建', '2018-12-09 20:14:10', 'PERMISSION_CREATE', 13);
INSERT INTO `permission` VALUES (16, '权限编辑', '2018-12-09 20:15:44', 'PERMISSION_EDIT', 13);
INSERT INTO `permission` VALUES (17, '权限删除', '2018-12-09 20:15:59', 'PERMISSION_DELETE', 13);
INSERT INTO `permission` VALUES (18, '缓存管理', '2018-12-17 13:53:25', 'REDIS_ALL', 0);
INSERT INTO `permission` VALUES (19, '新增缓存', '2018-12-17 13:53:44', 'REDIS_CREATE', 18);
INSERT INTO `permission` VALUES (20, '缓存查询', '2018-12-17 13:54:07', 'REDIS_SELECT', 18);
INSERT INTO `permission` VALUES (21, '缓存编辑', '2018-12-17 13:54:26', 'REDIS_EDIT', 18);
INSERT INTO `permission` VALUES (22, '缓存删除', '2018-12-17 13:55:04', 'REDIS_DELETE', 18);
INSERT INTO `permission` VALUES (23, '图床管理', '2018-12-27 20:31:49', 'PICTURE_ALL', 0);
INSERT INTO `permission` VALUES (24, '查询图片', '2018-12-27 20:32:04', 'PICTURE_SELECT', 23);
INSERT INTO `permission` VALUES (25, '上传图片', '2018-12-27 20:32:24', 'PICTURE_UPLOAD', 23);
INSERT INTO `permission` VALUES (26, '删除图片', '2018-12-27 20:32:45', 'PICTURE_DELETE', 23);
INSERT INTO `permission` VALUES (29, '菜单管理', '2018-12-28 17:34:31', 'MENU_ALL', 0);
INSERT INTO `permission` VALUES (30, '菜单查询', '2018-12-28 17:34:41', 'MENU_SELECT', 29);
INSERT INTO `permission` VALUES (31, '菜单创建', '2018-12-28 17:34:52', 'MENU_CREATE', 29);
INSERT INTO `permission` VALUES (32, '菜单编辑', '2018-12-28 17:35:20', 'MENU_EDIT', 29);
INSERT INTO `permission` VALUES (33, '菜单删除', '2018-12-28 17:35:29', 'MENU_DELETE', 29);

-- ----------------------------
-- Table structure for picture
-- ----------------------------
DROP TABLE IF EXISTS `picture`;
CREATE TABLE `picture`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NULL DEFAULT NULL,
  `delete_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `filename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `height` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `size` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `width` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qiniu_config
-- ----------------------------
DROP TABLE IF EXISTS `qiniu_config`;
CREATE TABLE `qiniu_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `accessKey` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bucket` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `host` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `secretKey` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `zone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qiniu_content
-- ----------------------------
DROP TABLE IF EXISTS `qiniu_content`;
CREATE TABLE `qiniu_content`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bucket` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `size` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updateTime` datetime NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '2018-11-23 11:04:37', '超级管理员', '超级管理员');
INSERT INTO `role` VALUES (2, '2018-11-23 13:09:06', '普通用户', '普通用户');

-- ----------------------------
-- Table structure for roles_permissions
-- ----------------------------
DROP TABLE IF EXISTS `roles_permissions`;
CREATE TABLE `roles_permissions`  (
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`, `permission_id`) USING BTREE,
  INDEX `FKboeuhl31go7wer3bpy6so7exi`(`permission_id`) USING BTREE,
  CONSTRAINT `FK4hrolwj4ned5i7qe8kyiaak6m` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKboeuhl31go7wer3bpy6so7exi` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of roles_permissions
-- ----------------------------
INSERT INTO `roles_permissions` VALUES (1, 1);
INSERT INTO `roles_permissions` VALUES (2, 3);
INSERT INTO `roles_permissions` VALUES (2, 8);
INSERT INTO `roles_permissions` VALUES (2, 14);
INSERT INTO `roles_permissions` VALUES (2, 19);
INSERT INTO `roles_permissions` VALUES (2, 23);
INSERT INTO `roles_permissions` VALUES (2, 30);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `createTime` datetime NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `enabled` bigint(20) NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `lastPasswordResetTime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_kpubos9gc2cvtkb0thktkbkes`(`email`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'https://i.loli.net/2018/12/31/5c297270b20e2.jpg', '2018-08-23 09:11:56', 'admin@qq.com', 1, '14e1b600b1fd579f47433b88e8d85291', 'admin', '2018-11-23 10:12:36');
INSERT INTO `user` VALUES (3, 'https://i.loli.net/2018/12/30/5c2871d6aa101.jpg', '2018-12-27 20:05:26', 'test@qq.com', 1, '14e1b600b1fd579f47433b88e8d85291', 'test', NULL);

-- ----------------------------
-- Table structure for users_roles
-- ----------------------------
DROP TABLE IF EXISTS `users_roles`;
CREATE TABLE `users_roles`  (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `FKq4eq273l04bpu4efj0jd0jb98`(`role_id`) USING BTREE,
  CONSTRAINT `users_roles_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `users_roles_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of users_roles
-- ----------------------------
INSERT INTO `users_roles` VALUES (1, 1);
INSERT INTO `users_roles` VALUES (3, 2);

-- ----------------------------
-- Table structure for verification_code
-- ----------------------------
DROP TABLE IF EXISTS `verification_code`;
CREATE TABLE `verification_code`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `createTime` datetime NULL DEFAULT NULL,
  `status` bit(1) NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scenes` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for visits
-- ----------------------------
DROP TABLE IF EXISTS `visits`;
CREATE TABLE `visits`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ip_counts` bigint(20) NULL DEFAULT NULL,
  `pv_counts` bigint(20) NULL DEFAULT NULL,
  `weekDay` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `createTime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
