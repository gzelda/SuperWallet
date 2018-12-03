package com.superwallet.service;

import com.superwallet.pojo.Banner;
import com.superwallet.pojo.Gamelist;

import java.util.List;

public interface ContentService {
    public List<Gamelist> listFixGames();

    public List<Gamelist> listSimpleGames(Integer gameRankLeft, Integer gameRankRight);

    public List<Banner> listBanner();
}
