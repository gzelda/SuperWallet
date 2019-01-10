package com.superwallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.superwallet.common.CodeRepresentation;
import com.superwallet.common.RequestParams;
import com.superwallet.common.SuperResult;
import com.superwallet.service.EOSScatterService;
import com.superwallet.utils.HttpUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class EOSScatterServiceImpl implements EOSScatterService {
    @Override
    public SuperResult getOrRequestIdentity(String UID) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(RequestParams.UID, UID);
        String requestUrl = CodeRepresentation.NODE_URL_EOS + CodeRepresentation.NODE_ACTION_EOS_SCATTER_GETORREQUESTIDENTITY;
        String resp = HttpUtil.post(requestUrl, params);
        SuperResult result = JSON.parseObject(resp, SuperResult.class);
        return result;
    }

    @Override
    public SuperResult identityFromPermissions(String UID) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(RequestParams.UID, UID);
        String requestUrl = CodeRepresentation.NODE_URL_EOS + CodeRepresentation.NODE_ACTION_EOS_SCATTER_IDENTITYFROMPERMISSIONS;
        String resp = HttpUtil.post(requestUrl, params);
        SuperResult result = JSON.parseObject(resp, SuperResult.class);
        return result;
    }

    @Override
    public SuperResult requestSignature(String UID, String buf) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(RequestParams.UID, UID);
        params.put(RequestParams.BUF, buf);
        String requestUrl = CodeRepresentation.NODE_URL_EOS + CodeRepresentation.NODE_ACTION_EOS_SCATTER_REQUESTSIGNATURE;
        String resp = HttpUtil.post(requestUrl, params);
        SuperResult result = JSON.parseObject(resp, SuperResult.class);
        return result;
    }

    @Override
    public SuperResult getOriginData(String data) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(RequestParams.DATA, data);
        String requestUrl = CodeRepresentation.NODE_URL_EOS + CodeRepresentation.NODE_ACTION_EOS_SCATTER_GETORIGINDATA;
        String resp = HttpUtil.post(requestUrl, params);
        System.out.println(resp);
        SuperResult result = JSON.parseObject(resp, SuperResult.class);
        return result;
    }
}
