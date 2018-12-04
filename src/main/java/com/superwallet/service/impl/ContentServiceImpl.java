package com.superwallet.service.impl;

import com.superwallet.mapper.BannerMapper;
import com.superwallet.mapper.GamelistMapper;
import com.superwallet.pojo.BannerExample;
import com.superwallet.pojo.BannerWithBLOBs;
import com.superwallet.pojo.Gamelist;
import com.superwallet.pojo.GamelistExample;
import com.superwallet.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 内容管理
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private GamelistMapper gamelistMapper;

    @Autowired
    private BannerMapper bannerMapper;

    /**
     * 展示购买位的游戏列表
     *
     * @return
     */
    @Override
    public List<Gamelist> listFixGames() {
        GamelistExample gamelistExample = new GamelistExample();
        GamelistExample.Criteria criteria = gamelistExample.createCriteria();
        //0-普通游戏 1-广告游戏
        criteria.andTypeEqualTo(new Byte("1"));
        List<Gamelist> gamelists = gamelistMapper.selectByExample(gamelistExample);
        return gamelists;
    }

    /**
     * 获取普通游戏列表
     *
     * @param gameRankLeft
     * @param gameRankRight
     * @return
     */
    @Override
    public List<Gamelist> listSimpleGames(Integer gameRankLeft, Integer gameRankRight) {
        GamelistExample gamelistExample = new GamelistExample();
        GamelistExample.Criteria criteria = gamelistExample.createCriteria();
        //0-普通游戏 1-广告游戏
        criteria.andTypeEqualTo(new Byte("0"));
        //TODO 游戏列表排序展示 rankLeft rankRight
        List<Gamelist> gamelists = gamelistMapper.selectByExample(gamelistExample);
        return gamelists;
    }

    /**
     * 查询广告列表
     *
     * @return
     */
    @Override
    public List<BannerWithBLOBs> listBanner() {
        BannerExample bannerExample = new BannerExample();
        List<BannerWithBLOBs> banners = bannerMapper.selectByExampleWithBLOBs(bannerExample);
        return banners;
    }
}
