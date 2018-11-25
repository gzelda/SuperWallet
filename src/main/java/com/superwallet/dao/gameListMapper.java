package com.superwallet.dao;

import com.superwallet.model.gameList;
import com.superwallet.model.gameListWithBLOBs;

public interface gameListMapper {
    int deleteByPrimaryKey(Long gid);

    int insert(gameListWithBLOBs record);

    int insertSelective(gameListWithBLOBs record);

    gameListWithBLOBs selectByPrimaryKey(Long gid);

    int updateByPrimaryKeySelective(gameListWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(gameListWithBLOBs record);

    int updateByPrimaryKey(gameList record);
}