package com.superwallet.common;

public class CodeRepresentation {
    public static final int CODE_FAIL = 0;
    public static final int CODE_SUCCESS = 1;
    public static final int CODE_ERROR = 2;
    public static final int STATUS_0 = 0;
    public static final int STATUS_1 = 1;
    public static final int STATUS_2 = 2;
    public static final int STATUS_3 = 3;

    //用户状态标志-status
    public static final Byte USER_STATUS_NOIDVALIDATION = 0;//没做身份认证
    public static final Byte USER_STATUS_NOFACEVALIDATION = 1;//没做人脸识别
    public static final Byte USER_STATUS_REGISTERED = 2;//老用户

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

    //币种分类
    public static final int ETH_TOKEN_TYPE_ETH = 0;//ETH
    public static final int ETH_TOKEN_TYPE_BGS = 1;//BGS
    public static final int EOS_TOKEN_TYPE_EOS = 0;//EOS

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


    //isAgency字段 0-非代理 1-代理
    public static final Byte ISAGENCY = new Byte("1");
    public static final Byte NOTAGENCY = new Byte("0");

    //超级账户的Address
    public static final String SUPER_ETH = "0x47B9Be7A0FC74Be3fccdECfC6d41d21D24D4a672";
    public static final String SUPER_BGS = "0x47B9Be7A0FC74Be3fccdECfC6d41d21D24D4a672";
    public static final String SUPER_EOS = "eostesttest1";

    //链上HTTP请求地址
    public static final String NODE_URL_ETH = "http://192.168.43.135:4000";
    public static final String NODE_URL_EOS = "http://10.180.55.125:3000";
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
