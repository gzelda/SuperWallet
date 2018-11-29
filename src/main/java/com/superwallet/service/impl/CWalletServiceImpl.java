package com.superwallet.service.impl;

import com.superwallet.common.WalletInfo;
import com.superwallet.mapper.BgswalletMapper;
import com.superwallet.mapper.EoswalletMapper;
import com.superwallet.mapper.EthwalletMapper;
import com.superwallet.pojo.Bgswallet;
import com.superwallet.pojo.Eoswallet;
import com.superwallet.pojo.Ethwallet;
import com.superwallet.service.CWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 中心化钱包Service
 */
@Service
public class CWalletServiceImpl implements CWalletService {

    @Autowired
    private EthwalletMapper ethwalletMapper;

    @Autowired
    private EoswalletMapper eoswalletMapper;

    @Autowired
    private BgswalletMapper bgswalletMapper;

    /**
     * 获取用户的所有中心化钱包信息
     *
     * @param UID
     * @return
     */
    @Override
    public List<WalletInfo> listCWalletInfo(String UID) {
        List<WalletInfo> list = new ArrayList<WalletInfo>();
        //TODO 火币接口
        //以太钱包
        Ethwallet ethwallet = ethwalletMapper.selectByPrimaryKey(UID);
        double HUOBIprice = 0.0;
        WalletInfo ethInfo = new WalletInfo(ethwallet.getEthaddress(), ethwallet.getAmount(),
                HUOBIprice, ethwallet.getLockedamount(), ethwallet.getAvailableamount());
        Bgswallet bgswallet = bgswalletMapper.selectByPrimaryKey(UID);
        WalletInfo bgsInfo = new WalletInfo(ethwallet.getEthaddress(), bgswallet.getAmount(),
                HUOBIprice, bgswallet.getLockedamount(), bgswallet.getAvailableamount());
        Eoswallet eoswallet = eoswalletMapper.selectByPrimaryKey(UID);
        WalletInfo eosInfo = new WalletInfo(eoswallet.getEosaddress(), eoswallet.getAmount(),
                HUOBIprice, eoswallet.getLockedamount(), eoswallet.getAvailableamount());
        list.add(ethInfo);
        list.add(bgsInfo);
        list.add(eosInfo);
        return list;
    }
}
