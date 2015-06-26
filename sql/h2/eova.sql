-- ----------------------------
-- Table structure for eova_button
-- ----------------------------
DROP TABLE IF EXISTS eova_button;
CREATE TABLE eova_button (
  id int(11) NOT NULL AUTO_INCREMENT,
  menuCode varchar(255) NOT NULL DEFAULT '' COMMENT '菜单Code',
  name varchar(255) NOT NULL DEFAULT '' COMMENT '按钮名称',
  ui varchar(255) NOT NULL DEFAULT '' COMMENT '按钮UI路径',
  bs varchar(500) DEFAULT '' COMMENT '按钮BS路径',
  indexNum int(11) DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (id)
) AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;

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
DROP TABLE IF EXISTS eova_dict;
CREATE TABLE eova_dict (
  id int(11) NOT NULL AUTO_INCREMENT,
  value varchar(50) NOT NULL,
  name varchar(50) NOT NULL,
  class varchar(50) NOT NULL,
  field varchar(50) NOT NULL,
  PRIMARY KEY (id)
) AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

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
DROP TABLE IF EXISTS eova_item;
CREATE TABLE eova_item (
  id int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  objectCode varchar(50) NOT NULL,
  poCode varchar(255) DEFAULT '' COMMENT '持久化对象',
  en varchar(30) NOT NULL COMMENT '英文名',
  cn varchar(30) NOT NULL DEFAULT '' COMMENT '中文名',
  isAuto boolean DEFAULT '0' COMMENT '主键是否自增长',
  dataType varchar(20) DEFAULT 'string' COMMENT '数据类型',
  type varchar(10) DEFAULT '文本框' COMMENT '控件类型',
  indexNum int(4) DEFAULT '9' COMMENT '排序索引',
  exp varchar(800) DEFAULT '' COMMENT '控件表达式',
  isQuery boolean DEFAULT '0' COMMENT '是否可查询',
  isShow boolean DEFAULT '1' COMMENT '是否可显示',
  isOrder boolean DEFAULT '1' COMMENT '是否可排序',
  isAdd boolean DEFAULT '1' COMMENT '是否可新增字段',
  isUpdate boolean DEFAULT '1' COMMENT '是否可修改字段',
  isEdit boolean DEFAULT '1' COMMENT '是否可编辑字段',
  isNotNull boolean DEFAULT '1' COMMENT '是否必填',
  placeholder varchar(255) DEFAULT '' COMMENT '输入提示',
  validate varchar(255) DEFAULT '' COMMENT '校验表达式',
  valueExp varchar(255) DEFAULT '' COMMENT '默认值表达式',
  width int(4) DEFAULT '130' COMMENT '控件宽度',
  height int(4) DEFAULT '20' COMMENT '控件高度',
  isMultiple boolean DEFAULT '0' COMMENT '是否多选',
  PRIMARY KEY (id)
) AUTO_INCREMENT=158 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_item
-- ----------------------------
INSERT INTO eova_item VALUES ('1', 'eova_user_code', '', 'id', 'ID', '1', 'number', '自增框', '0', '', '0', '1', '1', '1', '1', '0', '1', '', '', '', '130', '0', '0');
INSERT INTO eova_item VALUES ('2', 'eova_user_code', '', 'nickName', '昵称', '0', 'string', '文本框', '0', '', '1', '1', '1', '1', '1', '0', '1', '', '', '', '130', '0', '0');
INSERT INTO eova_item VALUES ('3', 'eova_user_code', '', 'loginId', '登录帐号', '0', 'string', '上传框', '0', '', '1', '1', '1', '1', '1', '0', '1', '', '', '', '130', '0', '0');
INSERT INTO eova_item VALUES ('4', 'eova_user_code', '', 'loginPwd', '登录密码', '0', 'string', '文本框', '0', '', '0', '0', '0', '1', '1', '0', '1', '', '', '', '130', '0', '0');
INSERT INTO eova_item VALUES ('5', 'eova_menu_code', '', 'id', 'ID', '1', 'number', '自增框', '1', '', '0', '0', '1', '1', '1', '0', '1', '', '', '', '100', '0', '0');
INSERT INTO eova_item VALUES ('6', 'eova_menu_code', '', 'code', '编码', '0', 'string', '文本框', '4', '', '1', '1', '1', '1', '0', '0', '1', '', '', '', '180', '0', '0');
INSERT INTO eova_item VALUES ('7', 'eova_menu_code', '', 'name', '名称', '0', 'string', '文本框', '2', '', '1', '1', '1', '1', '1', '0', '1', '', '', '', '180', '0', '0');
INSERT INTO eova_item VALUES ('8', 'eova_menu_code', '', 'type', '类型', '0', 'string', '文本框', '1', '', '0', '1', '1', '1', '1', '0', '1', '', '', '', '100', '0', '0');
INSERT INTO eova_item VALUES ('10', 'eova_menu_code', '', 'icon', '图标', '0', 'string', '图标框', '6', '', '0', '0', '1', '1', '1', '0', '1', '', '', '', '130', '0', '0');
INSERT INTO eova_item VALUES ('11', 'eova_menu_code', '', 'indexNum', '序号', '0', 'number', '文本框', '9', '', '0', '1', '1', '1', '1', '0', '1', '', '', '', '30', '0', '0');
INSERT INTO eova_item VALUES ('12', 'eova_menu_code', '', 'parentId', '父节点', '0', 'number', '查找框', '9', 'select id ID,name 菜单名称 from eova_menu where 1=1;ds=eova', '1', '1', '1', '1', '1', '0', '1', '', '', '', '100', '0', '0');
INSERT INTO eova_item VALUES ('13', 'eova_object_code', '', 'id', 'ID', '1', 'number', '自增框', '1', '', '0', '0', '1', '1', '1', '0', '1', '', '', '', '130', '0', '0');
INSERT INTO eova_item VALUES ('14', 'eova_object_code', '', 'code', '编码', '0', 'string', '文本框', '2', '', '1', '1', '1', '1', '0', '0', '1', '', '', '', '200', '0', '0');
INSERT INTO eova_item VALUES ('15', 'eova_object_code', '', 'name', '名称', '0', 'string', '文本框', '3', '', '0', '1', '1', '1', '1', '0', '1', '', '', '', '130', '0', '0');
INSERT INTO eova_item VALUES ('16', 'eova_object_code', '', 'view', '视图', '0', 'string', '文本框', '4', '', '1', '1', '1', '1', '1', '0', '0', '', '', '', '70', '0', '0');
INSERT INTO eova_item VALUES ('17', 'eova_object_code', '', 'table', '数据表', '0', 'string', '文本框', '5', '', '1', '1', '1', '1', '1', '0', '0', '', '', '', '70', '0', '0');
INSERT INTO eova_item VALUES ('18', 'eova_object_code', '', 'pkName', '主键', '0', 'string', '文本框', '6', '', '0', '1', '1', '1', '1', '0', '0', '', '', '', '70', '0', '0');
INSERT INTO eova_item VALUES ('19', 'eova_object_code', '', 'dataSource', '数据源', '0', 'string', '下拉框', '7', 'select value ID,name CN from eova_dict where class = \'eova_object\' and field = \'dataSource\';ds=eova', '0', '1', '1', '1', '1', '0', '1', '', '', '', '70', '0', '0');
INSERT INTO eova_item VALUES ('20', 'eova_object_code', '', 'isSingleSelect', '是否单选', '0', 'number', '复选框', '8', '', '0', '1', '1', '1', '1', '0', '0', '', '', '', '70', '0', '0');
INSERT INTO eova_item VALUES ('21', 'eova_object_code', '', 'isShowNum', '显示行号', '0', 'number', '复选框', '9', '', '0', '1', '1', '1', '1', '0', '0', '', '', '', '70', '0', '0');
INSERT INTO eova_item VALUES ('22', 'eova_object_code', '', 'isDefaultPkDesc', '默认逆序', '0', 'number', '复选框', '10', '', '0', '1', '1', '1', '1', '0', '0', '', '', '', '70', '0', '0');
INSERT INTO eova_item VALUES ('23', 'eova_object_code', '', 'filterWhere', '过滤条件', '0', 'string', '文本域', '11', '', '0', '1', '1', '1', '1', '0', '0', '', '', '', '130', '0', '0');
INSERT INTO eova_item VALUES ('24', 'eova_object_code', '', 'diyCard', 'DIY卡片面板', '0', 'string', '文本域', '12', '', '0', '1', '1', '1', '1', '0', '0', '', '', '', '130', '0', '0');
INSERT INTO eova_item VALUES ('25', 'eova_object_code', '', 'diyList', 'DIY列表面板', '0', 'string', '文本域', '13', '', '0', '0', '1', '1', '1', '0', '0', '', '', '', '130', '0', '0');
INSERT INTO eova_item VALUES ('26', 'eova_item_code', '', 'id', 'ID', '1', 'number', '自增框', '1', '', '0', '0', '1', '1', '1', '0', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('27', 'eova_item_code', '', 'objectCode', '对象编码', '0', 'string', '查找框', '2', 'select code 编码,name 名称 from eova_object where 1=1;ds=eova', '1', '1', '1', '1', '1', '0', '1', '', '', 'eova_user_code', '130', '20', '0');
INSERT INTO eova_item VALUES ('28', 'eova_item_code', '', 'en', '字段名', '0', 'string', '文本框', '3', '', '1', '1', '1', '1', '1', '1', '1', '数据库的字段名', '', '', '70', '20', '0');
INSERT INTO eova_item VALUES ('29', 'eova_item_code', '', 'cn', '中文名', '0', 'string', '文本框', '4', '', '1', '1', '1', '1', '1', '1', '1', '字段对应的中文描述', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('30', 'eova_item_code', '', 'isAuto', '自增长', '0', 'number', '复选框', '20', '', '0', '1', '1', '1', '1', '1', '0', '', '', '', '70', '20', '0');
INSERT INTO eova_item VALUES ('31', 'eova_item_code', '', 'dataType', '字段类型', '0', 'string', '下拉框', '6', 'select value ID,name CN from eova_dict where class = \'eova_item\' and field = \'dataType\';ds=eova', '0', '1', '1', '1', '1', '0', '1', '', '', 'string', '70', '20', '0');
INSERT INTO eova_item VALUES ('32', 'eova_item_code', '', 'type', '控件类型', '0', 'string', '下拉框', '7', 'select value ID,name CN from eova_dict where class = \'eova_item\' and field = \'Type\';ds=eova', '1', '1', '1', '1', '1', '1', '1', '', '', '文本框', '70', '20', '0');
INSERT INTO eova_item VALUES ('33', 'eova_item_code', '', 'indexNum', '排序', '0', 'number', '文本框', '8', '', '0', '1', '1', '1', '1', '1', '1', '', '', '0', '50', '20', '0');
INSERT INTO eova_item VALUES ('34', 'eova_item_code', '', 'exp', '表达式', '0', 'string', '文本域', '31', '', '0', '1', '1', '1', '1', '0', '0', '查找框和下拉框需需要表达式', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('35', 'eova_item_code', '', 'isQuery', '允许查询', '0', 'number', '复选框', '21', '', '0', '1', '1', '1', '1', '1', '0', '', '', '', '70', '20', '0');
INSERT INTO eova_item VALUES ('36', 'eova_item_code', '', 'isShow', '允许显示', '0', 'number', '复选框', '22', '', '0', '1', '1', '1', '1', '1', '0', '', '', '1', '70', '20', '0');
INSERT INTO eova_item VALUES ('37', 'eova_item_code', '', 'isOrder', '允许排序', '0', 'number', '复选框', '23', '', '0', '1', '1', '1', '1', '1', '0', '', '', '1', '70', '20', '0');
INSERT INTO eova_item VALUES ('38', 'eova_item_code', '', 'isAdd', '允许新增', '0', 'number', '复选框', '24', '', '0', '1', '1', '1', '1', '1', '0', '', '', '1', '70', '20', '0');
INSERT INTO eova_item VALUES ('39', 'eova_item_code', '', 'isUpdate', '允许修改', '0', 'number', '复选框', '25', '', '0', '1', '1', '1', '1', '1', '0', '', '', '1', '70', '20', '0');
INSERT INTO eova_item VALUES ('40', 'eova_item_code', '', 'isNotNull', '是否必填', '0', 'number', '复选框', '25', '', '0', '1', '1', '1', '1', '1', '0', '', '', '1', '70', '20', '0');
INSERT INTO eova_item VALUES ('41', 'eova_item_code', '', 'valueExp', '默认值表达式', '0', 'string', '文本域', '32', '', '0', '1', '1', '1', '1', '1', '0', '初始默认值', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('42', 'eova_item_code', '', 'width', '宽度', '0', 'number', '文本框', '17', '', '0', '1', '1', '1', '1', '1', '1', '', '', '130', '50', '20', '0');
INSERT INTO eova_item VALUES ('43', 'eova_item_code', '', 'height', '高度', '0', 'number', '文本框', '18', '', '0', '1', '1', '1', '1', '1', '1', '', '', '80', '50', '20', '0');
INSERT INTO eova_item VALUES ('44', 'eova_item_code', '', 'isMultiple', '允许多选', '0', 'number', '复选框', '26', '', '0', '1', '1', '1', '1', '1', '0', '', '', '', '70', '20', '0');
INSERT INTO eova_item VALUES ('45', 'eova_button_code', '', 'id', 'ID', '1', 'number', '自增框', '1', '', '0', '1', '1', '1', '1', '0', '0', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('46', 'eova_button_code', '', 'menuCode', '菜单编码', '0', 'string', '查找框', '2', 'select code 菜单编码,name 菜单名称 from eova_menu where 1=1;ds=eova', '1', '1', '1', '1', '1', '0', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('47', 'eova_button_code', '', 'name', '功能名称', '0', 'string', '文本框', '4', '', '1', '1', '1', '1', '1', '0', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('48', 'eova_button_code', '', 'ui', 'UI路径', '0', 'string', '文本框', '5', '', '1', '1', '1', '1', '1', '0', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('49', 'eova_button_code', '', 'bs', 'BS路径', '0', 'string', '文本框', '6', '', '1', '1', '1', '1', '1', '0', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('50', 'eova_dict_code', '', 'id', 'ID', '1', 'number', '自增框', '1', '', '0', '1', '1', '1', '1', '0', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('51', 'eova_dict_code', '', 'value', '值', '0', 'string', '文本框', '2', '', '1', '1', '1', '1', '1', '0', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('52', 'eova_dict_code', '', 'valueen', '字段名', '0', 'string', '文本框', '3', '', '1', '1', '1', '1', '1', '0', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('53', 'eova_dict_code', '', 'valuecn', '列名', '0', 'string', '文本框', '4', '', '1', '1', '1', '1', '1', '0', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('54', 'eova_dict_code', '', 'object', '对象', '0', 'string', '文本框', '5', '', '1', '1', '1', '1', '1', '0', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('55', 'eova_dict_code', '', 'item', 'item', '0', 'string', '文本框', '6', '', '1', '1', '1', '1', '1', '0', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('59', 'eova_menu_code', '', 'bizIntercept', '自定义业务拦截器', '0', 'string', '文本域', '14', '', '0', '1', '1', '1', '1', '0', '0', '', '', '', '300', '0', '0');
INSERT INTO eova_item VALUES ('60', 'eova_button_code', '', 'indexNum', '序号', '0', 'number', '文本框', '9', '', '0', '1', '1', '1', '1', '0', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('61', 'eova_role_code', '', 'id', 'ID', '1', 'number', '自增框', '0', '', '0', '1', '1', '1', '1', '0', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('62', 'eova_role_code', '', 'name', '角色名', '0', 'string', '文本框', '0', '', '1', '1', '1', '1', '1', '0', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('63', 'eova_role_code', '', 'info', '角色描述', '0', 'string', '文本框', '0', '', '0', '1', '1', '1', '1', '0', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('64', 'eova_user_code', '', 'rid', '角色', '0', 'string', '下拉框', '0', 'select id ID,name CN from eova_role where 1=1;ds=eova', '0', '1', '1', '1', '1', '0', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('70', 'eova_log_code', '', 'id', 'id', '1', 'number', '自增框', '1', '', '0', '1', '1', '1', '1', '0', '0', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('71', 'eova_log_code', '', 'uid', '操作用户', '0', 'number', '查找框', '2', 'select id UID,nickName 用户名 from eova_user where 1=1;ds=eova', '1', '1', '1', '1', '1', '0', '0', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('72', 'eova_log_code', '', 'type', '日志类型', '0', 'number', '文本框', '3', 'select value ID,name CN from eova_dict where class = \'eova_log\' and field = \'type\';ds=eova', '1', '1', '1', '1', '1', '0', '0', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('73', 'eova_log_code', '', 'ip', '操作IP', '0', 'string', '文本框', '4', '', '1', '1', '1', '1', '1', '0', '0', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('74', 'eova_log_code', '', 'info', '操作详情', '0', 'string', '文本框', '5', '', '0', '1', '1', '1', '1', '0', '1', '', '', '', '200', '20', '0');
INSERT INTO eova_item VALUES ('75', 'player_code', '', 'id', 'ID', '1', 'number', '自增框', '1', '', '0', '1', '1', '1', '1', '0', '0', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('76', 'player_code', '', 'status', '状态', '0', 'number', '下拉框', '2', 'select value ID,name CN from dict where class = \'users\' and field = \'status\';ds=main', '1', '1', '1', '1', '1', '1', '1', '', '', '0', '130', '20', '0');
INSERT INTO eova_item VALUES ('77', 'player_code', '', 'loginId', '登录账户', '0', 'string', '文本框', '3', '', '1', '1', '1', '1', '1', '1', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('78', 'player_code', '', 'loginPwd', '录登密码', '0', 'string', '文本框', '4', '', '0', '0', '1', '1', '1', '1', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('79', 'player_code', '', 'nickName', '艺人姓名', '1', 'string', '文本框', '1', '', '1', '1', '1', '1', '1', '1', '1', '', '', '', '130', '20', '1');
INSERT INTO eova_item VALUES ('80', 'player_code', '', 'regTime', '注册时间', '1', 'time', '时间框', '6', '', '1', '1', '1', '1', '1', '1', '1', '', '', 'CURRENT_TIMESTAMP', '180', '20', '1');
INSERT INTO eova_item VALUES ('81', 'eova_menu_code', '', 'url', 'URL', '0', 'string', '文本框', '15', '', '0', '1', '1', '1', '1', '0', '0', '', '', '', '130', '0', '0');
INSERT INTO eova_item VALUES ('82', 'eova_item_code', '', 'isEdit', '允许行内编辑', '0', 'number', '复选框', '25', '', '0', '1', '1', '1', '1', '1', '0', '', '', '', '70', '20', '0');
INSERT INTO eova_item VALUES ('83', 'eova_object_code', '', 'isCellEdit', '行内编辑', '0', 'number', '复选框', '8', '', '0', '1', '1', '1', '1', '0', '0', '', '', '', '70', '0', '0');
INSERT INTO eova_item VALUES ('84', 'player_code', '', 'info', '备注', '0', 'string', '编辑框', '9', '', '0', '1', '1', '0', '0', '0', '0', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('85', 'item_code', '', 'id', 'ID', '1', 'number', '自增框', '1', '', '1', '1', '1', '1', '1', '1', '1', '', '', '', '130', '20', '1');
INSERT INTO eova_item VALUES ('86', 'item_code', '', 'name', '名称', '0', 'string', '文本框', '2', '', '1', '1', '1', '1', '1', '1', '1', '', '', '', '130', '20', '1');
INSERT INTO eova_item VALUES ('87', 'item_code', '', 'info', '介绍', '1', 'string', '编辑框', '3', '', '1', '1', '1', '1', '1', '1', '1', '', '', '', '200', '20', '1');
INSERT INTO eova_item VALUES ('88', 'users_item_code', '', 'id', 'ID', '1', 'number', '自增框', '1', '', '0', '1', '1', '1', '1', '1', '0', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('89', 'users_item_code', '', 'usersId', '艺人', '1', 'number', '查找框', '2', 'select id ID,nickName 艺人 from users where 1=1;ds=main', '1', '1', '1', '1', '1', '1', '1', '', '', '', '130', '20', '1');
INSERT INTO eova_item VALUES ('90', 'users_item_code', '', 'itemId', '道具', '0', 'number', '下拉框', '3', 'select id ID,name CN from item where 1=1;ds=main', '1', '1', '1', '1', '1', '1', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('124', 'v_users_code', 'player_code', 'id', 'ID', '1', 'number', '自增框', '1', '', '0', '1', '1', '1', '1', '1', '0', '', '', '0', '130', '20', '0');
INSERT INTO eova_item VALUES ('125', 'v_users_code', 'player_code', 'status', '状态', '0', 'number', '文本框', '2', '', '1', '1', '1', '1', '1', '1', '1', '', '', '0', '130', '20', '0');
INSERT INTO eova_item VALUES ('126', 'v_users_code', 'player_code', 'loginId', '登录账户', '0', 'string', '文本框', '3', '', '1', '1', '1', '1', '1', '1', '1', '请输入帐号', 'min : 5, max : 7', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('127', 'v_users_code', 'player_code', 'loginPwd', '录登密码', '0', 'string', '文本框', '4', '', '0', '1', '1', '1', '1', '1', '0', '请输入密码', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('128', 'v_users_code', 'player_code', 'nickName', '昵称', '0', 'string', '文本域', '20', '', '1', '1', '1', '1', '1', '1', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('129', 'v_users_code', 'player_code', 'regTime', '注册时间', '0', 'time', '时间框', '6', '', '0', '1', '1', '1', '1', '1', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('130', 'v_users_code', 'player_code', 'info', '备注', '0', 'string', '文本域', '30', '', '0', '1', '1', '1', '1', '1', '0', '', 'min : 8, max : 10', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('132', 'v_users_code', 'users_exp_code', 'exp', '经验值', '0', 'number', '文本框', '9', '', '0', '1', '1', '1', '1', '1', '1', '', '', '0', '130', '20', '0');
INSERT INTO eova_item VALUES ('133', 'v_users_code', 'users_exp_code', 'avg', '年龄', '0', 'number', '文本框', '10', '', '0', '1', '1', '1', '1', '1', '0', '', '', '0', '130', '20', '0');
INSERT INTO eova_item VALUES ('134', 'v_users_code', 'users_exp_code', 'qq', 'QQ', '0', 'string', '文本框', '22', '', '0', '1', '1', '1', '1', '1', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('146', 'celledit_users_code', '', 'id', 'id', '1', 'number', '自增框', '1', '', '0', '0', '1', '1', '1', '1', '0', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('147', 'celledit_users_code', '', 'status', '状态', '0', 'number', '文本框', '2', '', '0', '1', '1', '1', '1', '1', '1', '', '', '0', '130', '20', '0');
INSERT INTO eova_item VALUES ('148', 'celledit_users_code', '', 'loginId', '登录账户', '0', 'string', '文本框', '3', '', '0', '1', '1', '1', '1', '1', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('149', 'celledit_users_code', '', 'loginPwd', '录登密码', '0', 'string', '文本框', '4', '', '0', '1', '1', '1', '1', '1', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('150', 'celledit_users_code', '', 'nickName', '昵称', '0', 'string', '文本域', '5', '', '1', '1', '1', '1', '1', '1', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('151', 'celledit_users_code', '', 'regTime', '注册时间', '0', 'time', '时间框', '6', '', '0', '1', '1', '1', '1', '1', '1', '', '', 'CURRENT_TIMESTAMP', '130', '20', '0');
INSERT INTO eova_item VALUES ('152', 'celledit_users_code', '', 'info', '备注', '0', 'string', '文本域', '7', '', '0', '1', '1', '1', '1', '1', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('153', 'users_exp_code', '', 'uid', 'uid', '0', 'number', '文本框', '1', '', '0', '1', '1', '1', '1', '1', '0', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('154', 'users_exp_code', '', 'exp', '经验值', '0', 'number', '文本框', '2', '', '0', '1', '1', '1', '1', '1', '1', '', '', '0', '130', '20', '0');
INSERT INTO eova_item VALUES ('155', 'users_exp_code', '', 'avg', '年龄', '0', 'number', '文本框', '3', '', '0', '1', '1', '1', '1', '1', '1', '', '', '0', '130', '20', '0');
INSERT INTO eova_item VALUES ('156', 'users_exp_code', '', 'qq', 'QQ', '0', 'string', '文本框', '4', '', '0', '1', '1', '1', '1', '1', '1', '', '', '', '130', '20', '0');
INSERT INTO eova_item VALUES ('157', 'eova_item_code', '', 'placeholder', '输入提示', '0', 'string', '文本框', '28', '', '0', '1', '1', '1', '1', '1', '0', 'input的placeholder属性', '', '', '130', '20', '0');

-- ----------------------------
-- Table structure for eova_log
-- ----------------------------
DROP TABLE IF EXISTS eova_log;
CREATE TABLE eova_log (
  id int(11) NOT NULL AUTO_INCREMENT,
  uid int(11) NOT NULL COMMENT '操作用户',
  type int(11) NOT NULL COMMENT '日志类型',
  ip varchar(255) NOT NULL COMMENT '操作IP',
  info varchar(500) DEFAULT '' COMMENT '操作详情',
  PRIMARY KEY (id)
) AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_log
-- ----------------------------
INSERT INTO eova_log VALUES ('1', '1', '2', '127.0.0.1', 'player_code[84]');
INSERT INTO eova_log VALUES ('2', '1', '2', '127.0.0.1', 'eova_user_code[4]');
INSERT INTO eova_log VALUES ('3', '1', '2', '127.0.0.1', 'v_users_code[null]');
INSERT INTO eova_log VALUES ('4', '1', '2', '127.0.0.1', 'player_code[84]');
INSERT INTO eova_log VALUES ('5', '1', '2', '127.0.0.1', 'item_code[6]');
INSERT INTO eova_log VALUES ('6', '1', '2', '127.0.0.1', 'v_users_code[null]');
INSERT INTO eova_log VALUES ('7', '1', '2', '127.0.0.1', 'v_users_code[null]');
INSERT INTO eova_log VALUES ('8', '1', '2', '127.0.0.1', 'users_item_code[7]');
INSERT INTO eova_log VALUES ('9', '1', '2', '127.0.0.1', 'users_item_code[7]');
INSERT INTO eova_log VALUES ('10', '1', '2', '127.0.0.1', 'users_item_code[7]');
INSERT INTO eova_log VALUES ('11', '1', '2', '127.0.0.1', 'v_users_code[null]');
INSERT INTO eova_log VALUES ('12', '1', '2', '127.0.0.1', 'users_item_code[7]');
INSERT INTO eova_log VALUES ('13', '1', '2', '127.0.0.1', 'v_users_code[null]');
INSERT INTO eova_log VALUES ('14', '1', '2', '127.0.0.1', 'v_users_code[null]');
INSERT INTO eova_log VALUES ('15', '1', '2', '127.0.0.1', 'v_users_code[null]');
INSERT INTO eova_log VALUES ('16', '1', '2', '127.0.0.1', 'v_users_code[null]');
INSERT INTO eova_log VALUES ('17', '1', '2', '127.0.0.1', 'v_users_code[null]');
INSERT INTO eova_log VALUES ('18', '1', '3', '127.0.0.1', 'v_users_code[29]');
INSERT INTO eova_log VALUES ('19', '1', '3', '127.0.0.1', 'v_users_code[83]');
INSERT INTO eova_log VALUES ('20', '1', '2', '127.0.0.1', 'v_users_code[null]');
INSERT INTO eova_log VALUES ('21', '1', '2', '127.0.0.1', 'v_users_code[null]');
INSERT INTO eova_log VALUES ('22', '1', '2', '127.0.0.1', 'users_item_code[7]');
INSERT INTO eova_log VALUES ('23', '1', '2', '127.0.0.1', 'v_users_code[null]');
INSERT INTO eova_log VALUES ('24', '1', '2', '127.0.0.1', 'item_code[6]');

-- ----------------------------
-- Table structure for eova_menu
-- ----------------------------
DROP TABLE IF EXISTS eova_menu;
CREATE TABLE eova_menu (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(255) NOT NULL COMMENT '编码',
  name varchar(30) NOT NULL COMMENT '名称',
  type varchar(20) NOT NULL DEFAULT '1' COMMENT '菜单类型',
  icon varchar(255) DEFAULT '' COMMENT '图标路径',
  indexNum int(11) DEFAULT '0' COMMENT '序号',
  parentId int(11) DEFAULT '0' COMMENT '父节点',
  isCollapse boolean DEFAULT '0' COMMENT '是否折叠',
  bizIntercept varchar(255) DEFAULT '' COMMENT '自定义业务拦截器',
  url varchar(255) DEFAULT '' COMMENT '自定义URL',
  PRIMARY KEY (id)
) AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

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
DROP TABLE IF EXISTS eova_menu_object;
CREATE TABLE eova_menu_object (
  id int(11) NOT NULL AUTO_INCREMENT,
  menuCode varchar(50) NOT NULL COMMENT '菜单编码',
  objectCode varchar(50) NOT NULL COMMENT '对象编码',
  PRIMARY KEY (id)
) AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

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
DROP TABLE IF EXISTS eova_object;
CREATE TABLE eova_object (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(50) NOT NULL COMMENT '对象编码',
  name varchar(50) NOT NULL COMMENT '对象名称',
  view varchar(255) DEFAULT '' COMMENT '查询数据视图',
  table varchar(50) DEFAULT NULL COMMENT '保存数据主表',
  pkName varchar(50) NOT NULL COMMENT '主键',
  dataSource varchar(50) DEFAULT 'main' COMMENT '数据源',
  isSingleSelect boolean DEFAULT '1' COMMENT '是否单选',
  isCellEdit boolean DEFAULT '0' COMMENT '是否可行内编辑',
  isShowNum boolean DEFAULT '1' COMMENT '是否显示行号',
  isDefaultPkDesc boolean DEFAULT '1' COMMENT '是否默认根据主键逆序',
  filterWhere varchar(500) DEFAULT '' COMMENT '初始数据过滤条件',
  diyCard varchar(255) DEFAULT '' COMMENT '自定义卡片面板',
  diyList varchar(255) DEFAULT '' COMMENT '自定义列表面板',
  diyIntercept varchar(255) DEFAULT '' COMMENT '自定义业务拦截器',
  PRIMARY KEY (id)
) AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

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
DROP TABLE IF EXISTS eova_role;
CREATE TABLE eova_role (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL COMMENT '角色名',
  info varchar(255) DEFAULT '' COMMENT '角色描述',
  fun varchar(5000) DEFAULT '' COMMENT '已授权功能',
  PRIMARY KEY (id)
) AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

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
DROP TABLE IF EXISTS eova_role_btn;
CREATE TABLE eova_role_btn (
  id int(11) NOT NULL AUTO_INCREMENT,
  rid int(11) NOT NULL COMMENT '角色',
  bid int(11) NOT NULL COMMENT '功能',
  PRIMARY KEY (id)
) AUTO_INCREMENT=1348 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_role_btn
-- ----------------------------
INSERT INTO eova_role_btn VALUES ('1294', '1', '65');
INSERT INTO eova_role_btn VALUES ('1295', '1', '67');
INSERT INTO eova_role_btn VALUES ('1296', '1', '68');
INSERT INTO eova_role_btn VALUES ('1297', '1', '69');
INSERT INTO eova_role_btn VALUES ('1298', '1', '66');
INSERT INTO eova_role_btn VALUES ('1299', '1', '71');
INSERT INTO eova_role_btn VALUES ('1300', '1', '72');
INSERT INTO eova_role_btn VALUES ('1301', '1', '73');
INSERT INTO eova_role_btn VALUES ('1302', '1', '74');
INSERT INTO eova_role_btn VALUES ('1303', '1', '75');
INSERT INTO eova_role_btn VALUES ('1304', '1', '76');
INSERT INTO eova_role_btn VALUES ('1305', '1', '77');
INSERT INTO eova_role_btn VALUES ('1306', '1', '78');
INSERT INTO eova_role_btn VALUES ('1307', '1', '79');
INSERT INTO eova_role_btn VALUES ('1308', '1', '80');
INSERT INTO eova_role_btn VALUES ('1309', '1', '81');
INSERT INTO eova_role_btn VALUES ('1310', '1', '86');
INSERT INTO eova_role_btn VALUES ('1311', '1', '87');
INSERT INTO eova_role_btn VALUES ('1312', '1', '96');
INSERT INTO eova_role_btn VALUES ('1313', '1', '97');
INSERT INTO eova_role_btn VALUES ('1314', '1', '98');
INSERT INTO eova_role_btn VALUES ('1315', '1', '99');
INSERT INTO eova_role_btn VALUES ('1316', '1', '7');
INSERT INTO eova_role_btn VALUES ('1317', '1', '29');
INSERT INTO eova_role_btn VALUES ('1318', '1', '30');
INSERT INTO eova_role_btn VALUES ('1319', '1', '31');
INSERT INTO eova_role_btn VALUES ('1320', '1', '8');
INSERT INTO eova_role_btn VALUES ('1321', '1', '26');
INSERT INTO eova_role_btn VALUES ('1322', '1', '27');
INSERT INTO eova_role_btn VALUES ('1323', '1', '28');
INSERT INTO eova_role_btn VALUES ('1324', '1', '32');
INSERT INTO eova_role_btn VALUES ('1325', '1', '9');
INSERT INTO eova_role_btn VALUES ('1326', '1', '1');
INSERT INTO eova_role_btn VALUES ('1327', '1', '10');
INSERT INTO eova_role_btn VALUES ('1328', '1', '11');
INSERT INTO eova_role_btn VALUES ('1329', '1', '12');
INSERT INTO eova_role_btn VALUES ('1330', '1', '23');
INSERT INTO eova_role_btn VALUES ('1331', '1', '2');
INSERT INTO eova_role_btn VALUES ('1332', '1', '16');
INSERT INTO eova_role_btn VALUES ('1333', '1', '17');
INSERT INTO eova_role_btn VALUES ('1334', '1', '18');
INSERT INTO eova_role_btn VALUES ('1335', '1', '3');
INSERT INTO eova_role_btn VALUES ('1336', '1', '19');
INSERT INTO eova_role_btn VALUES ('1337', '1', '20');
INSERT INTO eova_role_btn VALUES ('1338', '1', '21');
INSERT INTO eova_role_btn VALUES ('1339', '1', '22');
INSERT INTO eova_role_btn VALUES ('1340', '1', '4');
INSERT INTO eova_role_btn VALUES ('1341', '1', '13');
INSERT INTO eova_role_btn VALUES ('1342', '1', '14');
INSERT INTO eova_role_btn VALUES ('1343', '1', '15');
INSERT INTO eova_role_btn VALUES ('1344', '1', '5');
INSERT INTO eova_role_btn VALUES ('1345', '1', '24');
INSERT INTO eova_role_btn VALUES ('1346', '1', '25');
INSERT INTO eova_role_btn VALUES ('1347', '1', '6');

-- ----------------------------
-- Table structure for eova_user
-- ----------------------------
DROP TABLE IF EXISTS eova_user;
CREATE TABLE eova_user (
  id int(11) NOT NULL AUTO_INCREMENT,
  loginId varchar(30) NOT NULL COMMENT '帐号',
  loginPwd varchar(50) NOT NULL COMMENT '密码',
  nickName varchar(255) NOT NULL COMMENT '中文名',
  rid int(11) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (id)
) AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of eova_user
-- ----------------------------
INSERT INTO eova_user VALUES ('1', 'admin', '000000', 'Jieven', '1');
INSERT INTO eova_user VALUES ('3', 'test', '000000', '测试', '2');
INSERT INTO eova_user VALUES ('4', '', '1232134', '举套问天我操谁', '5');
