package com.superwallet.common;

import com.superwallet.utils.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;

public class CodeRepresentation {

    @Autowired
    private JedisClient jedisClient;

    //返回矩阵信息
    public static final int CODE_FAIL = 0;
    public static final int CODE_SUCCESS = 1;
    public static final int CODE_ERROR = 2;
    public static final int STATUS_0 = 0;
    public static final int STATUS_1 = 1;
    public static final int STATUS_2 = 2;
    public static final int STATUS_3 = 3;

    //----------userbasic表开始--------------
    //用户性别-sex
    public static final byte USER_SEX_WOMAN = 0;//女
    public static final byte USER_SEX_MAN = 1;//男
    //用户状态标志-status
    public static final byte USER_STATUS_NOIDVALIDATION = 1;//注册入库
    public static final byte USER_STATUS_VERIFIED = 2;//已经实名认证
    //是否是代理人--isAgency
    public static final byte USER_AGENT_NOTAGENCY = 0;//不是代理人
    public static final byte USER_AGENT_ISAGENCY = 1;//是代理人
    //----------userbasic表结束--------------

    //----------token钱包表开始--------------
    //货币是否可以锁仓-canLock
    public static final byte TOKEN_CANNOTLOCK = 0;//不可以
    public static final byte TOKEN_CANLOCK = 1;//可以
    //货币类型--总
    public static final int TOKENTYPE_ETH = 1;//ETH
    public static final int TOKENTYPE_EOS = 2;//EOS
    public static final int TOKENTYPE_BGS = 3;//BGS
    //货币类型-分表
    //ETHTOKEN-type
    public static final int ETH_TOKEN_TYPE_ETH = 0;//ETH
    public static final int ETH_TOKEN_TYPE_BGS = 1;//BGS
    //EOSTOKEN-type
    public static final int EOS_TOKEN_TYPE_EOS = 0;//EOS
    //----------token钱包表结束--------------

    //----------transfer转账记录表开始--------------
    public static final byte TRANSFER_TYPE_ON2OFF = 1;//链上转入中心
    public static final byte TRANSFER_TYPE_ON2ON = 2;//链上转链上
    public static final byte TRANSFER_TYPE_PAYLOCK = 3;//锁仓付费
    public static final byte TRANSFER_TYPE_PAYGAME = 4;//游戏付费
    public static final byte TRANSFER_TYPE_BUYAGENT = 5;//买代理人
    public static final byte TRANSFER_TYPE_BUYEOSRAM = 6;//买EOS的RAM
    public static final byte TRANSFER_TYPE_BUYEOSCPUNET = 7;//买EOS的CPU和NET
    public static final byte TRANSFER_TYPE_WITHDRAW = 8;//中心钱包提现
    public static final byte TRANSFER_TYPE_REGISTERBGS = 9;//注册送的BGS记录
    public static final byte TRANSFER_TYPE_INVITINGBGS = 10;//邀请人得到的BGS记录
    public static final byte TRANSFER_TYPE_LOCKPROFIT = 11;//锁仓收益
    public static final byte TRANSFER_TYPE_AGENTPROFIT = 12;//代理人收益
    //----------transfer转账记录表结束--------------

    //----------lockWarehouse锁仓记录表开始--------------
    public static final int LOCK_STAUTS_ONPROFIT = 1;//收益中
    public static final int LOCK_STATUS_ONOVER = 2;//归仓中
    public static final int LOCK_STATUS_FINISHED = 3;//已结束
    //----------lockWarehouse锁仓记录表结束--------------

    //----------profit收益记录表开始--------------
    //中心钱包收益状态
    public static final int PROFIT_STATUS_ONPROFIT = 0;//正在收益
    public static final int PROFIT_STATUS_FINISHED = 1;//已结束
    //中心钱包收益是否完成
    public static final int PROFIT_NOTFINISHED = 0;//尚未完成
    public static final int PROFIT_FINISHED = 1;//已完成
    //中心钱包收益类型
    public static final int PROFIT_TYPE_LOCK = 1;//锁仓收益
    public static final int PROFIT_TYPE_AGENT = 2;//代理人收益
    public static final int PROFIT_TYPE_WITHDRAW = 3;//提现收益
    public static final int PROFIT_TYPE_INVITING = 4;//邀请人收益
    //----------profit收益记录表结束--------------

    //SuperResult里的UID
    public static final String UID = "UID";

    //钱包
    public static final String ETHINFO = "ethInfo";
    public static final String EOSINFO = "eosInfo";
    public static final String BGSINFO = "bgsInfo";
    //现有币种总数
    public static final int COUNT_WALLETS = 3;

    //Session过期时间
    public static final int SESSION_EXPIRE = 1800;

    //Cookie名称
    public static final String TOKEN_KEY = "token";

    //代理人价格 --BGS
    public static final double AGENT_PRICE = 10000;

    //默认地址
    public static final String DEFAULT_ADDRESS = "default";

    //超级账户
    public static final String SUPER_UID = "10000";

    //转账记录的transferType
    public static final Byte CHAIN_ON2ON = 0;
    public static final Byte CHAIN_ON2OFF = 1;
    public static final Byte CHAIN_OFF2ON = 2;

    //转账记录的状态值status
    public static final Byte TRANSFER_FAIL = 0;
    public static final Byte TRANSFER_SUCCESS = 1;

    //锁仓状态
    public static final Byte LOCK_ON = 0;
    public static final Byte LOCK_OFF = 1;


    //这个币是否可以锁仓 0-不可以 1-可以
    public static final Byte CANNOT_LOCK = 0;
    public static final Byte CAN_LOCK = 1;


    //提现申请表的状态
    public static final Byte WITHDRAW_WAIT = 0;
    public static final Byte WITHDRAW_SUCCESS = 1;
    public static final Byte WITHDRAW_FAIL = 2;

    //用户邀请人之后可以获得的BGS
    public static final double INVITING_BGS = 50;

    //中心钱包余额加或者减
    public static final int CWALLET_MONEY_INC = 0;
    public static final int CWALLET_MONEY_DEC = 1;

    //超级账户的Address
    public static final String SUPER_ETH = "0x47B9Be7A0FC74Be3fccdECfC6d41d21D24D4a672";
    public static final String SUPER_BGS = "0x47B9Be7A0FC74Be3fccdECfC6d41d21D24D4a672";
    public static final String SUPER_EOS = "tygavingavin";

    //链上HTTP请求地址
    public static final String NODE_URL_ETH = "http://localhost:4000";
    public static final String NODE_URL_EOS = "http://localhost:3000";
    public static final String NODE_ACTION_CREATEETH = "/eth/createAccount";
    public static final String NODE_ACTION_CREATEEOS = "/eos/createAccount";
    public static final String NODE_ACTION_ETHTRANSFER = "/eth/transfer";
    public static final String NODE_ACTION_EOSTRANSFER = "/eos/transfer";
    public static final String NODE_ACTION_EOS_NETCPU = "/eos/trxNetCpu";
    public static final String NODE_ACTION_EOS_RAM = "/eos/trxRam";
    public static final String NODE_ACTION_EOS_ACCOUNTINFO = "/eos/getAccount";
    public static final String NODE_ACTION_ETH_ACCOUNTINFO = "/eth/getBalance";

    //爬取数字货币价格的网站
    public static final String URL_PRICE = "https://coinmarketcap.com/zh/";
}
