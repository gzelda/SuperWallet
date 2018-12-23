package com.superwallet.mapper;

import org.apache.ibatis.annotations.Param;
import pojo.Eostoken;
import pojo.EostokenExample;
import pojo.EostokenKey;

import java.util.List;

public interface EostokenMapper {
    int countByExample(EostokenExample example);

    int deleteByExample(EostokenExample example);

    int deleteByPrimaryKey(EostokenKey key);

    int insert(Eostoken record);

    int insertSelective(Eostoken record);

    List<Eostoken> selectByExample(EostokenExample example);

    Eostoken selectByPrimaryKey(EostokenKey key);

    int updateByExampleSelective(@Param("record") Eostoken record, @Param("example") EostokenExample example);

    int updateByExample(@Param("record") Eostoken record, @Param("example") EostokenExample example);

    int updateByPrimaryKeySelective(Eostoken record);

    int updateByPrimaryKey(Eostoken record);
}