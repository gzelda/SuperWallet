package com.superwallet.service.impl;

import com.superwallet.mapper.LockwarehouseMapper;
import com.superwallet.mapper.TransferMapper;
import com.superwallet.mapper.WithdrawmoneyMapper;
import com.superwallet.pojo.Lockwarehouse;
import com.superwallet.pojo.Transfer;
import com.superwallet.pojo.Withdrawmoney;
import com.superwallet.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 通用服务
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private TransferMapper transferMapper;

    @Autowired
    private WithdrawmoneyMapper withdrawmoneyMapper;

    @Autowired
    private LockwarehouseMapper lockwarehouseMapper;

    /**
     * 转账记录生成
     *
     * @param UID
     * @param transferType
     * @param tokenType
     * @param status
     * @param addressFrom
     * @param addressTo
     * @param tokenAmount
     * @return
     */
    @Override
    public void generateRecord(String UID, Byte transferType, Byte tokenType,
                               Byte status, String addressFrom, String addressTo,
                               Double tokenAmount) {
        //转账记录
        Transfer transfer = new Transfer();
        transfer.setUid(UID);
        transfer.setTransfertype(transferType);
        transfer.setTokentype(tokenType);
        transfer.setStatus(status);
        transfer.setSource(addressFrom);
        transfer.setDestination(addressTo);
        transfer.setCreatedtime(new Date());
        transfer.setAmount(tokenAmount);
        transferMapper.insert(transfer);
    }

    /**
     * 提現申請
     *
     * @param UID
     * @param WID
     * @param tokenType
     * @param status
     * @param tokenAmount
     */
    @Override
    public void withdrawRecord(String UID, String WID, Byte tokenType, Byte status, Double tokenAmount) {
        //写入提现申请表
        Withdrawmoney record = new Withdrawmoney();
        record.setUid(UID);
        record.setWid(WID);
        record.setCreatedtime(new Date());
        record.setTokentype(tokenType);
        record.setAmount(tokenAmount);
        record.setStatus(status);
        withdrawmoneyMapper.insert(record);
    }

    /**
     * 生成锁仓记录
     *
     * @param UID
     * @param period
     * @param tokenAmount
     * @param status
     */
    @Override
    public void lockedRecord(String UID, Integer period, Double tokenAmount, Byte status) {
        Lockwarehouse lockwarehouse = new Lockwarehouse();
        lockwarehouse.setUid(UID);
        lockwarehouse.setCreatedtime(new Date());
        lockwarehouse.setPeriod(period);
        lockwarehouse.setAmount(tokenAmount);
        lockwarehouse.setStatus(status);
        lockwarehouseMapper.insert(lockwarehouse);
    }

}
