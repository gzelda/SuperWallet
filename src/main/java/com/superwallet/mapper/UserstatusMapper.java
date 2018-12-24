package com.superwallet.mapper;

import com.superwallet.pojo.Userstatus;
import com.superwallet.pojo.UserstatusExample;
import org.apache.ibatis.annotations.Param;

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