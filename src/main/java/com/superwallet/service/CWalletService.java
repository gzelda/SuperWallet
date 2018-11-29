package com.superwallet.service;

import com.superwallet.common.WalletInfo;

import java.util.List;

public interface CWalletService {
    public List<WalletInfo> listCWalletInfo(String UID);
}
