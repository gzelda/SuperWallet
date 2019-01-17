package com.superwallet.mapper;

import com.superwallet.pojo.Optconf;
import com.superwallet.pojo.OptconfExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OptconfMapper {
    int countByExample(OptconfExample example);

    int deleteByExample(OptconfExample example);

    int deleteByPrimaryKey(String confname);

    int insert(Optconf record);

    int insertSelective(Optconf record);

    List<Optconf> selectByExample(OptconfExample example);

    Optconf selectByPrimaryKey(String confname);

    int updateByExampleSelective(@Param("record") Optconf record, @Param("example") OptconfExample example);

    int updateByExample(@Param("record") Optconf record, @Param("example") OptconfExample example);

    int updateByPrimaryKeySelective(Optconf record);

    int updateByPrimaryKey(Optconf record);
}