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
                        eth.getcWalletAmount() * eth.getTokenPrice()
                )
        );
        //EOS信息
        list.add(
                new ResponseDWalletSimpleInfo(
                        eos.getTokenType(),
                        eos.getTokenName(),
                        eos.getTokenAddress(),
                        eos.getcWalletAmount() * eos.getTokenPrice()
                )
        );
        //BGS信息
        list.add(
                new ResponseDWalletSimpleInfo(
                        bgs.getTokenType(),
                        bgs.getTokenName(),
                        bgs.getTokenAddress(),
                        bgs.getcWalletAmount() * bgs.getTokenPrice()
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
    public boolean transferMoney(String UID, Integer tokenType, Double tokenAmount, String addressTo, String description) {
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
                result = commonService.transferOnChain(UID, tokenAmount, addressFrom, addressTo, CodeRepresentation.ETH_TOKEN_TYPE_ETH, CodeRepresentation.TRANSFER_CHAIN_ETH);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
                //请求成功则记录一笔交易记录
                commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_SUCCESS, addressFrom, addressTo, tokenAmount);
                break;
            //转入eos钱包
            case CodeRepresentation.TOKENTYPE_EOS:
                Eostoken eostoken = (Eostoken) commonService.getToken(UID, tokenType);
                addressFrom = eostoken.getEosaccountname();
                //链上请求
                result = commonService.transferOnChain(UID, tokenAmount, addressFrom, addressTo, CodeRepresentation.EOS_TOKEN_TYPE_EOS, CodeRepresentation.TRANSFER_CHAIN_EOS);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
                //请求成功则记录一笔交易记录
                commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_SUCCESS, addressFrom, addressTo, tokenAmount);
                break;
            //转入bgs钱包
            case CodeRepresentation.TOKENTYPE_BGS:
                Ethtoken bgstoken = (Ethtoken) commonService.getToken(UID, tokenType);
                addressFrom = bgstoken.getEthaddress();
                //链上请求
                result = commonService.transferOnChain(UID, tokenAmount, addressFrom, addressTo, CodeRepresentation.ETH_TOKEN_TYPE_BGS, CodeRepresentation.TRANSFER_CHAIN_ETH);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
                //请求成功则记录一笔交易记录
                commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_SUCCESS, addressFrom, addressTo, tokenAmount);
                break;
        }
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
                result = commonService.transferOnChain(UID, tokenAmount, addressFrom, addressTo, CodeRepresentation.ETH_TOKEN_TYPE_ETH, CodeRepresentation.TRANSFER_CHAIN_ETH);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
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
                result = result = commonService.transferOnChain(UID, tokenAmount, addressFrom, addressTo, CodeRepresentation.EOS_TOKEN_TYPE_EOS, CodeRepresentation.TRANSFER_CHAIN_EOS);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
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
                result = result = commonService.transferOnChain(UID, tokenAmount, addressFrom, addressTo, CodeRepresentation.ETH_TOKEN_TYPE_BGS, CodeRepresentation.TRANSFER_CHAIN_ETH);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
                //请求成功--生成1.锁仓记录 2.更新token表
                amount = bgstoken.getAmount() + tokenAmount;
                bgstoken.setAmount(amount);
                ethtokenMapper.updateByPrimaryKey(bgstoken);
                commonService.lockedRecord(UID, tokenType, period, tokenAmount, status);
                break;
        }
        return true;
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
    public EOSWalletInfo listEOSBasic(String UID) {
        EOSWalletInfo eosWalletInfo;
        String resp;
        SuperResult result;
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(RequestParams.UID, UID);
        String eos_resp = HttpUtil.post(CodeRepresentation.NODE_URL_EOS + CodeRepresentation.NODE_ACTION_EOS_ACCOUNTINFO, params);
        SuperResult response_eos = JSON.parseObject(eos_resp, SuperResult.class);
        JSONObject eos_json = JSON.parseObject(response_eos.getData().toString());
        //-----开始设置EOS链上钱包信息-----
        //拿到EOS账户信息
        EostokenKey eostokenKey = new EostokenKey();
        eostokenKey.setUid(UID);
        eostokenKey.setType(CodeRepresentation.EOS_TOKEN_TYPE_EOS);
        Eostoken eos = eostokenMapper.selectByPrimaryKey(eostokenKey);
        String eos_account = eos.getEosaccountname();
        double eos_avaAmount = Double.parseDouble(eos_json.getString("core_liquid_balance").split(" ")[0]);
        double eos_lockedAmount = commonService.getLockedAmount(UID, CodeRepresentation.TOKENTYPE_EOS);
        double eos_amount = eos_avaAmount + eos_lockedAmount;
        double eos_price = 1.0;
        JSONObject mortgageEOS_json = JSON.parseObject(eos_json.getString("self_delegated_bandwidth"));
        double mortgageEOS_cpu = Double.parseDouble(mortgageEOS_json.getString("cpu_weight").split(" ")[0]);
        double mortgageEOS_net = Double.parseDouble(mortgageEOS_json.getString("net_weight").split(" ")[0]);
        JSONObject cpu_json = JSON.parseObject(eos_json.getString("cpu_limit"));
        JSONObject net_json = JSON.parseObject(eos_json.getString("net_limit"));
        double total_cpu = cpu_json.getDouble("max");
        double used_cpu = cpu_json.getDouble("used");
        double remain_cpu = cpu_json.getDouble("available");
        double total_net = net_json.getDouble("max");
        double used_net = net_json.getDouble("used");
        double remain_net = net_json.getDouble("available");
        double total_ram = eos_json.getDouble("ram_quota");
        double used_ram = eos_json.getDouble("ram_usage");
        //TODO 全网RAM总量
        double EOSRAM;
        double EOSRAM_USED;
        eosWalletInfo = new EOSWalletInfo(eos_amount, eos_lockedAmount, eos_avaAmount,
                eos_price, eos.getCanlock(), eos_account, mortgageEOS_cpu, mortgageEOS_net, total_cpu,
                total_net, total_ram, used_cpu, used_net, used_ram, remain_cpu, remain_net);
        //-----设置EOS链上钱包信息结束-----
        return eosWalletInfo;
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
        double tokenPrice = 1.0;
        TransferExample transferExample = new TransferExample();
        TransferExample.Criteria criteria = transferExample.createCriteria();
        criteria.andUidEqualTo(UID);
        criteria.andTokentypeEqualTo(new Byte(tokenType + ""));
        //TODO type条件查询定义
        List<Transfer> transfers = transferMapper.selectByExample(transferExample);
        int listCount = transfers.size();
        List<ResponseDWalletBillEntry> bills = new ArrayList<ResponseDWalletBillEntry>();
        //TODO 字段转译
        String transferType, lockedOrderState, transferTime;
        double transferAmount, transferAmountToRMB;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //获取交易记录并封装
        for (Transfer row : transfers) {
            transferType = row.getTransfertype() + "";
            lockedOrderState = row.getStatus() + "";
            transferAmount = row.getAmount();
            transferAmountToRMB = tokenPrice * row.getAmount();
            transferTime = format.format(row.getCreatedtime());
            ResponseDWalletBillEntry bill = new ResponseDWalletBillEntry(transferType, lockedOrderState, transferAmount, transferAmountToRMB, transferTime);
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
//        criteria.andTokentypeEqualTo(new Byte(tokenType + ""));
        lockwarehouseExample.setOrderByClause("createdTime DESC");
        List<Lockwarehouse> list = lockwarehouseMapper.selectByExample(lockwarehouseExample);
        int listCount = list.size();
        List<ResponseDWalletLockedOrderSimEntry> orders = new ArrayList<ResponseDWalletLockedOrderSimEntry>();
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
}
