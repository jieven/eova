/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50513
Source Host           : localhost:3306
Source Database       : web

Target Server Type    : MYSQL
Target Server Version : 50513
File Encoding         : 65001

Date: 2015-03-27 00:57:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `dict`
-- ----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `class` varchar(50) NOT NULL,
  `field` varchar(50) NOT NULL,
  `ext` varchar(255) NOT NULL DEFAULT '' COMMENT '扩展',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dict
-- ----------------------------
INSERT INTO `dict` VALUES ('1', '0', '正常', 'users', 'status', '');
INSERT INTO `dict` VALUES ('2', '1', '封号', 'users', 'status', '');
INSERT INTO `dict` VALUES ('3', '2', '禁言', 'users', 'status', '');
INSERT INTO `dict` VALUES ('4', '3', '删除', 'users', 'status', '');

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(2) DEFAULT '0' COMMENT '状态',
  `loginId` varchar(255) DEFAULT '' COMMENT '登录账户',
  `loginPwd` varchar(255) DEFAULT '' COMMENT '录登密码',
  `nickName` varchar(255) DEFAULT '' COMMENT '昵称',
  `regTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', '0', 'test1', '000000', '玩家10', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('2', '0', 'test2', '000000', '玩家11', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('3', '0', 'test3', '000000', '玩家12', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('4', '0', 'test4', '000000', '玩家13', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('5', '0', 'test5', '000000', '用户管理', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('6', '0', 'test6', '000000', '角色管理用', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('7', '0', 'test7', '000000', '用日志', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('8', '0', 'test8', '000000', '用户信息', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('9', '0', 'test9', '000000', '用户呵呵', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('10', '0', 'test10', '000000', '玩家10', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('11', '0', 'test11', '000000', '玩家11', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('12', '0', 'test12', '000000', '玩家12', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('13', '0', 'test13', '000000', '玩家13', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('14', '0', 'test14', '000000', '玩家14', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('15', '0', 'test15', '000000', '玩家15', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('16', '0', 'test16', '000000', '玩家16', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('17', '0', 'test17', '000000', '玩家17', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('18', '0', 'test18', '000000', '玩家18', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('19', '0', 'test19', '000000', '玩家19', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('20', '0', 'test20', '000000', '玩家20', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('21', '0', 'test21', '000000', '玩家21', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('22', '0', 'test22', '000000', '玩家22', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('23', '0', 'test23', '000000', '玩家23', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('24', '0', 'test24', '000000', '玩家24', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('25', '0', 'test25', '000000', '玩家25', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('26', '0', 'test26', '000000', '玩家26', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('27', '0', 'test27', '000000', '玩家27', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('28', '0', 'test28', '000000', '玩家28', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('29', '0', 'test29', '000000', '玩家29', '2014-12-30 00:55:49');
INSERT INTO `users` VALUES ('30', '0', 'test30', '000000', '玩家30', '2014-12-30 00:55:49');

-- ----------------------------
-- Table structure for `users_exp`
-- ----------------------------
DROP TABLE IF EXISTS `users_exp`;
CREATE TABLE `users_exp` (
  `uid` int(11) NOT NULL,
  `exp` int(11) DEFAULT '0' COMMENT '经验值',
  `avg` int(11) DEFAULT '0' COMMENT '年龄',
  `qq` varchar(255) DEFAULT '' COMMENT 'QQ',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users_exp
-- ----------------------------
INSERT INTO `users_exp` VALUES ('1', '0', '18', '1623736450');
INSERT INTO `users_exp` VALUES ('2', '0', '19', '1623736451');
INSERT INTO `users_exp` VALUES ('3', '0', '20', '1623736452');
INSERT INTO `users_exp` VALUES ('4', '0', '21', '1623736453');
INSERT INTO `users_exp` VALUES ('5', '0', '22', '1623736454');
INSERT INTO `users_exp` VALUES ('6', '0', '23', '1623736455');
INSERT INTO `users_exp` VALUES ('7', '0', '24', '1623736456');
INSERT INTO `users_exp` VALUES ('8', '0', '25', '1623736457');
INSERT INTO `users_exp` VALUES ('9', '0', '26', '1623736458');
INSERT INTO `users_exp` VALUES ('10', '0', '27', '1623736459');
INSERT INTO `users_exp` VALUES ('11', '0', '28', '1623736460');
INSERT INTO `users_exp` VALUES ('12', '0', '29', '1623736461');
INSERT INTO `users_exp` VALUES ('13', '0', '30', '1623736462');
INSERT INTO `users_exp` VALUES ('14', '0', '31', '1623736463');
INSERT INTO `users_exp` VALUES ('15', '0', '32', '1623736464');
INSERT INTO `users_exp` VALUES ('16', '0', '33', '1623736465');
INSERT INTO `users_exp` VALUES ('17', '0', '34', '1623736466');
INSERT INTO `users_exp` VALUES ('18', '0', '35', '1623736467');
INSERT INTO `users_exp` VALUES ('19', '0', '36', '1623736468');
INSERT INTO `users_exp` VALUES ('20', '0', '37', '1623736469');
INSERT INTO `users_exp` VALUES ('21', '0', '38', '1623736470');
INSERT INTO `users_exp` VALUES ('22', '0', '39', '1623736471');
INSERT INTO `users_exp` VALUES ('23', '0', '40', '1623736472');
INSERT INTO `users_exp` VALUES ('24', '0', '41', '1623736473');
INSERT INTO `users_exp` VALUES ('25', '0', '42', '1623736474');
INSERT INTO `users_exp` VALUES ('26', '0', '43', '1623736475');
INSERT INTO `users_exp` VALUES ('27', '0', '44', '1623736476');
INSERT INTO `users_exp` VALUES ('28', '0', '45', '1623736477');
INSERT INTO `users_exp` VALUES ('29', '0', '46', '1623736478');
INSERT INTO `users_exp` VALUES ('30', '0', '47', '1623736479');

-- ----------------------------
-- Table structure for `users_item`
-- ----------------------------
DROP TABLE IF EXISTS `users_item`;
CREATE TABLE `users_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `uid` int(11) NOT NULL COMMENT '用户ID',
  `name` varchar(255) DEFAULT '' COMMENT '道具名称',
  `num` int(11) DEFAULT '1' COMMENT '数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users_item
-- ----------------------------
INSERT INTO `users_item` VALUES ('1', '1', '皮鞭', '1');
INSERT INTO `users_item` VALUES ('2', '1', '蜡烛', '2');
INSERT INTO `users_item` VALUES ('3', '1', '绳子', '3');
