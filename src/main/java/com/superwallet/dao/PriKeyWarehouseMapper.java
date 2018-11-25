package com.superwallet.dao;

import com.superwallet.model.PriKeyWarehouse;

public interface PriKeyWarehouseMapper {
    int deleteByPrimaryKey(String uid);

    int insert(PriKeyWarehouse record);

    int insertSelective(PriKeyWarehouse record);

    PriKeyWarehouse selectByPrimaryKey(String uid);

    int updateByPrimaryKeySelective(PriKeyWarehouse record);

    int updateByPrimaryKey(PriKeyWarehouse record);
}