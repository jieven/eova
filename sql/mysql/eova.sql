/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50513
Source Host           : localhost:3306
Source Database       : eova

Target Server Type    : MYSQL
Target Server Version : 50513
File Encoding         : 65001

Date: 2015-08-13 02:50:06
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
  `order_num` int(11) DEFAULT '0' COMMENT '排序号',
  `group_num` int(11) DEFAULT '0' COMMENT '分组号',
  `is_base` tinyint(1) DEFAULT '0' COMMENT '是否基础功能',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=253 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_button
-- ----------------------------
INSERT INTO `eova_button` VALUES ('1', 'eova_menu', '查询', 'query', 'grid/query', '1', '0', '0');
INSERT INTO `eova_button` VALUES ('2', 'eova_button', '查询', 'query', 'grid/query', '1', '0', '0');
INSERT INTO `eova_button` VALUES ('5', 'eova_dictionary', '查询', 'query', 'grid/query', '1', '0', '0');
INSERT INTO `eova_button` VALUES ('6', 'eova_icon', '查询', 'query', 'grid/query', '1', '0', '0');
INSERT INTO `eova_button` VALUES ('7', 'sys_auth_user', '查询', 'query', 'grid/query', '1', '0', '0');
INSERT INTO `eova_button` VALUES ('8', 'sys_auth_role', '查询', 'query', 'grid/query', '1', '0', '0');
INSERT INTO `eova_button` VALUES ('9', 'sys_log', '查询', 'query', 'grid/query', '1', '0', '0');
INSERT INTO `eova_button` VALUES ('10', 'eova_menu', '新增', '/eova/menu/btn/add.html', 'menu/add', '1', '0', '0');
INSERT INTO `eova_button` VALUES ('16', 'eova_button', '新增', '/eova/widget/form/btn/add.html', 'crud/add', '2', '0', '1');
INSERT INTO `eova_button` VALUES ('17', 'eova_button', '修改', '/eova/widget/form/btn/update.html', 'crud/update', '3', '0', '1');
INSERT INTO `eova_button` VALUES ('18', 'eova_button', '删除', '/eova/widget/form/btn/delete.html', 'crud/delete', '4', '0', '1');
INSERT INTO `eova_button` VALUES ('22', 'eova_object', '导入元数据', '/eova/metadata/btn/import.html', 'meta/import', '10', '0', '0');
INSERT INTO `eova_button` VALUES ('23', 'eova_menu', '基本功能', '/eova/menu/btn/fun.html', 'meta/fun', '5', '0', '0');
INSERT INTO `eova_button` VALUES ('24', 'eova_dictionary', '新增', '/eova/widget/form/btn/add.html', 'crud/add', '2', '0', '1');
INSERT INTO `eova_button` VALUES ('25', 'eova_dictionary', '修改', '/eova/widget/form/btn/update.html', 'crud/update', '3', '0', '1');
INSERT INTO `eova_button` VALUES ('26', 'sys_auth_role', '新增', '/eova/widget/form/btn/add.html', 'crud/add', '2', '0', '1');
INSERT INTO `eova_button` VALUES ('27', 'sys_auth_role', '修改', '/eova/widget/form/btn/update.html', 'crud/update', '3', '0', '1');
INSERT INTO `eova_button` VALUES ('28', 'sys_auth_role', '删除', '/eova/widget/form/btn/delete.html', 'crud/delete', '4', '0', '1');
INSERT INTO `eova_button` VALUES ('29', 'sys_auth_user', '新增', '/eova/widget/form/btn/add.html', 'crud/add', '2', '0', '1');
INSERT INTO `eova_button` VALUES ('30', 'sys_auth_user', '修改', '/eova/widget/form/btn/update.html', 'crud/update', '3', '0', '1');
INSERT INTO `eova_button` VALUES ('31', 'sys_auth_user', '删除', '/eova/widget/form/btn/delete.html', 'crud/delete', '4', '0', '1');
INSERT INTO `eova_button` VALUES ('32', 'sys_auth_role', '权限分配', '/eova/auth/btn/roleChoose.html', '', '5', '0', '0');
INSERT INTO `eova_button` VALUES ('65', 'biz_demo_users', '查询', 'query', 'grid/query', '1', '0', '0');
INSERT INTO `eova_button` VALUES ('66', 'biz_demo_tool', '查询', 'query', 'grid/query', '1', '0', '0');
INSERT INTO `eova_button` VALUES ('71', 'biz_demo_tool', '新增', '/eova/widget/form/btn/add.html', 'crud/add', '2', '0', '1');
INSERT INTO `eova_button` VALUES ('72', 'biz_demo_tool', '修改', '/eova/widget/form/btn/update.html', 'crud/update', '3', '0', '1');
INSERT INTO `eova_button` VALUES ('73', 'biz_demo_tool', '删除', '/eova/widget/form/btn/delete.html', 'crud/delete', '4', '0', '1');
INSERT INTO `eova_button` VALUES ('74', 'biz_demo_usersitem', '查询', 'query', 'grid/query', '1', '0', '0');
INSERT INTO `eova_button` VALUES ('75', 'biz_demo_usersitem', '新增', '/eova/widget/form/btn/add.html', 'crud/add', '2', '0', '1');
INSERT INTO `eova_button` VALUES ('76', 'biz_demo_usersitem', '修改', '/eova/widget/form/btn/update.html', 'crud/update', '3', '0', '1');
INSERT INTO `eova_button` VALUES ('77', 'biz_demo_usersitem', '删除', '/eova/widget/form/btn/delete.html', 'crud/delete', '4', '0', '1');
INSERT INTO `eova_button` VALUES ('78', 'biz_demo_usersview', '查询', 'query', 'grid/query', '1', '0', '0');
INSERT INTO `eova_button` VALUES ('79', 'biz_demo_usersview', '新增', '/eova/widget/form/btn/add.html', 'crud/add', '2', '0', '1');
INSERT INTO `eova_button` VALUES ('80', 'biz_demo_usersview', '修改', '/eova/widget/form/btn/update.html', 'crud/update', '3', '0', '1');
INSERT INTO `eova_button` VALUES ('81', 'biz_demo_usersview', '删除', '/eova/widget/form/btn/delete.html', 'crud/delete', '4', '0', '1');
INSERT INTO `eova_button` VALUES ('86', 'biz_demo_userscell', '查询', 'query', 'grid/query', '1', '0', '0');
INSERT INTO `eova_button` VALUES ('87', 'biz_demo_import', '查询', 'query', 'grid/query', '1', '0', '0');
INSERT INTO `eova_button` VALUES ('96', 'biz_demo_import', '新增', '/eova/widget/form/btn/add.html', 'crud/add', '2', '0', '1');
INSERT INTO `eova_button` VALUES ('97', 'biz_demo_import', '修改', '/eova/widget/form/btn/update.html', 'crud/update', '3', '0', '1');
INSERT INTO `eova_button` VALUES ('98', 'biz_demo_import', '删除', '/eova/widget/form/btn/delete.html', 'crud/delete', '4', '0', '1');
INSERT INTO `eova_button` VALUES ('99', 'biz_demo_import', '导入', '/eova/template/crud/single/btn/import.html', 'crud/import', '0', '0', '1');
INSERT INTO `eova_button` VALUES ('100', 'biz_users_exp', '查询', 'query', 'grid/query', '1', '0', '0');
INSERT INTO `eova_button` VALUES ('101', 'biz_users_exp', '新增', '/eova/widget/form/btn/add.html', 'crud/add', '2', '0', '1');
INSERT INTO `eova_button` VALUES ('102', 'biz_users_exp', '修改', '/eova/widget/form/btn/update.html', 'crud/update', '3', '0', '1');
INSERT INTO `eova_button` VALUES ('103', 'biz_users_exp', '删除', '/eova/widget/form/btn/delete.html', 'crud/delete', '4', '0', '1');
INSERT INTO `eova_button` VALUES ('185', 'biz_demo_users', '新增', '/eova/widget/form/btn/add.html', 'crud/add', '2', '0', '1');
INSERT INTO `eova_button` VALUES ('186', 'biz_demo_users', '修改', '/eova/widget/form/btn/update.html', 'crud/update', '3', '0', '1');
INSERT INTO `eova_button` VALUES ('187', 'biz_demo_users', '删除', '/eova/widget/form/btn/delete.html', 'crud/delete', '4', '0', '1');
INSERT INTO `eova_button` VALUES ('231', 'eova_menu', '修改', '/eova/widget/form/btn/update.html', 'crud/update', '3', '0', '1');
INSERT INTO `eova_button` VALUES ('232', 'eova_menu', '删除', '/eova/widget/form/btn/delete.html', 'crud/delete', '4', '0', '1');
INSERT INTO `eova_button` VALUES ('239', 'eova_object', '查询', 'query', 'grid/query', '1', '0', '0');
INSERT INTO `eova_button` VALUES ('246', 'eova_object', '新增', '/eova/widget/form/btn/add.html', 'crud/add', '2', '0', '1');
INSERT INTO `eova_button` VALUES ('247', 'eova_object', '修改', '/eova/widget/form/btn/update.html', 'crud/update', '3', '0', '1');
INSERT INTO `eova_button` VALUES ('248', 'eova_object', '删除', '/eova/widget/form/btn/delete.html', 'crud/delete', '4', '0', '1');
INSERT INTO `eova_button` VALUES ('249', 'eova_object', '子添加', '/eova/widget/form/btn/add.html', 'crud/add', '2', '1', '1');
INSERT INTO `eova_button` VALUES ('250', 'eova_object', '子修改', '/eova/widget/form/btn/update.html', 'crud/update', '3', '1', '1');
INSERT INTO `eova_button` VALUES ('251', 'eova_object', '子删除', '/eova/widget/form/btn/delete.html', 'crud/delete', '4', '1', '1');
INSERT INTO `eova_button` VALUES ('252', 'eova_menu', 'diy', '/eova/menu/btn/add.html', '34324', '10', '0', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

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
INSERT INTO `eova_dict` VALUES ('17', '图片框', '图片框', 'eova_field', 'type');
INSERT INTO `eova_dict` VALUES ('18', '文件框', '文件框', 'eova_field', 'type');

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
  `is_multiple` tinyint(1) DEFAULT '0' COMMENT '是否多选项',
  `placeholder` varchar(255) DEFAULT NULL COMMENT '输入提示',
  `validator` varchar(255) DEFAULT NULL COMMENT 'UI校验表达式',
  `defaulter` varchar(255) DEFAULT NULL COMMENT '默认值表达式',
  `formatter` varchar(2000) DEFAULT NULL COMMENT '格式化器',
  `width` int(4) DEFAULT '130' COMMENT '控件宽度',
  `height` int(4) DEFAULT '20' COMMENT '控件高度',
  `config` varchar(2000) DEFAULT NULL COMMENT '拓展配置',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=173 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_field
-- ----------------------------
INSERT INTO `eova_field` VALUES ('1', 'meta_template', null, 'meta', 'meta', '0', 'string', '文本框', '9', null, '0', '1', '1', '1', '1', '1', '1', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('2', 'eova_user_code', null, 'nickname', '昵称', '0', 'string', '文本框', '0', null, '1', '1', '1', '1', '1', '0', '1', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('3', 'eova_user_code', null, 'login_id', '登录帐号', '0', 'string', '上传框', '0', null, '1', '1', '1', '1', '1', '0', '1', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('4', 'eova_user_code', null, 'login_pwd', '登录密码', '0', 'string', '文本框', '0', null, '0', '0', '0', '1', '1', '0', '1', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('5', 'eova_menu_code', null, 'id', 'ID', '1', 'number', '自增框', '1', null, '0', '0', '1', '1', '1', '0', '1', '0', null, null, null, null, '100', '20', null);
INSERT INTO `eova_field` VALUES ('6', 'eova_menu_code', null, 'code', '编码', '0', 'string', '文本框', '4', null, '1', '1', '1', '1', '0', '0', '1', '0', null, null, null, null, '180', '20', null);
INSERT INTO `eova_field` VALUES ('7', 'eova_menu_code', null, 'name', '名称', '0', 'string', '文本框', '2', null, '1', '1', '1', '1', '1', '0', '1', '0', null, null, null, null, '180', '20', null);
INSERT INTO `eova_field` VALUES ('8', 'eova_menu_code', null, 'type', '类型', '0', 'string', '文本框', '1', null, '0', '1', '1', '1', '1', '0', '1', '0', null, null, null, null, '100', '20', null);
INSERT INTO `eova_field` VALUES ('10', 'eova_menu_code', null, 'icon', '图标', '0', 'string', '图标框', '6', null, '0', '0', '1', '1', '1', '0', '1', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('11', 'eova_menu_code', null, 'order_num', '序号', '0', 'number', '文本框', '9', null, '0', '1', '1', '1', '1', '0', '1', '0', null, null, null, null, '30', '20', null);
INSERT INTO `eova_field` VALUES ('12', 'eova_menu_code', null, 'parentId', '父节点', '0', 'number', '查找框', '9', 'select id ID,name 目录菜单 from eova_menu where type = \'dir\';ds=eova', '1', '1', '1', '1', '1', '0', '1', '0', null, null, null, null, '100', '20', null);
INSERT INTO `eova_field` VALUES ('13', 'eova_object_code', null, 'id', 'ID', '1', 'number', '自增框', '1', null, '0', '1', '1', '1', '1', '0', '1', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('14', 'eova_object_code', null, 'code', '编码', '0', 'string', '文本框', '2', null, '1', '1', '1', '1', '0', '0', '1', '0', null, null, null, null, '200', '20', null);
INSERT INTO `eova_field` VALUES ('15', 'eova_object_code', null, 'name', '名称', '0', 'string', '文本框', '3', null, '0', '1', '1', '1', '1', '0', '1', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('16', 'eova_object_code', null, 'view_name', '视图', '0', 'string', '文本框', '4', null, '1', '1', '1', '1', '1', '0', '0', '0', null, null, null, null, '70', '20', null);
INSERT INTO `eova_field` VALUES ('17', 'eova_object_code', null, 'table_name', '数据表', '0', 'string', '文本框', '5', null, '1', '1', '1', '1', '1', '0', '0', '0', null, null, null, null, '70', '20', null);
INSERT INTO `eova_field` VALUES ('18', 'eova_object_code', null, 'pk_name', '主键', '0', 'string', '文本框', '6', null, '0', '1', '1', '1', '1', '0', '0', '0', null, null, null, null, '70', '20', null);
INSERT INTO `eova_field` VALUES ('19', 'eova_object_code', null, 'data_source', '数据源', '0', 'string', '下拉框', '7', 'select value ID,name CN from eova_dict where object = \'eova_object\' and field = \'data_source\';ds=eova', '0', '1', '1', '1', '1', '0', '1', '0', null, null, null, null, '70', '20', null);
INSERT INTO `eova_field` VALUES ('20', 'eova_object_code', null, 'is_single', '是否单选', '0', 'number', '复选框', '8', null, '0', '1', '1', '1', '1', '0', '0', '0', null, null, null, null, '70', '20', null);
INSERT INTO `eova_field` VALUES ('21', 'eova_object_code', null, 'is_show_num', '显示行号', '0', 'number', '复选框', '9', null, '0', '1', '1', '1', '1', '0', '0', '0', null, null, null, null, '70', '20', null);
INSERT INTO `eova_field` VALUES ('22', 'eova_object_code', null, 'is_default_pk_desc', '默认逆序', '0', 'number', '复选框', '10', null, '0', '1', '1', '1', '1', '0', '0', '0', null, null, null, null, '70', '20', null);
INSERT INTO `eova_field` VALUES ('23', 'eova_object_code', null, 'filter', '过滤条件', '0', 'string', '文本域', '11', null, '0', '1', '1', '1', '1', '0', '0', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('26', 'eova_field_code', null, 'id', 'ID', '1', 'number', '自增框', '1', null, '0', '0', '1', '1', '1', '0', '1', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('27', 'eova_field_code', null, 'object_code', '对象编码', '0', 'string', '查找框', '2', 'select code 编码,name 名称 from eova_object where 1=1;ds=eova', '1', '1', '1', '1', '1', '0', '1', '0', null, null, 'eova_user_code', null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('28', 'eova_field_code', null, 'en', '字段名', '0', 'string', '文本框', '4', null, '1', '1', '1', '1', '1', '1', '1', '0', '数据库的字段名', null, null, null, '70', '20', null);
INSERT INTO `eova_field` VALUES ('29', 'eova_field_code', null, 'cn', '中文名', '0', 'string', '文本框', '3', null, '1', '1', '1', '1', '1', '1', '1', '0', '字段对应的中文描述', null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('30', 'eova_field_code', null, 'is_auto', '自增长', '0', 'number', '复选框', '20', null, '0', '1', '1', '1', '1', '1', '0', '0', null, null, null, null, '70', '20', null);
INSERT INTO `eova_field` VALUES ('31', 'eova_field_code', null, 'data_type', '字段类型', '0', 'string', '下拉框', '6', 'select value ID,name CN from eova_dict where object = \'eova_field\' and field = \'data_type\';ds=eova', '0', '1', '1', '1', '1', '0', '1', '0', null, null, 'string', null, '70', '20', null);
INSERT INTO `eova_field` VALUES ('32', 'eova_field_code', null, 'type', '控件类型', '0', 'string', '下拉框', '7', 'select value ID,name CN from eova_dict where object = \'eova_field\' and field = \'type\';ds=eova', '1', '1', '1', '1', '1', '1', '1', '0', null, null, '文本框', null, '70', '20', null);
INSERT INTO `eova_field` VALUES ('33', 'eova_field_code', null, 'order_num', '排序', '0', 'number', '文本框', '8', null, '0', '1', '1', '1', '1', '1', '1', '0', null, null, '0', null, '50', '20', null);
INSERT INTO `eova_field` VALUES ('34', 'eova_field_code', null, 'exp', '表达式', '0', 'string', '文本域', '31', null, '0', '1', '1', '1', '1', '1', '0', '0', '查找框和下拉框需需要表达式', null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('35', 'eova_field_code', null, 'is_query', '允许查询', '0', 'number', '复选框', '21', null, '0', '1', '1', '1', '1', '1', '0', '0', null, null, null, null, '70', '20', null);
INSERT INTO `eova_field` VALUES ('36', 'eova_field_code', null, 'is_show', '允许显示', '0', 'number', '复选框', '22', null, '0', '1', '1', '1', '1', '1', '0', '0', null, null, '1', null, '70', '20', null);
INSERT INTO `eova_field` VALUES ('37', 'eova_field_code', null, 'is_order', '允许排序', '0', 'number', '复选框', '23', null, '0', '1', '1', '1', '1', '1', '0', '0', null, null, '1', null, '70', '20', null);
INSERT INTO `eova_field` VALUES ('38', 'eova_field_code', null, 'is_add', '允许新增', '0', 'number', '复选框', '24', null, '0', '1', '1', '1', '1', '1', '0', '0', null, null, '1', null, '70', '20', null);
INSERT INTO `eova_field` VALUES ('39', 'eova_field_code', null, 'is_update', '允许修改', '0', 'number', '复选框', '25', null, '0', '1', '1', '1', '1', '1', '0', '0', null, null, '1', null, '70', '20', null);
INSERT INTO `eova_field` VALUES ('40', 'eova_field_code', null, 'is_required', '是否必填', '0', 'number', '复选框', '25', null, '0', '1', '1', '1', '1', '1', '0', '0', null, null, '1', null, '70', '20', null);
INSERT INTO `eova_field` VALUES ('41', 'eova_field_code', null, 'defaulter', '默认值表达式', '0', 'string', '文本域', '32', null, '0', '1', '1', '1', '1', '1', '0', '0', '初始默认值', null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('42', 'eova_field_code', null, 'width', '宽度', '0', 'number', '文本框', '17', null, '0', '1', '1', '1', '1', '1', '1', '0', null, null, '130', null, '50', '20', null);
INSERT INTO `eova_field` VALUES ('43', 'eova_field_code', null, 'height', '高度', '0', 'number', '文本框', '18', null, '0', '1', '1', '1', '1', '1', '1', '0', null, null, '80', null, '50', '20', null);
INSERT INTO `eova_field` VALUES ('44', 'eova_field_code', null, 'is_multiple', '允许多选', '0', 'number', '复选框', '26', null, '0', '1', '1', '1', '1', '1', '0', '0', null, null, null, null, '70', '20', null);
INSERT INTO `eova_field` VALUES ('45', 'eova_button_code', null, 'id', 'ID', '1', 'number', '自增框', '1', null, '0', '1', '1', '1', '1', '1', '1', '0', null, null, null, null, '50', '20', null);
INSERT INTO `eova_field` VALUES ('46', 'eova_button_code', null, 'menu_code', '菜单编码', '0', 'string', '查找框', '2', 'select code 菜单编码,name 菜单名称 from eova_menu where 1=1;ds=eova', '1', '1', '1', '1', '1', '0', '1', '0', null, 'eovacode', null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('47', 'eova_button_code', null, 'name', '功能名称', '0', 'string', '文本框', '3', null, '1', '1', '1', '1', '1', '0', '1', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('48', 'eova_button_code', null, 'ui', 'UI路径', '0', 'string', '文本框', '4', null, '1', '1', '1', '1', '1', '1', '1', '0', null, null, null, null, '280', '20', null);
INSERT INTO `eova_field` VALUES ('49', 'eova_button_code', null, 'bs', 'BS路径', '0', 'string', '文本框', '5', null, '1', '1', '1', '1', '1', '0', '1', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('50', 'eova_dict_code', null, 'id', 'ID', '1', 'number', '自增框', '1', null, '0', '1', '1', '1', '1', '0', '1', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('51', 'eova_dict_code', null, 'value', '值', '0', 'string', '文本框', '2', null, '1', '1', '1', '1', '1', '0', '1', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('52', 'eova_dict_code', null, 'name', '字段名', '0', 'string', '文本框', '3', null, '1', '1', '1', '1', '1', '0', '1', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('53', 'eova_dict_code', null, 'object', '列名', '0', 'string', '文本框', '4', null, '1', '1', '1', '1', '1', '0', '1', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('54', 'eova_dict_code', null, 'field', '对象', '0', 'string', '文本框', '5', null, '1', '1', '1', '1', '1', '0', '1', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('59', 'eova_menu_code', null, 'biz_intercept', '自定义业务拦截器', '0', 'string', '文本域', '14', null, '0', '1', '1', '1', '1', '0', '0', '0', null, null, null, null, '300', '20', null);
INSERT INTO `eova_field` VALUES ('60', 'eova_button_code', null, 'order_num', '序号', '0', 'number', '文本框', '6', null, '0', '1', '1', '1', '1', '1', '1', '0', '按钮的显示顺序', 'digits', '10', null, '50', '20', null);
INSERT INTO `eova_field` VALUES ('61', 'eova_role_code', null, 'id', 'ID', '1', 'number', '自增框', '0', null, '0', '1', '1', '1', '1', '0', '1', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('62', 'eova_role_code', null, 'name', '角色名', '0', 'string', '文本框', '0', null, '1', '1', '1', '1', '1', '0', '1', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('63', 'eova_role_code', null, 'info', '角色描述', '0', 'string', '文本框', '0', null, '0', '1', '1', '1', '1', '0', '1', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('64', 'eova_user_code', null, 'rid', '角色', '0', 'string', '下拉框', '0', 'select id ID,name CN from eova_role where 1=1;ds=eova', '0', '1', '1', '1', '1', '0', '1', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('70', 'eova_log_code', null, 'id', 'id', '1', 'number', '自增框', '1', null, '0', '1', '1', '1', '1', '0', '0', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('71', 'eova_log_code', null, 'user_id', '操作用户', '0', 'number', '查找框', '2', 'select id UID,nickname 用户名 from eova_user where 1=1;ds=eova', '1', '1', '1', '1', '1', '0', '0', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('72', 'eova_log_code', null, 'type', '日志类型', '0', 'number', '文本框', '3', 'select value ID,name CN from eova_dict where object = \'eova_log\' and field = \'type\';ds=eova', '1', '1', '1', '1', '1', '0', '0', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('73', 'eova_log_code', null, 'ip', '操作IP', '0', 'string', '文本框', '4', null, '1', '1', '1', '1', '1', '0', '0', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('75', 'player_code', null, 'id', 'ID', '1', 'number', '自增框', '1', null, '0', '1', '1', '1', '1', '0', '0', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('76', 'player_code', null, 'status', '状态', '0', 'number', '下拉框', '2', 'select value ID,name CN from webdict where object = \'users\' and field = \'status\';ds=main', '1', '1', '1', '1', '1', '1', '1', '0', null, null, '0', null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('77', 'player_code', null, 'login_id', '登录账户', '1', 'string', '文本框', '3', null, '1', '1', '1', '1', '1', '1', '1', '1', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('78', 'player_code', null, 'login_pwd', '录登密码', '1', 'string', '文本框', '4', null, '1', '1', '1', '1', '1', '1', '1', '1', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('79', 'player_code', null, 'nickname', '艺人姓名', '1', 'string', '文本框', '1', '', '1', '1', '1', '1', '1', '1', '1', '1', '', null, '', 'function(value, row, index, field) {\r\n    if (value) {\r\n        return \'<b style=\"color:red\">\'+ value +\'</b>\';\r\n    }\r\n    return value;\r\n}', '130', '20', null);
INSERT INTO `eova_field` VALUES ('80', 'player_code', null, 'reg_time', '注册时间', '1', 'time', '时间框', '6', null, '1', '1', '1', '1', '1', '1', '1', '1', null, null, 'CURRENT_TIMESTAMP', null, '180', '20', null);
INSERT INTO `eova_field` VALUES ('81', 'eova_menu_code', null, 'url', 'URL', '0', 'string', '文本框', '15', null, '0', '1', '1', '1', '1', '0', '0', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('82', 'eova_field_code', null, 'is_edit', '允许行内编辑', '0', 'number', '复选框', '25', null, '0', '1', '1', '1', '1', '1', '0', '0', null, null, null, null, '70', '20', null);
INSERT INTO `eova_field` VALUES ('83', 'eova_object_code', null, 'is_celledit', '行内编辑', '0', 'number', '复选框', '8', null, '0', '1', '1', '1', '1', '0', '0', '0', null, null, null, null, '70', '20', null);
INSERT INTO `eova_field` VALUES ('84', 'player_code', null, 'info', '备注', '0', 'string', '编辑框', '9', null, '0', '1', '1', '0', '0', '0', '0', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('85', 'item_code', null, 'id', 'ID', '1', 'number', '自增框', '1', null, '1', '1', '1', '1', '1', '1', '1', '1', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('86', 'item_code', null, 'name', '名称', '0', 'string', '文本框', '2', null, '1', '1', '1', '1', '1', '1', '1', '1', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('87', 'item_code', null, 'info', '介绍', '1', 'string', '编辑框', '4', '', '1', '1', '1', '1', '1', '1', '1', '1', '', null, '', 'function(value, row, index, field) {\r\n    if (value && value.length > 10) {\r\n        return \'<span title=\"\' + value + \'\">\' + value + \'</span>\';\r\n    }\r\n    return value;\r\n}', '200', '20', null);
INSERT INTO `eova_field` VALUES ('88', 'users_item_code', null, 'id', 'ID', '1', 'number', '自增框', '1', null, '0', '1', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('89', 'users_item_code', null, 'users_id', '艺人', '0', 'number', '查找框', '2', 'select id ID,nickname 艺人 from users where 1=1;ds=main', '1', '1', '1', '1', '1', '1', '1', '1', null, null, null, 'function(value,row,index,field){return\'<a target=\"_blank\" href=\"http://g.cn\" style=\"color:blue\">\'+value+\'</a>\'}', '130', '20', null);
INSERT INTO `eova_field` VALUES ('90', 'users_item_code', null, 'item_id', '道具', '0', 'number', '下拉框', '3', 'select id ID,name CN from item where 1=1;ds=main', '1', '1', '0', '1', '1', '1', '1', '0', null, null, null, null, '300', '20', null);
INSERT INTO `eova_field` VALUES ('124', 'v_users_code', 'player_code', 'id', 'ID', '1', 'number', '自增框', '1', null, '0', '1', '1', '1', '1', '1', '0', '0', null, null, '0', null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('125', 'v_users_code', 'player_code', 'status', '状态', '0', 'number', '文本框', '2', null, '1', '1', '1', '1', '1', '1', '1', '0', null, 'range[1~9]', '0', null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('126', 'v_users_code', 'player_code', 'login_id', '登录账户', '0', 'string', '文本框', '3', null, '1', '1', '1', '1', '1', '1', '1', '0', '请输入帐号', 'username', null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('127', 'v_users_code', 'player_code', 'login_pwd', '录登密码', '0', 'string', '文本框', '4', null, '0', '1', '1', '1', '1', '1', '1', '0', '请输入密码', 'length[6~16]', null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('128', 'v_users_code', 'player_code', 'nickname', '昵称', '0', 'string', '文本域', '20', null, '1', '1', '1', '1', '1', '1', '1', '0', null, 'chinese;length[2~10]', null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('129', 'v_users_code', 'player_code', 'reg_time', '注册时间', '0', 'time', '时间框', '6', null, '0', '1', '1', '1', '1', '1', '1', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('130', 'v_users_code', 'player_code', 'info', '备注', '0', 'string', '文本域', '30', null, '0', '1', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('132', 'v_users_code', 'users_exp_code', 'exp', '经验值', '0', 'number', '文本框', '9', null, '0', '1', '1', '1', '1', '1', '1', '0', null, null, '0', null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('133', 'v_users_code', 'users_exp_code', 'avg', '年龄', '0', 'number', '文本框', '10', null, '0', '1', '1', '1', '1', '1', '1', '0', null, 'range[1~150]', '1', null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('134', 'v_users_code', 'users_exp_code', 'qq', 'QQ', '0', 'string', '文本框', '22', null, '0', '1', '1', '1', '1', '1', '1', '0', null, 'qq', null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('146', 'celledit_users_code', null, 'id', 'id', '1', 'number', '自增框', '1', null, '0', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('147', 'celledit_users_code', null, 'status', '状态', '0', 'number', '文本框', '2', null, '0', '1', '1', '1', '1', '1', '1', '0', null, null, '0', null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('148', 'celledit_users_code', null, 'login_id', '登录账户', '0', 'string', '文本框', '3', null, '0', '1', '1', '1', '1', '1', '1', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('149', 'celledit_users_code', null, 'login_pwd', '录登密码', '0', 'string', '文本框', '4', null, '0', '1', '1', '1', '1', '1', '1', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('150', 'celledit_users_code', null, 'nickname', '昵称', '0', 'string', '文本域', '5', null, '1', '1', '1', '1', '1', '1', '1', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('151', 'celledit_users_code', null, 'reg_time', '注册时间', '0', 'time', '时间框', '6', null, '0', '1', '1', '1', '1', '1', '1', '0', null, null, 'CURRENT_TIMESTAMP', null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('152', 'celledit_users_code', null, 'info', '备注', '0', 'string', '文本域', '7', null, '0', '1', '1', '1', '1', '1', '1', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('153', 'users_exp_code', null, 'users_id', 'users_id', '0', 'number', '文本框', '1', '', '0', '1', '1', '1', '1', '1', '0', '0', '', null, '', '', '130', '20', null);
INSERT INTO `eova_field` VALUES ('154', 'users_exp_code', null, 'exp', '经验值', '0', 'number', '文本框', '2', null, '0', '1', '1', '1', '1', '1', '1', '0', null, null, '0', null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('155', 'users_exp_code', null, 'avg', '年龄', '0', 'number', '文本框', '3', null, '0', '1', '1', '1', '1', '1', '1', '0', null, null, '0', null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('156', 'users_exp_code', null, 'qq', 'QQ', '0', 'string', '文本框', '4', null, '0', '1', '1', '1', '1', '1', '1', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('157', 'eova_field_code', null, 'placeholder', '输入提示', '0', 'string', '文本框', '28', null, '0', '1', '1', '1', '1', '1', '0', '0', 'input的placeholder属性', null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('158', 'eova_field_code', null, 'formatter', '格式化器', '0', 'string', '文本域', '34', null, '0', '1', '1', '1', '1', '1', '0', '0', '格式化的JS,参考EasyUI datagrid formatter', null, null, null, '130', '150', null);
INSERT INTO `eova_field` VALUES ('159', 'item_code', null, 'img', '图片', '0', 'string', '图片框', '3', '', '0', '1', '1', '1', '1', '1', '1', '1', '', null, '', 'function(value, row, index, field) {\r\n    if (value) {\r\n        return \'<img src=\"/upimg/\' + value + \'\">\';\r\n    }\r\n    return value;\r\n}', '200', '20', null);
INSERT INTO `eova_field` VALUES ('160', 'eova_user_code', null, 'id', 'ID', '1', 'number', '自增框', '0', null, '0', '1', '1', '1', '1', '0', '1', '0', null, null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('168', 'eova_button_code', null, 'group_num', '分组号', '0', 'number', '文本框', '7', null, '0', '1', '1', '1', '1', '1', '1', '0', 'Toolbar分组号', 'digits', '0', null, '50', '20', null);
INSERT INTO `eova_field` VALUES ('169', 'eova_button_code', null, 'is_base', '是否基础功能', '0', 'number', '复选框', '8', null, '0', '1', '0', '0', '0', '0', '0', '0', null, null, '0', null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('170', 'users_item_code', '', 'my_fun', '操作', '0', 'number', '文本框', '4', '', '0', '1', '0', '0', '0', '0', '0', '0', '', null, '', 'function(value,row,index,field){return\'<a href=\"/form/add/users_item_code\" style=\"color:blue\">添加</a> <a href=\"/form/update/users_item_code-\'+row.id+\'\" style=\"color:blue\">修改</a>\'}', '130', '20', null);
INSERT INTO `eova_field` VALUES ('171', 'eova_field_code', null, 'validator', 'UI校验器', '0', 'string', '文本域', '33', null, '0', '1', '1', '1', '1', '1', '0', '0', 'UI校验规则', null, null, null, '130', '20', null);
INSERT INTO `eova_field` VALUES ('172', 'eova_log_code', null, 'info', '详情', '0', 'string', '文本框', '5', null, '0', '1', '1', '1', '1', '1', '1', '0', null, null, null, null, '130', '20', null);

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
  `config` varchar(500) DEFAULT NULL COMMENT '菜单配置JSON',
  `diy_js` varchar(255) DEFAULT NULL COMMENT '依赖JS文件',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_menu
-- ----------------------------
INSERT INTO `eova_menu` VALUES ('1', 'eova', '平台维护', 'dir', 'icon-bricks', '3', '0', '0', null, null, null, null);
INSERT INTO `eova_menu` VALUES ('2', 'sys', '系统管理', 'dir', 'icon-cog', '2', '0', '0', null, null, null, null);
INSERT INTO `eova_menu` VALUES ('3', 'biz', '综合业务', 'dir', 'icon-plugin', '1', '0', '0', null, null, null, null);
INSERT INTO `eova_menu` VALUES ('4', 'eova_menu', '菜单管理', 'singleGrid', 'icon-applicationsidetree', '1', '1', '0', 'com.eova.core.menu.MenuIntercept', null, '{\"objectCode\":\"eova_menu_code\"}', null);
INSERT INTO `eova_menu` VALUES ('5', 'eova_button', '按钮管理', 'singleGrid', 'icon-layout', '2', '1', '0', null, null, '{\"objectCode\":\"eova_button_code\"}', null);
INSERT INTO `eova_menu` VALUES ('6', 'eova_object', '元数据管理', 'masterSlaveGrid', 'icon-databasetable', '3', '1', '0', 'com.eova.core.object.ObjectIntercept', null, '{\"fields\":[\"object_code\"],\"objectCode\":\"eova_object_code\",\"objectField\":\"code\",\"objects\":[\"eova_field_code\"]}', null);
INSERT INTO `eova_menu` VALUES ('8', 'eova_dictionary', '字典管理', 'singleGrid', 'icon-bookopen', '5', '1', '0', null, null, '{\"objectCode\":\"eova_dict_code\"}', null);
INSERT INTO `eova_menu` VALUES ('9', 'eova_icon', '图标实例', 'diy', 'icon-applicationviewicons', '6', '1', '0', null, '/toIcon', null, null);
INSERT INTO `eova_menu` VALUES ('10', 'sys_auth_user', '用户管理', 'singleGrid', 'icon-group', '1', '2', '0', null, null, '{\"objectCode\":\"eova_user_code\"}', null);
INSERT INTO `eova_menu` VALUES ('12', 'sys_log', '系统日志', 'singleGrid', 'icon-tablemultiple', '3', '2', '0', null, null, '{\"objectCode\":\"eova_log_code\"}', null);
INSERT INTO `eova_menu` VALUES ('22', 'biz_demo', '功能演示', 'dir', 'icon-bookopen', '1', '3', '0', null, null, null, null);
INSERT INTO `eova_menu` VALUES ('23', 'biz_demo_users', '单表CRUD', 'singleGrid', 'icon-grouplink', '1', '22', '0', null, null, '{\"objectCode\":\"player_code\"}', null);
INSERT INTO `eova_menu` VALUES ('24', 'biz_demo_tool', '富文本编辑', 'singleGrid', 'icon-controller', '1', '22', '0', null, null, '{\"objectCode\":\"item_code\"}', null);
INSERT INTO `eova_menu` VALUES ('26', 'biz_demo_usersitem', '下拉和查找', 'singleGrid', 'icon-controller', '1', '22', '0', null, null, '{\"objectCode\":\"users_item_code\"}', null);
INSERT INTO `eova_menu` VALUES ('27', 'biz_demo_usersview', '多表视图', 'singleGrid', 'icon-applicationviewcolumns', '1', '22', '0', null, null, '{\"objectCode\":\"v_users_code\"}', null);
INSERT INTO `eova_menu` VALUES ('29', 'biz_demo_userscell', '表格单元格编辑', 'singleGrid', 'icon-applicationviewcolumns', '1', '22', '0', null, null, '{\"objectCode\":\"celledit_users_code\"}', null);
INSERT INTO `eova_menu` VALUES ('30', 'biz_demo_import', '导入导出', 'singleGrid', 'icon-arrowswitch', '1', '22', '0', null, null, '{\"objectCode\":\"player_code\"}', null);
INSERT INTO `eova_menu` VALUES ('31', 'biz_users_exp', '非自增主键拓展表', 'singleGrid', 'icon-applicationdouble', '1', '22', '0', '', '', '{\"objectCode\":\"users_exp_code\"}', '/ui/js/test.js');

-- ----------------------------
-- Table structure for `eova_menu_object`
-- ----------------------------
DROP TABLE IF EXISTS `eova_menu_object`;
CREATE TABLE `eova_menu_object` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_code` varchar(50) NOT NULL COMMENT '菜单编码',
  `object_code` varchar(50) NOT NULL COMMENT '对象编码',
  `indexs` int(11) DEFAULT '0' COMMENT '对象索引',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_menu_object
-- ----------------------------
INSERT INTO `eova_menu_object` VALUES ('1', 'eova_menu', 'eova_menu_code', '0');
INSERT INTO `eova_menu_object` VALUES ('2', 'eova_button', 'eova_button_code', '0');
INSERT INTO `eova_menu_object` VALUES ('3', 'eova_object', 'eova_object_code', '0');
INSERT INTO `eova_menu_object` VALUES ('4', 'eova_field', 'eova_object_code', '0');
INSERT INTO `eova_menu_object` VALUES ('5', 'eova_dictionary', 'eova_dict_code', '0');
INSERT INTO `eova_menu_object` VALUES ('6', 'sys_auth_user', 'eova_user_code', '0');
INSERT INTO `eova_menu_object` VALUES ('7', 'sys_auth_role', 'eova_role_code', '0');
INSERT INTO `eova_menu_object` VALUES ('8', 'sys_log', 'eova_log_code', '0');
INSERT INTO `eova_menu_object` VALUES ('9', 'biz_player', 'player_code', '0');
INSERT INTO `eova_menu_object` VALUES ('10', 'eova_object', 'eova_field_code', '1');
INSERT INTO `eova_menu_object` VALUES ('11', 'myfun1', 'player_code', '0');
INSERT INTO `eova_menu_object` VALUES ('12', 'biz_demo_users', 'player_code', '0');
INSERT INTO `eova_menu_object` VALUES ('13', 'biz_demo_tool', 'item_code', '0');
INSERT INTO `eova_menu_object` VALUES ('14', 'biz_demo_usersitem', 'users_item_code', '0');
INSERT INTO `eova_menu_object` VALUES ('15', 'biz_demo_usersitem', 'users_item_code', '0');
INSERT INTO `eova_menu_object` VALUES ('16', 'biz_demo_usersview', 'v_users_code', '0');
INSERT INTO `eova_menu_object` VALUES ('17', 'biz_demo_userscelledit', 'player_code', '0');
INSERT INTO `eova_menu_object` VALUES ('18', 'biz_demo_userscell', 'celledit_users_code', '0');
INSERT INTO `eova_menu_object` VALUES ('19', 'biz_demo_import', 'player_code', '0');
INSERT INTO `eova_menu_object` VALUES ('20', 'biz_users_exp', 'users_exp_code', '0');

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
  `filter` varchar(500) DEFAULT NULL COMMENT '初始数据过滤条件',
  `default_order` varchar(255) DEFAULT NULL COMMENT '默认排序字段(desc)',
  `diy_card` varchar(255) DEFAULT NULL COMMENT '自定义卡片面板',
  `diy_list` varchar(255) DEFAULT NULL COMMENT '自定义列表面板',
  `biz_intercept` varchar(255) DEFAULT NULL COMMENT '自定义业务拦截器',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_object
-- ----------------------------
INSERT INTO `eova_object` VALUES ('1', 'meta_template', '元对象模版数据', null, '', '', '', '1', '0', '1', null, null, null, null, null);
INSERT INTO `eova_object` VALUES ('2', 'eova_object_code', '对象', null, 'eova_object', 'id', 'eova', '1', '0', '1', null, 'id desc', null, null, 'com.eova.core.object.ObjectIntercept');
INSERT INTO `eova_object` VALUES ('3', 'eova_user_code', '用户', null, 'eova_user', 'id', 'eova', '1', '0', '1', null, 'id desc', null, null, null);
INSERT INTO `eova_object` VALUES ('4', 'eova_field_code', '字段', null, 'eova_field', 'id', 'eova', '1', '1', '1', null, 'order_num', null, null, null);
INSERT INTO `eova_object` VALUES ('5', 'eova_button_code', '按钮', null, 'eova_button', 'id', 'eova', '1', '0', '1', null, 'id desc', null, null, null);
INSERT INTO `eova_object` VALUES ('6', 'eova_dict_code', '字典管理', null, 'eova_dict', 'id', 'eova', '1', '0', '1', null, 'id desc', null, null, null);
INSERT INTO `eova_object` VALUES ('7', 'eova_role_code', '角色管理', null, 'eova_role', 'id', 'eova', '1', '0', '1', null, 'id desc', null, null, null);
INSERT INTO `eova_object` VALUES ('8', 'eova_menu_code', '菜单', null, 'eova_menu', 'id', 'eova', '1', '0', '1', null, 'id desc', null, null, 'com.eova.core.menu.MenuIntercept');
INSERT INTO `eova_object` VALUES ('9', 'player_code', '玩家信息', null, 'users', 'id', 'main', '1', '1', '1', null, 'id desc', null, null, null);
INSERT INTO `eova_object` VALUES ('10', 'item_code', '道具', null, 'item', 'id', 'main', '1', '0', '1', null, null, null, null, null);
INSERT INTO `eova_object` VALUES ('11', 'users_item_code', '艺人关联道具', null, 'users_item', 'id', 'main', '1', '0', '1', null, null, null, null, null);
INSERT INTO `eova_object` VALUES ('13', 'v_users_code', '女优详情', 'v_users', null, 'id', 'main', '1', '0', '1', null, null, null, null, null);
INSERT INTO `eova_object` VALUES ('15', 'celledit_users_code', '可编辑用户', null, 'users', 'id', 'main', '1', '1', '1', null, null, null, null, null);
INSERT INTO `eova_object` VALUES ('16', 'users_exp_code', '女优信息拓展', null, 'users_exp', 'users_id', 'main', '1', '0', '1', null, null, null, null, null);
INSERT INTO `eova_object` VALUES ('18', 'eova_log_code', '操作日志', null, 'eova_log', 'id', 'eova', '1', '0', '1', null, 'id desc', null, null, null);

-- ----------------------------
-- Table structure for `eova_role`
-- ----------------------------
DROP TABLE IF EXISTS `eova_role`;
CREATE TABLE `eova_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '角色名',
  `info` varchar(255) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_role
-- ----------------------------
INSERT INTO `eova_role` VALUES ('1', '超级管理员', '系统最高级权限');
INSERT INTO `eova_role` VALUES ('2', '运营总监', '运营监控');
INSERT INTO `eova_role` VALUES ('3', '编辑', '网站数据编辑');
INSERT INTO `eova_role` VALUES ('4', '数据分析', '报表查看');
INSERT INTO `eova_role` VALUES ('5', '客服', '解答用户反馈');
INSERT INTO `eova_role` VALUES ('6', '测试', '常用功能测试');
INSERT INTO `eova_role` VALUES ('7', '运营专员', '游戏运营专员');
INSERT INTO `eova_role` VALUES ('8', '商务', '商务日常操作');

-- ----------------------------
-- Table structure for `eova_role_btn`
-- ----------------------------
DROP TABLE IF EXISTS `eova_role_btn`;
CREATE TABLE `eova_role_btn` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rid` int(11) NOT NULL COMMENT '角色',
  `bid` int(11) NOT NULL COMMENT '功能',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=492 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_role_btn
-- ----------------------------
INSERT INTO `eova_role_btn` VALUES ('55', '6', '65');
INSERT INTO `eova_role_btn` VALUES ('56', '6', '67');
INSERT INTO `eova_role_btn` VALUES ('57', '6', '68');
INSERT INTO `eova_role_btn` VALUES ('58', '6', '69');
INSERT INTO `eova_role_btn` VALUES ('59', '6', '66');
INSERT INTO `eova_role_btn` VALUES ('60', '6', '71');
INSERT INTO `eova_role_btn` VALUES ('61', '6', '72');
INSERT INTO `eova_role_btn` VALUES ('62', '6', '73');
INSERT INTO `eova_role_btn` VALUES ('63', '6', '74');
INSERT INTO `eova_role_btn` VALUES ('64', '6', '75');
INSERT INTO `eova_role_btn` VALUES ('65', '6', '76');
INSERT INTO `eova_role_btn` VALUES ('66', '6', '77');
INSERT INTO `eova_role_btn` VALUES ('67', '6', '78');
INSERT INTO `eova_role_btn` VALUES ('68', '6', '79');
INSERT INTO `eova_role_btn` VALUES ('69', '6', '80');
INSERT INTO `eova_role_btn` VALUES ('70', '6', '81');
INSERT INTO `eova_role_btn` VALUES ('71', '6', '86');
INSERT INTO `eova_role_btn` VALUES ('72', '6', '87');
INSERT INTO `eova_role_btn` VALUES ('73', '6', '96');
INSERT INTO `eova_role_btn` VALUES ('74', '6', '97');
INSERT INTO `eova_role_btn` VALUES ('75', '6', '98');
INSERT INTO `eova_role_btn` VALUES ('76', '6', '99');
INSERT INTO `eova_role_btn` VALUES ('77', '6', '7');
INSERT INTO `eova_role_btn` VALUES ('78', '6', '8');
INSERT INTO `eova_role_btn` VALUES ('79', '6', '9');
INSERT INTO `eova_role_btn` VALUES ('80', '6', '1');
INSERT INTO `eova_role_btn` VALUES ('81', '6', '10');
INSERT INTO `eova_role_btn` VALUES ('82', '6', '11');
INSERT INTO `eova_role_btn` VALUES ('83', '6', '23');
INSERT INTO `eova_role_btn` VALUES ('84', '6', '2');
INSERT INTO `eova_role_btn` VALUES ('85', '6', '16');
INSERT INTO `eova_role_btn` VALUES ('86', '6', '17');
INSERT INTO `eova_role_btn` VALUES ('87', '6', '3');
INSERT INTO `eova_role_btn` VALUES ('88', '6', '20');
INSERT INTO `eova_role_btn` VALUES ('89', '6', '22');
INSERT INTO `eova_role_btn` VALUES ('92', '6', '5');
INSERT INTO `eova_role_btn` VALUES ('93', '6', '24');
INSERT INTO `eova_role_btn` VALUES ('94', '6', '25');
INSERT INTO `eova_role_btn` VALUES ('99', '2', '65');
INSERT INTO `eova_role_btn` VALUES ('434', '1', '65');
INSERT INTO `eova_role_btn` VALUES ('435', '1', '185');
INSERT INTO `eova_role_btn` VALUES ('436', '1', '186');
INSERT INTO `eova_role_btn` VALUES ('437', '1', '187');
INSERT INTO `eova_role_btn` VALUES ('438', '1', '66');
INSERT INTO `eova_role_btn` VALUES ('439', '1', '71');
INSERT INTO `eova_role_btn` VALUES ('440', '1', '72');
INSERT INTO `eova_role_btn` VALUES ('441', '1', '73');
INSERT INTO `eova_role_btn` VALUES ('442', '1', '74');
INSERT INTO `eova_role_btn` VALUES ('443', '1', '75');
INSERT INTO `eova_role_btn` VALUES ('444', '1', '76');
INSERT INTO `eova_role_btn` VALUES ('445', '1', '77');
INSERT INTO `eova_role_btn` VALUES ('446', '1', '78');
INSERT INTO `eova_role_btn` VALUES ('447', '1', '79');
INSERT INTO `eova_role_btn` VALUES ('448', '1', '80');
INSERT INTO `eova_role_btn` VALUES ('449', '1', '81');
INSERT INTO `eova_role_btn` VALUES ('450', '1', '86');
INSERT INTO `eova_role_btn` VALUES ('451', '1', '99');
INSERT INTO `eova_role_btn` VALUES ('452', '1', '87');
INSERT INTO `eova_role_btn` VALUES ('453', '1', '96');
INSERT INTO `eova_role_btn` VALUES ('454', '1', '97');
INSERT INTO `eova_role_btn` VALUES ('455', '1', '98');
INSERT INTO `eova_role_btn` VALUES ('456', '1', '100');
INSERT INTO `eova_role_btn` VALUES ('457', '1', '101');
INSERT INTO `eova_role_btn` VALUES ('458', '1', '102');
INSERT INTO `eova_role_btn` VALUES ('459', '1', '103');
INSERT INTO `eova_role_btn` VALUES ('460', '1', '7');
INSERT INTO `eova_role_btn` VALUES ('461', '1', '29');
INSERT INTO `eova_role_btn` VALUES ('462', '1', '30');
INSERT INTO `eova_role_btn` VALUES ('463', '1', '31');
INSERT INTO `eova_role_btn` VALUES ('464', '1', '8');
INSERT INTO `eova_role_btn` VALUES ('465', '1', '26');
INSERT INTO `eova_role_btn` VALUES ('466', '1', '27');
INSERT INTO `eova_role_btn` VALUES ('467', '1', '28');
INSERT INTO `eova_role_btn` VALUES ('468', '1', '32');
INSERT INTO `eova_role_btn` VALUES ('469', '1', '9');
INSERT INTO `eova_role_btn` VALUES ('470', '1', '1');
INSERT INTO `eova_role_btn` VALUES ('471', '1', '10');
INSERT INTO `eova_role_btn` VALUES ('472', '1', '231');
INSERT INTO `eova_role_btn` VALUES ('473', '1', '232');
INSERT INTO `eova_role_btn` VALUES ('474', '1', '23');
INSERT INTO `eova_role_btn` VALUES ('475', '1', '252');
INSERT INTO `eova_role_btn` VALUES ('476', '1', '2');
INSERT INTO `eova_role_btn` VALUES ('477', '1', '16');
INSERT INTO `eova_role_btn` VALUES ('478', '1', '17');
INSERT INTO `eova_role_btn` VALUES ('479', '1', '18');
INSERT INTO `eova_role_btn` VALUES ('480', '1', '239');
INSERT INTO `eova_role_btn` VALUES ('481', '1', '246');
INSERT INTO `eova_role_btn` VALUES ('482', '1', '247');
INSERT INTO `eova_role_btn` VALUES ('483', '1', '248');
INSERT INTO `eova_role_btn` VALUES ('484', '1', '22');
INSERT INTO `eova_role_btn` VALUES ('485', '1', '249');
INSERT INTO `eova_role_btn` VALUES ('486', '1', '250');
INSERT INTO `eova_role_btn` VALUES ('487', '1', '251');
INSERT INTO `eova_role_btn` VALUES ('488', '1', '5');
INSERT INTO `eova_role_btn` VALUES ('489', '1', '24');
INSERT INTO `eova_role_btn` VALUES ('490', '1', '25');
INSERT INTO `eova_role_btn` VALUES ('491', '1', '6');

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_user
-- ----------------------------
INSERT INTO `eova_user` VALUES ('1', 'admin', '000000', '超级管理员', '1');
INSERT INTO `eova_user` VALUES ('3', 'test', '000000', '测试', '2');
INSERT INTO `eova_user` VALUES ('5', 'eova', '000000', 'Eova', '6');
