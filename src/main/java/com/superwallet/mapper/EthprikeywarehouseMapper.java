package com.superwallet.mapper;

import com.superwallet.pojo.Ethprikeywarehouse;
import com.superwallet.pojo.EthprikeywarehouseExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EthprikeywarehouseMapper {
    int countByExample(EthprikeywarehouseExample example);

    int deleteByExample(EthprikeywarehouseExample example);

    int deleteByPrimaryKey(String uid);

    int insert(Ethprikeywarehouse record);

    int insertSelective(Ethprikeywarehouse record);

    List<Ethprikeywarehouse> selectByExample(EthprikeywarehouseExample example);

    Ethprikeywarehouse selectByPrimaryKey(String uid);

    int updateByExampleSelective(@Param("record") Ethprikeywarehouse record, @Param("example") EthprikeywarehouseExample example);

    int updateByExample(@Param("record") Ethprikeywarehouse record, @Param("example") EthprikeywarehouseExample example);

    int updateByPrimaryKeySelective(Ethprikeywarehouse record);

    int updateByPrimaryKey(Ethprikeywarehouse record);
}