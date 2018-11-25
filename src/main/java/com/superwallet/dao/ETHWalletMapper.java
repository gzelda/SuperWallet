package com.superwallet.dao;

import com.superwallet.model.ETHWallet;

public interface ETHWalletMapper {
    int deleteByPrimaryKey(String uid);

    int insert(ETHWallet record);

    int insertSelective(ETHWallet record);

    ETHWallet selectByPrimaryKey(String uid);

    int updateByPrimaryKeySelective(ETHWallet record);

    int updateByPrimaryKey(ETHWallet record);
}