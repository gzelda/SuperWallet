package com.superwallet.dao;

import com.superwallet.model.userPrivate;
import com.superwallet.model.userPrivateWithBLOBs;

public interface userPrivateMapper {
    int deleteByPrimaryKey(String uid);

    int insert(userPrivateWithBLOBs record);

    int insertSelective(userPrivateWithBLOBs record);

    userPrivateWithBLOBs selectByPrimaryKey(String uid);

    int updateByPrimaryKeySelective(userPrivateWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(userPrivateWithBLOBs record);

    int updateByPrimaryKey(userPrivate record);
}