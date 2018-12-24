package com.superwallet.mapper;

import com.superwallet.pojo.Banner;
import com.superwallet.pojo.BannerExample;
import com.superwallet.pojo.BannerWithBLOBs;
import org.apache.ibatis.annotations.Param;

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