package com.superwallet.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.superwallet.utils.CodeGenerator;
import com.superwallet.utils.HttpUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class SendMessageToPhoneTest {
    private static final Logger logger = LogManager.getLogger(HttpUtil.class);

    public static void main(String[] args) {
        //请求地址
        String url = "http://intapi.253.com/send/json";

        //API账号，50位以内。必填
        String account = "I4471424";

        //API账号对应密钥，联系客服获取。必填
        String password = "TghkGlfVEN01fa";

        //验证码
        String phoneCode = CodeGenerator.smsCode();

        //短信内容。长度不能超过536个字符。必填
        String msg = "【superwallet】您的验证码是：" + phoneCode;

        //手机号码，格式(区号+手机号码)，例如：8615800000000，其中86为中国的区号，区号前不使用00开头,15800000000为接收短信的真实手机号码。5-20位。必填
        String mobile = "8618862173084";

        //用户收到短信之后显示的发件人，国内不支持自定义，国外支持，但是需要提前和运营商沟通注册，具体请与TIG对接人员确定。选填
        String senderId = "";

        //组装请求参数
        JSONObject map = new JSONObject();
        map.put("account", account);
        map.put("password", password);
        map.put("msg", msg);
        map.put("mobile", mobile);
        map.put("senderId", senderId);

        String params = map.toString();

        logger.info("请求参数为:" + params);
        try {
            String result = HttpUtil.post(url, params);

            logger.info("返回参数为:" + result);

            JSONObject jsonObject = JSON.parseObject(result);
            String code = jsonObject.get("code").toString();
            String msgid = jsonObject.get("msgid").toString();
            String error = jsonObject.get("error").toString();
            System.out.println("状态码:" + code + ",状态码说明:" + error + ",消息id:" + msgid);
            logger.info("状态码:" + code + ",状态码说明:" + error + ",消息id:" + msgid);
        } catch (Exception e) {
            logger.error("请求异常：" + e);
        }
    }
}
