package com.superwallet.mapper;

import org.apache.ibatis.annotations.Param;
import pojo.Userstatus;
import pojo.UserstatusExample;

import java.util.List;

public interface UserstatusMapper {
    int countByExample(UserstatusExample example);

    int deleteByExample(UserstatusExample example);

    int deleteByPrimaryKey(String uid);

    int insert(Userstatus record);

    int insertSelective(Userstatus record);

    List<Userstatus> selectByExample(UserstatusExample example);

    Userstatus selectByPrimaryKey(String uid);

    int updateByExampleSelective(@Param("record") Userstatus record, @Param("example") UserstatusExample example);

    int updateByExample(@Param("record") Userstatus record, @Param("example") UserstatusExample example);

    int updateByPrimaryKeySelective(Userstatus record);

    int updateByPrimaryKey(Userstatus record);
}