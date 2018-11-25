package com.superwallet.dao;

import com.superwallet.model.BGSWallet;

public interface BGSWalletMapper {
    int deleteByPrimaryKey(String uid);

    int insert(BGSWallet record);

    int insertSelective(BGSWallet record);

    BGSWallet selectByPrimaryKey(String uid);

    int updateByPrimaryKeySelective(BGSWallet record);

    int updateByPrimaryKey(BGSWallet record);
}