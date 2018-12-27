package com.superwallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.superwallet.common.CodeRepresentation;
import com.superwallet.common.LoginResult;
import com.superwallet.common.MessageRepresentation;
import com.superwallet.common.SuperResult;
import com.superwallet.mapper.*;
import com.superwallet.pojo.*;
import com.superwallet.service.CWalletService;
import com.superwallet.service.CommonService;
import com.superwallet.service.LoginRegisterService;
import com.superwallet.utils.ByteImageConvert;
import com.superwallet.utils.CodeGenerator;
import com.superwallet.utils.HttpUtil;
import com.superwallet.utils.PushtoSingle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class LoginRegisterServiceImpl implements LoginRegisterService {

    @Autowired
    private UserbasicMapper userbasicMapper;

    @Autowired
    private EostokenMapper eostokenMapper;

    @Autowired
    private EthtokenMapper ethtokenMapper;

    @Autowired
    private UserprivateMapper userprivateMapper;

    @Autowired
    private InviterMapper inviterMapper;

    @Autowired
    private CWalletService cWalletService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private EthtokenMapper bgstokenMapper;

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
        //默认头像设置
        String path = rootPath + "WEB-INF\\imgs\\default.jpg";
        byte[] headPhoto = ByteImageConvert.image2byte(path);
        userbasic.setHeadphoto(headPhoto);
        userbasic.setUid(uid);
        userbasic.setSex(CodeRepresentation.USER_SEX_MAN);
        userbasic.setIsagency(CodeRepresentation.USER_AGENT_NOTAGENCY);
        userbasic.setNickname(phoneNum);
        userbasic.setStatus(CodeRepresentation.USER_STATUS_NOIDVALIDATION);
        userbasic.setPhonenumber(phoneNum);
        userbasic.setPassword(passWord);
        //设置注册日期
        userbasic.setRegistertime(new Date());
        //设置用户的邀请码
        userbasic.setInvitedcode(CodeGenerator.getInvitedCode(phoneNum));
        //设置是被谁邀请的
        if (invitedCode != null && !invitedCode.equals("") && isValidInvitedCode(invitedCode)) {
            UserbasicExample userbasicExample = new UserbasicExample();
            UserbasicExample.Criteria criteria = userbasicExample.createCriteria();
            criteria.andInvitedcodeEqualTo(invitedCode);
            Userbasic inviter = userbasicMapper.selectByExample(userbasicExample).get(0);
            //用户设置邀请人信息
            userbasic.setInviter(inviter.getUid());
            //邀请人更新邀请的人的列表
            //插入一条邀请人记录
            Inviter record = new Inviter();
            record.setInvitingtime(new Date());
            record.setInviterid(inviter.getUid());
            record.setBeinvitedid(uid);
            inviterMapper.insert(record);
            //邀请方可以获得一笔BGS收入 更新BGS中心钱包表和生成一条交易记录
            //更新钱包表
            cWalletService.updateBGSWalletAmount(inviter.getUid(), CodeRepresentation.INVITING_BGS, CodeRepresentation.CWALLET_MONEY_INC);
            //生成交易记录
            //先找出邀请人的地址
            EthtokenKey bgstokenKey = new EthtokenKey(inviter.getUid(), CodeRepresentation.ETH_TOKEN_TYPE_BGS);
            Ethtoken bgstoken = bgstokenMapper.selectByPrimaryKey(bgstokenKey);
            String toAddress = bgstoken.getEthaddress();
            commonService.generateRecord(inviter.getUid(), CodeRepresentation.TRANSFER_TYPE_INVITINGBGS, (byte) CodeRepresentation.TOKENTYPE_BGS, CodeRepresentation.TRANSFER_SUCCESS, CodeRepresentation.SUPER_BGS, toAddress, CodeRepresentation.INVITING_BGS);
        }
        userbasicMapper.insert(userbasic);
        //注册成功后推送一条消息
        PushtoSingle.pushMessage("test", "test", "http://www.baidu.com");
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
        if (!registered)
            new LoginResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_1, MessageRepresentation.LOGIN_LOGINBYPASSWORD_CODE_0_STATUS_1, null);
        UserbasicExample userbasicExample = new UserbasicExample();
        UserbasicExample.Criteria criteria = userbasicExample.createCriteria();
        criteria.andPhonenumberEqualTo(phoneNum);
        criteria.andPasswordEqualTo(passWord);
        List<Userbasic> list = userbasicMapper.selectByExample(userbasicExample);
        //如果密码错误，0-0
        if (list == null || list.size() == 0)
            return new LoginResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, MessageRepresentation.LOGIN_LOGINBYPASSWORD_CODE_0_STATUS_0, null);
        int status = (int) list.get(0).getStatus();
        Userbasic user = list.get(0);
        user.setPaypassword(null);
        user.setPaypassword(null);
        //系统错误
        return new LoginResult(CodeRepresentation.CODE_ERROR, CodeRepresentation.STATUS_0, MessageRepresentation.ERROR_MSG, null);
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
        //登录成功返回用户基本信息
        if (list != null && list.size() != 0) {
            Userbasic user = list.get(0);
            user.setPaypassword(null);
            user.setPaypassword(null);
            return new LoginResult(CodeRepresentation.CODE_SUCCESS, CodeRepresentation.STATUS_0, MessageRepresentation.LOGIN_LOGINBYCODE_CODE_1_STATUS_0, user);
        }
        //系统错误
        return new LoginResult(CodeRepresentation.CODE_ERROR, CodeRepresentation.STATUS_0, MessageRepresentation.ERROR_MSG, null);
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
            return new LoginResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_1, MessageRepresentation.LOGIN_FINDPASSWORD_CODE_0_STATUS_1, null);
        }
        //找到已注册的用户
        UserbasicExample userbasicExample = new UserbasicExample();
        UserbasicExample.Criteria criteria = userbasicExample.createCriteria();
        criteria.andPhonenumberEqualTo(phoneNum);
        List<Userbasic> list = userbasicMapper.selectByExample(userbasicExample);
        Userbasic user = list.get(0);
        //更新密码
        user.setPassword(newPassWord);
        userbasicMapper.updateByPrimaryKey(user);
        user.setPaypassword(null);
        user.setPassword(null);
        //更新成功后，返回user
        return new LoginResult(CodeRepresentation.CODE_SUCCESS, CodeRepresentation.STATUS_0, MessageRepresentation.LOGIN_FINDPASSWORD_CODE_1_STATUS_0, user);
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
        userbasicMapper.updateByPrimaryKey(userbasic);
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
        ethtoken.setCanlock(CodeRepresentation.CANNOT_LOCK);
        bgstoken.setAmount(0d);
        bgstoken.setCanlock(CodeRepresentation.CAN_LOCK);
        eostoken.setAmount(0d);
        eostoken.setCanlock(CodeRepresentation.CANNOT_LOCK);
        ethtokenMapper.insert(ethtoken);
        ethtokenMapper.insert(bgstoken);
        eostokenMapper.insert(eostoken);
        Map<String, Object> eth_params = new HashMap<String, Object>();
        Map<String, Object> eos_params = new HashMap<String, Object>();
        //传UID
        eth_params.put("UID", UID);
        eos_params.put("UID", UID);
        String eth_resp = HttpUtil.post(CodeRepresentation.NODE_URL_ETH + CodeRepresentation.NODE_ACTION_CREATEETH, eth_params);
        String eos_resp = HttpUtil.post(CodeRepresentation.NODE_URL_EOS + CodeRepresentation.NODE_ACTION_CREATEEOS, eos_params);
        SuperResult response_eth = JSON.parseObject(eth_resp, SuperResult.class);
        SuperResult response_eos = JSON.parseObject(eos_resp, SuperResult.class);
        if (response_eth.getCode() == CodeRepresentation.CODE_FAIL || response_eos.getCode() == CodeRepresentation.CODE_FAIL) {
            return false;
        }
        return true;
    }

    /**
     * 判断密码是否正确
     *
     * @param UID
     * @param oldPassWord
     * @return
     */
    @Override
    public boolean isValidOldPassword(String UID, String oldPassWord) {
        Userbasic userbasic = userbasicMapper.selectByPrimaryKey(UID);
        if (!userbasic.getPassword().equals(oldPassWord)) return false;
        return true;
    }

    /**
     * 修改用户个人信息
     *
     * @param UID
     * @param headPhoto
     * @param nickName
     * @param sex
     */
    @Override
    public void modifyUserBasic(String UID, byte[] headPhoto, String nickName, Byte sex) {
        Userbasic user = userbasicMapper.selectByPrimaryKey(UID);
        user.setHeadphoto(headPhoto);
        user.setNickname(nickName);
        user.setSex(sex);
        userbasicMapper.updateByPrimaryKey(user);
    }

    /**
     * 判断支付密码是否存在
     *
     * @param UID
     * @return
     */
    @Override
    public boolean isPayCodeExists(String UID) {
        Userbasic user = userbasicMapper.selectByPrimaryKey(UID);
        String payCode = user.getPaypassword();
        if (payCode == null || payCode.equals("")) return false;
        return true;
    }

    /**
     * 用户实名认证信息
     *
     * @param UID
     * @param IDCardNumber
     * @param realName
     * @param IDCardFront
     * @param IDCardBack
     * @param face
     */
    @Override
    public void verifyUser(String UID, String IDCardNumber, String realName, String IDCardFront, String IDCardBack, String face) {
        //三张图片（身份证正反面、活体验证）都为url连接，需要下载
        byte[] idCardFrontImg = ByteImageConvert.getFileStream(IDCardFront);
        byte[] idCardBackImg = ByteImageConvert.getFileStream(IDCardBack);
        byte[] faceImg = ByteImageConvert.getFileStream(face);
        UserprivateWithBLOBs userPrivate = new UserprivateWithBLOBs();
        userPrivate.setUid(UID);
        userPrivate.setRealname(realName);
        userPrivate.setIdcardnumber(IDCardNumber);
        userPrivate.setIdcardfront(idCardFrontImg);
        userPrivate.setIdcardback(idCardBackImg);
        userPrivate.setFace(faceImg);
        userprivateMapper.insert(userPrivate);
    }
}
