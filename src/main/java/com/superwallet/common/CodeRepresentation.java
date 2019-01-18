package com.superwallet.common;


import java.util.HashMap;
import java.util.Map;

public class CodeRepresentation {

    //返回矩阵信息
    public static final int CODE_FAIL = 0;
    public static final int CODE_SUCCESS = 1;
    public static final int CODE_ERROR = 2;
    public static final int CODE_TIMEOUT = 8;
    public static final int STATUS_0 = 0;
    public static final int STATUS_1 = 1;
    public static final int STATUS_2 = 2;
    public static final int STATUS_TIMEOUT = 8;
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
    public static final Integer TOKENTYPE_ALL = 0;//全部
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
    public static final byte TRANSFER_TYPE_BUYEOSRAM = 6;//质押EOS的RAM
    public static final byte TRANSFER_TYPE_BUYEOSCPU = 7;//质押EOS的CPU
    public static final byte TRANSFER_TYPE_BUYEOSNET = 8;//质押EOS的NET
    public static final byte TRANSFER_TYPE_WITHDRAW_OUT = 9;//中心钱包提现--支出--中心到链上
    public static final byte TRANSFER_TYPE_WITHDRAW_IN = 10;//中心钱包提现--收入--链上到链上
    public static final byte TRANSFER_TYPE_REGISTERBGS = 11;//注册收益--注册送的BGS记录
    public static final byte TRANSFER_TYPE_INVITINGBGS = 12;//邀请收益--邀请人得到的BGS记录
    public static final byte TRANSFER_TYPE_LOCKPROFIT = 13;//锁仓收益
    public static final byte TRANSFER_TYPE_AGENTPROFIT = 14;//代理人收益
    public static final byte TRANSFER_TYPE_GAS = 15;//矿工费
    public static final byte TRANSFER_TYPE_WITHDRAW_FAIL = 16;//提现失败退回金额--收入
    //转账类型字典树
    public static Map<Byte, String> TRANSFER_TYPE_MAPPING = new HashMap<Byte, String>();

    //----------transfer转账记录表结束--------------

    //----------lockWarehouse锁仓记录表开始--------------
    public static final int LOCK_STAUTS_ONPROFIT = 1;//收益中
    public static final int LOCK_STATUS_ONOVER = 2;//归仓中
    public static final int LOCK_STATUS_FINISHED = 3;//已结束
    public static final int LOCK_STATUS_FAIL = 4;//失败
    public static Map<Integer, String> LOCK_STATUS_MAPPING = new HashMap<Integer, String>();
    //----------lockWarehouse锁仓记录表结束--------------

    //----------profit收益记录表开始--------------
    //中心钱包收益状态
    public static final int PROFIT_STATUS_ONPROFIT = 0;//正在收益
    public static final int PROFIT_STATUS_FINISHED = 1;//已结束
    public static Map<Integer, String> PROFIT_STATUS_MAPPING = new HashMap<Integer, String>();//收益状态字典树

    //中心钱包收益是否完成
    public static final int PROFIT_NOTFINISHED = 0;//尚未完成
    public static final int PROFIT_FINISHED = 1;//已完成

    //中心钱包收益类型
    public static final int PROFIT_TYPE_LOCK = 1;//锁仓收益
    public static final int PROFIT_TYPE_AGENT = 2;//代理人收益
    public static final int PROFIT_TYPE_WITHDRAW = 3;//提现收益
    public static final int PROFIT_TYPE_INVITING = 4;//邀请人收益
    public static final int PROFIT_TYPE_REGISTER = 5;//注册收益
    //收益类型字典树
    public static Map<Integer, String> PROFIT_TYPE_MAPPING = new HashMap<Integer, String>();
    //----------profit收益记录表结束--------------

    //保存session时sessionId的前缀
    public static final String SESSIONID_PREFIX = "SESSIONID:";

    //短信验证码保存时间
    public static final int MESSAGECODE_EXPIRE = 300;

    //超级账户名称
    public static final String SUPER_UID = "SuperUID";

    //货币名称
    public static final String ETH = "ETH";
    public static final String EOS = "EOS";
    public static final String BGS = "BGS";
    public static Map<Integer, String> TOKENNAME_MAPPING = new HashMap<Integer, String>();


    //钱包
    public static final String ETHINFO = "ethInfo";
    public static final String EOSINFO = "eosInfo";
    public static final String BGSINFO = "bgsInfo";

    //userStatus状态 0-正常 1-禁用
    public static final byte USERSTATUS_ON = 0;
    public static final byte USERSTATUS_PROFIT = 1;

    //现有币种总数
    public static final int COUNT_WALLETS = 3;

    //Session过期时间
    public static final int SESSION_EXPIRE = 1800;
    //Cookie过期时间
    public static final int COOKIE_EXPIRE = 36000;
    //验证码过期时间
    public static final int PHONECODE_EXPIRE = 300;

    //Cookie名称
    public static final String TOKEN_KEY = "token";
    //redis中存用户活跃的前缀
    public static final String REDIS_PRE_LASTOP = "lastOp:";
    //redis中存用户剩余免费质押次数
    public static final String REDIS_PRE_FREETIMES = "restFreeTimes:";


    //转账记录的状态值status
    public static final byte TRANSFER_ONPROCESS = 1;
    public static final byte TRANSFER_SUCCESS = 2;
    public static final byte TRANSFER_FAIL = 3;
    //转账状态字典树
    public static Map<Byte, String> TRANSFER_STATUS_MAPPING = new HashMap<Byte, String>();

    //提现申请表的状态
    public static final byte WITHDRAW_WAIT = 1;
    public static final byte WITHDRAW_SUCCESS = 2;
    public static final byte WITHDRAW_FAIL = 3;

    //中心钱包余额加或者减
    public static final int CWALLET_MONEY_INC = 0;//加
    public static final int CWALLET_MONEY_DEC = 1;//减

    //类型是收入还是支出
    public static final int NOT_INCOMING = 0;
    public static final int IS_INCOMING = 1;

    //ETH认证 0-未完成 1-已完成
    public static final int ETH_VALIDATION_ON = 0;
    public static final int ETH_VALIDATION_OVER = 1;

    //redis模块
    public static final String REDIS_OPTCONF = "operationCode";
    public static final String REDIS_MINI_TRANSFER_ETH = "MINI_TRANSFER_ETH";
    public static final String REDIS_MINI_TRANSFER_EOS = "MINI_TRANSFER_EOS";
    public static final String REDIS_MINI_TRANSFER_BGS = "MINI_TRANSFER_BGS";
    public static final String REDIS_MINI_LOCK_ETH = "MINI_LOCK_ETH";
    public static final String REDIS_MINI_LOCK_EOS = "MINI_LOCK_EOS";
    public static final String REDIS_MINI_LOCK_BGS = "MINI_LOCK_BGS";
    public static final String REDIS_PROFITWEIGHT_30 = "PROFITWEIGHT_30";
    public static final String REDIS_PROFITWEIGHT_90 = "PROFITWEIGHT_90";
    public static final String REDIS_PROFIT_BGS_LOCK_07 = "PROFIT_BGS_LOCK_07";
    public static final String REDIS_PROFIT_BGS_LOCK_30 = "PROFIT_BGS_LOCK_30";
    public static final String REDIS_PROFIT_BGS_LOCK_90 = "PROFIT_BGS_LOCK_90";
    public static final String REDIS_MINI_WITHDRAW_ETH = "MINI_WITHDRAW_ETH";
    public static final String REDIS_MINI_WITHDRAW_EOS = "MINI_WITHDRAW_EOS";
    public static final String REDIS_MINI_WITHDRAW_BGS = "MINI_WITHDRAW_BGS";
    public static final String REDIS_PRICE_BUYAGENT = "PRICE_BUYAGENT";
    public static final String REDIS_MAX_DAILY_INVITING_COUNT = "MAX_DAILY_INVITING_COUNT";
    public static final String REDIS_PROFIT_REGISTER_BGS = "PROFIT_REGISTER_BGS";
    public static final String REDIS_PROFIT_DAPP_LOCK = "PROFIT_DAPP_LOCK";
    public static final String REDIS_PROFIT_DAPP_AGENT = "PROFIT_DAPP_AGENT";
    public static final String REDIS_PROFIT_DAPP_PLATFORM = "PROFIT_DAPP_PLATFORM";
    public static final String REDIS_PROFIT_INVITING_BGS = "PROFIT_INVITING_BGS";
    public static final String REDIS_RECYCLE_MIN_AMOUNT = "RECYCLE_MIN_AMOUNT";
    public static final String REDIS_RECYCLE_INTERVALTIME = "RECYCLE_INTERVALTIME";
    public static final String REDIS_TRXCPU = "TRX_CPU_USER";
    public static final String REDIS_TRXNET = "TRX_NET_USER";
    public static final String REDIS_TOKENPRICE_ETH = "TOKENPRICE_ETH";
    public static final String REDIS_TOKENPRICE_EOS = "TOKENPRICE_EOS";
    public static final String REDIS_TOKENPRICE_BGS = "TOKENPRICE_BGS";
    public static final String REDIS_URL = "URL";

    public static final String REDIS_REMAINEOSWALLET = "restWallet";

    //查询链上钱包详情模块--条件查询类型对应
    public static final int LISTDETAILDWALLET_ALL = 0;
    public static final int LISTDETAILDWALLET_LOCK = 1;
    public static final int LISTDETAILDWALLET_GAME = 2;
    public static final int LISTDETAILDWALLET_TRANSFEROUT = 3;
    public static final int LISTDETAILDWALLET_AGENT = 4;
    public static final int LISTDETAILDWALLET_EOSSOURCE = 5;

    //查询收益详情--条件查询类型对应
    public static final int LISTDETAILPROFIT_ALL = 0;
    public static final int LISTDETAILPROFIT_LOCK = 1;
    public static final int LISTDETAILPROFIT_AGENT = 2;
    public static final int LISTDETAILPROFIT_WITHDRAW = 3;
    public static final int LISTDETAILPROFIT_REWARD = 4;


    //超级账户的Address
    public static final String SUPER_ETH = "0x47B9Be7A0FC74Be3fccdECfC6d41d21D24D4a672";
    public static final String SUPER_BGS = "0x47B9Be7A0FC74Be3fccdECfC6d41d21D24D4a672";
    public static final String SUPER_EOS = "gtygavintest";

    //链上HTTP请求地址
    public static final String NODE_URL_ETH = "http://3.17.163.147:4000";
    public static final String NODE_URL_EOS = "http://3.17.163.147:3000";
    public static final String NODE_ACTION_CREATEETH = "/eth/createAccount";
    public static final String NODE_ACTION_CREATEEOS = "/eos/createAccount";
    public static final String NODE_ACTION_ALLOCATEEOSWALLET = "/eos/wallet/allocateWallet";
    public static final String NODE_ACTION_RECYCLEWALLET = "/eos/wallet/recycleWallet";
    public static final String NODE_ACTION_ETHTRANSFER = "/eth/transfer";
    public static final String NODE_ACTION_ETH_QUERYPENDING = "/eth/queryPending";
    public static final String NODE_ACTION_EOSTRANSFER = "/eos/transfer";
    public static final String NODE_ACTION_EOS_NETCPU = "/eos/trxCPUNET";
    public static final String NODE_ACTION_EOS_CPU = "/eos/eosBank/trxCPU";
    public static final String NODE_ACTION_EOS_NET = "/eos/eosBank/trxNET";
    public static final String NODE_ACTION_EOS_RAM = "/eos/trxRam";
    public static final String NODE_ACTION_EOS_ACCOUNTINFO = "/eos/getAccount";
    public static final String NODE_ACTION_ETH_ACCOUNTINFO = "/eth/getBalance";
    public static final String NODE_ACTION_EOS_SCATTER_GETORREQUESTIDENTITY = "/eos/scatter/getOrRequestIdentity";
    public static final String NODE_ACTION_EOS_SCATTER_IDENTITYFROMPERMISSIONS = "/eos/scatter/identityFromPermissions";
    public static final String NODE_ACTION_EOS_SCATTER_REQUESTSIGNATURE = "/eos/scatter/requestSignature";
    public static final String NODE_ACTION_EOS_SCATTER_GETORIGINDATA = "/eos/scatter/getOriginData";


    static {
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_ON2OFF, "收入-链上转入中心");//收入
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_ON2ON, "支出-转出");//支出
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_PAYLOCK, "支出-锁仓");//支出
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_PAYGAME, "支出-游戏付费");//支出
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_BUYAGENT, "支出-代理");//支出
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_BUYEOSRAM, "支出-资源");//支出
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_BUYEOSCPU, "支出-资源");//支出
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_BUYEOSNET, "支出-资源");//支出
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_WITHDRAW_OUT, "支出-转出");//支出
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_WITHDRAW_IN, "收入-收益");//收入
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_REGISTERBGS, "收入-收益");//收入
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_INVITINGBGS, "收入-收益");//收入
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_LOCKPROFIT, "收入-解仓");//收入
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_AGENTPROFIT, "收入-收益");//收入
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_GAS, "支出-矿工费");//支出
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_WITHDRAW_FAIL, "收入-收益");//支出

        TRANSFER_STATUS_MAPPING.put(TRANSFER_FAIL, "交易失败");
        TRANSFER_STATUS_MAPPING.put(TRANSFER_SUCCESS, "交易成功");

        LOCK_STATUS_MAPPING.put(LOCK_STAUTS_ONPROFIT, "收益中");
        LOCK_STATUS_MAPPING.put(LOCK_STATUS_ONOVER, "归仓中");
        LOCK_STATUS_MAPPING.put(LOCK_STATUS_FINISHED, "已结束");

        PROFIT_STATUS_MAPPING.put(PROFIT_STATUS_ONPROFIT, "正在收益");
        PROFIT_STATUS_MAPPING.put(PROFIT_STATUS_FINISHED, "已完成");

        PROFIT_TYPE_MAPPING.put(PROFIT_TYPE_LOCK, "收益-锁仓");
        PROFIT_TYPE_MAPPING.put(PROFIT_TYPE_AGENT, "收益-代理");
        PROFIT_TYPE_MAPPING.put(PROFIT_TYPE_WITHDRAW, "划转-已结束");
        PROFIT_TYPE_MAPPING.put(PROFIT_TYPE_INVITING, "收益-邀请");
        PROFIT_TYPE_MAPPING.put(PROFIT_TYPE_REGISTER, "收益-注册");

        TOKENNAME_MAPPING.put(TOKENTYPE_ETH, ETH);
        TOKENNAME_MAPPING.put(TOKENTYPE_EOS, EOS);
        TOKENNAME_MAPPING.put(TOKENTYPE_BGS, BGS);
    }
}
