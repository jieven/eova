
-- ----------------------------
-- Table structure for dict
-- ----------------------------
DROP TABLE IF EXISTS dict;
CREATE TABLE dict (
  id int(11) NOT NULL AUTO_INCREMENT,
  value varchar(50) NOT NULL,
  name varchar(50) NOT NULL,
  class varchar(50) NOT NULL,
  field varchar(50) NOT NULL,
  ext varchar(255) NOT NULL DEFAULT '' COMMENT '扩展',
  PRIMARY KEY (id)
) AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dict
-- ----------------------------
INSERT INTO dict VALUES ('1', '0', '正常', 'users', 'status', '');
INSERT INTO dict VALUES ('2', '1', '封号', 'users', 'status', '');
INSERT INTO dict VALUES ('3', '2', '禁言', 'users', 'status', '');
INSERT INTO dict VALUES ('4', '3', '删除', 'users', 'status', '');

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS item;
CREATE TABLE item (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL COMMENT '名称',
  info varchar(1000) DEFAULT NULL COMMENT '介绍',
  PRIMARY KEY (id)
) AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO item VALUES ('1', '皮鞭', '鞭子不仅仅可以驭马');
INSERT INTO item VALUES ('2', '蜡烛', '蜡烛不再是照明工具');
INSERT INTO item VALUES ('3', '红绳', '绳子不再是生活用品');
INSERT INTO item VALUES ('4', '跳跳糖', '跳跳糖不再是童年的回忆');
INSERT INTO item VALUES ('5', '果冻', '果冻不仅可以吃');
INSERT INTO item VALUES ('6', '黄瓜', '<p>黄瓜不再是蔬菜佛挡杀佛第三方惹我让432432423</p>');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id int(11) NOT NULL AUTO_INCREMENT,
  status int(2) DEFAULT '0' COMMENT '状态',
  loginId varchar(255) DEFAULT '' COMMENT '登录账户',
  loginPwd varchar(255) DEFAULT '' COMMENT '录登密码',
  nickName varchar(255) DEFAULT '' COMMENT '昵称',
  regTime timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  info varchar(255) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (id)
) AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO users VALUES ('1', '0', 'test1', '000000', '小鸠美爱', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('2', '0', 'test2', '000000', '浅仓舞', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('3', '0', 'test3', '000000', '饭岛爱', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('4', '0', 'test4', '000000', '小泽圆', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('5', '0', 'test5', '000000', '光月夜也', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('6', '0', 'test6', '000000', '吉野纱莉', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('7', '0', 'test7', '000000', '葵实野理', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('8', '0', 'test8', '000000', '安倍夏树', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('9', '0', 'test9', '000000', '及川奈央', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('10', '0', 'test10', '000000', '早坂瞳', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('11', '0', 'test11', '000000', '萩原沙耶香', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('12', '0', 'test12', '000000', '大泽惠', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('13', '0', 'test13', '000000', '星崎未来', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('14', '0', 'test14', '000000', '西田美沙', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('15', '0', 'test15', '000000', '大石彩香', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('16', '0', 'test16', '000000', '桃井望', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('17', '0', 'test17', '000000', '柳田弥生', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('18', '0', 'test18', '000000', '伊东怜', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('19', '0', 'test19', '000000', '青木玲', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('20', '0', 'test20', '000000', '黑木麻衣', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('21', '0', 'test21', '000000', '天衣美津', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('22', '0', 'test22', '000000', '二宫沙树', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('23', '0', 'test23', '000000', '小峰由衣', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('24', '0', 'test24', '000000', '桃子美优', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('25', '213', 'test25', '000000', '萩原舞', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('26', '432423', 'test26', '000000', '小川麻美', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('27', '43242', 'test27', '000000', '小泽玛丽亚', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('28', '344233242', 'test28', '000000', '天海丽', '2014-12-01 00:10:49', '4324324234');
INSERT INTO users VALUES ('33', '3211', '2323', '', '稲森しほり', '2015-05-07 19:59:01', '');
INSERT INTO users VALUES ('34', '12311', '231', '', '早川濑里奈', '2015-05-07 23:28:17', '');
INSERT INTO users VALUES ('35', '0', 'test2111', '000000', '阿立未来', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('36', '1', 'test3111', '000000', '姬野爱', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('37', '1', 'test4111', '000000', '佐藤江梨花', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('38', '0', 'test2111', '000000', '稻森诗织', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('39', '0', 'test3111', '000000', '北条麻妃', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('40', '0', 'test4111', '000000', '前嶋美步', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('41', '0', 'test2111', '000000', '加护范子', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('42', '0', 'test3111', '000000', '爱原翼', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('43', '0', 'test4111', '000000', '石黑京香', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('44', '0', 'test2111', '000000', '村上里沙', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('45', '0', 'test3111', '000000', '心有花', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('46', '0', 'test4111', '000000', '明日花绮罗', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('47', '0', 'test2111', '000000', '鲇川奈绪', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('48', '0', 'test3111', '000000', '月野姬', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('49', '0', 'test4111', '000000', '伊藤青叶', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('50', '0', 'test2111', '000000', '仁科沙也加', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('51', '0', 'test3111', '000000', '花井美沙', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('52', '0', 'test4111', '000000', '赤西凉', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('53', '0', 'test2111', '000000', '美优千奈', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('54', '0', 'test3111', '000000', '绫濑美音', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('55', '0', 'test4111', '000000', '白鸟凉子', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('56', '0', 'test2111', '000000', '日向真昼', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('57', '0', 'test3111', '000000', '爱叶渚', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('58', '0', 'test4111', '000000', '筱原凉', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('59', '1', 'test2111', '000000', '石原莉奈', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('60', '0', 'test3111', '000000', '朝日奈明', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('61', '0', 'test4111', '000000', '石川铃华', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('62', '0', 'test2111', '000000', '原明奈', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('63', '0', 'test3111', '000000', '栗林里莉', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('64', '0', 'test4111', '000000', '莲井志帆', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('65', '0', 'test2111', '000000', '松下怜', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('66', '0', 'test3111', '000000', '羽月希', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('67', '0', 'test4111', '000000', '大槻响', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('68', '0', 'test2111', '000000', '伊东遥', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('69', '0', 'test3111', '000000', '雨宫琴音', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('70', '0', 'test4111', '000000', '坂田美影', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('71', '0', 'test2111', '000000', '松生彩', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('72', '0', '333', '000000', '樱井莉亚', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('73', '0', '222', '000000', '森美咲', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('74', '0', '11122', '000000', '并木优', '2014-12-30 00:55:49', '');
INSERT INTO users VALUES ('84', '0', 'test2', '000000', '浅仓舞', '2014-12-30 00:55:49', '11111');

-- ----------------------------
-- Table structure for users_exp
-- ----------------------------
DROP TABLE IF EXISTS users_exp;
CREATE TABLE users_exp (
  uid int(11) NOT NULL,
  exp int(11) DEFAULT '0' COMMENT '经验值',
  avg int(11) DEFAULT '0' COMMENT '年龄',
  qq varchar(255) DEFAULT '' COMMENT 'QQ',
  PRIMARY KEY (uid)
) DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users_exp
-- ----------------------------
INSERT INTO users_exp VALUES ('1', '0', '18', '1623736450');
INSERT INTO users_exp VALUES ('2', '0', '19', '1623736451');
INSERT INTO users_exp VALUES ('3', '0', '20', '1623736452');
INSERT INTO users_exp VALUES ('4', '0', '21', '1623736453');
INSERT INTO users_exp VALUES ('5', '0', '22', '1623736454');
INSERT INTO users_exp VALUES ('6', '0', '23', '1623736455');
INSERT INTO users_exp VALUES ('7', '0', '24', '1623736456');
INSERT INTO users_exp VALUES ('8', '0', '25', '1623736457');
INSERT INTO users_exp VALUES ('9', '0', '26', '1623736458');
INSERT INTO users_exp VALUES ('10', '0', '27', '1623736459');
INSERT INTO users_exp VALUES ('11', '0', '28', '1623736460');
INSERT INTO users_exp VALUES ('12', '0', '29', '1623736461');
INSERT INTO users_exp VALUES ('13', '0', '30', '1623736462');
INSERT INTO users_exp VALUES ('14', '0', '31', '1623736463');
INSERT INTO users_exp VALUES ('15', '0', '32', '1623736464');
INSERT INTO users_exp VALUES ('16', '0', '33', '1623736465');
INSERT INTO users_exp VALUES ('17', '0', '34', '1623736466');
INSERT INTO users_exp VALUES ('18', '0', '35', '1623736467');
INSERT INTO users_exp VALUES ('19', '0', '36', '1623736468');
INSERT INTO users_exp VALUES ('20', '0', '37', '1623736469');
INSERT INTO users_exp VALUES ('21', '0', '38', '1623736470');
INSERT INTO users_exp VALUES ('22', '0', '39', '1623736471');
INSERT INTO users_exp VALUES ('23', '0', '40', '1623736472');
INSERT INTO users_exp VALUES ('24', '0', '41', '1623736473');
INSERT INTO users_exp VALUES ('25', '0', '42', '1623736474');
INSERT INTO users_exp VALUES ('26', '0', '43', '1623736475');
INSERT INTO users_exp VALUES ('27', '0', '44', '1623736476');
INSERT INTO users_exp VALUES ('28', '0', '45', '1623736477');
INSERT INTO users_exp VALUES ('30', '0', '47', '1623736479');

-- ----------------------------
-- Table structure for users_item
-- ----------------------------
DROP TABLE IF EXISTS users_item;
CREATE TABLE users_item (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  usersId int(11) NOT NULL COMMENT '艺人',
  itemId int(11) NOT NULL COMMENT '道具',
  PRIMARY KEY (id)
) AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users_item
-- ----------------------------
INSERT INTO users_item VALUES ('1', '1', '1');
INSERT INTO users_item VALUES ('2', '1', '2');
INSERT INTO users_item VALUES ('3', '2', '5');
INSERT INTO users_item VALUES ('4', '2', '2');
INSERT INTO users_item VALUES ('5', '2', '3');
INSERT INTO users_item VALUES ('6', '4', '4');
INSERT INTO users_item VALUES ('7', '10', '1');

-- ----------------------------
-- View structure for v_users
-- ----------------------------
DROP VIEW IF EXISTS v_users;
CREATE VIEW v_users AS select users.id AS id,users.status AS status,users.loginId AS loginId,users.loginPwd AS loginPwd,users.nickName AS nickName,users.regTime AS regTime,users.info AS info,users_exp.uid AS uid,users_exp.exp AS exp,users_exp.avg AS avg,users_exp.qq AS qq from (users join users_exp) where (users.id = users_exp.uid) ;
