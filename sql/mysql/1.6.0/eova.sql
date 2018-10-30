/*
Navicat MySQL Data Transfer

Source Server         : YUN-DB
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : eova

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2018-06-26 13:58:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for eova_button
-- ----------------------------
DROP TABLE IF EXISTS `eova_button`;
CREATE TABLE `eova_button` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_code` varchar(255) NOT NULL COMMENT '菜单Code',
  `name` varchar(255) NOT NULL COMMENT '按钮名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `ui` varchar(255) DEFAULT NULL COMMENT '按钮UI路径',
  `bs` varchar(500) DEFAULT NULL COMMENT '按钮BS路径',
  `uri` varchar(100) DEFAULT NULL COMMENT '服务端URI',
  `order_num` int(11) DEFAULT '0' COMMENT '排序号',
  `group_num` int(11) DEFAULT '0' COMMENT '分组号',
  `is_base` tinyint(1) DEFAULT '0' COMMENT '是否基础功能',
  `is_hide` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1191 DEFAULT CHARSET=utf8 COMMENT='EOVA操作按钮';

-- ----------------------------
-- Records of eova_button
-- ----------------------------
INSERT INTO `eova_button` VALUES ('1', 'eova_menu', '查询', null, 'query', null, null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('2', 'eova_menu', '新增', null, '/eova/template/singletree/btn/add.html', null, null, '1', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('3', 'eova_menu', '修改', null, '/eova/template/singletree/btn/update.html', null, null, '2', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('4', 'eova_menu', '删除', null, '/eova/template/singletree/btn/delete.html', null, null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('5', 'eova_menu', '查看', null, '/eova/template/singletree/btn/detail.html', null, null, '4', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('6', 'eova_menu', '隐藏', null, '/eova/template/singletree/btn/hide.html', null, null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('7', 'eova_menu', '导出菜单脚本', 'eova-icon387', '/eova/menu/btn/export.html', null, null, '12', '0', '0', '0');
INSERT INTO `eova_button` VALUES ('8', 'eova_menu', '基本功能设置', 'eova-icon297', '/eova/menu/btn/fun.html', null, null, '11', '0', '0', '0');
INSERT INTO `eova_button` VALUES ('9', 'eova_menu', '新增功能', 'eova-icon724', '/eova/menu/btn/add.html', null, null, '0', '0', '0', '0');
INSERT INTO `eova_button` VALUES ('20', 'eova_object', '查询', null, 'query', null, null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('21', 'eova_object', '新增', null, '/eova/template/masterslave/btn1/add.html', null, null, '1', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('22', 'eova_object', '修改', null, '/eova/template/masterslave/btn1/update.html', null, null, '2', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('23', 'eova_object', '删除', null, '/eova/template/masterslave/btn1/delete.html', null, null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('24', 'eova_object', '查看', null, '/eova/template/masterslave/btn1/detail.html', null, null, '4', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('25', 'eova_object', '字段新增', null, '/eova/template/masterslave/btn2/add.html', null, null, '0', '1', '1', '0');
INSERT INTO `eova_button` VALUES ('26', 'eova_object', '字段修改', null, '/eova/template/masterslave/btn2/update.html', null, null, '1', '1', '1', '0');
INSERT INTO `eova_button` VALUES ('27', 'eova_object', '字段删除', null, '/eova/template/masterslave/btn2/delete.html', null, null, '2', '1', '1', '0');
INSERT INTO `eova_button` VALUES ('28', 'eova_object', '导出元数据脚本', 'eova-icon387', '/eova/meta/btn/export.html', null, null, '11', '0', '0', '0');
INSERT INTO `eova_button` VALUES ('29', 'eova_object', '导入元数据', 'eova-icon387', '/eova/meta/btn/import.html', null, null, '10', '0', '0', '0');
INSERT INTO `eova_button` VALUES ('30', 'eova_object', '覆盖同步', 'eova-icon391', '/eova/meta/btn/override.html', null, null, '12', '0', '0', '0');
INSERT INTO `eova_button` VALUES ('31', 'eova_object', '增量同步', 'eova-icon391', '/eova/meta/btn/syncnew.html', null, null, '13', '0', '0', '0');
INSERT INTO `eova_button` VALUES ('32', 'eova_object', '复制元数据', 'eova-icon382', '/eova/meta/btn/copy.html', null, null, '14', '0', '0', '0');
INSERT INTO `eova_button` VALUES ('33', 'eova_object', '添加虚拟字段', 'eova-icon380', '/eova/template/common/btn/input.html', '/meta/addVirtualField', '/meta/addVirtualField', '15', '0', '0', '0');
INSERT INTO `eova_button` VALUES ('40', 'eova_button', '查询', null, 'query', null, null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('41', 'eova_button', '新增', null, '/eova/template/treetogrid/btn/add.html', null, null, '1', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('42', 'eova_button', '修改', null, '/eova/template/treetogrid/btn/update.html', null, null, '2', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('43', 'eova_button', '删除', null, '/eova/template/treetogrid/btn/delete.html', null, null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('44', 'eova_button', '查看', null, '/eova/template/treetogrid/btn/detail.html', null, null, '4', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('45', 'eova_button', '导出按钮脚本', 'eova-icon387', '/eova/button/btn/export.html', null, null, '7', '0', '0', '0');
INSERT INTO `eova_button` VALUES ('80', 'eova_task', '查询', null, 'query', null, null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('81', 'eova_task', '新增', null, '/eova/template/single/btn/add.html', null, null, '1', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('82', 'eova_task', '修改', null, '/eova/template/single/btn/update.html', null, null, '2', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('83', 'eova_task', '删除', null, '/eova/template/single/btn/delete.html', null, null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('84', 'eova_task', '查看', null, '/eova/template/single/btn/detail.html', null, null, '4', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('85', 'eova_task', '导入', null, '/eova/template/single/btn/import.html', null, null, '5', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('86', 'eova_task', '启动任务', 'eova-icon288', '/eova/task/btn/start.html', null, null, '10', '0', '0', '0');
INSERT INTO `eova_button` VALUES ('87', 'eova_task', '暂停任务', 'eova-icon287', '/eova/task/btn/stop.html', null, null, '11', '0', '0', '0');
INSERT INTO `eova_button` VALUES ('88', 'eova_task', '立即运行一次', 'eova-icon290', '/eova/task/btn/trigger.html', null, null, '12', '0', '0', '0');
INSERT INTO `eova_button` VALUES ('90', 'eova_code', '查询', null, 'query', null, null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('100', 'sys_log', '查询', null, 'query', '/single_grid/list/sys_log;/grid/query/eova_log_code*', null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('101', 'sys_log', '新增', null, '/eova/template/single/btn/add.html', '/form/add/eova_log_code*;/form/doAdd/eova_log_code', null, '1', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('102', 'sys_log', '修改', null, '/eova/template/single/btn/update.html', '/form/update/eova_log_code*;/form/doUpdate/eova_log_code', null, '2', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('103', 'sys_log', '删除', null, '/eova/template/single/btn/delete.html', '/grid/delete/eova_log_code', null, '3', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('104', 'sys_log', '查看', null, '/eova/template/single/btn/detail.html', '/form/detail/eova_log_code*', null, '4', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('105', 'sys_log', '导入', null, '/eova/template/single/btn/import.html', '/single_grid/importXls/sys_log;/single_grid/doImportXls/sys_log', null, '5', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('110', 'sys_auth_role', '查询', null, 'query', '/single_grid/list/sys_auth_role;/grid/query/eova_role_code*', null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('111', 'sys_auth_role', '新增', null, '/eova/template/single/btn/add.html', '/form/add/eova_role_code*;/form/doAdd/eova_role_code', null, '1', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('112', 'sys_auth_role', '修改', null, '/eova/template/single/btn/update.html', '/form/update/eova_role_code*;/form/doUpdate/eova_role_code', null, '2', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('113', 'sys_auth_role', '删除', null, '/eova/template/single/btn/delete.html', '/grid/delete/eova_role_code', null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('114', 'sys_auth_role', '查看', null, '/eova/template/single/btn/detail.html', '/form/detail/eova_role_code*', null, '4', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('115', 'sys_auth_role', '导入', null, '/eova/template/single/btn/import.html', '/single_grid/importXls/sys_auth_role;/single_grid/doImportXls/sys_auth_role', null, '5', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('116', 'sys_auth_role', '权限分配', 'eova-icon523', '/eova/auth/btn/roleChoose.html', '/auth/toRoleChoose/*;/auth/getFunJson;/auth/getRoleFunJson/*;/auth/roleChoose/*', null, '10', '0', '0', '0');
INSERT INTO `eova_button` VALUES ('129', 'sys_users', '修改密码', 'eova-icon572', '/eova/template/common/btn/input.html', '/user/pwd', '/user/pwd', '7', '0', '0', '0');
INSERT INTO `eova_button` VALUES ('1000', 'biz_product', '产品发布', 'eova-icon57', '/product/btn/release.html', '/product/release', null, '7', '0', '0', '0');
INSERT INTO `eova_button` VALUES ('1001', 'biz_demo_users', '查询', null, 'query', '/single_grid/list/biz_demo_users;/grid/query/player_code*;/grid/export/player_code*', null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1002', 'biz_demo_users', '新增', null, '/eova/template/single/btn/add.html', '/form/add/player_code*;/form/doAdd/player_code', null, '1', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1003', 'biz_demo_users', '修改', null, '/eova/template/single/btn/update.html', '/form/update/player_code*;/form/doUpdate/player_code', null, '2', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1004', 'biz_demo_users', '删除', null, '/eova/template/single/btn/delete.html', '/grid/delete/player_code', null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1005', 'biz_demo_users', '查看', null, '/eova/template/single/btn/detail.html', '/form/detail/player_code*', null, '4', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1006', 'biz_demo_users', '导入', null, '/eova/template/single/btn/import.html', '/single_grid/importXls/biz_demo_users;/single_grid/doImportXls/biz_demo_users', null, '5', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1007', 'biz_demo_users', '隐藏', null, '/eova/template/single/btn/hide.html', '/grid/hide/player_code', null, '6', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('1008', 'biz_demo_userscell', '查询', null, 'query', '/single_grid/list/biz_demo_userscell;/grid/query/celledit_users_code*;/grid/export/celledit_users_code*', null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1009', 'biz_demo_userscell', '新增', null, '/eova/template/single/btn/add.html', '/form/add/celledit_users_code*;/form/doAdd/celledit_users_code', null, '1', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1010', 'biz_demo_userscell', '修改', null, '/eova/template/single/btn/update.html', '/form/update/celledit_users_code*;/form/doUpdate/celledit_users_code', null, '2', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1011', 'biz_demo_userscell', '删除', null, '/eova/template/single/btn/delete.html', '/grid/delete/celledit_users_code', null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1012', 'biz_demo_userscell', '查看', null, '/eova/template/single/btn/detail.html', '/form/detail/celledit_users_code*', null, '4', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1013', 'biz_demo_userscell', '导入', null, '/eova/template/single/btn/import.html', '/single_grid/importXls/biz_demo_userscell;/single_grid/doImportXls/biz_demo_userscell', null, '5', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1014', 'biz_demo_userscell', '隐藏', null, '/eova/template/single/btn/hide.html', '/grid/hide/celledit_users_code', null, '6', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('1015', 'biz_demo_import', '查询', null, 'query', '/single_grid/list/biz_demo_import;/grid/query/player_code*;/grid/export/player_code*', null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1016', 'biz_demo_import', '新增', null, '/eova/template/single/btn/add.html', '/form/add/player_code*;/form/doAdd/player_code', null, '1', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1017', 'biz_demo_import', '修改', null, '/eova/template/single/btn/update.html', '/form/update/player_code*;/form/doUpdate/player_code', null, '2', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1018', 'biz_demo_import', '删除', null, '/eova/template/single/btn/delete.html', '/grid/delete/player_code', null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1019', 'biz_demo_import', '查看', null, '/eova/template/single/btn/detail.html', '/form/detail/player_code*', null, '4', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1020', 'biz_demo_import', '导入', null, '/eova/template/single/btn/import.html', '/single_grid/importXls/biz_demo_import;/single_grid/doImportXls/biz_demo_import', null, '5', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1021', 'biz_demo_import', '隐藏', null, '/eova/template/single/btn/hide.html', '/grid/hide/player_code', null, '6', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('1022', 'biz_users_exp', '查询', null, 'query', '/single_grid/list/biz_users_exp;/grid/query/users_exp_code*;/grid/export/users_exp_code*', null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1023', 'biz_users_exp', '新增', null, '/eova/template/single/btn/add.html', '/form/add/users_exp_code*;/form/doAdd/users_exp_code', null, '1', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1024', 'biz_users_exp', '修改', null, '/eova/template/single/btn/update.html', '/form/update/users_exp_code*;/form/doUpdate/users_exp_code', null, '2', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1025', 'biz_users_exp', '删除', null, '/eova/template/single/btn/delete.html', '/grid/delete/users_exp_code', null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1026', 'biz_users_exp', '查看', null, '/eova/template/single/btn/detail.html', '/form/detail/users_exp_code*', null, '4', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1027', 'biz_users_exp', '导入', null, '/eova/template/single/btn/import.html', '/single_grid/importXls/biz_users_exp;/single_grid/doImportXls/biz_users_exp', null, '5', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1028', 'biz_users_exp', '隐藏', null, '/eova/template/single/btn/hide.html', '/grid/hide/users_exp_code', null, '6', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('1029', 'biz_hotelzz', '查询', null, 'query', '/master_slave_grid/list/biz_hotelzz;/grid/query/hotel*;/grid/export/hotel*;/grid/query/hotel_bed*;/grid/export/hotel_bed*;/grid/query/hotel_stock*;/grid/export/hotel_stock*', null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1030', 'biz_hotelzz', '新增', null, '/eova/template/masterslave/btn1/add.html', '/form/add/hotel*;/form/doAdd/hotel', null, '1', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1031', 'biz_hotelzz', '修改', null, '/eova/template/masterslave/btn1/update.html', '/form/update/hotel*;/form/doUpdate/hotel', null, '2', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1032', 'biz_hotelzz', '删除', null, '/eova/template/masterslave/btn1/delete.html', '/grid/delete/hotel', null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1033', 'biz_hotelzz', '查看', null, '/eova/template/masterslave/btn1/detail.html', '/form/detail/hotel*', null, '4', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1034', 'biz_hotelzz', '床位新增', null, '/eova/template/masterslave/btn2/add.html', '/form/add/hotel_bed*;/form/doAdd/hotel_bed', null, '0', '1', '1', '0');
INSERT INTO `eova_button` VALUES ('1035', 'biz_hotelzz', '床位修改', null, '/eova/template/masterslave/btn2/update.html', '/form/update/hotel_bed*;/form/doUpdate/hotel_bed', null, '1', '1', '1', '0');
INSERT INTO `eova_button` VALUES ('1036', 'biz_hotelzz', '床位删除', null, '/eova/template/masterslave/btn2/delete.html', '/grid/delete/hotel_bed', null, '2', '1', '1', '0');
INSERT INTO `eova_button` VALUES ('1037', 'biz_hotelzz', '床位查看', null, '/eova/template/masterslave/btn2/detail.html', '/form/detail/hotel_bed*', null, '3', '1', '1', '0');
INSERT INTO `eova_button` VALUES ('1038', 'biz_hotelzz', '存货新增', null, '/eova/template/masterslave/btn2/add.html', '/form/add/hotel_stock*;/form/doAdd/hotel_stock', null, '0', '2', '1', '0');
INSERT INTO `eova_button` VALUES ('1039', 'biz_hotelzz', '存货修改', null, '/eova/template/masterslave/btn2/update.html', '/form/update/hotel_stock*;/form/doUpdate/hotel_stock', null, '1', '2', '1', '0');
INSERT INTO `eova_button` VALUES ('1040', 'biz_hotelzz', '存货删除', null, '/eova/template/masterslave/btn2/delete.html', '/grid/delete/hotel_stock', null, '2', '2', '1', '0');
INSERT INTO `eova_button` VALUES ('1041', 'biz_hotelzz', '存货查看', null, '/eova/template/masterslave/btn2/detail.html', '/form/detail/hotel_stock*', null, '3', '2', '1', '0');
INSERT INTO `eova_button` VALUES ('1042', 'biz_4j_test1', '查询', null, 'query', '/single_grid/list/biz_4j_test1;/grid/query/player_code*;/grid/export/player_code*', null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1043', 'biz_4j_test1', '新增', null, '/eova/template/single/btn/add.html', '/form/add/player_code*;/form/doAdd/player_code', null, '1', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1044', 'biz_4j_test1', '修改', null, '/eova/template/single/btn/update.html', '/form/update/player_code*;/form/doUpdate/player_code', null, '2', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1045', 'biz_4j_test1', '删除', null, '/eova/template/single/btn/delete.html', '/grid/delete/player_code', null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1046', 'biz_4j_test1', '查看', null, '/eova/template/single/btn/detail.html', '/form/detail/player_code*', null, '4', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1047', 'biz_4j_test1', '导入', null, '/eova/template/single/btn/import.html', '/single_grid/importXls/biz_4j_test1;/single_grid/doImportXls/biz_4j_test1', null, '5', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1048', 'biz_4j_test1', '隐藏', null, '/eova/template/single/btn/hide.html', '/grid/hide/player_code', null, '6', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('1049', 'biz_data_login', '查询', null, 'query', '/single_chart/list/biz_data_login;/grid/query/data_login*;/grid/export/data_login*', null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1050', 'biz_data_login', '新增', null, '/eova/template/single/btn/add.html', '/form/add/data_login*;/form/doAdd/data_login', null, '1', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1051', 'biz_data_login', '修改', null, '/eova/template/single/btn/update.html', '/form/update/data_login*;/form/doUpdate/data_login', null, '2', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1052', 'biz_data_login', '删除', null, '/eova/template/single/btn/delete.html', '/grid/delete/data_login', null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1053', 'biz_data_login', '查看', null, '/eova/template/single/btn/detail.html', '/form/detail/data_login*', null, '4', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1054', 'biz_data_login', '导入', null, '/eova/template/single/btn/import.html', '/single_grid/importXls/biz_data_login;/single_grid/doImportXls/biz_data_login', null, '5', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1055', 'biz_data_login', '隐藏', null, '/eova/template/single/btn/hide.html', '/grid/hide/data_login', null, '6', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('1056', 'biz_demo_eova_all', '查询', null, 'query', '/single_grid/list/biz_demo_eova_all;/grid/query/test_info*;/grid/export/test_info*', null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1057', 'biz_demo_eova_all', '新增', null, '/eova/template/single/btn/add.html', '/form/add/test_info*;/form/doAdd/test_info', null, '1', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1058', 'biz_demo_eova_all', '修改', null, '/eova/template/single/btn/update.html', '/form/update/test_info*;/form/doUpdate/test_info', null, '2', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1059', 'biz_demo_eova_all', '删除', null, '/eova/template/single/btn/delete.html', '/grid/delete/test_info', null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1060', 'biz_demo_eova_all', '查看', null, '/eova/template/single/btn/detail.html', '/form/detail/test_info*', null, '4', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1061', 'biz_demo_eova_all', '导入', null, '/eova/template/single/btn/import.html', '/single_grid/importXls/biz_demo_eova_all;/single_grid/doImportXls/biz_demo_eova_all', null, '5', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1062', 'biz_demo_eova_all', '隐藏', null, '/eova/template/single/btn/hide.html', '/grid/hide/test_info', null, '6', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('1063', 'biz_demo_links', '查询', null, 'query', '/single_grid/list/biz_demo_links;/grid/query/links*;/grid/export/links*', null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1064', 'biz_demo_links', '新增', null, '/eova/template/single/btn/add.html', '/form/add/links*;/form/doAdd/links', null, '1', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1065', 'biz_demo_links', '修改', null, '/eova/template/single/btn/update.html', '/form/update/links*;/form/doUpdate/links', null, '2', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1066', 'biz_demo_links', '删除', null, '/eova/template/single/btn/delete.html', '/grid/delete/links', null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1067', 'biz_demo_links', '查看', null, '/eova/template/single/btn/detail.html', '/form/detail/links*', null, '4', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1068', 'biz_demo_links', '导入', null, '/eova/template/single/btn/import.html', '/single_grid/importXls/biz_demo_links;/single_grid/doImportXls/biz_demo_links', null, '5', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1069', 'biz_demo_links', '隐藏', null, '/eova/template/single/btn/hide.html', '/grid/hide/links', null, '6', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('1070', 'biz_demo_money', '查询', null, 'query', '/single_chart/list/biz_demo_money;/grid/query/data_money*;/grid/export/data_money*', null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1071', 'biz_demo_money', '新增', null, '/eova/template/single/btn/add.html', '/form/add/data_money*;/form/doAdd/data_money', null, '1', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1072', 'biz_demo_money', '修改', null, '/eova/template/single/btn/update.html', '/form/update/data_money*;/form/doUpdate/data_money', null, '2', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1073', 'biz_demo_money', '删除', null, '/eova/template/single/btn/delete.html', '/grid/delete/data_money', null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1074', 'biz_demo_money', '查看', null, '/eova/template/single/btn/detail.html', '/form/detail/data_money*', null, '4', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1075', 'biz_demo_money', '导入', null, '/eova/template/single/btn/import.html', '/single_grid/importXls/biz_demo_money;/single_grid/doImportXls/biz_demo_money', null, '5', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1076', 'biz_demo_money', '隐藏', null, '/eova/template/single/btn/hide.html', '/grid/hide/data_money', null, '6', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('1077', 'biz_demo_views_orders', '查询', null, 'query', '/single_grid/list/biz_demo_views_orders;/grid/query/v_orders*;/grid/export/v_orders*', null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1078', 'biz_demo_views_orders', '新增', null, '/eova/template/single/btn/add.html', '/form/add/v_orders*;/form/doAdd/v_orders', null, '1', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1079', 'biz_demo_views_orders', '修改', null, '/eova/template/single/btn/update.html', '/form/update/v_orders*;/form/doUpdate/v_orders', null, '2', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1080', 'biz_demo_views_orders', '删除', null, '/eova/template/single/btn/delete.html', '/grid/delete/v_orders', null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1081', 'biz_demo_views_orders', '查看', null, '/eova/template/single/btn/detail.html', '/form/detail/v_orders*', null, '4', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1082', 'biz_demo_views_orders', '导入', null, '/eova/template/single/btn/import.html', '/single_grid/importXls/biz_demo_views_orders;/single_grid/doImportXls/biz_demo_views_orders', null, '5', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1083', 'biz_demo_views_orders', '隐藏', null, '/eova/template/single/btn/hide.html', '/grid/hide/v_orders', null, '6', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('1084', 'biz_demo_views_users', '查询', null, 'query', '/single_grid/list/biz_demo_views_users;/grid/query/v_users*;/grid/export/v_users*', null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1085', 'biz_demo_views_users', '新增', null, '/eova/template/single/btn/add.html', '/form/add/v_users*;/form/doAdd/v_users', null, '1', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1086', 'biz_demo_views_users', '修改', null, '/eova/template/single/btn/update.html', '/form/update/v_users*;/form/doUpdate/v_users', null, '2', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1087', 'biz_demo_views_users', '删除', null, '/eova/template/single/btn/delete.html', '/grid/delete/v_users', null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1088', 'biz_demo_views_users', '查看', null, '/eova/template/single/btn/detail.html', '/form/detail/v_users*', null, '4', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1089', 'biz_demo_views_users', '导入', null, '/eova/template/single/btn/import.html', '/single_grid/importXls/biz_demo_views_users;/single_grid/doImportXls/biz_demo_views_users', null, '5', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1090', 'biz_demo_views_users', '隐藏', null, '/eova/template/single/btn/hide.html', '/grid/hide/v_users', null, '6', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('1091', 'biz_demo_area', '查询', null, 'query', '/single_tree/list/biz_demo_area;/grid/query/area*;/grid/export/area*;/treegrid/query/*', null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1092', 'biz_demo_area', '新增', null, '/eova/template/singletree/btn/add.html', '/form/add/area*;/form/doAdd/area', null, '1', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1093', 'biz_demo_area', '修改', null, '/eova/template/singletree/btn/update.html', '/form/update/area*;/form/doUpdate/area', null, '2', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1094', 'biz_demo_area', '删除', null, '/eova/template/singletree/btn/delete.html', '/grid/delete/area', null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1095', 'biz_demo_area', '查看', null, '/eova/template/singletree/btn/detail.html', '/form/detail/area*', null, '4', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1096', 'biz_demo_area', '隐藏', null, '/eova/template/singletree/btn/hide.html', '/grid/hide/area', null, '5', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('1097', 'biz_demo_tree_code', '查询', null, 'query', '/single_tree/list/biz_demo_tree_code;/grid/query/area_city*;/grid/export/area_city*;/treegrid/query/*', null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1098', 'biz_demo_tree_code', '新增', null, '/eova/template/singletree/btn/add.html', '/form/add/area_city*;/form/doAdd/area_city', null, '1', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1099', 'biz_demo_tree_code', '修改', null, '/eova/template/singletree/btn/update.html', '/form/update/area_city*;/form/doUpdate/area_city', null, '2', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1100', 'biz_demo_tree_code', '删除', null, '/eova/template/singletree/btn/delete.html', '/grid/delete/area_city', null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1101', 'biz_demo_tree_code', '查看', null, '/eova/template/singletree/btn/detail.html', '/form/detail/area_city*', null, '4', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1102', 'biz_demo_tree_code', '隐藏', null, '/eova/template/singletree/btn/hide.html', '/grid/hide/area_city', null, '5', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('1103', 'biz_demo_sale', '查询', null, 'query', '/tree_grid/list/biz_demo_sale;/grid/query/sale_data*;/grid/export/sale_data*;/tree/query/area_city*', null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1104', 'biz_demo_sale', '新增', null, '/eova/template/treetogrid/btn/add.html', '/form/add/sale_data*;/form/doAdd/sale_data', null, '1', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1105', 'biz_demo_sale', '修改', null, '/eova/template/treetogrid/btn/update.html', '/form/update/sale_data*;/form/doUpdate/sale_data', null, '2', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1106', 'biz_demo_sale', '删除', null, '/eova/template/treetogrid/btn/delete.html', '/grid/delete/sale_data', null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1107', 'biz_demo_sale', '查看', null, '/eova/template/treetogrid/btn/detail.html', '/form/detail/sale_data*', null, '4', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1108', 'biz_demo_hotel', '查询', null, 'query', '/single_grid/list/biz_demo_hotel;/grid/query/hotel*;/grid/export/hotel*', null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1109', 'biz_demo_hotel', '新增', null, '/eova/template/single/btn/add.html', '/form/add/hotel*;/form/doAdd/hotel', null, '1', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1110', 'biz_demo_hotel', '修改', null, '/eova/template/single/btn/update.html', '/form/update/hotel*;/form/doUpdate/hotel', null, '2', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1111', 'biz_demo_hotel', '删除', null, '/eova/template/single/btn/delete.html', '/grid/delete/hotel', null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1112', 'biz_demo_hotel', '查看', null, '/eova/template/single/btn/detail.html', '/form/detail/hotel*', null, '4', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1113', 'biz_demo_hotel', '导入', null, '/eova/template/single/btn/import.html', '/single_grid/importXls/biz_demo_hotel;/single_grid/doImportXls/biz_demo_hotel', null, '5', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1114', 'biz_demo_hotel', '隐藏', null, '/eova/template/single/btn/hide.html', '/grid/hide/hotel', null, '6', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('1115', 'biz_demo_hotel_stock', '查询', null, 'query', '/single_grid/list/biz_demo_hotel_stock;/grid/query/hotel_stock*;/grid/export/hotel_stock*', null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1116', 'biz_demo_hotel_stock', '新增', null, '/eova/template/single/btn/add.html', '/form/add/hotel_stock*;/form/doAdd/hotel_stock', null, '1', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1117', 'biz_demo_hotel_stock', '修改', null, '/eova/template/single/btn/update.html', '/form/update/hotel_stock*;/form/doUpdate/hotel_stock', null, '2', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1118', 'biz_demo_hotel_stock', '删除', null, '/eova/template/single/btn/delete.html', '/grid/delete/hotel_stock', null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1119', 'biz_demo_hotel_stock', '查看', null, '/eova/template/single/btn/detail.html', '/form/detail/hotel_stock*', null, '4', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1120', 'biz_demo_hotel_stock', '导入', null, '/eova/template/single/btn/import.html', '/single_grid/importXls/biz_demo_hotel_stock;/single_grid/doImportXls/biz_demo_hotel_stock', null, '5', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1121', 'biz_demo_hotel_stock', '隐藏', null, '/eova/template/single/btn/hide.html', '/grid/hide/hotel_stock', null, '6', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('1122', 'biz_product', '查询', null, 'query', '/single_grid/list/biz_product;/grid/query/product*;/grid/export/product*', null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1123', 'biz_product', '新增', null, '/eova/template/single/btn/add.html', '/form/add/product*;/form/doAdd/product', null, '1', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1124', 'biz_product', '修改', null, '/eova/template/single/btn/update.html', '/form/update/product*;/form/doUpdate/product', null, '2', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1125', 'biz_product', '删除', null, '/eova/template/single/btn/delete.html', '/grid/delete/product', null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1126', 'biz_product', '查看', null, '/eova/template/single/btn/detail.html', '/form/detail/product*', null, '4', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1127', 'biz_product', '导入', null, '/eova/template/single/btn/import.html', '/single_grid/importXls/biz_product;/single_grid/doImportXls/biz_product', null, '5', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1128', 'biz_product', '隐藏', null, '/eova/template/single/btn/hide.html', '/grid/hide/product', null, '6', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('1130', 'biz_office_xls1', '查询', null, 'query', '/office/list/biz_office_xls1;/office/show/biz_office_xls1;/office/file/biz_office_xls1', null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1131', 'biz_office_doc1', '查询', null, 'query', '/office/list/biz_office_doc1;/office/show/biz_office_doc1;/office/file/biz_office_doc1', null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1132', 'sys_users', '查询', null, 'query', '/single_grid/list/sys_users;/grid/query/eova_user_code*;/grid/export/eova_user_code*', null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1133', 'sys_users', '新增', null, '/eova/template/single/btn/add.html', '/form/add/eova_user_code*;/form/doAdd/eova_user_code', null, '1', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1134', 'sys_users', '修改', null, '/eova/template/single/btn/update.html', '/form/update/eova_user_code*;/form/doUpdate/eova_user_code', null, '2', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1135', 'sys_users', '删除', null, '/eova/template/single/btn/delete.html', '/grid/delete/eova_user_code', null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1136', 'sys_users', '查看', null, '/eova/template/single/btn/detail.html', '/form/detail/eova_user_code*', null, '4', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('1137', 'sys_users', '导入', null, '/eova/template/single/btn/import.html', '/single_grid/importXls/sys_users;/single_grid/doImportXls/sys_users', null, '5', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('1138', 'sys_users', '隐藏', null, '/eova/template/single/btn/hide.html', '/grid/hide/eova_user_code', null, '6', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('1163', 'test01', '查询', null, 'query', '/single_grid/list/test01;/grid/query/test01*;/grid/export/test01*', null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1164', 'test01', '新增', null, '/eova/template/single/btn/add.html', '/form/add/test01*;/form/doAdd/test01', null, '1', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1165', 'test01', '修改', null, '/eova/template/single/btn/update.html', '/form/update/test01*;/form/doUpdate/test01', null, '2', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1166', 'test01', '删除', null, '/eova/template/single/btn/delete.html', '/grid/delete/test01', null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1167', 'test01', '查看', null, '/eova/template/single/btn/detail.html', '/form/detail/test01*', null, '4', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1168', 'test01', '导入', null, '/eova/template/single/btn/import.html', '/single_grid/importXls/test01;/single_grid/doImportXls/test01', null, '5', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1169', 'test01', '隐藏', null, '/eova/template/single/btn/hide.html', '/grid/hide/test01', null, '6', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('1170', 'eova_dict', '查询', null, 'query', '/single_grid/list/eova_dict;/grid/query/dicts*;/grid/export/dicts*', null, '0', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1171', 'eova_dict', '新增', null, '/eova/template/single/btn/add.html', '/form/add/dicts*;/form/doAdd/dicts', null, '1', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1172', 'eova_dict', '修改', null, '/eova/template/single/btn/update.html', '/form/update/dicts*;/form/doUpdate/dicts', null, '2', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1173', 'eova_dict', '删除', null, '/eova/template/single/btn/delete.html', '/grid/delete/dicts', null, '3', '0', '1', '0');
INSERT INTO `eova_button` VALUES ('1174', 'eova_dict', '查看', null, '/eova/template/single/btn/detail.html', '/form/detail/dicts*', null, '4', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('1175', 'eova_dict', '导入', null, '/eova/template/single/btn/import.html', '/single_grid/importXls/eova_dict;/single_grid/doImportXls/eova_dict', null, '5', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('1176', 'eova_dict', '隐藏', null, '/eova/template/single/btn/hide.html', '/grid/hide/dicts', null, '6', '0', '1', '1');
INSERT INTO `eova_button` VALUES ('1177', 'eova_dict', '复制字典', 'eova-icon382', '/eova/template/common/btn/confirm.html', '/dict/copy', '/dict/copy', '7', '0', '0', '0');
INSERT INTO `eova_button` VALUES ('1188', 'biz_product', '清空库存', 'eova-icon259', '/eova/template/common/btn/confirm.html', '/product/clean', '/product/clean', '8', '0', '0', '0');
INSERT INTO `eova_button` VALUES ('1189', 'biz_product', '添加积分', 'eova-icon302', '/eova/template/common/btn/input.html', '/product/score', '/product/score', '9', '0', '0', '0');
INSERT INTO `eova_button` VALUES ('1190', 'biz_product', '更新产品', 'eova-icon76', '/eova/template/common/btn/execute.html', '/product/refresh', '/product/refresh', '10', '0', '0', '0');
INSERT INTO `eova_button` VALUES ('1238', 'eova_druid', '查询', null, 'query', '/druid/**', null, '0', '0', '1', '0');

-- ----------------------------
-- Table structure for eova_dict
-- ----------------------------
DROP TABLE IF EXISTS `eova_dict`;
CREATE TABLE `eova_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `object` varchar(50) NOT NULL,
  `field` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8 COMMENT='EOVA字典';

-- ----------------------------
-- Records of eova_dict
-- ----------------------------
INSERT INTO `eova_dict` VALUES ('1', 'main', '默认', 'eova_object', 'data_source');
INSERT INTO `eova_dict` VALUES ('2', 'eova', 'EOVA', 'eova_object', 'data_source');
INSERT INTO `eova_dict` VALUES ('40', '1', '新增', 'eova_log', 'type');
INSERT INTO `eova_dict` VALUES ('41', '2', '修改', 'eova_log', 'type');
INSERT INTO `eova_dict` VALUES ('42', '3', '删除', 'eova_log', 'type');
INSERT INTO `eova_dict` VALUES ('70', '0', '正常', 'eova_field', 'update_status');
INSERT INTO `eova_dict` VALUES ('71', '10', '只读', 'eova_field', 'update_status');
INSERT INTO `eova_dict` VALUES ('72', '20', '隐藏', 'eova_field', 'update_status');
INSERT INTO `eova_dict` VALUES ('73', '50', '禁用', 'eova_field', 'update_status');
INSERT INTO `eova_dict` VALUES ('100', '0', '暂停', 'eova_job', 'state');
INSERT INTO `eova_dict` VALUES ('101', '1', '运行', 'eova_job', 'state');

-- ----------------------------
-- Table structure for eova_field
-- ----------------------------
DROP TABLE IF EXISTS `eova_field`;
CREATE TABLE `eova_field` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `object_code` varchar(50) NOT NULL,
  `fieldnum` int(11) DEFAULT '0' COMMENT '表单分组序号',
  `order_num` int(4) DEFAULT '9' COMMENT '排序索引',
  `fieldset` varchar(255) DEFAULT '' COMMENT '表单分组',
  `table_name` varchar(255) DEFAULT NULL COMMENT '字段表名',
  `en` varchar(50) NOT NULL COMMENT '英文名',
  `cn` varchar(50) NOT NULL COMMENT '中文名',
  `is_auto` tinyint(1) DEFAULT '0' COMMENT '主键是否自增长',
  `type` varchar(10) DEFAULT '文本框' COMMENT '控件类型',
  `exp` varchar(800) DEFAULT NULL COMMENT '控件表达式',
  `is_query` tinyint(1) DEFAULT '0' COMMENT '是否可查询',
  `is_show` tinyint(1) DEFAULT '1' COMMENT '是否可显示',
  `is_disable` tinyint(1) DEFAULT '0' COMMENT '是否禁用',
  `is_order` tinyint(1) DEFAULT '1' COMMENT '是否可排序',
  `is_add` tinyint(1) DEFAULT '1' COMMENT '是否可新增字段(V1.6废弃)',
  `is_update` tinyint(1) DEFAULT '1' COMMENT '是否可修改字段(V1.6废弃)',
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
  `add_status` int(3) DEFAULT '0' COMMENT '状态：0=正常，10=只读，20=隐藏，50=禁用',
  `update_status` int(3) DEFAULT '0' COMMENT '状态：0=正常，10=只读，20=隐藏，50=禁用',
  `data_type` int(5) DEFAULT '12' COMMENT '数据类型',
  `data_type_name` varchar(20) DEFAULT 'VARCHAR' COMMENT '数据类型名称',
  `data_size` int(2) DEFAULT '1' COMMENT '整数位长度',
  `data_decimal` int(2) DEFAULT '0' COMMENT '小数位长度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3162 DEFAULT CHARSET=utf8 COMMENT='EOVA元字段';

-- ----------------------------
-- Records of eova_field
-- ----------------------------
INSERT INTO `eova_field` VALUES ('1', 'eova_meta_template', '0', '0', '', '', 'meta', 'meta', '0', '文本框', '', '0', '1', '0', '1', '1', '1', '1', '1', '0', '', '', '', '', '130', '20', '', '50', '50', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2', 'eova_object_code', '0', '1', '基础信息', null, 'id', 'ID', '1', '自增框', null, '0', '1', '0', '1', '1', '1', '0', '0', '0', null, null, null, null, '130', '20', null, '50', '20', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('3', 'eova_object_code', '0', '2', '基础信息', null, 'code', '编码', '0', '文本框', null, '1', '1', '0', '1', '1', '0', '0', '1', '0', null, 'eovacode', null, null, '200', '20', null, '0', '10', '12', 'VARCHAR', '100', '0');
INSERT INTO `eova_field` VALUES ('4', 'eova_object_code', '0', '3', '基础信息', null, 'name', '名称', '0', '文本框', null, '1', '1', '0', '1', '1', '1', '0', '1', '0', null, null, null, null, '130', '20', null, '0', '0', '12', 'VARCHAR', '100', '0');
INSERT INTO `eova_field` VALUES ('5', 'eova_object_code', '0', '4', '基础信息', null, 'view_name', '视图', '0', '文本框', null, '1', '1', '0', '1', '1', '1', '0', '0', '0', null, null, null, null, '70', '20', null, '50', '10', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('6', 'eova_object_code', '0', '5', '基础信息', null, 'table_name', '数据表', '0', '文本框', null, '1', '1', '0', '1', '1', '1', '0', '0', '0', null, null, null, null, '80', '20', null, '50', '10', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('7', 'eova_object_code', '0', '6', '基础信息', null, 'pk_name', '主键', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '0', '1', '0', null, null, null, null, '70', '20', null, '50', '10', '12', 'VARCHAR', '50', '0');
INSERT INTO `eova_field` VALUES ('8', 'eova_object_code', '0', '7', '基础信息', null, 'data_source', '数据源', '0', '下拉框', 'select value ID,name CN from eova_dict where object = \'eova_object\' and field = \'data_source\';ds=eova', '0', '1', '0', '1', '1', '1', '0', '1', '0', null, null, 'main', null, '70', '20', null, '0', '0', '12', 'VARCHAR', '50', '0');
INSERT INTO `eova_field` VALUES ('9', 'eova_object_code', '1', '8', '功能配置', null, 'is_single', '单选/多选', '0', '布尔框', null, '0', '1', '0', '1', '1', '1', '0', '0', '0', null, null, '1', null, '70', '20', null, '0', '0', '-7', 'BIT', '0', '0');
INSERT INTO `eova_field` VALUES ('10', 'eova_object_code', '1', '9', '功能配置', null, 'is_show_num', '显示行号', '0', '布尔框', null, '0', '1', '0', '1', '1', '1', '0', '0', '0', null, null, '1', null, '70', '20', null, '0', '0', '-7', 'BIT', '0', '0');
INSERT INTO `eova_field` VALUES ('11', 'eova_object_code', '1', '10', '功能配置', null, 'default_order', '默认排序', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '0', '0', '0', '例如：id desc (默认按ID倒序)', null, null, null, '70', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('12', 'eova_object_code', '1', '11', '功能配置', null, 'filter', '过滤条件', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '0', '0', '0', '例如：status = 1 (只显示状态为1的数据)', null, null, null, '130', '20', null, '0', '0', '12', 'VARCHAR', '500', '0');
INSERT INTO `eova_field` VALUES ('13', 'eova_object_code', '1', '13', '功能配置', null, 'diy_js', '依赖JS', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', '自定义JS文件路径', null, null, null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('14', 'eova_object_code', '1', '8', '功能配置', null, 'is_celledit', '行内编辑', '0', '布尔框', null, '0', '1', '0', '1', '1', '1', '0', '0', '0', null, null, '0', null, '70', '20', null, '0', '0', '-7', 'BIT', '0', '0');
INSERT INTO `eova_field` VALUES ('15', 'eova_object_code', '1', '12', '功能配置', null, 'biz_intercept', '业务拦截器', '0', '文本域', null, '1', '1', '0', '1', '1', '1', '0', '0', '0', '继承：com.eova.core.meta.MetaObjectIntercept', null, null, null, '179', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('16', 'eova_object_code', '1', '9', '功能配置', null, 'is_first_load', '是否初始加载', '0', '布尔框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '1', null, '130', '20', null, '0', '0', '-7', 'BIT', '0', '0');
INSERT INTO `eova_field` VALUES ('17', 'eova_object_code', '1', '17', '功能配置', null, 'view_sql', '视图SQL', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '', null, '130', '100', null, '50', '10', '12', 'VARCHAR', '1000', '0');
INSERT INTO `eova_field` VALUES ('18', 'eova_object_code', '1', '18', '功能配置', null, 'config', '拓展配置', '0', 'JSON框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '', null, '130', '300', null, '0', '0', '12', 'VARCHAR', '2000', '0');
INSERT INTO `eova_field` VALUES ('50', 'eova_field_code', '0', '1', '', null, 'id', 'ID', '1', '自增框', null, '0', '0', '0', '1', '1', '1', '0', '0', '0', null, null, null, null, '130', '20', null, '50', '20', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('51', 'eova_field_code', '0', '2', '', null, 'object_code', '对象编码', '0', '查找框', 'select code 编码,name 名称 from eova_object where id > 999 order by id desc;ds=eova', '1', '1', '0', '1', '1', '1', '0', '0', '0', null, null, null, null, '150', '20', null, '0', '0', '12', 'VARCHAR', '50', '0');
INSERT INTO `eova_field` VALUES ('52', 'eova_field_code', '0', '9', '', null, 'en', '字段名', '0', '文本框', null, '1', '1', '0', '1', '1', '1', '0', '0', '0', '数据库的字段名', null, null, null, '120', '20', null, '0', '0', '12', 'VARCHAR', '50', '0');
INSERT INTO `eova_field` VALUES ('53', 'eova_field_code', '0', '8', '', null, 'cn', '中文名', '0', '文本框', null, '1', '1', '0', '1', '1', '1', '1', '0', '0', '字段对应的中文描述', null, null, null, '130', '20', null, '0', '0', '12', 'VARCHAR', '50', '0');
INSERT INTO `eova_field` VALUES ('54', 'eova_field_code', '0', '24', '', null, 'is_auto', '自增长', '0', '布尔框', null, '0', '0', '0', '1', '1', '1', '1', '0', '0', null, null, '0', null, '70', '20', null, '0', '0', '-7', 'BIT', '0', '0');
INSERT INTO `eova_field` VALUES ('55', 'eova_field_code', '0', '10', '', null, 'data_type_name', '字段类型', '0', '文本框', '', '0', '1', '0', '0', '1', '1', '0', '0', '0', null, null, null, null, '124', '20', null, '0', '10', '12', 'VARCHAR', '20', '0');
INSERT INTO `eova_field` VALUES ('56', 'eova_field_code', '0', '11', '', null, 'type', '控件类型', '0', '下拉框', 'select value ID,name CN from eova_widget;ds=eova', '1', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '文本框', null, '70', '20', null, '0', '0', '12', 'VARCHAR', '10', '0');
INSERT INTO `eova_field` VALUES ('57', 'eova_field_code', '0', '6', '', null, 'order_num', '排序', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '9', null, '50', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('58', 'eova_field_code', '0', '51', '', null, 'exp', '表达式', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', '查找框和下拉框需需要表达式', null, null, null, '130', '20', null, '0', '0', '12', 'VARCHAR', '800', '0');
INSERT INTO `eova_field` VALUES ('59', 'eova_field_code', '0', '25', '', null, 'is_query', '快速查询', '0', '布尔框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '0', null, '60', '20', null, '0', '0', '-7', 'BIT', '0', '0');
INSERT INTO `eova_field` VALUES ('60', 'eova_field_code', '0', '26', '', null, 'is_show', '列表显示', '0', '布尔框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '1', null, '60', '20', null, '0', '0', '-7', 'BIT', '0', '0');
INSERT INTO `eova_field` VALUES ('61', 'eova_field_code', '0', '32', '', null, 'is_order', '允许排序', '0', '布尔框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '1', null, '60', '20', null, '0', '0', '-7', 'BIT', '0', '0');
INSERT INTO `eova_field` VALUES ('62', 'eova_field_code', '0', '28', '', null, 'is_add', '允许新增', '0', '布尔框', null, '0', '0', '0', '0', '0', '0', '0', '0', '0', null, null, '1', null, '70', '20', null, '0', '0', '-7', 'BIT', '0', '0');
INSERT INTO `eova_field` VALUES ('63', 'eova_field_code', '0', '29', '', null, 'is_update', '允许修改', '0', '布尔框', null, '0', '0', '0', '0', '0', '0', '0', '0', '0', null, null, '1', null, '70', '20', null, '0', '0', '-7', 'BIT', '0', '0');
INSERT INTO `eova_field` VALUES ('64', 'eova_field_code', '0', '31', '', null, 'is_required', '是否必填', '0', '布尔框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '1', null, '60', '20', null, '0', '0', '-7', 'BIT', '0', '0');
INSERT INTO `eova_field` VALUES ('65', 'eova_field_code', '0', '36', '', null, 'defaulter', '默认值', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', '缺省默认值', null, null, null, '110', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('66', 'eova_field_code', '0', '20', '', null, 'width', '列宽度', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '130', null, '50', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('67', 'eova_field_code', '0', '21', '', null, 'height', '高度', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '20', null, '50', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('68', 'eova_field_code', '0', '33', '', null, 'is_multiple', '是否有多值', '0', '布尔框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '0', null, '70', '20', null, '0', '0', '-7', 'BIT', '0', '0');
INSERT INTO `eova_field` VALUES ('69', 'eova_field_code', '0', '30', '', null, 'is_edit', '单元格编辑', '0', '布尔框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '1', null, '75', '20', null, '0', '0', '-7', 'BIT', '0', '0');
INSERT INTO `eova_field` VALUES ('70', 'eova_field_code', '0', '35', '', null, 'placeholder', '输入提示', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', 'input的placeholder属性', null, null, null, '60', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('71', 'eova_field_code', '0', '54', '', null, 'formatter', '格式化器', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', '格式化的JS,参考EasyUI datagrid formatter', null, null, null, '130', '150', null, '0', '0', '12', 'VARCHAR', '2000', '0');
INSERT INTO `eova_field` VALUES ('72', 'eova_field_code', '0', '53', '', null, 'validator', 'UI校验器', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', 'UI校验规则', null, null, null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('73', 'eova_field_code', '0', '27', '', null, 'is_disable', '是否禁用', '0', '布尔框', null, '0', '0', '0', '1', '1', '1', '1', '0', '0', null, null, '0', null, '70', '20', null, '0', '0', '-7', 'BIT', '0', '0');
INSERT INTO `eova_field` VALUES ('74', 'eova_field_code', '0', '23', '', null, 'update_status', '更新状态', '0', '下拉框', 'select value ID,name CN from eova_dict where object = \'eova_field\' and field = \'update_status\';ds=eova', '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '0', null, '60', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('75', 'eova_field_code', '0', '22', '', null, 'add_status', '新增状态', '0', '下拉框', 'select value ID,name CN from eova_dict where object = \'eova_field\' and field = \'update_status\';ds=eova', '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '0', null, '60', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('76', 'eova_field_code', '0', '7', '', null, 'fieldset', '分组名称', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '90', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('77', 'eova_field_code', '0', '5', '', null, 'fieldnum', '分组号', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '0', null, '70', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('78', 'eova_field_code', '0', '6', '', null, 'table_name', '字段表名', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '', null, '100', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('79', 'eova_field_code', '0', '999', '', null, 'config', '拓展配置', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '2000', '0');
INSERT INTO `eova_field` VALUES ('80', 'eova_field_code', '0', '30', '', null, 'data_type', '数据类型', '0', '文本框', null, '0', '0', '0', '1', '1', '1', '0', '1', '0', null, null, '', null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('81', 'eova_field_code', '0', '32', '', null, 'data_size', '整数位长度', '0', '文本框', null, '0', '0', '0', '1', '1', '1', '0', '0', '0', null, null, '1', null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('82', 'eova_field_code', '0', '33', '', null, 'data_decimal', '小数位长度', '0', '文本框', null, '0', '0', '0', '1', '1', '1', '0', '0', '0', null, null, '0', null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('100', 'eova_menu_code', '0', '1', '', null, 'id', 'ID', '1', '自增框', null, '0', '0', '0', '1', '1', '1', '0', '0', '0', null, null, null, null, '40', '20', null, '50', '20', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('101', 'eova_menu_code', '0', '4', null, null, 'code', '编码', '0', '文本框', null, '1', '1', '1', '1', '1', '0', '0', '1', '0', null, 'eovacode', null, null, '206', '20', null, '0', '10', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('102', 'eova_menu_code', '0', '1', null, null, 'name', '名称', '0', '文本框', null, '1', '1', '0', '1', '1', '1', '0', '1', '0', null, null, null, null, '206', '20', null, '0', '0', '12', 'VARCHAR', '100', '0');
INSERT INTO `eova_field` VALUES ('103', 'eova_menu_code', '0', '2', null, null, 'type', '类型', '0', '文本框', null, '0', '1', '0', '1', '1', '0', '0', '1', '0', null, null, null, null, '126', '20', null, '0', '10', '12', 'VARCHAR', '20', '0');
INSERT INTO `eova_field` VALUES ('104', 'eova_menu_code', '0', '6', null, null, 'iconskip', '图标', '0', '图标框', null, '0', '0', '0', '1', '1', '1', '0', '0', '0', null, null, null, null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('105', 'eova_menu_code', '0', '9', null, null, 'order_num', '序号', '0', '文本框', null, '0', '0', '0', '1', '1', '1', '0', '1', '0', null, null, '0', null, '30', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('106', 'eova_menu_code', '0', '9', null, null, 'parent_id', '父节点', '0', '下拉树', 'select id ID,parent_id PID, name NAME from eova_menu;ds=eova', '0', '0', '0', '1', '1', '0', '0', '1', '0', null, null, '0', null, '100', '20', null, '50', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('107', 'eova_menu_code', '0', '16', '', null, 'is_hide', '是否隐藏', '0', '布尔框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '0', null, '136', '20', null, '10', '10', '-7', 'BIT', '0', '0');
INSERT INTO `eova_field` VALUES ('109', 'eova_menu_code', '0', '12', '', null, 'biz_intercept', '业务拦截器', '0', '文本域', null, '0', '0', '0', '1', '1', '1', '0', '0', '0', '继承：模版业务拦截器', null, null, null, '300', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('110', 'eova_menu_code', '0', '15', '', null, 'url', '自定义功能', '0', '文本域', null, '0', '0', '0', '1', '1', '0', '0', '0', '0', null, null, null, null, '130', '20', null, '0', '50', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('111', 'eova_menu_code', '0', '11', '', null, 'filter', '过滤条件', '0', '文本域', '', '0', '1', '0', '1', '1', '1', '1', '0', '0', '例如：status = 1 (只显示状态为1的数据)', null, null, null, '346', '20', null, '0', '0', '12', 'VARCHAR', '500', '0');
INSERT INTO `eova_field` VALUES ('112', 'eova_menu_code', '0', '13', '', null, 'diy_js', '依赖JS', '0', '文本域', '', '0', '0', '0', '1', '1', '1', '1', '0', '0', '自定义JS文件路径', null, null, null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('113', 'eova_menu_code', '0', '17', '', null, 'open', '是否展开', '0', '布尔框', null, '0', '0', '0', '1', '1', '1', '1', '0', '0', null, null, '1', null, '130', '20', null, '0', '0', '-7', 'BIT', '0', '0');
INSERT INTO `eova_field` VALUES ('114', 'eova_menu_code', '0', '18', '', null, 'config', '菜单配置JSON', '0', 'JSON框', null, '0', '0', '0', '1', '1', '1', '1', '0', '0', null, null, '', null, '130', '400', null, '0', '0', '12', 'VARCHAR', '500', '0');
INSERT INTO `eova_field` VALUES ('150', 'eova_button_code', '0', '30', '', null, 'is_hide', '是否隐藏', '0', '布尔框', null, '0', '1', '0', '1', '0', '1', '1', '0', '0', null, null, '0', null, '60', '20', null, '0', '0', '-7', 'BIT', '0', '0');
INSERT INTO `eova_field` VALUES ('151', 'eova_button_code', '0', '3', '', null, 'icon', 'ICON', '0', '图标框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, 'function(value,row,index,field){if(value){return\'<span class=\"tree-icon tree-file \'+value+\'\"></span>\'}return value}', '36', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('152', 'eova_button_code', '0', '1', '', null, 'id', 'ID', '1', '自增框', null, '0', '0', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '50', '20', null, '50', '20', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('153', 'eova_button_code', '0', '2', '', null, 'menu_code', '菜单编码', '0', '查找框', 'select code 菜单编码,name 菜单名称 from eova_menu where 1=1;ds=eova', '0', '0', '0', '1', '1', '1', '0', '0', '0', null, 'eovacode', null, null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('154', 'eova_button_code', '0', '4', '', null, 'name', '功能名称', '0', '文本框', null, '1', '1', '0', '1', '1', '1', '0', '0', '0', null, null, null, null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('155', 'eova_button_code', '0', '10', '', null, 'ui', '前端实现', '0', '文本域', null, '1', '1', '0', '1', '1', '1', '1', '0', '0', '前端实现的文件路径', null, null, null, '266', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('156', 'eova_button_code', '0', '20', '', null, 'bs', 'URI权限规则', '0', '文本域', null, '1', '1', '0', '1', '1', '1', '0', '0', '0', '本功能所用服务端接口的URI权限规则', null, null, null, '250', '60', null, '0', '0', '12', 'VARCHAR', '500', '0');
INSERT INTO `eova_field` VALUES ('157', 'eova_button_code', '0', '6', '', null, 'order_num', '序号', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', '按钮的显示顺序', 'digits', '0', null, '50', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('158', 'eova_button_code', '0', '7', '', null, 'group_num', '分组号', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', 'Toolbar分组号', 'digits', '0', null, '50', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('159', 'eova_button_code', '0', '8', '', null, 'is_base', '是否基础功能', '0', '布尔框', null, '0', '0', '0', '1', '0', '0', '0', '0', '0', '', null, '0', null, '130', '20', null, '0', '0', '-7', 'BIT', '0', '0');
INSERT INTO `eova_field` VALUES ('201', 'eova_user_code', '0', '20', '', null, 'login_id', '登录帐号', '0', '文本框', null, '1', '1', '0', '1', '1', '1', '0', '1', '0', null, null, null, null, '136', '20', null, '0', '0', '12', 'VARCHAR', '30', '0');
INSERT INTO `eova_field` VALUES ('202', 'eova_user_code', '0', '40', null, null, 'login_pwd', '登录密码', '0', '密码框', null, '0', '0', '0', '0', '1', '0', '0', '1', '0', null, null, null, null, '130', '20', null, '0', '50', '12', 'VARCHAR', '50', '0');
INSERT INTO `eova_field` VALUES ('203', 'eova_user_code', '0', '15', '', null, 'rid', '角色', '0', '下拉框', 'select id ID,name CN from eova_role where lv > ${user.role.lv} order by id;ds=eova', '1', '1', '0', '1', '1', '1', '0', '1', '0', null, null, '0', null, '137', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('204', 'eova_user_code', '0', '10', '', null, 'id', 'ID', '1', '自增框', null, '0', '1', '0', '1', '1', '1', '0', '0', '0', null, null, null, null, '71', '20', null, '50', '20', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('220', 'eova_role_code', '1', '0', '', null, 'id', 'ID', '1', '自增框', null, '0', '1', '0', '1', '1', '1', '0', '0', '0', null, null, null, null, '130', '20', null, '50', '20', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('221', 'eova_role_code', '1', '1', '', null, 'name', '角色名', '0', '文本框', null, '1', '1', '0', '1', '1', '1', '0', '1', '0', null, null, null, null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('222', 'eova_role_code', '1', '3', '', null, 'info', '角色描述', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '0', '1', '0', null, null, null, null, '230', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('223', 'eova_role_code', '1', '2', '', null, 'lv', '权限级别', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '0', null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('260', 'eova_log_code', '1', '1', '', null, 'id', 'id', '1', '自增框', '', '0', '1', '0', '1', '1', '1', '0', '0', '0', '', '', null, '', '130', '20', null, '50', '20', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('261', 'eova_log_code', '1', '2', '', null, 'user_id', '操作用户', '0', '查找框', 'select id UID,login_id 用户名 from eova_user where 1=1;ds=eova', '1', '1', '0', '1', '1', '1', '0', '0', '0', null, null, null, null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('262', 'eova_log_code', '1', '3', '', '', 'type', '日志类型', '0', '下拉框', 'select value ID,name CN from eova_dict where object = \'eova_log\' and field = \'type\';ds=eova', '1', '1', '0', '1', '1', '1', '0', '0', '0', null, null, null, null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('263', 'eova_log_code', '1', '4', '', null, 'ip', '操作IP', '0', '文本框', null, '1', '1', '0', '1', '1', '1', '0', '0', '0', null, null, null, null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('264', 'eova_log_code', '1', '5', '', null, 'info', '详情', '0', '文本框', '', '1', '1', '0', '0', '0', '0', '0', '1', '0', '', '', null, '', '330', '20', null, '0', '0', '12', 'VARCHAR', '500', '0');
INSERT INTO `eova_field` VALUES ('265', 'eova_log_code', '1', '6', '', null, 'create_time', '操作时间', '0', '时间框', '', '1', '1', '0', '0', '0', '0', '0', '1', '0', null, null, null, null, '130', '20', null, '0', '0', '12', 'VARCHAR', '1', '0');
INSERT INTO `eova_field` VALUES ('280', 'eova_task_code', '1', '1', '', null, 'id', 'ID', '1', '自增框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '50', '20', null, '50', '20', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('281', 'eova_task_code', '1', '2', '', null, 'state', '状态', '0', '下拉框', 'select value ID,name CN from eova_dict where object = \'eova_job\' and field = \'state\';ds=eova', '1', '1', '0', '1', '0', '0', '0', '1', '0', null, null, '0', null, '50', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('282', 'eova_task_code', '1', '3', '', null, 'name', '名称', '0', '文本框', null, '1', '1', '0', '1', '1', '1', '1', '1', '0', '任务简称', null, null, null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('283', 'eova_task_code', '1', '4', '', null, 'exp', '表达式', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', 'Quartz表达式', '', null, null, '130', '20', null, '0', '0', '12', 'VARCHAR', '50', '0');
INSERT INTO `eova_field` VALUES ('284', 'eova_task_code', '1', '5', '', null, 'clazz', '实现类', '0', '文本域', null, '1', '1', '0', '1', '1', '1', '1', '1', '0', 'Job实现类', null, null, null, '230', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('285', 'eova_task_code', '1', '6', '', null, 'info', '说明', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', '任务的详细描述', null, null, null, '330', '50', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2000', 'celledit_users_code', '1', '1', '', null, 'id', 'id', '1', '自增框', null, '0', '0', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '50', '20', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2001', 'celledit_users_code', '1', '2', '', null, 'status', '状态', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '0', '1', '0', null, null, '0', null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2002', 'celledit_users_code', '1', '3', '', null, 'login_id', '登录账户', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2003', 'celledit_users_code', '1', '4', '', null, 'login_pwd', '录登密码', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '0', '1', '0', null, null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2004', 'celledit_users_code', '1', '5', '', null, 'nickname', '昵称', '0', '文本域', null, '1', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2005', 'celledit_users_code', '1', '6', '', null, 'reg_time', '注册时间', '0', '时间框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, null, null, '130', '20', null, '0', '0', '93', 'TIMESTAMP', '19', '0');
INSERT INTO `eova_field` VALUES ('2006', 'celledit_users_code', '1', '7', '', null, 'info', '备注', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2012', 'dicts', '0', '1', '', null, 'id', 'ID', '1', '自增框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '50', '20', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2013', 'dicts', '2', '40', '键值对', null, 'value', '值', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, null, null, '130', '20', null, '0', '0', '12', 'VARCHAR', '50', '0');
INSERT INTO `eova_field` VALUES ('2014', 'dicts', '2', '30', '键值对', null, 'name', '名称', '0', '文本框', null, '1', '1', '0', '1', '1', '1', '1', '1', '0', null, null, null, null, '130', '20', null, '0', '0', '12', 'VARCHAR', '50', '0');
INSERT INTO `eova_field` VALUES ('2015', 'dicts', '0', '10', '表字段', '', 'object', '表名', '0', '文本框', null, '1', '1', '0', '1', '1', '1', '1', '1', '0', null, null, null, null, '130', '20', null, '0', '0', '12', 'VARCHAR', '50', '0');
INSERT INTO `eova_field` VALUES ('2016', 'dicts', '0', '20', '表字段', null, 'field', '字段名', '0', '文本框', null, '1', '1', '0', '1', '1', '1', '1', '1', '0', null, null, null, null, '130', '20', null, '0', '0', '12', 'VARCHAR', '50', '0');
INSERT INTO `eova_field` VALUES ('2017', 'dicts', '2', '50', '键值对', null, 'ext', '扩展Json', '0', '文本框', null, '0', '0', '0', '0', '0', '0', '0', '0', '0', null, null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2018', 'hotel', '1', '11', '', null, 'province', '省', '0', '下拉框', 'select id ID,name CN from area where lv = 1;ds=main', '0', '1', '0', '0', '1', '1', '0', '1', '0', null, '', null, null, '146', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2019', 'hotel', '1', '12', '', null, 'city', '市', '0', '下拉框', 'select id ID,name CN from area where lv = 2;ds=main', '0', '1', '0', '0', '1', '1', '0', '1', '0', null, null, null, null, '146', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2020', 'hotel', '1', '13', '', null, 'region', '区', '0', '下拉框', 'select id ID,name CN from area where lv = 3;ds=main', '0', '1', '0', '0', '1', '1', '0', '1', '0', null, null, null, null, '146', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2021', 'hotel', '1', '1', '', null, 'id', 'ID', '1', '自增框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '65', '20', null, '50', '20', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2022', 'hotel', '1', '3', '', null, 'name', '酒店名', '0', '文本框', null, '1', '1', '0', '1', '1', '1', '1', '0', '0', null, '', null, 'function(value,row,index,field){var url=\'/single_grid/list/biz_demo_hotel_stock?query_hotel_id=\'+row.id;return \'<a href=\"javascript:parent.addTab(\\\'\'+row.name+\'库存\\\',\\\'\'+url+\'\\\',\\\'icon-building\\\');\" style=\"color:blue\">\'+value+\'</a>\';}', '241', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2023', 'hotel', '1', '4', '', null, 'tel', '电话', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '146', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2024', 'hotel', '1', '10', '', null, 'address', '详细地址', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '266', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2025', 'hotel', '1', '8', '', null, 'create_time', '创建时间', '0', '日期框', null, '1', '1', '0', '1', '1', '1', '1', '0', '0', null, '', null, null, '146', '20', null, '0', '0', '93', 'DATETIME', '19', '0');
INSERT INTO `eova_field` VALUES ('2026', 'hotel', '1', '7', '', null, 'state', '商户状态', '0', '下拉框', 'select value ID,name CN from dicts where object = \'hotel\' and field = \'state\';ds=main', '1', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '1', null, '146', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2027', 'hotel', '1', '7', '', null, 'score', '积分', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '0', null, '146', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2028', 'hotel_bed', '1', '1', '', null, 'id', 'ID', '1', '自增框', null, '0', '0', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '50', '20', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2029', 'hotel_bed', '1', '2', '', null, 'hotel_id', '酒店', '0', '下拉框', 'select id ID,name CN from hotel where 1=1;ds=main', '1', '0', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '10', '10', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2030', 'hotel_bed', '1', '3', '', null, 'sizes', '床铺尺码', '0', '下拉框', 'select value ID,name CN from dicts where object = \'product\' and field = \'size\';ds=main', '1', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '97', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2031', 'hotel_bed', '1', '4', '', null, 'num', '数量', '0', '文本框', null, '1', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '1', null, '362', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2032', 'hotel_stock', '1', '1', '', null, 'id', 'ID', '1', '自增框', '', '0', '0', '0', '1', '1', '1', '1', '0', '0', '', '', null, '', '130', '20', null, '50', '20', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2033', 'hotel_stock', '1', '2', '', null, 'hotel_id', '酒店', '0', '下拉框', 'select id ID,name CN from hotel where 1=1;ds=main', '0', '0', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2034', 'hotel_stock', '1', '3', '', null, 'category', '商品类型', '0', '下拉框', 'select value ID,name CN from dicts where object = \'product\' and field = \'category\';ds=main', '1', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2035', 'hotel_stock', '1', '4', '', null, 'num', '存货量', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '1', null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2036', 'item_code', '1', '1', '', null, 'id', 'ID', '1', '自增框', null, '1', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '50', '20', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2037', 'item_code', '1', '5', '', null, 'name', '名称', '0', '文件框', null, '1', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2038', 'item_code', '1', '4', '', null, 'info', '介绍', '0', '编辑框', '', '1', '1', '0', '1', '1', '1', '1', '1', '0', '', null, null, 'function(value,row,index,field){if(value&&value.length>10){return \'<span title=\"\'+value+\'\">\'+value+\'</span>\';}return value;}', '200', '20', null, '0', '0', '12', 'VARCHAR', '1000', '0');
INSERT INTO `eova_field` VALUES ('2039', 'item_code', '1', '6', '', null, 'img', '图片', '0', '图片框', '', '0', '1', '0', '1', '1', '1', '1', '1', '0', '', '', null, 'function(value,row,index,field){if(value){return \'<img src=\"/upimg/\'+value+\'\">\';}return value;}', '200', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2041', 'orders', '1', '100', '', '', 'diy_fun', '关联', '0', '文本框', '', '0', '1', '0', '0', '0', '0', '0', '0', '0', '', '', '', 'function(value,row,index,field){return\'<a target=\"_blank\" href=\"/singleGrid/list/biz_comment?query_order_id=\'+row.id+\'\" style=\"color:blue\">查看评价</a> <a target=\"_blank\" href=\"/form/update/payment-\'+row.pay_id+\'\" style=\"color:blue\">支付详情</a>\'}', '130', '20', null, '0', '0', '0', 'varchar', '1', '0');
INSERT INTO `eova_field` VALUES ('2042', 'orders', '1', '1', '', null, 'hotel_id', '酒店', '0', '查找框', 'select id ID,name 酒店名,address 地址 from hotel', '1', '1', '0', '1', '1', '1', '1', '1', '0', '', null, '0', null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2043', 'orders', '1', '1', '', null, 'id', 'ID', '1', '自增框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '50', '20', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2044', 'orders', '1', '6', '', null, 'pay_id', '支付ID', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '0', null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2045', 'orders', '1', '3', '', null, 'state', '订单状态', '0', '下拉框', 'select value ID,name CN from dicts where object = \'orders\' and field = \'state\';ds=main', '1', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '10', null, '130', '20', null, '0', '0', '4', 'INT UNSIGNED', '10', '0');
INSERT INTO `eova_field` VALUES ('2046', 'orders', '1', '4', '', null, 'money', '应付金额', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '0.00', null, '130', '20', null, '0', '0', '8', 'DOUBLE UNSIGNED', '10', '2');
INSERT INTO `eova_field` VALUES ('2047', 'orders', '1', '5', '', null, 'score', '消耗积分', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '0.00', null, '130', '20', null, '0', '0', '8', 'DOUBLE UNSIGNED', '10', '2');
INSERT INTO `eova_field` VALUES ('2048', 'orders', '1', '6', '', null, 'memo', '备注', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '256', '0');
INSERT INTO `eova_field` VALUES ('2049', 'orders', '1', '7', '', null, 'create_user_id', '创建用户ID', '0', '查找框', 'select id UID,login_id 用户名 from eova_user where 1=1;ds=eova', '1', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2050', 'orders', '1', '8', '', null, 'update_user_id', '最后更新人', '0', '查找框', 'select id UID,login_id 用户名 from eova_user where 1=1;ds=eova', '1', '0', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2051', 'orders', '1', '9', '', null, 'create_time', '订单时间', '0', '日期框', null, '1', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '0', '0', '93', 'DATETIME', '19', '0');
INSERT INTO `eova_field` VALUES ('2052', 'orders', '1', '10', '', null, 'update_time', '更新时间', '0', '日期框', null, '0', '0', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '0', '0', '93', 'DATETIME', '19', '0');
INSERT INTO `eova_field` VALUES ('2053', 'orders', '1', '11', '', null, 'is_invoice', '是否开票', '0', '布尔框', 'select value ID,name CN from dicts where object = \'orders\' and field = \'is_invoice\';ds=main', '0', '0', '0', '1', '1', '1', '1', '1', '0', null, null, '0', null, '130', '20', null, '0', '0', '-7', 'BIT', '0', '0');
INSERT INTO `eova_field` VALUES ('2054', 'orders', '1', '12', '', null, 'additional_info', '订单补充信息(JSON格式)', '0', '文本域', null, '0', '0', '0', '0', '0', '0', '0', '1', '0', null, null, null, '', '130', '20', null, '0', '0', '12', 'VARCHAR', '1000', '0');
INSERT INTO `eova_field` VALUES ('2055', 'order_item', '1', '1', '', null, 'id', '编号', '1', '自增框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '50', '20', '4', 'INT UNSIGNED', '10', '0');
INSERT INTO `eova_field` VALUES ('2056', 'order_item', '1', '2', '', null, 'order_id', '订单ID', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2057', 'order_item', '1', '3', '', null, 'product', '产品', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '230', '20', null, '0', '0', '12', 'VARCHAR', '128', '0');
INSERT INTO `eova_field` VALUES ('2058', 'order_item', '1', '4', '', null, 'price', '单价', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '0', null, '130', '20', null, '0', '0', '8', 'DOUBLE', '10', '0');
INSERT INTO `eova_field` VALUES ('2059', 'order_item', '1', '5', '', null, 'num', '购买数量', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '1', null, '130', '20', null, '0', '0', '4', 'INT UNSIGNED', '10', '0');
INSERT INTO `eova_field` VALUES ('2060', 'payment', '1', '1', '', null, 'id', '编号', '1', '自增框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '50', '20', '0', 'int', '1', '0');
INSERT INTO `eova_field` VALUES ('2061', 'payment', '1', '2', '', null, 'relation_id', '订单ID', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '0', '0', 'int', '1', '0');
INSERT INTO `eova_field` VALUES ('2062', 'payment', '1', '3', '', null, 'subject', '商品名', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '', null, '130', '20', null, '0', '0', '0', 'varchar', '1', '0');
INSERT INTO `eova_field` VALUES ('2063', 'payment', '1', '4', '', null, 'money', '支付金额', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '0.00', null, '130', '20', null, '0', '0', '0', 'varchar', '1', '0');
INSERT INTO `eova_field` VALUES ('2064', 'payment', '1', '5', '', null, 'platform', '支付平台', '0', '下拉框', 'select value ID,name CN from dicts where object = \'payment\' and field = \'platform\';ds=main', '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '0', '0', 'int', '1', '0');
INSERT INTO `eova_field` VALUES ('2065', 'payment', '1', '6', '', null, 'pay_business', '支付业务', '0', '下拉框', 'select value ID,name CN from dicts where object = \'payment\' and field = \'pay_business\';ds=main', '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '1', null, '130', '20', null, '0', '0', '0', 'int', '1', '0');
INSERT INTO `eova_field` VALUES ('2066', 'payment', '1', '7', '', null, 'pay_sequence', '支付流水号', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '', null, '130', '20', null, '0', '0', '0', 'varchar', '1', '0');
INSERT INTO `eova_field` VALUES ('2067', 'payment', '1', '8', '', null, 'platform_sequence', '平台流水号', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '', null, '130', '20', null, '0', '0', '0', 'varchar', '1', '0');
INSERT INTO `eova_field` VALUES ('2068', 'payment', '1', '9', '', null, 'bank_sequence', '银行流水号', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '', null, '130', '20', null, '0', '0', '0', 'varchar', '1', '0');
INSERT INTO `eova_field` VALUES ('2069', 'payment', '1', '10', '', null, 'status', '支付状态', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '0', null, '130', '20', null, '0', '0', '0', 'int', '1', '0');
INSERT INTO `eova_field` VALUES ('2070', 'payment', '1', '11', '', null, 'failed_reason', '支付失败原因', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '', null, '130', '20', null, '0', '0', '0', 'varchar', '1', '0');
INSERT INTO `eova_field` VALUES ('2071', 'payment', '1', '20', '', null, 'extend_info', '支付扩展信息', '0', '文本域', null, '0', '0', '0', '0', '0', '0', '0', '0', '0', null, null, '', null, '130', '120', null, '0', '0', '0', 'varchar', '1', '0');
INSERT INTO `eova_field` VALUES ('2072', 'payment', '1', '2', '', null, 'create_user_id', '创建用户ID', '0', '查找框', 'select id UID,login_id 用户名 from eova_user where 1=1;ds=eova', '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '0', null, '130', '20', null, '0', '0', '0', 'int', '1', '0');
INSERT INTO `eova_field` VALUES ('2073', 'payment', '1', '14', '', null, 'create_time', '创建时间', '0', '时间框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '0', '0', 'datetime', '1', '0');
INSERT INTO `eova_field` VALUES ('2074', 'payment', '1', '15', '', null, 'update_time', '更新时间', '0', '时间框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '0', '0', 'datetime', '1', '0');
INSERT INTO `eova_field` VALUES ('2075', 'player_code', '1', '10', '', '', 'tag', '标签', '0', '下拉框', 'select value ID, name CN from dicts where object = \'users\' and field = \'tag\'', '1', '1', '0', '1', '1', '1', '1', '1', '1', '', '', '', '', '130', '20', '', '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2076', 'player_code', '1', '0', '', '', 'id', 'ID', '1', '自增框', '', '0', '1', '0', '1', '1', '1', '0', '0', '0', '', '', '', '', '47', '20', '', '50', '20', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2077', 'player_code', '1', '2', '', '', 'status', '状态', '0', '查找框', 'select value ID , name CN from dicts where object = \'users\' and field = \'status\'', '1', '1', '0', '1', '1', '1', '1', '1', '0', '', '', '0', '', '65', '20', '', '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2078', 'player_code', '1', '3', '', '', 'login_id', '登录账户', '0', '文本框', '', '1', '1', '0', '1', '1', '1', '1', '1', '0', '', '', '', '', '130', '20', '', '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2079', 'player_code', '1', '4', '', '', 'login_pwd', '录登密码', '0', '密码框', '', '0', '1', '1', '1', '1', '1', '0', '1', '0', '', '', '', '', '130', '20', '', '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2080', 'player_code', '1', '1', '', '', 'nickname', '艺人姓名', '0', '文本框', '', '1', '1', '0', '1', '1', '1', '1', '1', '0', '请输入姓名', '', '', 'function(value,row,index,field){if(value){return \'<b style=\"color:red\">\'+value+\'</b>\';}return value;}', '130', '20', '', '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2081', 'player_code', '1', '6', '', '', 'reg_time', '注册时间', '0', '时间框', '', '1', '1', '0', '1', '1', '1', '1', '1', '0', '', '', '', '', '180', '20', '', '0', '0', '93', 'TIMESTAMP', '19', '0');
INSERT INTO `eova_field` VALUES ('2082', 'player_code', '1', '9', null, null, 'info', '备注', '0', '文本域', null, '0', '1', '0', '1', '0', '0', '0', '0', '0', null, null, null, null, '130', '20', null, '50', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2083', 'product', '1', '1', '', null, 'id', 'ID', '1', '自增框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '70', '20', null, '50', '20', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2084', 'product', '1', '2', '', null, 'type', '产品类型', '0', '下拉框', 'select value ID,name CN from dicts where object = \'product\' and field = \'type\'', '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '1', null, '70', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2085', 'product', '1', '3', '', null, 'category', '分类', '0', '下拉框', 'select value ID,name CN from dicts where object = \'product\' and field = \'category\'', '1', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '70', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2086', 'product', '1', '4', '', null, 'stuff', '材料', '0', '下拉框', 'select value ID,name CN from dicts where object = \'product\' and field = \'stuff\'', '1', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '70', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2087', 'product', '1', '5', null, null, 'sizes', '尺码', '0', '下拉框', 'select value ID,name CN from dicts where object = \'product\' and field = \'sizes\'', '1', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '70', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2088', 'product', '1', '6', '', null, 'name', '名称', '0', '文本框', null, '1', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '200', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2089', 'product', '1', '1', '', null, 'img', '商品图片', '0', '图片框', null, '0', '0', '0', '1', '1', '1', '1', '1', '0', null, null, null, null, '130', '20', '{\"filedir\":\"/image/bucao/product\"}', '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2090', 'product', '1', '8', '', null, 'test_price', '试用单价', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '0', null, '130', '20', null, '0', '0', '8', 'DOUBLE', '22', '0');
INSERT INTO `eova_field` VALUES ('2091', 'product', '1', '9', '', null, 'price', '商品单价', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '0', null, '130', '20', null, '0', '0', '8', 'DOUBLE', '22', '0');
INSERT INTO `eova_field` VALUES ('2092', 'product', '1', '10', '', null, 'cost_score', '消耗积分', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '0', null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2093', 'product', '1', '11', '', null, 'score', '奖励积分', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '0', null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2094', 'product', '1', '12', '', null, 'stock', '库存', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '1', null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2095', 'product', '1', '13', '', null, 'create_time', '创建时间', '0', '日期框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '0', '0', '93', 'DATETIME', '19', '0');
INSERT INTO `eova_field` VALUES ('2096', 'product', '1', '14', '', null, 'update_time', '更新时间', '0', '时间框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, null, null, '130', '20', null, '0', '0', '93', 'TIMESTAMP', '19', '0');
INSERT INTO `eova_field` VALUES ('2097', 'users_exp_code', '1', '1', '', null, 'users_id', 'users_id', '0', '文本框', '', '0', '1', '0', '1', '1', '1', '1', '0', '0', '', null, null, '', '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2098', 'users_exp_code', '1', '2', '', null, 'exp', '经验值', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '0', null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2099', 'users_exp_code', '1', '3', '', null, 'avg', '年龄', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '0', null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2100', 'users_exp_code', '1', '4', '', null, 'qq', 'QQ', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2101', 'users_item_code', '1', '1', '', null, 'id', 'ID', '1', '自增框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '50', '20', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2102', 'users_item_code', '1', '2', '', null, 'users_id', '艺人', '0', '查找框', 'select id UID,login_id CN from users where <%if(user.id != 0){%>  id > ${user.id}<%}%> order by id desc', '1', '1', '0', '0', '1', '1', '1', '0', '0', null, null, null, 'function(value,row,index,field){return\'<a target=\"_blank\" href=\"http://g.cn\" style=\"color:blue\">\'+value+\'</a>\'}', '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2103', 'users_item_code', '1', '3', '', null, 'item_id', '道具', '0', '下拉框', 'select id ID,name CN from item where 1=1;ds=main', '1', '1', '0', '0', '1', '1', '1', '0', '0', null, null, null, null, '300', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2104', 'users_item_code', '1', '4', '', '', 'my_fun', '操作', '0', '文本框', '', '0', '1', '0', '0', '0', '0', '0', '0', '0', '', null, '', 'function(value,row,index,field){return\'<a href=\"/form/add/users_item_code\" style=\"color:blue\">添加</a> <a href=\"/form/update/users_item_code-\'+row.id+\'\" style=\"color:blue\">修改</a>\'}', '130', '20', null, '0', '0', '0', 'int', '1', '0');
INSERT INTO `eova_field` VALUES ('2115', 'data_login', '1', '1', '', null, 'id', 'id', '1', '自增框', null, '0', '0', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '50', '20', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2116', 'data_login', '1', '2', '', null, 'day', '日期', '0', '日期框', null, '1', '1', '0', '1', '1', '1', '1', '1', '0', null, null, null, null, '130', '20', null, '0', '0', '91', 'DATE', '10', '0');
INSERT INTO `eova_field` VALUES ('2117', 'data_login', '1', '3', '', null, 'num', '每日登录数', '0', '数字框', null, '1', '1', '0', '1', '1', '1', '1', '1', '0', null, null, null, null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2132', 'user_info_code', '1', '1', '基本信息', null, 'id', 'id', '0', '查找框', 'select id ID,login_id 帐号 from eova_user;ds=eova', '0', '0', '0', '1', '1', '1', '1', '0', '0', '', null, null, null, '130', '20', null, '20', '20', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2133', 'user_info_code', '1', '2', '基本信息', null, 'rid', '冗余角色ID', '0', '文本框', '', '0', '0', '0', '0', '0', '0', '0', '0', '0', null, null, '0', null, '130', '20', null, '50', '50', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2134', 'user_info_code', '1', '5', '基本信息', null, 'status', '状态', '0', '下拉框', 'select value ID , name CN from dicts where object = \'users\' and field = \'status\' or object = \'${user.id}\'', '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '0', null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2135', 'user_info_code', '1', '6', '基本信息', null, 'nickname', '昵称', '0', '文本框', '', '0', '1', '0', '1', '1', '1', '1', '1', '0', '', null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '30', '0');
INSERT INTO `eova_field` VALUES ('2136', 'user_info_code', '1', '7', '基本信息', null, 'mobile', '联系手机', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, null, null, '130', '20', null, '0', '0', '12', 'VARCHAR', '11', '0');
INSERT INTO `eova_field` VALUES ('2137', 'user_info_code', '2', '8', '地区信息', null, 'province', '省', '0', '下拉框', 'select id ID,name CN from area where lv = 1', '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, null, null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2138', 'user_info_code', '2', '9', '地区信息', null, 'city', '市', '0', '下拉框', 'select id ID,name CN from area where lv = 2', '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, null, null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2139', 'user_info_code', '2', '10', '地区信息', null, 'region', '区', '0', '下拉框', 'select id ID,name CN from area where lv = 3', '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, null, null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2140', 'user_info_code', '1', '30', '基本信息', null, 'create_time', '创建时间', '0', '时间框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '50', '10', '93', 'TIMESTAMP', '19', '0');
INSERT INTO `eova_field` VALUES ('2388', 'test_info', '0', '2', '基础信息', null, 'id', 'ID', '1', '自增框', null, '1', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '50', '20', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2389', 'test_info', '0', '2', '基础信息', null, 'user_id', '用户', '0', '查找框', 'select id ID,login_id 帐号 from eova_user;ds=eova', '1', '1', '0', '1', '1', '1', '1', '1', '1', null, null, null, null, '130', '20', null, '0', '10', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2390', 'test_info', '0', '3', '基础信息', null, 'status', '状态', '0', '下拉框', 'select value ID,name CN from dicts where object = \'test_info\' and field = \'status\'', '1', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '0', null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2391', 'test_info', '0', '4', '基础信息', null, 'name', '姓名', '0', '文本框', '', '1', '1', '0', '1', '1', '1', '1', '1', '0', '', null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2392', 'test_info', '0', '5', '基础信息', null, 'age', '年龄', '0', '数字框', null, '1', '1', '0', '1', '1', '1', '1', '1', '0', null, null, null, '', '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2393', 'test_info', '2', '60', '详细信息', null, 'memo', '备注', '0', '编辑框', null, '1', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '200', null, '0', '0', '-1', 'TEXT', '65535', '0');
INSERT INTO `eova_field` VALUES ('2394', 'test_info', '0', '3', '基础信息', null, 'avatar', '头像', '0', '多图框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, 'function(value,row,index,field){if(value){var s=\'\';$.each(value.split(\',\'),function(i,o){s=s+\'<img src=\"\'+IMG+\'/avatar/\'+o+\'\" height=25>\';});return s;}return value}', '130', '50', '{\"filedir\":\"/avatar\",\"memo\":\"请选择图片上传,支持格式:.png/.jpg/.bmp\"}', '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2395', 'test_info', '1', '8', '操作时间', null, 'update_time', '更新日期', '0', '日期框', null, '1', '1', '0', '1', '1', '1', '1', '0', '0', null, null, 'NOW', null, '130', '20', null, '10', '10', '91', 'DATE', '10', '0');
INSERT INTO `eova_field` VALUES ('2396', 'test_info', '1', '9', '操作时间', '', 'create_time', '创建时间', '0', '时间框', null, '1', '1', '0', '1', '1', '1', '1', '0', '0', null, null, 'NOW', null, '130', '20', null, '10', '50', '93', 'TIMESTAMP', '19', '0');
INSERT INTO `eova_field` VALUES ('2397', 'test_info', '2', '12', '详细信息', null, 'delete_flag', '是否删除', '0', '布尔框', null, '1', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '0', '0', '-7', 'BIT', '0', '0');
INSERT INTO `eova_field` VALUES ('2398', 'test_info', '2', '10', '详细信息', null, 'address', '详细地址', '0', '文本域', null, '1', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '40', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2399', 'test_info', '2', '10', '详细信息', null, 'id_card', '身份证图片', '0', '文件框', null, '1', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2400', 'test_info', '2', '10', '详细信息', null, 'password', '密码', '0', '密码框', null, '1', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '0', '50', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2401', 'test_info', '2', '10', '详细信息', null, 'color', '颜值', '0', '颜色框', null, '1', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '0', '0', '12', 'VARCHAR', '10', '0');
INSERT INTO `eova_field` VALUES ('2624', 'test_info', '0', '4', '基础信息', null, 'tag', '标签', '0', '复选框', 'select value ID,name CN from dicts where object = \'test_info\' and field = \'tag\';cache=sys', '1', '1', '0', '1', '1', '1', '1', '0', '1', '不推荐使用此法，使用下拉框吧！', null, null, null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2628', 'test_info', '3', '19', '测试信息', null, 'test4', 'test4', '0', '文本框', null, '0', '0', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '0', '0', '-7', 'BIT', '0', '0');
INSERT INTO `eova_field` VALUES ('2629', 'test_info', '3', '20', '测试信息', null, 'test5', 'test5', '0', '文本框', null, '0', '0', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '0', '0', '-5', 'BIGINT UNSIGNED', '20', '0');
INSERT INTO `eova_field` VALUES ('2630', 'test_info', '3', '21', '测试信息', null, 'test6', 'test6', '0', '文本框', null, '0', '0', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '0', '0', '-6', 'TINYINT UNSIGNED', '3', '0');
INSERT INTO `eova_field` VALUES ('2631', 'test_info', '3', '16', '测试信息', null, 'test1', '下拉树', '0', '下拉树', 'select id ID,parent_id PID, name NAME from eova_menu;ds=eova;cache=sys', '0', '0', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '0', '0', '4', 'INT UNSIGNED', '10', '0');
INSERT INTO `eova_field` VALUES ('2632', 'test_info', '3', '17', '测试信息', null, 'test2', 'test2', '0', '文本框', null, '0', '0', '0', '1', '1', '1', '1', '0', '0', null, null, null, null, '130', '20', null, '0', '0', '7', 'FLOAT', '12', '0');
INSERT INTO `eova_field` VALUES ('2633', 'test_info', '0', '5', '测试信息', null, 'test3', '颜值', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, null, 'function(value,row,index,field){if(value){var s=\'<div style=\"width:100%;border:1px solid #ccc\">\'+\'<div style=\"width:\'+value+\'%;background:#cc0000;color:#fff\">\'+value+\'%\'+\'</div></div>\';return s;}else{return \'\';}}', '130', '20', null, '50', '50', '8', 'DOUBLE', '22', '0');
INSERT INTO `eova_field` VALUES ('2634', 'data_login', '1', '3', '', null, 'num1', '每日活跃数', '0', '数字框', null, '1', '1', '0', '1', '1', '1', '1', '1', '0', null, null, null, null, '130', '20', null, '0', '0', '0', 'INT', '1', '0');
INSERT INTO `eova_field` VALUES ('2745', 'links', '0', '1', '', null, 'id', 'ID', '1', '自增框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '', null, '136', '20', null, '50', '20', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2746', 'links', '0', '2', '', null, 'status', '状态', '0', '下拉框', 'select value ID,name CN from dicts where object = \'links\' and field = \'status\'', '1', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '1', null, '136', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2747', 'links', '0', '3', '', null, 'name', '链接文本', '0', '文本域', null, '1', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '', null, '136', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2748', 'links', '0', '4', '', null, 'url', '链接地址', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, 'http://www..com', null, '154', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2749', 'links', '0', '5', '', null, 'title', '小标题', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '216', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2761', 'data_money', '0', '1', '', null, 'id', 'id', '1', '自增框', null, '0', '0', '0', '1', '1', '1', '1', '0', '0', null, null, '', null, '130', '20', null, '50', '20', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2762', 'data_money', '0', '2', '', null, 'moon', '月份', '0', '文本框', null, '1', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '11', '0');
INSERT INTO `eova_field` VALUES ('2763', 'data_money', '0', '3', '', null, 'num', '手机销售额', '0', '文本框', null, '1', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2764', 'data_money', '0', '4', '', null, 'num1', '电脑销售额', '0', '文本框', null, '1', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2765', 'data_money', '0', '5', '', null, 'num2', '套子销售额', '0', '文本框', null, '1', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2766', 'test_info', '3', '22', '测试信息', null, 'test7', 'datetime', '0', '时间框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '', null, '130', '20', null, '0', '0', '93', 'DATETIME', '19', '0');
INSERT INTO `eova_field` VALUES ('2818', 'v_orders', '0', '1', '订单信息', 'orders', 'id', '编号', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '0', null, '130', '20', null, '50', '10', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2819', 'v_orders', '0', '2', '订单信息', 'orders', 'state', '订单状态', '0', '文本框', null, '1', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '10', null, '130', '20', null, '0', '0', '4', 'INT UNSIGNED', '10', '0');
INSERT INTO `eova_field` VALUES ('2820', 'v_orders', '0', '3', '订单信息', 'orders', 'money', '应付金额', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '0.00', null, '130', '20', null, '0', '0', '8', 'DOUBLE UNSIGNED', '10', '2');
INSERT INTO `eova_field` VALUES ('2821', 'v_orders', '0', '4', '订单信息', 'orders', 'memo', '备注', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '256', '0');
INSERT INTO `eova_field` VALUES ('2822', 'v_orders', '2', '5', '买家信息', 'orders', 'create_user_id', '创建用户ID', '0', '查找框', 'select id ID,nickname 昵称 from users', '1', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '100', null, '130', '20', null, '50', '10', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2823', 'v_orders', '0', '6', '订单信息', 'orders', 'create_time', '创建时间', '0', '时间框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, 'CURRENT_TIMESTAMP', null, '130', '20', null, '50', '50', '93', 'DATETIME', '19', '0');
INSERT INTO `eova_field` VALUES ('2824', 'v_orders', '1', '7', '收货人', 'orders', 'address_id', '收货地址ID', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', '', null, '100', null, '130', '20', null, '50', '10', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2825', 'v_orders', '1', '8', '收货人', 'address', 'name', '收件人', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2826', 'v_orders', '1', '9', '收货人', 'address', 'full', '详细地址', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2827', 'v_orders', '1', '10', '收货人', 'address', 'mobilephone', '收件人手机', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2828', 'v_orders', '2', '11', '买家信息', 'users', 'login_id', '买家帐号', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '10', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2829', 'v_orders', '2', '12', '买家信息', 'users', 'nickname', '买家昵称', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '10', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2830', 'v_orders', '2', '13', '买家信息', 'users', 'info', '备注', '0', '文本域', null, '0', '0', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2956', 'v_users', '0', '1', '用户信息', 'users', 'id', 'id', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '0', null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2957', 'v_users', '0', '2', '用户信息', 'users', 'status', '状态', '0', '文本框', null, '1', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '0', null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2958', 'v_users', '0', '3', '用户信息', 'users', 'login_id', '登录账户', '0', '文本域', null, '1', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2959', 'v_users', '0', '4', '用户信息', 'users', 'login_pwd', '录登密码', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2960', 'v_users', '0', '5', '用户信息', 'users', 'nickname', '昵称', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2961', 'v_users', '0', '6', '用户信息', 'users', 'reg_time', '注册时间', '0', '时间框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '', null, '130', '20', null, '0', '0', '93', 'TIMESTAMP', '19', '0');
INSERT INTO `eova_field` VALUES ('2962', 'v_users', '0', '7', '用户信息', 'users', 'info', '备注', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2963', 'v_users', '0', '8', '用户信息', 'users', 'tag', '标签', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2964', 'v_users', '2', '9', '拓展信息', 'users_exp', 'exp', '经验值', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '0', null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2965', 'v_users', '2', '10', '拓展信息', 'users_exp', 'avg', '年龄', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '0', null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2966', 'v_users', '2', '11', '拓展信息', 'users_exp', 'qq', 'QQ', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, 'qq;', '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2975', 'area', '0', '1', '', null, 'id', 'ID', '0', '文本框', null, '0', '0', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '50', '20', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2976', 'area', '0', '2', '', null, 'name', '名称', '0', '文本框', null, '1', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '50', '0');
INSERT INTO `eova_field` VALUES ('2977', 'area', '0', '4', '', null, 'pid', '父级', '0', '文本框', '', '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '10', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2978', 'area', '0', '3', '', null, 'lv', '级别', '0', '下拉框', 'select value ID,name CN from dicts where object = \'area\' and field = \'lv\'', '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2979', 'area_city', '0', '1', '', null, 'id', 'ID', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2980', 'area_city', '0', '2', '', null, 'name', '名称', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '50', '0');
INSERT INTO `eova_field` VALUES ('2981', 'area_city', '0', '3', '', null, 'code', '父级编码', '0', '查找框', 'SELECT id ID, name 名称 FROM area', '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '11', '0');
INSERT INTO `eova_field` VALUES ('2982', 'sale_data', '0', '1', '', null, 'id', 'ID', '1', '自增框', null, '0', '0', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '50', '20', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2983', 'sale_data', '0', '2', '', null, 'city_id', '城市ID', '0', '数字框', null, '0', '0', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '10', '10', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('2984', 'sale_data', '0', '3', '', null, 'name', '商品', '0', '文本框', null, '1', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('2985', 'sale_data', '0', '4', '', null, 'money', '销售额', '0', '数字框', null, '1', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '0', null, '130', '20', null, '0', '0', '8', 'DOUBLE', '22', '0');
INSERT INTO `eova_field` VALUES ('2992', 'test_info', '2', '61', '详细信息', null, 'json', '配置信息', '0', 'JSON框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '500', '0');
INSERT INTO `eova_field` VALUES ('3129', 'v_orders', '0', '5', '订单信息', 'orders', 'update_user_id', '更新用户ID', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '50', '10', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('3130', 'v_orders', '0', '8', '订单信息', 'orders', 'update_time', '更新时间', '0', '时间框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, '', null, '130', '20', null, '50', '10', '93', 'DATETIME', '19', '0');
INSERT INTO `eova_field` VALUES ('3131', 'data_money', '0', '6', '', 'virtual', 'total', '销售总额(虚拟字段)', '0', '文本框', '', '0', '1', '0', '0', '0', '0', '0', '0', '0', null, null, '', 'function(value,row,index,field){return row.num+row.num1+row.num2;}', '130', '20', null, '0', '0', '4', 'INT', '10', '0');
INSERT INTO `eova_field` VALUES ('3148', 'test01', '0', '10', '', null, 'uuid', 'UUID', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, null, 'UUID', null, '258', '20', null, '20', '0', '12', 'VARCHAR', '36', '0');
INSERT INTO `eova_field` VALUES ('3149', 'test01', '0', '20', '', null, 'name', '备注', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '1', '1', '0', null, 'remote(/test/remote)', 'SB666', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('3150', 'test01', '0', '30', '', null, 'time', '时间', '0', '时间框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, 'CURRENT_TIMESTAMP', null, '130', '20', null, '10', '0', '93', 'TIMESTAMP', '19', '0');
INSERT INTO `eova_field` VALUES ('3151', 'dicts', '0', '999', '', 'virtual', 'v_object_code', '对象编码', '0', '文本框', '', '1', '0', '0', '1', '1', '1', '1', '1', '0', '', '', '', 'function(value,row,index,field){return value}', '130', '20', '', '50', '50', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('3157', 'eova_user_code', '0', '50', '', null, 'name', '姓名', '0', '文本框', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', null, null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '50', '0');
INSERT INTO `eova_field` VALUES ('3158', 'eova_user_code', '0', '60', '', null, 'memo', '备注', '0', '文本域', null, '0', '0', '0', '1', '1', '1', '1', '0', '0', null, null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '100', '0');
INSERT INTO `eova_field` VALUES ('3159', 'test_info', '0', '999', '基础信息', 'virtual', 'v_year', '出生年份', '0', '文本框', '', '1', '1', '0', '0', '1', '1', '0', '1', '0', '', '', '', 'function(value,row,index,field){return new Date().getFullYear()-row.age;}', '130', '20', '', '50', '50', '12', 'VARCHAR', '255', '0');
INSERT INTO `eova_field` VALUES ('3161', 'eova_button_code', '0', '15', '', null, 'uri', '服务端URI入口', '0', '文本域', null, '0', '1', '0', '1', '1', '1', '1', '0', '0', '按钮模版的服务端处理入口的URI', null, '', null, '130', '20', null, '0', '0', '12', 'VARCHAR', '100', '0');

-- ----------------------------
-- Table structure for eova_log
-- ----------------------------
DROP TABLE IF EXISTS `eova_log`;
CREATE TABLE `eova_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) NOT NULL COMMENT '操作用户',
  `type` int(11) NOT NULL COMMENT '日志类型',
  `ip` varchar(255) NOT NULL COMMENT '操作IP',
  `info` varchar(500) DEFAULT NULL COMMENT '操作详情',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='EOVA操作日志';

-- ----------------------------
-- Table structure for eova_menu
-- ----------------------------
DROP TABLE IF EXISTS `eova_menu`;
CREATE TABLE `eova_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL COMMENT '编码',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `type` varchar(20) NOT NULL COMMENT '菜单类型',
  `iconskip` varchar(255) DEFAULT NULL COMMENT '图标',
  `order_num` int(11) DEFAULT '0' COMMENT '序号',
  `parent_id` int(11) DEFAULT '0' COMMENT '父节点',
  `open` tinyint(1) DEFAULT '1' COMMENT '是否展开',
  `biz_intercept` varchar(255) DEFAULT NULL COMMENT '自定义业务拦截器',
  `url` varchar(255) DEFAULT NULL COMMENT '自定义URL',
  `config` varchar(500) DEFAULT NULL COMMENT '菜单配置JSON',
  `diy_js` varchar(255) DEFAULT NULL COMMENT '依赖JS文件',
  `is_hide` tinyint(1) DEFAULT '0' COMMENT '是否隐藏',
  `filter` varchar(500) DEFAULT NULL COMMENT '初始数据过滤条件',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1062 DEFAULT CHARSET=utf8 COMMENT='EOVA菜单';

-- ----------------------------
-- Records of eova_menu
-- ----------------------------
INSERT INTO `eova_menu` VALUES ('1', 'eova', '平台维护', 'dir', 'eova-icon169', '3', '0', '0', null, null, null, null, '0', null);
INSERT INTO `eova_menu` VALUES ('3', 'biz', '综合业务', 'dir', 'eova-icon877', '1', '0', '1', '', null, '', '', '0', '');
INSERT INTO `eova_menu` VALUES ('20', 'eova_menu', '菜单管理', 'single_tree', 'eova-icon43', '30', '1', '1', null, null, '{\"objectCode\":\"eova_menu_code\",\"tree\":{\"iconField\":\"iconskip\",\"parentField\":\"parent_id\",\"treeField\":\"name\",\"idField\":\"id\",\"rootPid\":\"0\"}}', null, '0', null);
INSERT INTO `eova_menu` VALUES ('21', 'eova_button', '按钮管理', 'tree_grid', 'eova-icon169', '40', '1', '1', '', null, '{\"objectCode\":\"eova_button_code\",\"objectField\":\"menu_code\",\"tree\":{\"iconField\":\"iconskip\",\"idField\":\"id\",\"objectCode\":\"eova_menu_code\",\"objectField\":\"code\",\"parentField\":\"parent_id\",\"treeField\":\"name\",\"rootPid\":\"0\"}}', '', '0', '');
INSERT INTO `eova_menu` VALUES ('22', 'eova_object', '元数据管理', 'master_slave_grid', 'eova-icon395', '50', '1', '1', null, null, '{\"fields\":[\"object_code\"],\"objectCode\":\"eova_object_code\",\"objectField\":\"code\",\"objects\":[\"eova_field_code\"]}', null, '0', null);
INSERT INTO `eova_menu` VALUES ('24', 'eova_dict', '字典管理', 'single_grid', 'eova-icon145', '1', '1', '1', null, '', '{\"objectCode\":\"dicts\",\"params\":{}}', null, '0', null);
INSERT INTO `eova_menu` VALUES ('25', 'eova_task', '定时调度', 'single_grid', 'eova-icon280', '60', '1', '1', null, '', '{\"objectCode\":\"eova_task_code\"}', null, '0', null);
INSERT INTO `eova_menu` VALUES ('26', 'eova_code', '神器·宝箱', 'diy', 'eova-icon157', '70', '1', '1', '', '/code', '{}', '', '0', '');
INSERT INTO `eova_menu` VALUES ('900', 'sys', '系统管理', 'dir', 'eova-icon294', '1', '0', '0', null, null, null, null, '0', null);
INSERT INTO `eova_menu` VALUES ('901', 'sys_users', '用户管理', 'single_grid', 'eova-icon518', '0', '900', '1', '', '', '{\"objectCode\":\"eova_user_code\",\"params\":{}}', '', '0', '');
INSERT INTO `eova_menu` VALUES ('902', 'sys_auth_role', '角色管理', 'single_grid', 'eova-icon525', '2', '900', '1', '', null, '{\"objectCode\":\"eova_role_code\"}', '', '0', '');
INSERT INTO `eova_menu` VALUES ('903', 'sys_log', '系统日志', 'single_grid', 'eova-icon1058', '3', '900', '1', '', null, '{\"objectCode\":\"eova_log_code\"}', '', '0', '');
INSERT INTO `eova_menu` VALUES ('1000', 'biz_demo', '功能演示', 'dir', 'eova-icon145', '2', '3', '1', '', null, '', '', '0', '');
INSERT INTO `eova_menu` VALUES ('1001', 'biz_demo_users', '单表CRUD', 'single_grid', 'eova-icon49', '2', '1000', '1', '', '', '{\"objectCode\":\"player_code\",\"params\":{\"width\":600,\"height\":500}}', '', '0', '');
INSERT INTO `eova_menu` VALUES ('1005', 'biz_demo_userscell', '表格单元格编辑', 'single_grid', 'eova-icon294', '5', '1000', '1', '', null, '{\"objectCode\":\"celledit_users_code\"}', '', '0', '');
INSERT INTO `eova_menu` VALUES ('1006', 'biz_demo_import', '导入导出', 'single_grid', 'eova-icon84', '6', '1000', '1', '', null, '{\"objectCode\":\"player_code\"}', '', '0', '');
INSERT INTO `eova_menu` VALUES ('1007', 'biz_users_exp', '非自增主键拓展表', 'single_grid', 'eova-icon22', '7', '1000', '1', '', '', '{\"objectCode\":\"users_exp_code\"}', '/ui/js/diy/test.js', '0', '');
INSERT INTO `eova_menu` VALUES ('1009', 'biz_product', '产品管理', 'single_grid', 'eova-icon160', '8', '1000', '1', '', '', '{\"objectCode\":\"product\"}', '', '0', '');
INSERT INTO `eova_menu` VALUES ('1010', 'biz_hotelzz', '酒店管理(主子)', 'master_slave_grid', 'eova-icon182', '9', '1000', '1', '', '', '{\"fields\":[\"hotel_id\",\"hotel_id\"],\"objectCode\":\"hotel\",\"objectField\":\"id\",\"objects\":[\"hotel_bed\",\"hotel_stock\"],\"params\":{\"width\":620,\"height\":300}}', '', '0', '');
INSERT INTO `eova_menu` VALUES ('1011', 'biz_caidan', '报表管理', 'dir', 'eova-icon271', '50', '1000', '1', '', '', '{}', '', '0', '');
INSERT INTO `eova_menu` VALUES ('1012', 'biz_4j', '只显示有子功能的目录', 'dir', 'eova-icon482', '0', '1000', '1', '', '', '{}', '', '1', '');
INSERT INTO `eova_menu` VALUES ('1013', 'biz_4j_test1', '没子的目录不显示', 'single_grid', 'icon-applicationosxcascade', '99', '1012', '1', '', '', '{\"objectCode\":\"player_code\"}', null, '1', null);
INSERT INTO `eova_menu` VALUES ('1015', 'biz_data_login', '每日登录用户数', 'single_chart', 'eova-icon271', '1', '1011', '1', '', '', '{\"objectCode\":\"data_login\",\"chart\":{\"x\":\"day\",\"y\":[\"num\",\"num1\"],\"ycn\":[\"每日登陆数\",\"每日活跃数\"],\"yunit\":\"人\"}}', '', '0', '');
INSERT INTO `eova_menu` VALUES ('1029', 'biz_demo_eova_all', 'EOVA控件', 'single_grid', 'eova-icon11', '6', '3', '1', '', '', '{\"objectCode\":\"test_info\"}', '/ui/js/diy/focus.js', '0', '');
INSERT INTO `eova_menu` VALUES ('1035', 'biz_demo_links', '友情链接管理', 'single_grid', 'eova-icon33', '10', '1000', '1', '', '', '{\"objectCode\":\"links\"}', '', '0', '');
INSERT INTO `eova_menu` VALUES ('1040', 'biz_demo_money', '销售额报表', 'single_chart', 'eova-icon271', '1', '1011', '1', '', '', '{\"chart\":{\"x\":\"moon\",\"y\":[\"num\",\"num1\",\"num2\"],\"ycn\":[\"手机销售额\",\"电脑销售额\",\"套子销售额\"],\"yunit\":\"元\"},\"objectCode\":\"data_money\"}', '', '0', '');
INSERT INTO `eova_menu` VALUES ('1044', 'biz_demo_views_orders', '订单管理', 'single_grid', 'eova-icon49', '1', '1046', '1', '', '', '{\"objectCode\":\"v_orders\"}', '', '0', '');
INSERT INTO `eova_menu` VALUES ('1046', 'biz_demo_views', '多表视图', 'dir', 'eova-icon21', '51', '1000', '0', '', '', '{}', '', '0', '');
INSERT INTO `eova_menu` VALUES ('1047', 'biz_demo_views_users', '多表用户信息', 'single_grid', 'eova-icon1121', '1', '1046', '1', '', '', '{\"objectCode\":\"v_users\"}', '', '0', '');
INSERT INTO `eova_menu` VALUES ('1048', 'biz_demo_area', '地区树管理', 'single_tree', 'eova-icon43', '11', '1000', '1', '', '', '{\"objectCode\":\"area\",\"tree\":{\"iconField\":\"\",\"idField\":\"id\",\"parentField\":\"pid\",\"treeField\":\"name\",\"rootPid\":\"0\"}}', '', '0', '');
INSERT INTO `eova_menu` VALUES ('1049', 'biz_demo_tree_code', '父字符串树', 'single_tree', 'eova-icon43', '12', '1000', '1', '', '', '{\"objectCode\":\"area_city\",\"tree\":{\"iconField\":\"\",\"idField\":\"id\",\"parentField\":\"code\",\"treeField\":\"name\",\"rootPid\":\"0\"}}', '', '0', '');
INSERT INTO `eova_menu` VALUES ('1050', 'biz_demo_sale', '全国销售数据', 'tree_grid', 'eova-icon129', '13', '1000', '1', '', '', '{\"objectCode\":\"sale_data\",\"objectField\":\"city_id\",\"tree\":{\"iconField\":\"\",\"idField\":\"id\",\"objectCode\":\"area_city\",\"parentField\":\"code\",\"rootPid\":\"0\",\"treeField\":\"name\",\"objectField\":\"id\"}}', '', '0', '');
INSERT INTO `eova_menu` VALUES ('1053', 'biz_demo_hotel', '酒店管理(关联)', 'single_grid', 'eova-icon182', '9', '1000', '1', '', '', '{\"objectCode\":\"hotel\"}', '', '0', '');
INSERT INTO `eova_menu` VALUES ('1054', 'biz_demo_hotel_stock', '酒店库存管理', 'single_grid', 'eova-icon182', '9', '1000', '1', '', '', '{\"objectCode\":\"hotel_stock\"}', '', '0', '');
INSERT INTO `eova_menu` VALUES ('1056', 'biz_office_xls1', '高新园评分表', 'office', 'eova-icon779', '2', '1011', '1', 'com.oss.office.xls.Xls1Intercept', '/office/xls/xls1.html', '{\"params\":{\"office_type\":\"xls\"}}', '', '0', '');
INSERT INTO `eova_menu` VALUES ('1057', 'biz_office_doc1', '高新园检查表', 'office', 'eova-icon820', '4', '1011', '1', 'com.oss.office.doc.Doc1Intercept', '/office/doc/doc1.html', '{\"params\":{\"office_type\":\"doc\"}}', '', '0', '');
INSERT INTO `eova_menu` VALUES ('1060', 'test01', 'UUID和远程校验', 'single_grid', 'eova-icon1', '1', '1000', '1', '', '', '{\"objectCode\":\"test01\",\"params\":{}}', '', '0', '');
INSERT INTO `eova_menu` VALUES ('1061', 'eova_druid', '性能监控', 'diy', 'eova-icon473', '1', '1', '1', null, '/druid', '{\"params\":{}}', null, '0', null);
-- ----------------------------
-- Table structure for eova_object
-- ----------------------------
DROP TABLE IF EXISTS `eova_object`;
CREATE TABLE `eova_object` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(100) NOT NULL COMMENT '对象编码',
  `name` varchar(100) NOT NULL COMMENT '对象名称',
  `view_name` varchar(255) DEFAULT NULL COMMENT '查询数据视图',
  `table_name` varchar(255) DEFAULT NULL COMMENT '保存数据主表',
  `pk_name` varchar(50) DEFAULT NULL COMMENT '主键',
  `data_source` varchar(50) DEFAULT 'main' COMMENT '数据源',
  `is_single` tinyint(1) DEFAULT '1' COMMENT '是否单选',
  `is_celledit` tinyint(1) DEFAULT '0' COMMENT '是否可行内编辑',
  `is_show_num` tinyint(1) DEFAULT '1' COMMENT '是否显示行号',
  `is_first_load` tinyint(1) DEFAULT '1' COMMENT '是否初始加载',
  `filter` varchar(500) DEFAULT NULL COMMENT '初始数据过滤条件',
  `default_order` varchar(255) DEFAULT NULL COMMENT '默认排序字段(desc)',
  `diy_card` varchar(255) DEFAULT NULL COMMENT '自定义卡片面板',
  `diy_js` varchar(255) DEFAULT NULL COMMENT '依赖JS文件',
  `biz_intercept` varchar(255) DEFAULT NULL COMMENT '自定义业务拦截器',
  `view_sql` varchar(1000) DEFAULT NULL COMMENT '视图SQL',
  `config` varchar(2000) DEFAULT NULL COMMENT '拓展配置',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1130 DEFAULT CHARSET=utf8 COMMENT='EOVA元对象';

-- ----------------------------
-- Records of eova_object
-- ----------------------------
INSERT INTO `eova_object` VALUES ('1', 'meta_template', '元对象模版数据', null, 'eova_template', 'id', 'eova', '1', '0', '1', '1', null, null, null, null, '', null, null);
INSERT INTO `eova_object` VALUES ('2', 'eova_menu_code', '菜单', '', 'eova_menu', 'id', 'eova', '0', '0', '1', '1', '', 'parent_id,order_num', null, '', 'com.eova.core.menu.MenuIntercept', '', '');
INSERT INTO `eova_object` VALUES ('3', 'eova_object_code', '对象', '', 'eova_object', 'id', 'eova', '0', '0', '1', '1', 'and id > 999', 'id desc', null, '', 'com.eova.core.object.ObjectIntercept', null, '');
INSERT INTO `eova_object` VALUES ('4', 'eova_field_code', '字段', '', 'eova_field', 'id', 'eova', '1', '1', '1', '1', '', 'fieldnum,order_num', null, '', '', null, null);
INSERT INTO `eova_object` VALUES ('5', 'eova_button_code', '按钮', '', 'eova_button', 'id', 'eova', '0', '0', '1', '1', 'and id > 999 and is_base = 0', 'id desc', null, '', '', null, '');
INSERT INTO `eova_object` VALUES ('6', 'eova_user_code', '用户', '', 'eova_user', 'id', 'eova', '1', '0', '1', '1', 'and rid in (select id from eova_role where lv > ${user.role.lv})', 'id desc', null, '', 'com.eova.user.UserIntercept', null, '');
INSERT INTO `eova_object` VALUES ('7', 'eova_role_code', '角色管理', '', 'eova_role', 'id', 'eova', '1', '0', '1', '1', '<%if(user.role.lv != 0){%>\r\n${user.role.lv} < lv\r\n<%}%>', 'id desc', null, '', 'com.eova.core.role.RoleIntercept', '', '');
INSERT INTO `eova_object` VALUES ('8', 'eova_task_code', '定时调度', null, 'eova_task', 'id', 'eova', '1', '0', '1', '1', null, null, null, null, 'com.eova.core.task.TaskIntercept', null, null);
INSERT INTO `eova_object` VALUES ('9', 'eova_log_code', '操作日志', null, 'eova_log', 'id', 'eova', '1', '0', '1', '1', null, 'id desc', null, null, '', null, null);
INSERT INTO `eova_object` VALUES ('100', 'user_info_code', '用户详细信息', '', 'user_info', 'id', 'main', '1', '0', '1', '1', '', '', null, '/ui/js/diy/area.js', 'com.eova.user.UserInfoIntercept', null, null);
INSERT INTO `eova_object` VALUES ('1003', 'dicts', '业务字典', '', 'dicts', 'id', 'main', '1', '0', '1', '1', '', '', null, '', 'com.eova.core.dict.DictIntercept', '', '');
INSERT INTO `eova_object` VALUES ('1004', 'player_code', '玩家信息', null, 'users', 'id', 'main', '0', '0', '1', '1', '', 'id desc', null, '', '', null, null);
INSERT INTO `eova_object` VALUES ('1005', 'item_code', '道具', null, 'item', 'id', 'main', '1', '0', '1', '1', null, null, null, null, '', null, null);
INSERT INTO `eova_object` VALUES ('1006', 'users_item_code', '艺人关联道具', '', 'users_item', 'id', 'main', '1', '0', '1', '1', '', '', null, '', '', null, null);
INSERT INTO `eova_object` VALUES ('1008', 'celledit_users_code', '可编辑用户', null, 'users', 'id', 'main', '1', '1', '1', '1', '', '', null, '', '', null, null);
INSERT INTO `eova_object` VALUES ('1009', 'users_exp_code', '艺人信息拓展', null, 'users_exp', 'users_id', 'main', '1', '0', '1', '1', '', '', null, '', '', null, null);
INSERT INTO `eova_object` VALUES ('1010', 'hotel', '酒店', null, 'hotel', 'id', 'main', '1', '0', '1', '1', '', '', null, '/ui/js/diy/area.js', '', null, null);
INSERT INTO `eova_object` VALUES ('1011', 'order_item', '订单项', null, 'order_item', 'id', 'main', '1', '0', '1', '1', null, null, null, null, '', null, null);
INSERT INTO `eova_object` VALUES ('1012', 'payment', '支付', null, 'payment', 'id', 'main', '1', '0', '1', '1', null, null, null, null, '', null, null);
INSERT INTO `eova_object` VALUES ('1014', 'product', '产品', null, 'product', 'id', 'main', '1', '0', '1', '1', '', '', null, '', '', null, null);
INSERT INTO `eova_object` VALUES ('1015', 'hotel_bed', '酒店床位', '', 'hotel_bed', 'id', 'main', '1', '0', '1', '1', '', '', null, '', '', null, '');
INSERT INTO `eova_object` VALUES ('1016', 'orders', '订单管理', null, 'orders', 'id', 'main', '1', '0', '1', '0', null, null, null, null, '', null, null);
INSERT INTO `eova_object` VALUES ('1017', 'hotel_stock', '酒店存货', null, 'hotel_stock', 'id', 'main', '1', '0', '1', '1', null, null, null, null, '', null, null);
INSERT INTO `eova_object` VALUES ('1018', 'data_login', '每日登录用户统计', null, 'data_login', 'id', 'main', '1', '0', '1', '0', null, null, null, null, '', null, null);
INSERT INTO `eova_object` VALUES ('1053', 'test_info', 'EOVA全属性对象', '', 'test_info', 'id', 'main', '1', '0', '1', '1', '', '', null, '', 'com.oss.test.TestIntercept', null, '');
INSERT INTO `eova_object` VALUES ('1096', 'links', '友情链接', '', 'links', 'id', 'main', '1', '0', '1', '1', '', '', null, '/ui/js/diy/info/1.js', '', null, null);
INSERT INTO `eova_object` VALUES ('1098', 'data_money', '销售数据', null, 'data_money', 'id', 'main', '1', '0', '1', '1', null, null, null, null, '', null, null);
INSERT INTO `eova_object` VALUES ('1110', 'v_orders', '订单管理', 'v_orders', '', 'id', 'main', '1', '0', '1', '1', '', '', null, '', 'com.oss.order.OrderIntercept', 'select `o`.`id` AS `id`,`o`.`state` AS `state`,`o`.`money` AS `money`,`o`.`memo` AS `memo`,`o`.`create_user_id` AS `create_user_id`,`o`.`create_time` AS `create_time`,`o`.`address_id` AS `address_id`,`a`.`name` AS `name`,`a`.`full` AS `full`,`a`.`mobilephone` AS `mobilephone`,`u`.`login_id` AS `login_id`,`u`.`nickname` AS `nickname`,`u`.`info` AS `info` from ((`demo`.`orders` `o` left join `demo`.`users` `u` on((`o`.`create_user_id` = `u`.`id`))) left join `demo`.`address` `a` on((`o`.`address_id` = `a`.`id`)))', '{\"view\":{\"address\":{\"paramField\":\"address_id\",\"whereField\":\"id\"},\"orders\":{\"paramField\":\"id\",\"whereField\":\"id\"},\"users\":{\"paramField\":\"create_user_id\",\"whereField\":\"id\"}}}');
INSERT INTO `eova_object` VALUES ('1119', 'v_users', '用户视图', 'v_userinfo', '', 'id', 'main', '1', '0', '1', '1', '', '', null, '', '', 'select `u`.`id` AS `id`,`u`.`status` AS `status`,`u`.`login_id` AS `login_id`,`u`.`login_pwd` AS `login_pwd`,`u`.`nickname` AS `nickname`,`u`.`reg_time` AS `reg_time`,`u`.`info` AS `info`,`u`.`tag` AS `tag`,`ue`.`exp` AS `exp`,`ue`.`avg` AS `avg`,`ue`.`qq` AS `qq` from `demo`.`users` `u` join `demo`.`users_exp` `ue` where (`u`.`id` = `ue`.`users_id`)', '{\"view\":{\"users\":{\"whereField\":\"id\",\"paramField\":\"id\"},\"users_exp\":{\"whereField\":\"users_id\",\"paramField\":\"id\"}}}');
INSERT INTO `eova_object` VALUES ('1122', 'area', '省市区', '', 'area', 'id', 'main', '1', '0', '1', '1', 'lv < 3', '', null, '', '', null, '');
INSERT INTO `eova_object` VALUES ('1123', 'area_city', '全国各省', '', 'area_city', 'id', 'main', '1', '0', '1', '1', '', '', null, '', '', null, null);
INSERT INTO `eova_object` VALUES ('1124', 'sale_data', '销售数据', null, 'sale_data', 'id', 'main', '1', '0', '1', '1', null, null, null, null, '', null, null);
INSERT INTO `eova_object` VALUES ('1128', 'test01', '测试1', '', 'test01', 'uuid', 'main', '1', '0', '1', '1', '', 'time desc', null, '', '', '', '');

-- ----------------------------
-- Table structure for eova_role
-- ----------------------------
DROP TABLE IF EXISTS `eova_role`;
CREATE TABLE `eova_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '角色名',
  `info` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `lv` int(11) DEFAULT '0' COMMENT '权限级别',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='EOVA角色';

-- ----------------------------
-- Records of eova_role
-- ----------------------------
INSERT INTO `eova_role` VALUES ('1', '超级管理员', '开发者权限(禁止作为业务权限)', '0');
INSERT INTO `eova_role` VALUES ('2', '管理员', '管理所有可用功能', '10');
INSERT INTO `eova_role` VALUES ('3', '测试组长', '测试小组的组长', '20');
INSERT INTO `eova_role` VALUES ('4', '测试通用', '测试通用权限', '30');

-- ----------------------------
-- Table structure for eova_role_btn
-- ----------------------------
DROP TABLE IF EXISTS `eova_role_btn`;
CREATE TABLE `eova_role_btn` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rid` int(11) NOT NULL COMMENT '角色',
  `bid` int(11) NOT NULL COMMENT '功能',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1382 DEFAULT CHARSET=utf8 COMMENT='EOVA角色按钮';

-- ----------------------------
-- Records of eova_role_btn
-- ----------------------------
INSERT INTO `eova_role_btn` VALUES ('315', '4', '1070');
INSERT INTO `eova_role_btn` VALUES ('316', '4', '1049');
INSERT INTO `eova_role_btn` VALUES ('1147', '3', '116');
INSERT INTO `eova_role_btn` VALUES ('1148', '3', '112');
INSERT INTO `eova_role_btn` VALUES ('1149', '3', '113');
INSERT INTO `eova_role_btn` VALUES ('1150', '3', '110');
INSERT INTO `eova_role_btn` VALUES ('1151', '3', '111');
INSERT INTO `eova_role_btn` VALUES ('1152', '3', '1070');
INSERT INTO `eova_role_btn` VALUES ('1153', '3', '1049');
INSERT INTO `eova_role_btn` VALUES ('1330', '2', '116');
INSERT INTO `eova_role_btn` VALUES ('1331', '2', '112');
INSERT INTO `eova_role_btn` VALUES ('1332', '2', '113');
INSERT INTO `eova_role_btn` VALUES ('1333', '2', '110');
INSERT INTO `eova_role_btn` VALUES ('1334', '2', '111');
INSERT INTO `eova_role_btn` VALUES ('1335', '2', '1190');
INSERT INTO `eova_role_btn` VALUES ('1336', '2', '33');
INSERT INTO `eova_role_btn` VALUES ('1337', '2', '43');
INSERT INTO `eova_role_btn` VALUES ('1338', '2', '42');
INSERT INTO `eova_role_btn` VALUES ('1339', '2', '41');
INSERT INTO `eova_role_btn` VALUES ('1340', '2', '40');
INSERT INTO `eova_role_btn` VALUES ('1341', '2', '82');
INSERT INTO `eova_role_btn` VALUES ('1342', '2', '83');
INSERT INTO `eova_role_btn` VALUES ('1343', '2', '80');
INSERT INTO `eova_role_btn` VALUES ('1344', '2', '81');
INSERT INTO `eova_role_btn` VALUES ('1345', '2', '86');
INSERT INTO `eova_role_btn` VALUES ('1346', '2', '1188');
INSERT INTO `eova_role_btn` VALUES ('1347', '2', '87');
INSERT INTO `eova_role_btn` VALUES ('1348', '2', '1189');
INSERT INTO `eova_role_btn` VALUES ('1349', '2', '84');
INSERT INTO `eova_role_btn` VALUES ('1350', '2', '1134');
INSERT INTO `eova_role_btn` VALUES ('1351', '2', '1133');
INSERT INTO `eova_role_btn` VALUES ('1352', '2', '1135');
INSERT INTO `eova_role_btn` VALUES ('1353', '2', '22');
INSERT INTO `eova_role_btn` VALUES ('1354', '2', '23');
INSERT INTO `eova_role_btn` VALUES ('1355', '2', '25');
INSERT INTO `eova_role_btn` VALUES ('1356', '2', '129');
INSERT INTO `eova_role_btn` VALUES ('1357', '2', '26');
INSERT INTO `eova_role_btn` VALUES ('1358', '2', '27');
INSERT INTO `eova_role_btn` VALUES ('1359', '2', '28');
INSERT INTO `eova_role_btn` VALUES ('1360', '2', '29');
INSERT INTO `eova_role_btn` VALUES ('1361', '2', '3');
INSERT INTO `eova_role_btn` VALUES ('1362', '2', '1');
INSERT INTO `eova_role_btn` VALUES ('1363', '2', '7');
INSERT INTO `eova_role_btn` VALUES ('1364', '2', '30');
INSERT INTO `eova_role_btn` VALUES ('1365', '2', '6');
INSERT INTO `eova_role_btn` VALUES ('1366', '2', '32');
INSERT INTO `eova_role_btn` VALUES ('1367', '2', '4');
INSERT INTO `eova_role_btn` VALUES ('1368', '2', '31');
INSERT INTO `eova_role_btn` VALUES ('1369', '2', '9');
INSERT INTO `eova_role_btn` VALUES ('1370', '2', '8');
INSERT INTO `eova_role_btn` VALUES ('1371', '2', '1177');
INSERT INTO `eova_role_btn` VALUES ('1372', '2', '20');
INSERT INTO `eova_role_btn` VALUES ('1373', '2', '1132');
INSERT INTO `eova_role_btn` VALUES ('1374', '2', '1171');
INSERT INTO `eova_role_btn` VALUES ('1375', '2', '1172');
INSERT INTO `eova_role_btn` VALUES ('1376', '2', '1170');
INSERT INTO `eova_role_btn` VALUES ('1377', '2', '1173');
INSERT INTO `eova_role_btn` VALUES ('1378', '2', '45');
INSERT INTO `eova_role_btn` VALUES ('1379', '2', '44');
INSERT INTO `eova_role_btn` VALUES ('1380', '2', '90');
INSERT INTO `eova_role_btn` VALUES ('1381', '2', '88');
INSERT INTO `eova_role_btn` VALUES ('1609', '1', '1003');
INSERT INTO `eova_role_btn` VALUES ('1610', '1', '1004');
INSERT INTO `eova_role_btn` VALUES ('1611', '1', '1001');
INSERT INTO `eova_role_btn` VALUES ('1612', '1', '1002');
INSERT INTO `eova_role_btn` VALUES ('1613', '1', '1008');
INSERT INTO `eova_role_btn` VALUES ('1614', '1', '1091');
INSERT INTO `eova_role_btn` VALUES ('1615', '1', '1005');
INSERT INTO `eova_role_btn` VALUES ('1616', '1', '1108');
INSERT INTO `eova_role_btn` VALUES ('1617', '1', '1006');
INSERT INTO `eova_role_btn` VALUES ('1618', '1', '1109');
INSERT INTO `eova_role_btn` VALUES ('1619', '1', '1106');
INSERT INTO `eova_role_btn` VALUES ('1620', '1', '1093');
INSERT INTO `eova_role_btn` VALUES ('1621', '1', '1107');
INSERT INTO `eova_role_btn` VALUES ('1622', '1', '1092');
INSERT INTO `eova_role_btn` VALUES ('1623', '1', '33');
INSERT INTO `eova_role_btn` VALUES ('1624', '1', '1104');
INSERT INTO `eova_role_btn` VALUES ('1625', '1', '1095');
INSERT INTO `eova_role_btn` VALUES ('1626', '1', '1009');
INSERT INTO `eova_role_btn` VALUES ('1627', '1', '1190');
INSERT INTO `eova_role_btn` VALUES ('1628', '1', '1105');
INSERT INTO `eova_role_btn` VALUES ('1629', '1', '1094');
INSERT INTO `eova_role_btn` VALUES ('1630', '1', '1097');
INSERT INTO `eova_role_btn` VALUES ('1631', '1', '1103');
INSERT INTO `eova_role_btn` VALUES ('1632', '1', '1100');
INSERT INTO `eova_role_btn` VALUES ('1633', '1', '1099');
INSERT INTO `eova_role_btn` VALUES ('1634', '1', '1101');
INSERT INTO `eova_role_btn` VALUES ('1635', '1', '1098');
INSERT INTO `eova_role_btn` VALUES ('1636', '1', '1089');
INSERT INTO `eova_role_btn` VALUES ('1637', '1', '1110');
INSERT INTO `eova_role_btn` VALUES ('1638', '1', '43');
INSERT INTO `eova_role_btn` VALUES ('1639', '1', '42');
INSERT INTO `eova_role_btn` VALUES ('1640', '1', '41');
INSERT INTO `eova_role_btn` VALUES ('1641', '1', '40');
INSERT INTO `eova_role_btn` VALUES ('1642', '1', '1011');
INSERT INTO `eova_role_btn` VALUES ('1643', '1', '1188');
INSERT INTO `eova_role_btn` VALUES ('1644', '1', '1010');
INSERT INTO `eova_role_btn` VALUES ('1645', '1', '1189');
INSERT INTO `eova_role_btn` VALUES ('1646', '1', '1080');
INSERT INTO `eova_role_btn` VALUES ('1647', '1', '22');
INSERT INTO `eova_role_btn` VALUES ('1648', '1', '1084');
INSERT INTO `eova_role_btn` VALUES ('1649', '1', '23');
INSERT INTO `eova_role_btn` VALUES ('1650', '1', '1082');
INSERT INTO `eova_role_btn` VALUES ('1651', '1', '25');
INSERT INTO `eova_role_btn` VALUES ('1652', '1', '1081');
INSERT INTO `eova_role_btn` VALUES ('1653', '1', '26');
INSERT INTO `eova_role_btn` VALUES ('1654', '1', '1088');
INSERT INTO `eova_role_btn` VALUES ('1655', '1', '27');
INSERT INTO `eova_role_btn` VALUES ('1656', '1', '1087');
INSERT INTO `eova_role_btn` VALUES ('1657', '1', '28');
INSERT INTO `eova_role_btn` VALUES ('1658', '1', '1086');
INSERT INTO `eova_role_btn` VALUES ('1659', '1', '29');
INSERT INTO `eova_role_btn` VALUES ('1660', '1', '1085');
INSERT INTO `eova_role_btn` VALUES ('1661', '1', '3');
INSERT INTO `eova_role_btn` VALUES ('1662', '1', '1');
INSERT INTO `eova_role_btn` VALUES ('1663', '1', '1078');
INSERT INTO `eova_role_btn` VALUES ('1664', '1', '1079');
INSERT INTO `eova_role_btn` VALUES ('1665', '1', '30');
INSERT INTO `eova_role_btn` VALUES ('1666', '1', '7');
INSERT INTO `eova_role_btn` VALUES ('1667', '1', '6');
INSERT INTO `eova_role_btn` VALUES ('1668', '1', '32');
INSERT INTO `eova_role_btn` VALUES ('1669', '1', '31');
INSERT INTO `eova_role_btn` VALUES ('1670', '1', '4');
INSERT INTO `eova_role_btn` VALUES ('1671', '1', '9');
INSERT INTO `eova_role_btn` VALUES ('1672', '1', '1177');
INSERT INTO `eova_role_btn` VALUES ('1673', '1', '1000');
INSERT INTO `eova_role_btn` VALUES ('1674', '1', '1075');
INSERT INTO `eova_role_btn` VALUES ('1675', '1', '1124');
INSERT INTO `eova_role_btn` VALUES ('1676', '1', '1074');
INSERT INTO `eova_role_btn` VALUES ('1677', '1', '1125');
INSERT INTO `eova_role_btn` VALUES ('1678', '1', '1077');
INSERT INTO `eova_role_btn` VALUES ('1679', '1', '1122');
INSERT INTO `eova_role_btn` VALUES ('1680', '1', '1123');
INSERT INTO `eova_role_btn` VALUES ('1681', '1', '1071');
INSERT INTO `eova_role_btn` VALUES ('1682', '1', '1070');
INSERT INTO `eova_role_btn` VALUES ('1683', '1', '1073');
INSERT INTO `eova_role_btn` VALUES ('1684', '1', '1126');
INSERT INTO `eova_role_btn` VALUES ('1685', '1', '1072');
INSERT INTO `eova_role_btn` VALUES ('1686', '1', '1127');
INSERT INTO `eova_role_btn` VALUES ('1687', '1', '20');
INSERT INTO `eova_role_btn` VALUES ('1688', '1', '1132');
INSERT INTO `eova_role_btn` VALUES ('1689', '1', '1067');
INSERT INTO `eova_role_btn` VALUES ('1690', '1', '1068');
INSERT INTO `eova_role_btn` VALUES ('1691', '1', '1131');
INSERT INTO `eova_role_btn` VALUES ('1692', '1', '1130');
INSERT INTO `eova_role_btn` VALUES ('1693', '1', '100');
INSERT INTO `eova_role_btn` VALUES ('1694', '1', '1119');
INSERT INTO `eova_role_btn` VALUES ('1695', '1', '90');
INSERT INTO `eova_role_btn` VALUES ('1696', '1', '1066');
INSERT INTO `eova_role_btn` VALUES ('1697', '1', '1111');
INSERT INTO `eova_role_btn` VALUES ('1698', '1', '1065');
INSERT INTO `eova_role_btn` VALUES ('1699', '1', '1112');
INSERT INTO `eova_role_btn` VALUES ('1700', '1', '1064');
INSERT INTO `eova_role_btn` VALUES ('1701', '1', '1113');
INSERT INTO `eova_role_btn` VALUES ('1702', '1', '1063');
INSERT INTO `eova_role_btn` VALUES ('1703', '1', '1115');
INSERT INTO `eova_role_btn` VALUES ('1704', '1', '1061');
INSERT INTO `eova_role_btn` VALUES ('1705', '1', '1116');
INSERT INTO `eova_role_btn` VALUES ('1706', '1', '1060');
INSERT INTO `eova_role_btn` VALUES ('1707', '1', '1117');
INSERT INTO `eova_role_btn` VALUES ('1708', '1', '1118');
INSERT INTO `eova_role_btn` VALUES ('1709', '1', '1058');
INSERT INTO `eova_role_btn` VALUES ('1710', '1', '1059');
INSERT INTO `eova_role_btn` VALUES ('1711', '1', '1056');
INSERT INTO `eova_role_btn` VALUES ('1712', '1', '1057');
INSERT INTO `eova_role_btn` VALUES ('1713', '1', '1120');
INSERT INTO `eova_role_btn` VALUES ('1714', '1', '88');
INSERT INTO `eova_role_btn` VALUES ('1715', '1', '116');
INSERT INTO `eova_role_btn` VALUES ('1716', '1', '112');
INSERT INTO `eova_role_btn` VALUES ('1717', '1', '113');
INSERT INTO `eova_role_btn` VALUES ('1718', '1', '110');
INSERT INTO `eova_role_btn` VALUES ('1719', '1', '111');
INSERT INTO `eova_role_btn` VALUES ('1720', '1', '1047');
INSERT INTO `eova_role_btn` VALUES ('1721', '1', '1046');
INSERT INTO `eova_role_btn` VALUES ('1722', '1', '1045');
INSERT INTO `eova_role_btn` VALUES ('1723', '1', '1049');
INSERT INTO `eova_role_btn` VALUES ('1724', '1', '1050');
INSERT INTO `eova_role_btn` VALUES ('1725', '1', '1051');
INSERT INTO `eova_role_btn` VALUES ('1726', '1', '1054');
INSERT INTO `eova_role_btn` VALUES ('1727', '1', '1052');
INSERT INTO `eova_role_btn` VALUES ('1728', '1', '1053');
INSERT INTO `eova_role_btn` VALUES ('1729', '1', '82');
INSERT INTO `eova_role_btn` VALUES ('1730', '1', '83');
INSERT INTO `eova_role_btn` VALUES ('1731', '1', '80');
INSERT INTO `eova_role_btn` VALUES ('1732', '1', '81');
INSERT INTO `eova_role_btn` VALUES ('1733', '1', '86');
INSERT INTO `eova_role_btn` VALUES ('1734', '1', '87');
INSERT INTO `eova_role_btn` VALUES ('1735', '1', '84');
INSERT INTO `eova_role_btn` VALUES ('1736', '1', '1134');
INSERT INTO `eova_role_btn` VALUES ('1737', '1', '1133');
INSERT INTO `eova_role_btn` VALUES ('1738', '1', '1135');
INSERT INTO `eova_role_btn` VALUES ('1739', '1', '1035');
INSERT INTO `eova_role_btn` VALUES ('1740', '1', '1034');
INSERT INTO `eova_role_btn` VALUES ('1741', '1', '1037');
INSERT INTO `eova_role_btn` VALUES ('1742', '1', '1036');
INSERT INTO `eova_role_btn` VALUES ('1743', '1', '129');
INSERT INTO `eova_role_btn` VALUES ('1744', '1', '1039');
INSERT INTO `eova_role_btn` VALUES ('1745', '1', '1038');
INSERT INTO `eova_role_btn` VALUES ('1746', '1', '1040');
INSERT INTO `eova_role_btn` VALUES ('1747', '1', '1041');
INSERT INTO `eova_role_btn` VALUES ('1748', '1', '1042');
INSERT INTO `eova_role_btn` VALUES ('1749', '1', '1043');
INSERT INTO `eova_role_btn` VALUES ('1750', '1', '1044');
INSERT INTO `eova_role_btn` VALUES ('1751', '1', '1168');
INSERT INTO `eova_role_btn` VALUES ('1752', '1', '1167');
INSERT INTO `eova_role_btn` VALUES ('1753', '1', '1166');
INSERT INTO `eova_role_btn` VALUES ('1754', '1', '1029');
INSERT INTO `eova_role_btn` VALUES ('1755', '1', '1027');
INSERT INTO `eova_role_btn` VALUES ('1756', '1', '1026');
INSERT INTO `eova_role_btn` VALUES ('1757', '1', '1025');
INSERT INTO `eova_role_btn` VALUES ('1758', '1', '1024');
INSERT INTO `eova_role_btn` VALUES ('1759', '1', '1023');
INSERT INTO `eova_role_btn` VALUES ('1760', '1', '1032');
INSERT INTO `eova_role_btn` VALUES ('1761', '1', '1033');
INSERT INTO `eova_role_btn` VALUES ('1762', '1', '1030');
INSERT INTO `eova_role_btn` VALUES ('1763', '1', '1031');
INSERT INTO `eova_role_btn` VALUES ('1768', '1', '1171');
INSERT INTO `eova_role_btn` VALUES ('1769', '1', '1172');
INSERT INTO `eova_role_btn` VALUES ('1770', '1', '1170');
INSERT INTO `eova_role_btn` VALUES ('1771', '1', '1173');
INSERT INTO `eova_role_btn` VALUES ('1772', '1', '45');
INSERT INTO `eova_role_btn` VALUES ('1773', '1', '44');
INSERT INTO `eova_role_btn` VALUES ('1774', '1', '1017');
INSERT INTO `eova_role_btn` VALUES ('1775', '1', '1016');
INSERT INTO `eova_role_btn` VALUES ('1777', '1', '1019');
INSERT INTO `eova_role_btn` VALUES ('1778', '1', '1018');
INSERT INTO `eova_role_btn` VALUES ('1779', '1', '1013');
INSERT INTO `eova_role_btn` VALUES ('1780', '1', '1012');
INSERT INTO `eova_role_btn` VALUES ('1781', '1', '1015');
INSERT INTO `eova_role_btn` VALUES ('1783', '1', '1020');
INSERT INTO `eova_role_btn` VALUES ('1788', '1', '1022');
INSERT INTO `eova_role_btn` VALUES ('1794', '1', '1163');
INSERT INTO `eova_role_btn` VALUES ('1796', '1', '1164');
INSERT INTO `eova_role_btn` VALUES ('1798', '1', '1165');
INSERT INTO `eova_role_btn` VALUES ('1823', '5', '116');
INSERT INTO `eova_role_btn` VALUES ('1824', '5', '110');
INSERT INTO `eova_role_btn` VALUES ('1825', '5', '1075');
INSERT INTO `eova_role_btn` VALUES ('1826', '5', '1047');
INSERT INTO `eova_role_btn` VALUES ('1827', '5', '1074');
INSERT INTO `eova_role_btn` VALUES ('1828', '5', '1046');
INSERT INTO `eova_role_btn` VALUES ('1829', '5', '1045');
INSERT INTO `eova_role_btn` VALUES ('1830', '5', '1071');
INSERT INTO `eova_role_btn` VALUES ('1831', '5', '1070');
INSERT INTO `eova_role_btn` VALUES ('1832', '5', '1073');
INSERT INTO `eova_role_btn` VALUES ('1833', '5', '1049');
INSERT INTO `eova_role_btn` VALUES ('1834', '5', '1072');
INSERT INTO `eova_role_btn` VALUES ('1836', '5', '1050');
INSERT INTO `eova_role_btn` VALUES ('1837', '5', '1051');
INSERT INTO `eova_role_btn` VALUES ('1838', '5', '1054');
INSERT INTO `eova_role_btn` VALUES ('1839', '5', '1131');
INSERT INTO `eova_role_btn` VALUES ('1840', '5', '1042');
INSERT INTO `eova_role_btn` VALUES ('1841', '5', '1130');
INSERT INTO `eova_role_btn` VALUES ('1842', '5', '1052');
INSERT INTO `eova_role_btn` VALUES ('1843', '5', '1043');
INSERT INTO `eova_role_btn` VALUES ('1844', '5', '1053');
INSERT INTO `eova_role_btn` VALUES ('1845', '5', '1044');
INSERT INTO `eova_role_btn` VALUES ('1856', '1', '1238');


-- ----------------------------
-- Table structure for eova_task
-- ----------------------------
DROP TABLE IF EXISTS `eova_task`;
CREATE TABLE `eova_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `state` int(1) NOT NULL DEFAULT '0' COMMENT '状态：0=停止，1=启动',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `exp` varchar(50) NOT NULL COMMENT '表达式',
  `clazz` varchar(255) NOT NULL COMMENT '实现类',
  `info` varchar(255) DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='EOVA任务';

-- ----------------------------
-- Records of eova_task
-- ----------------------------
INSERT INTO `eova_task` VALUES ('1', '0', '每分', '0 * * * * ? *', 'com.oss.job.EveryMinJob', '每分钟来一发');
INSERT INTO `eova_task` VALUES ('2', '0', '每时', '0 0 * * * ? *', 'com.oss.job.EveryHourJob', '每小时统计一次');
INSERT INTO `eova_task` VALUES ('12', '0', '每天', '59 59 23 * * ? *', 'com.oss.job.EveryDayJob', '每天23点59分59秒跑一下');

-- ----------------------------
-- Table structure for eova_user
-- ----------------------------
DROP TABLE IF EXISTS `eova_user`;
CREATE TABLE `eova_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` varchar(30) DEFAULT NULL COMMENT '帐号',
  `login_pwd` varchar(50) DEFAULT NULL COMMENT '密码',
  `rid` int(11) NOT NULL DEFAULT '0' COMMENT '角色ID',
  `name` varchar(50) DEFAULT '' COMMENT '姓名',
  `memo` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_login_id` (`login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='EOVA用户';

-- ----------------------------
-- Records of eova_user
-- ----------------------------
INSERT INTO `eova_user` VALUES ('1', 'eova', '89BDF69372C2EF53EA409CDF020B5694', '1', '', null);
INSERT INTO `eova_user` VALUES ('2', 'admin', '89BDF69372C2EF53EA409CDF020B5694', '2', '侯亮平', '主管业务');
INSERT INTO `eova_user` VALUES ('3', 'test', '89BDF69372C2EF53EA409CDF020B5694', '3', '孙亦可', '惩奸除恶');
INSERT INTO `eova_user` VALUES ('4', 'test1', '89BDF69372C2EF53EA409CDF020B5694', '4', '林华华', '调皮可爱');

-- ----------------------------
-- Table structure for eova_widget
-- ----------------------------
DROP TABLE IF EXISTS `eova_widget`;
CREATE TABLE `eova_widget` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(5) NOT NULL DEFAULT '1' COMMENT '控件类型：1=EOVA控件，2=DIY控件',
  `value` varchar(50) NOT NULL COMMENT '控件值',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `version` float(5,1) DEFAULT '1.0' COMMENT '版本号',
  `path` varchar(50) DEFAULT NULL COMMENT '路径',
  `description` varchar(4000) DEFAULT NULL COMMENT '介绍',
  `config` varchar(4000) DEFAULT NULL COMMENT '控件配置信息JSON',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='EOVA控件';

-- ----------------------------
-- Records of eova_widget
-- ----------------------------
INSERT INTO `eova_widget` VALUES ('1', '1', '下拉框', '下拉框', '1.0', '', '', null);
INSERT INTO `eova_widget` VALUES ('2', '1', '查找框', '查找框', '1.0', '', '', null);
INSERT INTO `eova_widget` VALUES ('3', '1', '文本框', '文本框', '1.0', '', '', null);
INSERT INTO `eova_widget` VALUES ('4', '1', '文本域', '文本域', '1.0', '', '', null);
INSERT INTO `eova_widget` VALUES ('5', '1', '编辑框', '编辑框', '1.0', '', '', null);
INSERT INTO `eova_widget` VALUES ('6', '1', 'JSON框', 'JSON框', '1.0', '', '', null);
INSERT INTO `eova_widget` VALUES ('7', '1', '时间框', '时间框', '1.0', '', '', null);
INSERT INTO `eova_widget` VALUES ('8', '1', '日期框', '日期框', '1.0', '', '', null);
INSERT INTO `eova_widget` VALUES ('9', '1', '布尔框', '布尔框', '1.0', '', '', null);
INSERT INTO `eova_widget` VALUES ('10', '1', '图片框', '图片框', '1.0', '', '', null);
INSERT INTO `eova_widget` VALUES ('11', '1', '文件框', '文件框', '1.0', '', '', null);
INSERT INTO `eova_widget` VALUES ('12', '1', '图标框', '图标框', '1.0', '', '', null);
INSERT INTO `eova_widget` VALUES ('13', '1', '自增框', '自增框', '1.0', '', '', null);
INSERT INTO `eova_widget` VALUES ('14', '1', '下拉树', '下拉树', '1.0', '', '', null);
INSERT INTO `eova_widget` VALUES ('15', '1', '多图框', '多图框', '1.0', null, null, null);
INSERT INTO `eova_widget` VALUES ('30', '2', '密码框', '密码框', '1.0', '/widget/password/index.html', '我的密码框，系统不给，自己造一个，大家一起爽歪歪', null);
INSERT INTO `eova_widget` VALUES ('31', '2', '数字框', '数字框', '1.0', '/widget/number/index.html', 'number', null);
INSERT INTO `eova_widget` VALUES ('32', '2', '颜色框', '颜色框', '1.0', '/widget/color/index.html', 'color', null);
INSERT INTO `eova_widget` VALUES ('33', '2', '复选框', '复选框', '1.0', '/widget/check/index.html', '下拉变多复选框', null);
