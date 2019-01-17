package com.superwallet.service.impl;

import com.superwallet.common.*;
import com.superwallet.mapper.*;
import com.superwallet.pojo.*;
import com.superwallet.response.ResponseCWalletProfit;
import com.superwallet.response.ResponseCWalletProfitEntry;
import com.superwallet.response.ResponseCWalletSimProfit;
import com.superwallet.response.ResponseCWalletSimProfitEntry;
import com.superwallet.service.CWalletService;
import com.superwallet.service.CommonService;
import com.superwallet.service.DWalletService;
import com.superwallet.utils.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
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

    @Autowired
    private LockwarehouseMapper lockwarehouseMapper;

    @Autowired
    private ProfitMapper profitMapper;

    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private DWalletService dWalletService;

    /**
     * 获取用户的所有中心化钱包信息
     *
     * @param UID
     * @return
     */
    @Override
    public List<CWalletInfo> listCWalletInfo(String UID) {
        List<CWalletInfo> list = new ArrayList<CWalletInfo>();
        CommonWalletInfo eth = commonService.getMappingCWalletInfo(UID, CodeRepresentation.TOKENTYPE_ETH);
        //EOS钱包需要做特判
        if (commonService.hasEOSWallet(UID)) {
            CommonWalletInfo eos = commonService.getMappingCWalletInfo(UID, CodeRepresentation.TOKENTYPE_EOS);
            CWalletInfo eosInfo = new CWalletInfo(eos.getTokenAddress(), eos.getcWalletAmount(),
                    eos.getTokenPrice(), eos.getLockedAmount(), eos.getcWalletAmount());
            list.add(eosInfo);
        }
        CommonWalletInfo bgs = commonService.getMappingCWalletInfo(UID, CodeRepresentation.TOKENTYPE_BGS);
        CWalletInfo ethInfo = new CWalletInfo(eth.getTokenAddress(), eth.getcWalletAmount(),
                eth.getTokenPrice(), eth.getLockedAmount(), eth.getcWalletAmount());
        CWalletInfo bgsInfo = new CWalletInfo(bgs.getTokenAddress(), bgs.getcWalletAmount(),
                bgs.getTokenPrice(), bgs.getLockedAmount(), bgs.getcWalletAmount());
        list.add(ethInfo);
        list.add(bgsInfo);
        return list;
    }

    /**
     * 中心钱包转入
     *
     * @return
     */
    @Override
    @Transactional
    public boolean transferMoney(String UID, Integer tokenType, Double tokenAmount, Double gasPrice) {
        String addressFrom, addressTo;
        //请求nodejs的参数
        //nodejs返回结果--JSON String
        SuperResult result;
        //转账类型
        Byte transferType = CodeRepresentation.TRANSFER_TYPE_ON2OFF;
        //转账币种
        Byte token = new Byte(tokenType + "");
        boolean res;
        switch (tokenType) {
            //转入eth钱包
            case CodeRepresentation.TOKENTYPE_ETH:
                //找到中心化钱包
                Ethtoken ethtoken = (Ethtoken) commonService.getToken(UID, tokenType);
                addressFrom = CodeRepresentation.SUPER_ETH;
                addressTo = ethtoken.getEthaddress();
                //链上HTTP请求
                result = commonService.ETHTransfer(UID, tokenAmount, gasPrice, addressFrom, addressTo, CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
                //余额转入,钱包更新
                updateETHWalletAmount(UID, tokenAmount, CodeRepresentation.CWALLET_MONEY_INC);
                //生成转账记录
                res = commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_SUCCESS, addressFrom, addressTo, tokenAmount);
                if (!res) {
                    try {
                        throw new Exception();
                    } catch (Exception e) {
                        System.out.println("ETH转账交易记录生成失败");
                        new SuperResult(CodeRepresentation.CODE_ERROR, CodeRepresentation.STATUS_0, MessageRepresentation.ERROR_MSG, null);
                    }
                }
                break;
            //转入eos钱包
            case CodeRepresentation.TOKENTYPE_EOS:
                //找到中心化eos钱包
                Eostoken eostoken = (Eostoken) commonService.getToken(UID, tokenType);
                addressFrom = CodeRepresentation.SUPER_EOS;
                addressTo = eostoken.getEosaccountname();
                //链上请求
                result = commonService.EOSTransfer(UID, tokenAmount, addressFrom, addressTo, CodeRepresentation.EOS_TOKEN_TYPE_EOS, null);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
                //余额转入,钱包更新
                updateEOSWalletAmount(UID, tokenAmount, CodeRepresentation.CWALLET_MONEY_INC);
                //生成转账记录
                res = commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_SUCCESS, addressFrom, addressTo, tokenAmount);
                if (!res) {
                    try {
                        throw new Exception();
                    } catch (Exception e) {
                        System.out.println("EOS转账交易记录生成失败");
                        new SuperResult(CodeRepresentation.CODE_ERROR, CodeRepresentation.STATUS_0, MessageRepresentation.ERROR_MSG, null);
                    }
                }
                break;
            //转入bgs钱包
            case CodeRepresentation.TOKENTYPE_BGS:
                //找到中心化BGS钱包
                Ethtoken bgstoken = (Ethtoken) commonService.getToken(UID, tokenType);
                String fromAddress_bgs = bgstoken.getEthaddress();
                addressFrom = CodeRepresentation.SUPER_BGS;
                addressTo = fromAddress_bgs;
                //链上请求
                result = commonService.ETHTransfer(UID, tokenAmount, gasPrice, addressFrom, addressTo, CodeRepresentation.ETH_TOKEN_TYPE_BGS);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
                //余额转入,钱包更新
                updateBGSWalletAmount(UID, tokenAmount, CodeRepresentation.CWALLET_MONEY_INC);
                //生成转账记录
                res = commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_SUCCESS, addressFrom, addressTo, tokenAmount);
                if (!res) {
                    try {
                        throw new Exception();
                    } catch (Exception e) {
                        System.out.println("BGS转账交易记录生成失败");
                        new SuperResult(CodeRepresentation.CODE_ERROR, CodeRepresentation.STATUS_0, MessageRepresentation.ERROR_MSG, null);
                    }
                }
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
    @Transactional
    public boolean withdrawRequest(String UID, Integer tokenType, Double tokenAmount) {
        //判断提现额度是否小于最小额度
        double minAmount = commonService.getMinWithdrawAmount(tokenType);
        if (tokenAmount < minAmount) {
            return false;
        }
        //拿到中心钱包信息
        EthtokenKey ethtokenKey = new EthtokenKey();
        EostokenKey eostokenKey = new EostokenKey();
        ethtokenKey.setUid(UID);
        eostokenKey.setUid(UID);
        Double amount;
        switch (tokenType) {
            //ETH
            case CodeRepresentation.TOKENTYPE_ETH:
                ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                Ethtoken ethtoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
                //获得可用余额
                amount = ethtoken.getAmount();
                //余额不足
                if (amount < tokenAmount) return false;
                //余额更新
                updateETHWalletAmount(UID, tokenAmount, CodeRepresentation.CWALLET_MONEY_DEC);
                commonService.generateRecord(UID, CodeRepresentation.TRANSFER_TYPE_WITHDRAW_OUT, Byte.valueOf(tokenType + ""), CodeRepresentation.TRANSFER_SUCCESS, ethtoken.getEthaddress(), ethtoken.getEthaddress(), tokenAmount);
                commonService.withdrawRecord(UID, UUID.randomUUID().toString(), Byte.valueOf(tokenType + ""), CodeRepresentation.WITHDRAW_WAIT, tokenAmount);
                break;
            //EOS
            case CodeRepresentation.TOKENTYPE_EOS:
                eostokenKey.setType(CodeRepresentation.EOS_TOKEN_TYPE_EOS);
                Eostoken eostoken = eostokenMapper.selectByPrimaryKey(eostokenKey);
                //获得可用余额
                amount = eostoken.getAmount();
                //余额不足
                if (amount < tokenAmount) return false;
                //余额更新
                updateEOSWalletAmount(UID, tokenAmount, CodeRepresentation.CWALLET_MONEY_DEC);
                commonService.generateRecord(UID, CodeRepresentation.TRANSFER_TYPE_WITHDRAW_OUT, Byte.valueOf(tokenType + ""), CodeRepresentation.TRANSFER_SUCCESS, eostoken.getEosaccountname(), eostoken.getEosaccountname(), tokenAmount);
                commonService.withdrawRecord(UID, UUID.randomUUID().toString(), new Byte(tokenType + ""), CodeRepresentation.WITHDRAW_WAIT, tokenAmount);
                break;
            //BGS
            case CodeRepresentation.TOKENTYPE_BGS:
                ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_BGS);
                Ethtoken bgstoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
                //获得可用余额
                amount = bgstoken.getAmount();
                //余额不足
                if (amount < tokenAmount) return false;
                //余额更新
                updateBGSWalletAmount(UID, tokenAmount, CodeRepresentation.CWALLET_MONEY_DEC);
                commonService.generateRecord(UID, CodeRepresentation.TRANSFER_TYPE_WITHDRAW_OUT, Byte.valueOf(tokenType + ""), CodeRepresentation.TRANSFER_SUCCESS, bgstoken.getEthaddress(), bgstoken.getEthaddress(), tokenAmount);
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
    public boolean withdraw(String UID, String WID, Integer tokenType, Double tokenAmount, Double gasPrice) {
        String addressFrom, addressTo;
        //nodejs返回结果
        SuperResult result;
        //转账类型
        byte transferType;
        //转账币种
        Byte token = Byte.valueOf(tokenType + "");
        //拿到申请表里的转账记录
        WithdrawmoneyKey key = new WithdrawmoneyKey(UID, WID);
        Withdrawmoney record = withdrawmoneyMapper.selectByPrimaryKey(key);
        if (record.getStatus() == CodeRepresentation.WITHDRAW_FAIL) return false;
        switch (tokenType) {
            //ETH
            case CodeRepresentation.TOKENTYPE_ETH:
                Ethtoken ethtoken = (Ethtoken) commonService.getToken(UID, tokenType);
                //从中心转到链上
                addressFrom = CodeRepresentation.SUPER_ETH;
                addressTo = ethtoken.getEthaddress();
                result = commonService.ETHTransfer(CodeRepresentation.SUPER_UID, tokenAmount, gasPrice, addressFrom, addressTo, CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                //链上转账请求失败
                if (result.getCode() == CodeRepresentation.CODE_FAIL) {
                    //提现失败则退回金额，并记录一笔转账记录，此单作废
                    record.setStatus(CodeRepresentation.WITHDRAW_FAIL);
                    withdrawmoneyMapper.updateByPrimaryKey(record);
                    updateETHWalletAmount(UID, tokenAmount, CodeRepresentation.CWALLET_MONEY_INC);
                    transferType = CodeRepresentation.TRANSFER_TYPE_WITHDRAW_FAIL;
                    commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_FAIL, addressFrom, addressTo, tokenAmount);
                    return false;
                }
                //申请表记录更新--涉及到钱是否返还问题
                record.setStatus(CodeRepresentation.WITHDRAW_SUCCESS);
                withdrawmoneyMapper.updateByPrimaryKey(record);
                transferType = CodeRepresentation.TRANSFER_TYPE_WITHDRAW_IN;
                commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_SUCCESS, addressFrom, addressTo, tokenAmount);
                break;
            //EOS
            case CodeRepresentation.TOKENTYPE_EOS:
                Eostoken eostoken = (Eostoken) commonService.getToken(UID, tokenType);
                //从中心转到链上
                addressFrom = CodeRepresentation.SUPER_EOS;
                addressTo = eostoken.getEosaccountname();
                result = commonService.EOSTransfer(CodeRepresentation.SUPER_UID, tokenAmount, addressFrom, addressTo, CodeRepresentation.EOS_TOKEN_TYPE_EOS, "提现");
                //链上转账请求失败
                if (result.getCode() == CodeRepresentation.CODE_FAIL) {
                    //提现失败则退回金额，并记录一笔转账记录，此单作废
                    record.setStatus(CodeRepresentation.WITHDRAW_FAIL);
                    withdrawmoneyMapper.updateByPrimaryKey(record);
                    updateEOSWalletAmount(UID, tokenAmount, CodeRepresentation.CWALLET_MONEY_INC);
                    transferType = CodeRepresentation.TRANSFER_TYPE_WITHDRAW_FAIL;
                    commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_FAIL, addressFrom, addressTo, tokenAmount);
                    return false;
                }
                //申请表记录更新--涉及到钱是否返还问题
                record.setStatus(CodeRepresentation.WITHDRAW_SUCCESS);
                withdrawmoneyMapper.updateByPrimaryKey(record);
                transferType = CodeRepresentation.TRANSFER_TYPE_WITHDRAW_IN;
                commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_SUCCESS, addressFrom, addressTo, tokenAmount);
                break;
            //BGS
            case CodeRepresentation.TOKENTYPE_BGS:
                Ethtoken bgstoken = (Ethtoken) commonService.getToken(UID, tokenType);
                //从中心转到链上
                addressFrom = CodeRepresentation.SUPER_BGS;
                addressTo = bgstoken.getEthaddress();
                result = commonService.ETHTransfer(CodeRepresentation.SUPER_UID, tokenAmount, gasPrice, addressFrom, addressTo, CodeRepresentation.ETH_TOKEN_TYPE_BGS);
                //链上转账请求失败
                if (result.getCode() == CodeRepresentation.CODE_FAIL) {
                    //提现失败则退回金额，并记录一笔转账记录，此单作废
                    record.setStatus(CodeRepresentation.WITHDRAW_FAIL);
                    withdrawmoneyMapper.updateByPrimaryKey(record);
                    updateBGSWalletAmount(UID, tokenAmount, CodeRepresentation.CWALLET_MONEY_INC);
                    transferType = CodeRepresentation.TRANSFER_TYPE_WITHDRAW_FAIL;
                    commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_FAIL, addressFrom, addressTo, tokenAmount);
                    return false;
                }
                //申请表记录更新--涉及到钱是否返还问题
                record.setStatus(CodeRepresentation.WITHDRAW_SUCCESS);
                withdrawmoneyMapper.updateByPrimaryKey(record);
                transferType = CodeRepresentation.TRANSFER_TYPE_WITHDRAW_IN;
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
        if (tokenType != CodeRepresentation.TOKENTYPE_ALL)
            criteria.andTokentypeEqualTo(tokenType.byteValue());
        List<Transfer> result = transferMapper.selectByExample(transferExample);
        return result;
    }

    /**
     * 收益列表展示
     *
     * @param UID
     * @return
     */
    @Override
    public ResponseCWalletSimProfit listProfit(String UID) {
        double allTokenAmountToRMB, totalProfitToRMB = 0;
        double eth_tokenPrice = commonService.getTokenPriceByType(CodeRepresentation.TOKENTYPE_ETH);
        double eos_tokenPrice = commonService.getTokenPriceByType(CodeRepresentation.TOKENTYPE_EOS);
        double bgs_tokenPrice = commonService.getTokenPriceByType(CodeRepresentation.TOKENTYPE_BGS);
        List<ResponseCWalletSimProfitEntry> list = new ArrayList<ResponseCWalletSimProfitEntry>();
        ResponseCWalletSimProfitEntry eth = commonService.getCWalletTokenProfit(UID, CodeRepresentation.TOKENTYPE_ETH);
        ResponseCWalletSimProfitEntry eos = commonService.getCWalletTokenProfit(UID, CodeRepresentation.TOKENTYPE_EOS);
        ResponseCWalletSimProfitEntry bgs = commonService.getCWalletTokenProfit(UID, CodeRepresentation.TOKENTYPE_BGS);
        list.add(eth);
        list.add(eos);
        list.add(bgs);
        //算链上总额
        CommonWalletInfo ethWalletInfo = commonService.getMappingDAndCWalletInfo(UID, CodeRepresentation.TOKENTYPE_ETH);
        CommonWalletInfo eosWalletInfo = commonService.getMappingDAndCWalletInfo(UID, CodeRepresentation.TOKENTYPE_EOS);
        CommonWalletInfo bgsWalletInfo = commonService.getMappingDAndCWalletInfo(UID, CodeRepresentation.TOKENTYPE_BGS);
        allTokenAmountToRMB = ethWalletInfo.getBalance() * eth_tokenPrice + bgsWalletInfo.getBalance() * bgs_tokenPrice + eosWalletInfo.getBalance() * eos_tokenPrice;
        totalProfitToRMB = eth.getTokenProfitToRMB() + eos.getTokenProfitToRMB() + bgs.getTokenProfitToRMB();
        ResponseCWalletSimProfit result = new ResponseCWalletSimProfit(allTokenAmountToRMB, totalProfitToRMB, list);
        return result;
    }

    /**
     * 拿到特定币种锁仓的所有收益
     *
     * @param UID
     * @param tokenType
     * @return
     */
    @Override
    public List<ResponseCWalletProfitEntry> getLockedOrderProfit(String UID, Integer tokenType) {
        //查询所有锁仓订单
        LockwarehouseExample lockwarehouseExample = new LockwarehouseExample();
        LockwarehouseExample.Criteria criteria = lockwarehouseExample.createCriteria();
        criteria.andUidEqualTo(UID);
        criteria.andProfittokentypeEqualTo(tokenType);
        List<Lockwarehouse> orders = lockwarehouseMapper.selectByExample(lockwarehouseExample);
        if (orders == null || orders.size() == 0) return null;
        List<ResponseCWalletProfitEntry> result = new ArrayList<ResponseCWalletProfitEntry>();
        for (Lockwarehouse order : orders) {
            //锁仓订单状态为已结束
            if (order.getStatus() == CodeRepresentation.LOCK_STATUS_ONOVER || order.getStatus() == CodeRepresentation.LOCK_STATUS_FINISHED) {
                ResponseCWalletProfitEntry entry = finishedLockedOrderToEntry(order);
                result.add(entry);
            } else {//锁仓订单状态为收益中
                List<ResponseCWalletProfitEntry> list = totalProfitToEntry(UID, tokenType, order.getLid() + "", CodeRepresentation.PROFIT_TYPE_LOCK);
                result.addAll(list);
            }
        }
        return result;
    }

    /**
     * 拿到所有划转的收益--查询transfer表
     *
     * @param UID
     * @param tokenType
     * @return
     */
    @Override
    public List<ResponseCWalletProfitEntry> getWithDrawProfit(String UID, Integer tokenType) {
        //查询transfer表，拿到所有划转记录
        TransferExample transferExample = new TransferExample();
        TransferExample.Criteria criteria = transferExample.createCriteria();
        criteria.andUidEqualTo(UID);
        criteria.andTokentypeEqualTo(new Byte(tokenType + ""));
        criteria.andTransfertypeEqualTo(CodeRepresentation.TRANSFER_TYPE_WITHDRAW_IN);
        criteria.andStatusEqualTo(CodeRepresentation.TRANSFER_SUCCESS);
        double tokenPrice = commonService.getTokenPriceByType(tokenType);
        List<Transfer> list = transferMapper.selectByExample(transferExample);
        List<ResponseCWalletProfitEntry> result = new ArrayList<ResponseCWalletProfitEntry>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Transfer record : list) {
            result.add(new ResponseCWalletProfitEntry(
                    CodeRepresentation.PROFIT_TYPE_MAPPING.get(CodeRepresentation.PROFIT_TYPE_WITHDRAW),
                    format.format(record.getCreatedtime()),
                    CodeRepresentation.PROFIT_FINISHED,
                    record.getStatus(),
                    record.getAmount(),
                    record.getAmount() * tokenPrice
            ));
        }
        return result;
    }

    /**
     * 获取代理人收益
     *
     * @param UID
     * @param tokenType
     * @return
     */
    @Override
    public List<ResponseCWalletProfitEntry> getAgentProfit(String UID, Integer tokenType) {
        List<ResponseCWalletProfitEntry> result = totalProfitToEntry(UID, tokenType, null, CodeRepresentation.PROFIT_TYPE_AGENT);
        return result;
    }

    /**
     * 获取邀请人得到的收益
     *
     * @param UID
     * @param tokenType
     * @return
     */
    @Override
    public List<ResponseCWalletProfitEntry> getInvitingProfit(String UID, Integer tokenType) {
        //查询transfer表，拿到所有划转记录
        TransferExample transferExample = new TransferExample();
        TransferExample.Criteria criteria = transferExample.createCriteria();
        criteria.andUidEqualTo(UID);
        criteria.andTokentypeEqualTo(new Byte(tokenType + ""));
        ArrayList<Byte> ins = new ArrayList<Byte>();
        ins.add(CodeRepresentation.TRANSFER_TYPE_INVITINGBGS);
        ins.add(CodeRepresentation.TRANSFER_TYPE_REGISTERBGS);
        criteria.andTransfertypeIn(ins);
        double tokenPrice = commonService.getTokenPriceByType(tokenType);
        List<Transfer> list = transferMapper.selectByExample(transferExample);
        List<ResponseCWalletProfitEntry> result = new ArrayList<ResponseCWalletProfitEntry>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Transfer record : list) {
            result.add(new ResponseCWalletProfitEntry(
                    CodeRepresentation.PROFIT_TYPE_MAPPING.get(commonService.invitingOrRegister(record.getTransfertype())),
                    format.format(record.getCreatedtime()),
                    CodeRepresentation.PROFIT_FINISHED,
                    record.getStatus(),
                    record.getAmount(),
                    record.getAmount() * tokenPrice
            ));
        }
        return result;
    }

    /**
     * 将已结束的锁仓对象封装
     *
     * @param order
     * @return
     */
    @Override
    public ResponseCWalletProfitEntry finishedLockedOrderToEntry(Lockwarehouse order) {
        ResponseCWalletProfitEntry result = new ResponseCWalletProfitEntry();
        int profitTokentype = order.getProfittokentype();
        double tokenPrice = commonService.getTokenPriceByType(order.getTokentype());
        result.setIsFinished(CodeRepresentation.PROFIT_FINISHED);
        result.setProfit(order.getFinalprofit());
        result.setProfitToRMB(order.getFinalprofit() * tokenPrice);
        //时间为锁仓结束时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result.setTime(format.format(order.getCreatedtime().getTime() + order.getPeriod() * 24 * 60 * 60 * 1000));
        //收益状态
        result.setStatus(order.getStatus());
        //收益类型
        result.setType(CodeRepresentation.PROFIT_TYPE_MAPPING.get(CodeRepresentation.PROFIT_TYPE_LOCK));
        return result;
    }

    /**
     * 将未完成的收益进行累加计算
     *
     * @param UID
     * @param orderID
     * @param profitType
     * @return
     */
    @Override
    public List<ResponseCWalletProfitEntry> totalProfitToEntry(String UID, Integer tokenType, String orderID, Integer profitType) {
        ProfitExample profitExample = new ProfitExample();
        ProfitExample.Criteria criteria = profitExample.createCriteria();
        criteria.andUidEqualTo(UID);
        if (orderID != null) {
            criteria.andOrderidEqualTo(orderID);
        }
        criteria.andProfittypeEqualTo(profitType);
        List<Profit> list = profitMapper.selectByExample(profitExample);
        //如果收益类型是锁仓，得求和
        List<ResponseCWalletProfitEntry> result = new ArrayList<ResponseCWalletProfitEntry>();
        double tokenPrice = commonService.getTokenPriceByType(tokenType);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (profitType == CodeRepresentation.PROFIT_TYPE_LOCK) {
            double profit = 0;
            for (Profit row : list) {
                profit += row.getProfit();
            }
            result.add(new ResponseCWalletProfitEntry(
                    CodeRepresentation.PROFIT_TYPE_MAPPING.get(profitType),
                    format.format(new Date()),
                    CodeRepresentation.PROFIT_NOTFINISHED,
                    CodeRepresentation.PROFIT_STATUS_ONPROFIT,
                    profit,
                    profit * tokenPrice
            ));
        } else {//其他收益类型直接记录每一条收益记录
            for (Profit row : list) {
                result.add(new ResponseCWalletProfitEntry(
                        CodeRepresentation.PROFIT_TYPE_MAPPING.get(profitType),
                        format.format(row.getCreatetime()),
                        CodeRepresentation.PROFIT_FINISHED,
                        CodeRepresentation.PROFIT_STATUS_FINISHED,
                        row.getProfit(),
                        row.getProfit() * tokenPrice
                ));
            }
        }
        return result;
    }

    /**
     * 币种收益详情展示
     *
     * @param UID
     * @param tokenType
     * @return
     */
    @Override
    public ResponseCWalletProfit listDetailProfit(String UID, Integer tokenType, Integer type) {
        //封装所需所有字段
        int isLocked = 1, isAgent = 1, listCount = 0;
        double tokenProfit, tokenProfitToRMB, lockedProfit = 0, lockedProfitToRMB, agentProfit = 0, agentProfitToRMB, mostRollOut;
        //查询用户的对应货币余额
        CommonWalletInfo walletInfo = commonService.getMappingCWalletInfo(UID, tokenType);
        double tokenPrice = walletInfo.getTokenPrice();
        mostRollOut = walletInfo.getcWalletAmount();
        tokenProfit = walletInfo.getcWalletAmount();
        tokenProfitToRMB = tokenProfit * tokenPrice;
        //先查询用户是否有对于这个货币的锁仓
        LockwarehouseExample isLockedExample = new LockwarehouseExample();
        LockwarehouseExample.Criteria criteria = isLockedExample.createCriteria();
        criteria.andUidEqualTo(UID);
        criteria.andTokentypeEqualTo(tokenType);
        List<Lockwarehouse> lockedOrders = lockwarehouseMapper.selectByExample(isLockedExample);
        if (lockedOrders == null || lockedOrders.size() == 0) isLocked = 0;

        //判断是否是代理人
        Userbasic user = userbasicMapper.selectByPrimaryKey(UID);
        if (user.getIsagency() != CodeRepresentation.USER_AGENT_ISAGENCY) isAgent = 0;

        List<ResponseCWalletProfitEntry> result = new ArrayList<ResponseCWalletProfitEntry>();
        //条件查询
        List<ResponseCWalletProfitEntry> lockedOrderProfit = getLockedOrderProfit(UID, tokenType);
        List<ResponseCWalletProfitEntry> agentProfitList = getAgentProfit(UID, tokenType);
        List<ResponseCWalletProfitEntry> withDrawProfit = getWithDrawProfit(UID, tokenType);
        List<ResponseCWalletProfitEntry> invitingProfit = getInvitingProfit(UID, tokenType);
        switch (type) {
            case CodeRepresentation.LISTDETAILPROFIT_ALL:
                //拿到用户所有的锁仓收益金额
                if (lockedOrderProfit != null && lockedOrderProfit.size() != 0) {
                    result.addAll(lockedOrderProfit);
                    listCount += lockedOrderProfit.size();
                    for (ResponseCWalletProfitEntry row : lockedOrderProfit) {
                        lockedProfit += row.getProfit();
                    }
                }
                //拿到用户代理人的收益金额
                if (agentProfitList != null && agentProfitList.size() != 0) {
                    result.addAll(agentProfitList);
                    listCount += agentProfitList.size();
                    for (ResponseCWalletProfitEntry row : lockedOrderProfit) {
                        agentProfit += row.getProfit();
                    }
                }
                //拿到用户提现收益
                if (withDrawProfit != null && withDrawProfit.size() != 0) {
                    listCount += withDrawProfit.size();
                    result.addAll(withDrawProfit);
                }
                //拿到奖励收益
                if (invitingProfit != null || invitingProfit.size() != 0) {
                    listCount += invitingProfit.size();
                    result.addAll(invitingProfit);
                }
                break;
            case CodeRepresentation.LISTDETAILPROFIT_LOCK:
                //拿到用户所有的锁仓收益金额
                if (lockedOrderProfit != null && lockedOrderProfit.size() != 0) {
                    result.addAll(lockedOrderProfit);
                    listCount += lockedOrderProfit.size();
                    for (ResponseCWalletProfitEntry row : lockedOrderProfit) {
                        lockedProfit += row.getProfit();
                    }
                }
                break;
            case CodeRepresentation.LISTDETAILPROFIT_AGENT:
                //拿到用户代理人的收益金额
                if (agentProfitList != null && agentProfitList.size() != 0) {
                    result.addAll(agentProfitList);
                    listCount += agentProfitList.size();
                    for (ResponseCWalletProfitEntry row : lockedOrderProfit) {
                        agentProfit += row.getProfit();
                    }
                }
                break;
            case CodeRepresentation.LISTDETAILPROFIT_WITHDRAW:
                //拿到用户提现收益
                if (withDrawProfit != null && withDrawProfit.size() != 0) {
                    listCount += withDrawProfit.size();
                    result.addAll(withDrawProfit);
                }
                break;
            case CodeRepresentation.LISTDETAILPROFIT_REWARD:
                //拿到奖励收益
                if (invitingProfit != null || invitingProfit.size() != 0) {
                    listCount += invitingProfit.size();
                    result.addAll(invitingProfit);
                }
                break;
        }
        //记录做排序
        Collections.sort(result, new Comparator<ResponseCWalletProfitEntry>() {
            @Override
            public int compare(ResponseCWalletProfitEntry o1, ResponseCWalletProfitEntry o2) {
                return o2.getTime().compareTo(o1.getTime());
            }
        });
        lockedProfitToRMB = lockedProfit * tokenPrice;
        agentProfitToRMB = agentProfit * tokenPrice;
        ResponseCWalletProfit out = new ResponseCWalletProfit(
                listCount,
                isLocked,
                isAgent,
                tokenProfit,
                tokenProfitToRMB,
                lockedProfit,
                lockedProfitToRMB,
                agentProfit,
                agentProfitToRMB,
                mostRollOut,
                result
        );
        return out;
    }

    /**
     * 更新ETH中心钱包表余额
     *
     * @param UID
     * @param amount
     * @param type
     */
    @Override
    @Transactional
    public boolean updateETHWalletAmount(String UID, double amount, Integer type) {
        EthtokenKey ethtokenKey = new EthtokenKey();
        ethtokenKey.setUid(UID);
        ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_ETH);
        Ethtoken ethtoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
        double remain = ethtoken.getAmount();
        if (type == CodeRepresentation.CWALLET_MONEY_INC) remain += amount;
        else if (type == CodeRepresentation.CWALLET_MONEY_DEC) remain -= amount;
        ethtoken.setAmount(remain);
        int rows = ethtokenMapper.updateByPrimaryKey(ethtoken);
        if (rows == 0) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("更新ETH钱包表失败");
                return false;
            }
        }
        return true;
    }

    /**
     * 更新EOS中心钱包表余额
     *
     * @param UID
     * @param amount
     * @param type
     */
    @Override
    @Transactional
    public boolean updateEOSWalletAmount(String UID, double amount, Integer type) {
        EostokenKey eostokenKey = new EostokenKey();
        eostokenKey.setUid(UID);
        eostokenKey.setType(CodeRepresentation.EOS_TOKEN_TYPE_EOS);
        Eostoken eostoken = eostokenMapper.selectByPrimaryKey(eostokenKey);
        double remain = eostoken.getAmount();
        if (type == CodeRepresentation.CWALLET_MONEY_INC) remain += amount;
        else if (type == CodeRepresentation.CWALLET_MONEY_DEC) remain -= amount;
        eostoken.setAmount(remain);
        int rows = eostokenMapper.updateByPrimaryKey(eostoken);
        if (rows == 0) try {
            throw new Exception();
        } catch (Exception e) {
            System.out.println("更新EOS钱包表失败");
            return false;
        }
        return true;
    }

    /**
     * 更新ETH-BGS中心钱包表余额
     *
     * @param UID
     * @param amount
     * @param type
     */
    @Override
    @Transactional
    public boolean updateBGSWalletAmount(String UID, double amount, Integer type) {
        EthtokenKey bgstokenKey = new EthtokenKey();
        bgstokenKey.setUid(UID);
        bgstokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_BGS);
        Ethtoken bgstoken = ethtokenMapper.selectByPrimaryKey(bgstokenKey);
        double remain = bgstoken.getAmount();
        if (type == CodeRepresentation.CWALLET_MONEY_INC) remain += amount;
        else if (type == CodeRepresentation.CWALLET_MONEY_DEC) remain -= amount;
        bgstoken.setAmount(remain);
        int rows = ethtokenMapper.updateByPrimaryKey(bgstoken);
        if (rows == 0) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("更新BGS钱包表失败");
                return false;
            }
        }
        return true;
    }
}
