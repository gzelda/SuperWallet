package com.superwallet.mapper;

import com.superwallet.pojo.Banner;
import com.superwallet.pojo.BannerExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerMapper {
    int countByExample(BannerExample example);

    int deleteByExample(BannerExample example);

    int deleteByPrimaryKey(Long bid);

    int insert(Banner record);

    int insertSelective(Banner record);

    List<Banner> selectByExampleWithBLOBs(BannerExample example);

    List<Banner> selectByExample(BannerExample example);

    Banner selectByPrimaryKey(Long bid);

    int updateByExampleSelective(@Param("record") Banner record, @Param("example") BannerExample example);

    int updateByExampleWithBLOBs(@Param("record") Banner record, @Param("example") BannerExample example);

    int updateByExample(@Param("record") Banner record, @Param("example") BannerExample example);

    int updateByPrimaryKeySelective(Banner record);

    int updateByPrimaryKeyWithBLOBs(Banner record);
}