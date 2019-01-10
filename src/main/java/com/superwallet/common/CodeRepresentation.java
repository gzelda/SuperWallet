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
    public static final byte TRANSFER_TYPE_BUYEOSRAM = 6;//买EOS的RAM
    public static final byte TRANSFER_TYPE_BUYEOSCPUNET = 7;//买EOS的CPU和NET
    public static final byte TRANSFER_TYPE_WITHDRAW = 8;//中心钱包提现
    public static final byte TRANSFER_TYPE_REGISTERBGS = 9;//注册送的BGS记录
    public static final byte TRANSFER_TYPE_INVITINGBGS = 10;//邀请人得到的BGS记录
    public static final byte TRANSFER_TYPE_LOCKPROFIT = 11;//锁仓收益
    public static final byte TRANSFER_TYPE_AGENTPROFIT = 12;//代理人收益
    //转账类型字典树
    public static Map<Byte, String> TRANSFER_TYPE_MAPPING = new HashMap<Byte, String>();

    //----------transfer转账记录表结束--------------

    //----------lockWarehouse锁仓记录表开始--------------
    public static final int LOCK_STAUTS_ONPROFIT = 1;//收益中
    public static final int LOCK_STATUS_ONOVER = 2;//归仓中
    public static final int LOCK_STATUS_FINISHED = 3;//已结束
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

    //转账记录的状态值status
    public static final byte TRANSFER_FAIL = 0;
    public static final byte TRANSFER_SUCCESS = 1;
    //转账状态字典树
    public static Map<Byte, String> TRANSFER_STATUS_MAPPING = new HashMap<Byte, String>();

    //提现申请表的状态
    public static final byte WITHDRAW_WAIT = 1;
    public static final byte WITHDRAW_SUCCESS = 2;
    public static final byte WITHDRAW_FAIL = 3;

    //中心钱包余额加或者减
    public static final int CWALLET_MONEY_INC = 0;//加
    public static final int CWALLET_MONEY_DEC = 1;//减

    //货币价格--redis
    public static final String TOKENPRICE_KEY = "tokenprice";
    public static final String TOKENPRICE_ETH = "eth";
    public static final String TOKENPRICE_EOS = "eos";
    public static final String TOKENPRICE_BGS = "bgs";


    //超级账户的Address
    public static final String SUPER_ETH = "0x47B9Be7A0FC74Be3fccdECfC6d41d21D24D4a672";
    public static final String SUPER_BGS = "0x47B9Be7A0FC74Be3fccdECfC6d41d21D24D4a672";
    public static final String SUPER_EOS = "tygavingavin";

    //链上HTTP请求地址
    public static final String NODE_URL_ETH = "http://3.17.163.147:4000";
    public static final String NODE_URL_EOS = "http://3.17.163.147:3000";
    public static final String NODE_ACTION_CREATEETH = "/eth/createAccount";
    public static final String NODE_ACTION_CREATEEOS = "/eos/createAccount";
    public static final String NODE_ACTION_ETHTRANSFER = "/eth/transfer";
    public static final String NODE_ACTION_EOSTRANSFER = "/eos/transfer";
    public static final String NODE_ACTION_EOS_NETCPU = "/eos/trxNetCpu";
    public static final String NODE_ACTION_EOS_RAM = "/eos/trxRam";
    public static final String NODE_ACTION_EOS_ACCOUNTINFO = "/eos/getAccount";
    public static final String NODE_ACTION_ETH_ACCOUNTINFO = "/eth/getBalance";
    public static final String NODE_ACTION_EOS_SCATTER_GETORREQUESTIDENTITY = "/eos/scatter/getOrRequestIdentity";
    public static final String NODE_ACTION_EOS_SCATTER_IDENTITYFROMPERMISSIONS = "/eos/scatter/identityFromPermissions";
    public static final String NODE_ACTION_EOS_SCATTER_REQUESTSIGNATURE = "/eos/scatter/requestSignature";
    public static final String NODE_ACTION_EOS_SCATTER_GETORIGINDATA = "/eos/scatter/getOriginData";

    static {
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_ON2OFF, "链上转入中心");
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_ON2ON, "链上转账");
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_PAYLOCK, "锁仓付费");
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_PAYGAME, "游戏付费");
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_BUYAGENT, "买代理人");
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_BUYEOSRAM, "买EOS的RAM");
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_BUYEOSCPUNET, "买EOS的CPU和NET");
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_WITHDRAW, "中心钱包提现");
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_REGISTERBGS, "注册送的BGS记录");
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_INVITINGBGS, "邀请人得到的BGS记录");
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_LOCKPROFIT, "锁仓收益");
        TRANSFER_TYPE_MAPPING.put(TRANSFER_TYPE_AGENTPROFIT, "代理人收益");

        TRANSFER_STATUS_MAPPING.put(TRANSFER_FAIL, "交易失败");
        TRANSFER_STATUS_MAPPING.put(TRANSFER_SUCCESS, "交易成功");

        LOCK_STATUS_MAPPING.put(LOCK_STAUTS_ONPROFIT, "收益中");
        LOCK_STATUS_MAPPING.put(LOCK_STATUS_ONOVER, "归仓中");
        LOCK_STATUS_MAPPING.put(LOCK_STATUS_FINISHED, "已结束");

        PROFIT_STATUS_MAPPING.put(PROFIT_STATUS_ONPROFIT, "正在收益");
        PROFIT_STATUS_MAPPING.put(PROFIT_STATUS_FINISHED, "已完成");

        PROFIT_TYPE_MAPPING.put(PROFIT_TYPE_LOCK, "锁仓收益");
        PROFIT_TYPE_MAPPING.put(PROFIT_TYPE_AGENT, "代理人收益");
        PROFIT_TYPE_MAPPING.put(PROFIT_TYPE_WITHDRAW, "提现收益");
        PROFIT_TYPE_MAPPING.put(PROFIT_TYPE_INVITING, "邀请人收益");

        TOKENNAME_MAPPING.put(TOKENTYPE_ETH, ETH);
        TOKENNAME_MAPPING.put(TOKENTYPE_EOS, EOS);
        TOKENNAME_MAPPING.put(TOKENTYPE_BGS, BGS);
    }
}
