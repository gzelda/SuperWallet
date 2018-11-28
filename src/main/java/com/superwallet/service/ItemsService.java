package com.superwallet.service;

import com.superwallet.pojo.Items;

import java.util.List;

public interface ItemsService {

    /**
     * 查询商品列表
     *
     * @return
     */
    List<Items> queryItemList();

    /*
     * 根据商品id查询商品
     */
    Items queryItemById(int id);
    /**
     * 根据id更新商品
     *
     * @param item
     */
    void updateItemById(Items item);

}
