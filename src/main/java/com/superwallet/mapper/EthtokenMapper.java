package com.superwallet.mapper;

import com.superwallet.pojo.Ethtoken;
import com.superwallet.pojo.EthtokenExample;
import com.superwallet.pojo.EthtokenKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EthtokenMapper {
    int countByExample(EthtokenExample example);

    int deleteByExample(EthtokenExample example);

    int deleteByPrimaryKey(EthtokenKey key);

    int insert(Ethtoken record);

    int insertSelective(Ethtoken record);

    List<Ethtoken> selectByExample(EthtokenExample example);

    Ethtoken selectByPrimaryKey(EthtokenKey key);

    int updateByExampleSelective(@Param("record") Ethtoken record, @Param("example") EthtokenExample example);

    int updateByExample(@Param("record") Ethtoken record, @Param("example") EthtokenExample example);

    int updateByPrimaryKeySelective(Ethtoken record);

    int updateByPrimaryKey(Ethtoken record);
}