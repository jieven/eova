drop table eova_button;
drop table eova_dict;
drop table eova_field;
drop table eova_log;
drop table eova_menu;
drop table eova_menu_object;
drop table eova_object;
drop table eova_role;
drop table eova_role_btn;
drop table eova_task;
drop table eova_user;
drop table eova_widget;

drop sequence seq_eova_button;
drop sequence seq_eova_dict;
drop sequence seq_eova_field;
drop sequence seq_eova_log;
drop sequence seq_eova_menu;
drop sequence seq_eova_menu_object;
drop sequence seq_eova_object;
drop sequence seq_eova_role;
drop sequence seq_eova_role_btn;
drop sequence seq_eova_task;
drop sequence seq_eova_user;
drop sequence seq_eova_widget;

create sequence seq_eova_button increment by 1 start with 11281 maxvalue 9999999999;
create sequence seq_eova_dict increment by 1 start with 1011 maxvalue 9999999999;
create sequence seq_eova_field increment by 1 start with 31311 maxvalue 9999999999;
create sequence seq_eova_log increment by 1 start with 51 maxvalue 9999999999;
create sequence seq_eova_menu increment by 1 start with 10541 maxvalue 9999999999;
create sequence seq_eova_menu_object increment by 1 start with 01 maxvalue 9999999999;
create sequence seq_eova_object increment by 1 start with 11241 maxvalue 9999999999;
create sequence seq_eova_role increment by 1 start with 41 maxvalue 9999999999;
create sequence seq_eova_role_btn increment by 1 start with 2731 maxvalue 9999999999;
create sequence seq_eova_task increment by 1 start with 121 maxvalue 9999999999;
create sequence seq_eova_user increment by 1 start with 71 maxvalue 9999999999;
create sequence seq_eova_widget increment by 1 start with 331 maxvalue 9999999999;

create table eova_button(
    id NUMBER(10) NOT NULL,
    menu_code VARCHAR2(255) NOT NULL,
    name VARCHAR2(255) NOT NULL,
    icon VARCHAR2(255),
    ui VARCHAR2(255),
    bs VARCHAR2(500),
    order_num NUMBER(10),
    group_num NUMBER(10),
    is_base CHAR(1),
    is_hide CHAR(1)
);

alter table eova_button add constraint pk_eova_button primary key(id);
comment on column eova_button.menu_code is '菜单Code';
comment on column eova_button.name is '按钮名称';
comment on column eova_button.icon is '图标';
comment on column eova_button.ui is '按钮UI路径';
comment on column eova_button.bs is '按钮BS路径';
comment on column eova_button.order_num is '排序号';
comment on column eova_button.group_num is '分组号';
comment on column eova_button.is_base is '是否基础功能';
comment on column eova_button.is_hide is '是否删除';
alter table eova_button modify order_num default '0';
alter table eova_button modify group_num default '0';
alter table eova_button modify is_base default '0';
alter table eova_button modify is_hide default '0';

create table eova_dict(
    id NUMBER(10) NOT NULL,
    value VARCHAR2(50) NOT NULL,
    name VARCHAR2(50) NOT NULL,
    object VARCHAR2(50) NOT NULL,
    field VARCHAR2(50) NOT NULL
);

alter table eova_dict add constraint pk_eova_dict primary key(id);

create table eova_field(
    id NUMBER(10) NOT NULL,
    object_code VARCHAR2(50) NOT NULL,
    fieldnum NUMBER(10),
    order_num NUMBER(10),
    fieldset VARCHAR2(255),
    table_name VARCHAR2(255),
    en VARCHAR2(50) NOT NULL,
    cn VARCHAR2(50) NOT NULL,
    is_auto CHAR(1),
    type VARCHAR2(10),
    exp VARCHAR2(800),
    is_query CHAR(1),
    is_show CHAR(1),
    is_disable CHAR(1),
    is_order CHAR(1),
    is_add CHAR(1),
    is_update CHAR(1),
    is_edit CHAR(1),
    is_required CHAR(1),
    is_multiple CHAR(1),
    placeholder VARCHAR2(255),
    validator VARCHAR2(255),
    defaulter VARCHAR2(255),
    formatter VARCHAR2(2000),
    width NUMBER(10),
    height NUMBER(10),
    config VARCHAR2(2000),
    add_status NUMBER(10),
    update_status NUMBER(10),
    data_type NUMBER(10),
    data_type_name VARCHAR2(20),
    data_size NUMBER(10),
    data_decimal NUMBER(10)
);

alter table eova_field add constraint pk_eova_field primary key(id);
comment on column eova_field.id is 'ID';
comment on column eova_field.fieldnum is '表单分组序号';
comment on column eova_field.order_num is '排序索引';
comment on column eova_field.fieldset is '表单分组';
comment on column eova_field.table_name is '字段表名';
comment on column eova_field.en is '英文名';
comment on column eova_field.cn is '中文名';
comment on column eova_field.is_auto is '主键是否自增长';
comment on column eova_field.type is '控件类型';
comment on column eova_field.exp is '控件表达式';
comment on column eova_field.is_query is '是否可查询';
comment on column eova_field.is_show is '是否可显示';
comment on column eova_field.is_disable is '是否禁用';
comment on column eova_field.is_order is '是否可排序';
comment on column eova_field.is_add is '是否可新增字段';
comment on column eova_field.is_update is '是否可修改字段';
comment on column eova_field.is_edit is '是否可编辑字段';
comment on column eova_field.is_required is '是否必填';
comment on column eova_field.is_multiple is '是否多选项';
comment on column eova_field.placeholder is '输入提示';
comment on column eova_field.validator is 'UI校验表达式';
comment on column eova_field.defaulter is '默认值表达式';
comment on column eova_field.formatter is '格式化器';
comment on column eova_field.width is '控件宽度';
comment on column eova_field.height is '控件高度';
comment on column eova_field.config is '拓展配置';
comment on column eova_field.add_status is '状态：0=正常，10=只读，20=隐藏，50=禁用';
comment on column eova_field.update_status is '状态：0=正常，10=只读，20=隐藏，50=禁用';
comment on column eova_field.data_type is '数据类型';
comment on column eova_field.data_type_name is '数据类型名称';
comment on column eova_field.data_size is '整数位长度';
comment on column eova_field.data_decimal is '小数位长度';
alter table eova_field modify fieldnum default '0';
alter table eova_field modify order_num default '9';
alter table eova_field modify is_auto default '0';
alter table eova_field modify type default '文本框';
alter table eova_field modify is_query default '0';
alter table eova_field modify is_show default '1';
alter table eova_field modify is_disable default '0';
alter table eova_field modify is_order default '1';
alter table eova_field modify is_add default '1';
alter table eova_field modify is_update default '1';
alter table eova_field modify is_edit default '1';
alter table eova_field modify is_required default '1';
alter table eova_field modify is_multiple default '0';
alter table eova_field modify width default '130';
alter table eova_field modify height default '20';
alter table eova_field modify add_status default '0';
alter table eova_field modify update_status default '0';
alter table eova_field modify data_type default '12';
alter table eova_field modify data_type_name default 'VARCHAR';
alter table eova_field modify data_size default '1';
alter table eova_field modify data_decimal default '0';

create table eova_log(
    id NUMBER(10) NOT NULL,
    user_id NUMBER(10) NOT NULL,
    type NUMBER(10) NOT NULL,
    ip VARCHAR2(255) NOT NULL,
    info VARCHAR2(500),
    create_time DATE
);

alter table eova_log add constraint pk_eova_log primary key(id);
comment on column eova_log.user_id is '操作用户';
comment on column eova_log.type is '日志类型';
comment on column eova_log.ip is '操作IP';
comment on column eova_log.info is '操作详情';
comment on column eova_log.create_time is '操作时间';
alter table eova_log modify create_time default sysdate;

create table eova_menu(
    id NUMBER(10) NOT NULL,
    code VARCHAR2(255) NOT NULL,
    name VARCHAR2(100) NOT NULL,
    type VARCHAR2(20) NOT NULL,
    iconskip VARCHAR2(255),
    order_num NUMBER(10),
    parent_id NUMBER(10),
    open CHAR(1),
    biz_intercept VARCHAR2(255),
    url VARCHAR2(255),
    config VARCHAR2(500),
    diy_js VARCHAR2(255),
    is_hide CHAR(1),
    filter VARCHAR2(500)
);

alter table eova_menu add constraint pk_eova_menu primary key(id);
comment on column eova_menu.code is '编码';
comment on column eova_menu.name is '名称';
comment on column eova_menu.type is '菜单类型';
comment on column eova_menu.iconskip is '图标';
comment on column eova_menu.order_num is '序号';
comment on column eova_menu.parent_id is '父节点';
comment on column eova_menu.open is '是否展开';
comment on column eova_menu.biz_intercept is '自定义业务拦截器';
comment on column eova_menu.url is '自定义URL';
comment on column eova_menu.config is '菜单配置JSON';
comment on column eova_menu.diy_js is '依赖JS文件';
comment on column eova_menu.is_hide is '是否隐藏';
comment on column eova_menu.filter is '初始数据过滤条件';
alter table eova_menu modify order_num default '0';
alter table eova_menu modify parent_id default '0';
alter table eova_menu modify open default '1';
alter table eova_menu modify is_hide default '0';

create table eova_menu_object(
    id NUMBER(10) NOT NULL,
    menu_code VARCHAR2(50) NOT NULL,
    object_code VARCHAR2(50) NOT NULL,
    indexs NUMBER(10)
);

alter table eova_menu_object add constraint pk_eova_menu_object primary key(id);
comment on column eova_menu_object.menu_code is '菜单编码';
comment on column eova_menu_object.object_code is '对象编码';
comment on column eova_menu_object.indexs is '对象索引';
alter table eova_menu_object modify indexs default '0';

create table eova_object(
    id NUMBER(10) NOT NULL,
    code VARCHAR2(100) NOT NULL,
    name VARCHAR2(100) NOT NULL,
    view_name VARCHAR2(255),
    table_name VARCHAR2(255),
    pk_name VARCHAR2(50) NOT NULL,
    data_source VARCHAR2(50),
    is_single CHAR(1),
    is_celledit CHAR(1),
    is_show_num CHAR(1),
    is_first_load CHAR(1),
    filter VARCHAR2(500),
    default_order VARCHAR2(255),
    diy_card VARCHAR2(255),
    diy_js VARCHAR2(255),
    biz_intercept VARCHAR2(255),
    view_sql VARCHAR2(1000),
    config VARCHAR2(2000)
);

alter table eova_object add constraint pk_eova_object primary key(id);
comment on column eova_object.code is '对象编码';
comment on column eova_object.name is '对象名称';
comment on column eova_object.view_name is '查询数据视图';
comment on column eova_object.table_name is '保存数据主表';
comment on column eova_object.pk_name is '主键';
comment on column eova_object.data_source is '数据源';
comment on column eova_object.is_single is '是否单选';
comment on column eova_object.is_celledit is '是否可行内编辑';
comment on column eova_object.is_show_num is '是否显示行号';
comment on column eova_object.is_first_load is '是否初始加载';
comment on column eova_object.filter is '初始数据过滤条件';
comment on column eova_object.default_order is '默认排序字段(desc)';
comment on column eova_object.diy_card is '自定义卡片面板';
comment on column eova_object.diy_js is '依赖JS文件';
comment on column eova_object.biz_intercept is '自定义业务拦截器';
comment on column eova_object.view_sql is '视图SQL';
comment on column eova_object.config is '拓展配置';
alter table eova_object modify data_source default 'main';
alter table eova_object modify is_single default '1';
alter table eova_object modify is_celledit default '0';
alter table eova_object modify is_show_num default '1';
alter table eova_object modify is_first_load default '1';

create table eova_role(
    id NUMBER(10) NOT NULL,
    name VARCHAR2(255) NOT NULL,
    info VARCHAR2(255),
    lv NUMBER(10)
);

alter table eova_role add constraint pk_eova_role primary key(id);
comment on column eova_role.name is '角色名';
comment on column eova_role.info is '角色描述';
comment on column eova_role.lv is '权限级别';
alter table eova_role modify lv default '0';

create table eova_role_btn(
    id NUMBER(10) NOT NULL,
    rid NUMBER(10) NOT NULL,
    bid NUMBER(10) NOT NULL
);

alter table eova_role_btn add constraint pk_eova_role_btn primary key(id);
comment on column eova_role_btn.rid is '角色';
comment on column eova_role_btn.bid is '功能';

create table eova_task(
    id NUMBER(10) NOT NULL,
    state NUMBER(10) NOT NULL,
    name VARCHAR2(255) NOT NULL,
    exp VARCHAR2(50) NOT NULL,
    clazz VARCHAR2(255) NOT NULL,
    info VARCHAR2(255)
);

alter table eova_task add constraint pk_eova_task primary key(id);
comment on column eova_task.state is '状态：0=停止，1=启动';
comment on column eova_task.name is '名称';
comment on column eova_task.exp is '表达式';
comment on column eova_task.clazz is '实现类';
comment on column eova_task.info is '说明';
alter table eova_task modify state default '0';

create table eova_user(
    id NUMBER(10) NOT NULL,
    login_id VARCHAR2(30) NOT NULL,
    login_pwd VARCHAR2(50) NOT NULL,
    rid NUMBER(10)
);

alter table eova_user add constraint pk_eova_user primary key(id);
comment on column eova_user.login_id is '帐号';
comment on column eova_user.login_pwd is '密码';
comment on column eova_user.rid is '角色ID';
alter table eova_user modify rid default '0';

create table eova_widget(
    id NUMBER(10) NOT NULL,
    type NUMBER(10) NOT NULL,
    value VARCHAR2(50) NOT NULL,
    name VARCHAR2(50) NOT NULL,
    version VARCHAR2(5),
    path VARCHAR2(50),
    description VARCHAR2(4000),
    config VARCHAR2(4000)
);

alter table eova_widget add constraint pk_eova_widget primary key(id);
comment on column eova_widget.type is '控件类型：1=EOVA控件，2=DIY控件';
comment on column eova_widget.value is '控件值';
comment on column eova_widget.name is '名称';
comment on column eova_widget.version is '版本号';
comment on column eova_widget.path is '路径';
comment on column eova_widget.description is '介绍';
comment on column eova_widget.config is '控件配置信息JSON';
alter table eova_widget modify type default '1';
alter table eova_widget modify version default '1.0';