package com.superwallet.mapper;

import com.superwallet.pojo.Eoswallet;
import com.superwallet.pojo.EoswalletExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EoswalletMapper {
    int countByExample(EoswalletExample example);

    int deleteByExample(EoswalletExample example);

    int deleteByPrimaryKey(String uid);

    int insert(Eoswallet record);

    int insertSelective(Eoswallet record);

    List<Eoswallet> selectByExample(EoswalletExample example);

    Eoswallet selectByPrimaryKey(String uid);

    int updateByExampleSelective(@Param("record") Eoswallet record, @Param("example") EoswalletExample example);

    int updateByExample(@Param("record") Eoswallet record, @Param("example") EoswalletExample example);

    int updateByPrimaryKeySelective(Eoswallet record);

    int updateByPrimaryKey(Eoswallet record);
}