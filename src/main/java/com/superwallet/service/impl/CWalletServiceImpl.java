package com.superwallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.superwallet.common.CWalletInfo;
import com.superwallet.common.CodeRepresentation;
import com.superwallet.common.RequestParams;
import com.superwallet.common.SuperResult;
import com.superwallet.mapper.*;
import com.superwallet.pojo.*;
import com.superwallet.service.CWalletService;
import com.superwallet.service.CommonService;
import com.superwallet.utils.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 中心化钱包Service
 */
@Service
public class CWalletServiceImpl implements CWalletService {

    @Autowired
    private EthtokenMapper ethtokenMapper;

    @Autowired
    private EostokenMapper eostokenMapper;

    @Autowired
    private TransferMapper transferMapper;

    @Autowired
    private UserbasicMapper userbasicMapper;

    @Autowired
    private CommonService commonService;

    @Autowired
    private WithdrawmoneyMapper withdrawmoneyMapper;

    /**
     * 获取用户的所有中心化钱包信息
     *
     * @param UID
     * @return
     */
    @Override
    public List<CWalletInfo> listCWalletInfo(String UID) {
        List<CWalletInfo> list = new ArrayList<CWalletInfo>();
        //TODO 数字货币价格--目前爬虫实现
        String origin = HttpUtil.get(CodeRepresentation.URL_PRICE);
        Document document = Jsoup.parse(origin);
        String eth_price = document.getElementById("id-ethereum").getElementsByClass("price").text();
        String eos_price = document.getElementById("id-eos").getElementsByClass("price").text();
        //TODO 缺少BGS价格
        //以太钱包
        EthtokenKey ethtokenKey = new EthtokenKey();
        ethtokenKey.setUid(UID);
        ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_ETH);
        Ethtoken ethtoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
        double HUOBIprice = 1.0;
        double price_eth = Double.parseDouble(eth_price.substring(1));
        double price_eos = Double.parseDouble(eos_price.substring(1));
        CWalletInfo ethInfo = new CWalletInfo(ethtoken.getEthaddress() == null ? "" : ethtoken.getEthaddress(), ethtoken.getAmount(),
                price_eth, ethtoken.getLockedamount(), ethtoken.getAvailableamount());
        ethtoken.setType(CodeRepresentation.ETH_TOKEN_TYPE_BGS);
        Ethtoken bgstoken = ethtokenMapper.selectByPrimaryKey(ethtoken);
        CWalletInfo bgsInfo = new CWalletInfo(ethtoken.getEthaddress() == null ? "" : ethtoken.getEthaddress(), bgstoken.getAmount(),
                HUOBIprice, bgstoken.getLockedamount(), bgstoken.getAvailableamount());
        EostokenKey eostokenKey = new EostokenKey();
        eostokenKey.setUid(UID);
        eostokenKey.setType(CodeRepresentation.EOS_TOKEN_TYPE_EOS);
        Eostoken eostoken = eostokenMapper.selectByPrimaryKey(eostokenKey);
        CWalletInfo eosInfo = new CWalletInfo(eostoken.getEosaccountname() == null ? "" : eostoken.getEosaccountname(), eostoken.getAmount(),
                price_eos, eostoken.getLockedamount(), eostoken.getAvailableamount());
        list.add(ethInfo);
        list.add(bgsInfo);
        list.add(eosInfo);
        return list;
    }

    /**
     * 中心钱包转入
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean transferMoney(String UID, Integer tokenType, Double tokenAmount) {
        //主键类
        EthtokenKey ethtokenKey = new EthtokenKey();
        EostokenKey eostokenKey = new EostokenKey();
        ethtokenKey.setUid(UID);
        eostokenKey.setUid(UID);
        String addressFrom = CodeRepresentation.DEFAULT_ADDRESS, addressTo;
        //请求nodejs的参数
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(RequestParams.UID, UID);
        params.put(RequestParams.AMOUNT, tokenAmount);
        //nodejs返回结果--JSON String
        String resp;
        SuperResult result;
        //转账类型
        Byte transferType = CodeRepresentation.CHAIN_ON2OFF;
        //转账币种
        Byte token = new Byte(tokenType + "");
        switch (tokenType) {
            //转入eth钱包
            case 0:
                //找到中心化钱包
                ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                Ethtoken ethtoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
                String fromAddress_eth = ethtoken.getEthaddress();
                addressFrom = CodeRepresentation.SUPER_ETH;
                addressTo = fromAddress_eth;
                //链上HTTP请求
                params.put(RequestParams.FROMADDRESS, addressFrom);
                params.put(RequestParams.TOADDRESS, addressTo);
                params.put(RequestParams.TYPE, CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                resp = HttpUtil.post(CodeRepresentation.NODE_URL_ETH + CodeRepresentation.NODE_ACTION_ETHTRANSFER, params);
                result = JSON.parseObject(resp, SuperResult.class);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
                Double amount_eth = ethtoken.getAmount();
                Double avaAmount_eth = ethtoken.getAvailableamount();
                //TODO 事务管理
                //余额转入,钱包更新
                amount_eth += tokenAmount;
                avaAmount_eth += tokenAmount;
                ethtoken.setAmount(amount_eth);
                ethtoken.setAvailableamount(avaAmount_eth);
                ethtokenMapper.updateByPrimaryKey(ethtoken);
                //生成转账记录
                commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_SUCCESS, addressFrom, addressTo, tokenAmount);
                break;
            //转入eos钱包
            case 1:
                //找到中心化eos钱包
                eostokenKey.setType(CodeRepresentation.EOS_TOKEN_TYPE_EOS);
                Eostoken eostoken = eostokenMapper.selectByPrimaryKey(eostokenKey);
                String fromAddress_eos = eostoken.getEosaccountname();
                addressFrom = CodeRepresentation.SUPER_EOS;
                addressTo = fromAddress_eos;
                //链上请求
                params.put(RequestParams.FROMADDRESS, addressFrom);
                params.put(RequestParams.TOADDRESS, addressTo);
                params.put(RequestParams.TYPE, CodeRepresentation.EOS_TOKEN_TYPE_EOS);
                resp = HttpUtil.post(CodeRepresentation.NODE_URL_EOS + CodeRepresentation.NODE_ACTION_EOSTRANSFER, params);
                result = JSON.parseObject(resp, SuperResult.class);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
                Double amount_eos = eostoken.getAmount();
                Double avaAmount_eos = eostoken.getAvailableamount();
                //余额转入,钱包更新
                amount_eos += tokenAmount;
                avaAmount_eos += tokenAmount;
                eostoken.setAmount(amount_eos);
                eostoken.setAvailableamount(avaAmount_eos);
                eostokenMapper.updateByPrimaryKey(eostoken);
                //生成转账记录
                commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_SUCCESS, addressFrom, addressTo, tokenAmount);
                break;
            //转入bgs钱包
            case 2:
                //找到中心化BGS钱包
                ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_BGS);
                Ethtoken bgstoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
                String fromAddress_bgs = bgstoken.getEthaddress();
                addressFrom = CodeRepresentation.SUPER_BGS;
                addressTo = fromAddress_bgs;
                //链上请求
                params.put(RequestParams.FROMADDRESS, addressFrom);
                params.put(RequestParams.TOADDRESS, addressTo);
                params.put(RequestParams.TYPE, CodeRepresentation.ETH_TOKEN_TYPE_BGS);
                resp = HttpUtil.post(CodeRepresentation.NODE_URL_ETH + CodeRepresentation.NODE_ACTION_ETHTRANSFER, params);
                result = JSON.parseObject(resp, SuperResult.class);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
                Double amount_bgs = bgstoken.getAmount();
                Double avaAmount_bgs = bgstoken.getAvailableamount();
                //余额转入,钱包更新
                amount_bgs += tokenAmount;
                avaAmount_bgs += tokenAmount;
                bgstoken.setAmount(amount_bgs);
                bgstoken.setAvailableamount(avaAmount_bgs);
                ethtokenMapper.updateByPrimaryKey(bgstoken);
                //生成转账记录
                commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_SUCCESS, addressFrom, addressTo, tokenAmount);
                break;
        }
        return true;
    }

    /**
     * 提现申请
     *
     * @param UID
     * @param tokenType
     * @param tokenAmount
     * @return
     */
    @Override
    public boolean withdrawRequest(String UID, Integer tokenType, Double tokenAmount) {
        //拿到中心钱包信息
        EthtokenKey ethtokenKey = new EthtokenKey();
        EostokenKey eostokenKey = new EostokenKey();
        ethtokenKey.setUid(UID);
        eostokenKey.setUid(UID);
        Double avaAmount, amount;
        switch (tokenType) {
            //ETH
            case 0:
                ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                Ethtoken ethtoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
                //获得可用余额
                avaAmount = ethtoken.getAvailableamount();
                amount = ethtoken.getAmount();
                //余额不足
                if (avaAmount < tokenAmount) return false;
                //余额更新
                avaAmount -= tokenAmount;
                amount -= tokenAmount;
                ethtoken.setAvailableamount(avaAmount);
                ethtoken.setAmount(amount);
                ethtokenMapper.updateByPrimaryKey(ethtoken);
                commonService.withdrawRecord(UID, UUID.randomUUID().toString(), new Byte(tokenType + ""), CodeRepresentation.WITHDRAW_WAIT, tokenAmount);
                break;
            //EOS
            case 1:
                eostokenKey.setType(CodeRepresentation.EOS_TOKEN_TYPE_EOS);
                Eostoken eostoken = eostokenMapper.selectByPrimaryKey(eostokenKey);
                //获得可用余额
                avaAmount = eostoken.getAvailableamount();
                amount = eostoken.getAmount();
                //余额不足
                if (avaAmount < tokenAmount) return false;
                //余额更新
                avaAmount -= tokenAmount;
                amount -= tokenAmount;
                eostoken.setAvailableamount(avaAmount);
                eostoken.setAmount(amount);
                eostokenMapper.updateByPrimaryKey(eostoken);
                commonService.withdrawRecord(UID, UUID.randomUUID().toString(), new Byte(tokenType + ""), CodeRepresentation.WITHDRAW_WAIT, tokenAmount);
                break;
            //BGS
            case 2:
                ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_BGS);
                Ethtoken bgstoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
                //获得可用余额
                avaAmount = bgstoken.getAvailableamount();
                amount = bgstoken.getAmount();
                //余额不足
                if (avaAmount < tokenAmount) return false;
                //余额更新
                avaAmount -= tokenAmount;
                amount -= tokenAmount;
                bgstoken.setAvailableamount(avaAmount);
                bgstoken.setAmount(amount);
                ethtokenMapper.updateByPrimaryKey(bgstoken);
                commonService.withdrawRecord(UID, UUID.randomUUID().toString(), new Byte(tokenType + ""), CodeRepresentation.WITHDRAW_WAIT, tokenAmount);
                break;
        }
        return true;
    }

    /**
     * 中心钱包提币到链上钱包--转账操作
     *
     * @param UID
     * @param tokenType
     * @param tokenAmount
     * @return
     */
    @Override
    @Transactional
    public boolean withdraw(String UID, String WID, Integer tokenType, Double tokenAmount) {
        EthtokenKey ethtokenKey = new EthtokenKey();
        EostokenKey eostokenKey = new EostokenKey();
        ethtokenKey.setUid(UID);
        eostokenKey.setUid(UID);
        String addressFrom, addressTo;
        //请求nodejs的参数
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(RequestParams.UID, UID);
        params.put(RequestParams.AMOUNT, tokenAmount);
        //nodejs返回结果--JSON String
        String resp;
        SuperResult result;
        //转账类型
        Byte transferType = CodeRepresentation.CHAIN_OFF2ON;
        //转账币种
        Byte token = new Byte(tokenType + "");
        Double avaAmount, amount;
        //拿到申请表里的转账记录
        WithdrawmoneyKey key = new WithdrawmoneyKey(UID, WID);
        Withdrawmoney record = withdrawmoneyMapper.selectByPrimaryKey(key);
        switch (tokenType) {
            //ETH
            case 0:
                ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                Ethtoken ethtoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
                //从中心转到链上
                addressFrom = CodeRepresentation.SUPER_ETH;
                addressTo = ethtoken.getEthaddress();
                params.put(RequestParams.FROMADDRESS, addressFrom);
                params.put(RequestParams.TOADDRESS, addressTo);
                params.put(RequestParams.TYPE, CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                resp = HttpUtil.post(CodeRepresentation.NODE_URL_ETH + CodeRepresentation.NODE_ACTION_ETHTRANSFER, params);
                result = JSON.parseObject(resp, SuperResult.class);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
                //申请表记录更新--涉及到钱是否返还问题
                record.setStatus(CodeRepresentation.WITHDRAW_SUCCESS);
                withdrawmoneyMapper.updateByPrimaryKey(record);
                commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_SUCCESS, addressFrom, addressTo, tokenAmount);
                break;
            //EOS
            case 1:
                eostokenKey.setType(CodeRepresentation.EOS_TOKEN_TYPE_EOS);
                Eostoken eostoken = eostokenMapper.selectByPrimaryKey(eostokenKey);
                //从中心转到链上
                addressFrom = CodeRepresentation.SUPER_EOS;
                addressTo = eostoken.getEosaccountname();
                params.put(RequestParams.FROMADDRESS, addressFrom);
                params.put(RequestParams.TOADDRESS, addressTo);
                params.put(RequestParams.TYPE, CodeRepresentation.EOS_TOKEN_TYPE_EOS);
                resp = HttpUtil.post(CodeRepresentation.NODE_URL_EOS + CodeRepresentation.NODE_ACTION_EOSTRANSFER, params);
                result = JSON.parseObject(resp, SuperResult.class);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
                //申请表记录更新--涉及到钱是否返还问题
                record.setStatus(CodeRepresentation.WITHDRAW_SUCCESS);
                withdrawmoneyMapper.updateByPrimaryKey(record);
                commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_SUCCESS, addressFrom, addressTo, tokenAmount);
                break;
            //BGS
            case 2:
                ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_BGS);
                Ethtoken bgstoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
                //从中心转到链上
                addressFrom = CodeRepresentation.SUPER_BGS;
                addressTo = bgstoken.getEthaddress();
                params.put(RequestParams.FROMADDRESS, addressFrom);
                params.put(RequestParams.TOADDRESS, addressTo);
                params.put(RequestParams.TYPE, CodeRepresentation.ETH_TOKEN_TYPE_BGS);
                resp = HttpUtil.post(CodeRepresentation.NODE_URL_ETH + CodeRepresentation.NODE_ACTION_ETHTRANSFER, params);
                result = JSON.parseObject(resp, SuperResult.class);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
                //申请表记录更新--涉及到钱是否返还问题
                record.setStatus(CodeRepresentation.WITHDRAW_SUCCESS);
                withdrawmoneyMapper.updateByPrimaryKey(record);
                commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_SUCCESS, addressFrom, addressTo, tokenAmount);
                break;
        }
        return true;
    }


    /**
     * 查看历史转账信息--只要是在平台操作的记录都有
     *
     * @param UID
     * @param tokenType
     * @return
     */
    @Override
    public List<Transfer> listHistoryBills(String UID, Integer tokenType) {
        //查找特定币转账记录
        TransferExample transferExample = new TransferExample();
        TransferExample.Criteria criteria = transferExample.createCriteria();
        criteria.andUidEqualTo(UID);
        criteria.andTokentypeEqualTo(tokenType.byteValue());
        List<Transfer> result = transferMapper.selectByExample(transferExample);
        return result;
    }

    /**
     * 购买代理人
     *
     * @param UID
     * @return
     */
    @Override
    @Transactional
    public boolean buyAgent(String UID) {
        Userbasic user = userbasicMapper.selectByPrimaryKey(UID);
        EthtokenKey ethtokenKey = new EthtokenKey();
        ethtokenKey.setUid(UID);
        ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_BGS);
        Ethtoken bgstoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
        //余额不足
        if (bgstoken.getAvailableamount() < CodeRepresentation.AGENT_PRICE) return false;
        //购买成功
        bgstoken.setAmount(bgstoken.getAmount() - CodeRepresentation.AGENT_PRICE);
        bgstoken.setAvailableamount(bgstoken.getAvailableamount() - CodeRepresentation.AGENT_PRICE);
        //设置用户为代理人
        user.setIsagency(CodeRepresentation.ISAGENCY);
        userbasicMapper.updateByPrimaryKey(user);
        ethtokenMapper.updateByPrimaryKey(bgstoken);
        //转账记录
        Transfer transfer = new Transfer();
        transfer.setUid(UID);
        transfer.setTransfertype(CodeRepresentation.CHAIN_OFF2ON);
        transfer.setTokentype(new Byte(CodeRepresentation.ETH_TOKEN_TYPE_BGS + ""));
        transfer.setStatus(CodeRepresentation.TRANSFER_SUCCESS);
        transfer.setSource(bgstoken.getEthaddress());
        //TODO super账户地址
        transfer.setDestination(CodeRepresentation.SUPER_BGS);
        transfer.setCreatedtime(new Date());
        transfer.setAmount(CodeRepresentation.AGENT_PRICE);
        transferMapper.insert(transfer);
        return true;
    }
}
