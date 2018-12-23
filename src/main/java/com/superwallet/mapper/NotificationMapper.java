package com.superwallet.mapper;

import org.apache.ibatis.annotations.Param;
import pojo.Notification;
import pojo.NotificationExample;

import java.util.List;

public interface NotificationMapper {
    int countByExample(NotificationExample example);

    int deleteByExample(NotificationExample example);

    int deleteByPrimaryKey(Long nid);

    int insert(Notification record);

    int insertSelective(Notification record);

    List<Notification> selectByExampleWithBLOBs(NotificationExample example);

    List<Notification> selectByExample(NotificationExample example);

    Notification selectByPrimaryKey(Long nid);

    int updateByExampleSelective(@Param("record") Notification record, @Param("example") NotificationExample example);

    int updateByExampleWithBLOBs(@Param("record") Notification record, @Param("example") NotificationExample example);

    int updateByExample(@Param("record") Notification record, @Param("example") NotificationExample example);

    int updateByPrimaryKeySelective(Notification record);

    int updateByPrimaryKeyWithBLOBs(Notification record);

    int updateByPrimaryKey(Notification record);
}