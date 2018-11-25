package com.superwallet.dao;

import com.superwallet.model.EOSWallet;

public interface EOSWalletMapper {
    int deleteByPrimaryKey(String uid);

    int insert(EOSWallet record);

    int insertSelective(EOSWallet record);

    EOSWallet selectByPrimaryKey(String uid);

    int updateByPrimaryKeySelective(EOSWallet record);

    int updateByPrimaryKey(EOSWallet record);
}