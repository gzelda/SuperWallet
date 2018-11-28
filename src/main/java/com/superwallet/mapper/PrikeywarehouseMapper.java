package com.superwallet.mapper;

import com.superwallet.pojo.Prikeywarehouse;
import com.superwallet.pojo.PrikeywarehouseExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PrikeywarehouseMapper {
    int countByExample(PrikeywarehouseExample example);

    int deleteByExample(PrikeywarehouseExample example);

    int deleteByPrimaryKey(String uid);

    int insert(Prikeywarehouse record);

    int insertSelective(Prikeywarehouse record);

    List<Prikeywarehouse> selectByExample(PrikeywarehouseExample example);

    Prikeywarehouse selectByPrimaryKey(String uid);

    int updateByExampleSelective(@Param("record") Prikeywarehouse record, @Param("example") PrikeywarehouseExample example);

    int updateByExample(@Param("record") Prikeywarehouse record, @Param("example") PrikeywarehouseExample example);

    int updateByPrimaryKeySelective(Prikeywarehouse record);

    int updateByPrimaryKey(Prikeywarehouse record);
}