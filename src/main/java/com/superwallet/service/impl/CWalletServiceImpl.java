package com.superwallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.superwallet.common.CodeRepresentation;
import com.superwallet.common.RequestParams;
import com.superwallet.common.SuperResult;
import com.superwallet.common.WalletInfo;
import com.superwallet.mapper.EostokenMapper;
import com.superwallet.mapper.EthtokenMapper;
import com.superwallet.mapper.TransferMapper;
import com.superwallet.mapper.UserbasicMapper;
import com.superwallet.pojo.*;
import com.superwallet.service.CWalletService;
import com.superwallet.utils.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    /**
     * 获取用户的所有中心化钱包信息
     *
     * @param UID
     * @return
     */
    @Override
    public List<WalletInfo> listCWalletInfo(String UID) {
        List<WalletInfo> list = new ArrayList<WalletInfo>();
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
        WalletInfo ethInfo = new WalletInfo(ethtoken.getEthaddress() == null ? "" : ethtoken.getEthaddress(), ethtoken.getAmount(),
                price_eth, ethtoken.getLockedamount(), ethtoken.getAvailableamount());
        ethtoken.setType(CodeRepresentation.ETH_TOKEN_TYPE_BGS);
        Ethtoken bgstoken = ethtokenMapper.selectByPrimaryKey(ethtoken);
        WalletInfo bgsInfo = new WalletInfo(ethtoken.getEthaddress() == null ? "" : ethtoken.getEthaddress(), bgstoken.getAmount(),
                HUOBIprice, bgstoken.getLockedamount(), bgstoken.getAvailableamount());
        EostokenKey eostokenKey = new EostokenKey();
        eostokenKey.setUid(UID);
        eostokenKey.setType(CodeRepresentation.EOS_TOKEN_TYPE_EOS);
        Eostoken eostoken = eostokenMapper.selectByPrimaryKey(eostokenKey);
        WalletInfo eosInfo = new WalletInfo(eostoken.getEosaccountname() == null ? "" : eostoken.getEosaccountname(), eostoken.getAmount(),
                price_eos, eostoken.getLockedamount(), eostoken.getAvailableamount());
        list.add(ethInfo);
        list.add(bgsInfo);
        list.add(eosInfo);
        return list;
    }

    /**
     * 链上钱包转出与中心钱包转入
     *
     * @return
     */
    @Override
    @Transactional
    public boolean transferMoney(String UID, Integer tokenType, Double tokenAmount) {
        EthtokenKey ethtokenKey = new EthtokenKey();
        EostokenKey eostokenKey = new EostokenKey();
        ethtokenKey.setUid(UID);
        eostokenKey.setUid(UID);
        String addressFrom = CodeRepresentation.DEFAULT_ADDRESS;
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(RequestParams.UID, UID);
        params.put(RequestParams.AMOUNT, tokenAmount);
        String resp;
        SuperResult result;
        switch (tokenType) {
            //转入eth钱包
            case 0:
                //拿到ETH超级账户
                EthtokenKey superWallet_eth = new EthtokenKey();
                superWallet_eth.setUid(UID);
                superWallet_eth.setType(CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                Ethtoken ethtoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
                String fromAddress_eth = ethtoken.getEthaddress();
                addressFrom = fromAddress_eth;
                //TODO 链上HTTP
                params.put(RequestParams.FROMADDRESS, addressFrom);
                params.put(RequestParams.TOADDRESS, CodeRepresentation.SUPER_ETH);
                params.put(RequestParams.TYPE, CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                resp = HttpUtil.post(CodeRepresentation.NODE_URL_ETH + CodeRepresentation.NODE_ACTION_ETHTRANSFER, params);
                result = JSON.parseObject(resp, SuperResult.class);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
                Double amount_eth = ethtoken.getAmount();
                Double avaAmount_eth = ethtoken.getAvailableamount();
                //余额转入
                amount_eth += tokenAmount;
                avaAmount_eth += tokenAmount;
                ethtoken.setAmount(amount_eth);
                ethtoken.setAvailableamount(avaAmount_eth);
                ethtokenMapper.updateByPrimaryKey(ethtoken);
                break;
            //转入eos钱包
            case 1:
                //拿到EOS超级账户
                EostokenKey superWallet_eos = new EostokenKey();
                superWallet_eos.setUid(UID);
                superWallet_eos.setType(CodeRepresentation.EOS_TOKEN_TYPE_EOS);
                Eostoken super_eos = eostokenMapper.selectByPrimaryKey(superWallet_eos);
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
                Double amount_eos = eostoken.getAmount();
                Double avaAmount_eos = eostoken.getAvailableamount();
                amount_eos += tokenAmount;
                avaAmount_eos += tokenAmount;
                eostoken.setAmount(amount_eos);
                eostoken.setAvailableamount(avaAmount_eos);
                eostokenMapper.updateByPrimaryKey(eostoken);
                break;
            //转入bgs钱包
            case 2:
                //拿到BGS超级账户
                EthtokenKey superWallet_bgs = new EthtokenKey();
                superWallet_bgs.setUid(UID);
                superWallet_bgs.setType(CodeRepresentation.ETH_TOKEN_TYPE_BGS);
                Ethtoken super_bgs = ethtokenMapper.selectByPrimaryKey(superWallet_bgs);
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
                Double amount_bgs = bgstoken.getAmount();
                Double avaAmount_bgs = bgstoken.getAvailableamount();
                amount_bgs += tokenAmount;
                avaAmount_bgs += tokenAmount;
                bgstoken.setAmount(amount_bgs);
                bgstoken.setAvailableamount(avaAmount_bgs);
                ethtokenMapper.updateByPrimaryKey(bgstoken);
                break;
        }
        //转账记录
        Transfer transfer = new Transfer();
        transfer.setUid(UID);
        transfer.setTransfertype(CodeRepresentation.CHAIN_OFF2ON);
        transfer.setTokentype(new Byte(tokenType + ""));
        transfer.setStatus(CodeRepresentation.TRANSFER_SUCCESS);
        transfer.setSource(addressFrom);
        transfer.setDestination(addressFrom);
        transfer.setCreatedtime(new Date());
        transfer.setAmount(tokenAmount);
        transferMapper.insert(transfer);
        return true;
    }

    /**
     * 中心钱包提币到链上钱包
     *
     * @param UID
     * @param tokenType
     * @param tokenAmount
     * @return
     */
    @Override
    @Transactional
    public boolean withdraw(String UID, Integer tokenType, Double tokenAmount) {
        //TODO 提现地址问题
        //TODO 提现记录
        //TODO 提现表
        EthtokenKey ethtokenKey = new EthtokenKey();
        EostokenKey eostokenKey = new EostokenKey();
        ethtokenKey.setUid(UID);
        eostokenKey.setUid(UID);
        switch (tokenType) {
            //ETH
            case 0:
                ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                Ethtoken ethtoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
                Double amount_eth = ethtoken.getAmount();
                //余额不足
                if (amount_eth < tokenAmount) return false;
                amount_eth -= tokenAmount;
                ethtoken.setAmount(amount_eth);
                ethtokenMapper.updateByPrimaryKey(ethtoken);
                break;
            //EOS
            case 1:
                eostokenKey.setType(CodeRepresentation.EOS_TOKEN_TYPE_EOS);
                Eostoken eostoken = eostokenMapper.selectByPrimaryKey(eostokenKey);
                Double amount_eos = eostoken.getAmount();
                //余额不足
                if (amount_eos < tokenAmount) return false;
                amount_eos -= tokenAmount;
                eostoken.setAmount(amount_eos);
                eostokenMapper.updateByPrimaryKey(eostoken);
                break;
            //BGS
            case 2:
                ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_BGS);
                Ethtoken bgstoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
                Double amount_bgs = bgstoken.getAmount();
                //余额不足
                if (amount_bgs < tokenAmount) return false;
                amount_bgs -= tokenAmount;
                bgstoken.setAmount(amount_bgs);
                ethtokenMapper.updateByPrimaryKey(bgstoken);
                break;
        }
        //TODO 需要业务人员判断是否同意该用户提币
        //TODO 转到链上钱包地址
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
