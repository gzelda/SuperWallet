package com.superwallet.mapper;

import com.superwallet.pojo.Eosprikeywarehouse;
import com.superwallet.pojo.EosprikeywarehouseExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EosprikeywarehouseMapper {
    int countByExample(EosprikeywarehouseExample example);

    int deleteByExample(EosprikeywarehouseExample example);

    int deleteByPrimaryKey(String uid);

    int insert(Eosprikeywarehouse record);

    int insertSelective(Eosprikeywarehouse record);

    List<Eosprikeywarehouse> selectByExample(EosprikeywarehouseExample example);

    Eosprikeywarehouse selectByPrimaryKey(String uid);

    int updateByExampleSelective(@Param("record") Eosprikeywarehouse record, @Param("example") EosprikeywarehouseExample example);

    int updateByExample(@Param("record") Eosprikeywarehouse record, @Param("example") EosprikeywarehouseExample example);

    int updateByPrimaryKeySelective(Eosprikeywarehouse record);

    int updateByPrimaryKey(Eosprikeywarehouse record);
}