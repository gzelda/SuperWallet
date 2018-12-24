package com.superwallet.utils;

import java.io.Serializable;

public class CoinConvertUtils implements Serializable {
    public static String type2nameMapping(Integer tokenType) {
        switch (tokenType) {
            case 0:
                return "ETH";
            case 1:
                return "EOS";
            case 2:
                return "BGS";
        }
        return "UNKNOWN";
    }

}
