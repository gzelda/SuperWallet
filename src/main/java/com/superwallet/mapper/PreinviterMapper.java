package com.superwallet.mapper;

import com.superwallet.pojo.Preinviter;
import com.superwallet.pojo.PreinviterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PreinviterMapper {
    int countByExample(PreinviterExample example);

    int deleteByExample(PreinviterExample example);

    int deleteByPrimaryKey(String phonenum);

    int insert(Preinviter record);

    int insertSelective(Preinviter record);

    List<Preinviter> selectByExample(PreinviterExample example);

    Preinviter selectByPrimaryKey(String phonenum);

    int updateByExampleSelective(@Param("record") Preinviter record, @Param("example") PreinviterExample example);

    int updateByExample(@Param("record") Preinviter record, @Param("example") PreinviterExample example);

    int updateByPrimaryKeySelective(Preinviter record);

    int updateByPrimaryKey(Preinviter record);
}