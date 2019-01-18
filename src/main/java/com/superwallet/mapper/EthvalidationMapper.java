package com.superwallet.mapper;

import com.superwallet.pojo.Ethvalidation;
import com.superwallet.pojo.EthvalidationExample;
import com.superwallet.pojo.EthvalidationKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EthvalidationMapper {
    int countByExample(EthvalidationExample example);

    int deleteByExample(EthvalidationExample example);

    int deleteByPrimaryKey(EthvalidationKey key);

    int insert(Ethvalidation record);

    int insertSelective(Ethvalidation record);

    List<Ethvalidation> selectByExample(EthvalidationExample example);

    Ethvalidation selectByPrimaryKey(EthvalidationKey key);

    int updateByExampleSelective(@Param("record") Ethvalidation record, @Param("example") EthvalidationExample example);

    int updateByExample(@Param("record") Ethvalidation record, @Param("example") EthvalidationExample example);

    int updateByPrimaryKeySelective(Ethvalidation record);

    int updateByPrimaryKey(Ethvalidation record);
}