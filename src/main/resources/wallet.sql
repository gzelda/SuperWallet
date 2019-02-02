/*
Navicat MySQL Data Transfer

Source Server         : aws-rds-mysql
Source Server Version : 50722
Source Host           : superwallet.couot2aumoqo.us-east-2.rds.amazonaws.com:3306
Source Database       : superwallet

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-02-02 13:16:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for appupdate
-- ----------------------------
DROP TABLE IF EXISTS `appupdate`;
CREATE TABLE `appupdate` (
  `appPath` varchar(255) NOT NULL,
  `appVersion` varchar(255) NOT NULL,
  `appLog` varchar(255) NOT NULL,
  `isForce` int(11) NOT NULL,
  `versionSize` varchar(255) NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bguser
-- ----------------------------
DROP TABLE IF EXISTS `bguser`;
CREATE TABLE `bguser` (
  `bid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `realName` varchar(20) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `state` tinyint(4) NOT NULL,
  `admin` bit(1) NOT NULL,
  PRIMARY KEY (`bid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bguser_role
-- ----------------------------
DROP TABLE IF EXISTS `bguser_role`;
CREATE TABLE `bguser_role` (
  `bid` bigint(20) NOT NULL,
  `rid` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for eostoken
-- ----------------------------
DROP TABLE IF EXISTS `eostoken`;
CREATE TABLE `eostoken` (
  `UID` char(100) NOT NULL,
  `EOSAccountName` varchar(50) DEFAULT NULL,
  `amount` double NOT NULL,
  `type` int(11) NOT NULL,
  `canLock` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`UID`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ethtoken
-- ----------------------------
DROP TABLE IF EXISTS `ethtoken`;
CREATE TABLE `ethtoken` (
  `UID` char(100) NOT NULL,
  `ETHAddress` varchar(50) DEFAULT NULL,
  `amount` double NOT NULL,
  `canLock` tinyint(4) NOT NULL DEFAULT '0',
  `type` int(11) NOT NULL,
  PRIMARY KEY (`UID`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for ethvalidation
-- ----------------------------
DROP TABLE IF EXISTS `ethvalidation`;
CREATE TABLE `ethvalidation` (
  `UID` varchar(100) NOT NULL,
  `transferId` bigint(20) NOT NULL,
  `hashValue` varchar(100) NOT NULL,
  `status` int(11) NOT NULL,
  `nonce` int(11) NOT NULL,
  PRIMARY KEY (`UID`,`transferId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `fid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `UID` char(100) NOT NULL,
  `createTime` datetime NOT NULL,
  `content` varchar(255) NOT NULL,
  `contact` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gamelist
-- ----------------------------
DROP TABLE IF EXISTS `gamelist`;
CREATE TABLE `gamelist` (
  `gid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `gameName` varchar(255) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `text` text,
  `link` varchar(255) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  `joindate` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `sort` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for inviter
-- ----------------------------
DROP TABLE IF EXISTS `inviter`;
CREATE TABLE `inviter` (
  `inviterID` varchar(100) NOT NULL,
  `beinvitedID` varchar(100) NOT NULL,
  `invitingTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`inviterID`,`beinvitedID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for lockwarehouse
-- ----------------------------
DROP TABLE IF EXISTS `lockwarehouse`;
CREATE TABLE `lockwarehouse` (
  `UID` char(100) NOT NULL,
  `LID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `period` int(10) unsigned NOT NULL,
  `createdTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `tokenType` int(11) NOT NULL,
  `finalProfit` double DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0',
  `profitTokenType` int(11) NOT NULL,
  PRIMARY KEY (`LID`,`UID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `mid` bigint(20) NOT NULL AUTO_INCREMENT,
  `text` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT '0',
  PRIMARY KEY (`mid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for menu_permission
-- ----------------------------
DROP TABLE IF EXISTS `menu_permission`;
CREATE TABLE `menu_permission` (
  `mid` bigint(20) NOT NULL COMMENT '菜单id',
  `pid` bigint(20) DEFAULT NULL COMMENT '权限id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification` (
  `nid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `UID` char(100) NOT NULL,
  `title` varchar(100) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `notice` text,
  PRIMARY KEY (`nid`,`UID`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for optconf
-- ----------------------------
DROP TABLE IF EXISTS `optconf`;
CREATE TABLE `optconf` (
  `confName` varchar(30) NOT NULL,
  `confValue` varchar(255) NOT NULL,
  PRIMARY KEY (`confName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `pid` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `resource` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for preinviter
-- ----------------------------
DROP TABLE IF EXISTS `preinviter`;
CREATE TABLE `preinviter` (
  `phoneNum` varchar(30) NOT NULL,
  `invitedCode` varchar(50) NOT NULL,
  PRIMARY KEY (`phoneNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for profit
-- ----------------------------
DROP TABLE IF EXISTS `profit`;
CREATE TABLE `profit` (
  `UID` varchar(100) NOT NULL,
  `PID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `orderID` varchar(100) DEFAULT NULL,
  `profitType` int(11) NOT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `profit` double NOT NULL,
  PRIMARY KEY (`PID`,`UID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `rid` bigint(20) NOT NULL AUTO_INCREMENT,
  `sn` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`rid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `rid` bigint(20) NOT NULL,
  `mid` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `rid` bigint(20) NOT NULL,
  `pid` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for systemlog
-- ----------------------------
DROP TABLE IF EXISTS `systemlog`;
CREATE TABLE `systemlog` (
  `sid` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `opuserId` bigint(20) NOT NULL,
  `optime` datetime NOT NULL,
  `ipaddr` varchar(255) DEFAULT NULL,
  `function` varchar(255) NOT NULL,
  `opusername` varchar(20) NOT NULL,
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=147 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for textlist
-- ----------------------------
DROP TABLE IF EXISTS `textlist`;
CREATE TABLE `textlist` (
  `tid` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  `text` mediumtext,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for transfer
-- ----------------------------
DROP TABLE IF EXISTS `transfer`;
CREATE TABLE `transfer` (
  `UID` char(100) NOT NULL,
  `transferId` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `source` varchar(50) NOT NULL,
  `destination` varchar(50) NOT NULL,
  `amount` double NOT NULL,
  `transferType` tinyint(4) NOT NULL,
  `tokenType` tinyint(4) NOT NULL,
  `createdTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL,
  PRIMARY KEY (`transferId`,`UID`)
) ENGINE=InnoDB AUTO_INCREMENT=316 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for userbasic
-- ----------------------------
DROP TABLE IF EXISTS `userbasic`;
CREATE TABLE `userbasic` (
  `UID` char(100) NOT NULL,
  `nickName` varchar(20) NOT NULL,
  `sex` tinyint(4) NOT NULL,
  `isAgency` tinyint(4) NOT NULL,
  `headPhoto` varchar(100) DEFAULT NULL,
  `phoneNumber` char(15) NOT NULL,
  `inviter` char(100) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `passWord` varchar(100) DEFAULT NULL,
  `payPassWord` varchar(100) DEFAULT NULL,
  `invitedCode` varchar(100) DEFAULT NULL,
  `registerTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`UID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for userprivate
-- ----------------------------
DROP TABLE IF EXISTS `userprivate`;
CREATE TABLE `userprivate` (
  `UID` char(100) NOT NULL,
  `realName` varchar(20) NOT NULL,
  `IDCardNumber` char(20) NOT NULL,
  `IDCardFront` blob NOT NULL,
  `IDCardBack` blob NOT NULL,
  `face` blob NOT NULL,
  PRIMARY KEY (`UID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for userstatus
-- ----------------------------
DROP TABLE IF EXISTS `userstatus`;
CREATE TABLE `userstatus` (
  `UID` char(100) NOT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `updatedTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`UID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for withdrawmoney
-- ----------------------------
DROP TABLE IF EXISTS `withdrawmoney`;
CREATE TABLE `withdrawmoney` (
  `UID` char(100) NOT NULL,
  `WID` char(100) NOT NULL,
  `tokenType` tinyint(4) NOT NULL,
  `amount` double NOT NULL,
  `createdTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL,
  `auditor` varchar(100) DEFAULT NULL,
  `auditTime` timestamp NULL DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`UID`,`WID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
