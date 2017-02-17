/*
Navicat MySQL Data Transfer

Source Server         : YUN-DB
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : demo

Target Server Type    : ORACLE
Target Server Version : 100100
File Encoding         : 65001

Date: 2016-12-18 00:28:46
*/

set define off;

BEGIN
	
-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO address VALUES ('1', '111', '111', '11111111');
INSERT INTO address VALUES ('2', '张三2', '上海陆家嘴', '13524523888');
INSERT INTO address VALUES ('3', '张三3', '上海陆家嘴', '13524523888');
INSERT INTO address VALUES ('4', '张三4', '上海陆家嘴', '13524523888');
INSERT INTO address VALUES ('100', '1111111111111', '11111', '11111111111111');
INSERT INTO address VALUES ('107', '11', '1', '111');
INSERT INTO address VALUES ('108', '1111', '111', '11');
INSERT INTO address VALUES ('109', '王麻子', '王麻子', '王麻子');
INSERT INTO address VALUES ('110', '王麻子1', '王麻子1', '王麻子1');
INSERT INTO address VALUES ('111', '1111', '2222', '111111111111111');
INSERT INTO address VALUES ('112', '1', '1', '1');
INSERT INTO address VALUES ('113', '11', '1111111', '11111111111111');
INSERT INTO address VALUES ('114', '111', '111', '111');
INSERT INTO address VALUES ('115', '111', '111', '111');
INSERT INTO address VALUES ('116', '111', '111', '111');
INSERT INTO address VALUES ('117', '1', '111111', '11111111111111');
INSERT INTO address VALUES ('123', '111', '111111', '11111111111111');
INSERT INTO address VALUES ('124', '55555555', '555555555', '5555555');
INSERT INTO address VALUES ('125', '111', '111', '111');

-- ----------------------------
-- Records of area_city
-- ----------------------------
INSERT INTO area_city VALUES ('1', '中国', '0');
INSERT INTO area_city VALUES ('2', '直辖市', '1');
INSERT INTO area_city VALUES ('3', '安徽', '1');
INSERT INTO area_city VALUES ('4', '福建', '1');
INSERT INTO area_city VALUES ('5', '甘肃', '1');
INSERT INTO area_city VALUES ('6', '广东', '1');
INSERT INTO area_city VALUES ('7', '广西', '1');
INSERT INTO area_city VALUES ('8', '贵州', '1');
INSERT INTO area_city VALUES ('9', '海南', '1');
INSERT INTO area_city VALUES ('10', '河北', '1');
INSERT INTO area_city VALUES ('11', '河南', '1');
INSERT INTO area_city VALUES ('12', '黑龙江', '1');
INSERT INTO area_city VALUES ('13', '湖北', '1');
INSERT INTO area_city VALUES ('14', '湖南', '1');
INSERT INTO area_city VALUES ('15', '吉林', '1');
INSERT INTO area_city VALUES ('16', '江苏', '1');
INSERT INTO area_city VALUES ('17', '江西', '1');
INSERT INTO area_city VALUES ('18', '辽宁', '1');
INSERT INTO area_city VALUES ('19', '内蒙古', '1');
INSERT INTO area_city VALUES ('20', '宁夏', '1');
INSERT INTO area_city VALUES ('21', '青海', '1');
INSERT INTO area_city VALUES ('22', '山东', '1');
INSERT INTO area_city VALUES ('23', '山西', '1');
INSERT INTO area_city VALUES ('24', '陕西', '1');
INSERT INTO area_city VALUES ('25', '上海', '2');
INSERT INTO area_city VALUES ('26', '四川', '1');
INSERT INTO area_city VALUES ('27', '天津', '2');
INSERT INTO area_city VALUES ('28', '西藏', '1');
INSERT INTO area_city VALUES ('29', '新疆', '1');
INSERT INTO area_city VALUES ('30', '云南', '1');
INSERT INTO area_city VALUES ('31', '浙江', '1');
INSERT INTO area_city VALUES ('32', '重庆', '2');
INSERT INTO area_city VALUES ('33', '香港', '1');
INSERT INTO area_city VALUES ('34', '澳门', '1');
INSERT INTO area_city VALUES ('35', '台湾', '1');
INSERT INTO area_city VALUES ('42', '北京', '2');

-- ----------------------------
-- Records of data_login
-- ----------------------------
INSERT INTO data_login VALUES ('1', TO_TIMESTAMP('2016-02-01', 'YYYY-MM-DD HH24:MI:SS'), '50', '30');
INSERT INTO data_login VALUES ('2', TO_TIMESTAMP('2016-02-02', 'YYYY-MM-DD HH24:MI:SS'), '55', '33');
INSERT INTO data_login VALUES ('3', TO_TIMESTAMP('2016-02-03', 'YYYY-MM-DD HH24:MI:SS'), '60', '34');
INSERT INTO data_login VALUES ('4', TO_TIMESTAMP('2016-02-04', 'YYYY-MM-DD HH24:MI:SS'), '65', '35');
INSERT INTO data_login VALUES ('5', TO_TIMESTAMP('2016-02-05', 'YYYY-MM-DD HH24:MI:SS'), '70', '36');
INSERT INTO data_login VALUES ('6', TO_TIMESTAMP('2016-02-06', 'YYYY-MM-DD HH24:MI:SS'), '75', '37');
INSERT INTO data_login VALUES ('7', TO_TIMESTAMP('2016-02-07', 'YYYY-MM-DD HH24:MI:SS'), '80', '38');
INSERT INTO data_login VALUES ('8', TO_TIMESTAMP('2016-02-08', 'YYYY-MM-DD HH24:MI:SS'), '85', '39');
INSERT INTO data_login VALUES ('9', TO_TIMESTAMP('2016-02-09', 'YYYY-MM-DD HH24:MI:SS'), '90', '40');
INSERT INTO data_login VALUES ('10', TO_TIMESTAMP('2016-02-10', 'YYYY-MM-DD HH24:MI:SS'), '95', '41');
INSERT INTO data_login VALUES ('11', TO_TIMESTAMP('2016-02-11', 'YYYY-MM-DD HH24:MI:SS'), '100', '42');
INSERT INTO data_login VALUES ('12', TO_TIMESTAMP('2016-02-12', 'YYYY-MM-DD HH24:MI:SS'), '105', '43');
INSERT INTO data_login VALUES ('13', TO_TIMESTAMP('2016-02-13', 'YYYY-MM-DD HH24:MI:SS'), '110', '44');
INSERT INTO data_login VALUES ('14', TO_TIMESTAMP('2016-02-14', 'YYYY-MM-DD HH24:MI:SS'), '115', '45');
INSERT INTO data_login VALUES ('15', TO_TIMESTAMP('2016-02-15', 'YYYY-MM-DD HH24:MI:SS'), '120', '46');
INSERT INTO data_login VALUES ('16', TO_TIMESTAMP('2016-02-16', 'YYYY-MM-DD HH24:MI:SS'), '125', '47');
INSERT INTO data_login VALUES ('17', TO_TIMESTAMP('2016-02-17', 'YYYY-MM-DD HH24:MI:SS'), '130', '48');
INSERT INTO data_login VALUES ('18', TO_TIMESTAMP('2016-02-18', 'YYYY-MM-DD HH24:MI:SS'), '135', '49');
INSERT INTO data_login VALUES ('19', TO_TIMESTAMP('2016-02-19', 'YYYY-MM-DD HH24:MI:SS'), '140', '50');
INSERT INTO data_login VALUES ('20', TO_TIMESTAMP('2016-02-20', 'YYYY-MM-DD HH24:MI:SS'), '145', '51');

-- ----------------------------
-- Records of data_money
-- ----------------------------
INSERT INTO data_money VALUES ('1', '1月', '10000', '10000', '50000');
INSERT INTO data_money VALUES ('2', '2月', '20000', '15000', '49000');
INSERT INTO data_money VALUES ('3', '3月', '30000', '20000', '48000');
INSERT INTO data_money VALUES ('4', '4月', '40000', '25000', '47000');
INSERT INTO data_money VALUES ('5', '5月', '50000', '30000', '46000');
INSERT INTO data_money VALUES ('6', '6月', '60000', '10000', '45000');
INSERT INTO data_money VALUES ('7', '7月', '70000', '15000', '44000');
INSERT INTO data_money VALUES ('8', '8月', '80000', '20000', '43000');
INSERT INTO data_money VALUES ('9', '9月', '90000', '25000', '42000');
INSERT INTO data_money VALUES ('10', '10月', '100000', '30000', '41000');

-- ----------------------------
-- Records of dicts
-- ----------------------------
INSERT INTO dicts VALUES ('1', '1', '被套', 'product', 'category', '');
INSERT INTO dicts VALUES ('2', '2', '床单', 'product', 'category', '');
INSERT INTO dicts VALUES ('3', '3', '浴巾', 'product', 'category', '');
INSERT INTO dicts VALUES ('4', '4', '毛巾', 'product', 'category', '');
INSERT INTO dicts VALUES ('5', '5', '枕套', 'product', 'category', '');
INSERT INTO dicts VALUES ('6', '1', '纯棉', 'product', 'stuff', '');
INSERT INTO dicts VALUES ('7', '2', '涤纶', 'product', 'stuff', '');
INSERT INTO dicts VALUES ('8', '3', '纺纱', 'product', 'stuff', '');
INSERT INTO dicts VALUES ('9', '4', '轻纱', 'product', 'stuff', '');
INSERT INTO dicts VALUES ('10', '1', '90', 'product', 'size', '');
INSERT INTO dicts VALUES ('11', '2', '120', 'product', 'size', '');
INSERT INTO dicts VALUES ('12', '3', '150', 'product', 'size', '');
INSERT INTO dicts VALUES ('13', '4', '180', 'product', 'size', '');
INSERT INTO dicts VALUES ('14', '10', '待支付', 'orders', 'state', '');
INSERT INTO dicts VALUES ('15', '20', '已支付', 'orders', 'state', '');
INSERT INTO dicts VALUES ('16', '30', '已发货', 'orders', 'state', '');
INSERT INTO dicts VALUES ('17', '40', '已收货', 'orders', 'state', '');
INSERT INTO dicts VALUES ('20', '1', '租赁商品', 'product', 'type', '');
INSERT INTO dicts VALUES ('21', '2', '积分商品', 'product', 'type', '');
INSERT INTO dicts VALUES ('28', '1', '在线租赁', 'payment', 'pay_business', '');
INSERT INTO dicts VALUES ('29', '1', '支付宝', 'payment', 'platform', '');
INSERT INTO dicts VALUES ('30', '2', '微信', 'payment', 'platform', '');
INSERT INTO dicts VALUES ('33', '0', '正常', 'users', 'status', '');
INSERT INTO dicts VALUES ('34', '1', '封号', 'users', 'status', '');
INSERT INTO dicts VALUES ('35', '2', '禁言', 'users', 'status', '');
INSERT INTO dicts VALUES ('36', '3', '删除', 'users', 'status', '');
INSERT INTO dicts VALUES ('54', '0', '坦克', 'users', 'tag', '');
INSERT INTO dicts VALUES ('55', '1', 'ADC', 'users', 'tag', '');
INSERT INTO dicts VALUES ('56', '2', '打野', 'users', 'tag', '');
INSERT INTO dicts VALUES ('57', '3', 'AP', 'users', 'tag', '');
INSERT INTO dicts VALUES ('58', '0', '普通', 'test_info', 'status', '');
INSERT INTO dicts VALUES ('59', '1', '禁用', 'test_info', 'status', '');
INSERT INTO dicts VALUES ('60', '1', '潜水', 'test_info', 'tag', '');
INSERT INTO dicts VALUES ('61', '2', '冒泡', 'test_info', 'tag', '');
INSERT INTO dicts VALUES ('62', '3', '活跃', 'test_info', 'tag', '');
INSERT INTO dicts VALUES ('63', '4', '水货', 'test_info', 'tag', '');
INSERT INTO dicts VALUES ('64', '5', '喷子', 'test_info', 'tag', '');
INSERT INTO dicts VALUES ('65', '6', '山炮', 'test_info', 'tag', '');
INSERT INTO dicts VALUES ('148', '10', '待支付', 'v_orders', 'state', '');
INSERT INTO dicts VALUES ('149', '20', '已支付', 'v_orders', 'state', '');
INSERT INTO dicts VALUES ('150', '30', '已发货', 'v_orders', 'state', '');
INSERT INTO dicts VALUES ('151', '40', '已收货', 'v_orders', 'state', '');
INSERT INTO dicts VALUES ('152', '1', '正常', 'links', 'status', '');
INSERT INTO dicts VALUES ('153', '2', '禁用', 'links', 'status', '');
INSERT INTO dicts VALUES ('158', '0', '国', 'area', 'lv', '');
INSERT INTO dicts VALUES ('159', '1', '省', 'area', 'lv', '');
INSERT INTO dicts VALUES ('160', '2', '市', 'area', 'lv', '');
INSERT INTO dicts VALUES ('161', '3', '区', 'area', 'lv', '');
INSERT INTO dicts VALUES ('172', '1', '普通商户', 'hotel', 'state', '');
INSERT INTO dicts VALUES ('173', '2', '签约商户', 'hotel', 'state', '');

-- ----------------------------
-- Records of hotel
-- ----------------------------
INSERT INTO hotel VALUES ('1', '松江锦江之星1', '(021)37621128', '上海市松江区西林北路950号', '2', '12', TO_TIMESTAMP('2015-12-04 17:01:30', 'YYYY-MM-DD HH24:MI:SS'), '25', '321', '2705');
INSERT INTO hotel VALUES ('2', '松江锦江之星2', '(021)37621129', '上海市松江区西林北路951号', '1', '0', TO_TIMESTAMP('2015-09-04 17:17:00', 'YYYY-MM-DD HH24:MI:SS'), '2', '52', '505');
INSERT INTO hotel VALUES ('3', '松江锦江之星3', '(021)37621130', '上海市松江区西林北路952号', '2', '0', TO_TIMESTAMP('2015-09-05 17:17:00', 'YYYY-MM-DD HH24:MI:SS'), '3', '36', '398');

-- ----------------------------
-- Records of hotel_bed
-- ----------------------------
INSERT INTO hotel_bed VALUES ('2', '1', '2', '2');
INSERT INTO hotel_bed VALUES ('3', '1', '3', '2');
INSERT INTO hotel_bed VALUES ('4', '2', '3', '100');

-- ----------------------------
-- Records of hotel_stock
-- ----------------------------
INSERT INTO hotel_stock VALUES ('1', '1', '1', '15');
INSERT INTO hotel_stock VALUES ('2', '1', '2', '11');
INSERT INTO hotel_stock VALUES ('6', '2', '1', '100');

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO item VALUES ('1', '灭世者的死亡之帽', '<p>+140点法术强度。唯一被动：提升30%法术强度。11111</p>', '1449027976244.jpg');
INSERT INTO item VALUES ('2', '麦瑞德裂血手套', '<p>+40点攻击、+40%攻击速度、+25点护甲、唯一被动：物理攻击会造成目标最大生命值的4%的魔法伤害。</p>', null);
INSERT INTO item VALUES ('3', '多兰之盾', '<p>+120点生命值、+10点护甲、+8点生命回复/5秒</p>', null);
INSERT INTO item VALUES ('4', '黑色切割者', '<p>+55点攻击力、+30%攻击速度。唯一被动：物理攻击减少目标15点护甲，持续5秒（最多叠加3次）。</p>', null);
INSERT INTO item VALUES ('5', '长剑', '<p>+10点攻击力</p>', '1437496392574.gif');
INSERT INTO item VALUES ('6', '灵巧披风', '<p>+18%暴击几率</p>', '1437495216704.png');
INSERT INTO item VALUES ('7', '多兰之刃', '<p>+80点生命值、+10点攻击力、+3%生命偷取</p>', '1437494908238.png');

-- ----------------------------
-- Records of links
-- ----------------------------
INSERT INTO links VALUES ('1', '1', '百度', 'http://www.baidu.com', '百度一下,你就知道');
INSERT INTO links VALUES ('2', '1', 'EOVA简单开发', 'http://www.eova.cn', '最简单的快速开发平台');

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO member VALUES ('1', '1', '0', '超级管理员', '', '', '', '', TO_TIMESTAMP('2016-04-03 14:16:52', 'YYYY-MM-DD HH24:MI:SS'), null, null, null, null, null, null);
INSERT INTO member VALUES ('2', '2', '0', '系统管理员', '', '', '', '', TO_TIMESTAMP('2016-04-03 14:16:52', 'YYYY-MM-DD HH24:MI:SS'), null, null, null, null, null, null);
INSERT INTO member VALUES ('3', '3', '0', '新省主管', '我是省长', '13524523406', '', '', TO_TIMESTAMP('2016-04-03 14:16:52', 'YYYY-MM-DD HH24:MI:SS'), '29', '0', '0', null, null, null);
INSERT INTO member VALUES ('4', '3', '0', '乌市主管', '我是市长', '13524523415', '', '', TO_TIMESTAMP('2016-04-03 14:17:21', 'YYYY-MM-DD HH24:MI:SS'), '29', '351', '0', null, null, null);
INSERT INTO member VALUES ('5', '3', '0', '天山主管', '我是区长', '13524523410', '', '', TO_TIMESTAMP('2016-04-03 14:17:21', 'YYYY-MM-DD HH24:MI:SS'), '29', '351', '3004', null, null, null);
INSERT INTO member VALUES ('10', '4', '0', '天山用户1', '上海静安小鲜肉', '13524523411', '', '', TO_TIMESTAMP('2016-04-03 14:17:21', 'YYYY-MM-DD HH24:MI:SS'), '29', '351', '3004', null, null, null);
INSERT INTO member VALUES ('11', '4', '0', '天山用户2', '上海静安小鲜肉', '13524523427', '', '', TO_TIMESTAMP('2016-04-03 14:17:21', 'YYYY-MM-DD HH24:MI:SS'), '29', '351', '3004', null, null, null);
INSERT INTO member VALUES ('12', '4', '0', '天山用户3', '上海小鲜肉', '13524523414', '', '', TO_TIMESTAMP('2016-04-03 14:17:21', 'YYYY-MM-DD HH24:MI:SS'), '29', '351', '3005', null, null, null);
INSERT INTO member VALUES ('13', '4', '0', '天山用户4', '上海小鲜肉', '13524523414', null, null, TO_TIMESTAMP('2016-04-03 16:10:17', 'YYYY-MM-DD HH24:MI:SS'), '29', '351', '3005', null, null, null);

-- ----------------------------
-- Records of order_item
-- ----------------------------
INSERT INTO order_item VALUES ('1', '1', '3', '洹宾 40*40S店标大提花被套3', '10', '1');
INSERT INTO order_item VALUES ('2', '2', '18', '120床型纯棉四件套', '3', '2');
INSERT INTO order_item VALUES ('3', '2', '18', '120床型纯棉四件套', '3', '2');
INSERT INTO order_item VALUES ('4', '2', '18', '120床型纯棉四件套', '3', '2');
INSERT INTO order_item VALUES ('5', '3', '18', '120床型纯棉四件套', '3', '6');
INSERT INTO order_item VALUES ('6', '4', '19', '150床型纯棉四件套', '2', '1');

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO orders VALUES ('1', '1', '1', '30', '10.00', '0.00', '22222222222', '7', '7', TO_TIMESTAMP('2015-10-11 21:46:12', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2015-10-11 21:46:12', 'YYYY-MM-DD HH24:MI:SS'), '0', null, '上海市松江区西林北路950号', '松江锦江之星1', '(021)37621128', '1');
INSERT INTO orders VALUES ('2', '1', '2', '10', '6.00', '0.00', '', '21', '21', TO_TIMESTAMP('2015-11-06 23:49:02', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2015-11-06 23:49:02', 'YYYY-MM-DD HH24:MI:SS'), '0', null, 'gongyi', 'gongyi', 'gongyi', '2');
INSERT INTO orders VALUES ('3', '28', '3', '10', '18.00', '0.00', '', '24', '24', TO_TIMESTAMP('2015-11-14 09:12:18', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2015-11-14 09:12:18', 'YYYY-MM-DD HH24:MI:SS'), '0', null, '淮河路 ', '三系', '18155502888', '3');
INSERT INTO orders VALUES ('4', '1', '4', '10', '2.40', '0.00', '', '7', '7', TO_TIMESTAMP('2015-11-15 21:19:16', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2015-11-15 21:19:16', 'YYYY-MM-DD HH24:MI:SS'), '0', null, '上海市松江区西林北路950号', '客栈1号', '(021)37621128', '4');
INSERT INTO orders VALUES ('5', '0', '0', '10', '0.00', '0.00', '1', '100', '666', TO_TIMESTAMP('2016-11-27 01:44:25', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2016-11-27 01:44:25', 'YYYY-MM-DD HH24:MI:SS'), '0', null, null, null, null, '100');
INSERT INTO orders VALUES ('6', '0', '0', '10', '0.00', '0.00', '1111', '108', '108', TO_TIMESTAMP('2016-11-27 02:07:09', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2016-11-27 02:07:09', 'YYYY-MM-DD HH24:MI:SS'), '0', null, null, null, null, '107');
INSERT INTO orders VALUES ('7', '0', '0', '10', '0.00', '0.00', '21', '109', '109', TO_TIMESTAMP('2016-11-27 02:07:55', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2016-11-27 02:07:55', 'YYYY-MM-DD HH24:MI:SS'), '0', null, null, null, null, '108');
INSERT INTO orders VALUES ('8', '0', '0', '10', '0.00', '0.00', '1', '113', '113', TO_TIMESTAMP('2016-11-27 22:10:07', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2016-11-27 22:10:07', 'YYYY-MM-DD HH24:MI:SS'), '0', null, null, null, null, '112');
INSERT INTO orders VALUES ('9', '0', '0', '10', '0.00', '0.00', '11', '114', '114', TO_TIMESTAMP('2016-11-27 22:14:33', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2016-11-27 22:14:33', 'YYYY-MM-DD HH24:MI:SS'), '0', null, null, null, null, '113');
INSERT INTO orders VALUES ('10', '0', '0', '10', '0.00', '0.00', '11', '116', '116', TO_TIMESTAMP('2016-11-29 01:21:51', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2016-11-29 01:21:51', 'YYYY-MM-DD HH24:MI:SS'), '0', null, null, null, null, '117');
INSERT INTO orders VALUES ('11', '0', '0', '10', '0.00', '0.00', '1', '122', '122', TO_TIMESTAMP('2016-11-29 01:42:41', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2016-11-29 01:42:41', 'YYYY-MM-DD HH24:MI:SS'), '0', null, null, null, null, '123');
INSERT INTO orders VALUES ('12', '0', '0', '10', '0.00', '0.00', '5', '123', '123', TO_TIMESTAMP('2016-11-29 01:42:54', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2016-11-29 01:42:54', 'YYYY-MM-DD HH24:MI:SS'), '0', null, null, null, null, '124');

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO product VALUES ('28', '1', '4', '1', '3', '150床型纯棉被套', '1475741380036.png', '3', '2.4', '0', '0', '5000', TO_TIMESTAMP('2015-11-06 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2015-12-09 01:54:19', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO product VALUES ('29', '1', '4', '1', '3', '150床型纯棉被套11', '1451664266663.jpg', '3', '2.4', '0', '0', '5000', TO_TIMESTAMP('2015-11-06 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2015-12-09 01:54:19', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO product VALUES ('30', '1', '7', '1', '0', '纯棉枕套', '1449597463551.jpg', '3', '2.4', '0', '0', '5000', TO_TIMESTAMP('2015-11-06 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2015-12-09 01:57:36', 'YYYY-MM-DD HH24:MI:SS'));

-- ----------------------------
-- Records of sale_data
-- ----------------------------
INSERT INTO sale_data VALUES ('1', '25', '华为', '10');
INSERT INTO sale_data VALUES ('2', '25', '苹果', '11');
INSERT INTO sale_data VALUES ('3', '25', '小米', '12');
INSERT INTO sale_data VALUES ('4', '25', '三星', '13');
INSERT INTO sale_data VALUES ('5', '25', '魅族', '14');
INSERT INTO sale_data VALUES ('6', '25', '中兴', '15');
INSERT INTO sale_data VALUES ('7', '25', '乐视', '16');
INSERT INTO sale_data VALUES ('11', '42', '华为', '20');
INSERT INTO sale_data VALUES ('12', '42', '苹果', '21');
INSERT INTO sale_data VALUES ('13', '42', '小米', '22');
INSERT INTO sale_data VALUES ('14', '42', '三星', '23');
INSERT INTO sale_data VALUES ('15', '42', '魅族', '24');
INSERT INTO sale_data VALUES ('18', '42', '联想', '27');
INSERT INTO sale_data VALUES ('19', '42', '酷派', '28');
INSERT INTO sale_data VALUES ('20', '42', '黑莓', '29');
INSERT INTO sale_data VALUES ('21', '3', '华为', '30');
INSERT INTO sale_data VALUES ('22', '3', '苹果', '31');
INSERT INTO sale_data VALUES ('23', '3', '小米', '32');
INSERT INTO sale_data VALUES ('24', '3', '三星', '33');
INSERT INTO sale_data VALUES ('28', '3', '联想', '37');
INSERT INTO sale_data VALUES ('29', '3', '酷派', '38');
INSERT INTO sale_data VALUES ('30', '3', '黑莓', '39');
INSERT INTO sale_data VALUES ('31', '4', '华为', '40');
INSERT INTO sale_data VALUES ('32', '4', '苹果', '41');
INSERT INTO sale_data VALUES ('33', '4', '小米', '42');
INSERT INTO sale_data VALUES ('34', '4', '三星', '43');
INSERT INTO sale_data VALUES ('35', '4', '魅族', '44');
INSERT INTO sale_data VALUES ('36', '4', '中兴', '45');
INSERT INTO sale_data VALUES ('37', '4', '乐视', '46');
INSERT INTO sale_data VALUES ('38', '4', '联想', '47');
INSERT INTO sale_data VALUES ('39', '4', '酷派', '48');
INSERT INTO sale_data VALUES ('40', '4', '黑莓', '49');
INSERT INTO sale_data VALUES ('41', '4', '火腿', '1000');
INSERT INTO sale_data VALUES ('42', '27', '狗不理', '10000');
INSERT INTO sale_data VALUES ('43', '25', '土豆', '20');

-- ----------------------------
-- Records of test_info
-- ----------------------------
INSERT INTO test_info VALUES ('1', '1', '1', '123465', '17', '<p>上海虹口区足球场上海虹口</p><p><br></p>', '1475647037704.jpg', '0', '', '1464002566632.jpg', TO_TIMESTAMP('2016-05-26', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2016-09-18 00:32:48', 'YYYY-MM-DD HH24:MI:SS'), '00000', '#000000', '2,3,4,5', '{"domain":"http://www.eova.cn"}', '0', null, '0', '1', '0', null, TO_TIMESTAMP('2016-10-08 02:39:34', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO test_info VALUES ('4', '2', '0', '4324342', '43423423', '<p>哎哟不错哟！ 发个表情试试&nbsp;<img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/6d/lovea_thumb.gif"><br><img src="http://127.0.0.1/editor/1464193877187.png" style="max-width: 100%; width: 100.8px; height: 100.8px;" class="">&nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<img src="http://127.0.0.1/editor/1464194237135.png" style="max-width: 100%;"><br></p><p><br></p>', '1475655648414.png', '0', '423432423', '1464193847117.png', TO_TIMESTAMP('2016-05-26', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2016-05-26 00:30:40', 'YYYY-MM-DD HH24:MI:SS'), '000000', '#000000', '3', null, '0', null, '0', null, '0', null, null);
INSERT INTO test_info VALUES ('7', '1', '1', '新增导入1', '17', '<p>222上海虹口区足球场上海虹口222</p><p><br></p>', '1477243093297.png', '0', '更新导入。。。。', '1464002566632.jpg', TO_TIMESTAMP('2016-05-26', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2016-09-18 00:32:48', 'YYYY-MM-DD HH24:MI:SS'), '00000', '#000000', '2,4', null, '0', null, '0', null, '0', null, null);
INSERT INTO test_info VALUES ('8', '2', '1', '新增导入2', '43423423', '<p>哎哟不错哟！ 发个表情试试 <img src="http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/6d/lovea_thumb.gif"><br><img src="http://127.0.0.1:10086/editor/1464193877187.png" style="max-width: 100%; width: 100.8px; height: 100.8px;" class="">        <img src="http://127.0.0.1:10086/editor/1464194237135.png" style="max-width: 100%;"><br></p><p><br></p>', '1475647089038.jpg', '0', '423432423', '1464193847117.png', TO_TIMESTAMP('2016-05-26', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2016-05-26 00:30:40', 'YYYY-MM-DD HH24:MI:SS'), '000000', '#000000', '3', null, '0', null, '0', null, '0', null, null);
INSERT INTO test_info VALUES ('9', '2', '0', '4324', '432', '<p>423</p>', '1475944038375.png', '1', '4322344', '1475862732716.png', TO_TIMESTAMP('2016-10-08', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2016-10-08 01:52:39', 'YYYY-MM-DD HH24:MI:SS'), '423', '#000000', '2', null, '1', '1', '1', '1', '1', '1', null);

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO user_info VALUES ('1', '1', '0', '超管', '1623736450', '0', '0', '0', TO_TIMESTAMP('2016-04-03 14:16:52', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO user_info VALUES ('2', '2', '0', '系统管理员', '', null, null, null, TO_TIMESTAMP('2016-04-03 14:16:52', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO user_info VALUES ('3', '3', '0', '专业测试', '13524523406', '25', '321', '2710', TO_TIMESTAMP('2016-04-03 14:16:52', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO user_info VALUES ('4', '4', '0', '测试小白', '13524523488', '25', '321', '2710', TO_TIMESTAMP('2016-11-21 00:19:08', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO user_info VALUES ('5', '2', '0', '', null, null, null, null, TO_TIMESTAMP('2016-11-25 14:49:45', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO user_info VALUES ('7', '2', '0', '', null, null, null, null, TO_TIMESTAMP('2016-11-25 14:51:23', 'YYYY-MM-DD HH24:MI:SS'));

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO users VALUES ('7', '1', 'test11', '000011', '德玛西亚之力', TO_TIMESTAMP('2014-12-30 00:55:49', 'YYYY-MM-DD HH24:MI:SS'), '这个用户是专业差评11', '11111');
INSERT INTO users VALUES ('9', '1', 'test22', '000000', '卡牌大师', TO_TIMESTAMP('2014-12-30 00:55:49', 'YYYY-MM-DD HH24:MI:SS'), '#000000', '0');
INSERT INTO users VALUES ('10', '0', 'test10', '000000', '堕落天使', TO_TIMESTAMP('2014-12-30 00:55:49', 'YYYY-MM-DD HH24:MI:SS'), '', '');
INSERT INTO users VALUES ('12', '0', 'test12', '000000', '熔岩巨兽', TO_TIMESTAMP('2014-12-30 00:55:49', 'YYYY-MM-DD HH24:MI:SS'), '', '');
INSERT INTO users VALUES ('13', '0', 'test13', '000000', '祖安狂人', TO_TIMESTAMP('2014-12-30 00:55:49', 'YYYY-MM-DD HH24:MI:SS'), '', '');
INSERT INTO users VALUES ('14', '0', 'test14', '000000', '钢铁大使', TO_TIMESTAMP('2014-12-30 00:55:49', 'YYYY-MM-DD HH24:MI:SS'), '', '');
INSERT INTO users VALUES ('15', '0', 'test15', '000000', '寡妇制造者', TO_TIMESTAMP('2014-12-30 00:55:49', 'YYYY-MM-DD HH24:MI:SS'), '', '3,2');
INSERT INTO users VALUES ('16', '0', 'test16', '000000', '时光守护者', TO_TIMESTAMP('2014-12-30 00:55:49', 'YYYY-MM-DD HH24:MI:SS'), '', '3');
INSERT INTO users VALUES ('17', '0', 'test17', '000000', '末日使者', TO_TIMESTAMP('2014-12-30 00:55:49', 'YYYY-MM-DD HH24:MI:SS'), '', '2,3');
INSERT INTO users VALUES ('18', '0', 'test18', '000000', '殇之木乃伊', TO_TIMESTAMP('2014-12-30 00:55:49', 'YYYY-MM-DD HH24:MI:SS'), '', '0,3');
INSERT INTO users VALUES ('19', '0', 'test19', '000000', '牛头酋长', TO_TIMESTAMP('2014-12-30 00:55:49', 'YYYY-MM-DD HH24:MI:SS'), '', '0');
INSERT INTO users VALUES ('20', '0', 'test20', '000000', '邪恶小法师', TO_TIMESTAMP('2014-12-30 00:55:49', 'YYYY-MM-DD HH24:MI:SS'), '', '3');
INSERT INTO users VALUES ('21', '0', 'test21', '000000', '风暴之怒', TO_TIMESTAMP('2014-12-30 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), '', '3');
INSERT INTO users VALUES ('22', '0', 'test22', '000000', '麦林炮手', TO_TIMESTAMP('2014-12-30 00:55:49', 'YYYY-MM-DD HH24:MI:SS'), '', '1');
INSERT INTO users VALUES ('23', '2', 'test23', '000000', '黑暗之女', TO_TIMESTAMP('2014-12-30 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), '', '3');

-- ----------------------------
-- Records of users_exp
-- ----------------------------
INSERT INTO users_exp VALUES ('6', '1', '23', '1623736455');
INSERT INTO users_exp VALUES ('7', '1', '11', '11111');
INSERT INTO users_exp VALUES ('9', '22', '22', '22222');
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
INSERT INTO users_exp VALUES ('21', '0', '38', '1623736470');
INSERT INTO users_exp VALUES ('22', '0', '39', '1623736471');
INSERT INTO users_exp VALUES ('23', '0', '40', '1623736472');
INSERT INTO users_exp VALUES ('24', '0', '41', '1623736473');
INSERT INTO users_exp VALUES ('25', '0', '42', '1623736474');
INSERT INTO users_exp VALUES ('26', '0', '43', '1623736475');
INSERT INTO users_exp VALUES ('27', '0', '44', '1623736476');
INSERT INTO users_exp VALUES ('28', '0', '45', '1623736477');
INSERT INTO users_exp VALUES ('29', '0', '1', '100000');
INSERT INTO users_exp VALUES ('30', '213', '1', '10000');
INSERT INTO users_exp VALUES ('31', '213', '1', '145454');

-- ----------------------------
-- Records of users_item
-- ----------------------------
INSERT INTO users_item VALUES ('1', '7', '3');
INSERT INTO users_item VALUES ('2', '27', '2');
INSERT INTO users_item VALUES ('3', '9', '5');
INSERT INTO users_item VALUES ('4', '10', '2');
INSERT INTO users_item VALUES ('5', '11', '3');
INSERT INTO users_item VALUES ('6', '23', '4');

END;
/