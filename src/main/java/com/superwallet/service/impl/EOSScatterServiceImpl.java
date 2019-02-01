package com.superwallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.superwallet.common.CodeRepresentation;
import com.superwallet.common.RequestParams;
import com.superwallet.common.SuperResult;
import com.superwallet.response.ResponseEOSScatterCPUNETEntry;
import com.superwallet.service.CommonService;
import com.superwallet.service.EOSScatterService;
import com.superwallet.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class EOSScatterServiceImpl implements EOSScatterService {

    @Autowired
    private CommonService commonService;

    @Override
    public SuperResult getOrRequestIdentity(String UID) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(RequestParams.UID, UID);
        String requestUrl = CodeRepresentation.NODE_URL_EOS + CodeRepresentation.NODE_ACTION_EOS_SCATTER_GETORREQUESTIDENTITY;
        String resp = HttpUtil.post(requestUrl, params);
//        System.out.println(resp);
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
    public SuperResult requestSignature(String UID, String buf, int restFreeTimes) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(RequestParams.UID, UID);
        params.put(RequestParams.BUF, buf);
        params.put(RequestParams.RESTFREETIMES, restFreeTimes);
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

    /**
     * 拿到EOS的CPU、NET的剩余百分比
     *
     * @return
     */
    @Override
    public ResponseEOSScatterCPUNETEntry getCPUNETPercent(String UID) {
        SuperResult result = commonService.getEOSInfo(UID);
        if (result.getCode() == CodeRepresentation.CODE_FAIL) {
            return null;
        }
        JSONObject eos_json = JSON.parseObject(result.getData().toString());
        JSONObject cpu_json = JSON.parseObject(eos_json.getString("cpu_limit"));
        JSONObject net_json = JSON.parseObject(eos_json.getString("net_limit"));
        ResponseEOSScatterCPUNETEntry info;
        try {
            double total_cpu = cpu_json.getDouble("max");
            double used_cpu = cpu_json.getDouble("used");
            double remain_cpu = cpu_json.getDouble("available");
            double total_net = net_json.getDouble("max");
            double used_net = net_json.getDouble("used");
            double remain_net = net_json.getDouble("available");
            double cpu_remain_percent = remain_cpu / total_cpu * 100.0;
            if (cpu_remain_percent < 0) cpu_remain_percent = 0.0;
            double net_remain_percent = remain_net / total_net * 100.0;
            if (net_remain_percent < 0) net_remain_percent = 0.0;
            info = new ResponseEOSScatterCPUNETEntry(cpu_remain_percent, net_remain_percent);
        } catch (Exception e) {
            info = new ResponseEOSScatterCPUNETEntry(100, 100);
        }
        return info;
    }
}
