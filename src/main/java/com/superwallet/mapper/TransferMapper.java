package com.superwallet.mapper;

import org.apache.ibatis.annotations.Param;
import pojo.Transfer;
import pojo.TransferExample;
import pojo.TransferKey;

import java.util.List;

public interface TransferMapper {
    int countByExample(TransferExample example);

    int deleteByExample(TransferExample example);

    int deleteByPrimaryKey(TransferKey key);

    int insert(Transfer record);

    int insertSelective(Transfer record);

    List<Transfer> selectByExample(TransferExample example);

    Transfer selectByPrimaryKey(TransferKey key);

    int updateByExampleSelective(@Param("record") Transfer record, @Param("example") TransferExample example);

    int updateByExample(@Param("record") Transfer record, @Param("example") TransferExample example);

    int updateByPrimaryKeySelective(Transfer record);

    int updateByPrimaryKey(Transfer record);
}