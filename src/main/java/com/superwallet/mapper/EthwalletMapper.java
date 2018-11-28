package com.superwallet.mapper;

import com.superwallet.pojo.Ethwallet;
import com.superwallet.pojo.EthwalletExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EthwalletMapper {
    int countByExample(EthwalletExample example);

    int deleteByExample(EthwalletExample example);

    int deleteByPrimaryKey(String uid);

    int insert(Ethwallet record);

    int insertSelective(Ethwallet record);

    List<Ethwallet> selectByExample(EthwalletExample example);

    Ethwallet selectByPrimaryKey(String uid);

    int updateByExampleSelective(@Param("record") Ethwallet record, @Param("example") EthwalletExample example);

    int updateByExample(@Param("record") Ethwallet record, @Param("example") EthwalletExample example);

    int updateByPrimaryKeySelective(Ethwallet record);

    int updateByPrimaryKey(Ethwallet record);
}