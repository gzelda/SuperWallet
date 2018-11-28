package com.superwallet.service;

import com.superwallet.mapper.ItemsMapper;
import com.superwallet.pojo.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemsServiceImpl implements ItemsService {

    @Autowired
    private ItemsMapper itemMapper;

    @Override
    public List<Items> queryItemList() {
        // 从数据库查询商品数据
        List<Items> list = this.itemMapper.selectByExample(null);

        return list;
    }

    @Override
    public Items queryItemById(int id) {
        Items item = this.itemMapper.selectByPrimaryKey(id);

        return item;
    }

    @Override
    public void updateItemById(Items item) {
        this.itemMapper.updateByPrimaryKeySelective(item);
    }

}
