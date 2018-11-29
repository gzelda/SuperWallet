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

}
