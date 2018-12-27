package com.superwallet.mapper;

import com.superwallet.pojo.Inviter;
import com.superwallet.pojo.InviterExample;
import com.superwallet.pojo.InviterKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InviterMapper {
    int countByExample(InviterExample example);

    int deleteByExample(InviterExample example);

    int deleteByPrimaryKey(InviterKey key);

    int insert(Inviter record);

    int insertSelective(Inviter record);

    List<Inviter> selectByExample(InviterExample example);

    Inviter selectByPrimaryKey(InviterKey key);

    int updateByExampleSelective(@Param("record") Inviter record, @Param("example") InviterExample example);

    int updateByExample(@Param("record") Inviter record, @Param("example") InviterExample example);

    int updateByPrimaryKeySelective(Inviter record);

    int updateByPrimaryKey(Inviter record);
}