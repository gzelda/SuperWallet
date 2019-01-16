/*wallet项目数据库表设计*/
drop database if exists superWallet;
create database superWallet;
use superWallet;

-- 用户基本资料表--
--status：0-未实名认证 1-已实名认证--
drop table if exists userbasic;
create table userbasic(
    UID char(100) not null,
    nickName varchar(20) not null,
    sex tinyint not null,
    isAgency tinyint not null,
    headPhoto longblob,
    phoneNumber char(15) not null,
    inviter char(20),
    status tinyint,
    passWord varchar(100),
    payPassWord varchar(100),
    invitedCode varchar(100),
    registerTime timestamp not null default now(),
    primary key(UID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

--邀请人表--
drop table if exists inviter;
create table inviter(
    inviterID varchar(100) not null,
    beinvitedID varchar(100) not null,
    invitingTime timestamp not null default now(),
    primary key(inviterID,beinvitedID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 用户状态信息表--
-- 0-允许 1-禁用 --
drop table if exists userstatus;
create table userstatus(
    UID char(100) not null,
    state tinyint,
    updatedTime timestamp,
    primary key(UID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 用户隐私资料表--
drop table if exists userprivate;
create table userprivate(
    UID char(100) not null,
    realName varchar(20) not null,
    IDCardNumber char(20) not null,
    IDCardFront blob not null,
    IDCardBack blob not null,
    face blob not null,
    primary key(UID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

--代理人表--
drop table if exists agent;
create table agent(
    UID char(100) not null,
    createTime timestamp not null,
    nickName varchar(50) not null,
    sex tinyint not null,
    phoneNumber varchar(50) not null,
    lowerAmount int,
    earnings double,
    totalIncome double,
    primary key(UID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 中心化钱包--
--canLock 0-不可以 1-可以--
-- ETH--
drop table if exists ethtoken;
create table ethtoken(
    UID char(100) not null,
    ETHAddress varchar(50),
    amount double not null,
    canLock tinyint not null default 0,
    type int not null,
    primary key(UID,type)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- EOS--
drop table if exists eostoken;
create table eostoken(
    UID char(100) not null,
    EOSAccountName varchar(50),
    amount double not null,
    type int not null,
    canLock tinyint not null default 0,
    primary key(UID,type)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 私钥库--
--ETH私钥库--
drop table if exists ETHPriKeyWarehouse,EOSPriKeyWarehouse;
create table ETHPriKeyWarehouse(
    UID char(100) not null,
    priKey varchar(70),
    primary key(UID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

--EOS私钥库--
create table EOSPriKeyWarehouse(
    UID char(100) not null,
    ownerPriKey varchar(70) not null,
    activePriKey varchar(70),
    primary key(UID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 交易记录--
/**
几种转账类型：
1.链上中心转账--链上钱包转到中心钱包
 2.链上链上转账--链上钱包转到链上钱包
 3.锁仓支出--链上钱包锁入中心钱包
 4.Dapp游戏支出
 5.购买代理人支出
 6.买EOS的RAM支出
 7.买EOS的CPU和NET支出
 8.提现（最小限额）--中心钱包转到链上钱包
 9.注册获得BGS
 10.邀请获得BGS
 11.锁仓获得收益
 12.代理获得收益
*/
drop table if exists transfer;
create table transfer(
    UID char(100) not null,
    transferId bigint unsigned auto_increment not null,
    source varchar(50) not null,
    destination varchar(50) not null,
    amount double not null,
    transferType tinyint not null,
    tokenType tinyint not null,
    createdTime timestamp not null,
    status tinyint not null,
    primary key(transferId,UID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 锁仓记录--
/**
status类型：
1.收益中
2.归仓中
3.已结束
*/
drop table if exists lockwarehouse;
create table lockwarehouse(
    UID char(100) not null,
    LID bigint unsigned auto_increment not null,
    amount double not null,
    period int unsigned not null,
    createdTime timestamp not null,
    tokenType int not null,
    finalProfit double default 0,
    status int not null default 0,
    profitTokenType int not null,
    primary key(LID,UID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

--收益表--
drop table if exists profit;
create table profit(
    UID varchar(100) not null,
    PID bigint unsigned auto_increment not null,
    orderID varchar(100),
    profitType int not null,
    createTime timestamp not null default now(),
    profit double not null,
    primary  key(PID,UID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

--提现表--
drop table if exists withdrawmoney;
create table withdrawmoney(
    UID char(100) not null,
    WID char(100) not null,
    tokenType tinyint not null,
    amount double not null,
    createdTime timestamp not null,
    status tinyint not null,
    auditor varchar(100),
    auditTime timestamp default now(),
    remark varchar(255),
    primary key(UID,WID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- banner--
drop table if exists banner;
create table banner(
    bid bigint unsigned auto_increment not null,
    adName varchar(100),
    photo mediumblob,
    textOfAd text,
    linkOfAd text,
    type int,
	status tinyint not null default 0,
    primary key(bid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- game list--
drop table if exists gameList;
create table if not exists gameList(
    gid bigint unsigned auto_increment not null,
    gameName varchar(50),
    photo mediumblob,
    text text,
    link nvarchar(255),
    type tinyint,
    sort tinyint,
    joindate timestamp not null default now(),
    primary key(gid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- notification--
drop table if exists notification;
create table notification(
    nid bigint unsigned auto_increment not null,
    UID char(100) not null,
    title varchar(100) not null,
    createTime timestamp not null,
    notice text,
    primary key(nid,UID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--后台操作日志--
drop table if exists systemlog;
create table systemlog(
    sid bigint unsigned auto_increment not null,
    opuserId varchar(100) not null,
    optime timestamp not null,
    function varchar(100) not null,
    opusername varchar(100) not null,
    primary key(sid,opuserId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

--用户反馈--
drop table if exists feedback;
create table feedback(
    fid bigint unsigned auto_increment not null,
    UID varchar(100) not null,
    createTime timestamp not null default now(),
    content varchar(255) not null,
    contact varchar(30) ,
    primary key(fid,UID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

--后台管理人员--
drop table if exists bgUser;
create table bgUser(
    bid bigint unsigned auto_increment not null,
    username varchar(100) not null,
    realName varchar(100),
    password varchar(100) not null,
    state tinyint not null,
    admin tinyint not null,
    primary key(bid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

--角色--
drop table if exists role;
create table role(
    rid bigint not null,
    sn varchar(100) not null,
    name varchar(100),
    primary key(rid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

--权限--
drop table if exists permission;
create table permission(
    pid bigint unsigned auto_increment not null,
    name varchar(100) not null,
    resource varchar(100),
    primary key(pid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

--菜单--
drop table if exists menu;
create table menu(
    mid bigint not null,
    text varchar(100) not null,
    url varchar(100),
    parent_id bigint,
    primary key(mid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;