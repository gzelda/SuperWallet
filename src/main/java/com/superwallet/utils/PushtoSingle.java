package com.superwallet.utils;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;

public class PushtoSingle {
    //采用"Java SDK 快速入门"， "第二步 获取访问凭证 "中获得的应用配置，用户可以自行替换
    private static String appId = "edsJ02bj187dY9s55UGzG6";
    private static String appKey = "rT7vwXLTU79neQtKlUtvf9";
    private static String masterSecret = "cv7O22Xrq79icwbrIygBn1";

    static String CID = "69ae53040472f98831a738f0f69c646a";
    //别名推送方式
//    static String Alias = "UID";
    static String host = "http://sdk.open.api.igexin.com/apiex.htm";

    public static void pushMessage(String title, String text, String url, String phoneNum) {
        IGtPush push = new IGtPush(host, appKey, masterSecret);
        //ios端
        TransmissionTemplate template = getTemplate(title, text);
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);

        //安卓端
        LinkTemplate template2 = linkTemplateDemo(title, text, url);
        SingleMessage message2 = new SingleMessage();
        message2.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message2.setOfflineExpireTime(24 * 3600 * 1000);
        message2.setData(template2);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message2.setPushNetWorkType(0);

        Target target = new Target();
        target.setAppId(appId);
//        target.setClientId(CID);
        target.setAlias(phoneNum);
        IPushResult ret1 = null;
        IPushResult ret2 = null;
        try {
            ret1 = push.pushMessageToSingle(message, target);
            ret2 = push.pushMessageToSingle(message2, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret1 = push.pushMessageToSingle(message, target, e.getRequestId());
            ret2 = push.pushMessageToSingle(message2, target, e.getRequestId());
        }
        if (ret1 != null || ret2 != null) {
            System.out.println(ret1.getResponse().toString());
            System.out.println(ret2.getResponse().toString());
        } else {
            System.out.println("服务器响应异常");
        }
    }

    public static void main(String[] args) {
        IGtPush push = new IGtPush(host, appKey, masterSecret);
        TransmissionTemplate template = getTemplate("标题1", "内容1");
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);

        LinkTemplate template2 = linkTemplateDemo("标题1", "内容1", "链接1");
        SingleMessage message2 = new SingleMessage();
        message2.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message2.setOfflineExpireTime(24 * 3600 * 1000);
        message2.setData(template2);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message2.setPushNetWorkType(0);

        Target target = new Target();
        target.setAppId(appId);
        //target.setClientId(CID);
        target.setAlias("8617695556242");
        IPushResult ret1 = null;
        IPushResult ret2 = null;
        try {
            ret1 = push.pushMessageToSingle(message, target);
            ret2 = push.pushMessageToSingle(message2, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret1 = push.pushMessageToSingle(message, target, e.getRequestId());
            ret2 = push.pushMessageToSingle(message2, target, e.getRequestId());
        }
        if (ret1 != null || ret2 != null) {
            System.out.println(ret1.getResponse().toString());
            System.out.println(ret2.getResponse().toString());
        } else {
            System.out.println("服务器响应异常");
        }
    }


    private static TransmissionTemplate getTemplate(String title, String text) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        template.setTransmissionContent(text);
        template.setTransmissionType(2);
        APNPayload payload = new APNPayload();
        //在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
        payload.setAutoBadge("+1");
        payload.setContentAvailable(1);
        payload.setSound("default");
        payload.setCategory("$由客户端定义");
        payload.addCustomMsg("payload", "payload");

        //简单模式APNPayload.SimpleMsg
        payload.setAlertMsg(getDictionaryAlertMsg(title, text));
        template.setAPNInfo(payload);
        return template;
    }

    private static APNPayload.DictionaryAlertMsg getDictionaryAlertMsg(String title, String text) {
        APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
        alertMsg.setBody(text);
        alertMsg.setActionLocKey("ActionLockey");
        alertMsg.setLocKey("LocKey");
        alertMsg.addLocArg("loc-args");
        alertMsg.setLaunchImage("launch-image");
        // iOS8.2以上版本支持
        alertMsg.setTitle(title);
        alertMsg.setTitleLocKey("TitleLocKey");
        alertMsg.addTitleLocArg("TitleLocArg");
        return alertMsg;
    }

    public static LinkTemplate linkTemplateDemo(String title, String text, String url) {
        LinkTemplate template = new LinkTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);

        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(title);
        style.setText(text);
        // 配置通知栏图标
        style.setLogo("icon.png");
        // 配置通知栏网络图标
        style.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        style.setRing(true);
        style.setVibrate(true);
        style.setClearable(true);
        template.setStyle(style);
        // 设置打开的网址地址
        template.setUrl(url);
        return template;
    }
}



