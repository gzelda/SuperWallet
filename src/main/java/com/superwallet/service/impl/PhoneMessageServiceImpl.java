package com.superwallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.superwallet.service.PhoneMessageService;
import com.superwallet.utils.HttpUtil;
import org.springframework.stereotype.Service;

@Service
public class PhoneMessageServiceImpl implements PhoneMessageService {

    /**
     * 发现短信验证码
     *
     * @param phoneNum
     * @param phoneCode
     * @return
     */
    @Override
    public int sendMessage(String phoneNum, String phoneCode) {
        //请求地址
        String url = "http://intapi.253.com/send/json";

        //API账号，50位以内。必填
        String account = "I4471424";

        //API账号对应密钥，联系客服获取。必填
        String password = "TghkGlfVEN01fa";

        //短信内容。长度不能超过536个字符。必填
        String msg = "【superwallet】您的验证码是：" + phoneCode;

        //手机号码，格式(区号+手机号码)，例如：8615800000000，其中86为中国的区号，区号前不使用00开头,15800000000为接收短信的真实手机号码。5-20位。必填

        //用户收到短信之后显示的发件人，国内不支持自定义，国外支持，但是需要提前和运营商沟通注册，具体请与TIG对接人员确定。选填
        String senderId = "";

        //组装请求参数
        JSONObject map = new JSONObject();
        map.put("account", account);
        map.put("password", password);
        map.put("msg", msg);
        map.put("mobile", phoneNum);
        map.put("senderId", senderId);

        String params = map.toString();
        String code = "", msgid, error;
        try {
            String result = HttpUtil.post(url, params);
            JSONObject jsonObject = JSON.parseObject(result);
            code = jsonObject.get("code").toString();
            msgid = jsonObject.get("msgid").toString();
            error = jsonObject.get("error").toString();
//            System.out.println("状态码:" + code + ",状态码说明:" + error + ",消息id:" + msgid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (!code.equals("") && code.equals("0")) ? 1 : 0;
    }
}
