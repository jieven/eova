DROP SCHEMA IF EXISTS web CASCADE;
DROP USER web;
CREATE USER web WITH PASSWORD 'web' NOSUPERUSER ;
CREATE SCHEMA IF NOT EXISTS AUTHORIZATION web;
SET SEARCH_PATH TO 'web';
-- ----------------------------
-- Table structure for dict
-- ----------------------------
DROP TABLE IF EXISTS dict;
CREATE TABLE dict(
 id Integer NOT NULL,
 value Character varying(50) NOT NULL,
 name Character varying(50) NOT NULL,
 class Character varying(50) NOT NULL,
 field Character varying(50) NOT NULL,
 ext Character varying(255) DEFAULT '' NOT NULL
)
WITH (OIDS=FALSE)
;
COMMENT ON COLUMN dict.ext IS '扩展'
;
-- Add keys for table dict

ALTER TABLE dict ADD CONSTRAINT PK_dict PRIMARY KEY (id)
;
DROP SEQUENCE IF EXISTS dict_seq;
CREATE SEQUENCE dict_seq START WITH 5 INCREMENT BY 1;
ALTER TABLE dict ALTER id SET DEFAULT NEXTVAL('dict_seq');
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
CREATE TABLE item(
 id Integer NOT NULL,
 name Character varying(255) NOT NULL,
 info Character varying(1000) DEFAULT NULL
)
WITH (OIDS=FALSE)
;
COMMENT ON COLUMN item.name IS '名称'
;
COMMENT ON COLUMN item.info IS '介绍'
;

-- Add keys for table item

ALTER TABLE item ADD CONSTRAINT PK_item PRIMARY KEY (id)
;
DROP SEQUENCE IF EXISTS item_seq;
CREATE SEQUENCE item_seq START WITH 7 INCREMENT BY 1;
ALTER TABLE item ALTER id SET DEFAULT NEXTVAL('item_seq');
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
CREATE TABLE users(
 id Integer NOT NULL,
 status Integer DEFAULT '0',
 loginId Character varying(255) DEFAULT '',
 loginPwd Character varying(255) DEFAULT '',
 nickName Character varying(255) DEFAULT '',
 regTime Timestamp(9) DEFAULT CURRENT_TIMESTAMP,
 info Character varying(255) DEFAULT ''
)
WITH (OIDS=FALSE)
;
COMMENT ON COLUMN users.status IS '状态'
;
COMMENT ON COLUMN users.loginId IS '登录账户'
;
COMMENT ON COLUMN users.loginPwd IS '录登密码'
;
COMMENT ON COLUMN users.nickName IS '昵称'
;
COMMENT ON COLUMN users.regTime IS '注册时间'
;
COMMENT ON COLUMN users.info IS '备注'
;
-- Add keys for table users

ALTER TABLE users ADD CONSTRAINT PK_users PRIMARY KEY (id)
;
DROP SEQUENCE IF EXISTS users_seq;
CREATE SEQUENCE users_seq START WITH 84 INCREMENT BY 1;
ALTER TABLE users ALTER id SET DEFAULT NEXTVAL('users_seq');
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
CREATE TABLE users_exp(
 uid Integer NOT NULL,
 exp Integer DEFAULT '0',
 avg Integer DEFAULT '0',
 qq Character varying(255) DEFAULT ''
)
WITH (OIDS=FALSE)
;
COMMENT ON COLUMN users_exp.exp IS '经验值'
;
COMMENT ON COLUMN users_exp.avg IS '年龄'
;
COMMENT ON COLUMN users_exp.qq IS 'QQ'
;

-- Add keys for table users_exp

ALTER TABLE users_exp ADD CONSTRAINT PK_users_exp PRIMARY KEY (uid)
;
DROP SEQUENCE IF EXISTS users_exp_seq;
CREATE SEQUENCE users_exp_seq START WITH 31 INCREMENT BY 1;
ALTER TABLE users_exp ALTER uid SET DEFAULT NEXTVAL('users_exp_seq');

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
CREATE TABLE users_item(
 id Integer NOT NULL,
 usersId Integer NOT NULL,
 itemId Integer NOT NULL
)
WITH (OIDS=FALSE)
;
COMMENT ON COLUMN users_item.id IS 'ID'
;
COMMENT ON COLUMN users_item.usersId IS '艺人'
;
COMMENT ON COLUMN users_item.itemId IS '道具'
;

-- Add keys for table users_item

ALTER TABLE users_item ADD CONSTRAINT PK_users_item PRIMARY KEY (id)
;
DROP SEQUENCE IF EXISTS users_item_seq;
CREATE SEQUENCE users_item_seq START WITH 8 INCREMENT BY 1;
ALTER TABLE users_item ALTER id SET DEFAULT NEXTVAL('users_item_seq');
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
CREATE VIEW v_users AS
SELECT id AS id, status AS status, loginId AS loginId, loginPwd AS loginPwd, nickName AS nickName, regTime AS regTime, info AS info, uid AS uid, exp AS exp, avg AS avg, qq AS qq
FROM users, users_exp
WHERE (users.id = users_exp.uid)
;