package com.superwallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.superwallet.common.CodeRepresentation;
import com.superwallet.common.LoginResult;
import com.superwallet.common.SuperResult;
import com.superwallet.mapper.EostokenMapper;
import com.superwallet.mapper.EthtokenMapper;
import com.superwallet.mapper.UserbasicMapper;
import com.superwallet.pojo.Eostoken;
import com.superwallet.pojo.Ethtoken;
import com.superwallet.pojo.Userbasic;
import com.superwallet.pojo.UserbasicExample;
import com.superwallet.service.LoginRegisterService;
import com.superwallet.utils.ByteImageConvert;
import com.superwallet.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class LoginRegisterServiceImpl implements LoginRegisterService {

    @Autowired
    private UserbasicMapper userbasicMapper;

    @Autowired
    private EostokenMapper eostokenMapper;

    @Autowired
    private EthtokenMapper ethtokenMapper;

    /**
     * 查看手机号是否已经被注册过
     *
     * @param phoneNum
     * @return
     */
    @Override
    public boolean isRegistered(String phoneNum) {
        //扫表看是否有这个手机号
        UserbasicExample userbasicExample = new UserbasicExample();
        UserbasicExample.Criteria criteria = userbasicExample.createCriteria();
        criteria.andPhonenumberEqualTo(phoneNum);
        List<Userbasic> list = userbasicMapper.selectByExample(userbasicExample);
        //result为判断是否被注册过，被注册过为true，否则为false
        boolean result = false;
        if (list != null && list.size() != 0)
            result = true;
        return result;
    }

    /**
     * 用户注册
     *
     * @param phoneNum
     * @param passWord
     * @param invitedCode
     */
    @Override
    public String register(String phoneNum, String passWord, String invitedCode, String rootPath) {
        //再次判断一下手机号是否已被注册
        boolean registered = isRegistered(phoneNum);
        if (registered)
            return "registered";
        //如果邀请码不为空并且合法，根据邀请码找到邀请人
        String uid = UUID.randomUUID().toString();
        Userbasic userbasic = new Userbasic();
        //TODO 默认头像设置
        String path = rootPath + "WEB-INF\\imgs\\default.jpg";
        byte[] headPhoto = ByteImageConvert.image2byte(path);
        userbasic.setHeadphoto(headPhoto);
        userbasic.setUid(uid);
        userbasic.setSex(new Byte("0"));
        userbasic.setIsagency(CodeRepresentation.NOTAGENCY);
        userbasic.setNickname(phoneNum);
        userbasic.setStatus(CodeRepresentation.USER_STATUS_NOIDVALIDATION);
        userbasic.setPhonenumber(phoneNum);
        userbasic.setPassword(passWord);
        //TODO 设置用户的邀请码
        //设置是被谁邀请的
        if (invitedCode != null && !invitedCode.equals("") && isValidInvitedCode(invitedCode)) {
            UserbasicExample userbasicExample = new UserbasicExample();
            UserbasicExample.Criteria criteria = userbasicExample.createCriteria();
            criteria.andInvitedcodeEqualTo(invitedCode);
            Userbasic inviter = userbasicMapper.selectByExample(userbasicExample).get(0);
            //用户设置邀请人信息
            userbasic.setInviter(inviter.getUid());
            //邀请人更新邀请的人的列表
            //邀请人的invitedPeople 存String --- uid,uid,uid,
            String invitedpeople = inviter.getInvitedpeople();
            if (invitedpeople == null) invitedpeople = "";
            invitedpeople += uid + ",";
            inviter.setInvitedpeople(invitedpeople);
            userbasicMapper.updateByExample(inviter, new UserbasicExample());
        }
        userbasicMapper.insert(userbasic);
        return uid;
    }

    /**
     * 判断邀请码是否正确
     *
     * @param invitedCode
     * @return
     */
    @Override
    public boolean isValidInvitedCode(String invitedCode) {
        if (invitedCode == null || invitedCode.equals("")) return true;
        UserbasicExample example = new UserbasicExample();
        UserbasicExample.Criteria criteria = example.createCriteria();
        criteria.andInvitedcodeEqualTo(invitedCode);
        List<Userbasic> list = userbasicMapper.selectByExample(example);
        if (list == null || list.size() == 0) return false;
        return true;
    }

    /**
     * 密码登录
     *
     * @param phoneNum
     * @param passWord
     * @return
     */
    @Override
    public LoginResult loginByPassWord(String phoneNum, String passWord) {
        boolean registered = isRegistered(phoneNum);
        //如果没注册，0-1代表无此手机号
        if (!registered) new LoginResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_1, null);
        UserbasicExample userbasicExample = new UserbasicExample();
        UserbasicExample.Criteria criteria = userbasicExample.createCriteria();
        criteria.andPhonenumberEqualTo(phoneNum);
        criteria.andPasswordEqualTo(passWord);
        List<Userbasic> list = userbasicMapper.selectByExample(userbasicExample);
        //如果密码错误，0-0
        if (list == null || list.size() == 0)
            return new LoginResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, null);
        int status = (int) list.get(0).getStatus();
        Userbasic user = list.get(0);
        user.setPaypassword(null);
        user.setPaypassword(null);
        switch (status) {
            case 0:
                //登录成功，但未做身份认证
                return new LoginResult(CodeRepresentation.CODE_SUCCESS, CodeRepresentation.STATUS_0, user);
            case 1:
                //登录成功，但未做人脸识别
                return new LoginResult(CodeRepresentation.CODE_SUCCESS, CodeRepresentation.STATUS_1, user);
            case 2:
                //登录成功，返回UID
                return new LoginResult(CodeRepresentation.CODE_SUCCESS, CodeRepresentation.STATUS_2, user);
        }
        //系统错误
        return new LoginResult(CodeRepresentation.CODE_ERROR, CodeRepresentation.STATUS_0, null);
    }

    /**
     * 验证码登录
     *
     * @param phoneNum
     * @return
     */
    @Override
    public LoginResult loginByCode(String phoneNum) {
        boolean registered = isRegistered(phoneNum);
        if (!registered) {
            //手机号码未注册
            return new LoginResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_1, null);
        }
        UserbasicExample userbasicExample = new UserbasicExample();
        UserbasicExample.Criteria criteria = userbasicExample.createCriteria();
        criteria.andPhonenumberEqualTo(phoneNum);
        List<Userbasic> list = userbasicMapper.selectByExample(userbasicExample);
        Userbasic user = list.get(0);
        user.setPaypassword(null);
        user.setPaypassword(null);
        int status = (int) list.get(0).getStatus();
        switch (status) {
            case 0:
                //登录成功，但未做身份认证
                return new LoginResult(CodeRepresentation.CODE_SUCCESS, CodeRepresentation.STATUS_0, user);
            case 1:
                //登录成功，但未做人脸识别
                return new LoginResult(CodeRepresentation.CODE_SUCCESS, CodeRepresentation.STATUS_1, user);
            case 2:
                //登录成功，返回UID
                return new LoginResult(CodeRepresentation.CODE_SUCCESS, CodeRepresentation.STATUS_2, user);
        }
        //系统错误
        return new LoginResult(CodeRepresentation.CODE_ERROR, CodeRepresentation.STATUS_0, null);
    }

    /**
     * 找回密码，设置新密码
     *
     * @param phoneNum
     * @param newPassWord
     * @return
     */
    @Override
    public LoginResult findPassword(String phoneNum, String newPassWord) {
        //判断有无此手机号
        boolean registered = isRegistered(phoneNum);
        if (!registered) {
            //手机号码未注册
            return new LoginResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, null);
        }
        //找到已注册的用户
        UserbasicExample userbasicExample = new UserbasicExample();
        UserbasicExample.Criteria criteria = userbasicExample.createCriteria();
        criteria.andPhonenumberEqualTo(phoneNum);
        List<Userbasic> list = userbasicMapper.selectByExample(userbasicExample);
        Userbasic user = list.get(0);
        //更新密码
        user.setPassword(newPassWord);
        userbasicMapper.updateByExample(user, userbasicExample);
        user.setPaypassword(null);
        user.setPassword(null);
        //更新成功后，返回user
        return new LoginResult(CodeRepresentation.CODE_SUCCESS, CodeRepresentation.STATUS_0, user);
    }

    /**
     * 设置支付密码
     *
     * @param UID
     * @param payCode
     */
    @Override
    public void setPayCode(String UID, String payCode) {
        //根据UID找到用户
        Userbasic userbasic = userbasicMapper.selectByPrimaryKey(UID);
        //设置支付密码
        userbasic.setPaypassword(payCode);
        userbasicMapper.updateByExample(userbasic, new UserbasicExample());
    }

    /**
     * 更改支付密码模块，判断旧支付密码
     *
     * @param UID
     * @param payCode
     * @return
     */
    @Override
    public boolean payCodeValidation(String UID, String payCode) {
        Userbasic userbasic = userbasicMapper.selectByPrimaryKey(UID);
        //旧支付密码不正确
        if (payCode == null || payCode.equals("") || !payCode.equals(userbasic.getPaypassword())) {
            return false;
        }
        return true;
    }

    /**
     * 判断用户是否超时
     *
     * @param request
     * @return
     */
    @Override
    public boolean isTimeOut(String UID, HttpServletRequest request) {
        //根据session看是否过期
        HttpSession session = request.getSession();
        String attribute = (String) session.getAttribute(UID);
        if (attribute == null || attribute.equals("")) return true;
        return false;
    }

    /**
     * 当新用户注册成功时，初始化钱包信息
     *
     * @param UID
     */
    @Override
    public boolean initWallet(String UID) {
        //初始化钱包信息
        Ethtoken ethtoken = new Ethtoken();
        Ethtoken bgstoken = new Ethtoken();
        Eostoken eostoken = new Eostoken();
        //UID
        ethtoken.setUid(UID);
        bgstoken.setUid(UID);
        eostoken.setUid(UID);
        //TYPE
        ethtoken.setType(CodeRepresentation.ETH_TOKEN_TYPE_ETH);
        bgstoken.setType(CodeRepresentation.ETH_TOKEN_TYPE_BGS);
        eostoken.setType(CodeRepresentation.EOS_TOKEN_TYPE_EOS);
        ethtoken.setAmount(0d);
        ethtoken.setAvailableamount(0d);
        ethtoken.setLockedamount(0d);
        bgstoken.setAmount(0d);
        bgstoken.setAvailableamount(0d);
        bgstoken.setLockedamount(0d);
        eostoken.setAmount(0d);
        eostoken.setAvailableamount(0d);
        eostoken.setLockedamount(0d);
        //TODO 中心钱包地址
        //TODO 初始化私钥
        Map<String, Object> eth_params = new HashMap<String, Object>();
        Map<String, Object> eos_params = new HashMap<String, Object>();
        eth_params.put("UID", UID);
        eos_params.put("UID", UID);
        String eth_resp = HttpUtil.post(CodeRepresentation.NODE_URL_ETH + CodeRepresentation.NODE_ACTION_CREATEETH, eth_params);
        String eos_resp = HttpUtil.post(CodeRepresentation.NODE_URL_ETH + CodeRepresentation.NODE_ACTION_CREATEEOS, eth_params);
        SuperResult response_eth = JSON.parseObject(eth_resp, SuperResult.class);
        SuperResult response_eos = JSON.parseObject(eos_resp, SuperResult.class);
        if (response_eth.getCode() == 0 || response_eos.getCode() == 0) return false;
        ethtokenMapper.insert(ethtoken);
        ethtokenMapper.insert(bgstoken);
        eostokenMapper.insert(eostoken);
        return true;
    }
}
