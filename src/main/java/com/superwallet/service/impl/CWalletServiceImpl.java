package com.superwallet.service.impl;

import com.superwallet.common.CodeRepresentation;
import com.superwallet.common.WalletInfo;
import com.superwallet.mapper.EostokenMapper;
import com.superwallet.mapper.EthtokenMapper;
import com.superwallet.mapper.TransferMapper;
import com.superwallet.pojo.*;
import com.superwallet.service.CWalletService;
import com.superwallet.utils.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        double HUOBIprice = 0.0;
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
    public boolean transferMoney(String UID, Integer tokenType, Double tokenAmount) {
        //TODO 涉及到金额的数据库事物管理问题
        //TODO 余额判断，链上钱包金额存储问题
        //TODO 每次转账要做记录 表设计问题
        EthtokenKey ethtokenKey = new EthtokenKey();
        EostokenKey eostokenKey = new EostokenKey();
        ethtokenKey.setUid(UID);
        eostokenKey.setUid(UID);
        switch (tokenType) {
            //转入eth钱包
            case 0:
                ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                Ethtoken ethtoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
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
                Double amount_eos = eostoken.getAmount();
                amount_eos += tokenAmount;
                eostoken.setAmount(amount_eos);
                eostokenMapper.updateByExample(eostoken, new EostokenExample());
                break;
            //转入bgs钱包
            case 2:
                ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_BGS);
                Ethtoken bgstoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
                Double amount_bgs = bgstoken.getAmount();
                amount_bgs += tokenAmount;
                bgstoken.setAmount(amount_bgs);
                ethtokenMapper.updateByExample(bgstoken, new EthtokenExample());
                break;
        }
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
                ethtokenMapper.updateByExample(ethtoken, new EthtokenExample());
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
                eostokenMapper.updateByExample(eostoken, new EostokenExample());
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
                ethtokenMapper.updateByExample(bgstoken, new EthtokenExample());
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
}
