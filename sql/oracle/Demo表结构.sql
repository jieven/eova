drop table address;
drop table area;
drop table area_city;
drop table data_login;
drop table data_money;
drop table dicts;
drop table hotel;
drop table hotel_bed;
drop table hotel_stock;
drop table item;
drop table links;
drop table member;
drop table order_item;
drop table orders;
drop table product;
drop table sale_data;
drop table test_info;
drop table user_info;
drop table users;
drop table users_exp;
drop table users_item;

drop sequence seq_address;
drop sequence seq_area;
drop sequence seq_area_city;
drop sequence seq_data_login;
drop sequence seq_data_money;
drop sequence seq_dicts;
drop sequence seq_hotel;
drop sequence seq_hotel_bed;
drop sequence seq_hotel_stock;
drop sequence seq_item;
drop sequence seq_links;
drop sequence seq_member;
drop sequence seq_order_item;
drop sequence seq_orders;
drop sequence seq_product;
drop sequence seq_sale_data;
drop sequence seq_test_info;
drop sequence seq_user_info;
drop sequence seq_users;
drop sequence seq_users_exp;
drop sequence seq_users_item;

create sequence seq_address increment by 1 start with 1251 maxvalue 9999999999;
create sequence seq_area increment by 1 start with 34101 maxvalue 9999999999;
create sequence seq_area_city increment by 1 start with 421 maxvalue 9999999999;
create sequence seq_data_login increment by 1 start with 201 maxvalue 9999999999;
create sequence seq_data_money increment by 1 start with 101 maxvalue 9999999999;
create sequence seq_dicts increment by 1 start with 1731 maxvalue 9999999999;
create sequence seq_hotel increment by 1 start with 31 maxvalue 9999999999;
create sequence seq_hotel_bed increment by 1 start with 41 maxvalue 9999999999;
create sequence seq_hotel_stock increment by 1 start with 61 maxvalue 9999999999;
create sequence seq_item increment by 1 start with 71 maxvalue 9999999999;
create sequence seq_links increment by 1 start with 21 maxvalue 9999999999;
create sequence seq_member increment by 1 start with 131 maxvalue 9999999999;
create sequence seq_order_item increment by 1 start with 61 maxvalue 9999999999;
create sequence seq_orders increment by 1 start with 121 maxvalue 9999999999;
create sequence seq_product increment by 1 start with 301 maxvalue 9999999999;
create sequence seq_sale_data increment by 1 start with 431 maxvalue 9999999999;
create sequence seq_test_info increment by 1 start with 91 maxvalue 9999999999;
create sequence seq_user_info increment by 1 start with 71 maxvalue 9999999999;
create sequence seq_users increment by 1 start with 231 maxvalue 9999999999;
create sequence seq_users_exp increment by 1 start with 311 maxvalue 9999999999;
create sequence seq_users_item increment by 1 start with 61 maxvalue 9999999999;

create table address(
    id NUMBER(10) NOT NULL,
    name VARCHAR2(255),
    full VARCHAR2(255),
    mobilephone VARCHAR2(255)
);

alter table address add constraint pk_address primary key(id);
comment on column address.id is 'ID';
comment on column address.name is '姓名';
comment on column address.full is '详细地址';
comment on column address.mobilephone is '手机';

create table area(
    id NUMBER(10) NOT NULL,
    name VARCHAR2(50) NOT NULL,
    pid NUMBER(10) NOT NULL,
    lv NUMBER(10) NOT NULL
);

alter table area add constraint pk_area primary key(id);
comment on column area.id is 'ID';
comment on column area.name is '名称';
comment on column area.pid is '父级';
comment on column area.lv is '级别：0=中国,1=省,2=市,3=区';

create table area_city(
    id NUMBER(10) NOT NULL,
    name VARCHAR2(50) NOT NULL,
    code VARCHAR2(11) NOT NULL
);

alter table area_city add constraint pk_area_city primary key(id);
comment on column area_city.id is 'ID';
comment on column area_city.name is '名称';
comment on column area_city.code is '父级编码';

create table data_login(
    id NUMBER(10) NOT NULL,
    day DATE,
    num NUMBER(10),
    num1 NUMBER(10)
);

alter table data_login add constraint pk_data_login primary key(id);
comment on column data_login.day is '日期';
comment on column data_login.num is '每日活跃数';
comment on column data_login.num1 is '每日登录数';

create table data_money(
    id NUMBER(10) NOT NULL,
    moon VARCHAR2(11),
    num NUMBER(10),
    num1 NUMBER(10),
    num2 NUMBER(10)
);

alter table data_money add constraint pk_data_money primary key(id);
comment on column data_money.moon is '月份';
comment on column data_money.num is '手机销售额';
comment on column data_money.num1 is '电脑销售额';
comment on column data_money.num2 is '避孕套销售额';

create table dicts(
    id NUMBER(10) NOT NULL,
    value VARCHAR2(50) NOT NULL,
    name VARCHAR2(50) NOT NULL,
    object VARCHAR2(50) NOT NULL,
    field VARCHAR2(50) NOT NULL,
    ext VARCHAR2(255)
);

alter table dicts add constraint pk_dicts primary key(id);
comment on column dicts.value is '字典值';
comment on column dicts.name is '字典中文';
comment on column dicts.object is '表名';
comment on column dicts.field is '字段名';
comment on column dicts.ext is '扩展Json';

create table hotel(
    id NUMBER(10) NOT NULL,
    name VARCHAR2(255) NOT NULL,
    tel VARCHAR2(255) NOT NULL,
    address VARCHAR2(255) NOT NULL,
    state NUMBER(10),
    score NUMBER(10),
    create_time DATE NOT NULL,
    province NUMBER(10),
    city NUMBER(10),
    region NUMBER(10)
);

alter table hotel add constraint pk_hotel primary key(id);
comment on column hotel.id is 'ID';
comment on column hotel.name is '酒店名';
comment on column hotel.tel is '电话';
comment on column hotel.address is '详细地址';
comment on column hotel.state is '用户状态：1=普通商户，2=签约商户';
comment on column hotel.score is '积分';
comment on column hotel.create_time is '创建时间';
comment on column hotel.province is '省';
comment on column hotel.city is '市';
comment on column hotel.region is '区';
alter table hotel modify state default '1';
alter table hotel modify score default '0';

create table hotel_bed(
    id NUMBER(10) NOT NULL,
    hotel_id NUMBER(10) NOT NULL,
    sizes NUMBER(10) NOT NULL,
    num NUMBER(10)
);

alter table hotel_bed add constraint pk_hotel_bed primary key(id);
comment on column hotel_bed.id is 'ID';
comment on column hotel_bed.hotel_id is '酒店';
comment on column hotel_bed.sizes is '床铺尺码';
comment on column hotel_bed.num is '数量';
alter table hotel_bed modify num default '1';

create table hotel_stock(
    id NUMBER(10) NOT NULL,
    hotel_id NUMBER(10) NOT NULL,
    category NUMBER(10) NOT NULL,
    num NUMBER(10)
);

alter table hotel_stock add constraint pk_hotel_stock primary key(id);
comment on column hotel_stock.id is 'ID';
comment on column hotel_stock.hotel_id is '酒店';
comment on column hotel_stock.category is '商品类型';
comment on column hotel_stock.num is '存货量';
alter table hotel_stock modify num default '1';

create table item(
    id NUMBER(10) NOT NULL,
    name VARCHAR2(255) NOT NULL,
    info VARCHAR2(1000),
    img VARCHAR2(255)
);

alter table item add constraint pk_item primary key(id);
comment on column item.name is '名称';
comment on column item.info is '介绍';
comment on column item.img is '物品图片';

create table links(
    id NUMBER(10) NOT NULL,
    status NUMBER(10) NOT NULL,
    name VARCHAR2(255) NOT NULL,
    url VARCHAR2(255) NOT NULL,
    title VARCHAR2(255)
);

alter table links add constraint pk_links primary key(id);
comment on column links.id is 'ID';
comment on column links.status is '状态:1=正常,2=禁用';
comment on column links.name is '链接文本';
comment on column links.url is '链接地址';
comment on column links.title is '小标题';
alter table links modify status default '1';
alter table links modify url default 'http://www..com';

create table member(
    id NUMBER(10) NOT NULL,
    rid NUMBER(10),
    status NUMBER(10),
    nickname VARCHAR2(30),
    company_name VARCHAR2(255),
    mobile VARCHAR2(11),
    phone VARCHAR2(20),
    phone2 VARCHAR2(255),
    create_time DATE NOT NULL,
    province NUMBER(10),
    city NUMBER(10),
    region NUMBER(10),
    admin_province NUMBER(10),
    admin_city NUMBER(10),
    admin_region NUMBER(10)
);

alter table member add constraint pk_member primary key(id);
comment on column member.rid is '冗余角色ID';
comment on column member.status is '状态';
comment on column member.nickname is '昵称';
comment on column member.company_name is '单位名称';
comment on column member.mobile is '联系手机';
comment on column member.phone is '联系电话';
comment on column member.phone2 is '应急电话';
comment on column member.create_time is '注册时间';
comment on column member.province is '省';
comment on column member.city is '市';
comment on column member.region is '区';
comment on column member.admin_province is '管理省';
comment on column member.admin_city is '管理市';
comment on column member.admin_region is '管理区';
alter table member modify rid default '0';
alter table member modify status default '0';
alter table member modify create_time default sysdate;

create table order_item(
    id NUMBER(10) NOT NULL,
    order_id NUMBER(10) NOT NULL,
    product_id NUMBER(10) NOT NULL,
    product VARCHAR2(128) NOT NULL,
    price VARCHAR2(10) NOT NULL,
    num NUMBER(10) NOT NULL
);

alter table order_item add constraint pk_order_item primary key(id);
comment on column order_item.id is '编号';
comment on column order_item.order_id is '订单ID';
comment on column order_item.product_id is '产品ID';
comment on column order_item.product is '产品';
comment on column order_item.price is '单价';
comment on column order_item.num is '购买数量';
alter table order_item modify price default '0';
alter table order_item modify num default '1';

create table orders(
    id NUMBER(10) NOT NULL,
    hotel_id NUMBER(10),
    pay_id NUMBER(10),
    state NUMBER(10),
    money VARCHAR2(10),
    score VARCHAR2(10),
    memo VARCHAR2(256),
    create_user_id NUMBER(10) NOT NULL,
    update_user_id NUMBER(10) NOT NULL,
    create_time DATE NOT NULL,
    update_time DATE NOT NULL,
    is_invoice CHAR(1),
    additional_info VARCHAR2(1000),
    address VARCHAR2(500),
    consignee VARCHAR2(50),
    tel VARCHAR2(20),
    address_id NUMBER(10)
);

alter table orders add constraint pk_orders primary key(id);
comment on column orders.id is '编号';
comment on column orders.hotel_id is '所属酒店';
comment on column orders.pay_id is '支付ID';
comment on column orders.state is '订单状态：10=待支付,20=已支付,30=已发货,40=已收货';
comment on column orders.money is '应付金额';
comment on column orders.score is '消耗积分';
comment on column orders.memo is '备注';
comment on column orders.create_user_id is '创建用户ID';
comment on column orders.update_user_id is '最后更新用户ID';
comment on column orders.create_time is '创建时间';
comment on column orders.update_time is '更新时间';
comment on column orders.is_invoice is '是否开票';
comment on column orders.additional_info is '订单补充信息(JSON格式)';
comment on column orders.address is '收货地址';
comment on column orders.consignee is '收货人';
comment on column orders.tel is '联系方式';
comment on column orders.address_id is '收获地址';
alter table orders modify hotel_id default '0';
alter table orders modify pay_id default '0';
alter table orders modify state default '10';
alter table orders modify money default '0.00';
alter table orders modify score default '0.00';
alter table orders modify is_invoice default '0';

create table product(
    id NUMBER(10) NOT NULL,
    type NUMBER(10),
    category NUMBER(10) NOT NULL,
    stuff NUMBER(10) NOT NULL,
    sizes NUMBER(10) NOT NULL,
    name VARCHAR2(255) NOT NULL,
    img VARCHAR2(255),
    test_price VARCHAR2(22),
    price VARCHAR2(22),
    cost_score NUMBER(10),
    score NUMBER(10),
    stock NUMBER(10),
    create_time DATE NOT NULL,
    update_time DATE
);

alter table product add constraint pk_product primary key(id);
comment on column product.id is 'ID';
comment on column product.type is '产品类型：1=租赁商品，2=积分商品';
comment on column product.category is '分类';
comment on column product.stuff is '材料';
comment on column product.sizes is '尺码';
comment on column product.name is '名称';
comment on column product.img is '商品图片';
comment on column product.test_price is '试用单价';
comment on column product.price is '商品单价';
comment on column product.cost_score is '消耗积分：购买商品需消耗的积分';
comment on column product.score is '奖励积分';
comment on column product.stock is '库存';
comment on column product.create_time is '创建时间';
comment on column product.update_time is '更新时间';
alter table product modify type default '1';
alter table product modify test_price default '0';
alter table product modify price default '0';
alter table product modify cost_score default '0';
alter table product modify score default '0';
alter table product modify stock default '1';
alter table product modify update_time default sysdate;

create table sale_data(
    id NUMBER(10) NOT NULL,
    city_id NUMBER(10) NOT NULL,
    name VARCHAR2(255) NOT NULL,
    money VARCHAR2(22)
);

alter table sale_data add constraint pk_sale_data primary key(id);
comment on column sale_data.id is 'ID';
comment on column sale_data.city_id is '城市ID';
comment on column sale_data.name is '商品';
comment on column sale_data.money is '销售额';
alter table sale_data modify money default '0';

create table test_info(
    id NUMBER(10) NOT NULL,
    user_id NUMBER(10),
    status NUMBER(10),
    name VARCHAR2(255),
    age NUMBER(10),
    memo VARCHAR2(4000),
    avatar VARCHAR2(255),
    delete_flag CHAR(1),
    address VARCHAR2(255),
    id_card VARCHAR2(255),
    update_time DATE,
    create_time DATE,
    password VARCHAR2(255),
    color VARCHAR2(10),
    tag VARCHAR2(255),
    json VARCHAR2(500),
    test1 NUMBER(10),
    test2 VARCHAR2(12),
    test3 VARCHAR2(22),
    test4 CHAR(1),
    test5 NUMBER(20),
    test6 NUMBER(3),
    test7 DATE
);

alter table test_info add constraint pk_test_info primary key(id);
comment on column test_info.id is 'ID';
comment on column test_info.user_id is '用户';
comment on column test_info.status is '状态：0=普通，1=禁用';
comment on column test_info.name is '姓名';
comment on column test_info.age is '年龄';
comment on column test_info.memo is '备注';
comment on column test_info.avatar is '头像';
comment on column test_info.delete_flag is '是否删除';
comment on column test_info.address is '详细地址';
comment on column test_info.id_card is '身份证图片';
comment on column test_info.update_time is '更新日期';
comment on column test_info.create_time is '创建时间';
comment on column test_info.password is '密码';
comment on column test_info.color is '颜值';
comment on column test_info.tag is '标签';
comment on column test_info.json is '配置信息';
comment on column test_info.test7 is 'datetime';
alter table test_info modify status default '0';
alter table test_info modify create_time default sysdate;

create table user_info(
    id NUMBER(10) NOT NULL,
    rid NUMBER(10),
    status NUMBER(10),
    nickname VARCHAR2(30),
    mobile VARCHAR2(11),
    province NUMBER(10),
    city NUMBER(10),
    region NUMBER(10),
    create_time DATE NOT NULL
);

alter table user_info add constraint pk_user_info primary key(id);
comment on column user_info.rid is '冗余角色ID';
comment on column user_info.status is '状态';
comment on column user_info.nickname is '昵称';
comment on column user_info.mobile is '联系手机';
comment on column user_info.province is '省';
comment on column user_info.city is '市';
comment on column user_info.region is '区';
comment on column user_info.create_time is '创建时间';
alter table user_info modify rid default '0';
alter table user_info modify status default '0';
alter table user_info modify create_time default sysdate;

create table users(
    id NUMBER(10) NOT NULL,
    status NUMBER(10),
    login_id VARCHAR2(255),
    login_pwd VARCHAR2(255),
    nickname VARCHAR2(255),
    reg_time DATE,
    info VARCHAR2(255),
    tag VARCHAR2(255)
);

alter table users add constraint pk_users primary key(id);
comment on column users.status is '状态';
comment on column users.login_id is '登录账户';
comment on column users.login_pwd is '录登密码';
comment on column users.nickname is '昵称';
comment on column users.reg_time is '注册时间';
comment on column users.info is '备注';
comment on column users.tag is '标签';
alter table users modify status default '0';
alter table users modify reg_time default sysdate;

create table users_exp(
    users_id NUMBER(10) NOT NULL,
    exp NUMBER(10),
    avg NUMBER(10),
    qq VARCHAR2(255)
);

alter table users_exp add constraint pk_users_exp primary key(users_id);
comment on column users_exp.exp is '经验值';
comment on column users_exp.avg is '年龄';
comment on column users_exp.qq is 'QQ';
alter table users_exp modify exp default '0';
alter table users_exp modify avg default '0';

create table users_item(
    id NUMBER(10) NOT NULL,
    users_id NUMBER(10) NOT NULL,
    item_id NUMBER(10) NOT NULL
);

alter table users_item add constraint pk_users_item primary key(id);
comment on column users_item.id is 'ID';
comment on column users_item.users_id is '艺人';
comment on column users_item.item_id is '道具';