package com.superwallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.superwallet.common.*;
import com.superwallet.mapper.EostokenMapper;
import com.superwallet.mapper.EthtokenMapper;
import com.superwallet.mapper.LockwarehouseMapper;
import com.superwallet.mapper.TransferMapper;
import com.superwallet.pojo.*;
import com.superwallet.response.*;
import com.superwallet.service.CWalletService;
import com.superwallet.service.CommonService;
import com.superwallet.service.DWalletService;
import com.superwallet.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    @Autowired
    private CommonService commonService;

    @Autowired
    private CWalletService cWalletService;

    /**
     * 展示链上钱包基本信息
     *
     * @param UID
     * @return
     */
    @Override
    public List<ResponseDWalletSimpleInfo> listDWalletInfo(String UID) {
        List<ResponseDWalletSimpleInfo> list = new ArrayList<ResponseDWalletSimpleInfo>();
        CommonWalletInfo eth = commonService.getMappingDAndCWalletInfo(UID, CodeRepresentation.TOKENTYPE_ETH);
        CommonWalletInfo eos = commonService.getMappingDAndCWalletInfo(UID, CodeRepresentation.TOKENTYPE_EOS);
        CommonWalletInfo bgs = commonService.getMappingDAndCWalletInfo(UID, CodeRepresentation.TOKENTYPE_BGS);
        //ETH信息
        list.add(
                new ResponseDWalletSimpleInfo(
                        eth.getTokenType(),
                        eth.getTokenName(),
                        eth.getTokenAddress(),
                        eth.getBalance() * eth.getTokenPrice()
                )
        );
        //EOS信息
        list.add(
                new ResponseDWalletSimpleInfo(
                        eos.getTokenType(),
                        eos.getTokenName(),
                        eos.getTokenAddress(),
                        eos.getBalance() * eos.getTokenPrice()
                )
        );
        //BGS信息
        list.add(
                new ResponseDWalletSimpleInfo(
                        bgs.getTokenType(),
                        bgs.getTokenName(),
                        bgs.getTokenAddress(),
                        bgs.getBalance() * bgs.getTokenPrice()
                )
        );
        return list;
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
    public SuperResult transferMoney(String UID, Integer tokenType, Double tokenAmount, Double gasPrice, String addressTo, String description) {
        CommonWalletInfo wallet = commonService.getMappingDAndCWalletInfo(UID, tokenType);
        //余额不足 直接返回
        if (wallet.getBalance() < tokenAmount) {
            return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, MessageRepresentation.DWALLET_TRANSFER_CODE_0_STATUS_0, null);
        }
        String addressFrom;
        String resp;
        SuperResult result;
        Byte transferType = CodeRepresentation.TRANSFER_TYPE_ON2ON;
        //转账币种
        Byte token = new Byte(tokenType + "");
        switch (tokenType) {
            //转入eth钱包
            case CodeRepresentation.TOKENTYPE_ETH:
                Ethtoken ethtoken = (Ethtoken) commonService.getToken(UID, tokenType);
                addressFrom = ethtoken.getEthaddress();
                result = commonService.ETHTransfer(UID, tokenAmount, gasPrice, addressFrom, addressTo, CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                //链上转账请求失败
                if (result.getCode() == 0) {
                    return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_1, MessageRepresentation.DWALLET_TRANSFER_CODE_0_STATUS_1, null);
                }
                //请求成功则记录一笔交易记录
                commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_SUCCESS, addressFrom, addressTo, tokenAmount);
                break;
            //转入eos钱包
            case CodeRepresentation.TOKENTYPE_EOS:
                Eostoken eostoken = (Eostoken) commonService.getToken(UID, tokenType);
                addressFrom = eostoken.getEosaccountname();
                //链上请求
                result = commonService.EOSTransfer(UID, tokenAmount, addressFrom, addressTo, CodeRepresentation.EOS_TOKEN_TYPE_EOS);
                //链上转账请求失败
                if (result.getCode() == 0) {
                    return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_1, MessageRepresentation.DWALLET_TRANSFER_CODE_0_STATUS_1, null);
                }
                //请求成功则记录一笔交易记录
                commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_SUCCESS, addressFrom, addressTo, tokenAmount);
                break;
            //转入bgs钱包
            case CodeRepresentation.TOKENTYPE_BGS:
                Ethtoken bgstoken = (Ethtoken) commonService.getToken(UID, tokenType);
                addressFrom = bgstoken.getEthaddress();
                //链上请求
                result = commonService.ETHTransfer(UID, tokenAmount, gasPrice, addressFrom, addressTo, CodeRepresentation.ETH_TOKEN_TYPE_BGS);
                //链上转账请求失败
                if (result.getCode() == 0) {
                    return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_1, MessageRepresentation.DWALLET_TRANSFER_CODE_0_STATUS_1, null);
                }
                //请求成功则记录一笔交易记录
                commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_SUCCESS, addressFrom, addressTo, tokenAmount);
                break;
        }
        return new SuperResult(CodeRepresentation.CODE_SUCCESS, CodeRepresentation.STATUS_0, MessageRepresentation.DWALLET_TRANSFER_CODE_1_STATUS_0, null);
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
    public SuperResult lock(String UID, Integer tokenType, Double tokenAmount, Double gasPrice, Integer period) {
        CommonWalletInfo walletInfo = commonService.getMappingDAndCWalletInfo(UID, tokenType);
        //余额不足 直接失败
        if (walletInfo.getBalance() < tokenAmount) {
            return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, MessageRepresentation.DWALLET_TRANSFER_CODE_0_STATUS_0, null);
        }
        String addressFrom, addressTo;
        SuperResult result;
        Double amount;
        //转账类型 -- 链上转链上
        Byte transferType = CodeRepresentation.TRANSFER_TYPE_ON2ON;
        //转账币种
        Byte token = new Byte(tokenType + "");
        //锁仓状态
        int status = CodeRepresentation.LOCK_STAUTS_ONPROFIT;
        switch (tokenType) {
            //转入eth钱包
            case CodeRepresentation.TOKENTYPE_ETH:
                Ethtoken ethtoken = (Ethtoken) commonService.getToken(UID, tokenType);
                addressFrom = ethtoken.getEthaddress();
                addressTo = CodeRepresentation.SUPER_ETH;
                result = commonService.ETHTransfer(UID, tokenAmount, gasPrice, addressFrom, addressTo, CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                //链上转账请求失败
                if (result.getCode() == CodeRepresentation.CODE_FAIL) {
                    return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_1, MessageRepresentation.DWALLET_LOCK_CODE_0_STATUS_0, null);
                }
                //请求成功--生成1.锁仓记录 2.更新token表
                amount = ethtoken.getAmount() + tokenAmount;
                ethtoken.setAmount(amount);
                ethtokenMapper.updateByPrimaryKey(ethtoken);
                commonService.lockedRecord(UID, tokenType, period, tokenAmount, status);
                break;
            //转入eos钱包
            case CodeRepresentation.TOKENTYPE_EOS:
                Eostoken eostoken = (Eostoken) commonService.getToken(UID, tokenType);
                addressFrom = eostoken.getEosaccountname();
                addressTo = CodeRepresentation.SUPER_EOS;
                //链上请求
                result = commonService.EOSTransfer(UID, tokenAmount, addressFrom, addressTo, CodeRepresentation.EOS_TOKEN_TYPE_EOS);
                //链上转账请求失败
                if (result.getCode() == CodeRepresentation.CODE_FAIL) {
                    return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_1, MessageRepresentation.DWALLET_LOCK_CODE_0_STATUS_0, null);
                }
                //请求成功--生成1.锁仓记录 2.更新token表
                amount = eostoken.getAmount() + tokenAmount;
                eostoken.setAmount(amount);
                eostokenMapper.updateByPrimaryKey(eostoken);
                commonService.lockedRecord(UID, tokenType, period, tokenAmount, status);
                break;
            //转入bgs钱包
            case CodeRepresentation.TOKENTYPE_BGS:
                Ethtoken bgstoken = (Ethtoken) commonService.getToken(UID, tokenType);
                addressFrom = bgstoken.getEthaddress();
                addressTo = CodeRepresentation.SUPER_BGS;
                //链上请求
                result = result = commonService.ETHTransfer(UID, tokenAmount, gasPrice, addressFrom, addressTo, CodeRepresentation.ETH_TOKEN_TYPE_BGS);
                //链上转账请求失败
                if (result.getCode() == CodeRepresentation.CODE_FAIL) {
                    return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_1, MessageRepresentation.DWALLET_LOCK_CODE_0_STATUS_0, null);
                }
                //请求成功--生成1.锁仓记录 2.更新token表
                amount = bgstoken.getAmount() + tokenAmount;
                bgstoken.setAmount(amount);
                ethtokenMapper.updateByPrimaryKey(bgstoken);
                commonService.lockedRecord(UID, tokenType, period, tokenAmount, status);
                break;
        }
        return SuperResult.ok(MessageRepresentation.DWALLET_LOCK_CODE_1_STATUS_0);
    }

    /**
     * 质押和赎回EOS的CPU和网络资源
     *
     * @param UID
     * @param cpuAmount
     * @param netAmount
     * @param actionType
     */
    @Override
    public boolean buyOrSellEOSCPUNET(String UID, Double cpuAmount, Double netAmount, Integer actionType) {
        String resp;
        SuperResult result;
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(RequestParams.UID, UID);
        params.put(RequestParams.CPUAMOUNT, cpuAmount);
        params.put(RequestParams.NETAMOUNT, netAmount);
        params.put(RequestParams.ACTIONTYPE, actionType);
        resp = HttpUtil.post(CodeRepresentation.NODE_URL_EOS + CodeRepresentation.NODE_ACTION_EOS_NETCPU, params);
        result = JSON.parseObject(resp, SuperResult.class);
        //链上转账请求失败
        if (result.getCode() == 0) return false;
        return true;
    }

    /**
     * 质押或赎回RAM资源
     *
     * @param UID
     * @param ramAmount
     * @param actionType
     * @return
     */
    @Override
    public boolean buyOrSellRAM(String UID, Double ramAmount, Integer actionType) {
        String resp;
        SuperResult result;
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(RequestParams.UID, UID);
        params.put(RequestParams.RAMAMOUNT, ramAmount);
        params.put(RequestParams.ACTIONTYPE, actionType);
        resp = HttpUtil.post(CodeRepresentation.NODE_URL_EOS + CodeRepresentation.NODE_ACTION_EOS_RAM, params);
        result = JSON.parseObject(resp, SuperResult.class);
        //链上转账请求失败
        if (result.getCode() == 0) return false;
        return true;
    }

    /**
     * 展示用户EOS的基本信息
     *
     * @param UID
     * @return
     */
    @Override
    public ResponseDWalletEOSDetailInfo listEOSDetailInfo(String UID) {
        DecimalFormat format = new DecimalFormat("#.##");
        ResponseDWalletEOSDetailInfo eosInfo;
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(RequestParams.UID, UID);
        String eos_resp = HttpUtil.post(CodeRepresentation.NODE_URL_EOS + CodeRepresentation.NODE_ACTION_EOS_ACCOUNTINFO, params);
        SuperResult response_eos = JSON.parseObject(eos_resp, SuperResult.class);
        JSONObject eos_json = JSON.parseObject(response_eos.getData().toString());
        //-----开始设置EOS详细信息-----
        String balance = commonService.parseEOSJson(eos_json.getString("core_liquid_balance"));
        JSONObject mortgageEOS_json = JSON.parseObject(eos_json.getString("self_delegated_bandwidth"));
        String mortgageEOS_cpu = "0";
        String mortgageEOS_net = "0";
        if (mortgageEOS_json != null) {
            mortgageEOS_cpu = mortgageEOS_json.getString("cpu_weight");
            mortgageEOS_net = mortgageEOS_json.getString("net_weight");
        }
        JSONObject cpu_json = JSON.parseObject(eos_json.getString("cpu_limit"));
        JSONObject net_json = JSON.parseObject(eos_json.getString("net_limit"));
        String used_cpu = cpu_json.getString("used") + " ms";
        String total_cpu = cpu_json.getString("max") + " ms";
        String used_net = net_json.getString("used") + " KB";
        String total_net = net_json.getString("max") + " KB";
        //TODO CPU、NET、RAM价格
        String price_cpu = "0.2 EOS/ms/天";
        String price_net = "0.2 EOS/KB/天";
        String cpu_can_redemption = "0";
        String net_can_redemption = "0";
        String price_ram = "0";
        String used_ram = eos_json.getString("ram_usage") + " KB";
        String total_ram = eos_json.getString("ram_quota") + " KB";
        //TODO 全网RAM总量
        String used_ram_chain = "10MB";
        String total_ram_chain = "100MB";
        eosInfo = new ResponseDWalletEOSDetailInfo(
                mortgageEOS_cpu,
                used_cpu,
                total_cpu,
                mortgageEOS_net,
                used_net,
                total_net,
                balance,
                price_cpu,
                price_net,
                cpu_can_redemption,
                net_can_redemption,
                used_ram,
                total_ram,
                price_ram,
                used_ram_chain,
                total_ram_chain
        );
        //-----设置EOS链上钱包信息结束-----
        return eosInfo;
    }

    /**
     * 链上钱包详单展示
     *
     * @param UID
     * @param tokenType
     * @param type
     * @return
     */
    @Override
    public ResponseDWalletBill listDetailDWalletInfo(String UID, Integer tokenType, Integer type) {
        //根据货币类型拿到对应的地址、余额、锁仓余额
        CommonWalletInfo walletInfo = commonService.getMappingDAndCWalletInfo(UID, tokenType);
        //TODO 货币价格
        double tokenPrice = commonService.getTokenPriceByType(tokenType);
        TransferExample transferExample = new TransferExample();
        TransferExample.Criteria criteria = transferExample.createCriteria();
        criteria.andUidEqualTo(UID);
        criteria.andTokentypeEqualTo(new Byte(tokenType + ""));
        //TODO type条件查询定义
        List<Transfer> transfers = transferMapper.selectByExample(transferExample);
        int listCount = transfers.size();
        List<ResponseDWalletBillEntry> bills = new ArrayList<ResponseDWalletBillEntry>();
        String transferType, transferStatus, transferTime;
        double transferAmount, transferAmountToRMB;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //获取交易记录并封装
        for (Transfer row : transfers) {
            transferType = CodeRepresentation.TRANSFER_TYPE_MAPPING.get(row.getTransfertype());
            transferStatus = CodeRepresentation.TRANSFER_STATUS_MAPPING.get(row.getStatus());
            transferAmount = row.getAmount();
            transferAmountToRMB = tokenPrice * row.getAmount();
            transferTime = format.format(row.getCreatedtime());
            ResponseDWalletBillEntry bill = new ResponseDWalletBillEntry(transferType, transferStatus, transferAmount, transferAmountToRMB, transferTime);
            bills.add(bill);
        }
        //设置返回信息
        ResponseDWalletBill result = new ResponseDWalletBill(
                walletInfo.getTokenName(),
                walletInfo.getBalance(),
                walletInfo.getBalance() * tokenPrice,
                walletInfo.getLockedAmount(),
                walletInfo.getTokenAddress(),
                listCount,
                bills);
        return result;
    }

    /**
     * 展示钱包详细信息--资产模块
     *
     * @param UID
     * @return
     */
    @Override
    public ResponseDWalletAssets listAssets(String UID) {
        double allTokenAmountToRMB;
        int listCount;
        double eth_tokenPrice = 1.0;
        double eos_tokenPrice = 1.0;
        double bgs_tokenPrice = 1.0;
        CommonWalletInfo ethWalletInfo = commonService.getMappingDAndCWalletInfo(UID, CodeRepresentation.TOKENTYPE_ETH);
        CommonWalletInfo eosWalletInfo = commonService.getMappingDAndCWalletInfo(UID, CodeRepresentation.TOKENTYPE_EOS);
        CommonWalletInfo bgsWalletInfo = commonService.getMappingDAndCWalletInfo(UID, CodeRepresentation.TOKENTYPE_BGS);
        List<ResponseDWalletAssetsEntry> walletInfo = new ArrayList<ResponseDWalletAssetsEntry>();
        //TODO 收益计算
        ResponseDWalletAssetsEntry eth = new ResponseDWalletAssetsEntry(
                ethWalletInfo.getBalance(),
                ethWalletInfo.getBalance() * eth_tokenPrice,
                ethWalletInfo.getTokenName(),
                ethWalletInfo.getCanLock(),
                ethWalletInfo.getLockedAmount(),
                0
        );
        ResponseDWalletAssetsEntry bgs = new ResponseDWalletAssetsEntry(
                bgsWalletInfo.getBalance(),
                bgsWalletInfo.getBalance() * bgs_tokenPrice,
                bgsWalletInfo.getTokenName(),
                bgsWalletInfo.getCanLock(),
                bgsWalletInfo.getLockedAmount(),
                0
        );
        ResponseDWalletAssetsEntry eos = new ResponseDWalletAssetsEntry(
                eosWalletInfo.getBalance(),
                eosWalletInfo.getBalance() * eos_tokenPrice,
                eosWalletInfo.getTokenName(),
                eosWalletInfo.getCanLock(),
                eosWalletInfo.getLockedAmount(),
                0
        );
        walletInfo.add(eth);
        walletInfo.add(bgs);
        walletInfo.add(eos);
        allTokenAmountToRMB = eth.getTokenAmountToRMB() + bgs.getTokenAmountToRMB() + eos.getTokenAmountToRMB();
        listCount = walletInfo.size();
        ResponseDWalletAssets result = new ResponseDWalletAssets(allTokenAmountToRMB, listCount, walletInfo);
        return result;
    }

    /**
     * 锁仓订单列表展示
     *
     * @param UID
     * @param tokenType
     * @return
     */
    @Override
    public ResponseDWalletLockedOrder listOrders(String UID, Integer tokenType) {
        LockwarehouseExample lockwarehouseExample = new LockwarehouseExample();
        LockwarehouseExample.Criteria criteria = lockwarehouseExample.createCriteria();
        criteria.andUidEqualTo(UID);
        //TODO 根据货币类型条件查询
        if (tokenType != CodeRepresentation.TOKENTYPE_ALL)
            criteria.andTokentypeEqualTo(tokenType);
        lockwarehouseExample.setOrderByClause("createdTime DESC");
        List<Lockwarehouse> list = lockwarehouseMapper.selectByExample(lockwarehouseExample);
        int listCount = list.size();
        List<ResponseDWalletLockedOrderSimEntry> orders = new ArrayList<ResponseDWalletLockedOrderSimEntry>();
        if (list != null) {
            for (Lockwarehouse row : list) {
                ResponseDWalletLockedOrderEntry detailEntry = commonService.lockedOrderToEntry(row);
                orders.add(
                        new ResponseDWalletLockedOrderSimEntry(
                                detailEntry.getLID(),
                                detailEntry.getTokenName(),
                                detailEntry.getLockedOrderState(),
                                detailEntry.getLockedOrderInTimeProfit(),
                                detailEntry.getPeriod() - detailEntry.getLockedOrderLeftDay(),
                                detailEntry.getPeriod(),
                                detailEntry.getLockedOrderStartTime()
                        )
                );
            }
        }
        ResponseDWalletLockedOrder result = new ResponseDWalletLockedOrder(listCount, orders);
        return result;
    }

    /**
     * 获取某个锁仓订单的详情
     *
     * @param UID
     * @param LID
     * @return
     */
    @Override
    public ResponseDWalletLockedOrderEntry getOrder(String UID, String LID) {
        long orderId = Long.parseLong(LID);
        LockwarehouseKey lockwarehouseKey = new LockwarehouseKey(orderId, UID);
        Lockwarehouse row = lockwarehouseMapper.selectByPrimaryKey(lockwarehouseKey);
        //拿到锁仓详情
        ResponseDWalletLockedOrderEntry result = commonService.lockedOrderToEntry(row);
        return result;
    }

    @Override
    public SuperResult getOrRequestIdentity(String UID) {

        return null;
    }

    @Override
    public SuperResult identityFromPermissions(String UID) {
        return null;
    }

    @Override
    public SuperResult requestSignature(String UID) {
        return null;
    }

    @Override
    public SuperResult getOriginData(String UID) {
        return null;
    }
}
