/*wallet项目数据库表设计*/

-- 用户基本资料表--
drop table if exists userBasic;
create table userBasic(
    UID char(100) not null,
    nickName varchar(20) not null,
    sex tinyint not null,
    isAgency tinyint not null,
    headPhoto blob not null,
    phoneNumber char(15) not null,
    inviter char(20),
    status tinyint,
    passWord varchar(100),
    payPassWord varchar(100),
    invitedCode varchar(100),
    invitedPeople varchar(255),
    primary key(UID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 用户状态信息表--
drop table if exists userStatus;
create table userStatus(
    UID char(100) not null,
    lastOpTime timestamp,
    lastOpDevice varchar(70);
    primary key(UID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 用户隐私资料表--
drop table if exists userPrivate;
create table userPrivate(
    UID char(100) not null,
    realName varchar(20) not null,
    IDCardNumber char(20) not null,
    IDCardFront blob not null,
    IDCardBack blob not null,
    primary key(UID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 中心化钱包--
-- ETH--
drop table if exists ETHWALLET,ETHTOKEN;
create table ETHTOKEN(
    UID char(100) not null,
    ETHAddress varchar(50),
    lockedAmount double not null,
    availableAmount double not null,
    amount double not null,
    type int not null,
    primary key(UID,type)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- EOS--
drop table if exists EOSWallet,EOSTOKEN;
create table EOSTOKEN(
    UID char(100) not null,
    EOSAccountName varchar(50),
    lockedAmount double not null,
    availableAmount double not null,
    amount double not null,
    type int not null,
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
1.锁仓--链上钱包锁入中心钱包
2.链上中心转账--链上钱包转到中心钱包
3.链上链上转账--链上钱包转到链上钱包
4.买代理人（中心钱包买）--中心钱包扣
5.提现（最小限额）--中心钱包转到链上钱包
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
drop table if exists lockWarehouse;
create table lockWarehouse(
    UID char(100) not null,
    LID bigint unsigned auto_increment not null,
    amount double not null,
    period int unsigned not null,
    createdTime timestamp not null,
    status tinyint not null,
    primary key(LID,UID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- banner--
drop table if exists banner;
create table banner(
    bid bigint unsigned auto_increment not null,
    photo mediumblob,
    textOfAd text,
    linkOfAd text,
    type int,
    primary key(bid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- game list--
drop table if exists gameList;
create table if not exists gameList(
    gid bigint unsigned auto_increment not null,
    photo mediumblob,
    text text,
    link nvarchar(255),
    type tinyint,
    primary key(gid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- notification--
drop table if exists notification;
create table notification(
    nid bigint unsigned auto_increment not null,
    protocol varchar(20),
    notice text,
    primary key(nid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
