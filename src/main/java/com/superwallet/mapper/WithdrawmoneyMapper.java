package com.superwallet.mapper;

import org.apache.ibatis.annotations.Param;
import pojo.Withdrawmoney;
import pojo.WithdrawmoneyExample;
import pojo.WithdrawmoneyKey;

import java.util.List;

public interface WithdrawmoneyMapper {
    int countByExample(WithdrawmoneyExample example);

    int deleteByExample(WithdrawmoneyExample example);

    int deleteByPrimaryKey(WithdrawmoneyKey key);

    int insert(Withdrawmoney record);

    int insertSelective(Withdrawmoney record);

    List<Withdrawmoney> selectByExample(WithdrawmoneyExample example);

    Withdrawmoney selectByPrimaryKey(WithdrawmoneyKey key);

    int updateByExampleSelective(@Param("record") Withdrawmoney record, @Param("example") WithdrawmoneyExample example);

    int updateByExample(@Param("record") Withdrawmoney record, @Param("example") WithdrawmoneyExample example);

    int updateByPrimaryKeySelective(Withdrawmoney record);

    int updateByPrimaryKey(Withdrawmoney record);
}