/*wallet项目数据库表设计*/

-- 用户基本资料表--
drop table userBasic if exists;
create table userBasic(
    UID char(20) not null,
    nickName varchar(20) not null,
    sex tinyint not null,
    isAgency tinyint not null,
    headPhoto blob not null,
    phoneNumber char(15) not null,
    inviter char(20),
    status tinyint,
    passWord varchar(20),
    payPassWord varchar(100),
    invitedCode varchar(100),
    invitedPeople json,
    primary key(UID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 用户状态信息表--
drop table userStatus if exists;
create table userStatus(
    UID char(20) not null,
    lastOpTime timestamp,
    lastOpDevice varchar(70);
    primary key(UID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 用户隐私资料表--
drop table userPrivate if exists;
create table userPrivate(
    UID char(20) not null,
    realName varchar(20) not null,
    IDCardNumber char(20) not null,
    IDCardFront blob not null,
    IDCardBack blob not null,
    primary key(UID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 中心化钱包--
-- ETH--
drop table ETHWallet if exists;
create table ETHWallet(
    UID char(20) not null,
    ETHAddress varchar(50) not null,
    lockedAmount int unsigned not null,
    availableAmount int unsigned not null,
    amount int unsigned not null,
    primary key(UID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- EOS--
drop table EOSWallet if exists;
create table EOSWallet(
    UID char(20) not null,
    EOSAddress varchar(50) not null,
    lockedAmount int unsigned not null,
    availableAmount int unsigned not null,
    amount int unsigned not null,
    primary key(UID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- BGS--
drop table BGSWallet if exists;
create table BGSWallet(
    UID char(20) not null,
    lockedAmount int unsigned not null,
    availableAmount int unsigned not null,
    amount int unsigned not null,
    primary key(UID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 私钥库--
drop table PriKeyWarehouse if exists;
create table PriKeyWarehouse(
    UID char(20) not null,
    keyType tinyint,
    priKey varchar(70),
    primary key(UID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 交易记录--
drop table transfer if exists;
create table transfer(
    UID char(20) not null,
    transferId bigint unsigned auto_increment not null,
    sourceAddress varchar(50) not null,
    destAddress varchar(50) not null,
    amount int not null,
    transferType int unsigned not null,
    tokenType tinyint not null,
    createdTime timestamp not null,
    primary key(transferId,UID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 锁仓记录--
drop table lockWarehouse if exists;
create table lockWarehouse(
    UID char(20) not null,
    lockWarehouseId bigint unsigned auto_increment not null,
    amount int not null,
    period int unsigned not null,
    createdTime timestamp not null,
    status tinyint not null,
    primary key(lockWarehouseId,UID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- banner--
drop table banner if exists;
create table banner(
    bid bigint unsigned auto_increment not null,
    photo mediumblob,
    textOfAd text,
    linkOfAd text,
    primary key(bid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- game list--
drop table gameList if exists;
create table gameList(
    gid bigint unsigned auto_increment not null,
    photo mediumblob,
    text text,
    link nvarchar(255),
    primary key(gid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- notification--
drop table notification if exists;
create table notification(
    nid bigint unsigned auto_increment not null,
    protocol varchar(20),
    notice text,
    primary key(nid)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;