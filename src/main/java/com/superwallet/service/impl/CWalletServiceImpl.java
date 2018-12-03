package com.superwallet.service.impl;

import com.superwallet.common.CodeRepresentation;
import com.superwallet.common.WalletInfo;
import com.superwallet.mapper.BgswalletMapper;
import com.superwallet.mapper.EoswalletMapper;
import com.superwallet.mapper.EthwalletMapper;
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
    private EthwalletMapper ethwalletMapper;

    @Autowired
    private EoswalletMapper eoswalletMapper;

    @Autowired
    private BgswalletMapper bgswalletMapper;

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
        //数字货币价格--目前爬虫实现
        String origin = HttpUtil.get(CodeRepresentation.URL_PRICE);
        Document document = Jsoup.parse(origin);
        String eth_price = document.getElementById("id-ethereum").getElementsByClass("price").text();
        String eos_price = document.getElementById("id-eos").getElementsByClass("price").text();
        //TODO 缺少BGS价格
        //以太钱包
        Ethwallet ethwallet = ethwalletMapper.selectByPrimaryKey(UID);
        double HUOBIprice = 0.0;
        double price_eth = Double.parseDouble(eth_price.substring(1));
        double price_eos = Double.parseDouble(eos_price.substring(1));
        WalletInfo ethInfo = new WalletInfo(ethwallet.getEthaddress(), ethwallet.getAmount(),
                price_eth, ethwallet.getLockedamount(), ethwallet.getAvailableamount());
        Bgswallet bgswallet = bgswalletMapper.selectByPrimaryKey(UID);
        WalletInfo bgsInfo = new WalletInfo(ethwallet.getEthaddress(), bgswallet.getAmount(),
                HUOBIprice, bgswallet.getLockedamount(), bgswallet.getAvailableamount());
        Eoswallet eoswallet = eoswalletMapper.selectByPrimaryKey(UID);
        WalletInfo eosInfo = new WalletInfo(eoswallet.getEosaddress(), eoswallet.getAmount(),
                price_eos, eoswallet.getLockedamount(), eoswallet.getAvailableamount());
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
    public boolean transferMoney(String UID, Integer tokenType, Integer tokenAmount) {
        //TODO 涉及到金额的数据库事物管理问题
        //TODO 余额判断，链上钱包金额存储问题
        //TODO 每次转账要做记录 表设计问题
        switch (tokenType) {
            //转入eth钱包
            case 0:
                Ethwallet ethwallet = ethwalletMapper.selectByPrimaryKey(UID);
                Integer amount_eth = ethwallet.getAmount();
                //余额转入
                amount_eth += tokenAmount;
                ethwallet.setAmount(amount_eth);
                ethwalletMapper.updateByExample(ethwallet, new EthwalletExample());
                break;
            //转入eos钱包
            case 1:
                Eoswallet eoswallet = eoswalletMapper.selectByPrimaryKey(UID);
                Integer amount_eos = eoswallet.getAmount();
                amount_eos += tokenAmount;
                eoswallet.setAmount(amount_eos);
                eoswalletMapper.updateByExample(eoswallet, new EoswalletExample());
                break;
            //转入bgs钱包
            case 2:
                Bgswallet bgswallet = bgswalletMapper.selectByPrimaryKey(UID);
                Integer amount_bgs = bgswallet.getAmount();
                amount_bgs += tokenAmount;
                bgswallet.setAmount(amount_bgs);
                bgswalletMapper.updateByExample(bgswallet, new BgswalletExample());
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
    public boolean withdraw(String UID, Integer tokenType, Integer tokenAmount) {
        //TODO 提现地址问题
        //TODO 提现记录
        switch (tokenType) {
            //ETH
            case 0:
                Ethwallet ethwallet = ethwalletMapper.selectByPrimaryKey(UID);
                Integer amount_eth = ethwallet.getAmount();
                //余额不足
                if (amount_eth < tokenAmount) return false;
                amount_eth -= tokenAmount;
                ethwallet.setAmount(amount_eth);
                ethwalletMapper.updateByExample(ethwallet, new EthwalletExample());
                break;
            //EOS
            case 1:
                Eoswallet eoswallet = eoswalletMapper.selectByPrimaryKey(UID);
                Integer amount_eos = eoswallet.getAmount();
                //余额不足
                if (amount_eos < tokenAmount) return false;
                amount_eos -= tokenAmount;
                eoswallet.setAmount(amount_eos);
                eoswalletMapper.updateByExample(eoswallet, new EoswalletExample());
                break;
            //BGS
            case 2:
                Bgswallet bgswallet = bgswalletMapper.selectByPrimaryKey(UID);
                Integer amount_bgs = bgswallet.getAmount();
                //余额不足
                if (amount_bgs < tokenAmount) return false;
                amount_bgs -= tokenAmount;
                bgswallet.setAmount(amount_bgs);
                bgswalletMapper.updateByExample(bgswallet, new BgswalletExample());
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
