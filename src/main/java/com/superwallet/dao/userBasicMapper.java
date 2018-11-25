package com.superwallet.dao;

import com.superwallet.model.userBasic;

public interface userBasicMapper {
    int deleteByPrimaryKey(String uid);

    int insert(userBasic record);

    int insertSelective(userBasic record);

    userBasic selectByPrimaryKey(String uid);

    int updateByPrimaryKeySelective(userBasic record);

    int updateByPrimaryKeyWithBLOBs(userBasic record);

    int updateByPrimaryKey(userBasic record);
}