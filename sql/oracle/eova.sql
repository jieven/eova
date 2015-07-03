/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50513
Source Host           : localhost:3306
Source Database       : eova

Target Server Type    : ORACLE
Target Server Version : 100100
File Encoding         : 65001

Date: 2015-06-30 01:21:42
*/

;

-- ----------------------------
-- Table structure for eova_button
-- ----------------------------
DROP TABLE eova_button;
CREATE TABLE eova_button (
id NUMBER(11) NOT NULL ,
menuCode NVARCHAR2(255) NOT NULL ,
name NVARCHAR2(255) NOT NULL ,
ui NVARCHAR2(255) NULL ,
bs NVARCHAR2(500) NULL ,
indexNum NUMBER(11) NULL 
)

;
COMMENT ON COLUMN eova_button.menuCode IS '菜单Code';
COMMENT ON COLUMN eova_button.name IS '按钮名称';
COMMENT ON COLUMN eova_button.ui IS '按钮UI路径';
COMMENT ON COLUMN eova_button.bs IS '按钮BS路径';
COMMENT ON COLUMN eova_button.indexNum IS '排序';

-- ----------------------------
-- Records of eova_button
-- ----------------------------
INSERT INTO eova_button VALUES ('1', 'eova_menu', '查询', '', '', '0');
INSERT INTO eova_button VALUES ('2', 'eova_button', '查询', '', '', '0');
INSERT INTO eova_button VALUES ('3', 'eova_object', '查询', '', '', '0');
INSERT INTO eova_button VALUES ('4', 'eova_item', '查询', '', '', '0');
INSERT INTO eova_button VALUES ('5', 'eova_dictionary', '查询', '', '', '0');
INSERT INTO eova_button VALUES ('6', 'eova_icon', '查询', '', '', '0');
INSERT INTO eova_button VALUES ('7', 'sys_auth_user', '查询', '', '', '0');
INSERT INTO eova_button VALUES ('8', 'sys_auth_role', '查询', '', '', '0');
INSERT INTO eova_button VALUES ('9', 'sys_log', '查询', '', '', '0');
INSERT INTO eova_button VALUES ('10', 'eova_menu', '新增', '/eova/menu/btn/add.html', '', '1');
INSERT INTO eova_button VALUES ('11', 'eova_menu', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '2');
INSERT INTO eova_button VALUES ('12', 'eova_menu', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '3');
INSERT INTO eova_button VALUES ('13', 'eova_item', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '1');
INSERT INTO eova_button VALUES ('14', 'eova_item', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '2');
INSERT INTO eova_button VALUES ('15', 'eova_item', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '3');
INSERT INTO eova_button VALUES ('16', 'eova_button', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '1');
INSERT INTO eova_button VALUES ('17', 'eova_button', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '2');
INSERT INTO eova_button VALUES ('18', 'eova_button', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '3');
INSERT INTO eova_button VALUES ('19', 'eova_object', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '1');
INSERT INTO eova_button VALUES ('20', 'eova_object', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '2');
INSERT INTO eova_button VALUES ('21', 'eova_object', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '3');
INSERT INTO eova_button VALUES ('22', 'eova_object', '导入元数据', '/eova/metadata/btn/import.html', ' ', '5');
INSERT INTO eova_button VALUES ('23', 'eova_menu', '基本功能', '/eova/menu/btn/fun.html', '', '5');
INSERT INTO eova_button VALUES ('24', 'eova_dictionary', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '1');
INSERT INTO eova_button VALUES ('25', 'eova_dictionary', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '2');
INSERT INTO eova_button VALUES ('26', 'sys_auth_role', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '1');
INSERT INTO eova_button VALUES ('27', 'sys_auth_role', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '2');
INSERT INTO eova_button VALUES ('28', 'sys_auth_role', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '3');
INSERT INTO eova_button VALUES ('29', 'sys_auth_user', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '1');
INSERT INTO eova_button VALUES ('30', 'sys_auth_user', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '2');
INSERT INTO eova_button VALUES ('31', 'sys_auth_user', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '3');
INSERT INTO eova_button VALUES ('32', 'sys_auth_role', '权限分配', '/eova/auth/btn/roleChoose.html', '', '5');
INSERT INTO eova_button VALUES ('65', 'biz_demo_users', '查询', '', '', '0');
INSERT INTO eova_button VALUES ('66', 'biz_demo_tool', '查询', '', '', '0');
INSERT INTO eova_button VALUES ('67', 'biz_demo_users', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '0');
INSERT INTO eova_button VALUES ('68', 'biz_demo_users', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '0');
INSERT INTO eova_button VALUES ('69', 'biz_demo_users', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '0');
INSERT INTO eova_button VALUES ('71', 'biz_demo_tool', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '0');
INSERT INTO eova_button VALUES ('72', 'biz_demo_tool', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '0');
INSERT INTO eova_button VALUES ('73', 'biz_demo_tool', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '0');
INSERT INTO eova_button VALUES ('74', 'biz_demo_usersitem', '查询', '', '', '0');
INSERT INTO eova_button VALUES ('75', 'biz_demo_usersitem', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '0');
INSERT INTO eova_button VALUES ('76', 'biz_demo_usersitem', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '0');
INSERT INTO eova_button VALUES ('77', 'biz_demo_usersitem', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '0');
INSERT INTO eova_button VALUES ('78', 'biz_demo_usersview', '查询', '', '', '0');
INSERT INTO eova_button VALUES ('79', 'biz_demo_usersview', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '0');
INSERT INTO eova_button VALUES ('80', 'biz_demo_usersview', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '0');
INSERT INTO eova_button VALUES ('81', 'biz_demo_usersview', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '0');
INSERT INTO eova_button VALUES ('86', 'biz_demo_userscell', '查询', '', '', '0');
INSERT INTO eova_button VALUES ('87', 'biz_demo_import', '查询', '', '', '0');
INSERT INTO eova_button VALUES ('96', 'biz_demo_import', '新增', '/eova/template/crud/btn/add.html', 'crud/add', '0');
INSERT INTO eova_button VALUES ('97', 'biz_demo_import', '修改', '/eova/template/crud/btn/update.html', 'crud/update', '0');
INSERT INTO eova_button VALUES ('98', 'biz_demo_import', '删除', '/eova/template/crud/btn/dels.html', 'crud/delete', '0');
INSERT INTO eova_button VALUES ('99', 'biz_demo_import', '导入', '/eova/template/crud/btn/import.html', 'crud/import', '0');

-- ----------------------------
-- Table structure for eova_dict
-- ----------------------------
DROP TABLE eova_dict;
CREATE TABLE eova_dict (
id NUMBER(11) NOT NULL ,
value NVARCHAR2(50) NOT NULL ,
name NVARCHAR2(50) NOT NULL ,
class NVARCHAR2(50) NOT NULL ,
field NVARCHAR2(50) NOT NULL 
)

;

-- ----------------------------
-- Records of eova_dict
-- ----------------------------
INSERT INTO eova_dict VALUES ('1', 'main', '默认', 'eova_object', 'dataSource');
INSERT INTO eova_dict VALUES ('2', 'eova', 'EOVA', 'eova_object', 'dataSource');
INSERT INTO eova_dict VALUES ('3', 'string', '字符', 'eova_item', 'dataType');
INSERT INTO eova_dict VALUES ('4', 'number', '数字', 'eova_item', 'dataType');
INSERT INTO eova_dict VALUES ('5', 'time', '时间', 'eova_item', 'dataType');
INSERT INTO eova_dict VALUES ('6', '1', '新增', 'eova_log', 'type');
INSERT INTO eova_dict VALUES ('7', '2', '修改', 'eova_log', 'type');
INSERT INTO eova_dict VALUES ('8', '3', '删除', 'eova_log', 'type');
INSERT INTO eova_dict VALUES ('9', '文本框', '文本框', 'eova_item', 'type');
INSERT INTO eova_dict VALUES ('10', '下拉框', '下拉框', 'eova_item', 'type');
INSERT INTO eova_dict VALUES ('11', '查找框', '查找框', 'eova_item', 'type');
INSERT INTO eova_dict VALUES ('12', '时间框', '时间框', 'eova_item', 'type');
INSERT INTO eova_dict VALUES ('13', '文本域', '文本域', 'eova_item', 'type');
INSERT INTO eova_dict VALUES ('14', '编辑框', '编辑框', 'eova_item', 'type');
INSERT INTO eova_dict VALUES ('15', '复选框', '复选框', 'eova_item', 'type');
INSERT INTO eova_dict VALUES ('16', '自增框', '自增框', 'eova_item', 'type');

-- ----------------------------
-- Table structure for eova_item
-- ----------------------------
DROP TABLE eova_item;
CREATE TABLE eova_item (
id NUMBER(11) NOT NULL ,
objectCode NVARCHAR2(50) NOT NULL ,
poCode NVARCHAR2(255) NULL ,
en NVARCHAR2(30) NOT NULL ,
cn NVARCHAR2(30) NOT NULL ,
isAuto CHAR(1) NULL ,
dataType NVARCHAR2(20) NULL ,
type NVARCHAR2(10) NULL ,
indexNum NUMBER(11) NULL ,
exp NVARCHAR2(800) NULL ,
isQuery CHAR(1) NULL ,
isShow CHAR(1) NULL ,
isOrder CHAR(1) NULL ,
isAdd CHAR(1) NULL ,
isUpdate CHAR(1) NULL ,
isEdit CHAR(1) NULL ,
isNotNull CHAR(1) NULL ,
placeholder NVARCHAR2(255) NULL ,
validator NVARCHAR2(255) NULL ,
valueExp NVARCHAR2(255) NULL ,
width NUMBER(11) NULL ,
height NUMBER(11) NULL ,
isMultiple CHAR(1) NULL 
)

;
COMMENT ON COLUMN eova_item.id IS 'ID';
COMMENT ON COLUMN eova_item.poCode IS '持久化对象';
COMMENT ON COLUMN eova_item.en IS '英文名';
COMMENT ON COLUMN eova_item.cn IS '中文名';
COMMENT ON COLUMN eova_item.isAuto IS '主键是否自增长';
COMMENT ON COLUMN eova_item.dataType IS '数据类型';
COMMENT ON COLUMN eova_item.type IS '控件类型';
COMMENT ON COLUMN eova_item.indexNum IS '排序索引';
COMMENT ON COLUMN eova_item.exp IS '控件表达式';
COMMENT ON COLUMN eova_item.isQuery IS '是否可查询';
COMMENT ON COLUMN eova_item.isShow IS '是否可显示';
COMMENT ON COLUMN eova_item.isOrder IS '是否可排序';
COMMENT ON COLUMN eova_item.isAdd IS '是否可新增字段';
COMMENT ON COLUMN eova_item.isUpdate IS '是否可修改字段';
COMMENT ON COLUMN eova_item.isEdit IS '是否可编辑字段';
COMMENT ON COLUMN eova_item.isNotNull IS '是否必填';
COMMENT ON COLUMN eova_item.placeholder IS '输入提示';
COMMENT ON COLUMN eova_item.validator IS 'UI校验表达式';
COMMENT ON COLUMN eova_item.valueExp IS '默认值表达式';
COMMENT ON COLUMN eova_item.width IS '控件宽度';
COMMENT ON COLUMN eova_item.height IS '控件高度';
COMMENT ON COLUMN eova_item.isMultiple IS '是否多选';

-- ----------------------------
-- Records of eova_item
-- ----------------------------
INSERT INTO CODER.EOVA_ITEM VALUES ('1', 'eova_user_code', null, 'ID', 'ID', '1', 'number', '自增框', '0', null, '0', '1', '1', '1', '1', '0', '1', null, null, null, '130', '0', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('2', 'eova_user_code', null, 'NICKNAME', '昵称', '0', 'string', '文本框', '0', null, '1', '1', '1', '1', '1', '0', '1', null, null, null, '130', '0', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('3', 'eova_user_code', null, 'LOGINID', '登录帐号', '0', 'string', '上传框', '0', null, '1', '1', '1', '1', '1', '0', '1', null, null, null, '130', '0', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('4', 'eova_user_code', null, 'LOGINPWD', '登录密码', '0', 'string', '文本框', '0', null, '0', '0', '0', '1', '1', '0', '1', null, null, null, '130', '0', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('5', 'eova_menu_code', null, 'ID', 'ID', '1', 'number', '自增框', '1', null, '0', '0', '1', '1', '1', '0', '1', null, null, null, '100', '0', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('6', 'eova_menu_code', null, 'CODE', '编码', '0', 'string', '文本框', '4', null, '1', '1', '1', '1', '0', '0', '1', null, null, null, '180', '0', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('7', 'eova_menu_code', null, 'NAME', '名称', '0', 'string', '文本框', '2', null, '1', '1', '1', '1', '1', '0', '1', null, null, null, '180', '0', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('8', 'eova_menu_code', null, 'TYPE', '类型', '0', 'string', '文本框', '1', null, '0', '1', '1', '1', '1', '0', '1', null, null, null, '100', '0', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('10', 'eova_menu_code', null, 'ICON', '图标', '0', 'string', '图标框', '6', null, '0', '0', '1', '1', '1', '0', '1', null, null, null, '130', '0', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('11', 'eova_menu_code', null, 'INDEXNUM', '序号', '0', 'number', '文本框', '9', null, '0', '1', '1', '1', '1', '0', '1', null, null, null, '30', '0', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('12', 'eova_menu_code', null, 'PARENTID', '父节点', '0', 'number', '查找框', '9', 'select id ID,name 菜单名称 from eova_menu where 1=1;ds=eova', '1', '1', '1', '1', '1', '0', '1', null, null, null, '100', '0', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('13', 'eova_object_code', null, 'ID', 'ID', '1', 'number', '自增框', '1', null, '0', '0', '1', '1', '1', '0', '1', null, null, null, '130', '0', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('14', 'eova_object_code', null, 'CODE', '编码', '0', 'string', '文本框', '2', null, '1', '1', '1', '1', '0', '0', '1', null, null, null, '200', '0', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('15', 'eova_object_code', null, 'NAME', '名称', '0', 'string', '文本框', '3', null, '0', '1', '1', '1', '1', '0', '1', null, null, null, '130', '0', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('16', 'eova_object_code', null, 'VIEW', '视图', '0', 'string', '文本框', '4', null, '1', '1', '1', '1', '1', '0', '0', null, null, null, '70', '0', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('17', 'eova_object_code', null, 'TABLE', '数据表', '0', 'string', '文本框', '5', null, '1', '1', '1', '1', '1', '0', '0', null, null, null, '70', '0', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('18', 'eova_object_code', null, 'PKNAME', '主键', '0', 'string', '文本框', '6', null, '0', '1', '1', '1', '1', '0', '0', null, null, null, '70', '0', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('19', 'eova_object_code', null, 'DATASOURCE', '数据源', '0', 'string', '下拉框', '7', 'select value ID,name CN from eova_dict where class = ''eova_object'' and field = ''dataSource'';ds=eova', '0', '1', '1', '1', '1', '0', '1', null, null, null, '70', '0', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('20', 'eova_object_code', null, 'ISSINGLESELECT', '是否单选', '0', 'number', '复选框', '8', null, '0', '1', '1', '1', '1', '0', '0', null, null, null, '70', '0', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('21', 'eova_object_code', null, 'ISSHOWNUM', '显示行号', '0', 'number', '复选框', '9', null, '0', '1', '1', '1', '1', '0', '0', null, null, null, '70', '0', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('22', 'eova_object_code', null, 'ISDEFAULTPKDESC', '默认逆序', '0', 'number', '复选框', '10', null, '0', '1', '1', '1', '1', '0', '0', null, null, null, '70', '0', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('23', 'eova_object_code', null, 'FILTERWHERE', '过滤条件', '0', 'string', '文本域', '11', null, '0', '1', '1', '1', '1', '0', '0', null, null, null, '130', '0', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('26', 'eova_item_code', null, 'ID', 'ID', '1', 'number', '自增框', '1', null, '0', '0', '1', '1', '1', '0', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('27', 'eova_item_code', null, 'OBJECTCODE', '对象编码', '0', 'string', '查找框', '2', 'select code 编码,name 名称 from eova_object where 1=1;ds=eova', '1', '1', '1', '1', '1', '0', '1', null, null, 'eova_user_code', '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('28', 'eova_item_code', null, 'EN', '字段名', '0', 'string', '文本框', '3', null, '1', '1', '1', '1', '1', '1', '1', '数据库的字段名', null, null, '70', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('29', 'eova_item_code', null, 'CN', '中文名', '0', 'string', '文本框', '4', null, '1', '1', '1', '1', '1', '1', '1', '字段对应的中文描述', null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('30', 'eova_item_code', null, 'ISAUTO', '自增长', '0', 'number', '复选框', '20', null, '0', '1', '1', '1', '1', '1', '0', null, null, null, '70', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('31', 'eova_item_code', null, 'DATATYPE', '字段类型', '0', 'string', '下拉框', '6', 'select value ID,name CN from eova_dict where class = ''eova_item'' and field = ''dataType'';ds=eova', '0', '1', '1', '1', '1', '0', '1', null, null, 'string', '70', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('32', 'eova_item_code', null, 'TYPE', '控件类型', '0', 'string', '下拉框', '7', 'select value ID,name CN from eova_dict where class = ''eova_item'' and field = ''Type'';ds=eova', '1', '1', '1', '1', '1', '1', '1', null, null, '文本框', '70', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('33', 'eova_item_code', null, 'INDEXNUM', '排序', '0', 'number', '文本框', '8', null, '0', '1', '1', '1', '1', '1', '1', null, null, '0', '50', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('34', 'eova_item_code', null, 'EXP', '表达式', '0', 'string', '文本域', '31', null, '0', '1', '1', '1', '1', '0', '0', '查找框和下拉框需需要表达式', null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('35', 'eova_item_code', null, 'ISQUERY', '允许查询', '0', 'number', '复选框', '21', null, '0', '1', '1', '1', '1', '1', '0', null, null, null, '70', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('36', 'eova_item_code', null, 'ISSHOW', '允许显示', '0', 'number', '复选框', '22', null, '0', '1', '1', '1', '1', '1', '0', null, null, '1', '70', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('37', 'eova_item_code', null, 'ISORDER', '允许排序', '0', 'number', '复选框', '23', null, '0', '1', '1', '1', '1', '1', '0', null, null, '1', '70', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('38', 'eova_item_code', null, 'ISADD', '允许新增', '0', 'number', '复选框', '24', null, '0', '1', '1', '1', '1', '1', '0', null, null, '1', '70', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('39', 'eova_item_code', null, 'ISUPDATE', '允许修改', '0', 'number', '复选框', '25', null, '0', '1', '1', '1', '1', '1', '0', null, null, '1', '70', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('40', 'eova_item_code', null, 'ISNOTNULL', '是否必填', '0', 'number', '复选框', '25', null, '0', '1', '1', '1', '1', '1', '0', null, null, '1', '70', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('41', 'eova_item_code', null, 'VALUEEXP', '默认值表达式', '0', 'string', '文本域', '32', null, '0', '1', '1', '1', '1', '1', '0', '初始默认值', null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('42', 'eova_item_code', null, 'WIDTH', '宽度', '0', 'number', '文本框', '17', null, '0', '1', '1', '1', '1', '1', '1', null, null, '130', '50', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('43', 'eova_item_code', null, 'HEIGHT', '高度', '0', 'number', '文本框', '18', null, '0', '1', '1', '1', '1', '1', '1', null, null, '80', '50', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('44', 'eova_item_code', null, 'ISMULTIPLE', '允许多选', '0', 'number', '复选框', '26', null, '0', '1', '1', '1', '1', '1', '0', null, null, null, '70', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('45', 'eova_button_code', null, 'ID', 'ID', '1', 'number', '自增框', '1', null, '0', '1', '1', '1', '1', '0', '0', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('46', 'eova_button_code', null, 'MENUCODE', '菜单编码', '0', 'string', '查找框', '2', 'select code 菜单编码,name 菜单名称 from eova_menu where 1=1;ds=eova', '1', '1', '1', '1', '1', '0', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('47', 'eova_button_code', null, 'NAME', '功能名称', '0', 'string', '文本框', '4', null, '1', '1', '1', '1', '1', '0', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('48', 'eova_button_code', null, 'UI', 'UI路径', '0', 'string', '文本框', '5', null, '1', '1', '1', '1', '1', '0', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('49', 'eova_button_code', null, 'BS', 'BS路径', '0', 'string', '文本框', '6', null, '1', '1', '1', '1', '1', '0', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('50', 'eova_dict_code', null, 'ID', 'ID', '1', 'number', '自增框', '1', null, '0', '1', '1', '1', '1', '0', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('51', 'eova_dict_code', null, 'VALUE', '值', '0', 'string', '文本框', '2', null, '1', '1', '1', '1', '1', '0', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('52', 'eova_dict_code', null, 'VALUEEN', '字段名', '0', 'string', '文本框', '3', null, '1', '1', '1', '1', '1', '0', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('53', 'eova_dict_code', null, 'VALUECN', '列名', '0', 'string', '文本框', '4', null, '1', '1', '1', '1', '1', '0', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('54', 'eova_dict_code', null, 'OBJECT', '对象', '0', 'string', '文本框', '5', null, '1', '1', '1', '1', '1', '0', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('55', 'eova_dict_code', null, 'ITEM', 'item', '0', 'string', '文本框', '6', null, '1', '1', '1', '1', '1', '0', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('59', 'eova_menu_code', null, 'BIZINTERCEPT', '自定义业务拦截器', '0', 'string', '文本域', '14', null, '0', '1', '1', '1', '1', '0', '0', null, null, null, '300', '0', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('60', 'eova_button_code', null, 'INDEXNUM', '序号', '0', 'number', '文本框', '9', null, '0', '1', '1', '1', '1', '0', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('61', 'eova_role_code', null, 'ID', 'ID', '1', 'number', '自增框', '0', null, '0', '1', '1', '1', '1', '0', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('62', 'eova_role_code', null, 'NAME', '角色名', '0', 'string', '文本框', '0', null, '1', '1', '1', '1', '1', '0', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('63', 'eova_role_code', null, 'INFO', '角色描述', '0', 'string', '文本框', '0', null, '0', '1', '1', '1', '1', '0', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('64', 'eova_user_code', null, 'RID', '角色', '0', 'string', '下拉框', '0', 'select id ID,name CN from eova_role where 1=1;ds=eova', '0', '1', '1', '1', '1', '0', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('70', 'eova_log_code', null, 'ID', 'id', '1', 'number', '自增框', '1', null, '0', '1', '1', '1', '1', '0', '0', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('71', 'eova_log_code', null, 'USERID', '操作用户', '0', 'number', '查找框', '2', 'select id UID,nickName 用户名 from eova_user where 1=1;ds=eova', '1', '1', '1', '1', '1', '0', '0', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('72', 'eova_log_code', null, 'TYPE', '日志类型', '0', 'number', '文本框', '3', 'select value ID,name CN from eova_dict where class = ''eova_log'' and field = ''type'';ds=eova', '1', '1', '1', '1', '1', '0', '0', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('73', 'eova_log_code', null, 'IP', '操作IP', '0', 'string', '文本框', '4', null, '1', '1', '1', '1', '1', '0', '0', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('74', 'eova_log_code', null, 'INFO', '操作详情', '0', 'string', '文本框', '5', null, '0', '1', '1', '1', '1', '0', '1', null, null, null, '200', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('75', 'player_code', null, 'ID', 'ID', '1', 'number', '自增框', '1', null, '0', '1', '1', '1', '1', '0', '0', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('76', 'player_code', null, 'STATUS', '状态', '0', 'number', '下拉框', '2', 'select value ID,name CN from dict where class = ''users'' and field = ''status'';ds=main', '1', '1', '1', '1', '1', '1', '1', null, null, '0', '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('77', 'player_code', null, 'LOGINID', '登录账户', '0', 'string', '文本框', '3', null, '1', '1', '1', '1', '1', '1', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('78', 'player_code', null, 'LOGINPWD', '录登密码', '0', 'string', '文本框', '4', null, '0', '0', '1', '1', '1', '1', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('79', 'player_code', null, 'NICKNAME', '艺人姓名', '1', 'string', '文本框', '1', null, '1', '1', '1', '1', '1', '1', '1', null, null, null, '130', '20', '1');
INSERT INTO CODER.EOVA_ITEM VALUES ('80', 'player_code', null, 'REGTIME', '注册时间', '1', 'time', '时间框', '6', null, '1', '1', '1', '1', '1', '1', '1', null, null, 'CURRENT_TIMESTAMP', '180', '20', '1');
INSERT INTO CODER.EOVA_ITEM VALUES ('81', 'eova_menu_code', null, 'URL', 'URL', '0', 'string', '文本框', '15', null, '0', '1', '1', '1', '1', '0', '0', null, null, null, '130', '0', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('82', 'eova_item_code', null, 'ISEDIT', '允许行内编辑', '0', 'number', '复选框', '25', null, '0', '1', '1', '1', '1', '1', '0', null, null, null, '70', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('83', 'eova_object_code', null, 'ISCELLEDIT', '行内编辑', '0', 'number', '复选框', '8', null, '0', '1', '1', '1', '1', '0', '0', null, null, null, '70', '0', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('84', 'player_code', null, 'INFO', '备注', '0', 'string', '编辑框', '9', null, '0', '1', '1', '0', '0', '0', '0', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('85', 'item_code', null, 'ID', 'ID', '1', 'number', '自增框', '1', null, '1', '1', '1', '1', '1', '1', '1', null, null, null, '130', '20', '1');
INSERT INTO CODER.EOVA_ITEM VALUES ('86', 'item_code', null, 'NAME', '名称', '0', 'string', '文本框', '2', null, '1', '1', '1', '1', '1', '1', '1', null, null, null, '130', '20', '1');
INSERT INTO CODER.EOVA_ITEM VALUES ('87', 'item_code', null, 'INFO', '介绍', '1', 'string', '编辑框', '3', null, '1', '1', '1', '1', '1', '1', '1', null, null, null, '200', '20', '1');
INSERT INTO CODER.EOVA_ITEM VALUES ('88', 'users_item_code', null, 'ID', 'ID', '1', 'number', '自增框', '1', null, '0', '1', '1', '1', '1', '1', '0', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('89', 'users_item_code', null, 'USERSID', '艺人', '1', 'number', '查找框', '2', 'select id ID,nickName 艺人 from users where 1=1;ds=main', '1', '1', '1', '1', '1', '1', '1', null, null, null, '130', '20', '1');
INSERT INTO CODER.EOVA_ITEM VALUES ('90', 'users_item_code', null, 'ITEMID', '道具', '0', 'number', '下拉框', '3', 'select id ID,name CN from item where 1=1;ds=main', '1', '1', '1', '1', '1', '1', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('124', 'v_users_code', 'player_code', 'ID', 'ID', '1', 'number', '自增框', '1', null, '0', '1', '1', '1', '1', '1', '0', null, null, '0', '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('125', 'v_users_code', 'player_code', 'STATUS', '状态', '0', 'number', '文本框', '2', null, '1', '1', '1', '1', '1', '1', '1', null, null, '0', '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('126', 'v_users_code', 'player_code', 'LOGINID', '登录账户', '0', 'string', '文本框', '3', null, '1', '1', '1', '1', '1', '1', '1', '请输入帐号', 'min : 5, max : 7', null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('127', 'v_users_code', 'player_code', 'LOGINPWD', '录登密码', '0', 'string', '文本框', '4', null, '0', '1', '1', '1', '1', '1', '0', '请输入密码', null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('128', 'v_users_code', 'player_code', 'NICKNAME', '昵称', '0', 'string', '文本域', '20', null, '1', '1', '1', '1', '1', '1', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('129', 'v_users_code', 'player_code', 'REGTIME', '注册时间', '0', 'time', '时间框', '6', null, '0', '1', '1', '1', '1', '1', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('130', 'v_users_code', 'player_code', 'INFO', '备注', '0', 'string', '文本域', '30', null, '0', '1', '1', '1', '1', '1', '0', null, 'min : 8, max : 10', null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('132', 'v_users_code', 'users_exp_code', 'EXP', '经验值', '0', 'number', '文本框', '9', null, '0', '1', '1', '1', '1', '1', '1', null, null, '0', '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('133', 'v_users_code', 'users_exp_code', 'AVG', '年龄', '0', 'number', '文本框', '10', null, '0', '1', '1', '1', '1', '1', '0', null, null, '0', '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('134', 'v_users_code', 'users_exp_code', 'QQ', 'QQ', '0', 'string', '文本框', '22', null, '0', '1', '1', '1', '1', '1', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('146', 'celledit_users_code', null, 'ID', 'id', '1', 'number', '自增框', '1', null, '0', '0', '1', '1', '1', '1', '0', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('147', 'celledit_users_code', null, 'STATUS', '状态', '0', 'number', '文本框', '2', null, '0', '1', '1', '1', '1', '1', '1', null, null, '0', '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('148', 'celledit_users_code', null, 'LOGINID', '登录账户', '0', 'string', '文本框', '3', null, '0', '1', '1', '1', '1', '1', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('149', 'celledit_users_code', null, 'LOGINPWD', '录登密码', '0', 'string', '文本框', '4', null, '0', '1', '1', '1', '1', '1', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('150', 'celledit_users_code', null, 'NICKNAME', '昵称', '0', 'string', '文本域', '5', null, '1', '1', '1', '1', '1', '1', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('151', 'celledit_users_code', null, 'REGTIME', '注册时间', '0', 'time', '时间框', '6', null, '0', '1', '1', '1', '1', '1', '1', null, null, 'CURRENT_TIMESTAMP', '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('152', 'celledit_users_code', null, 'INFO', '备注', '0', 'string', '文本域', '7', null, '0', '1', '1', '1', '1', '1', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('153', 'users_exp_code', null, 'UID', 'uid', '0', 'number', '文本框', '1', null, '0', '1', '1', '1', '1', '1', '0', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('154', 'users_exp_code', null, 'EXP', '经验值', '0', 'number', '文本框', '2', null, '0', '1', '1', '1', '1', '1', '1', null, null, '0', '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('155', 'users_exp_code', null, 'AVG', '年龄', '0', 'number', '文本框', '3', null, '0', '1', '1', '1', '1', '1', '1', null, null, '0', '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('156', 'users_exp_code', null, 'QQ', 'QQ', '0', 'string', '文本框', '4', null, '0', '1', '1', '1', '1', '1', '1', null, null, null, '130', '20', '0');
INSERT INTO CODER.EOVA_ITEM VALUES ('157', 'eova_item_code', null, 'PLACEHOLDER', '输入提示', '0', 'string', '文本框', '28', null, '0', '1', '1', '1', '1', '1', '0', 'input的placeholder属性', null, null, '130', '20', '0');

-- ----------------------------
-- Table structure for eova_log
-- ----------------------------
DROP TABLE eova_log;
CREATE TABLE eova_log (
id NUMBER(11) NOT NULL ,
userId NUMBER(11) NOT NULL ,
type NUMBER(11) NOT NULL ,
ip NVARCHAR2(255) NOT NULL ,
info NVARCHAR2(500) NULL 
)

;
COMMENT ON COLUMN eova_log.userId IS '操作用户';
COMMENT ON COLUMN eova_log.type IS '日志类型';
COMMENT ON COLUMN eova_log.ip IS '操作IP';
COMMENT ON COLUMN eova_log.info IS '操作详情';

-- ----------------------------
-- Records of eova_log
-- ----------------------------

-- ----------------------------
-- Table structure for eova_menu
-- ----------------------------
DROP TABLE eova_menu;
CREATE TABLE eova_menu (
id NUMBER(11) NOT NULL ,
code NVARCHAR2(255) NOT NULL ,
name NVARCHAR2(30) NOT NULL ,
type NVARCHAR2(20) NOT NULL ,
icon NVARCHAR2(255) NULL ,
indexNum NUMBER(11) NULL ,
parentId NUMBER(11) NULL ,
isCollapse char(1) NULL ,
bizIntercept NVARCHAR2(255) NULL ,
url NVARCHAR2(255) NULL 
)

;
COMMENT ON COLUMN eova_menu.code IS '编码';
COMMENT ON COLUMN eova_menu.name IS '名称';
COMMENT ON COLUMN eova_menu.type IS '菜单类型';
COMMENT ON COLUMN eova_menu.icon IS '图标路径';
COMMENT ON COLUMN eova_menu.indexNum IS '序号';
COMMENT ON COLUMN eova_menu.parentId IS '父节点';
COMMENT ON COLUMN eova_menu.isCollapse IS '是否折叠';
COMMENT ON COLUMN eova_menu.bizIntercept IS '自定义业务拦截器';
COMMENT ON COLUMN eova_menu.url IS '自定义URL';

-- ----------------------------
-- Records of eova_menu
-- ----------------------------
INSERT INTO eova_menu VALUES ('1', 'eova', '平台维护', 'singleGrid', 'icon-bricks', '3', '0', '0', '', '');
INSERT INTO eova_menu VALUES ('2', 'sys', '系统管理', 'singleGrid', 'icon-cog', '2', '0', '0', '', '');
INSERT INTO eova_menu VALUES ('3', 'biz', '综合业务', 'singleGrid', 'icon-plugin', '1', '0', '0', '', '');
INSERT INTO eova_menu VALUES ('4', 'eova_menu', '菜单管理', 'singleGrid', 'icon-applicationsidetree', '1', '1', '0', 'com.eova.core.menu.MenuIntercept', '');
INSERT INTO eova_menu VALUES ('5', 'eova_button', '按钮管理', 'singleGrid', 'icon-layout', '2', '1', '0', '', '');
INSERT INTO eova_menu VALUES ('6', 'eova_object', '对象管理', 'singleGrid', 'icon-databasetable', '3', '1', '0', 'com.eova.core.object.ObjectIntercept', '');
INSERT INTO eova_menu VALUES ('7', 'eova_item', '字段管理', 'singleGrid', 'icon-applicationviewcolumns', '4', '1', '0', '', '');
INSERT INTO eova_menu VALUES ('8', 'eova_dictionary', '字典管理', 'singleGrid', 'icon-bookopen', '5', '1', '0', '', '');
INSERT INTO eova_menu VALUES ('9', 'eova_icon', '图标实例', 'diy', 'icon-applicationviewicons', '6', '1', '0', '', '/toIcon');
INSERT INTO eova_menu VALUES ('10', 'sys_auth_user', '用户管理', 'singleGrid', 'icon-group', '1', '2', '0', '', '');
INSERT INTO eova_menu VALUES ('11', 'sys_auth_role', '角色管理', 'singleGrid', 'icon-groupkey', '2', '2', '0', '', '');
INSERT INTO eova_menu VALUES ('12', 'sys_log', '系统日志', 'singleGrid', 'icon-tablemultiple', '3', '2', '0', '', '');
INSERT INTO eova_menu VALUES ('22', 'biz_demo', '功能演示', 'dir', 'icon-bookopen', '1', '3', '0', '', '');
INSERT INTO eova_menu VALUES ('23', 'biz_demo_users', '单表CRUD', 'singleGrid', 'icon-grouplink', '1', '22', '0', '', '');
INSERT INTO eova_menu VALUES ('24', 'biz_demo_tool', '富文本编辑', 'singleGrid', 'icon-controller', '1', '22', '0', '', '');
INSERT INTO eova_menu VALUES ('26', 'biz_demo_usersitem', '下拉和查找', 'singleGrid', 'icon-controller', '1', '22', '0', '', '');
INSERT INTO eova_menu VALUES ('27', 'biz_demo_usersview', '多表视图', 'singleGrid', 'icon-applicationviewcolumns', '1', '22', '0', '', '');
INSERT INTO eova_menu VALUES ('29', 'biz_demo_userscell', '表格单元格编辑', 'singleGrid', 'icon-applicationviewcolumns', '1', '22', '0', '', '');
INSERT INTO eova_menu VALUES ('30', 'biz_demo_import', '导入导出', 'singleGrid', 'icon-arrowswitch', '1', '22', '0', '', '');

-- ----------------------------
-- Table structure for eova_menu_object
-- ----------------------------
DROP TABLE eova_menu_object;
CREATE TABLE eova_menu_object (
id NUMBER(11) NOT NULL ,
menuCode NVARCHAR2(50) NOT NULL ,
objectCode NVARCHAR2(50) NOT NULL 
)

;
COMMENT ON COLUMN eova_menu_object.menuCode IS '菜单编码';
COMMENT ON COLUMN eova_menu_object.objectCode IS '对象编码';

-- ----------------------------
-- Records of eova_menu_object
-- ----------------------------
INSERT INTO eova_menu_object VALUES ('1', 'eova_menu', 'eova_menu_code');
INSERT INTO eova_menu_object VALUES ('2', 'eova_button', 'eova_button_code');
INSERT INTO eova_menu_object VALUES ('3', 'eova_object', 'eova_object_code');
INSERT INTO eova_menu_object VALUES ('4', 'eova_item', 'eova_item_code');
INSERT INTO eova_menu_object VALUES ('5', 'eova_dictionary', 'eova_dict_code');
INSERT INTO eova_menu_object VALUES ('6', 'sys_auth_user', 'eova_user_code');
INSERT INTO eova_menu_object VALUES ('7', 'sys_auth_role', 'eova_role_code');
INSERT INTO eova_menu_object VALUES ('8', 'sys_log', 'eova_log_code');
INSERT INTO eova_menu_object VALUES ('9', 'biz_player', 'player_code');
INSERT INTO eova_menu_object VALUES ('10', 'eova_object', 'eova_object_code');
INSERT INTO eova_menu_object VALUES ('11', 'myfun1', 'player_code');
INSERT INTO eova_menu_object VALUES ('12', 'biz_demo_users', 'player_code');
INSERT INTO eova_menu_object VALUES ('13', 'biz_demo_tool', 'item_code');
INSERT INTO eova_menu_object VALUES ('14', 'biz_demo_usersitem', 'users_item_code');
INSERT INTO eova_menu_object VALUES ('15', 'biz_demo_usersitem', 'users_item_code');
INSERT INTO eova_menu_object VALUES ('16', 'biz_demo_usersview', 'v_users_code');
INSERT INTO eova_menu_object VALUES ('17', 'biz_demo_userscelledit', 'player_code');
INSERT INTO eova_menu_object VALUES ('18', 'biz_demo_userscell', 'celledit_users_code');
INSERT INTO eova_menu_object VALUES ('19', 'biz_demo_import', 'player_code');

-- ----------------------------
-- Table structure for eova_object
-- ----------------------------
DROP TABLE eova_object;
CREATE TABLE eova_object (
id NUMBER(11) NOT NULL ,
code NVARCHAR2(50) NOT NULL ,
name NVARCHAR2(50) NOT NULL ,
viewName NVARCHAR2(255) NULL ,
tableName NVARCHAR2(50) NULL ,
pkName NVARCHAR2(50) NOT NULL ,
dataSource NVARCHAR2(50) NULL ,
isSingleSelect CHAR(1) NULL ,
isCellEdit CHAR(1) NULL ,
isShowNum CHAR(1) NULL ,
isDefaultPkDesc CHAR(1) NULL ,
filterWhere NVARCHAR2(500) NULL ,
diyCard NVARCHAR2(255) NULL ,
diyList NVARCHAR2(255) NULL ,
diyIntercept NVARCHAR2(255) NULL 
)

;
COMMENT ON COLUMN eova_object.code IS '对象编码';
COMMENT ON COLUMN eova_object.name IS '对象名称';
COMMENT ON COLUMN eova_object.viewName IS '查询数据视图';
COMMENT ON COLUMN eova_object.tableName IS '保存数据主表';
COMMENT ON COLUMN eova_object.pkName IS '主键';
COMMENT ON COLUMN eova_object.dataSource IS '数据源';
COMMENT ON COLUMN eova_object.isSingleSelect IS '是否单选';
COMMENT ON COLUMN eova_object.isCellEdit IS '是否可行内编辑';
COMMENT ON COLUMN eova_object.isShowNum IS '是否显示行号';
COMMENT ON COLUMN eova_object.isDefaultPkDesc IS '是否默认根据主键逆序';
COMMENT ON COLUMN eova_object.filterWhere IS '初始数据过滤条件';
COMMENT ON COLUMN eova_object.diyCard IS '自定义卡片面板';
COMMENT ON COLUMN eova_object.diyList IS '自定义列表面板';
COMMENT ON COLUMN eova_object.diyIntercept IS '自定义业务拦截器';

-- ----------------------------
-- Records of eova_object
-- ----------------------------
INSERT INTO eova_object VALUES ('1', 'eova_menu_code', '菜单', '', 'eova_menu', 'id', 'eova', '1', '0', '1', '1', '', '', '', '');
INSERT INTO eova_object VALUES ('2', 'eova_object_code', '对象模型', '', 'eova_object', 'id', 'eova', '1', '0', '1', '1', '', '', '', '');
INSERT INTO eova_object VALUES ('3', 'eova_user_code', '用户', '', 'eova_user', 'id', 'eova', '1', '0', '1', '1', '', '', '', '');
INSERT INTO eova_object VALUES ('4', 'eova_item_code', '字段管理', '', 'eova_item', 'id', 'eova', '0', '1', '1', '1', '', '', '', '');
INSERT INTO eova_object VALUES ('5', 'eova_button_code', '按钮管理', '', 'eova_button', 'id', 'eova', '1', '0', '1', '1', '', '', '', '');
INSERT INTO eova_object VALUES ('6', 'eova_dict_code', '字典管理', '', 'eova_dict', 'id', 'eova', '1', '0', '1', '1', '', '', '', '');
INSERT INTO eova_object VALUES ('7', 'eova_role_code', '角色管理', '', 'eova_role', 'id', 'eova', '1', '0', '1', '1', '', '', '', '');
INSERT INTO eova_object VALUES ('8', 'eova_log_code', '操作日志', '', 'eova_log', 'id', 'eova', '1', '0', '1', '1', '', '', '', '');
INSERT INTO eova_object VALUES ('9', 'player_code', '玩家信息', '', 'users', 'id', 'main', '1', '1', '1', '1', '', 'where status=1 or status=0', '', '');
INSERT INTO eova_object VALUES ('10', 'item_code', '道具', '', 'item', 'id', 'main', '1', '0', '1', '1', '', '', '', '');
INSERT INTO eova_object VALUES ('11', 'users_item_code', '艺人关联道具', '', 'users_item', 'id', 'main', '1', '0', '1', '1', '', '', '', '');
INSERT INTO eova_object VALUES ('13', 'v_users_code', '女优详情', 'v_users', '', 'id', 'main', '1', '0', '1', '1', '', '', '', '');
INSERT INTO eova_object VALUES ('15', 'celledit_users_code', '可编辑用户', '', 'users', 'id', 'main', '1', '1', '1', '1', '', '', '', '');
INSERT INTO eova_object VALUES ('16', 'users_exp_code', '女优信息拓展', '', 'users_exp', 'uid', 'main', '1', '0', '1', '1', '', '', '', '');

-- ----------------------------
-- Table structure for eova_role
-- ----------------------------
DROP TABLE eova_role;
CREATE TABLE eova_role (
id NUMBER(11) NOT NULL ,
name NVARCHAR2(255) NOT NULL ,
info NVARCHAR2(255) NULL ,
fun NCLOB NULL 
)

;
COMMENT ON COLUMN eova_role.name IS '角色名';
COMMENT ON COLUMN eova_role.info IS '角色描述';
COMMENT ON COLUMN eova_role.fun IS '已授权功能';

-- ----------------------------
-- Records of eova_role
-- ----------------------------
INSERT INTO eova_role VALUES ('1', '超级管理员', '系统最高级权限', '');
INSERT INTO eova_role VALUES ('2', '运营总监', '运营监控', '');
INSERT INTO eova_role VALUES ('3', '编辑', '网站数据编辑', '');
INSERT INTO eova_role VALUES ('4', '数据分析', '报表查看', '');
INSERT INTO eova_role VALUES ('5', '客服', '解答用户反馈', '');
INSERT INTO eova_role VALUES ('6', '测试', '常用功能测试', '');
INSERT INTO eova_role VALUES ('7', '运营专员', '游戏运营专员', '');
INSERT INTO eova_role VALUES ('8', '商务', '商务日常操作', '');

-- ----------------------------
-- Table structure for eova_role_btn
-- ----------------------------
DROP TABLE eova_role_btn;
CREATE TABLE eova_role_btn (
id NUMBER(11) NOT NULL ,
rid NUMBER(11) NOT NULL ,
bid NUMBER(11) NOT NULL 
)

;
COMMENT ON COLUMN eova_role_btn.rid IS '角色';
COMMENT ON COLUMN eova_role_btn.bid IS '功能';

-- ----------------------------
-- Records of eova_role_btn
-- ----------------------------
INSERT INTO eova_role_btn VALUES ('1348', '1', '65');
INSERT INTO eova_role_btn VALUES ('1349', '1', '67');
INSERT INTO eova_role_btn VALUES ('1350', '1', '68');
INSERT INTO eova_role_btn VALUES ('1351', '1', '69');
INSERT INTO eova_role_btn VALUES ('1352', '1', '66');
INSERT INTO eova_role_btn VALUES ('1353', '1', '71');
INSERT INTO eova_role_btn VALUES ('1354', '1', '72');
INSERT INTO eova_role_btn VALUES ('1355', '1', '73');
INSERT INTO eova_role_btn VALUES ('1356', '1', '74');
INSERT INTO eova_role_btn VALUES ('1357', '1', '75');
INSERT INTO eova_role_btn VALUES ('1358', '1', '76');
INSERT INTO eova_role_btn VALUES ('1359', '1', '77');
INSERT INTO eova_role_btn VALUES ('1360', '1', '78');
INSERT INTO eova_role_btn VALUES ('1361', '1', '79');
INSERT INTO eova_role_btn VALUES ('1362', '1', '80');
INSERT INTO eova_role_btn VALUES ('1363', '1', '81');
INSERT INTO eova_role_btn VALUES ('1364', '1', '86');
INSERT INTO eova_role_btn VALUES ('1365', '1', '87');
INSERT INTO eova_role_btn VALUES ('1366', '1', '96');
INSERT INTO eova_role_btn VALUES ('1367', '1', '97');
INSERT INTO eova_role_btn VALUES ('1368', '1', '98');
INSERT INTO eova_role_btn VALUES ('1369', '1', '99');
INSERT INTO eova_role_btn VALUES ('1370', '1', '7');
INSERT INTO eova_role_btn VALUES ('1371', '1', '29');
INSERT INTO eova_role_btn VALUES ('1372', '1', '30');
INSERT INTO eova_role_btn VALUES ('1373', '1', '31');
INSERT INTO eova_role_btn VALUES ('1374', '1', '8');
INSERT INTO eova_role_btn VALUES ('1375', '1', '26');
INSERT INTO eova_role_btn VALUES ('1376', '1', '27');
INSERT INTO eova_role_btn VALUES ('1377', '1', '28');
INSERT INTO eova_role_btn VALUES ('1378', '1', '32');
INSERT INTO eova_role_btn VALUES ('1379', '1', '9');
INSERT INTO eova_role_btn VALUES ('1380', '1', '1');
INSERT INTO eova_role_btn VALUES ('1381', '1', '10');
INSERT INTO eova_role_btn VALUES ('1382', '1', '11');
INSERT INTO eova_role_btn VALUES ('1383', '1', '12');
INSERT INTO eova_role_btn VALUES ('1384', '1', '23');
INSERT INTO eova_role_btn VALUES ('1385', '1', '2');
INSERT INTO eova_role_btn VALUES ('1386', '1', '16');
INSERT INTO eova_role_btn VALUES ('1387', '1', '17');
INSERT INTO eova_role_btn VALUES ('1388', '1', '18');
INSERT INTO eova_role_btn VALUES ('1389', '1', '3');
INSERT INTO eova_role_btn VALUES ('1390', '1', '19');
INSERT INTO eova_role_btn VALUES ('1391', '1', '20');
INSERT INTO eova_role_btn VALUES ('1392', '1', '21');
INSERT INTO eova_role_btn VALUES ('1393', '1', '22');
INSERT INTO eova_role_btn VALUES ('1394', '1', '4');
INSERT INTO eova_role_btn VALUES ('1395', '1', '13');
INSERT INTO eova_role_btn VALUES ('1396', '1', '14');
INSERT INTO eova_role_btn VALUES ('1397', '1', '15');
INSERT INTO eova_role_btn VALUES ('1398', '1', '5');
INSERT INTO eova_role_btn VALUES ('1399', '1', '24');
INSERT INTO eova_role_btn VALUES ('1400', '1', '25');
INSERT INTO eova_role_btn VALUES ('1401', '1', '6');

-- ----------------------------
-- Table structure for eova_user
-- ----------------------------
DROP TABLE eova_user;
CREATE TABLE eova_user (
id NUMBER(11) NOT NULL ,
loginId NVARCHAR2(30) NOT NULL ,
loginPwd NVARCHAR2(50) NOT NULL ,
nickName NVARCHAR2(255) NOT NULL ,
rid NUMBER(11) NULL 
)

;
COMMENT ON COLUMN eova_user.loginId IS '帐号';
COMMENT ON COLUMN eova_user.loginPwd IS '密码';
COMMENT ON COLUMN eova_user.nickName IS '中文名';
COMMENT ON COLUMN eova_user.rid IS '角色ID';

-- ----------------------------
-- Records of eova_user
-- ----------------------------
INSERT INTO eova_user VALUES ('1', 'admin', '000000', 'Jieven', '1');
INSERT INTO eova_user VALUES ('3', 'test', '000000', '测试', '2');

-- ----------------------------
-- Primary Key structure for table eova_button
-- ----------------------------
ALTER TABLE eova_button ADD PRIMARY KEY (id);

-- ----------------------------
-- Primary Key structure for table eova_dict
-- ----------------------------
ALTER TABLE eova_dict ADD PRIMARY KEY (id);

-- ----------------------------
-- Primary Key structure for table eova_item
-- ----------------------------
ALTER TABLE eova_item ADD PRIMARY KEY (id);

-- ----------------------------
-- Primary Key structure for table eova_log
-- ----------------------------
ALTER TABLE eova_log ADD PRIMARY KEY (id);

-- ----------------------------
-- Primary Key structure for table eova_menu
-- ----------------------------
ALTER TABLE eova_menu ADD PRIMARY KEY (id);

-- ----------------------------
-- Primary Key structure for table eova_menu_object
-- ----------------------------
ALTER TABLE eova_menu_object ADD PRIMARY KEY (id);

-- ----------------------------
-- Primary Key structure for table eova_object
-- ----------------------------
ALTER TABLE eova_object ADD PRIMARY KEY (id);

-- ----------------------------
-- Primary Key structure for table eova_role
-- ----------------------------
ALTER TABLE eova_role ADD PRIMARY KEY (id);

-- ----------------------------
-- Primary Key structure for table eova_role_btn
-- ----------------------------
ALTER TABLE eova_role_btn ADD PRIMARY KEY (id);

-- ----------------------------
-- Primary Key structure for table eova_user
-- ----------------------------
ALTER TABLE eova_user ADD PRIMARY KEY (id);
