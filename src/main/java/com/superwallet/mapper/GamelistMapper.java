package com.superwallet.mapper;

import org.apache.ibatis.annotations.Param;
import pojo.Gamelist;
import pojo.GamelistExample;
import pojo.GamelistWithBLOBs;

import java.util.List;

public interface GamelistMapper {
    int countByExample(GamelistExample example);

    int deleteByExample(GamelistExample example);

    int deleteByPrimaryKey(Long gid);

    int insert(GamelistWithBLOBs record);

    int insertSelective(GamelistWithBLOBs record);

    List<GamelistWithBLOBs> selectByExampleWithBLOBs(GamelistExample example);

    List<Gamelist> selectByExample(GamelistExample example);

    GamelistWithBLOBs selectByPrimaryKey(Long gid);

    int updateByExampleSelective(@Param("record") GamelistWithBLOBs record, @Param("example") GamelistExample example);

    int updateByExampleWithBLOBs(@Param("record") GamelistWithBLOBs record, @Param("example") GamelistExample example);

    int updateByExample(@Param("record") Gamelist record, @Param("example") GamelistExample example);

    int updateByPrimaryKeySelective(GamelistWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(GamelistWithBLOBs record);

    int updateByPrimaryKey(Gamelist record);
}