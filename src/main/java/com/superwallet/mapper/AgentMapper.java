package com.superwallet.mapper;

import com.superwallet.pojo.Agent;
import com.superwallet.pojo.AgentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AgentMapper {
    int countByExample(AgentExample example);

    int deleteByExample(AgentExample example);

    int deleteByPrimaryKey(String uid);

    int insert(Agent record);

    int insertSelective(Agent record);

    List<Agent> selectByExample(AgentExample example);

    Agent selectByPrimaryKey(String uid);

    int updateByExampleSelective(@Param("record") Agent record, @Param("example") AgentExample example);

    int updateByExample(@Param("record") Agent record, @Param("example") AgentExample example);

    int updateByPrimaryKeySelective(Agent record);

    int updateByPrimaryKey(Agent record);
}