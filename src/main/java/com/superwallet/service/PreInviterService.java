package com.superwallet.service;

import com.superwallet.common.SuperResult;

public interface PreInviterService {
    SuperResult genPreInviterRecord(String phoneNum, String invitedCode);
}
