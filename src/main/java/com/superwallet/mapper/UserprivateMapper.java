package com.superwallet.mapper;

import org.apache.ibatis.annotations.Param;
import pojo.Userprivate;
import pojo.UserprivateExample;
import pojo.UserprivateWithBLOBs;

import java.util.List;

public interface UserprivateMapper {
    int countByExample(UserprivateExample example);

    int deleteByExample(UserprivateExample example);

    int deleteByPrimaryKey(String uid);

    int insert(UserprivateWithBLOBs record);

    int insertSelective(UserprivateWithBLOBs record);

    List<UserprivateWithBLOBs> selectByExampleWithBLOBs(UserprivateExample example);

    List<Userprivate> selectByExample(UserprivateExample example);

    UserprivateWithBLOBs selectByPrimaryKey(String uid);

    int updateByExampleSelective(@Param("record") UserprivateWithBLOBs record, @Param("example") UserprivateExample example);

    int updateByExampleWithBLOBs(@Param("record") UserprivateWithBLOBs record, @Param("example") UserprivateExample example);

    int updateByExample(@Param("record") Userprivate record, @Param("example") UserprivateExample example);

    int updateByPrimaryKeySelective(UserprivateWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(UserprivateWithBLOBs record);

    int updateByPrimaryKey(Userprivate record);
}