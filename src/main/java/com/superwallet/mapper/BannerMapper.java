package com.superwallet.mapper;

import org.apache.ibatis.annotations.Param;
import pojo.Banner;
import pojo.BannerExample;
import pojo.BannerWithBLOBs;

import java.util.List;

public interface BannerMapper {
    int countByExample(BannerExample example);

    int deleteByExample(BannerExample example);

    int deleteByPrimaryKey(Long bid);

    int insert(BannerWithBLOBs record);

    int insertSelective(BannerWithBLOBs record);

    List<BannerWithBLOBs> selectByExampleWithBLOBs(BannerExample example);

    List<Banner> selectByExample(BannerExample example);

    BannerWithBLOBs selectByPrimaryKey(Long bid);

    int updateByExampleSelective(@Param("record") BannerWithBLOBs record, @Param("example") BannerExample example);

    int updateByExampleWithBLOBs(@Param("record") BannerWithBLOBs record, @Param("example") BannerExample example);

    int updateByExample(@Param("record") Banner record, @Param("example") BannerExample example);

    int updateByPrimaryKeySelective(BannerWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(BannerWithBLOBs record);

    int updateByPrimaryKey(Banner record);
}