package com.superwallet.common;

import java.io.Serializable;

public class DynamicParameters implements Serializable {
    //转出最低额限制
    public static final double MINI_TRANSFER_ETH = 0;//ETH
    public static final double MINI_TRANSFER_EOS = 0;//EOS
    public static final double MINI_TRANSFER_BGS = 0;//BGS

    //锁仓最低额限制
    public static final double MINI_LOCK_ETH = 0;//ETH
    public static final double MINI_LOCK_EOS = 0;//EOS
    public static final double MINI_LOCK_BGS = 0;//BGS

    //锁仓收益权重-- 30/90天对7天
    public static final double PROFITWEIGHT_30 = 1;//30天锁仓的权重
    public static final double PROFITWEIGHT_90 = 1;//90天锁仓的权重
    //锁仓对应每天返回的BGS数量
    public static final double PROFIT_BGS_LOCK_07 = 1;//7天锁仓返回BGS数
    public static final double PROFIT_BGS_LOCK_30 = 2;//30天锁仓返回BGS数
    public static final double PROFIT_BGS_LOCK_90 = 3;//90天锁仓返回BGS数

    //提现最低限额
    public static final double MINI_WITHDRAW_ETH = 0;//ETH
    public static final double MINI_WITHDRAW_EOS = 0;//EOS
    public static final double MINI_WITHDRAW_BGS = 0;//BGS

    //代理人价格
    public static final double PRICE_BUYAGENT_BGS = 2;//BGS

    //每日邀请好友上限
    public static final double MAX_DAILY_INVITING_COUNT = 2;//邀请数量
    //邀请好友奖励BGS数量
    public static final double PROFIT_INVITING_BGS = 5;//BGS

    //Dapp返现分配
    public static final double PROFIT_DAPP_LOCK = 0.1;//锁仓分配比例
    public static final double PROFIT_DAPP_AGENT = 0.2;//代理人分配比例
    public static final double PROFIT_DAPP_PLATFORM = 0.1;//平台利润分配比例

}
