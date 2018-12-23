package com.superwallet.mapper;

import org.apache.ibatis.annotations.Param;
import pojo.Lockwarehouse;
import pojo.LockwarehouseExample;
import pojo.LockwarehouseKey;

import java.util.List;

public interface LockwarehouseMapper {
    int countByExample(LockwarehouseExample example);

    int deleteByExample(LockwarehouseExample example);

    int deleteByPrimaryKey(LockwarehouseKey key);

    int insert(Lockwarehouse record);

    int insertSelective(Lockwarehouse record);

    List<Lockwarehouse> selectByExample(LockwarehouseExample example);

    Lockwarehouse selectByPrimaryKey(LockwarehouseKey key);

    int updateByExampleSelective(@Param("record") Lockwarehouse record, @Param("example") LockwarehouseExample example);

    int updateByExample(@Param("record") Lockwarehouse record, @Param("example") LockwarehouseExample example);

    int updateByPrimaryKeySelective(Lockwarehouse record);

    int updateByPrimaryKey(Lockwarehouse record);
}