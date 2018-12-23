package com.superwallet.mapper;

import org.apache.ibatis.annotations.Param;
import pojo.Userbasic;
import pojo.UserbasicExample;

import java.util.List;

public interface UserbasicMapper {
    int countByExample(UserbasicExample example);

    int deleteByExample(UserbasicExample example);

    int deleteByPrimaryKey(String uid);

    int insert(Userbasic record);

    int insertSelective(Userbasic record);

    List<Userbasic> selectByExampleWithBLOBs(UserbasicExample example);

    List<Userbasic> selectByExample(UserbasicExample example);

    Userbasic selectByPrimaryKey(String uid);

    int updateByExampleSelective(@Param("record") Userbasic record, @Param("example") UserbasicExample example);

    int updateByExampleWithBLOBs(@Param("record") Userbasic record, @Param("example") UserbasicExample example);

    int updateByExample(@Param("record") Userbasic record, @Param("example") UserbasicExample example);

    int updateByPrimaryKeySelective(Userbasic record);

    int updateByPrimaryKeyWithBLOBs(Userbasic record);

    int updateByPrimaryKey(Userbasic record);
}