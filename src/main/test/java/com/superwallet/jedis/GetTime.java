package com.superwallet.jedis;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class GetTime {

    @Test
    public void getTime() throws ParseException {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long beginTime = parser.parse("2018-06-22 00:00:00").getTime();
        long endTime = parser.parse("2018-06-23 00:00:00").getTime();
        System.out.println(beginTime);
        System.out.println(endTime);
    }
}
