package com.superwallet.service;

import com.superwallet.common.SuperResult;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {
    SuperResult getUserByToken(String token, HttpServletRequest request);
}
