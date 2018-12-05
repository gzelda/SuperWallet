package com.superwallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.superwallet.common.CodeRepresentation;
import com.superwallet.common.RequestParams;
import com.superwallet.common.SuperResult;
import com.superwallet.common.WalletInfo;
import com.superwallet.mapper.EostokenMapper;
import com.superwallet.mapper.EthtokenMapper;
import com.superwallet.mapper.LockwarehouseMapper;
import com.superwallet.mapper.TransferMapper;
import com.superwallet.pojo.*;
import com.superwallet.service.DWalletService;
import com.superwallet.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class DWalletServiceImpl implements DWalletService {

    @Autowired
    private EthtokenMapper ethtokenMapper;

    @Autowired
    private EostokenMapper eostokenMapper;

    @Autowired
    private TransferMapper transferMapper;

    @Autowired
    private LockwarehouseMapper lockwarehouseMapper;

    /**
     * 链上钱包信息展示
     *
     * @param UID
     * @return
     */
    @Override
    public List<WalletInfo> listWalletInfo(String UID) {

        return null;
    }

    /**
     * 链上钱包转账
     *
     * @param UID
     * @param tokenType
     * @param tokenAmount
     * @param addressTo
     * @param description
     * @return
     */
    @Override
    @Transactional
    public boolean transferMoney(String UID, Integer tokenType, Double tokenAmount, String addressTo, String description) {
        EthtokenKey ethtokenKey = new EthtokenKey();
        EostokenKey eostokenKey = new EostokenKey();
        ethtokenKey.setUid(UID);
        eostokenKey.setUid(UID);
        String addressFrom = "default";
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("UID", UID);
        params.put(RequestParams.TOADDRESS, addressTo);
        String resp;
        SuperResult result;
        switch (tokenType) {
            //转入eth钱包
            case 0:
                ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                Ethtoken ethtoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
                //TODO 链上HTTP
                String fromAddress_eth = ethtoken.getEthaddress();
                addressFrom = fromAddress_eth;
                //TODO 链上HTTP
                params.put(RequestParams.FROMADDRESS, addressFrom);
                params.put(RequestParams.TYPE, CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                resp = HttpUtil.post(CodeRepresentation.NODE_URL_ETH + CodeRepresentation.NODE_ACTION_ETHTRANSFER, params);
                result = JSON.parseObject(resp, SuperResult.class);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
                Double amount_eth = ethtoken.getAmount();
                //余额转入
                amount_eth += tokenAmount;
                ethtoken.setAmount(amount_eth);
                ethtokenMapper.updateByExample(ethtoken, new EthtokenExample());
                break;
            //转入eos钱包
            case 1:
                eostokenKey.setType(CodeRepresentation.EOS_TOKEN_TYPE_EOS);
                Eostoken eostoken = eostokenMapper.selectByPrimaryKey(eostokenKey);
                String fromAddress_eos = eostoken.getEosaccountname();
                addressFrom = fromAddress_eos;
                //链上请求
                params.put(RequestParams.FROMADDRESS, addressFrom);
                params.put(RequestParams.TYPE, CodeRepresentation.EOS_TOKEN_TYPE_EOS);
                resp = HttpUtil.post(CodeRepresentation.NODE_URL_EOS + CodeRepresentation.NODE_ACTION_EOSTRANSFER, params);
                result = JSON.parseObject(resp, SuperResult.class);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
                Double amount_eos = eostoken.getAmount();
                amount_eos += tokenAmount;
                eostoken.setAmount(amount_eos);
                eostokenMapper.updateByExample(eostoken, new EostokenExample());
                break;
            //转入bgs钱包
            case 2:
                ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_BGS);
                Ethtoken bgstoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
                String fromAddress_bgs = bgstoken.getEthaddress();
                addressFrom = fromAddress_bgs;
                //链上请求
                params.put(RequestParams.FROMADDRESS, addressFrom);
                params.put(RequestParams.TYPE, CodeRepresentation.ETH_TOKEN_TYPE_BGS);
                resp = HttpUtil.post(CodeRepresentation.NODE_URL_ETH + CodeRepresentation.NODE_ACTION_ETHTRANSFER, params);
                result = JSON.parseObject(resp, SuperResult.class);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
                Double amount_bgs = bgstoken.getAmount();
                amount_bgs += tokenAmount;
                bgstoken.setAmount(amount_bgs);
                ethtokenMapper.updateByExample(bgstoken, new EthtokenExample());
                break;
        }
        //转账记录
        Transfer transfer = new Transfer();
        transfer.setUid(UID);
        transfer.setTransfertype(CodeRepresentation.CHAIN_ON2ON);
        transfer.setTokentype(new Byte(tokenType + ""));
        transfer.setStatus(CodeRepresentation.TRANSFER_SUCCESS);
        transfer.setSource(addressFrom);
        transfer.setDestination(addressTo);
        transfer.setCreatedtime(new Date());
        transferMapper.insert(transfer);
        return true;
    }

    /**
     * 链上钱包锁仓
     *
     * @param UID
     * @param tokenType
     * @param tokenAmount
     * @param period
     * @return
     */
    @Override
    @Transactional
    public boolean lock(String UID, Integer tokenType, Double tokenAmount, Integer period) {
        EthtokenKey ethtokenKey = new EthtokenKey();
        EostokenKey eostokenKey = new EostokenKey();
        ethtokenKey.setUid(UID);
        eostokenKey.setUid(UID);
        String addressFrom = "default";
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("UID", UID);
        String resp;
        SuperResult result;
        switch (tokenType) {
            //转入eth钱包
            case 0:
                ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                Ethtoken ethtoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
                //TODO 链上HTTP
                String fromAddress_eth = ethtoken.getEthaddress();
                addressFrom = fromAddress_eth;
                params.put(RequestParams.FROMADDRESS, addressFrom);
                params.put(RequestParams.TOADDRESS, CodeRepresentation.SUPER_ETH);
                params.put(RequestParams.TYPE, CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                resp = HttpUtil.post(CodeRepresentation.NODE_URL_ETH + CodeRepresentation.NODE_ACTION_ETHTRANSFER, params);
                result = JSON.parseObject(resp, SuperResult.class);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
                break;
            //转入eos钱包
            case 1:
                eostokenKey.setType(CodeRepresentation.EOS_TOKEN_TYPE_EOS);
                Eostoken eostoken = eostokenMapper.selectByPrimaryKey(eostokenKey);
                String fromAddress_eos = eostoken.getEosaccountname();
                addressFrom = fromAddress_eos;
                //链上请求
                params.put(RequestParams.FROMADDRESS, addressFrom);
                params.put(RequestParams.TOADDRESS, CodeRepresentation.SUPER_EOS);
                params.put(RequestParams.TYPE, CodeRepresentation.EOS_TOKEN_TYPE_EOS);
                resp = HttpUtil.post(CodeRepresentation.NODE_URL_EOS + CodeRepresentation.NODE_ACTION_EOSTRANSFER, params);
                result = JSON.parseObject(resp, SuperResult.class);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
                break;
            //转入bgs钱包
            case 2:
                ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_BGS);
                Ethtoken bgstoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
                String fromAddress_bgs = bgstoken.getEthaddress();
                addressFrom = fromAddress_bgs;
                //链上请求
                params.put(RequestParams.FROMADDRESS, addressFrom);
                params.put(RequestParams.TOADDRESS, CodeRepresentation.SUPER_ETH);
                params.put(RequestParams.TYPE, CodeRepresentation.ETH_TOKEN_TYPE_BGS);
                resp = HttpUtil.post(CodeRepresentation.NODE_URL_ETH + CodeRepresentation.NODE_ACTION_ETHTRANSFER, params);
                result = JSON.parseObject(resp, SuperResult.class);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
                break;
        }
        //锁仓记录
        Lockwarehouse lockwarehouse = new Lockwarehouse();
        lockwarehouse.setUid(UID);
        lockwarehouse.setCreatedtime(new Date());
        lockwarehouse.setPeriod(period);
        lockwarehouse.setAmount(tokenAmount);
        lockwarehouse.setStatus(CodeRepresentation.LOCK_ON);
        lockwarehouseMapper.insert(lockwarehouse);
        //转账记录
        Transfer transfer = new Transfer();
        transfer.setUid(UID);
        transfer.setTransfertype(CodeRepresentation.CHAIN_ON2ON);
        transfer.setTokentype(new Byte(tokenType + ""));
        transfer.setStatus(CodeRepresentation.TRANSFER_SUCCESS);
        transfer.setSource(addressFrom);
        transfer.setDestination("");
        transfer.setCreatedtime(new Date());
        transferMapper.insert(transfer);
        return true;
    }

    /**
     * 锁仓订单展示
     *
     * @param UID
     * @param timeStampLeft
     * @param timeStampRight
     * @return
     */
    @Override
    public List<Lockwarehouse> listOrders(String UID, String timeStampLeft, String timeStampRight) {
        LockwarehouseExample lockwarehouseExample = new LockwarehouseExample();
        LockwarehouseExample.Criteria criteria = lockwarehouseExample.createCriteria();
        criteria.andUidEqualTo(UID);
        List<Lockwarehouse> list = lockwarehouseMapper.selectByExample(lockwarehouseExample);
        return list;
    }
}
