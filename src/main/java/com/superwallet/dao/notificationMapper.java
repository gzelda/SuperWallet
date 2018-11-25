package com.superwallet.dao;

import com.superwallet.model.notification;

public interface notificationMapper {
    int deleteByPrimaryKey(Long nid);

    int insert(notification record);

    int insertSelective(notification record);

    notification selectByPrimaryKey(Long nid);

    int updateByPrimaryKeySelective(notification record);

    int updateByPrimaryKeyWithBLOBs(notification record);

    int updateByPrimaryKey(notification record);
}