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
    public static final int USER_STATUS_NOIDVALIDATION = 0;//没做身份认证
    public static final int USER_STATUS_NOFACEVALIDATION = 1;//没做人脸识别
    public static final int USER_STATUS_REGISTERED = 2;//老用户

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

    //爬取数字货币价格的网站
    public static final String URL_PRICE = "https://coinmarketcap.com/zh/";


}
