package com.superwallet.dao;

import com.superwallet.model.transfer;
import com.superwallet.model.transferKey;

public interface transferMapper {
    int deleteByPrimaryKey(transferKey key);

    int insert(transfer record);

    int insertSelective(transfer record);

    transfer selectByPrimaryKey(transferKey key);

    int updateByPrimaryKeySelective(transfer record);

    int updateByPrimaryKey(transfer record);
}