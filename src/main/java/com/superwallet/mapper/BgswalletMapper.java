package com.superwallet.mapper;

import com.superwallet.pojo.Bgswallet;
import com.superwallet.pojo.BgswalletExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BgswalletMapper {
    int countByExample(BgswalletExample example);

    int deleteByExample(BgswalletExample example);

    int deleteByPrimaryKey(String uid);

    int insert(Bgswallet record);

    int insertSelective(Bgswallet record);

    List<Bgswallet> selectByExample(BgswalletExample example);

    Bgswallet selectByPrimaryKey(String uid);

    int updateByExampleSelective(@Param("record") Bgswallet record, @Param("example") BgswalletExample example);

    int updateByExample(@Param("record") Bgswallet record, @Param("example") BgswalletExample example);

    int updateByPrimaryKeySelective(Bgswallet record);

    int updateByPrimaryKey(Bgswallet record);
}