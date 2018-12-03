package com.superwallet.common.utils;

import com.superwallet.common.CodeRepresentation;
import com.superwallet.utils.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

public class OnLinePriceTest {

    @Test
    public void getOnlinePrice() throws Exception {
        String origin = HttpUtil.get(CodeRepresentation.URL_PRICE);
        Document document = Jsoup.parse(origin);
        Elements eth_price = document.getElementById("id-ethereum").getElementsByClass("price");
        Elements eos_price = document.getElementById("id-eos").getElementsByClass("price");
        System.out.println(eos_price.text());
    }

}
