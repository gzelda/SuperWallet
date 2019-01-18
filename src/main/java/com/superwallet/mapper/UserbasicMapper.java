package com.superwallet.mapper;

import com.superwallet.pojo.Userbasic;
import com.superwallet.pojo.UserbasicExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserbasicMapper {
    int countByExample(UserbasicExample example);

    int deleteByExample(UserbasicExample example);

    int deleteByPrimaryKey(String uid);

    int insert(Userbasic record);

    int insertSelective(Userbasic record);

    List<Userbasic> selectByExample(UserbasicExample example);

    Userbasic selectByPrimaryKey(String uid);

    int updateByExampleSelective(@Param("record") Userbasic record, @Param("example") UserbasicExample example);

    int updateByExample(@Param("record") Userbasic record, @Param("example") UserbasicExample example);

    int updateByPrimaryKeySelective(Userbasic record);

    int updateByPrimaryKey(Userbasic record);
}