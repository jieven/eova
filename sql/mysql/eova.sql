/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50513
Source Host           : localhost:3306
Source Database       : eova

Target Server Type    : MYSQL
Target Server Version : 50513
File Encoding         : 65001

Date: 2015-06-30 01:28:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `eova_button`
-- ----------------------------
DROP TABLE IF EXISTS `eova_button`;
CREATE TABLE `eova_button` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_code` varchar(255) NOT NULL COMMENT '菜单Code',
  `name` varchar(255) NOT NULL COMMENT '按钮名称',
  `ui` varchar(255) DEFAULT NULL COMMENT '按钮UI路径',
  `bs` varchar(500) DEFAULT NULL COMMENT '按钮BS路径',
  `order_num` int(11) DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_button
-- ----------------------------
INSERT INTO `eova_button` VALUES ('1', 'eova_menu', '查询', NULL, NULL, '0');
INSERT INTO `eova_button` VALUES ('2', 'eova_button', '查询', NULL, NULL, '0');
INSERT INTO `eova_button` VALUES ('3', 'eova_object', '查询', NULL, NULL, '0');
INSERT INTO `eova_button` VALUES ('4', 'eova_field', '查询', NULL, NULL, '0');
INSERT INTO `eova_button` VALUES ('5', 'eova_dictionary', '查询', NULL, NULL, '0');
INSERT INTO `eova_button` VALUES ('6', 'eova_icon', '查询', NULL, NULL, '0');
INSERT INTO `eova_button` VALUES ('7', 'sys_auth_user', '查询', NULL, NULL, '0');
INSERT INTO `eova_button` VALUES ('8', 'sys_auth_role', '查询', NULL, NULL, '0');
INSERT INTO `eova_button` VALUES ('9', 'sys_log', '查询', NULL, NULL, '0');
INSERT INTO `eova_button` VALUES ('10', 'eova_menu', '新增', '/eova/menu/btn/add.html', NULL, '1');
INSERT INTO `eova_button` VALUES ('11', 'eova_menu', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '2');
INSERT INTO `eova_button` VALUES ('12', 'eova_menu', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '3');
INSERT INTO `eova_button` VALUES ('13', 'eova_field', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '1');
INSERT INTO `eova_button` VALUES ('14', 'eova_field', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '2');
INSERT INTO `eova_button` VALUES ('15', 'eova_field', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '3');
INSERT INTO `eova_button` VALUES ('16', 'eova_button', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '1');
INSERT INTO `eova_button` VALUES ('17', 'eova_button', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '2');
INSERT INTO `eova_button` VALUES ('18', 'eova_button', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '3');
INSERT INTO `eova_button` VALUES ('19', 'eova_object', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '1');
INSERT INTO `eova_button` VALUES ('20', 'eova_object', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '2');
INSERT INTO `eova_button` VALUES ('21', 'eova_object', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '3');
INSERT INTO `eova_button` VALUES ('22', 'eova_object', '导入元数据', '/eova/metadata/btn/import.html', ' ', '5');
INSERT INTO `eova_button` VALUES ('23', 'eova_menu', '基本功能', '/eova/menu/btn/fun.html', NULL, '5');
INSERT INTO `eova_button` VALUES ('24', 'eova_dictionary', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '1');
INSERT INTO `eova_button` VALUES ('25', 'eova_dictionary', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '2');
INSERT INTO `eova_button` VALUES ('26', 'sys_auth_role', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '1');
INSERT INTO `eova_button` VALUES ('27', 'sys_auth_role', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '2');
INSERT INTO `eova_button` VALUES ('28', 'sys_auth_role', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '3');
INSERT INTO `eova_button` VALUES ('29', 'sys_auth_user', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '1');
INSERT INTO `eova_button` VALUES ('30', 'sys_auth_user', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '2');
INSERT INTO `eova_button` VALUES ('31', 'sys_auth_user', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '3');
INSERT INTO `eova_button` VALUES ('32', 'sys_auth_role', '权限分配', '/eova/auth/btn/roleChoose.html', NULL, '5');
INSERT INTO `eova_button` VALUES ('65', 'biz_demo_users', '查询', NULL, NULL, '0');
INSERT INTO `eova_button` VALUES ('66', 'biz_demo_tool', '查询', NULL, NULL, '0');
INSERT INTO `eova_button` VALUES ('67', 'biz_demo_users', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '0');
INSERT INTO `eova_button` VALUES ('68', 'biz_demo_users', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '0');
INSERT INTO `eova_button` VALUES ('69', 'biz_demo_users', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '0');
INSERT INTO `eova_button` VALUES ('71', 'biz_demo_tool', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '0');
INSERT INTO `eova_button` VALUES ('72', 'biz_demo_tool', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '0');
INSERT INTO `eova_button` VALUES ('73', 'biz_demo_tool', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '0');
INSERT INTO `eova_button` VALUES ('74', 'biz_demo_usersitem', '查询', NULL, NULL, '0');
INSERT INTO `eova_button` VALUES ('75', 'biz_demo_usersitem', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '0');
INSERT INTO `eova_button` VALUES ('76', 'biz_demo_usersitem', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '0');
INSERT INTO `eova_button` VALUES ('77', 'biz_demo_usersitem', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '0');
INSERT INTO `eova_button` VALUES ('78', 'biz_demo_usersview', '查询', NULL, NULL, '0');
INSERT INTO `eova_button` VALUES ('79', 'biz_demo_usersview', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '0');
INSERT INTO `eova_button` VALUES ('80', 'biz_demo_usersview', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '0');
INSERT INTO `eova_button` VALUES ('81', 'biz_demo_usersview', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '0');
INSERT INTO `eova_button` VALUES ('86', 'biz_demo_userscell', '查询', NULL, NULL, '0');
INSERT INTO `eova_button` VALUES ('87', 'biz_demo_import', '查询', NULL, NULL, '0');
INSERT INTO `eova_button` VALUES ('96', 'biz_demo_import', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '0');
INSERT INTO `eova_button` VALUES ('97', 'biz_demo_import', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '0');
INSERT INTO `eova_button` VALUES ('98', 'biz_demo_import', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '0');
INSERT INTO `eova_button` VALUES ('99', 'biz_demo_import', '导入', '/eova/template/crud/btn/import.html', 'crud/import', '0');

-- ----------------------------
-- Table structure for `eova_dict`
-- ----------------------------
DROP TABLE IF EXISTS `eova_dict`;
CREATE TABLE `eova_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `object` varchar(50) NOT NULL,
  `field` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_dict
-- ----------------------------
INSERT INTO `eova_dict` VALUES ('1', 'main', '默认', 'eova_object', 'data_source');
INSERT INTO `eova_dict` VALUES ('2', 'eova', 'EOVA', 'eova_object', 'data_source');
INSERT INTO `eova_dict` VALUES ('3', 'string', '字符', 'eova_field', 'data_type');
INSERT INTO `eova_dict` VALUES ('4', 'number', '数字', 'eova_field', 'data_type');
INSERT INTO `eova_dict` VALUES ('5', 'time', '时间', 'eova_field', 'data_type');
INSERT INTO `eova_dict` VALUES ('6', '1', '新增', 'eova_log', 'type');
INSERT INTO `eova_dict` VALUES ('7', '2', '修改', 'eova_log', 'type');
INSERT INTO `eova_dict` VALUES ('8', '3', '删除', 'eova_log', 'type');
INSERT INTO `eova_dict` VALUES ('9', '文本框', '文本框', 'eova_field', 'type');
INSERT INTO `eova_dict` VALUES ('10', '下拉框', '下拉框', 'eova_field', 'type');
INSERT INTO `eova_dict` VALUES ('11', '查找框', '查找框', 'eova_field', 'type');
INSERT INTO `eova_dict` VALUES ('12', '时间框', '时间框', 'eova_field', 'type');
INSERT INTO `eova_dict` VALUES ('13', '文本域', '文本域', 'eova_field', 'type');
INSERT INTO `eova_dict` VALUES ('14', '编辑框', '编辑框', 'eova_field', 'type');
INSERT INTO `eova_dict` VALUES ('15', '复选框', '复选框', 'eova_field', 'type');
INSERT INTO `eova_dict` VALUES ('16', '自增框', '自增框', 'eova_field', 'type');

-- ----------------------------
-- Table structure for `eova_field`
-- ----------------------------
DROP TABLE IF EXISTS `eova_field`;
CREATE TABLE `eova_field` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `object_code` varchar(50) NOT NULL,
  `po_code` varchar(255) DEFAULT NULL COMMENT '持久化对象',
  `en` varchar(50) NOT NULL COMMENT '英文名',
  `cn` varchar(50) NOT NULL COMMENT '中文名',
  `is_auto` tinyint(1) DEFAULT '0' COMMENT '主键是否自增长',
  `data_type` varchar(20) DEFAULT 'string' COMMENT '数据类型',
  `type` varchar(10) DEFAULT '文本框' COMMENT '控件类型',
  `order_num` int(4) DEFAULT '9' COMMENT '排序索引',
  `exp` varchar(800) DEFAULT NULL COMMENT '控件表达式',
  `is_query` tinyint(1) DEFAULT '0' COMMENT '是否可查询',
  `is_show` tinyint(1) DEFAULT '1' COMMENT '是否可显示',
  `is_order` tinyint(1) DEFAULT '1' COMMENT '是否可排序',
  `is_add` tinyint(1) DEFAULT '1' COMMENT '是否可新增字段',
  `is_update` tinyint(1) DEFAULT '1' COMMENT '是否可修改字段',
  `is_edit` tinyint(1) DEFAULT '1' COMMENT '是否可编辑字段',
  `is_required` tinyint(1) DEFAULT '1' COMMENT '是否必填',
  `placeholder` varchar(255) DEFAULT NULL COMMENT '输入提示',
  `validator` varchar(255) DEFAULT NULL COMMENT 'UI校验表达式',
  `defaulter` varchar(255) DEFAULT NULL COMMENT '默认值表达式',
  `width` int(4) DEFAULT '130' COMMENT '控件宽度',
  `height` int(4) DEFAULT '20' COMMENT '控件高度',
  `is_multiple` tinyint(1) DEFAULT '0' COMMENT '是否多选项',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=185 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_field
-- ----------------------------
INSERT INTO `eova_field` VALUES ('1', 'eova_user_code', NULL, 'id', 'ID', '1', 'number', '自增框', '0', NULL, '0', '1', '1', '1', '1', '0', '1', NULL, NULL, NULL, '130', '0', '0');
INSERT INTO `eova_field` VALUES ('2', 'eova_user_code', NULL, 'nickname', '昵称', '0', 'string', '文本框', '0', NULL, '1', '1', '1', '1', '1', '0', '1', NULL, NULL, NULL, '130', '0', '0');
INSERT INTO `eova_field` VALUES ('3', 'eova_user_code', NULL, 'login_id', '登录帐号', '0', 'string', '上传框', '0', NULL, '1', '1', '1', '1', '1', '0', '1', NULL, NULL, NULL, '130', '0', '0');
INSERT INTO `eova_field` VALUES ('4', 'eova_user_code', NULL, 'login_pwd', '登录密码', '0', 'string', '文本框', '0', NULL, '0', '0', '0', '1', '1', '0', '1', NULL, NULL, NULL, '130', '0', '0');
INSERT INTO `eova_field` VALUES ('5', 'eova_menu_code', NULL, 'id', 'ID', '1', 'number', '自增框', '1', NULL, '0', '0', '1', '1', '1', '0', '1', NULL, NULL, NULL, '100', '0', '0');
INSERT INTO `eova_field` VALUES ('6', 'eova_menu_code', NULL, 'code', '编码', '0', 'string', '文本框', '4', NULL, '1', '1', '1', '1', '0', '0', '1', NULL, NULL, NULL, '180', '0', '0');
INSERT INTO `eova_field` VALUES ('7', 'eova_menu_code', NULL, 'name', '名称', '0', 'string', '文本框', '2', NULL, '1', '1', '1', '1', '1', '0', '1', NULL, NULL, NULL, '180', '0', '0');
INSERT INTO `eova_field` VALUES ('8', 'eova_menu_code', NULL, 'type', '类型', '0', 'string', '文本框', '1', NULL, '0', '1', '1', '1', '1', '0', '1', NULL, NULL, NULL, '100', '0', '0');
INSERT INTO `eova_field` VALUES ('10', 'eova_menu_code', NULL, 'icon', '图标', '0', 'string', '图标框', '6', NULL, '0', '0', '1', '1', '1', '0', '1', NULL, NULL, NULL, '130', '0', '0');
INSERT INTO `eova_field` VALUES ('11', 'eova_menu_code', NULL, 'order_num', '序号', '0', 'number', '文本框', '9', NULL, '0', '1', '1', '1', '1', '0', '1', NULL, NULL, NULL, '30', '0', '0');
INSERT INTO `eova_field` VALUES ('12', 'eova_menu_code', NULL, 'parentId', '父节点', '0', 'number', '查找框', '9', 'select id ID,name 菜单名称 from eova_menu where 1=1;ds=eova', '1', '1', '1', '1', '1', '0', '1', NULL, NULL, NULL, '100', '0', '0');
INSERT INTO `eova_field` VALUES ('13', 'eova_object_code', NULL, 'id', 'ID', '1', 'number', '自增框', '1', NULL, '0', '0', '1', '1', '1', '0', '1', NULL, NULL, NULL, '130', '0', '0');
INSERT INTO `eova_field` VALUES ('14', 'eova_object_code', NULL, 'code', '编码', '0', 'string', '文本框', '2', NULL, '1', '1', '1', '1', '0', '0', '1', NULL, NULL, NULL, '200', '0', '0');
INSERT INTO `eova_field` VALUES ('15', 'eova_object_code', NULL, 'name', '名称', '0', 'string', '文本框', '3', NULL, '0', '1', '1', '1', '1', '0', '1', NULL, NULL, NULL, '130', '0', '0');
INSERT INTO `eova_field` VALUES ('16', 'eova_object_code', NULL, 'view_name', '视图', '0', 'string', '文本框', '4', NULL, '1', '1', '1', '1', '1', '0', '0', NULL, NULL, NULL, '70', '0', '0');
INSERT INTO `eova_field` VALUES ('17', 'eova_object_code', NULL, 'table_name', '数据表', '0', 'string', '文本框', '5', NULL, '1', '1', '1', '1', '1', '0', '0', NULL, NULL, NULL, '70', '0', '0');
INSERT INTO `eova_field` VALUES ('18', 'eova_object_code', NULL, 'pk_name', '主键', '0', 'string', '文本框', '6', NULL, '0', '1', '1', '1', '1', '0', '0', NULL, NULL, NULL, '70', '0', '0');
INSERT INTO `eova_field` VALUES ('19', 'eova_object_code', NULL, 'data_source', '数据源', '0', 'string', '下拉框', '7', 'select value ID,name CN from `eova_dict` where object = \'eova_object\' and field = \'data_source\';ds=eova', '0', '1', '1', '1', '1', '0', '1', NULL, NULL, NULL, '70', '0', '0');
INSERT INTO `eova_field` VALUES ('20', 'eova_object_code', NULL, 'is_single', '是否单选', '0', 'number', '复选框', '8', NULL, '0', '1', '1', '1', '1', '0', '0', NULL, NULL, NULL, '70', '0', '0');
INSERT INTO `eova_field` VALUES ('21', 'eova_object_code', NULL, 'is_show_num', '显示行号', '0', 'number', '复选框', '9', NULL, '0', '1', '1', '1', '1', '0', '0', NULL, NULL, NULL, '70', '0', '0');
INSERT INTO `eova_field` VALUES ('22', 'eova_object_code', NULL, 'is_default_pk_desc', '默认逆序', '0', 'number', '复选框', '10', NULL, '0', '1', '1', '1', '1', '0', '0', NULL, NULL, NULL, '70', '0', '0');
INSERT INTO `eova_field` VALUES ('23', 'eova_object_code', NULL, 'filter', '过滤条件', '0', 'string', '文本域', '11', NULL, '0', '1', '1', '1', '1', '0', '0', NULL, NULL, NULL, '130', '0', '0');
INSERT INTO `eova_field` VALUES ('26', 'eova_field_code', NULL, 'id', 'ID', '1', 'number', '自增框', '1', NULL, '0', '0', '1', '1', '1', '0', '1', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('27', 'eova_field_code', NULL, 'object_code', '对象编码', '0', 'string', '查找框', '2', 'select code 编码,name 名称 from eova_object where 1=1;ds=eova', '1', '1', '1', '1', '1', '0', '1', NULL, NULL, 'eova_user_code', '130', '20', '0');
INSERT INTO `eova_field` VALUES ('28', 'eova_field_code', NULL, 'en', '字段名', '0', 'string', '文本框', '3', NULL, '1', '1', '1', '1', '1', '1', '1', '数据库的字段名', NULL, NULL, '70', '20', '0');
INSERT INTO `eova_field` VALUES ('29', 'eova_field_code', NULL, 'cn', '中文名', '0', 'string', '文本框', '4', NULL, '1', '1', '1', '1', '1', '1', '1', '字段对应的中文描述', NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('30', 'eova_field_code', NULL, 'is_auto', '自增长', '0', 'number', '复选框', '20', NULL, '0', '1', '1', '1', '1', '1', '0', NULL, NULL, NULL, '70', '20', '0');
INSERT INTO `eova_field` VALUES ('31', 'eova_field_code', NULL, 'data_type', '字段类型', '0', 'string', '下拉框', '6', 'select value ID,name CN from `eova_dict` where object = \'eova_field\' and field = \'data_type\';ds=eova', '0', '1', '1', '1', '1', '0', '1', NULL, NULL, 'string', '70', '20', '0');
INSERT INTO `eova_field` VALUES ('32', 'eova_field_code', NULL, 'type', '控件类型', '0', 'string', '下拉框', '7', 'select value ID,name CN from `eova_dict` where object = \'eova_field\' and field = \'type\';ds=eova', '1', '1', '1', '1', '1', '1', '1', NULL, NULL, '文本框', '70', '20', '0');
INSERT INTO `eova_field` VALUES ('33', 'eova_field_code', NULL, 'order_num', '排序', '0', 'number', '文本框', '8', NULL, '0', '1', '1', '1', '1', '1', '1', NULL, NULL, '0', '50', '20', '0');
INSERT INTO `eova_field` VALUES ('34', 'eova_field_code', NULL, 'exp', '表达式', '0', 'string', '文本域', '31', NULL, '0', '1', '1', '1', '1', '0', '0', '查找框和下拉框需需要表达式', NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('35', 'eova_field_code', NULL, 'is_query', '允许查询', '0', 'number', '复选框', '21', NULL, '0', '1', '1', '1', '1', '1', '0', NULL, NULL, NULL, '70', '20', '0');
INSERT INTO `eova_field` VALUES ('36', 'eova_field_code', NULL, 'is_show', '允许显示', '0', 'number', '复选框', '22', NULL, '0', '1', '1', '1', '1', '1', '0', NULL, NULL, '1', '70', '20', '0');
INSERT INTO `eova_field` VALUES ('37', 'eova_field_code', NULL, 'is_order', '允许排序', '0', 'number', '复选框', '23', NULL, '0', '1', '1', '1', '1', '1', '0', NULL, NULL, '1', '70', '20', '0');
INSERT INTO `eova_field` VALUES ('38', 'eova_field_code', NULL, 'is_add', '允许新增', '0', 'number', '复选框', '24', NULL, '0', '1', '1', '1', '1', '1', '0', NULL, NULL, '1', '70', '20', '0');
INSERT INTO `eova_field` VALUES ('39', 'eova_field_code', NULL, 'is_update', '允许修改', '0', 'number', '复选框', '25', NULL, '0', '1', '1', '1', '1', '1', '0', NULL, NULL, '1', '70', '20', '0');
INSERT INTO `eova_field` VALUES ('40', 'eova_field_code', NULL, 'is_required', '是否必填', '0', 'number', '复选框', '25', NULL, '0', '1', '1', '1', '1', '1', '0', NULL, NULL, '1', '70', '20', '0');
INSERT INTO `eova_field` VALUES ('41', 'eova_field_code', NULL, 'defaulter', '默认值表达式', '0', 'string', '文本域', '32', NULL, '0', '1', '1', '1', '1', '1', '0', '初始默认值', NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('42', 'eova_field_code', NULL, 'width', '宽度', '0', 'number', '文本框', '17', NULL, '0', '1', '1', '1', '1', '1', '1', NULL, NULL, '130', '50', '20', '0');
INSERT INTO `eova_field` VALUES ('43', 'eova_field_code', NULL, 'height', '高度', '0', 'number', '文本框', '18', NULL, '0', '1', '1', '1', '1', '1', '1', NULL, NULL, '80', '50', '20', '0');
INSERT INTO `eova_field` VALUES ('44', 'eova_field_code', NULL, 'is_multiple', '允许多选', '0', 'number', '复选框', '26', NULL, '0', '1', '1', '1', '1', '1', '0', NULL, NULL, NULL, '70', '20', '0');
INSERT INTO `eova_field` VALUES ('45', 'eova_button_code', NULL, 'id', 'ID', '1', 'number', '自增框', '1', NULL, '0', '1', '1', '1', '1', '0', '0', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('46', 'eova_button_code', NULL, 'menu_code', '菜单编码', '0', 'string', '查找框', '2', 'select code 菜单编码,name 菜单名称 from eova_menu where 1=1;ds=eova', '1', '1', '1', '1', '1', '0', '1', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('47', 'eova_button_code', NULL, 'name', '功能名称', '0', 'string', '文本框', '4', NULL, '1', '1', '1', '1', '1', '0', '1', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('48', 'eova_button_code', NULL, 'ui', 'UI路径', '0', 'string', '文本框', '5', NULL, '1', '1', '1', '1', '1', '0', '1', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('49', 'eova_button_code', NULL, 'bs', 'BS路径', '0', 'string', '文本框', '6', NULL, '1', '1', '1', '1', '1', '0', '1', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('50', 'eova_dict_code', NULL, 'id', 'ID', '1', 'number', '自增框', '1', NULL, '0', '1', '1', '1', '1', '0', '1', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('51', 'eova_dict_code', NULL, 'value', '值', '0', 'string', '文本框', '2', NULL, '1', '1', '1', '1', '1', '0', '1', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('52', 'eova_dict_code', NULL, 'name', '字段名', '0', 'string', '文本框', '3', NULL, '1', '1', '1', '1', '1', '0', '1', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('53', 'eova_dict_code', NULL, 'object', '列名', '0', 'string', '文本框', '4', NULL, '1', '1', '1', '1', '1', '0', '1', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('54', 'eova_dict_code', NULL, 'field', '对象', '0', 'string', '文本框', '5', NULL, '1', '1', '1', '1', '1', '0', '1', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('59', 'eova_menu_code', NULL, 'biz_intercept', '自定义业务拦截器', '0', 'string', '文本域', '14', NULL, '0', '1', '1', '1', '1', '0', '0', NULL, NULL, NULL, '300', '0', '0');
INSERT INTO `eova_field` VALUES ('60', 'eova_button_code', NULL, 'order_num', '序号', '0', 'number', '文本框', '9', NULL, '0', '1', '1', '1', '1', '0', '1', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('61', 'eova_role_code', NULL, 'id', 'ID', '1', 'number', '自增框', '0', NULL, '0', '1', '1', '1', '1', '0', '1', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('62', 'eova_role_code', NULL, 'name', '角色名', '0', 'string', '文本框', '0', NULL, '1', '1', '1', '1', '1', '0', '1', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('63', 'eova_role_code', NULL, 'info', '角色描述', '0', 'string', '文本框', '0', NULL, '0', '1', '1', '1', '1', '0', '1', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('64', 'eova_user_code', NULL, 'rid', '角色', '0', 'string', '下拉框', '0', 'select id ID,name CN from eova_role where 1=1;ds=eova', '0', '1', '1', '1', '1', '0', '1', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('70', 'eova_log_code', NULL, 'id', 'id', '1', 'number', '自增框', '1', NULL, '0', '1', '1', '1', '1', '0', '0', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('71', 'eova_log_code', NULL, 'user_id', '操作用户', '0', 'number', '查找框', '2', 'select id UID,nickname 用户名 from eova_user where 1=1;ds=eova', '1', '1', '1', '1', '1', '0', '0', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('72', 'eova_log_code', NULL, 'type', '日志类型', '0', 'number', '文本框', '3', 'select value ID,name CN from `eova_dict` where object = \'eova_log\' and field = \'type\';ds=eova', '1', '1', '1', '1', '1', '0', '0', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('73', 'eova_log_code', NULL, 'ip', '操作IP', '0', 'string', '文本框', '4', NULL, '1', '1', '1', '1', '1', '0', '0', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('74', 'eova_log_code', NULL, 'info', '操作详情', '0', 'string', '文本框', '5', NULL, '0', '1', '1', '1', '1', '0', '1', NULL, NULL, NULL, '200', '20', '0');
INSERT INTO `eova_field` VALUES ('75', 'player_code', NULL, 'id', 'ID', '1', 'number', '自增框', '1', NULL, '0', '1', '1', '1', '1', '0', '0', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('76', 'player_code', NULL, 'status', '状态', '0', 'number', '下拉框', '2', 'select value ID,name CN from `dict` where object = \'users\' and field = \'status\';ds=main', '1', '1', '1', '1', '1', '1', '1', NULL, NULL, '0', '130', '20', '0');
INSERT INTO `eova_field` VALUES ('77', 'player_code', NULL, 'login_id', '登录账户', '0', 'string', '文本框', '3', NULL, '1', '1', '1', '1', '1', '1', '1', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('78', 'player_code', NULL, 'login_pwd', '录登密码', '0', 'string', '文本框', '4', NULL, '0', '0', '1', '1', '1', '1', '1', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('79', 'player_code', NULL, 'nickname', '艺人姓名', '1', 'string', '文本框', '1', NULL, '1', '1', '1', '1', '1', '1', '1', NULL, NULL, NULL, '130', '20', '1');
INSERT INTO `eova_field` VALUES ('80', 'player_code', NULL, 'reg_time', '注册时间', '1', 'time', '时间框', '6', NULL, '1', '1', '1', '1', '1', '1', '1', NULL, NULL, 'CURRENT_TIMESTAMP', '180', '20', '1');
INSERT INTO `eova_field` VALUES ('81', 'eova_menu_code', NULL, 'url', 'URL', '0', 'string', '文本框', '15', NULL, '0', '1', '1', '1', '1', '0', '0', NULL, NULL, NULL, '130', '0', '0');
INSERT INTO `eova_field` VALUES ('82', 'eova_field_code', NULL, 'is_edit', '允许行内编辑', '0', 'number', '复选框', '25', NULL, '0', '1', '1', '1', '1', '1', '0', NULL, NULL, NULL, '70', '20', '0');
INSERT INTO `eova_field` VALUES ('83', 'eova_object_code', NULL, 'is_celledit', '行内编辑', '0', 'number', '复选框', '8', NULL, '0', '1', '1', '1', '1', '0', '0', NULL, NULL, NULL, '70', '0', '0');
INSERT INTO `eova_field` VALUES ('84', 'player_code', NULL, 'info', '备注', '0', 'string', '编辑框', '9', NULL, '0', '1', '1', '0', '0', '0', '0', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('85', 'item_code', NULL, 'id', 'ID', '1', 'number', '自增框', '1', NULL, '1', '1', '1', '1', '1', '1', '1', NULL, NULL, NULL, '130', '20', '1');
INSERT INTO `eova_field` VALUES ('86', 'item_code', NULL, 'name', '名称', '0', 'string', '文本框', '2', NULL, '1', '1', '1', '1', '1', '1', '1', NULL, NULL, NULL, '130', '20', '1');
INSERT INTO `eova_field` VALUES ('87', 'item_code', NULL, 'info', '介绍', '1', 'string', '编辑框', '3', NULL, '1', '1', '1', '1', '1', '1', '1', NULL, NULL, NULL, '200', '20', '1');
INSERT INTO `eova_field` VALUES ('88', 'users_item_code', NULL, 'id', 'ID', '1', 'number', '自增框', '1', NULL, '0', '1', '1', '1', '1', '1', '0', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('89', 'users_item_code', NULL, 'users_id', '艺人', '1', 'number', '查找框', '2', 'select id ID,nickname 艺人 from users where 1=1;ds=main', '1', '1', '1', '1', '1', '1', '1', NULL, NULL, NULL, '130', '20', '1');
INSERT INTO `eova_field` VALUES ('90', 'users_item_code', NULL, 'item_id', '道具', '0', 'number', '下拉框', '3', 'select id ID,name CN from item where 1=1;ds=main', '1', '1', '1', '1', '1', '1', '1', NULL, NULL, NULL, '600', '20', '0');
INSERT INTO `eova_field` VALUES ('124', 'v_users_code', 'player_code', 'id', 'ID', '1', 'number', '自增框', '1', NULL, '0', '1', '1', '1', '1', '1', '0', NULL, NULL, '0', '130', '20', '0');
INSERT INTO `eova_field` VALUES ('125', 'v_users_code', 'player_code', 'status', '状态', '0', 'number', '文本框', '2', NULL, '1', '1', '1', '1', '1', '1', '1', NULL, NULL, '0', '130', '20', '0');
INSERT INTO `eova_field` VALUES ('126', 'v_users_code', 'player_code', 'login_id', '登录账户', '0', 'string', '文本框', '3', NULL, '1', '1', '1', '1', '1', '1', '1', '请输入帐号', 'min : 5, max : 7', NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('127', 'v_users_code', 'player_code', 'login_pwd', '录登密码', '0', 'string', '文本框', '4', NULL, '0', '1', '1', '1', '1', '1', '0', '请输入密码', NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('128', 'v_users_code', 'player_code', 'nickname', '昵称', '0', 'string', '文本域', '20', NULL, '1', '1', '1', '1', '1', '1', '1', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('129', 'v_users_code', 'player_code', 'reg_time', '注册时间', '0', 'time', '时间框', '6', NULL, '0', '1', '1', '1', '1', '1', '1', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('130', 'v_users_code', 'player_code', 'info', '备注', '0', 'string', '文本域', '30', NULL, '0', '1', '1', '1', '1', '1', '0', NULL, 'min : 8, max : 10', NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('132', 'v_users_code', 'users_exp_code', 'exp', '经验值', '0', 'number', '文本框', '9', NULL, '0', '1', '1', '1', '1', '1', '1', NULL, NULL, '0', '130', '20', '0');
INSERT INTO `eova_field` VALUES ('133', 'v_users_code', 'users_exp_code', 'avg', '年龄', '0', 'number', '文本框', '10', NULL, '0', '1', '1', '1', '1', '1', '0', NULL, NULL, '0', '130', '20', '0');
INSERT INTO `eova_field` VALUES ('134', 'v_users_code', 'users_exp_code', 'qq', 'QQ', '0', 'string', '文本框', '22', NULL, '0', '1', '1', '1', '1', '1', '1', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('146', 'celledit_users_code', NULL, 'id', 'id', '1', 'number', '自增框', '1', NULL, '0', '0', '1', '1', '1', '1', '0', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('147', 'celledit_users_code', NULL, 'status', '状态', '0', 'number', '文本框', '2', NULL, '0', '1', '1', '1', '1', '1', '1', NULL, NULL, '0', '130', '20', '0');
INSERT INTO `eova_field` VALUES ('148', 'celledit_users_code', NULL, 'login_id', '登录账户', '0', 'string', '文本框', '3', NULL, '0', '1', '1', '1', '1', '1', '1', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('149', 'celledit_users_code', NULL, 'login_pwd', '录登密码', '0', 'string', '文本框', '4', NULL, '0', '1', '1', '1', '1', '1', '1', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('150', 'celledit_users_code', NULL, 'nickname', '昵称', '0', 'string', '文本域', '5', NULL, '1', '1', '1', '1', '1', '1', '1', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('151', 'celledit_users_code', NULL, 'reg_time', '注册时间', '0', 'time', '时间框', '6', NULL, '0', '1', '1', '1', '1', '1', '1', NULL, NULL, 'CURRENT_TIMESTAMP', '130', '20', '0');
INSERT INTO `eova_field` VALUES ('152', 'celledit_users_code', NULL, 'info', '备注', '0', 'string', '文本域', '7', NULL, '0', '1', '1', '1', '1', '1', '1', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('153', 'users_exp_code', NULL, 'users_id', 'users_id', '0', 'number', '文本框', '1', NULL, '0', '1', '1', '1', '1', '1', '0', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('154', 'users_exp_code', NULL, 'exp', '经验值', '0', 'number', '文本框', '2', NULL, '0', '1', '1', '1', '1', '1', '1', NULL, NULL, '0', '130', '20', '0');
INSERT INTO `eova_field` VALUES ('155', 'users_exp_code', NULL, 'avg', '年龄', '0', 'number', '文本框', '3', NULL, '0', '1', '1', '1', '1', '1', '1', NULL, NULL, '0', '130', '20', '0');
INSERT INTO `eova_field` VALUES ('156', 'users_exp_code', NULL, 'qq', 'QQ', '0', 'string', '文本框', '4', NULL, '0', '1', '1', '1', '1', '1', '1', NULL, NULL, NULL, '130', '20', '0');
INSERT INTO `eova_field` VALUES ('157', 'eova_field_code', NULL, 'placeholder', '输入提示', '0', 'string', '文本框', '28', NULL, '0', '1', '1', '1', '1', '1', '0', 'input的placeholder属性', NULL, NULL, '130', '20', '0');

-- ----------------------------
-- Table structure for `eova_log`
-- ----------------------------
DROP TABLE IF EXISTS `eova_log`;
CREATE TABLE `eova_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '操作用户',
  `type` int(11) NOT NULL COMMENT '日志类型',
  `ip` varchar(255) NOT NULL COMMENT '操作IP',
  `info` varchar(500) DEFAULT NULL COMMENT '操作详情',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_log
-- ----------------------------

-- ----------------------------
-- Table structure for `eova_menu`
-- ----------------------------
DROP TABLE IF EXISTS `eova_menu`;
CREATE TABLE `eova_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL COMMENT '编码',
  `name` varchar(30) NOT NULL COMMENT '名称',
  `type` varchar(20) NOT NULL COMMENT '菜单类型',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标路径',
  `order_num` int(11) DEFAULT '0' COMMENT '序号',
  `parentId` int(11) DEFAULT '0' COMMENT '父节点',
  `is_collapse` tinyint(1) DEFAULT '0' COMMENT '是否折叠',
  `biz_intercept` varchar(255) DEFAULT NULL COMMENT '自定义业务拦截器',
  `url` varchar(255) DEFAULT NULL COMMENT '自定义URL',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_menu
-- ----------------------------
INSERT INTO `eova_menu` VALUES ('1', 'eova', '平台维护', 'singleGrid', 'icon-bricks', '3', '0', '0', NULL, NULL);
INSERT INTO `eova_menu` VALUES ('2', 'sys', '系统管理', 'singleGrid', 'icon-cog', '2', '0', '0', NULL, NULL);
INSERT INTO `eova_menu` VALUES ('3', 'biz', '综合业务', 'singleGrid', 'icon-plugin', '1', '0', '0', NULL, NULL);
INSERT INTO `eova_menu` VALUES ('4', 'eova_menu', '菜单管理', 'singleGrid', 'icon-applicationsidetree', '1', '1', '0', 'com.eova.core.menu.MenuIntercept', NULL);
INSERT INTO `eova_menu` VALUES ('5', 'eova_button', '按钮管理', 'singleGrid', 'icon-layout', '2', '1', '0', NULL, NULL);
INSERT INTO `eova_menu` VALUES ('6', 'eova_object', '对象管理', 'singleGrid', 'icon-databasetable', '3', '1', '0', 'com.eova.core.object.ObjectIntercept', NULL);
INSERT INTO `eova_menu` VALUES ('7', 'eova_field', '字段管理', 'singleGrid', 'icon-applicationviewcolumns', '4', '1', '0', NULL, NULL);
INSERT INTO `eova_menu` VALUES ('8', 'eova_dictionary', '字典管理', 'singleGrid', 'icon-bookopen', '5', '1', '0', NULL, NULL);
INSERT INTO `eova_menu` VALUES ('9', 'eova_icon', '图标实例', 'diy', 'icon-applicationviewicons', '6', '1', '0', NULL, '/toIcon');
INSERT INTO `eova_menu` VALUES ('10', 'sys_auth_user', '用户管理', 'singleGrid', 'icon-group', '1', '2', '0', NULL, NULL);
INSERT INTO `eova_menu` VALUES ('11', 'sys_auth_role', '角色管理', 'singleGrid', 'icon-groupkey', '2', '2', '0', NULL, NULL);
INSERT INTO `eova_menu` VALUES ('12', 'sys_log', '系统日志', 'singleGrid', 'icon-tablemultiple', '3', '2', '0', NULL, NULL);
INSERT INTO `eova_menu` VALUES ('22', 'biz_demo', '功能演示', 'dir', 'icon-bookopen', '1', '3', '0', NULL, NULL);
INSERT INTO `eova_menu` VALUES ('23', 'biz_demo_users', '单表CRUD', 'singleGrid', 'icon-grouplink', '1', '22', '0', NULL, NULL);
INSERT INTO `eova_menu` VALUES ('24', 'biz_demo_tool', '富文本编辑', 'singleGrid', 'icon-controller', '1', '22', '0', NULL, NULL);
INSERT INTO `eova_menu` VALUES ('26', 'biz_demo_usersitem', '下拉和查找', 'singleGrid', 'icon-controller', '1', '22', '0', NULL, NULL);
INSERT INTO `eova_menu` VALUES ('27', 'biz_demo_usersview', '多表视图', 'singleGrid', 'icon-applicationviewcolumns', '1', '22', '0', NULL, NULL);
INSERT INTO `eova_menu` VALUES ('29', 'biz_demo_userscell', '表格单元格编辑', 'singleGrid', 'icon-applicationviewcolumns', '1', '22', '0', NULL, NULL);
INSERT INTO `eova_menu` VALUES ('30', 'biz_demo_import', '导入导出', 'singleGrid', 'icon-arrowswitch', '1', '22', '0', NULL, NULL);

-- ----------------------------
-- Table structure for `eova_menu_object`
-- ----------------------------
DROP TABLE IF EXISTS `eova_menu_object`;
CREATE TABLE `eova_menu_object` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_code` varchar(50) NOT NULL COMMENT '菜单编码',
  `object_code` varchar(50) NOT NULL COMMENT '对象编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_menu_object
-- ----------------------------
INSERT INTO `eova_menu_object` VALUES ('1', 'eova_menu', 'eova_menu_code');
INSERT INTO `eova_menu_object` VALUES ('2', 'eova_button', 'eova_button_code');
INSERT INTO `eova_menu_object` VALUES ('3', 'eova_object', 'eova_object_code');
INSERT INTO `eova_menu_object` VALUES ('4', 'eova_field', 'eova_field_code');
INSERT INTO `eova_menu_object` VALUES ('5', 'eova_dictionary', 'eova_dict_code');
INSERT INTO `eova_menu_object` VALUES ('6', 'sys_auth_user', 'eova_user_code');
INSERT INTO `eova_menu_object` VALUES ('7', 'sys_auth_role', 'eova_role_code');
INSERT INTO `eova_menu_object` VALUES ('8', 'sys_log', 'eova_log_code');
INSERT INTO `eova_menu_object` VALUES ('9', 'biz_player', 'player_code');
INSERT INTO `eova_menu_object` VALUES ('10', 'eova_object', 'eova_object_code');
INSERT INTO `eova_menu_object` VALUES ('11', 'myfun1', 'player_code');
INSERT INTO `eova_menu_object` VALUES ('12', 'biz_demo_users', 'player_code');
INSERT INTO `eova_menu_object` VALUES ('13', 'biz_demo_tool', 'item_code');
INSERT INTO `eova_menu_object` VALUES ('14', 'biz_demo_usersitem', 'users_item_code');
INSERT INTO `eova_menu_object` VALUES ('15', 'biz_demo_usersitem', 'users_item_code');
INSERT INTO `eova_menu_object` VALUES ('16', 'biz_demo_usersview', 'v_users_code');
INSERT INTO `eova_menu_object` VALUES ('17', 'biz_demo_userscelledit', 'player_code');
INSERT INTO `eova_menu_object` VALUES ('18', 'biz_demo_userscell', 'celledit_users_code');
INSERT INTO `eova_menu_object` VALUES ('19', 'biz_demo_import', 'player_code');

-- ----------------------------
-- Table structure for `eova_object`
-- ----------------------------
DROP TABLE IF EXISTS `eova_object`;
CREATE TABLE `eova_object` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(100) NOT NULL COMMENT '对象编码',
  `name` varchar(100) NOT NULL COMMENT '对象名称',
  `view_name` varchar(255) DEFAULT NULL COMMENT '查询数据视图',
  `table_name` varchar(255) DEFAULT NULL COMMENT '保存数据主表',
  `pk_name` varchar(50) NOT NULL COMMENT '主键',
  `data_source` varchar(50) DEFAULT 'main' COMMENT '数据源',
  `is_single` tinyint(1) DEFAULT '1' COMMENT '是否单选',
  `is_celledit` tinyint(1) DEFAULT '0' COMMENT '是否可行内编辑',
  `is_show_num` tinyint(1) DEFAULT '1' COMMENT '是否显示行号',
  `is_default_pk_desc` tinyint(1) DEFAULT '1' COMMENT '是否默认根据主键逆序',
  `filter` varchar(500) DEFAULT NULL COMMENT '初始数据过滤条件',
  `diy_card` varchar(255) DEFAULT NULL COMMENT '自定义卡片面板',
  `diy_list` varchar(255) DEFAULT NULL COMMENT '自定义列表面板',
  `diy_intercept` varchar(255) DEFAULT NULL COMMENT '自定义业务拦截器',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_object
-- ----------------------------
INSERT INTO `eova_object` VALUES ('1', 'eova_menu_code', '菜单', NULL, 'eova_menu', 'id', 'eova', '1', '0', '1', '1', NULL, NULL, NULL, NULL);
INSERT INTO `eova_object` VALUES ('2', 'eova_object_code', '对象模型', NULL, 'eova_object', 'id', 'eova', '1', '0', '1', '1', NULL, NULL, NULL, NULL);
INSERT INTO `eova_object` VALUES ('3', 'eova_user_code', '用户', NULL, 'eova_user', 'id', 'eova', '1', '0', '1', '1', NULL, NULL, NULL, NULL);
INSERT INTO `eova_object` VALUES ('4', 'eova_field_code', '字段管理', NULL, 'eova_field', 'id', 'eova', '0', '1', '1', '1', NULL, NULL, NULL, NULL);
INSERT INTO `eova_object` VALUES ('5', 'eova_button_code', '按钮管理', NULL, 'eova_button', 'id', 'eova', '1', '0', '1', '1', NULL, NULL, NULL, NULL);
INSERT INTO `eova_object` VALUES ('6', 'eova_dict_code', '字典管理', NULL, 'eova_dict', 'id', 'eova', '1', '0', '1', '1', NULL, NULL, NULL, NULL);
INSERT INTO `eova_object` VALUES ('7', 'eova_role_code', '角色管理', NULL, 'eova_role', 'id', 'eova', '1', '0', '1', '1', NULL, NULL, NULL, NULL);
INSERT INTO `eova_object` VALUES ('8', 'eova_log_code', '操作日志', NULL, 'eova_log', 'id', 'eova', '1', '0', '1', '1', NULL, NULL, NULL, NULL);
INSERT INTO `eova_object` VALUES ('9', 'player_code', '玩家信息', NULL, 'users', 'id', 'main', '1', '1', '1', '1', NULL, 'where status=1 or status=0', NULL, NULL);
INSERT INTO `eova_object` VALUES ('10', 'item_code', '道具', NULL, 'item', 'id', 'main', '1', '0', '1', '1', NULL, NULL, NULL, NULL);
INSERT INTO `eova_object` VALUES ('11', 'users_item_code', '艺人关联道具', NULL, 'users_item', 'id', 'main', '1', '0', '1', '1', NULL, NULL, NULL, NULL);
INSERT INTO `eova_object` VALUES ('13', 'v_users_code', '女优详情', 'v_users', NULL, 'id', 'main', '1', '0', '1', '1', NULL, NULL, NULL, NULL);
INSERT INTO `eova_object` VALUES ('15', 'celledit_users_code', '可编辑用户', NULL, 'users', 'id', 'main', '1', '1', '1', '1', NULL, NULL, NULL, NULL);
INSERT INTO `eova_object` VALUES ('16', 'users_exp_code', '女优信息拓展', NULL, 'users_exp', 'users_id', 'main', '1', '0', '1', '1', NULL, NULL, NULL, NULL);
INSERT INTO `eova_object` VALUES ('17', 'test01', 'test01', NULL, 'dict', 'id', 'main', '1', '0', '1', '1', NULL, NULL, NULL, NULL);
INSERT INTO `eova_object` VALUES ('18', 'test02', 'test02', NULL, 'users', 'id', 'main', '1', '0', '1', '1', NULL, NULL, NULL, NULL);
INSERT INTO `eova_object` VALUES ('19', 'test04', 'test04', NULL, 'users', 'id', 'main', '1', '0', '1', '1', NULL, NULL, NULL, NULL);
INSERT INTO `eova_object` VALUES ('20', 'test05', 'test05', NULL, 'users', 'id', 'main', '1', '0', '1', '1', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for `eova_role`
-- ----------------------------
DROP TABLE IF EXISTS `eova_role`;
CREATE TABLE `eova_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '角色名',
  `info` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `fun` varchar(5000) DEFAULT NULL COMMENT '已授权功能',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_role
-- ----------------------------
INSERT INTO `eova_role` VALUES ('1', '超级管理员', '系统最高级权限', NULL);
INSERT INTO `eova_role` VALUES ('2', '运营总监', '运营监控', NULL);
INSERT INTO `eova_role` VALUES ('3', '编辑', '网站数据编辑', NULL);
INSERT INTO `eova_role` VALUES ('4', '数据分析', '报表查看', NULL);
INSERT INTO `eova_role` VALUES ('5', '客服', '解答用户反馈', NULL);
INSERT INTO `eova_role` VALUES ('6', '测试', '常用功能测试', NULL);
INSERT INTO `eova_role` VALUES ('7', '运营专员', '游戏运营专员', NULL);
INSERT INTO `eova_role` VALUES ('8', '商务', '商务日常操作', NULL);

-- ----------------------------
-- Table structure for `eova_role_btn`
-- ----------------------------
DROP TABLE IF EXISTS `eova_role_btn`;
CREATE TABLE `eova_role_btn` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rid` int(11) NOT NULL COMMENT '角色',
  `bid` int(11) NOT NULL COMMENT '功能',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1402 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_role_btn
-- ----------------------------
INSERT INTO `eova_role_btn` VALUES ('1348', '1', '65');
INSERT INTO `eova_role_btn` VALUES ('1349', '1', '67');
INSERT INTO `eova_role_btn` VALUES ('1350', '1', '68');
INSERT INTO `eova_role_btn` VALUES ('1351', '1', '69');
INSERT INTO `eova_role_btn` VALUES ('1352', '1', '66');
INSERT INTO `eova_role_btn` VALUES ('1353', '1', '71');
INSERT INTO `eova_role_btn` VALUES ('1354', '1', '72');
INSERT INTO `eova_role_btn` VALUES ('1355', '1', '73');
INSERT INTO `eova_role_btn` VALUES ('1356', '1', '74');
INSERT INTO `eova_role_btn` VALUES ('1357', '1', '75');
INSERT INTO `eova_role_btn` VALUES ('1358', '1', '76');
INSERT INTO `eova_role_btn` VALUES ('1359', '1', '77');
INSERT INTO `eova_role_btn` VALUES ('1360', '1', '78');
INSERT INTO `eova_role_btn` VALUES ('1361', '1', '79');
INSERT INTO `eova_role_btn` VALUES ('1362', '1', '80');
INSERT INTO `eova_role_btn` VALUES ('1363', '1', '81');
INSERT INTO `eova_role_btn` VALUES ('1364', '1', '86');
INSERT INTO `eova_role_btn` VALUES ('1365', '1', '87');
INSERT INTO `eova_role_btn` VALUES ('1366', '1', '96');
INSERT INTO `eova_role_btn` VALUES ('1367', '1', '97');
INSERT INTO `eova_role_btn` VALUES ('1368', '1', '98');
INSERT INTO `eova_role_btn` VALUES ('1369', '1', '99');
INSERT INTO `eova_role_btn` VALUES ('1370', '1', '7');
INSERT INTO `eova_role_btn` VALUES ('1371', '1', '29');
INSERT INTO `eova_role_btn` VALUES ('1372', '1', '30');
INSERT INTO `eova_role_btn` VALUES ('1373', '1', '31');
INSERT INTO `eova_role_btn` VALUES ('1374', '1', '8');
INSERT INTO `eova_role_btn` VALUES ('1375', '1', '26');
INSERT INTO `eova_role_btn` VALUES ('1376', '1', '27');
INSERT INTO `eova_role_btn` VALUES ('1377', '1', '28');
INSERT INTO `eova_role_btn` VALUES ('1378', '1', '32');
INSERT INTO `eova_role_btn` VALUES ('1379', '1', '9');
INSERT INTO `eova_role_btn` VALUES ('1380', '1', '1');
INSERT INTO `eova_role_btn` VALUES ('1381', '1', '10');
INSERT INTO `eova_role_btn` VALUES ('1382', '1', '11');
INSERT INTO `eova_role_btn` VALUES ('1383', '1', '12');
INSERT INTO `eova_role_btn` VALUES ('1384', '1', '23');
INSERT INTO `eova_role_btn` VALUES ('1385', '1', '2');
INSERT INTO `eova_role_btn` VALUES ('1386', '1', '16');
INSERT INTO `eova_role_btn` VALUES ('1387', '1', '17');
INSERT INTO `eova_role_btn` VALUES ('1388', '1', '18');
INSERT INTO `eova_role_btn` VALUES ('1389', '1', '3');
INSERT INTO `eova_role_btn` VALUES ('1390', '1', '19');
INSERT INTO `eova_role_btn` VALUES ('1391', '1', '20');
INSERT INTO `eova_role_btn` VALUES ('1392', '1', '21');
INSERT INTO `eova_role_btn` VALUES ('1393', '1', '22');
INSERT INTO `eova_role_btn` VALUES ('1394', '1', '4');
INSERT INTO `eova_role_btn` VALUES ('1395', '1', '13');
INSERT INTO `eova_role_btn` VALUES ('1396', '1', '14');
INSERT INTO `eova_role_btn` VALUES ('1397', '1', '15');
INSERT INTO `eova_role_btn` VALUES ('1398', '1', '5');
INSERT INTO `eova_role_btn` VALUES ('1399', '1', '24');
INSERT INTO `eova_role_btn` VALUES ('1400', '1', '25');
INSERT INTO `eova_role_btn` VALUES ('1401', '1', '6');

-- ----------------------------
-- Table structure for `eova_user`
-- ----------------------------
DROP TABLE IF EXISTS `eova_user`;
CREATE TABLE `eova_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` varchar(30) NOT NULL COMMENT '帐号',
  `login_pwd` varchar(50) NOT NULL COMMENT '密码',
  `nickname` varchar(255) NOT NULL COMMENT '中文名',
  `rid` int(11) DEFAULT '0' COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_user
-- ----------------------------
INSERT INTO `eova_user` VALUES ('1', 'admin', '000000', 'Jieven', '1');
INSERT INTO `eova_user` VALUES ('3', 'test', '000000', '测试', '2');
