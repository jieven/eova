drop table com_base_dalei;
drop table item;
drop table users;
drop table users_exp;
drop table users_item;
drop table webdict;

drop sequence seq_com_base_dalei;
drop sequence seq_item;
drop sequence seq_users;
drop sequence seq_users_exp;
drop sequence seq_users_item;
drop sequence seq_webdict;

create sequence seq_com_base_dalei increment by 1 start with 21 maxvalue 9999999999;
create sequence seq_item increment by 1 start with 91 maxvalue 9999999999;
create sequence seq_users increment by 1 start with 551 maxvalue 9999999999;
create sequence seq_users_exp increment by 1 start with 11111 maxvalue 9999999999;
create sequence seq_users_item increment by 1 start with 111 maxvalue 9999999999;
create sequence seq_webdict increment by 1 start with 41 maxvalue 9999999999;

create table com_base_dalei(
    id NUMBER(10) NOT NULL,
    pid NUMBER(10),
    tn_id NUMBER(10),
    layer NUMBER(3),
    arrange VARCHAR2(255),
    dldm VARCHAR2(20),
    dlmc VARCHAR2(100),
    dljm VARCHAR2(50),
    bz VARCHAR2(255),
    xtmr NUMBER(3),
    row_no NUMBER(10),
    alterdate NUMBER(10),
    lastchanged DATE NOT NULL,
    gid VARCHAR2(50),
    gbbjbtjclsp NUMBER(3)
);

alter table com_base_dalei add constraint pk_com_base_dalei primary key(id);
comment on column com_base_dalei.tn_id is '租户ID';
comment on column com_base_dalei.layer is '树层次';
comment on column com_base_dalei.arrange is '树结点链';
comment on column com_base_dalei.dldm is '大类代码';
comment on column com_base_dalei.dlmc is '大类名称';
comment on column com_base_dalei.dljm is '大类简码  - 备用';
comment on column com_base_dalei.bz is '备注';
comment on column com_base_dalei.xtmr is '系统默认';
comment on column com_base_dalei.gid is 'GUID字段';
comment on column com_base_dalei.gbbjbtjclsp is '各报表均不统计此类商品';
alter table com_base_dalei modify tn_id default '0';
alter table com_base_dalei modify layer default '0';
alter table com_base_dalei modify xtmr default '0';
alter table com_base_dalei modify lastchanged default 'CURRENT_TIMESTAMP';
alter table com_base_dalei modify gbbjbtjclsp default '0';

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

create table users(
    id NUMBER(10) NOT NULL,
    status NUMBER(10),
    login_id VARCHAR2(255),
    login_pwd VARCHAR2(255),
    nickname VARCHAR2(255),
    reg_time DATE,
    info VARCHAR2(255)
);

alter table users add constraint pk_users primary key(id);
comment on column users.status is '状态';
comment on column users.login_id is '登录账户';
comment on column users.login_pwd is '录登密码';
comment on column users.nickname is '昵称';
comment on column users.reg_time is '注册时间';
comment on column users.info is '备注';
alter table users modify status default '0';
alter table users modify reg_time default 'CURRENT_TIMESTAMP';

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

create table webdict(
    id NUMBER(10) NOT NULL,
    value VARCHAR2(50) NOT NULL,
    name VARCHAR2(50) NOT NULL,
    object VARCHAR2(50) NOT NULL,
    field VARCHAR2(50) NOT NULL,
    ext VARCHAR2(255) NOT NULL
);

alter table webdict add constraint pk_webdict primary key(id);
comment on column webdict.ext is '扩展Json';
