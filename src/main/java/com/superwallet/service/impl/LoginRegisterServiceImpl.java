package com.superwallet.service.impl;

import com.superwallet.common.CodeRepresentation;
import com.superwallet.common.LoginResult;
import com.superwallet.mapper.UserbasicMapper;
import com.superwallet.pojo.Userbasic;
import com.superwallet.pojo.UserbasicExample;
import com.superwallet.service.LoginRegisterService;
import com.superwallet.utils.ByteImageConvert;
import com.superwallet.utils.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginRegisterServiceImpl implements LoginRegisterService {

    @Autowired
    private UserbasicMapper userbasicMapper;

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
        //TODO 根据邀请码找到邀请人--缺字段
        String uid = CodeGenerator.getUID(phoneNum);
        Userbasic userbasic = new Userbasic();
        //TODO 默认头像设置
        String path = rootPath + "WEB-INF\\imgs\\default.jpg";
        byte[] headPhoto = ByteImageConvert.image2byte(path);
        userbasic.setHeadphoto(headPhoto);
        userbasic.setUid(uid);
        userbasic.setSex(new Byte("0"));
        userbasic.setIsagency(new Byte("0"));
        userbasic.setNickname(phoneNum);
        userbasic.setStatus(new Byte("0"));
        userbasic.setPhonenumber(phoneNum);
        userbasic.setPassword(passWord);
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
        //TODO 用来判断邀请码是否正确
        UserbasicExample example = new UserbasicExample();
        UserbasicExample.Criteria criteria = example.createCriteria();
        criteria.andInvitedcodeEqualTo(invitedCode);
        List<Userbasic> list = userbasicMapper.selectByExample(example);
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
        String uid = list.get(0).getUid();
        switch (status) {
            case 0:
                //登录成功，但未做身份认证
                return new LoginResult(CodeRepresentation.CODE_SUCCESS, CodeRepresentation.STATUS_0, uid);
            case 1:
                //登录成功，但未做人脸识别
                return new LoginResult(CodeRepresentation.CODE_SUCCESS, CodeRepresentation.STATUS_1, uid);
            case 2:
                //登录成功，返回UID
                return new LoginResult(CodeRepresentation.CODE_SUCCESS, CodeRepresentation.STATUS_2, uid);
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
        String uid = list.get(0).getUid();
        int status = (int) list.get(0).getStatus();
        switch (status) {
            case 0:
                //登录成功，但未做身份认证
                return new LoginResult(CodeRepresentation.CODE_SUCCESS, CodeRepresentation.STATUS_0, uid);
            case 1:
                //登录成功，但未做人脸识别
                return new LoginResult(CodeRepresentation.CODE_SUCCESS, CodeRepresentation.STATUS_1, uid);
            case 2:
                //登录成功，返回UID
                return new LoginResult(CodeRepresentation.CODE_SUCCESS, CodeRepresentation.STATUS_2, uid);
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
        //更新成功后，返回uid
        return new LoginResult(CodeRepresentation.CODE_SUCCESS, CodeRepresentation.STATUS_0, user.getUid());
    }
}
