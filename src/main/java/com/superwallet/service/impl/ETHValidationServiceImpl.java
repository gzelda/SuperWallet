package com.superwallet.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.superwallet.common.CodeRepresentation;
import com.superwallet.common.SuperResult;
import com.superwallet.mapper.EthvalidationMapper;
import com.superwallet.mapper.TransferMapper;
import com.superwallet.pojo.Ethvalidation;
import com.superwallet.pojo.EthvalidationExample;
import com.superwallet.pojo.Transfer;
import com.superwallet.pojo.TransferKey;
import com.superwallet.service.CommonService;
import com.superwallet.service.ETHValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ETHValidationServiceImpl implements ETHValidationService {
    @Autowired
    private EthvalidationMapper ethvalidationMapper;

    @Autowired
    private TransferMapper transferMapper;

    @Autowired
    private CommonService commonService;

    /**
     * ETH转账延迟验证
     */
    @Override
    public void ETHValidation() {
        //拿到待确认的订单
        EthvalidationExample ethvalidationExample = new EthvalidationExample();
        EthvalidationExample.Criteria criteria = ethvalidationExample.createCriteria();
        criteria.andStatusEqualTo(CodeRepresentation.ETH_VALIDATION_ON);
        List<Ethvalidation> list = ethvalidationMapper.selectByExample(ethvalidationExample);
        //查询每个待确认订单
        String txHash;
        SuperResult result;
        List<String> txHashs = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            Ethvalidation row = list.get(i);
            txHash = row.getHashvalue();
            if (txHash != null && !txHash.equals("")) {
//                System.out.println(txHash);
                txHashs.add(txHash);
            }
        }
        if (txHashs.size() == 1) {
            txHashs.add("");
        }
        //链上查询订单状态
        result = commonService.queryPending(txHashs);
        //状态为已确认
        if (result.getCode() == CodeRepresentation.CODE_SUCCESS) {
//            System.out.println(result.getData());
            List<Integer> allStatus;
            try {
                allStatus = (List) JSONObject.parseObject(result.getData().toString()).get("status");
//                System.out.println(allStatus.size());
            } catch (Exception e) {
                return;
            }
            List<Ethvalidation> ethvalidations = new ArrayList<Ethvalidation>();
            List<Transfer> transfers = new ArrayList<Transfer>();
            for (int i = 0; i < list.size(); i++) {
                //不用改表
                if (allStatus.get(i) == 0) {
                    continue;
                }
                Ethvalidation row = list.get(i);
                Transfer transfer = transferMapper.selectByPrimaryKey(new TransferKey(row.getUid(), row.getTransferid()));
                if (transfer != null) {
                    //更新交易记录状态
                    transfer.setStatus(CodeRepresentation.TRANSFER_SUCCESS);
                    transfers.add(transfer);
                    //更新订单状态
                    row.setStatus(CodeRepresentation.ETH_VALIDATION_OVER);
                    ethvalidations.add(row);
                }
            }
            if (transfers.size() != 0)
                transferMapper.updateBatch(transfers);
            if (ethvalidations.size() != 0)
                ethvalidationMapper.updateBatch(ethvalidations);
        }
    }
}
