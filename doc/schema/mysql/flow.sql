# ************************************************************
# Sequel Ace SQL dump
# 版本号： 20067
#
# https://sequel-ace.com/
# https://github.com/Sequel-Ace/Sequel-Ace
#
# 主机: localhost (MySQL 8.3.0)
# 数据库: flow
# 生成时间: 2024-05-19 13:26:45 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE='NO_AUTO_VALUE_ON_ZERO', SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# 转储表 tb_user_relational_database
# ------------------------------------------------------------

DROP TABLE IF EXISTS `tb_user_relational_database`;

CREATE TABLE `tb_user_relational_database` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `rid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user` bigint DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'VALID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `info` text COLLATE utf8mb4_general_ci,
  `create_time` bigint DEFAULT NULL,
  `update_time` bigint DEFAULT NULL,
  `expire_time` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



# 转储表 tb_user_relational_table
# ------------------------------------------------------------

DROP TABLE IF EXISTS `tb_user_relational_table`;

CREATE TABLE `tb_user_relational_table` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `tid` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `db` bigint DEFAULT NULL,
  `user` bigint DEFAULT NULL,
  `name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `info` text COLLATE utf8mb4_general_ci,
  `status` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `schema_info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `create_time` bigint DEFAULT NULL,
  `update_time` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



# 转储表 tb_user_setting
# ------------------------------------------------------------

DROP TABLE IF EXISTS `tb_user_setting`;

CREATE TABLE `tb_user_setting` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `user` bigint DEFAULT NULL,
  `data` text COLLATE utf8mb4_general_ci,
  `create_time` bigint DEFAULT NULL,
  `update_time` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



# 转储表 tb_user_vector_database
# ------------------------------------------------------------

DROP TABLE IF EXISTS `tb_user_vector_database`;

CREATE TABLE `tb_user_vector_database` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `vid` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user` bigint DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'VALID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `info` text COLLATE utf8mb4_general_ci,
  `embedding_size` int DEFAULT '1536',
  `embedding_model` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'text-embedding-ada-002',
  `create_time` bigint DEFAULT NULL,
  `update_time` bigint DEFAULT NULL,
  `expire_time` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



# 转储表 tb_workflow
# ------------------------------------------------------------

DROP TABLE IF EXISTS `tb_workflow`;

CREATE TABLE `tb_workflow` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `wid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user` bigint DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `title` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `brief` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `images` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `tags` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `language` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `version` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `fast_access` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0',
  `create_time` bigint DEFAULT NULL,
  `update_time` bigint DEFAULT NULL,
  `expire_time` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



# 转储表 tb_workflow_run_record
# ------------------------------------------------------------

DROP TABLE IF EXISTS `tb_workflow_run_record`;

CREATE TABLE `tb_workflow_run_record` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `rid` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `wid` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `schedule_time` bigint DEFAULT NULL,
  `start_time` bigint DEFAULT NULL,
  `end_time` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



# 转储表 tb_workflow_template
# ------------------------------------------------------------

DROP TABLE IF EXISTS `tb_workflow_template`;

CREATE TABLE `tb_workflow_template` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `tid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user` bigint DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `title` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `brief` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `images` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `tags` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `version` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `is_official` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `official_order` int DEFAULT NULL,
  `create_time` bigint DEFAULT NULL,
  `update_time` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
