package com.superwallet.controller;

import com.superwallet.common.SuperResult;
import com.superwallet.pojo.BannerWithBLOBs;
import com.superwallet.pojo.Gamelist;
import com.superwallet.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 获取广告游戏列表
     *
     * @return
     */
    @RequestMapping(value = "/content/listFixGame", method = RequestMethod.GET)
    @ResponseBody
    public SuperResult listFixGames() {
        List<Gamelist> gamelists = contentService.listFixGames();
        return SuperResult.ok(gamelists);
    }

    /**
     * 返回普通游戏列表
     *
     * @param gameRankLeft
     * @param gameRankRight
     * @return
     */
    @RequestMapping(value = "/content/listSimpleGame", method = RequestMethod.GET)
    @ResponseBody
    public SuperResult listSimpleGames(int gameRankLeft, int gameRankRight) {
        List<Gamelist> gamelists = contentService.listSimpleGames(gameRankLeft, gameRankRight);
        return SuperResult.ok(gamelists);
    }

    /**
     * 返回广告
     *
     * @return
     */
    @RequestMapping(value = "/content/listBanner", method = RequestMethod.GET)
    @ResponseBody
    public SuperResult listBanners() {
        List<BannerWithBLOBs> banners = contentService.listBanner();
        return SuperResult.ok(banners);
    }


}
