/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50513
Source Host           : localhost:3306
Source Database       : eova

Target Server Type    : MYSQL
Target Server Version : 50513
File Encoding         : 65001

Date: 2015-03-27 00:57:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `eova_button`
-- ----------------------------
DROP TABLE IF EXISTS `eova_button`;
CREATE TABLE `eova_button` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menuCode` varchar(255) NOT NULL DEFAULT '' COMMENT '菜单Code',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '按钮名称',
  `ui` varchar(255) NOT NULL DEFAULT '' COMMENT '按钮UI路径',
  `bs` varchar(500) DEFAULT '' COMMENT '按钮BS路径',
  `indexNum` int(11) DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `menuCode_index` (`menuCode`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_button
-- ----------------------------
INSERT INTO `eova_button` VALUES ('1', 'eova_menu', '查询', '', '', '0');
INSERT INTO `eova_button` VALUES ('2', 'eova_button', '查询', '', '', '0');
INSERT INTO `eova_button` VALUES ('3', 'eova_object', '查询', '', '', '0');
INSERT INTO `eova_button` VALUES ('4', 'eova_item', '查询', '', '', '0');
INSERT INTO `eova_button` VALUES ('5', 'eova_dictionary', '查询', '', '', '0');
INSERT INTO `eova_button` VALUES ('6', 'eova_icon', '查询', '', '', '0');
INSERT INTO `eova_button` VALUES ('7', 'sys_auth_user', '查询', '', '', '0');
INSERT INTO `eova_button` VALUES ('8', 'sys_auth_role', '查询', '', '', '0');
INSERT INTO `eova_button` VALUES ('9', 'sys_log', '查询', '', '', '0');
INSERT INTO `eova_button` VALUES ('10', 'eova_menu', '新增', '/eova/menu/btn/add.html', '/menu/add', '1');
INSERT INTO `eova_button` VALUES ('11', 'eova_menu', '修改', '/eova/menu/btn/update.html', 'crud/update', '2');
INSERT INTO `eova_button` VALUES ('12', 'eova_menu', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '3');
INSERT INTO `eova_button` VALUES ('13', 'eova_item', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '1');
INSERT INTO `eova_button` VALUES ('14', 'eova_item', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '2');
INSERT INTO `eova_button` VALUES ('15', 'eova_item', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '3');
INSERT INTO `eova_button` VALUES ('16', 'eova_button', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '1');
INSERT INTO `eova_button` VALUES ('17', 'eova_button', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '2');
INSERT INTO `eova_button` VALUES ('18', 'eova_button', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '3');
INSERT INTO `eova_button` VALUES ('19', 'eova_object', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '1');
INSERT INTO `eova_button` VALUES ('20', 'eova_object', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '2');
INSERT INTO `eova_button` VALUES ('21', 'eova_object', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '3');
INSERT INTO `eova_button` VALUES ('22', 'eova_object', '导入元数据', '/eova/metadata/btn/import.html', 'metadata/import', '5');
INSERT INTO `eova_button` VALUES ('23', 'eova_menu', '基本功能', '/eova/metadata/btn/fun.html', '/metadata/menuFun', '5');
INSERT INTO `eova_button` VALUES ('24', 'eova_dictionary', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '1');
INSERT INTO `eova_button` VALUES ('25', 'eova_dictionary', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '2');
INSERT INTO `eova_button` VALUES ('26', 'sys_auth_role', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '1');
INSERT INTO `eova_button` VALUES ('27', 'sys_auth_role', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '2');
INSERT INTO `eova_button` VALUES ('28', 'sys_auth_role', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '3');
INSERT INTO `eova_button` VALUES ('29', 'sys_auth_user', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '1');
INSERT INTO `eova_button` VALUES ('30', 'sys_auth_user', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '2');
INSERT INTO `eova_button` VALUES ('31', 'sys_auth_user', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '3');
INSERT INTO `eova_button` VALUES ('32', 'sys_auth_role', '权限分配', '/eova/auth/btn/roleChoose.html', '/auth/roleChoose', '5');
INSERT INTO `eova_button` VALUES ('44', 'biz_player', '查询', '', '', '0');
INSERT INTO `eova_button` VALUES ('45', 'biz_player', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '0');
INSERT INTO `eova_button` VALUES ('46', 'biz_player', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '0');
INSERT INTO `eova_button` VALUES ('47', 'biz_player', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '0');

-- ----------------------------
-- Table structure for `eova_dict`
-- ----------------------------
DROP TABLE IF EXISTS `eova_dict`;
CREATE TABLE `eova_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `class` varchar(50) NOT NULL,
  `field` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_dict
-- ----------------------------
INSERT INTO `eova_dict` VALUES ('1', 'main', '默认', 'eova_object', 'dataSource');
INSERT INTO `eova_dict` VALUES ('2', 'eova', 'EOVA', 'eova_object', 'dataSource');
INSERT INTO `eova_dict` VALUES ('3', 'string', '字符', 'eova_item', 'dataType');
INSERT INTO `eova_dict` VALUES ('4', 'number', '数字', 'eova_item', 'dataType');
INSERT INTO `eova_dict` VALUES ('5', 'time', '时间', 'eova_item', 'dataType');
INSERT INTO `eova_dict` VALUES ('6', '1', '新增', 'eova_log', 'type');
INSERT INTO `eova_dict` VALUES ('7', '2', '修改', 'eova_log', 'type');
INSERT INTO `eova_dict` VALUES ('8', '3', '删除', 'eova_log', 'type');

-- ----------------------------
-- Table structure for `eova_item`
-- ----------------------------
DROP TABLE IF EXISTS `eova_item`;
CREATE TABLE `eova_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `objectCode` varchar(50) NOT NULL,
  `poCode` varchar(255) DEFAULT '' COMMENT '持久化对象',
  `en` varchar(30) NOT NULL COMMENT '英文名',
  `cn` varchar(30) NOT NULL DEFAULT '' COMMENT '中文名',
  `isAuto` tinyint(1) DEFAULT '0' COMMENT '主键是否自增长',
  `dataType` varchar(20) DEFAULT 'string' COMMENT '数据类型',
  `type` varchar(10) DEFAULT '文本框' COMMENT '控件类型',
  `indexNum` int(4) DEFAULT '9' COMMENT '排序索引',
  `exp` varchar(800) DEFAULT '' COMMENT '控件表达式',
  `isQuery` tinyint(1) DEFAULT '0' COMMENT '是否快速查询',
  `isShow` tinyint(1) DEFAULT '1' COMMENT '是否显示',
  `isOrder` tinyint(1) DEFAULT '1' COMMENT '是否排序',
  `isAdd` tinyint(1) DEFAULT '1' COMMENT '是否保存',
  `isUpdate` tinyint(1) DEFAULT '1' COMMENT '是否修改',
  `isNotNull` tinyint(1) DEFAULT '1' COMMENT '是否必填',
  `valueExp` varchar(255) DEFAULT '' COMMENT '默认值表达式',
  `width` int(4) DEFAULT '130' COMMENT '控件宽度',
  `height` int(4) DEFAULT '20' COMMENT '控件高度',
  `isMultiple` tinyint(1) DEFAULT '0' COMMENT '是否多选',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_item
-- ----------------------------
INSERT INTO `eova_item` VALUES ('1', 'eova_user_code', '', 'id', 'ID', '1', 'number', '自增框', '0', '', '0', '1', '1', '1', '1', '1', '', '130', '0', '0');
INSERT INTO `eova_item` VALUES ('2', 'eova_user_code', '', 'nickName', '昵称', '0', 'string', '文本框', '0', '', '1', '1', '1', '1', '1', '1', '', '130', '0', '0');
INSERT INTO `eova_item` VALUES ('3', 'eova_user_code', '', 'loginId', '登录帐号', '0', 'string', '文本框', '0', '', '1', '1', '1', '1', '1', '1', '', '130', '0', '0');
INSERT INTO `eova_item` VALUES ('4', 'eova_user_code', '', 'loginPwd', '登录密码', '0', 'string', '文本框', '0', '', '0', '0', '0', '1', '1', '1', '', '130', '0', '0');
INSERT INTO `eova_item` VALUES ('5', 'eova_menu_code', '', 'id', 'ID', '0', 'number', '自增框', '1', '', '0', '0', '1', '1', '1', '1', '', '130', '0', '0');
INSERT INTO `eova_item` VALUES ('6', 'eova_menu_code', '', 'code', '编码', '0', 'string', '查找框', '4', 'select code 编码,code 编码,name 名称 from eova_object where 1=1;ds=eova', '1', '1', '1', '1', '1', '1', '', '130', '0', '0');
INSERT INTO `eova_item` VALUES ('7', 'eova_menu_code', '', 'name', '名称', '0', 'string', '文本框', '2', '', '1', '1', '1', '1', '1', '1', '', '130', '0', '0');
INSERT INTO `eova_item` VALUES ('8', 'eova_menu_code', '', 'type', '类型', '0', 'string', '文本框', '3', '', '0', '1', '1', '1', '1', '1', '', '130', '0', '0');
INSERT INTO `eova_item` VALUES ('10', 'eova_menu_code', '', 'icon', '图标', '0', 'string', '图标框', '6', '', '0', '1', '1', '1', '1', '1', '', '130', '0', '0');
INSERT INTO `eova_item` VALUES ('11', 'eova_menu_code', '', 'indexNum', '序号', '0', 'number', '文本框', '9', '', '0', '1', '1', '1', '1', '1', '', '130', '0', '0');
INSERT INTO `eova_item` VALUES ('12', 'eova_menu_code', '', 'parentId', '父节点', '0', 'number', '查找框', '9', 'select id ID,name 菜单名称 from eova_menu where 1=1;ds=eova', '1', '1', '1', '1', '1', '1', '', '130', '0', '0');
INSERT INTO `eova_item` VALUES ('13', 'eova_object_code', '', 'id', 'ID', '1', 'number', '自增框', '1', '', '0', '0', '1', '1', '1', '1', '', '130', '0', '0');
INSERT INTO `eova_item` VALUES ('14', 'eova_object_code', '', 'code', '编码', '0', 'string', '文本框', '2', '', '1', '1', '1', '1', '0', '1', '', '130', '0', '0');
INSERT INTO `eova_item` VALUES ('15', 'eova_object_code', '', 'name', '名称', '0', 'string', '文本框', '3', '', '0', '1', '1', '1', '1', '1', '', '130', '0', '0');
INSERT INTO `eova_item` VALUES ('16', 'eova_object_code', '', 'view', '视图', '0', 'string', '文本框', '4', '', '1', '1', '1', '1', '1', '0', '', '80', '0', '0');
INSERT INTO `eova_item` VALUES ('17', 'eova_object_code', '', 'table', '数据表', '0', 'string', '文本框', '5', '', '1', '1', '1', '1', '1', '0', '', '80', '0', '0');
INSERT INTO `eova_item` VALUES ('18', 'eova_object_code', '', 'pkName', '主键', '0', 'string', '文本框', '6', '', '0', '1', '1', '1', '1', '1', '', '80', '0', '0');
INSERT INTO `eova_item` VALUES ('19', 'eova_object_code', '', 'dataSource', '数据源', '0', 'string', '下拉框', '7', 'select value ID,name CN from `eova_dict` where `class` = \'eova_object\' and field = \'dataSource\';ds=eova', '0', '1', '1', '1', '1', '1', '', '80', '0', '0');
INSERT INTO `eova_item` VALUES ('20', 'eova_object_code', '', 'isSingleSelect', '是否单选', '0', 'number', '复选框', '8', '', '0', '1', '1', '1', '1', '1', '', '80', '0', '0');
INSERT INTO `eova_item` VALUES ('21', 'eova_object_code', '', 'isShowNum', '显示行号', '0', 'number', '复选框', '9', '', '0', '1', '1', '1', '1', '1', '', '80', '0', '0');
INSERT INTO `eova_item` VALUES ('22', 'eova_object_code', '', 'isDefaultPkDesc', '默认按主键逆序', '0', 'number', '复选框', '10', '', '0', '1', '1', '1', '1', '1', '', '130', '0', '0');
INSERT INTO `eova_item` VALUES ('23', 'eova_object_code', '', 'filterWhere', '过滤条件', '0', 'string', '文本域', '11', '', '0', '1', '1', '1', '1', '0', '', '130', '0', '0');
INSERT INTO `eova_item` VALUES ('24', 'eova_object_code', '', 'diyCard', '自定义卡片面板', '0', 'string', '文本域', '12', '', '0', '1', '1', '1', '1', '0', '', '130', '0', '0');
INSERT INTO `eova_item` VALUES ('25', 'eova_object_code', '', 'diyList', '自定义列表面板', '0', 'string', '文本域', '13', '', '0', '0', '1', '1', '1', '1', '', '130', '0', '0');
INSERT INTO `eova_item` VALUES ('26', 'eova_item_code', '', 'id', 'ID', '1', 'number', '自增框', '1', '', '0', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('27', 'eova_item_code', '', 'objectCode', '对象编码', '0', 'string', '查找框', '2', 'select code 编码,name 名称 from eova_object where 1=1;ds=eova', '1', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('28', 'eova_item_code', '', 'en', '字段名', '0', 'string', '文本框', '3', '', '1', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('29', 'eova_item_code', '', 'cn', '中文名', '0', 'string', '文本框', '4', '', '1', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('30', 'eova_item_code', '', 'isAuto', '自增长', '0', 'number', '复选框', '20', '', '0', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('31', 'eova_item_code', '', 'dataType', '字段类型', '0', 'string', '下拉框', '6', 'select value ID,name CN from `eova_dict` where `class` = \'eova_item\' and field = \'dataType\';ds=eova', '0', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('32', 'eova_item_code', '', 'type', '控件类型', '0', 'string', '文本框', '7', '', '0', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('33', 'eova_item_code', '', 'indexNum', '排序', '0', 'number', '文本框', '8', '', '0', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('34', 'eova_item_code', '', 'exp', '表达式', '0', 'string', '文本域', '31', '', '0', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('35', 'eova_item_code', '', 'isQuery', '允许查询', '0', 'number', '复选框', '21', '', '0', '1', '1', '1', '1', '1', '', '80', '20', '0');
INSERT INTO `eova_item` VALUES ('36', 'eova_item_code', '', 'isShow', '允许显示', '0', 'number', '复选框', '22', '', '0', '1', '1', '1', '1', '1', '', '80', '20', '0');
INSERT INTO `eova_item` VALUES ('37', 'eova_item_code', '', 'isOrder', '允许排序', '0', 'number', '复选框', '23', '', '0', '1', '1', '1', '1', '1', '', '80', '20', '0');
INSERT INTO `eova_item` VALUES ('38', 'eova_item_code', '', 'isAdd', '允许新增', '0', 'number', '复选框', '24', '', '0', '1', '1', '1', '1', '0', '', '80', '20', '0');
INSERT INTO `eova_item` VALUES ('39', 'eova_item_code', '', 'isUpdate', '允许修改', '0', 'number', '复选框', '25', '', '0', '1', '1', '1', '1', '1', '', '80', '20', '0');
INSERT INTO `eova_item` VALUES ('40', 'eova_item_code', '', 'isNotNull', '是否必填', '0', 'number', '复选框', '25', '', '0', '1', '1', '1', '1', '0', '', '80', '20', '0');
INSERT INTO `eova_item` VALUES ('41', 'eova_item_code', '', 'valueExp', '默认值表达式', '0', 'string', '文本域', '32', '', '0', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('42', 'eova_item_code', '', 'width', '宽度', '0', 'number', '文本框', '17', '', '0', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('43', 'eova_item_code', '', 'height', '高度', '0', 'number', '文本框', '18', '', '0', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('44', 'eova_item_code', '', 'isMultiple', '允许多选', '0', 'number', '复选框', '26', '', '0', '1', '1', '1', '1', '1', '', '80', '20', '0');
INSERT INTO `eova_item` VALUES ('45', 'eova_button_code', '', 'id', 'ID', '1', 'number', '自增框', '1', '', '0', '1', '1', '1', '1', '0', '', '80', '20', '0');
INSERT INTO `eova_item` VALUES ('46', 'eova_button_code', '', 'menuCode', '菜单编码', '0', 'string', '查找框', '2', 'select code 菜单编码,name 菜单名称 from eova_menu where 1=1;ds=eova', '1', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('47', 'eova_button_code', '', 'name', '功能名称', '0', 'string', '文本框', '4', '', '1', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('48', 'eova_button_code', '', 'ui', 'UI路径', '0', 'string', '文本框', '5', '', '1', '1', '1', '1', '1', '1', '', '250', '20', '0');
INSERT INTO `eova_item` VALUES ('49', 'eova_button_code', '', 'bs', 'BS路径', '0', 'string', '文本框', '6', '', '1', '1', '1', '1', '1', '1', '', '250', '20', '0');
INSERT INTO `eova_item` VALUES ('50', 'eova_dict_code', '', 'id', 'ID', '1', 'number', '自增框', '1', '', '0', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('51', 'eova_dict_code', '', 'value', '值', '0', 'string', '文本框', '2', '', '1', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('52', 'eova_dict_code', '', 'valueen', '字段名', '0', 'string', '文本框', '3', '', '1', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('53', 'eova_dict_code', '', 'valuecn', '列名', '0', 'string', '文本框', '4', '', '1', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('54', 'eova_dict_code', '', 'object', '对象', '0', 'string', '文本框', '5', '', '1', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('55', 'eova_dict_code', '', 'item', 'item', '0', 'string', '文本框', '6', '', '1', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('59', 'eova_menu_code', '', 'bizIntercept', '自定义业务拦截器', '0', 'string', '文本域', '14', '', '0', '1', '1', '1', '1', '0', '', '130', '0', '0');
INSERT INTO `eova_item` VALUES ('60', 'eova_button_code', '', 'indexNum', '序号', '0', 'number', '文本框', '9', '', '0', '1', '1', '1', '1', '1', '', '80', '20', '0');
INSERT INTO `eova_item` VALUES ('61', 'eova_role_code', '', 'id', 'ID', '1', 'number', '自增框', '0', '', '0', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('62', 'eova_role_code', '', 'name', '角色名', '0', 'string', '文本框', '0', '', '1', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('63', 'eova_role_code', '', 'info', '角色描述', '0', 'string', '文本框', '0', '', '0', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('64', 'eova_user_code', '', 'rid', '角色', '0', 'string', '下拉框', '0', 'select id ID,name CN from eova_role where 1=1;ds=eova', '0', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('70', 'eova_log_code', '', 'id', 'id', '1', 'number', '自增框', '1', '', '0', '1', '1', '1', '1', '0', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('71', 'eova_log_code', '', 'uid', '操作用户', '0', 'number', '查找框', '2', 'select id UID,nickName 用户名 from eova_user where 1=1;ds=eova', '1', '1', '1', '1', '1', '0', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('72', 'eova_log_code', '', 'type', '日志类型', '0', 'number', '文本框', '3', 'select value ID,name CN from `eova_dict` where `class` = \'eova_log\' and field = \'type\';ds=eova', '1', '1', '1', '1', '1', '0', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('73', 'eova_log_code', '', 'ip', '操作IP', '0', 'string', '文本框', '4', '', '1', '1', '1', '1', '1', '0', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('74', 'eova_log_code', '', 'info', '操作详情', '0', 'string', '文本框', '5', '', '0', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('75', 'player_code', '', 'id', 'ID', '1', 'number', '自增框', '1', '', '0', '1', '1', '1', '1', '0', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('76', 'player_code', '', 'status', '状态', '0', 'number', '文本框', '2', '', '1', '1', '1', '1', '1', '1', '0', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('77', 'player_code', '', 'loginId', '登录账户', '0', 'string', '文本框', '3', '', '1', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('78', 'player_code', '', 'loginPwd', '录登密码', '0', 'string', '文本框', '4', '', '0', '0', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('79', 'player_code', '', 'nickName', '称号', '0', 'string', '文本框', '5', '', '1', '1', '1', '1', '1', '1', '', '130', '20', '0');
INSERT INTO `eova_item` VALUES ('80', 'player_code', '', 'regTime', '注册时间', '0', 'time', '时间框', '6', '', '0', '1', '1', '1', '1', '1', 'CURRENT_TIMESTAMP', '130', '20', '0');

-- ----------------------------
-- Table structure for `eova_log`
-- ----------------------------
DROP TABLE IF EXISTS `eova_log`;
CREATE TABLE `eova_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '操作用户',
  `type` int(11) NOT NULL COMMENT '日志类型',
  `ip` varchar(255) NOT NULL COMMENT '操作IP',
  `info` varchar(500) DEFAULT '' COMMENT '操作详情',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_log
-- ----------------------------
INSERT INTO `eova_log` VALUES ('1', '1', '2', '127.0.0.1', 'eova_role_code[18]');
INSERT INTO `eova_log` VALUES ('2', '1', '2', '127.0.0.1', 'eova_role_code[18]');
INSERT INTO `eova_log` VALUES ('3', '1', '1', '127.0.0.1', 'eova_role_code');
INSERT INTO `eova_log` VALUES ('4', '1', '3', '127.0.0.1', 'eova_role_code[20]');
INSERT INTO `eova_log` VALUES ('5', '1', '2', '127.0.0.1', 'eova_item_code[7]');
INSERT INTO `eova_log` VALUES ('6', '1', '2', '127.0.0.1', 'eova_item_code[7]');
INSERT INTO `eova_log` VALUES ('7', '1', '3', '127.0.0.1', 'eova_role_code[19]');
INSERT INTO `eova_log` VALUES ('8', '1', '2', '127.0.0.1', 'eova_user_code[4]');
INSERT INTO `eova_log` VALUES ('9', '1', '2', '127.0.0.1', 'eova_item_code[266]');
INSERT INTO `eova_log` VALUES ('10', '1', '1', '127.0.0.1', 'eova_menu_code');
INSERT INTO `eova_log` VALUES ('11', '1', '2', '127.0.0.1', 'eova_menu_code[14]');
INSERT INTO `eova_log` VALUES ('12', '1', '3', '127.0.0.1', 'eova_button_code[36]');
INSERT INTO `eova_log` VALUES ('13', '1', '2', '127.0.0.1', 'eova_item_code[64]');
INSERT INTO `eova_log` VALUES ('14', '1', '2', '127.0.0.1', 'eova_item_code[4]');
INSERT INTO `eova_log` VALUES ('15', '1', '2', '127.0.0.1', 'eova_item_code[4]');
INSERT INTO `eova_log` VALUES ('16', '1', '2', '127.0.0.1', 'eova_object_code[9]');
INSERT INTO `eova_log` VALUES ('17', '1', '3', '127.0.0.1', 'eova_object_code[10]');
INSERT INTO `eova_log` VALUES ('18', '1', '3', '127.0.0.1', 'eova_object_code[11]');
INSERT INTO `eova_log` VALUES ('19', '1', '3', '127.0.0.1', 'eova_object_code[12]');
INSERT INTO `eova_log` VALUES ('20', '1', '2', '127.0.0.1', 'eova_object_code[9]');
INSERT INTO `eova_log` VALUES ('21', '1', '2', '127.0.0.1', 'eova_role_code[8]');
INSERT INTO `eova_log` VALUES ('22', '1', '2', '127.0.0.1', 'eova_role_code[8]');
INSERT INTO `eova_log` VALUES ('23', '1', '1', '127.0.0.1', 'eova_user_code');
INSERT INTO `eova_log` VALUES ('24', '1', '2', '127.0.0.1', 'eova_user_code[4]');
INSERT INTO `eova_log` VALUES ('25', '1', '2', '127.0.0.1', 'player_code[29]');
INSERT INTO `eova_log` VALUES ('26', '1', '2', '127.0.0.1', 'player_code[30]');
INSERT INTO `eova_log` VALUES ('27', '1', '2', '127.0.0.1', 'player_code[30]');
INSERT INTO `eova_log` VALUES ('28', '1', '2', '127.0.0.1', 'player_code[29]');
INSERT INTO `eova_log` VALUES ('29', '1', '2', '127.0.0.1', 'player_code[30]');
INSERT INTO `eova_log` VALUES ('30', '1', '2', '127.0.0.1', 'player_code[30]');
INSERT INTO `eova_log` VALUES ('31', '1', '2', '127.0.0.1', 'player_code[30]');
INSERT INTO `eova_log` VALUES ('32', '1', '1', '127.0.0.1', 'player_code');
INSERT INTO `eova_log` VALUES ('33', '1', '3', '127.0.0.1', 'eova_object_code[10]');
INSERT INTO `eova_log` VALUES ('34', '1', '2', '127.0.0.1', 'eova_object_code[2]');
INSERT INTO `eova_log` VALUES ('35', '1', '3', '127.0.0.1', 'eova_item_code[106,105,104,103,102,101,100,99,98,97,96]');
INSERT INTO `eova_log` VALUES ('36', '1', '3', '127.0.0.1', 'eova_item_code[119,118,117,116,115,114,113,112,111,110,109,108,107]');
INSERT INTO `eova_log` VALUES ('37', '1', '3', '127.0.0.1', 'eova_item_code[132,131,130,129,128,127,126,125,124,123,122,121,120]');
INSERT INTO `eova_log` VALUES ('38', '1', '3', '127.0.0.1', 'eova_item_code[95,94,93,92,91,90,89,88,87,86,85,84,83,81,82]');
INSERT INTO `eova_log` VALUES ('39', '1', '3', '127.0.0.1', 'eova_object_code[14]');
INSERT INTO `eova_log` VALUES ('40', '1', '3', '127.0.0.1', 'eova_object_code[15]');
INSERT INTO `eova_log` VALUES ('41', '1', '3', '127.0.0.1', 'eova_item_code[9]');
INSERT INTO `eova_log` VALUES ('42', '1', '3', '127.0.0.1', 'eova_menu_code[22]');
INSERT INTO `eova_log` VALUES ('43', '1', '3', '127.0.0.1', 'eova_menu_code[23]');
INSERT INTO `eova_log` VALUES ('44', '1', '3', '127.0.0.1', 'eova_menu_code[24]');
INSERT INTO `eova_log` VALUES ('45', '1', '3', '127.0.0.1', 'eova_menu_code[25]');
INSERT INTO `eova_log` VALUES ('46', '1', '3', '127.0.0.1', 'eova_menu_code[26]');
INSERT INTO `eova_log` VALUES ('47', '1', '3', '127.0.0.1', 'eova_button_code[49]');
INSERT INTO `eova_log` VALUES ('48', '1', '3', '127.0.0.1', 'eova_button_code[48]');
INSERT INTO `eova_log` VALUES ('49', '1', '2', '127.0.0.1', 'eova_item_code[59]');
INSERT INTO `eova_log` VALUES ('50', '1', '3', '127.0.0.1', 'eova_object_code[16]');

-- ----------------------------
-- Table structure for `eova_menu`
-- ----------------------------
DROP TABLE IF EXISTS `eova_menu`;
CREATE TABLE `eova_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL COMMENT '编码',
  `name` varchar(30) NOT NULL COMMENT '名称',
  `type` varchar(20) NOT NULL DEFAULT '1' COMMENT '菜单类型',
  `icon` varchar(255) DEFAULT '' COMMENT '图标路径',
  `indexNum` int(11) DEFAULT '0' COMMENT '序号',
  `parentId` int(11) DEFAULT '0' COMMENT '父节点',
  `isCollapse` tinyint(1) DEFAULT '0' COMMENT '是否折叠',
  `bizIntercept` varchar(255) DEFAULT '' COMMENT '自定义业务拦截器',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_menu
-- ----------------------------
INSERT INTO `eova_menu` VALUES ('1', 'eova', '平台维护', 'singleGrid', 'ext-icon-bricks', '3', '0', '0', '');
INSERT INTO `eova_menu` VALUES ('2', 'sys', '系统管理', 'singleGrid', 'ext-icon-cog', '2', '0', '0', '');
INSERT INTO `eova_menu` VALUES ('3', 'biz', '综合业务', 'singleGrid', 'ext-icon-plugin', '1', '0', '0', '');
INSERT INTO `eova_menu` VALUES ('4', 'eova_menu', '菜单管理', 'singleGrid', 'ext-icon-application_side_tree', '1', '1', '0', 'com.eova.core.menu.MenuIntercept');
INSERT INTO `eova_menu` VALUES ('5', 'eova_button', '按钮管理', 'singleGrid', 'ext-icon-layout', '2', '1', '0', '');
INSERT INTO `eova_menu` VALUES ('6', 'eova_object', '对象管理', 'singleGrid', 'ext-icon-database_table', '3', '1', '0', 'com.eova.core.object.ObjectIntercept');
INSERT INTO `eova_menu` VALUES ('7', 'eova_item', '字段管理', 'singleGrid', 'ext-icon-application_view_columns', '4', '1', '0', '');
INSERT INTO `eova_menu` VALUES ('8', 'eova_dictionary', '字典管理', 'singleGrid', 'ext-icon-book_open', '5', '1', '0', '');
INSERT INTO `eova_menu` VALUES ('9', 'eova_icon', '图标管理', 'diy', 'ext-icon-application_view_icons', '6', '1', '0', '');
INSERT INTO `eova_menu` VALUES ('10', 'sys_auth_user', '用户管理', 'singleGrid', 'ext-icon-group', '1', '2', '0', '');
INSERT INTO `eova_menu` VALUES ('11', 'sys_auth_role', '角色管理', 'singleGrid', 'ext-icon-group_key', '2', '2', '0', '');
INSERT INTO `eova_menu` VALUES ('12', 'sys_log', '系统日志', 'singleGrid', 'ext-icon-table_multiple', '3', '2', '0', '');
INSERT INTO `eova_menu` VALUES ('19', 'biz_player', '玩家管理', 'singleGrid', 'ext-icon-group', '0', '3', '0', '');

-- ----------------------------
-- Table structure for `eova_menu_object`
-- ----------------------------
DROP TABLE IF EXISTS `eova_menu_object`;
CREATE TABLE `eova_menu_object` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menuCode` varchar(50) NOT NULL COMMENT '菜单编码',
  `objectCode` varchar(50) NOT NULL COMMENT '对象编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_menu_object
-- ----------------------------
INSERT INTO `eova_menu_object` VALUES ('1', 'eova_menu', 'eova_menu_code');
INSERT INTO `eova_menu_object` VALUES ('2', 'eova_button', 'eova_button_code');
INSERT INTO `eova_menu_object` VALUES ('3', 'eova_object', 'eova_object_code');
INSERT INTO `eova_menu_object` VALUES ('4', 'eova_item', 'eova_item_code');
INSERT INTO `eova_menu_object` VALUES ('5', 'eova_dictionary', 'eova_dict_code');
INSERT INTO `eova_menu_object` VALUES ('6', 'sys_auth_user', 'eova_user_code');
INSERT INTO `eova_menu_object` VALUES ('7', 'sys_auth_role', 'eova_role_code');
INSERT INTO `eova_menu_object` VALUES ('8', 'sys_log', 'eova_log_code');
INSERT INTO `eova_menu_object` VALUES ('9', 'biz_player', 'player_code');
INSERT INTO `eova_menu_object` VALUES ('10', 'eova_object', 'eova_object_code');

-- ----------------------------
-- Table structure for `eova_object`
-- ----------------------------
DROP TABLE IF EXISTS `eova_object`;
CREATE TABLE `eova_object` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL COMMENT '对象编码',
  `name` varchar(50) NOT NULL COMMENT '对象名称',
  `view` varchar(255) DEFAULT '' COMMENT '查询数据视图',
  `table` varchar(50) NOT NULL COMMENT '保存数据主表',
  `pkName` varchar(50) NOT NULL COMMENT '主键',
  `dataSource` varchar(50) DEFAULT 'main' COMMENT '数据源',
  `isSingleSelect` tinyint(1) DEFAULT '1' COMMENT '是否单选',
  `isShowNum` tinyint(1) DEFAULT '1' COMMENT '是否显示行号',
  `isDefaultPkDesc` tinyint(1) DEFAULT '1' COMMENT '是否默认根据主键逆序',
  `filterWhere` varchar(500) DEFAULT '' COMMENT '初始数据过滤条件',
  `diyCard` varchar(255) DEFAULT '' COMMENT '自定义卡片面板',
  `diyList` varchar(255) DEFAULT '' COMMENT '自定义列表面板',
  `diyIntercept` varchar(255) DEFAULT '' COMMENT '自定义业务拦截器',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_object
-- ----------------------------
INSERT INTO `eova_object` VALUES ('1', 'eova_menu_code', '菜单', '', 'eova_menu', 'id', 'eova', '1', '1', '1', '', '', '', '');
INSERT INTO `eova_object` VALUES ('2', 'eova_object_code', '对象模型', '', 'eova_object', 'id', 'eova', '1', '1', '1', '', '', '', '');
INSERT INTO `eova_object` VALUES ('3', 'eova_user_code', '用户', '', 'eova_user', 'id', 'eova', '1', '1', '1', '', '', '', '');
INSERT INTO `eova_object` VALUES ('4', 'eova_item_code', '字段管理', '', 'eova_item', 'id', 'eova', '0', '1', '1', '', '', '', '');
INSERT INTO `eova_object` VALUES ('5', 'eova_button_code', '按钮管理', '', 'eova_button', 'id', 'eova', '1', '1', '1', '', '', '', '');
INSERT INTO `eova_object` VALUES ('6', 'eova_dict_code', '字典管理', '', 'eova_dict', 'id', 'eova', '1', '1', '1', '', '', '', '');
INSERT INTO `eova_object` VALUES ('7', 'eova_role_code', '角色管理', '', 'eova_role', 'id', 'eova', '1', '1', '1', '', '', '', '');
INSERT INTO `eova_object` VALUES ('8', 'eova_log_code', '操作日志', '', 'eova_log', 'id', 'eova', '1', '1', '1', '', '', '', '');
INSERT INTO `eova_object` VALUES ('9', 'player_code', '玩家信息', '', 'users', 'id', 'main', '1', '1', '1', '', '', '', '');

-- ----------------------------
-- Table structure for `eova_role`
-- ----------------------------
DROP TABLE IF EXISTS `eova_role`;
CREATE TABLE `eova_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '角色名',
  `info` varchar(255) DEFAULT '' COMMENT '角色描述',
  `fun` varchar(5000) DEFAULT '' COMMENT '已授权功能',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_role
-- ----------------------------
INSERT INTO `eova_role` VALUES ('1', '超级管理员', '系统最高级权限', '');
INSERT INTO `eova_role` VALUES ('2', '运营总监', '运营监控', '');
INSERT INTO `eova_role` VALUES ('3', '编辑', '网站数据编辑', '');
INSERT INTO `eova_role` VALUES ('4', '数据分析', '报表查看', '');
INSERT INTO `eova_role` VALUES ('5', '客服', '解答用户反馈', '');
INSERT INTO `eova_role` VALUES ('6', '测试', '常用功能测试', '');
INSERT INTO `eova_role` VALUES ('7', '运营专员', '游戏运营专员', '');
INSERT INTO `eova_role` VALUES ('8', '商务', '商务日常操作', '');

-- ----------------------------
-- Table structure for `eova_role_btn`
-- ----------------------------
DROP TABLE IF EXISTS `eova_role_btn`;
CREATE TABLE `eova_role_btn` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rid` int(11) NOT NULL COMMENT '角色',
  `bid` int(11) NOT NULL COMMENT '功能',
  `isBtn` tinyint(1) DEFAULT '0' COMMENT '是否按钮',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=242 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_role_btn
-- ----------------------------
INSERT INTO `eova_role_btn` VALUES ('206', '1', '44', '0');
INSERT INTO `eova_role_btn` VALUES ('207', '1', '45', '0');
INSERT INTO `eova_role_btn` VALUES ('208', '1', '46', '0');
INSERT INTO `eova_role_btn` VALUES ('209', '1', '47', '0');
INSERT INTO `eova_role_btn` VALUES ('210', '1', '7', '0');
INSERT INTO `eova_role_btn` VALUES ('211', '1', '29', '0');
INSERT INTO `eova_role_btn` VALUES ('212', '1', '30', '0');
INSERT INTO `eova_role_btn` VALUES ('213', '1', '31', '0');
INSERT INTO `eova_role_btn` VALUES ('214', '1', '8', '0');
INSERT INTO `eova_role_btn` VALUES ('215', '1', '26', '0');
INSERT INTO `eova_role_btn` VALUES ('216', '1', '27', '0');
INSERT INTO `eova_role_btn` VALUES ('217', '1', '28', '0');
INSERT INTO `eova_role_btn` VALUES ('218', '1', '32', '0');
INSERT INTO `eova_role_btn` VALUES ('219', '1', '9', '0');
INSERT INTO `eova_role_btn` VALUES ('220', '1', '1', '0');
INSERT INTO `eova_role_btn` VALUES ('221', '1', '10', '0');
INSERT INTO `eova_role_btn` VALUES ('222', '1', '11', '0');
INSERT INTO `eova_role_btn` VALUES ('223', '1', '12', '0');
INSERT INTO `eova_role_btn` VALUES ('224', '1', '23', '0');
INSERT INTO `eova_role_btn` VALUES ('225', '1', '2', '0');
INSERT INTO `eova_role_btn` VALUES ('226', '1', '16', '0');
INSERT INTO `eova_role_btn` VALUES ('227', '1', '17', '0');
INSERT INTO `eova_role_btn` VALUES ('228', '1', '18', '0');
INSERT INTO `eova_role_btn` VALUES ('229', '1', '3', '0');
INSERT INTO `eova_role_btn` VALUES ('230', '1', '19', '0');
INSERT INTO `eova_role_btn` VALUES ('231', '1', '20', '0');
INSERT INTO `eova_role_btn` VALUES ('232', '1', '21', '0');
INSERT INTO `eova_role_btn` VALUES ('233', '1', '22', '0');
INSERT INTO `eova_role_btn` VALUES ('234', '1', '4', '0');
INSERT INTO `eova_role_btn` VALUES ('235', '1', '13', '0');
INSERT INTO `eova_role_btn` VALUES ('236', '1', '14', '0');
INSERT INTO `eova_role_btn` VALUES ('237', '1', '15', '0');
INSERT INTO `eova_role_btn` VALUES ('238', '1', '5', '0');
INSERT INTO `eova_role_btn` VALUES ('239', '1', '24', '0');
INSERT INTO `eova_role_btn` VALUES ('240', '1', '25', '0');
INSERT INTO `eova_role_btn` VALUES ('241', '1', '6', '0');

-- ----------------------------
-- Table structure for `eova_user`
-- ----------------------------
DROP TABLE IF EXISTS `eova_user`;
CREATE TABLE `eova_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loginId` varchar(30) NOT NULL COMMENT '帐号',
  `loginPwd` varchar(50) NOT NULL COMMENT '密码',
  `nickName` varchar(255) NOT NULL COMMENT '中文名',
  `rid` int(11) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_user
-- ----------------------------
INSERT INTO `eova_user` VALUES ('1', 'admin', '000000', 'Jieven', '1');
INSERT INTO `eova_user` VALUES ('3', 'test', '000000', '测试', '2');
INSERT INTO `eova_user` VALUES ('4', '12321', '1232134', '举套问天我操谁', '4');

-- ----------------------------
-- Function structure for `queryChild`
-- ----------------------------
DROP FUNCTION IF EXISTS `queryChild`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `queryChild`(rootId INT) RETURNS varchar(4000) CHARSET utf8
BEGIN
DECLARE sTemp VARCHAR(4000);
DECLARE sTempChd VARCHAR(4000);
SET sTemp = '$';
SET sTempChd = cast(rootId as char);
WHILE sTempChd is not NULL DO
SET sTemp = CONCAT(sTemp,',',sTempChd);
SELECT group_concat(id) INTO sTempChd FROM eova_menu where FIND_IN_SET(parentId, sTempChd)>0;
END WHILE;
return sTemp;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `queryParent`
-- ----------------------------
DROP FUNCTION IF EXISTS `queryParent`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `queryParent`(rootId INT) RETURNS varchar(4000) CHARSET utf8
BEGIN
DECLARE sTemp VARCHAR(4000);
DECLARE sTempChd VARCHAR(4000);
SET sTemp = '$';
SET sTempChd = cast(rootId as char);
WHILE sTempChd is not NULL DO
SET sTemp = CONCAT(sTemp,',',sTempChd);
SELECT group_concat(parentId) INTO sTempChd FROM eova_menu where FIND_IN_SET(id, sTempChd)>0;
END WHILE;
return sTemp;
END
;;
DELIMITER ;
