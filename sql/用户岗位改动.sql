-- ----------------------------
-- Table structure for sys_users_jobs
-- ----------------------------
DROP TABLE IF EXISTS `sys_users_jobs`;
CREATE TABLE `sys_users_jobs` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `job_id` bigint(20) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`,`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- sys_user 需要将岗位信息移入 sys_users_jobs 表中，这里不提供移入脚本--
ALTER TABLE sys_user DROP job_id;