package com.superwallet.mapper;

import com.superwallet.pojo.Profit;
import com.superwallet.pojo.ProfitExample;
import com.superwallet.pojo.ProfitKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProfitMapper {
    int countByExample(ProfitExample example);

    int deleteByExample(ProfitExample example);

    int deleteByPrimaryKey(ProfitKey key);

    int insert(Profit record);

    int insertSelective(Profit record);

    List<Profit> selectByExample(ProfitExample example);

    Profit selectByPrimaryKey(ProfitKey key);

    int updateByExampleSelective(@Param("record") Profit record, @Param("example") ProfitExample example);

    int updateByExample(@Param("record") Profit record, @Param("example") ProfitExample example);

    int updateByPrimaryKeySelective(Profit record);

    int updateByPrimaryKey(Profit record);
}