package com.superwallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.superwallet.common.*;
import com.superwallet.mapper.*;
import com.superwallet.pojo.*;
import com.superwallet.response.ResponseCWalletProfit;
import com.superwallet.response.ResponseCWalletProfitEntry;
import com.superwallet.response.ResponseCWalletSimProfit;
import com.superwallet.response.ResponseCWalletSimProfitEntry;
import com.superwallet.service.CWalletService;
import com.superwallet.service.CommonService;
import com.superwallet.utils.HttpUtil;
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
        CommonWalletInfo eos = commonService.getMappingCWalletInfo(UID, CodeRepresentation.TOKENTYPE_EOS);
        CommonWalletInfo bgs = commonService.getMappingCWalletInfo(UID, CodeRepresentation.TOKENTYPE_BGS);
        CWalletInfo ethInfo = new CWalletInfo(eth.getTokenAddress(), eth.getcWalletAmount(),
                eth.getTokenPrice(), eth.getLockedAmount(), eth.getcWalletAmount());
        CWalletInfo bgsInfo = new CWalletInfo(bgs.getTokenAddress(), bgs.getcWalletAmount(),
                bgs.getTokenPrice(), bgs.getLockedAmount(), bgs.getcWalletAmount());
        CWalletInfo eosInfo = new CWalletInfo(eos.getTokenAddress(), eos.getcWalletAmount(),
                eos.getTokenPrice(), eos.getLockedAmount(), eos.getcWalletAmount());
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
        String addressFrom, addressTo;
        //请求nodejs的参数
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(RequestParams.UID, UID);
        params.put(RequestParams.AMOUNT, tokenAmount);
        //nodejs返回结果--JSON String
        String resp;
        SuperResult result;
        //转账类型
        Byte transferType = CodeRepresentation.TRANSFER_TYPE_ON2OFF;
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
                //TODO 事务管理
                //余额转入,钱包更新
                amount_eth += tokenAmount;
                ethtoken.setAmount(amount_eth);
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
                //余额转入,钱包更新
                amount_eos += tokenAmount;
                eostoken.setAmount(amount_eos);
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
                //余额转入,钱包更新
                amount_bgs += tokenAmount;
                bgstoken.setAmount(amount_bgs);
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
        String addressFrom, addressTo;
        //nodejs返回结果
        SuperResult result;
        //转账类型
        byte transferType = CodeRepresentation.TRANSFER_TYPE_WITHDRAW;
        //转账币种
        Byte token = Byte.valueOf(tokenType + "");
        //拿到申请表里的转账记录
        WithdrawmoneyKey key = new WithdrawmoneyKey(UID, WID);
        Withdrawmoney record = withdrawmoneyMapper.selectByPrimaryKey(key);
        switch (tokenType) {
            //ETH
            case CodeRepresentation.TOKENTYPE_ETH:
                Ethtoken ethtoken = (Ethtoken) commonService.getToken(UID, tokenType);
                //从中心转到链上
                addressFrom = CodeRepresentation.SUPER_ETH;
                addressTo = ethtoken.getEthaddress();
                result = commonService.transferOnChain(UID, tokenAmount, addressFrom, addressTo, CodeRepresentation.ETH_TOKEN_TYPE_ETH, CodeRepresentation.TRANSFER_CHAIN_ETH);
                //链上转账请求失败
                if (result.getCode() == 0) {
                    //提现失败则退回金额，并记录一笔转账记录，此单作废
                    record.setStatus(CodeRepresentation.WITHDRAW_FAIL);
                    withdrawmoneyMapper.updateByPrimaryKey(record);
                    updateETHWalletAmount(UID, tokenAmount, CodeRepresentation.CWALLET_MONEY_INC);
                    commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_FAIL, addressFrom, addressTo, tokenAmount);
                    return false;
                }
                //申请表记录更新--涉及到钱是否返还问题
                record.setStatus(CodeRepresentation.WITHDRAW_SUCCESS);
                withdrawmoneyMapper.updateByPrimaryKey(record);
                commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_SUCCESS, addressFrom, addressTo, tokenAmount);
                break;
            //EOS
            case CodeRepresentation.TOKENTYPE_EOS:
                Eostoken eostoken = (Eostoken) commonService.getToken(UID, tokenType);
                //从中心转到链上
                addressFrom = CodeRepresentation.SUPER_EOS;
                addressTo = eostoken.getEosaccountname();
                result = commonService.transferOnChain(UID, tokenAmount, addressFrom, addressTo, CodeRepresentation.EOS_TOKEN_TYPE_EOS, CodeRepresentation.TRANSFER_CHAIN_EOS);
                //链上转账请求失败
                if (result.getCode() == 0) {
                    //提现失败则退回金额，并记录一笔转账记录，此单作废
                    record.setStatus(CodeRepresentation.WITHDRAW_FAIL);
                    withdrawmoneyMapper.updateByPrimaryKey(record);
                    updateEOSWalletAmount(UID, tokenAmount, CodeRepresentation.CWALLET_MONEY_INC);
                    commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_FAIL, addressFrom, addressTo, tokenAmount);
                    return false;
                }
                //申请表记录更新--涉及到钱是否返还问题
                record.setStatus(CodeRepresentation.WITHDRAW_SUCCESS);
                withdrawmoneyMapper.updateByPrimaryKey(record);
                commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_SUCCESS, addressFrom, addressTo, tokenAmount);
                break;
            //BGS
            case CodeRepresentation.TOKENTYPE_BGS:
                Ethtoken bgstoken = (Ethtoken) commonService.getToken(UID, tokenType);
                //从中心转到链上
                addressFrom = CodeRepresentation.SUPER_BGS;
                addressTo = bgstoken.getEthaddress();
                result = commonService.transferOnChain(UID, tokenAmount, addressFrom, addressTo, CodeRepresentation.ETH_TOKEN_TYPE_BGS, CodeRepresentation.TRANSFER_CHAIN_ETH);
                //链上转账请求失败
                if (result.getCode() == 0) {
                    //提现失败则退回金额，并记录一笔转账记录，此单作废
                    record.setStatus(CodeRepresentation.WITHDRAW_FAIL);
                    withdrawmoneyMapper.updateByPrimaryKey(record);
                    updateBGSWalletAmount(UID, tokenAmount, CodeRepresentation.CWALLET_MONEY_INC);
                    commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_FAIL, addressFrom, addressTo, tokenAmount);
                    return false;
                }
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
        double PRICE_BUYAGENT_BGS;
        try {
            PRICE_BUYAGENT_BGS = Double.parseDouble(jedisClient.hget("operationCode", "PRICE_BUYAGENT_BGS"));
        } catch (Exception e) {
            PRICE_BUYAGENT_BGS = DynamicParameters.PRICE_BUYAGENT_BGS;
        }
        Userbasic user = userbasicMapper.selectByPrimaryKey(UID);
        EthtokenKey ethtokenKey = new EthtokenKey();
        ethtokenKey.setUid(UID);
        ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_BGS);
        Ethtoken bgstoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
        //余额不足
        if (bgstoken.getAmount() < PRICE_BUYAGENT_BGS) {
            return false;
        }
        //购买成功
        updateBGSWalletAmount(UID, PRICE_BUYAGENT_BGS, CodeRepresentation.CWALLET_MONEY_DEC);
        //设置用户为代理人
        user.setIsagency(CodeRepresentation.USER_AGENT_ISAGENCY);
        userbasicMapper.updateByPrimaryKey(user);
        //转账记录
        commonService.generateRecord(UID,
                CodeRepresentation.TRANSFER_TYPE_BUYAGENT,
                Byte.valueOf(CodeRepresentation.TOKENTYPE_BGS + ""),
                CodeRepresentation.TRANSFER_SUCCESS, bgstoken.getEthaddress(),
                CodeRepresentation.SUPER_BGS,
                PRICE_BUYAGENT_BGS);
        return true;
    }

    /**
     * 收益列表展示
     *
     * @param UID
     * @return
     */
    @Override
    public ResponseCWalletSimProfit listProfit(String UID) {
        double totalProfitToRMB = 0;
        List<ResponseCWalletSimProfitEntry> list = new ArrayList<ResponseCWalletSimProfitEntry>();
        ResponseCWalletSimProfitEntry eth = commonService.getCWalletTokenProfit(UID, CodeRepresentation.TOKENTYPE_ETH);
        ResponseCWalletSimProfitEntry eos = commonService.getCWalletTokenProfit(UID, CodeRepresentation.TOKENTYPE_EOS);
        ResponseCWalletSimProfitEntry bgs = commonService.getCWalletTokenProfit(UID, CodeRepresentation.TOKENTYPE_BGS);
        list.add(eth);
        list.add(eos);
        list.add(bgs);
        totalProfitToRMB = eth.getTokenProfitToRMB() + eos.getTokenProfitToRMB() + bgs.getTokenProfitToRMB();
        ResponseCWalletSimProfit result = new ResponseCWalletSimProfit(totalProfitToRMB, list);
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
        criteria.andTransfertypeEqualTo(CodeRepresentation.TRANSFER_TYPE_WITHDRAW);
        double tokenPrice = 1.0;
        List<Transfer> list = transferMapper.selectByExample(transferExample);
        List<ResponseCWalletProfitEntry> result = new ArrayList<ResponseCWalletProfitEntry>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Transfer record : list) {
            result.add(new ResponseCWalletProfitEntry(
                    CodeRepresentation.PROFIT_TYPE_WITHDRAW + "",
                    format.format(record.getCreatedtime()),
                    CodeRepresentation.PROFIT_FINISHED,
                    CodeRepresentation.PROFIT_STATUS_FINISHED + "",
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
        criteria.andTransfertypeEqualTo(CodeRepresentation.TRANSFER_TYPE_INVITINGBGS);
        double tokenPrice = 1.0;
        List<Transfer> list = transferMapper.selectByExample(transferExample);
        List<ResponseCWalletProfitEntry> result = new ArrayList<ResponseCWalletProfitEntry>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Transfer record : list) {
            result.add(new ResponseCWalletProfitEntry(
                    CodeRepresentation.PROFIT_TYPE_INVITING + "",
                    format.format(record.getCreatedtime()),
                    CodeRepresentation.PROFIT_FINISHED,
                    CodeRepresentation.PROFIT_STATUS_FINISHED + "",
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
        //TODO 拿对应货币行情价
        int profitTokentype = order.getProfittokentype();
        double tokenPrice = 1.0;
        result.setIsFinished(CodeRepresentation.PROFIT_FINISHED);
        result.setProfit(order.getFinalprofit());
        result.setProfitToRMB(order.getFinalprofit() * tokenPrice);
        //时间为锁仓结束时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        result.setTime(format.format(order.getCreatedtime().getTime() + order.getPeriod() * 24 * 60 * 60 * 1000));
        //收益状态
        //TODO 字典树
        result.setStatus(CodeRepresentation.PROFIT_STATUS_FINISHED + "");
        //收益类型
        result.setType(CodeRepresentation.PROFIT_TYPE_LOCK + "");
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
        //TODO 货币行情价
        double tokenPrice = 1.0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (profitType == CodeRepresentation.PROFIT_TYPE_LOCK) {
            double profit = 0;
            for (Profit row : list) {
                profit += row.getProfit();
            }
            //TODO 文字字典树
            result.add(new ResponseCWalletProfitEntry(
                    profitType + "",
                    format.format(new Date()),
                    CodeRepresentation.PROFIT_NOTFINISHED,
                    CodeRepresentation.PROFIT_STATUS_ONPROFIT + "",
                    profit,
                    profit * tokenPrice
            ));
        } else {//其他收益类型直接记录每一条收益记录
            for (Profit row : list) {
                result.add(new ResponseCWalletProfitEntry(
                        profitType + "",
                        format.format(row.getCreatetime()),
                        CodeRepresentation.PROFIT_FINISHED,
                        CodeRepresentation.PROFIT_STATUS_FINISHED + "",
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
    public ResponseCWalletProfit listDetailProfit(String UID, Integer tokenType) {
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
        List<ResponseCWalletProfitEntry> lockedOrderProfit = getLockedOrderProfit(UID, tokenType);
        //拿到用户所有的锁仓收益金额
        if (lockedOrderProfit != null && lockedOrderProfit.size() != 0) {
            result.addAll(lockedOrderProfit);
            listCount += lockedOrderProfit.size();
            for (ResponseCWalletProfitEntry row : lockedOrderProfit) {
                lockedProfit += row.getProfit();
            }
        }
        List<ResponseCWalletProfitEntry> agentProfitList = getAgentProfit(UID, tokenType);
        //拿到用户代理人的收益金额
        if (agentProfitList != null && agentProfitList.size() != 0) {
            result.addAll(agentProfitList);
            listCount += agentProfitList.size();
            for (ResponseCWalletProfitEntry row : lockedOrderProfit) {
                agentProfit += row.getProfit();
            }
        }
        List<ResponseCWalletProfitEntry> withDrawProfit = getWithDrawProfit(UID, tokenType);
        if (withDrawProfit != null && withDrawProfit.size() != 0) {
            listCount += withDrawProfit.size();
            result.addAll(withDrawProfit);
        }
        List<ResponseCWalletProfitEntry> invitingProfit = getInvitingProfit(UID, tokenType);
        if (invitingProfit != null || invitingProfit.size() != 0) {
            listCount += invitingProfit.size();
            result.addAll(invitingProfit);
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
    public void updateETHWalletAmount(String UID, double amount, Integer type) {
        EthtokenKey ethtokenKey = new EthtokenKey();
        ethtokenKey.setUid(UID);
        ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_ETH);
        Ethtoken ethtoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
        double remain = ethtoken.getAmount();
        if (type == CodeRepresentation.CWALLET_MONEY_INC) remain += amount;
        else if (type == CodeRepresentation.CWALLET_MONEY_DEC) remain -= amount;
        ethtoken.setAmount(remain);
        ethtokenMapper.updateByPrimaryKey(ethtoken);
    }

    /**
     * 更新EOS中心钱包表余额
     *
     * @param UID
     * @param amount
     * @param type
     */
    @Override
    public void updateEOSWalletAmount(String UID, double amount, Integer type) {
        EostokenKey eostokenKey = new EostokenKey();
        eostokenKey.setUid(UID);
        eostokenKey.setType(CodeRepresentation.EOS_TOKEN_TYPE_EOS);
        Eostoken eostoken = eostokenMapper.selectByPrimaryKey(eostokenKey);
        double remain = eostoken.getAmount();
        if (type == CodeRepresentation.CWALLET_MONEY_INC) remain += amount;
        else if (type == CodeRepresentation.CWALLET_MONEY_DEC) remain -= amount;
        eostoken.setAmount(remain);
        eostokenMapper.updateByPrimaryKey(eostoken);
    }

    /**
     * 更新ETH-BGS中心钱包表余额
     *
     * @param UID
     * @param amount
     * @param type
     */
    @Override
    public void updateBGSWalletAmount(String UID, double amount, Integer type) {
        EthtokenKey bgstokenKey = new EthtokenKey();
        bgstokenKey.setUid(UID);
        bgstokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_BGS);
        Ethtoken bgstoken = ethtokenMapper.selectByPrimaryKey(bgstokenKey);
        double remain = bgstoken.getAmount();
        if (type == CodeRepresentation.CWALLET_MONEY_INC) remain += amount;
        else if (type == CodeRepresentation.CWALLET_MONEY_DEC) remain -= amount;
        bgstoken.setAmount(remain);
        ethtokenMapper.updateByPrimaryKey(bgstoken);
    }
}
