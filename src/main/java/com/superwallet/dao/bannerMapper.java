package com.superwallet.dao;

import com.superwallet.model.banner;

public interface bannerMapper {
    int deleteByPrimaryKey(Long bid);

    int insert(banner record);

    int insertSelective(banner record);

    banner selectByPrimaryKey(Long bid);

    int updateByPrimaryKeySelective(banner record);

    int updateByPrimaryKeyWithBLOBs(banner record);
}