package com.superwallet.controller.test;

import com.superwallet.utils.ByteImageConvert;
import com.superwallet.utils.HttpUtil;
import org.junit.Test;

import java.util.HashMap;

public class LoginControllerTest {

    private String url = "http://localhost:8080";

    @Test
    public void regGetIDCode() {
        String res = HttpUtil.get(url + "/register/getIDCode?phoneNum=8618862173084");
        System.out.println(res);
    }

    @Test
    public void regRegisterConfirm() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("phoneNum", "8618862173084");
        map.put("phoneIDCode", "32966");
        map.put("invitedCode", "");
        String res = HttpUtil.post(url + "/register/registerConfirm", map);
        System.out.println(res);
    }

    @Test
    public void logLoginByPassWord() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("phoneNum", "8618862173084");
        map.put("passWord", "11111111");
        String res = HttpUtil.post(url + "/login/loginByPassWord", map);
        System.out.println(res);
    }

    @Test
    public void logChangePassword() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("phoneNum", "8618862173084");
        map.put("phoneIDCode", "842662");
        map.put("oldPassWord", "11111111");
        map.put("newPassWord", "11111111");
        String res = HttpUtil.post(url + "/login/changePassword", map);
        System.out.println(res);
    }

    @Test
    public void modifyUserBasic() throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        byte[] head = ByteImageConvert.image2byte("D:/default.jpg");
//        ByteImageConvert.byte2image(head,"D:/a.jpg");
        map.put("headPhoto", head);
        map.put("nickName", "Sher");
        String res = HttpUtil.post(url + "/login/modifyUserBasic", map);
        System.out.println(res);
    }

}
