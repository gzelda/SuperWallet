package com.superwallet.dao;

import com.superwallet.model.lockWarehouse;
import com.superwallet.model.lockWarehouseKey;

public interface lockWarehouseMapper {
    int deleteByPrimaryKey(lockWarehouseKey key);

    int insert(lockWarehouse record);

    int insertSelective(lockWarehouse record);

    lockWarehouse selectByPrimaryKey(lockWarehouseKey key);

    int updateByPrimaryKeySelective(lockWarehouse record);

    int updateByPrimaryKey(lockWarehouse record);
}