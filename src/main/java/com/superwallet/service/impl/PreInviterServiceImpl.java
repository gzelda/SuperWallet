package com.superwallet.service.impl;

import com.superwallet.common.CodeRepresentation;
import com.superwallet.common.MessageRepresentation;
import com.superwallet.common.SuperResult;
import com.superwallet.mapper.PreinviterMapper;
import com.superwallet.pojo.Preinviter;
import com.superwallet.pojo.PreinviterExample;
import com.superwallet.service.PreInviterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreInviterServiceImpl implements PreInviterService {

    @Autowired
    private PreinviterMapper preinviterMapper;

    /**
     * 生成预邀请记录
     *
     * @param phoneNum
     * @param invitedCode
     * @return
     */
    @Override
    public SuperResult genPreInviterRecord(String phoneNum, String invitedCode) {
        //判断用户是否已经被预邀请过了
        Preinviter row = preinviterMapper.selectByPrimaryKey(phoneNum);
        //如果已经被预邀请了
        if (row != null) {
            return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_1, "您已领取，请勿重复领取", null);
        }
        Preinviter record = new Preinviter(phoneNum, invitedCode);
        int rows = preinviterMapper.insert(record);
        if (rows == 0) {
            System.out.println("生成预邀请记录失败");
            return new SuperResult(CodeRepresentation.CODE_ERROR, CodeRepresentation.STATUS_0, MessageRepresentation.ERROR_MSG, null);
        }
        return SuperResult.ok("领取成功");
    }
}