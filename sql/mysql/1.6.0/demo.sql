/*
Navicat MySQL Data Transfer

Source Server         : YUN-DB
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2018-04-05 23:13:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for area
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `pid` int(11) NOT NULL COMMENT '父级',
  `lv` int(11) NOT NULL COMMENT '级别：0=中国,1=省,2=市,3=区',
  PRIMARY KEY (`id`),
  KEY `pid_index` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=3409 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `id` int(5) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `full` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `mobilephone` varchar(255) DEFAULT NULL COMMENT '手机',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8 COMMENT='详细地址';

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES ('2', '张三2', '上海陆家嘴', '13524523888');
INSERT INTO `address` VALUES ('3', '张三3', '上海陆家嘴', '13524523888');
INSERT INTO `address` VALUES ('4', '张三4', '上海陆家嘴', '13524523888');

-- ----------------------------
-- Table structure for area_city
-- ----------------------------
DROP TABLE IF EXISTS `area_city`;
CREATE TABLE `area_city` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `code` varchar(11) NOT NULL COMMENT '父级编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COMMENT='地区城市';

-- ----------------------------
-- Records of area_city
-- ----------------------------
INSERT INTO `area_city` VALUES ('1', '中国', '0');
INSERT INTO `area_city` VALUES ('2', '直辖市', '1');
INSERT INTO `area_city` VALUES ('3', '安徽', '1');
INSERT INTO `area_city` VALUES ('4', '福建', '1');
INSERT INTO `area_city` VALUES ('5', '甘肃', '1');
INSERT INTO `area_city` VALUES ('6', '广东', '1');
INSERT INTO `area_city` VALUES ('7', '广西', '1');
INSERT INTO `area_city` VALUES ('8', '贵州', '1');
INSERT INTO `area_city` VALUES ('9', '海南', '1');
INSERT INTO `area_city` VALUES ('10', '河北', '1');
INSERT INTO `area_city` VALUES ('11', '河南', '1');
INSERT INTO `area_city` VALUES ('12', '黑龙江', '1');
INSERT INTO `area_city` VALUES ('13', '湖北', '1');
INSERT INTO `area_city` VALUES ('14', '湖南', '1');
INSERT INTO `area_city` VALUES ('15', '吉林', '1');
INSERT INTO `area_city` VALUES ('16', '江苏', '1');
INSERT INTO `area_city` VALUES ('17', '江西', '1');
INSERT INTO `area_city` VALUES ('18', '辽宁', '1');
INSERT INTO `area_city` VALUES ('19', '内蒙古', '1');
INSERT INTO `area_city` VALUES ('20', '宁夏', '1');
INSERT INTO `area_city` VALUES ('21', '青海', '1');
INSERT INTO `area_city` VALUES ('22', '山东', '1');
INSERT INTO `area_city` VALUES ('23', '山西', '1');
INSERT INTO `area_city` VALUES ('24', '陕西', '1');
INSERT INTO `area_city` VALUES ('25', '上海', '2');
INSERT INTO `area_city` VALUES ('26', '四川', '1');
INSERT INTO `area_city` VALUES ('27', '天津', '2');
INSERT INTO `area_city` VALUES ('28', '西藏', '1');
INSERT INTO `area_city` VALUES ('29', '新疆', '1');
INSERT INTO `area_city` VALUES ('30', '云南', '1');
INSERT INTO `area_city` VALUES ('31', '浙江', '1');
INSERT INTO `area_city` VALUES ('32', '重庆', '2');
INSERT INTO `area_city` VALUES ('33', '香港', '1');
INSERT INTO `area_city` VALUES ('34', '澳门', '1');
INSERT INTO `area_city` VALUES ('35', '台湾', '1');
INSERT INTO `area_city` VALUES ('42', '北京', '2');

-- ----------------------------
-- Table structure for data_login
-- ----------------------------
DROP TABLE IF EXISTS `data_login`;
CREATE TABLE `data_login` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `day` date DEFAULT NULL COMMENT '日期',
  `num` int(11) DEFAULT NULL COMMENT '每日活跃数',
  `num1` int(11) DEFAULT NULL COMMENT '每日登录数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of data_login
-- ----------------------------
INSERT INTO `data_login` VALUES ('1', '2016-02-01', '50', '30');
INSERT INTO `data_login` VALUES ('2', '2016-02-02', '55', '33');
INSERT INTO `data_login` VALUES ('3', '2016-02-03', '60', '34');
INSERT INTO `data_login` VALUES ('4', '2016-02-04', '65', '35');
INSERT INTO `data_login` VALUES ('5', '2016-02-05', '70', '36');
INSERT INTO `data_login` VALUES ('6', '2016-02-06', '75', '37');
INSERT INTO `data_login` VALUES ('7', '2016-02-07', '80', '38');
INSERT INTO `data_login` VALUES ('8', '2016-02-08', '85', '39');
INSERT INTO `data_login` VALUES ('9', '2016-02-09', '90', '40');
INSERT INTO `data_login` VALUES ('10', '2016-02-10', '95', '41');
INSERT INTO `data_login` VALUES ('11', '2016-02-11', '100', '42');
INSERT INTO `data_login` VALUES ('12', '2016-02-12', '105', '43');
INSERT INTO `data_login` VALUES ('13', '2016-02-13', '110', '44');
INSERT INTO `data_login` VALUES ('14', '2016-02-14', '115', '45');
INSERT INTO `data_login` VALUES ('15', '2016-02-15', '120', '46');
INSERT INTO `data_login` VALUES ('16', '2016-02-16', '125', '47');
INSERT INTO `data_login` VALUES ('17', '2016-02-17', '130', '48');
INSERT INTO `data_login` VALUES ('18', '2016-02-18', '135', '49');
INSERT INTO `data_login` VALUES ('19', '2016-02-19', '140', '50');
INSERT INTO `data_login` VALUES ('20', '2016-02-20', '145', '51');

-- ----------------------------
-- Table structure for data_money
-- ----------------------------
DROP TABLE IF EXISTS `data_money`;
CREATE TABLE `data_money` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `moon` varchar(11) DEFAULT NULL COMMENT '月份',
  `num` int(11) DEFAULT NULL COMMENT '手机销售额',
  `num1` int(11) DEFAULT NULL COMMENT '电脑销售额',
  `num2` int(11) DEFAULT NULL COMMENT '套子销售额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of data_money
-- ----------------------------
INSERT INTO `data_money` VALUES ('1', '1月', '10000', '10000', '50000');
INSERT INTO `data_money` VALUES ('2', '2月', '20000', '15000', '49000');
INSERT INTO `data_money` VALUES ('3', '3月', '30000', '20000', '48000');
INSERT INTO `data_money` VALUES ('4', '4月', '40000', '25000', '47000');
INSERT INTO `data_money` VALUES ('5', '5月', '50000', '30000', '46000');
INSERT INTO `data_money` VALUES ('6', '6月', '60000', '10000', '45000');
INSERT INTO `data_money` VALUES ('7', '7月', '70000', '15000', '44000');
INSERT INTO `data_money` VALUES ('8', '8月', '80000', '20000', '43000');
INSERT INTO `data_money` VALUES ('9', '9月', '90000', '25000', '42000');
INSERT INTO `data_money` VALUES ('10', '10月', '100000', '30000', '41000');

-- ----------------------------
-- Table structure for dicts
-- ----------------------------
DROP TABLE IF EXISTS `dicts`;
CREATE TABLE `dicts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` varchar(50) NOT NULL COMMENT '字典值',
  `name` varchar(50) NOT NULL COMMENT '字典中文',
  `object` varchar(50) NOT NULL COMMENT '表名',
  `field` varchar(50) NOT NULL COMMENT '字段名',
  `ext` varchar(255) DEFAULT '' COMMENT '扩展Json',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=212 DEFAULT CHARSET=utf8 COMMENT='数据字典';

-- ----------------------------
-- Records of dicts
-- ----------------------------
INSERT INTO `dicts` VALUES ('1', '1', '被套', 'product', 'category', '');
INSERT INTO `dicts` VALUES ('2', '2', '床单', 'product', 'category', '');
INSERT INTO `dicts` VALUES ('3', '3', '浴巾', 'product', 'category', '');
INSERT INTO `dicts` VALUES ('4', '4', '毛巾', 'product', 'category', '');
INSERT INTO `dicts` VALUES ('5', '5', '枕套', 'product', 'category', '');
INSERT INTO `dicts` VALUES ('6', '1', '纯棉', 'product', 'stuff', '');
INSERT INTO `dicts` VALUES ('7', '2', '涤纶', 'product', 'stuff', '');
INSERT INTO `dicts` VALUES ('8', '3', '纺纱', 'product', 'stuff', '');
INSERT INTO `dicts` VALUES ('9', '4', '轻纱', 'product', 'stuff', '');
INSERT INTO `dicts` VALUES ('10', '1', '90', 'product', 'size', '');
INSERT INTO `dicts` VALUES ('11', '2', '120', 'product', 'size', '');
INSERT INTO `dicts` VALUES ('12', '3', '150', 'product', 'size', '');
INSERT INTO `dicts` VALUES ('13', '4', '180', 'product', 'size', '');
INSERT INTO `dicts` VALUES ('14', '10', '待支付', 'orders', 'state', '');
INSERT INTO `dicts` VALUES ('15', '20', '已支付', 'orders', 'state', '');
INSERT INTO `dicts` VALUES ('16', '30', '已发货', 'orders', 'state', '');
INSERT INTO `dicts` VALUES ('17', '40', '已收货', 'orders', 'state', '');
INSERT INTO `dicts` VALUES ('33', '0', '正常', 'users', 'status', '');
INSERT INTO `dicts` VALUES ('34', '1', '封号', 'users', 'status', '');
INSERT INTO `dicts` VALUES ('35', '2', '禁言', 'users', 'status', '');
INSERT INTO `dicts` VALUES ('36', '3', '删除', 'users', 'status', '');
INSERT INTO `dicts` VALUES ('54', '0', '坦克', 'users', 'tag', '');
INSERT INTO `dicts` VALUES ('55', '1', 'ADC', 'users', 'tag', '');
INSERT INTO `dicts` VALUES ('56', '2', '打野', 'users', 'tag', '');
INSERT INTO `dicts` VALUES ('57', '3', 'AP', 'users', 'tag', '');
INSERT INTO `dicts` VALUES ('58', '0', '普通', 'test_info', 'status', '');
INSERT INTO `dicts` VALUES ('59', '1', '禁用', 'test_info', 'status', '');
INSERT INTO `dicts` VALUES ('60', '1', '潜水', 'test_info', 'tag', '');
INSERT INTO `dicts` VALUES ('61', '2', '冒泡', 'test_info', 'tag', '');
INSERT INTO `dicts` VALUES ('62', '3', '活跃', 'test_info', 'tag', '');
INSERT INTO `dicts` VALUES ('63', '4', '水货', 'test_info', 'tag', '');
INSERT INTO `dicts` VALUES ('64', '5', '喷子', 'test_info', 'tag', '');
INSERT INTO `dicts` VALUES ('65', '6', '山炮', 'test_info', 'tag', '');
INSERT INTO `dicts` VALUES ('148', '10', '待支付', 'v_orders', 'state', '');
INSERT INTO `dicts` VALUES ('149', '20', '已支付', 'v_orders', 'state', '');
INSERT INTO `dicts` VALUES ('150', '30', '已发货', 'v_orders', 'state', '');
INSERT INTO `dicts` VALUES ('151', '40', '已收货', 'v_orders', 'state', '');
INSERT INTO `dicts` VALUES ('158', '0', '国', 'area', 'lv', '');
INSERT INTO `dicts` VALUES ('159', '1', '省', 'area', 'lv', '');
INSERT INTO `dicts` VALUES ('160', '2', '市', 'area', 'lv', '');
INSERT INTO `dicts` VALUES ('161', '3', '区', 'area', 'lv', '');
INSERT INTO `dicts` VALUES ('198', '1', '普通商户', 'hotel', 'state', '');
INSERT INTO `dicts` VALUES ('199', '2', '签约商户', 'hotel', 'state', '');
INSERT INTO `dicts` VALUES ('204', '1', '正常', 'links', 'status', '');
INSERT INTO `dicts` VALUES ('205', '2', '禁用', 'links', 'status', '');
INSERT INTO `dicts` VALUES ('210', '1', '租赁商品', 'product', 'type', '');
INSERT INTO `dicts` VALUES ('211', '2', '积分商品', 'product', 'type', '');

-- ----------------------------
-- Table structure for hotel
-- ----------------------------
DROP TABLE IF EXISTS `hotel`;
CREATE TABLE `hotel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) NOT NULL COMMENT '酒店名',
  `tel` varchar(255) NOT NULL COMMENT '电话',
  `address` varchar(255) NOT NULL COMMENT '详细地址',
  `state` int(11) DEFAULT '1' COMMENT '用户状态：1=普通商户，2=签约商户',
  `score` int(11) DEFAULT '0' COMMENT '积分',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `province` int(11) DEFAULT NULL COMMENT '省',
  `city` int(11) DEFAULT NULL COMMENT '市',
  `region` int(11) DEFAULT NULL COMMENT '区',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='酒店';

-- ----------------------------
-- Records of hotel
-- ----------------------------
INSERT INTO `hotel` VALUES ('1', '松江锦江之星1', '(021)37621128', '上海市松江区西林北路950号1', '2', '12', '2015-12-04 00:00:00', '25', '321', '2705');
INSERT INTO `hotel` VALUES ('2', '松江锦江之星2', '(021)37621129', '上海市松江区西林北路951号', '1', '0', '2015-09-04 17:17:00', '2', '52', '505');
INSERT INTO `hotel` VALUES ('3', '松江锦江之星3', '(021)37621130', '上海市松江区西林北路952号', '2', '0', '2015-09-05 17:17:00', '3', '36', '398');

-- ----------------------------
-- Table structure for hotel_bed
-- ----------------------------
DROP TABLE IF EXISTS `hotel_bed`;
CREATE TABLE `hotel_bed` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `hotel_id` int(11) NOT NULL COMMENT '酒店',
  `sizes` int(2) NOT NULL COMMENT '床铺尺码',
  `num` int(11) DEFAULT '1' COMMENT '数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hotel_bed
-- ----------------------------
INSERT INTO `hotel_bed` VALUES ('2', '1', '2', '2');
INSERT INTO `hotel_bed` VALUES ('4', '2', '3', '100');

-- ----------------------------
-- Table structure for hotel_stock
-- ----------------------------
DROP TABLE IF EXISTS `hotel_stock`;
CREATE TABLE `hotel_stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `hotel_id` int(11) NOT NULL COMMENT '酒店',
  `category` int(2) NOT NULL COMMENT '商品类型',
  `num` int(11) DEFAULT '1' COMMENT '存货量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hotel_stock
-- ----------------------------
INSERT INTO `hotel_stock` VALUES ('1', '1', '1', '15');
INSERT INTO `hotel_stock` VALUES ('2', '1', '2', '11');
INSERT INTO `hotel_stock` VALUES ('6', '2', '1', '100');

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '名称',
  `info` varchar(1000) DEFAULT NULL COMMENT '介绍',
  `img` varchar(255) DEFAULT NULL COMMENT '物品图片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES ('1', '灭世者的死亡之帽', '<p>+140点法术强度。唯一被动：提升30%法术强度。11111</p>', '1449027976244.jpg');
INSERT INTO `item` VALUES ('2', '麦瑞德裂血手套', '<p>+40点攻击、+40%攻击速度、+25点护甲、唯一被动：物理攻击会造成目标最大生命值的4%的魔法伤害。</p>', null);
INSERT INTO `item` VALUES ('3', '多兰之盾', '<p>+120点生命值、+10点护甲、+8点生命回复/5秒</p>', null);
INSERT INTO `item` VALUES ('4', '黑色切割者', '<p>+55点攻击力、+30%攻击速度。唯一被动：物理攻击减少目标15点护甲，持续5秒（最多叠加3次）。</p>', null);
INSERT INTO `item` VALUES ('5', '长剑', '<p>+10点攻击力</p>', '1437496392574.gif');
INSERT INTO `item` VALUES ('6', '灵巧披风', '<p>+18%暴击几率</p>', '1437495216704.png');
INSERT INTO `item` VALUES ('7', '多兰之刃', '<p>+80点生命值、+10点攻击力、+3%生命偷取</p>', '1437494908238.png');

-- ----------------------------
-- Table structure for links
-- ----------------------------
DROP TABLE IF EXISTS `links`;
CREATE TABLE `links` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态:1=正常,2=禁用',
  `name` varchar(255) NOT NULL COMMENT '链接文本',
  `url` varchar(255) NOT NULL DEFAULT 'http://www..com' COMMENT '链接地址',
  `title` varchar(255) DEFAULT NULL COMMENT '小标题',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of links
-- ----------------------------
INSERT INTO `links` VALUES ('1', '1', '百度', 'http://www.baidu.com', '百度一下,你就知道');
INSERT INTO `links` VALUES ('2', '1', 'EOVA简单开发', 'http://www.eova.cn', '最简单的快速开发平台');
INSERT INTO `links` VALUES ('3', '1', 'EOVA简单开发1', 'http://www.eova.cn', '最简单的快速开发平台');

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `id` int(11) NOT NULL,
  `uid` int(11) DEFAULT NULL COMMENT 'EOVA系统用户ID',
  `rid` int(11) DEFAULT '0' COMMENT '冗余角色ID',
  `status` int(2) DEFAULT '0' COMMENT '状态',
  `nickname` varchar(30) DEFAULT '' COMMENT '昵称',
  `company_name` varchar(255) DEFAULT NULL COMMENT '单位名称',
  `mobile` varchar(11) DEFAULT NULL COMMENT '联系手机',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `phone2` varchar(255) DEFAULT NULL COMMENT '应急电话',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '注册时间',
  `province` int(11) DEFAULT NULL COMMENT '省',
  `city` int(11) DEFAULT NULL COMMENT '市',
  `region` int(11) DEFAULT NULL COMMENT '区',
  `admin_province` int(11) DEFAULT NULL COMMENT '管理省',
  `admin_city` int(11) DEFAULT NULL COMMENT '管理市',
  `admin_region` int(11) DEFAULT NULL COMMENT '管理区',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_nickname` (`nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息';

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES ('1', '1', '1', '0', '超级管理员', '', '135000000', 'eova@qq.com', '89BDF69372C2EF53EA409CDF020B5694', '', '', '2017-06-02 18:07:01', null, null, null, null, null, null);
INSERT INTO `member` VALUES ('2', '2', '2', '0', '系统管理员', '', '', null, null, '', '', '2017-06-02 18:07:01', null, null, null, null, null, null);
INSERT INTO `member` VALUES ('3', '3', '3', '0', '新省主管', '我是省长', '13524523406', null, null, '', '', '2017-06-02 18:07:02', '29', '0', '0', null, null, null);
INSERT INTO `member` VALUES ('4', '4', '3', '0', '乌市主管', '我是市长', '13524523415', null, null, '', '', '2017-06-02 18:07:02', '29', '351', '0', null, null, null);
INSERT INTO `member` VALUES ('5', '5', '3', '0', '天山主管', '我是区长', '13524523410', null, null, '', '', '2017-06-02 18:07:03', '29', '351', '3004', null, null, null);
INSERT INTO `member` VALUES ('10', null, '4', '0', '天山用户1', '上海静安小鲜肉', '13524523411', null, null, '', '', '2016-04-03 14:17:21', '29', '351', '3004', null, null, null);
INSERT INTO `member` VALUES ('11', null, '4', '0', '天山用户2', '上海静安小鲜肉', '13524523427', null, null, '', '', '2016-04-03 14:17:21', '29', '351', '3004', null, null, null);
INSERT INTO `member` VALUES ('12', null, '4', '0', '天山用户3', '上海小鲜肉', '13524523414', null, null, '', '', '2016-04-03 14:17:21', '29', '351', '3005', null, null, null);
INSERT INTO `member` VALUES ('13', null, '4', '0', '天山用户4', '上海小鲜肉', '13524523414', null, null, null, null, '2016-04-03 16:10:17', '29', '351', '3005', null, null, null);

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '编号',
  `order_id` int(11) NOT NULL COMMENT '订单ID',
  `product_id` int(11) NOT NULL COMMENT '产品ID',
  `product` varchar(128) NOT NULL COMMENT '产品',
  `price` double(10,0) NOT NULL DEFAULT '0' COMMENT '单价',
  `num` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '购买数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='订单项';

-- ----------------------------
-- Records of order_item
-- ----------------------------
INSERT INTO `order_item` VALUES ('1', '1', '3', '洹宾 40*40S店标大提花被套3', '10', '1');
INSERT INTO `order_item` VALUES ('2', '2', '18', '120床型纯棉四件套', '3', '2');
INSERT INTO `order_item` VALUES ('3', '2', '18', '120床型纯棉四件套', '3', '2');
INSERT INTO `order_item` VALUES ('4', '2', '18', '120床型纯棉四件套', '3', '2');
INSERT INTO `order_item` VALUES ('5', '3', '18', '120床型纯棉四件套', '3', '6');
INSERT INTO `order_item` VALUES ('6', '4', '19', '150床型纯棉四件套', '2', '1');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `hotel_id` int(11) DEFAULT '0' COMMENT '所属酒店',
  `pay_id` int(10) DEFAULT '0' COMMENT '支付ID',
  `state` int(3) unsigned DEFAULT '10' COMMENT '订单状态：10=待支付,20=已支付,30=已发货,40=已收货',
  `money` double(10,2) unsigned DEFAULT '0.00' COMMENT '应付金额',
  `score` double(10,2) unsigned DEFAULT '0.00' COMMENT '消耗积分',
  `memo` varchar(256) DEFAULT '' COMMENT '备注',
  `create_user_id` int(11) NOT NULL COMMENT '创建用户ID',
  `update_user_id` int(11) NOT NULL COMMENT '最后更新用户ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `is_invoice` tinyint(1) unsigned DEFAULT '0' COMMENT '是否开票',
  `additional_info` varchar(1000) DEFAULT NULL COMMENT '订单补充信息(JSON格式)',
  `address` varchar(500) DEFAULT NULL COMMENT '收货地址',
  `consignee` varchar(50) DEFAULT NULL COMMENT '收货人',
  `tel` varchar(20) DEFAULT NULL COMMENT '联系方式',
  `address_id` int(11) DEFAULT NULL COMMENT '收获地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='订单';

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('1', '1', '1', '30', '10.00', '0.00', '22222222222', '7', '7', '2015-10-11 21:46:12', '2015-10-11 21:46:12', '0', null, '上海市松江区西林北路950号', '松江锦江之星1', '(021)37621128', '1');
INSERT INTO `orders` VALUES ('2', '1', '2', '10', '6.00', '0.00', '', '21', '21', '2015-11-06 23:49:02', '2015-11-06 23:49:02', '0', null, 'gongyi', 'gongyi', 'gongyi', '2');
INSERT INTO `orders` VALUES ('3', '28', '3', '10', '18.00', '0.00', '', '24', '24', '2015-11-14 09:12:18', '2015-11-14 09:12:18', '0', null, '淮河路 ', '三系', '18155502888', '3');
INSERT INTO `orders` VALUES ('4', '1', '4', '10', '2.40', '0.00', '', '7', '7', '2015-11-15 21:19:16', '2015-11-15 21:19:16', '0', null, '上海市松江区西林北路950号', '客栈1号', '(021)37621128', '4');
INSERT INTO `orders` VALUES ('5', '0', '0', '10', '0.00', '0.00', '1', '100', '666', '2016-11-27 01:44:25', '2016-11-27 01:44:25', '0', null, null, null, null, '100');
INSERT INTO `orders` VALUES ('6', '0', '0', '10', '0.00', '0.00', '1111', '108', '108', '2016-11-27 02:07:09', '2016-11-27 02:07:09', '0', null, null, null, null, '107');
INSERT INTO `orders` VALUES ('7', '0', '0', '10', '0.00', '0.00', '21', '109', '109', '2016-11-27 02:07:55', '2016-11-27 02:07:55', '0', null, null, null, null, '108');
INSERT INTO `orders` VALUES ('8', '0', '0', '10', '0.00', '0.00', '1', '113', '113', '2016-11-27 22:10:07', '2016-11-27 22:10:07', '0', null, null, null, null, '112');
INSERT INTO `orders` VALUES ('9', '0', '0', '10', '0.00', '0.00', '11', '114', '114', '2016-11-27 22:14:33', '2016-11-27 22:14:33', '0', null, null, null, null, '113');
INSERT INTO `orders` VALUES ('10', '0', '0', '10', '0.00', '0.00', '11', '116', '116', '2016-11-29 01:21:51', '2016-11-29 01:21:51', '0', null, null, null, null, '117');
INSERT INTO `orders` VALUES ('11', '0', '0', '10', '0.00', '0.00', '1', '122', '122', '2016-11-29 01:42:41', '2016-11-29 01:42:41', '0', null, null, null, null, '123');
INSERT INTO `orders` VALUES ('12', '0', '0', '10', '0.00', '0.00', '5', '123', '123', '2016-11-29 01:42:54', '2016-11-29 01:42:54', '0', null, null, null, null, '124');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` int(2) DEFAULT '1' COMMENT '产品类型：1=租赁商品，2=积分商品',
  `category` int(2) NOT NULL COMMENT '分类',
  `stuff` int(2) NOT NULL COMMENT '材料',
  `sizes` int(2) NOT NULL COMMENT '尺码',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `img` varchar(255) DEFAULT NULL COMMENT '商品图片',
  `test_price` double DEFAULT '0' COMMENT '试用单价',
  `price` double DEFAULT '0' COMMENT '商品单价',
  `cost_score` int(11) DEFAULT '0' COMMENT '消耗积分：购买商品需消耗的积分',
  `score` int(11) DEFAULT '0' COMMENT '奖励积分',
  `stock` int(11) DEFAULT '1' COMMENT '库存',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('28', '1', '4', '1', '3', '150床型纯棉被套1', '1475741380036.png', '3', '2.4', '0', '7', '0', '2015-11-06 00:00:00', '2018-03-22 15:47:32');
INSERT INTO `product` VALUES ('29', '1', '4', '1', '3', '150床型纯棉被套1', '1451664266663.jpg', '3', '2.4', '0', '0', '5000', '2015-11-06 00:00:00', '2018-03-22 15:46:25');
INSERT INTO `product` VALUES ('30', '1', '7', '1', '0', '纯棉枕套1', '1449597463551.jpg', '3', '2.4', '0', '0', '5000', '2015-11-06 00:00:00', '2018-03-22 15:46:34');

-- ----------------------------
-- Table structure for sale_data
-- ----------------------------
DROP TABLE IF EXISTS `sale_data`;
CREATE TABLE `sale_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `city_id` int(11) NOT NULL COMMENT '城市ID',
  `name` varchar(255) NOT NULL COMMENT '商品',
  `money` double DEFAULT '0' COMMENT '销售额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sale_data
-- ----------------------------
INSERT INTO `sale_data` VALUES ('1', '25', '华为', '10');
INSERT INTO `sale_data` VALUES ('2', '25', '苹果', '11');
INSERT INTO `sale_data` VALUES ('3', '25', '小米', '12');
INSERT INTO `sale_data` VALUES ('4', '25', '三星', '13');
INSERT INTO `sale_data` VALUES ('5', '25', '魅族', '14');
INSERT INTO `sale_data` VALUES ('6', '25', '中兴', '15');
INSERT INTO `sale_data` VALUES ('7', '25', '乐视', '16');
INSERT INTO `sale_data` VALUES ('11', '42', '华为', '20');
INSERT INTO `sale_data` VALUES ('12', '42', '苹果', '21');
INSERT INTO `sale_data` VALUES ('13', '42', '小米', '22');
INSERT INTO `sale_data` VALUES ('14', '42', '三星', '23');
INSERT INTO `sale_data` VALUES ('15', '42', '魅族', '24');
INSERT INTO `sale_data` VALUES ('18', '42', '联想', '27');
INSERT INTO `sale_data` VALUES ('19', '42', '酷派', '28');
INSERT INTO `sale_data` VALUES ('20', '42', '黑莓', '29');
INSERT INTO `sale_data` VALUES ('21', '3', '华为', '30');
INSERT INTO `sale_data` VALUES ('22', '3', '苹果', '31');
INSERT INTO `sale_data` VALUES ('23', '3', '小米', '32');
INSERT INTO `sale_data` VALUES ('24', '3', '三星', '33');
INSERT INTO `sale_data` VALUES ('28', '3', '联想', '37');
INSERT INTO `sale_data` VALUES ('29', '3', '酷派', '38');
INSERT INTO `sale_data` VALUES ('30', '3', '黑莓', '39');
INSERT INTO `sale_data` VALUES ('31', '4', '华为', '40');
INSERT INTO `sale_data` VALUES ('32', '4', '苹果', '41');
INSERT INTO `sale_data` VALUES ('33', '4', '小米', '42');
INSERT INTO `sale_data` VALUES ('34', '4', '三星', '43');
INSERT INTO `sale_data` VALUES ('35', '4', '魅族', '44');
INSERT INTO `sale_data` VALUES ('36', '4', '中兴', '45');
INSERT INTO `sale_data` VALUES ('37', '4', '乐视', '46');
INSERT INTO `sale_data` VALUES ('38', '4', '联想', '47');
INSERT INTO `sale_data` VALUES ('39', '4', '酷派', '48');
INSERT INTO `sale_data` VALUES ('40', '4', '黑莓', '49');
INSERT INTO `sale_data` VALUES ('41', '4', '火腿', '1000');
INSERT INTO `sale_data` VALUES ('42', '27', '狗不理', '10000');
INSERT INTO `sale_data` VALUES ('43', '25', '土豆', '20');

-- ----------------------------
-- Table structure for test_info
-- ----------------------------
DROP TABLE IF EXISTS `test_info`;
CREATE TABLE `test_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户',
  `status` int(11) DEFAULT '0' COMMENT '状态：0=普通，1=禁用',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `memo` text COMMENT '备注',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `delete_flag` tinyint(1) DEFAULT NULL COMMENT '是否删除',
  `address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `id_card` varchar(255) DEFAULT NULL COMMENT '身份证图片',
  `update_time` date DEFAULT NULL COMMENT '更新日期',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `color` varchar(10) DEFAULT NULL COMMENT '颜值',
  `tag` varchar(255) DEFAULT NULL COMMENT '标签',
  `json` varchar(500) DEFAULT NULL COMMENT '配置信息',
  `test1` int(10) unsigned DEFAULT NULL,
  `test2` float DEFAULT NULL,
  `test3` double DEFAULT NULL,
  `test4` tinyint(1) DEFAULT NULL,
  `test5` bigint(5) unsigned DEFAULT NULL,
  `test6` tinyint(10) unsigned DEFAULT NULL,
  `test7` datetime DEFAULT NULL COMMENT 'datetime',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test_info
-- ----------------------------
INSERT INTO `test_info` VALUES ('1', '1', '0', 'Jieven', '17', '<p><img src=\"http://127.0.0.1/editor/1464194237135.png\" alt=\"\" style=\"max-width: 100%;\"><br></p><p>上海虹口区足球场上海虹口666</p><p><br></p>', '1475647037704.jpg', '0', 'eova20175201314', '1464002566632.jpg', '2016-05-26', '2018-06-29 15:05:59', '00000', '#000000', '3,2,4', '{\"domain\":\"http://www.eova.cn\"}', '21', '10.1', '35.11', '1', '0', null, '2016-10-08 02:39:34');
INSERT INTO `test_info` VALUES ('4', '2', '0', 'Eova', '20', '<p>哎哟不错哟！ 发个表情试试 <img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/6d/lovea_thumb.gif\"><br></p><p><br></p>', '1475655648414.png', '0', '423432423', '1464193847117.png', '2016-05-26', '2018-04-09 22:17:19', '000000', '#000000', '3', '', '0', '20.2', '40.22', null, '0', null, null);
INSERT INTO `test_info` VALUES ('7', '1', '1', '新增导入1', '17', '<p>222上海虹口区足球场上海虹口222</p><p><br></p>', '1477243093297.png', '0', '更新导入。。。。', '1464002566632.jpg', '2016-05-26', '2018-10-30 13:11:20', '00000', '#000000', '2,4', null, '0', '30.3', '50.33', null, '0', null, null);
INSERT INTO `test_info` VALUES ('8', '2', '1', '新增导入2', '30', '<p>哎哟不错哟！ 发个表情试试 <img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/6d/lovea_thumb.gif\"><br></p><p><br></p>', '1475647089038.jpg', '0', '6666', '1464193847117.png', '2016-05-26', '2018-10-30 13:11:30', '000000', '#000000', '3', null, '0', '40.4', '60.44', null, '0', null, null);
INSERT INTO `test_info` VALUES ('9', '2', '0', '卡卡西', '50', '<p>423</p>', '154087582470216395.jpg,154087620398730457.png,154087620398847633.png', '1', '4322344', '1475862732716.png', '2016-10-08', '2018-10-30 13:10:04', '423', '#000000', '2', null, '1', '50', '70', '1', '1', '1', null);

-- ----------------------------
-- Table structure for test01
-- ----------------------------
DROP TABLE IF EXISTS `test01`;
CREATE TABLE `test01` (
  `uuid` varchar(36) NOT NULL,
  `name` varchar(255) DEFAULT 'SB666',
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test01
-- ----------------------------
INSERT INTO `test01` VALUES ('190BB199C7E04C40909E401D45CC2E99', '21312312222', '2018-03-16 18:04:10');
INSERT INTO `test01` VALUES ('26c40fb4c0b6457cabbf14bafe90d6c0', '233431', '2017-10-26 13:59:45');
INSERT INTO `test01` VALUES ('56CB455A7EEA40A9BDD8B06056DD98F8', '344322343423432', '2018-03-16 18:04:41');
INSERT INTO `test01` VALUES ('56FAD143325D4C0E803863E236DED8A5', '111', '2018-03-16 17:59:55');
INSERT INTO `test01` VALUES ('5B1C42DCA7214300A4F168188C453260', '11111', '2018-03-16 17:54:09');
INSERT INTO `test01` VALUES ('985ad9044d0a4cfca8a8f4f4c9c15b13', '2222222221111', '2017-10-26 13:53:47');
INSERT INTO `test01` VALUES ('AFFE9831297E4C60AA4F9BD6FCD3AD3C', '1111', '2018-03-16 18:03:45');
INSERT INTO `test01` VALUES ('da2bb6dabec5436cb9545efd27d3318d', '333333333333', '2017-10-26 13:54:35');
INSERT INTO `test01` VALUES ('E2087AF386ED4C1097C4E823D6EA3CA8', '1111', '2018-03-16 17:59:27');
INSERT INTO `test01` VALUES ('ef0eb8ec679d408fa3497e3017461253', 'S666', '2017-10-26 13:58:50');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL,
  `rid` int(11) DEFAULT '0' COMMENT '冗余角色ID',
  `status` int(2) DEFAULT '0' COMMENT '状态',
  `nickname` varchar(30) DEFAULT '' COMMENT '昵称',
  `mobile` varchar(11) DEFAULT NULL COMMENT '联系手机',
  `province` int(11) DEFAULT NULL COMMENT '省',
  `city` int(11) DEFAULT NULL COMMENT '市',
  `region` int(11) DEFAULT NULL COMMENT '区',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户详细信息';

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', '1', '0', '超管', '1623736450', '0', '0', '0', '2016-04-03 14:16:52');
INSERT INTO `user_info` VALUES ('2', '2', '0', '系统管理员', '', null, null, null, '2016-04-03 14:16:52');
INSERT INTO `user_info` VALUES ('3', '3', '0', '专业测试', '13524523406', '25', '321', '2710', '2016-04-03 14:16:52');
INSERT INTO `user_info` VALUES ('4', '4', '0', '测试小白', '13524523488', '25', '321', '2710', '2016-11-21 00:19:08');
INSERT INTO `user_info` VALUES ('5', '2', '0', '', null, null, null, null, '2016-11-25 14:49:45');
INSERT INTO `user_info` VALUES ('7', '2', '0', '', null, null, null, null, '2016-11-25 14:51:23');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(2) DEFAULT '0' COMMENT '状态',
  `login_id` varchar(255) DEFAULT '' COMMENT '登录账户',
  `login_pwd` varchar(255) DEFAULT '' COMMENT '录登密码',
  `nickname` varchar(255) DEFAULT '' COMMENT '昵称',
  `reg_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `info` varchar(255) DEFAULT '' COMMENT '备注',
  `tag` varchar(255) DEFAULT '' COMMENT '标签',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('7', '1', 'test11', '000011', '德玛西亚之力', '2017-10-14 13:08:04', '这个用户是专业差评11', '11111');
INSERT INTO `users` VALUES ('9', '1', 'test22', '000000', '卡牌大师', '2014-12-30 00:55:49', '12', '0');
INSERT INTO `users` VALUES ('10', '0', 'test10', '000000', '堕落天使', '2014-12-30 00:55:49', '', '');
INSERT INTO `users` VALUES ('12', '0', 'test12', '000000', '熔岩巨兽', '2014-12-30 00:55:49', '11', '1');
INSERT INTO `users` VALUES ('13', '0', 'test13', '000000', '祖安狂人', '2014-12-30 00:55:49', '', '');
INSERT INTO `users` VALUES ('14', '0', 'test14', '000000', '钢铁大使', '2014-12-30 00:55:49', '', '');
INSERT INTO `users` VALUES ('15', '0', 'test15', '000000', '寡妇制造者', '2014-12-30 00:55:49', '', '3,2');
INSERT INTO `users` VALUES ('16', '0', 'test16', '000000', '时光守护者', '2014-12-30 00:55:49', '', '3');
INSERT INTO `users` VALUES ('17', '0', 'test17', '000000', '末日使者', '2014-12-30 00:55:49', '', '2,3');
INSERT INTO `users` VALUES ('18', '0', 'test18', '000000', '殇之木乃伊', '2014-12-30 00:55:49', '', '0,3');
INSERT INTO `users` VALUES ('19', '0', 'test19', '000000', '牛头酋长', '2014-12-30 00:55:49', '', '0');
INSERT INTO `users` VALUES ('20', '0', 'test20', '000000', '邪恶小法师', '2014-12-30 00:55:49', '', '3');
INSERT INTO `users` VALUES ('21', '0', 'test21', '000000', '风暴之怒', '2014-12-30 00:00:00', '', '3');
INSERT INTO `users` VALUES ('22', '0', 'test22', '000000', '麦林炮手', '2014-12-30 00:55:49', '', '1');
INSERT INTO `users` VALUES ('23', '1', 'test23', '000000', '黑暗之女', '2014-12-30 00:00:00', '333', '1');

-- ----------------------------
-- Table structure for users_exp
-- ----------------------------
DROP TABLE IF EXISTS `users_exp`;
CREATE TABLE `users_exp` (
  `users_id` int(11) NOT NULL,
  `exp` int(11) DEFAULT '0' COMMENT '经验值',
  `avg` int(11) DEFAULT '0' COMMENT '年龄',
  `qq` varchar(255) DEFAULT '' COMMENT 'QQ',
  PRIMARY KEY (`users_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users_exp
-- ----------------------------
INSERT INTO `users_exp` VALUES ('6', '1', '23', '1623736455');
INSERT INTO `users_exp` VALUES ('7', '1', '11', '11111');
INSERT INTO `users_exp` VALUES ('9', '22', '22', '22222');
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
INSERT INTO `users_exp` VALUES ('21', '0', '38', '1623736470');
INSERT INTO `users_exp` VALUES ('22', '0', '39', '1623736471');
INSERT INTO `users_exp` VALUES ('23', '0', '40', '1623736472');
INSERT INTO `users_exp` VALUES ('24', '0', '41', '1623736473');
INSERT INTO `users_exp` VALUES ('25', '0', '42', '1623736474');
INSERT INTO `users_exp` VALUES ('26', '0', '43', '1623736475');
INSERT INTO `users_exp` VALUES ('27', '0', '44', '1623736476');
INSERT INTO `users_exp` VALUES ('28', '0', '45', '1623736477');
INSERT INTO `users_exp` VALUES ('29', '0', '1', '100000');
INSERT INTO `users_exp` VALUES ('30', '213', '1', '10000');
INSERT INTO `users_exp` VALUES ('31', '213', '1', '145454');

-- ----------------------------
-- Table structure for users_item
-- ----------------------------
DROP TABLE IF EXISTS `users_item`;
CREATE TABLE `users_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `users_id` int(11) NOT NULL COMMENT '艺人',
  `item_id` int(11) NOT NULL COMMENT '道具',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users_item
-- ----------------------------
INSERT INTO `users_item` VALUES ('1', '7', '3');
INSERT INTO `users_item` VALUES ('2', '27', '2');
INSERT INTO `users_item` VALUES ('3', '9', '5');
INSERT INTO `users_item` VALUES ('4', '10', '2');
INSERT INTO `users_item` VALUES ('5', '11', '3');
INSERT INTO `users_item` VALUES ('6', '23', '4');


-- ----------------------------
-- View
-- ----------------------------
DROP VIEW IF EXISTS `users_total`;
CREATE VIEW `users_total` AS SELECT
	`users`.`status` AS `status`,
	count(`users`.`status`) AS `num`
FROM
	`users`
GROUP BY
	`users`.`status`;

DROP VIEW IF EXISTS `v_orders`;
CREATE VIEW `v_orders` AS SELECT
	`o`.`id` AS `id`,
	`o`.`state` AS `state`,
	`o`.`money` AS `money`,
	`o`.`memo` AS `memo`,
	`o`.`update_user_id` AS `update_user_id`,
	`o`.`create_user_id` AS `create_user_id`,
	`o`.`create_time` AS `create_time`,
	`o`.`update_time` AS `update_time`,
	`o`.`address_id` AS `address_id`,
	`a`.`name` AS `name`,
	`a`.`full` AS `full`,
	`a`.`mobilephone` AS `mobilephone`,
	`u`.`login_id` AS `login_id`,
	`u`.`nickname` AS `nickname`,
	`u`.`info` AS `info`
FROM
	(
		(
			`orders` `o`
			LEFT JOIN `users` `u` ON (
				(
					`o`.`create_user_id` = `u`.`id`
				)
			)
		)
		LEFT JOIN `address` `a` ON (
			(`o`.`address_id` = `a`.`id`)
		)
	);

DROP VIEW IF EXISTS `v_userinfo`;
CREATE VIEW `v_userinfo` AS SELECT
	`u`.`id` AS `id`,
	`u`.`status` AS `status`,
	`u`.`login_id` AS `login_id`,
	`u`.`login_pwd` AS `login_pwd`,
	`u`.`nickname` AS `nickname`,
	`u`.`reg_time` AS `reg_time`,
	`u`.`info` AS `info`,
	`u`.`tag` AS `tag`,
	`ue`.`exp` AS `exp`,
	`ue`.`avg` AS `avg`,
	`ue`.`qq` AS `qq`
FROM
	(
		`users` `u`
		JOIN `users_exp` `ue`
	)
WHERE
	(`u`.`id` = `ue`.`users_id`);
