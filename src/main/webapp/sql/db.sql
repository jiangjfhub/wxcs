-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.6.26-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.1.0.4867
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 wxsv 的数据库结构
CREATE DATABASE IF NOT EXISTS `wxsv` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `wxsv`;


-- 导出  表 wxsv.wx_user 结构
CREATE TABLE IF NOT EXISTS `wx_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `pass_word` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `create_date` DATETIME NOT NULL COMMENT '创建时间',
  `expire_date` DATETIME NOT NULL COMMENT '过期时间',
  `state` tinyint(4) DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表';

-- 正在导出表  wxsv.wx_user 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `wx_user` DISABLE KEYS */;
INSERT INTO `wx_user` (`id`, `name`, `pass_word`, `state`) VALUES
	(1, 'jiangjf', '123', 1);
/*!40000 ALTER TABLE `wx_user` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

CREATE TABLE `bt_resource` (
	`id` INT(5) NOT NULL AUTO_INCREMENT,
	`bt_code` VARCHAR(50) NOT NULL,
	`bt_name` TEXT NULL,
	`bt_actor` TEXT NULL,
	`description` TEXT NULL COMMENT '描述',
	`img_url` VARCHAR(250) NULL DEFAULT NULL,
	`released_date` DATETIME NULL DEFAULT NULL,
	`create_date` DATETIME NULL DEFAULT NULL,
	`state` TINYINT(4) NOT NULL DEFAULT '1',
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=580
;

CREATE TABLE `resource_link` (
	`id` INT(5) NOT NULL AUTO_INCREMENT,
	`resource_id` INT(11) NOT NULL,
	`link_name` TEXT NOT NULL,
	`href_link` TEXT NOT NULL,
	`magnet_link` TEXT NOT NULL,
	`size` VARCHAR(255) NULL DEFAULT NULL,
	`convert_date` DATETIME NULL DEFAULT NULL,
	`create_date` DATETIME NULL DEFAULT NULL,
	`state` TINYINT(4) NOT NULL DEFAULT '1',
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=424
;


