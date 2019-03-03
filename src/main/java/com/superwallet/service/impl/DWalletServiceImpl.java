package com.superwallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.superwallet.common.*;
import com.superwallet.mapper.*;
import com.superwallet.pojo.*;
import com.superwallet.response.*;
import com.superwallet.service.CWalletService;
import com.superwallet.service.CommonService;
import com.superwallet.service.DWalletService;
import com.superwallet.utils.HttpUtil;
import com.superwallet.utils.JedisClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private UserbasicMapper userbasicMapper;

    @Autowired
    private InviterMapper inviterMapper;

    @Autowired
    private OptconfMapper optconfMapper;

    @Autowired
    private EthvalidationMapper ethvalidationMapper;

    private Logger logger = LogManager.getLogger(getClass().getName());

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
        //EOS钱包需要查询用户是否拥有EOS 单独处理
        if (commonService.hasEOSWallet(UID)) {
            CommonWalletInfo eos = commonService.getMappingDAndCWalletInfo(UID, CodeRepresentation.TOKENTYPE_EOS);
            //EOS信息
            list.add(
                    new ResponseDWalletSimpleInfo(
                            eos.getTokenType(),
                            eos.getTokenName(),
                            eos.getTokenAddress(),
                            eos.getTokenPrice(),
                            eos.getBalance() * eos.getTokenPrice(),
                            eos.getBalance()
                    )
            );
        }
        CommonWalletInfo bgs = commonService.getMappingDAndCWalletInfo(UID, CodeRepresentation.TOKENTYPE_BGS);
        //ETH信息
        list.add(
                new ResponseDWalletSimpleInfo(
                        eth.getTokenType(),
                        eth.getTokenName(),
                        eth.getTokenAddress(),
                        eth.getTokenPrice(),
                        eth.getBalance() * eth.getTokenPrice(),
                        eth.getBalance()
                )
        );
        //BGS信息
        list.add(
                new ResponseDWalletSimpleInfo(
                        bgs.getTokenType(),
                        bgs.getTokenName(),
                        bgs.getTokenAddress(),
                        bgs.getTokenPrice(),
                        bgs.getBalance() * bgs.getTokenPrice(),
                        bgs.getBalance()
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
     * @param memo
     * @return
     */
    @Override
    @Transactional
    public SuperResult transferMoney(String UID, Integer tokenType, Double tokenAmount, Double gasPrice, String addressTo, String memo) {
        //转账额度小于最小额，直接返回
        double minAmount = commonService.getMinTransferAmount(tokenType);
        if (tokenAmount < minAmount) {
            return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_2, MessageRepresentation.DWALLET_TRANSFER_CODE_0_STATUS_2, null);
        }
        CommonWalletInfo wallet = commonService.getMappingDAndCWalletInfo(UID, tokenType);
        //余额不足 直接返回
        if (wallet.getBalance() < tokenAmount) {
            return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, MessageRepresentation.DWALLET_TRANSFER_CODE_0_STATUS_0, null);
        }
        if (gasPrice == null) gasPrice = 0d;
        String addressFrom;
        String resp;
        SuperResult result;
        Byte transferType = CodeRepresentation.TRANSFER_TYPE_ON2ON;
        //转账币种
        Byte token = new Byte(tokenType + "");
        RecordResult res;
        boolean canGenETHValidationRecord;
        int nonce;
        String txHash;
        switch (tokenType) {
            //转入eth钱包
            case CodeRepresentation.TOKENTYPE_ETH:
                Ethtoken ethtoken = (Ethtoken) commonService.getToken(UID, tokenType);
                addressFrom = ethtoken.getEthaddress();
                result = commonService.ETHTransfer(UID, tokenAmount, gasPrice, addressFrom, addressTo, CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                //链上转账请求失败
                if (result.getCode() == CodeRepresentation.CODE_FAIL) {
                    return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_1, MessageRepresentation.DWALLET_TRANSFER_CODE_0_STATUS_1, null);
                }
                //拿到nonce和txHash
                try {
                    nonce = JSONObject.parseObject(result.getData().toString()).getInteger("nonce");
                    txHash = JSONObject.parseObject(result.getData().toString()).getString("txHash");
                } catch (Exception e) {
                    return new SuperResult(CodeRepresentation.CODE_ERROR, CodeRepresentation.STATUS_0, MessageRepresentation.ERROR_MSG, null);
                }
//                System.out.println(result.getData() + "\n=======" + result.getMsg());
                //请求成功则记录一笔交易记录
                res = commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_ONPROCESS, addressFrom, addressTo, tokenAmount);
                if (res.isGenerated()) {
//                    System.out.println(txHash + ":" + nonce);
                    canGenETHValidationRecord = commonService.canGenETHValidationRecord(UID, txHash, nonce);
                    if (!canGenETHValidationRecord) {
                        return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_1, MessageRepresentation.DWALLET_TRANSFER_CODE_0_STATUS_1, null);
                    }
                    commonService.genETHValidation(UID, res.getTransferId(), txHash, CodeRepresentation.ETH_VALIDATION_ON, nonce);
                }
                break;
            //转入eos钱包
            case CodeRepresentation.TOKENTYPE_EOS:
                Eostoken eostoken = (Eostoken) commonService.getToken(UID, tokenType);
                addressFrom = eostoken.getEosaccountname();
                //链上请求
                result = commonService.EOSTransfer(UID, tokenAmount, addressFrom, addressTo, CodeRepresentation.EOS_TOKEN_TYPE_EOS, memo);
                //链上转账请求失败
                if (result.getCode() == CodeRepresentation.CODE_FAIL) {
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
                if (result.getCode() == CodeRepresentation.CODE_FAIL) {
                    return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_1, MessageRepresentation.DWALLET_TRANSFER_CODE_0_STATUS_1, null);
                }
                //拿到nonce和txHash
                try {
                    nonce = JSONObject.parseObject(result.getData().toString()).getInteger("nonce");
                    txHash = JSONObject.parseObject(result.getData().toString()).getString("txHash");
                } catch (Exception e) {
                    return new SuperResult(CodeRepresentation.CODE_ERROR, CodeRepresentation.STATUS_0, MessageRepresentation.ERROR_MSG, null);
                }
                //请求成功则记录一笔交易记录
                res = commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_ONPROCESS, addressFrom, addressTo, tokenAmount);
                if (res.isGenerated()) {
                    canGenETHValidationRecord = commonService.canGenETHValidationRecord(UID, txHash, nonce);
                    if (!canGenETHValidationRecord) {
                        return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_1, MessageRepresentation.DWALLET_TRANSFER_CODE_0_STATUS_1, null);
                    }
                    commonService.genETHValidation(UID, res.getTransferId(), txHash, CodeRepresentation.ETH_VALIDATION_ON, nonce);
                }
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
        //判断锁仓额度是否小于最小额度
        double minAmount = commonService.getMinLockAmount(tokenType);
        if (tokenAmount < minAmount) {
            return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_2, MessageRepresentation.DWALLET_TRANSFER_CODE_0_STATUS_2, null);
        }
        if (gasPrice == null) gasPrice = 0d;
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
        boolean genLockedRecord;
        RecordResult res;
        boolean canGenETHValidationRecord;
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
                //请求成功--生成1.锁仓记录 2.更新token表 3.生成锁仓交易记录
                amount = ethtoken.getAmount() + tokenAmount;
                ethtoken.setAmount(amount);
                ethtokenMapper.updateByPrimaryKey(ethtoken);
                genLockedRecord = commonService.lockedRecord(UID, tokenType, period, tokenAmount, status);
                res = commonService.generateRecord(UID, CodeRepresentation.TRANSFER_TYPE_PAYLOCK, Byte.valueOf(tokenType + ""), CodeRepresentation.TRANSFER_ONPROCESS, ethtoken.getEthaddress(), CodeRepresentation.SUPER_ETH, tokenAmount);
                if (!genLockedRecord) {
                    System.out.println(UID + ":生成锁仓记录失败");
                }
                if (!res.isGenerated()) {
                    System.out.println(UID + ":生成锁仓付费交易记录失败");
                } else {
                    int nonce;
                    String txHash;
                    try {
                        nonce = JSONObject.parseObject(result.getData().toString()).getInteger("nonce");
                        txHash = JSONObject.parseObject(result.getData().toString()).getString("txHash");
                    } catch (Exception e) {
                        return new SuperResult(CodeRepresentation.CODE_ERROR, CodeRepresentation.STATUS_0, MessageRepresentation.ERROR_MSG, null);
                    }
                    canGenETHValidationRecord = commonService.canGenETHValidationRecord(UID, txHash, nonce);
                    if (!canGenETHValidationRecord) {
                        return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_1, MessageRepresentation.DWALLET_LOCK_CODE_0_STATUS_0, null);
                    }
                    commonService.genETHValidation(UID, res.getTransferId(), txHash, CodeRepresentation.ETH_VALIDATION_ON, nonce);
                }
                break;
            //转入eos钱包
            case CodeRepresentation.TOKENTYPE_EOS:
                Eostoken eostoken = (Eostoken) commonService.getToken(UID, tokenType);
                addressFrom = eostoken.getEosaccountname();
                addressTo = CodeRepresentation.SUPER_EOS;
                //链上请求
                result = commonService.EOSTransfer(UID, tokenAmount, addressFrom, addressTo, CodeRepresentation.EOS_TOKEN_TYPE_EOS, "锁仓");
                //链上转账请求失败
                if (result.getCode() == CodeRepresentation.CODE_FAIL) {
                    return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_1, MessageRepresentation.DWALLET_LOCK_CODE_0_STATUS_0, null);
                }
                //请求成功--生成1.锁仓记录 2.更新token表 3.生成锁仓交易记录
                amount = eostoken.getAmount() + tokenAmount;
                eostoken.setAmount(amount);
                eostokenMapper.updateByPrimaryKey(eostoken);
                genLockedRecord = commonService.lockedRecord(UID, tokenType, period, tokenAmount, status);
                res = commonService.generateRecord(UID, CodeRepresentation.TRANSFER_TYPE_PAYLOCK, Byte.valueOf(tokenType + ""), CodeRepresentation.TRANSFER_SUCCESS, eostoken.getEosaccountname(), CodeRepresentation.SUPER_EOS, tokenAmount);
                if (!genLockedRecord) {
                    System.out.println(UID + ":生成锁仓记录失败");
                }
                if (!res.isGenerated()) {
                    System.out.println(UID + ":生成锁仓付费交易记录失败");
                }
                break;
            //转入bgs钱包
            case CodeRepresentation.TOKENTYPE_BGS:
                Ethtoken bgstoken = (Ethtoken) commonService.getToken(UID, tokenType);
                addressFrom = bgstoken.getEthaddress();
                addressTo = CodeRepresentation.SUPER_BGS;
                //链上请求
                result = commonService.ETHTransfer(UID, tokenAmount, gasPrice, addressFrom, addressTo, CodeRepresentation.ETH_TOKEN_TYPE_BGS);
                //链上转账请求失败
                if (result.getCode() == CodeRepresentation.CODE_FAIL) {
                    return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_1, MessageRepresentation.DWALLET_LOCK_CODE_0_STATUS_0, null);
                }
                //请求成功--生成1.锁仓记录 2.更新token表 3.生成锁仓交易记录
                amount = bgstoken.getAmount() + tokenAmount;
                bgstoken.setAmount(amount);
                ethtokenMapper.updateByPrimaryKey(bgstoken);
                genLockedRecord = commonService.lockedRecord(UID, tokenType, period, tokenAmount, status);
                res = commonService.generateRecord(UID, CodeRepresentation.TRANSFER_TYPE_PAYLOCK, Byte.valueOf(tokenType + ""), CodeRepresentation.TRANSFER_ONPROCESS, bgstoken.getEthaddress(), CodeRepresentation.SUPER_BGS, tokenAmount);
                if (!genLockedRecord) {
                    System.out.println(UID + ":生成锁仓记录失败");
                }
                if (!res.isGenerated()) {
                    System.out.println(UID + ":生成锁仓付费交易记录失败");
                } else {
                    int nonce;
                    String txHash;
                    try {
                        nonce = JSONObject.parseObject(result.getData().toString()).getInteger("nonce");
                        txHash = JSONObject.parseObject(result.getData().toString()).getString("txHash");
                    } catch (Exception e) {
                        return new SuperResult(CodeRepresentation.CODE_ERROR, CodeRepresentation.STATUS_0, MessageRepresentation.ERROR_MSG, null);
                    }
                    canGenETHValidationRecord = commonService.canGenETHValidationRecord(UID, txHash, nonce);
                    if (!canGenETHValidationRecord) {
                        return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_1, MessageRepresentation.DWALLET_LOCK_CODE_0_STATUS_0, null);
                    }
                    commonService.genETHValidation(UID, res.getTransferId(), txHash, CodeRepresentation.ETH_VALIDATION_ON, nonce);
                }
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
        List<ResponseDWalletBillWalletEntry> wallets = new ArrayList<ResponseDWalletBillWalletEntry>();
        //EOS钱包需要特判
        if (commonService.hasEOSWallet(UID)) {
            CommonWalletInfo eos = commonService.getMappingDAndCWalletInfo(UID, CodeRepresentation.TOKENTYPE_EOS);
            ResponseDWalletBillWalletEntry entry = new ResponseDWalletBillWalletEntry(
                    eos.getTokenType(),
                    eos.getTokenName(),
                    eos.getTokenAddress(),
                    eos.getBalance(),
                    eos.getTokenPrice() * eos.getBalance(),
                    eos.getLockedAmount()
            );
            wallets.add(entry);
        }
        CommonWalletInfo eth = commonService.getMappingDAndCWalletInfo(UID, CodeRepresentation.TOKENTYPE_ETH);
        CommonWalletInfo bgs = commonService.getMappingDAndCWalletInfo(UID, CodeRepresentation.TOKENTYPE_BGS);
        ResponseDWalletBillWalletEntry ethWallet = new ResponseDWalletBillWalletEntry(
                eth.getTokenType(),
                eth.getTokenName(),
                eth.getTokenAddress(),
                eth.getBalance(),
                eth.getTokenPrice() * eth.getBalance(),
                eth.getLockedAmount()
        );
        ResponseDWalletBillWalletEntry bgsWallet = new ResponseDWalletBillWalletEntry(
                bgs.getTokenType(),
                bgs.getTokenName(),
                bgs.getTokenAddress(),
                bgs.getBalance(),
                bgs.getTokenPrice() * bgs.getBalance(),
                bgs.getLockedAmount()
        );
        wallets.add(ethWallet);
        wallets.add(bgsWallet);
        int listCount = 0;
        List<ResponseDWalletBillEntry> bills = new ArrayList<ResponseDWalletBillEntry>();
        //条件查询
        List<ResponseDWalletBillEntry> transferInfo = listDetailDWalletTransferInfo(UID, tokenType, type);
        bills.addAll(transferInfo);
        listCount = bills.size();
        //设置返回信息
        ResponseDWalletBill result = new ResponseDWalletBill(
                wallets,
                listCount,
                bills);
        return result;
    }

    /**
     * 将交易记录封装成对象并展示
     *
     * @param UID
     * @param tokenType
     * @param type
     * @return
     */
    @Override
    public List<ResponseDWalletBillEntry> listDetailDWalletTransferInfo(String UID, Integer tokenType, Integer type) {
        double tokenPrice = commonService.getTokenPriceByType(tokenType);
        TransferExample transferExample = new TransferExample();
        transferExample.setOrderByClause("createdTime DESC");
        TransferExample.Criteria criteria = transferExample.createCriteria();
        criteria.andUidEqualTo(UID);
        criteria.andTokentypeEqualTo(new Byte(tokenType + ""));
        List<Byte> offChainRecord = new ArrayList<>();
        offChainRecord.addAll(Arrays.asList(
                CodeRepresentation.TRANSFER_TYPE_REGISTERBGS,
                CodeRepresentation.TRANSFER_TYPE_INVITINGBGS,
                CodeRepresentation.TRANSFER_TYPE_LOCKPROFIT,
                CodeRepresentation.TRANSFER_TYPE_AGENTPROFIT,
                CodeRepresentation.TRANSFER_TYPE_WITHDRAW_FAIL,
                CodeRepresentation.TRANSFER_TYPE_WITHDRAW_OUT
        ));
        criteria.andTransfertypeNotIn(offChainRecord);
        ArrayList<Byte> list = new ArrayList<Byte>();
        switch (type) {
            case CodeRepresentation.LISTDETAILDWALLET_LOCK:
                list.add(CodeRepresentation.TRANSFER_TYPE_PAYLOCK);
//                list.add(CodeRepresentation.TRANSFER_TYPE_LOCKPROFIT);
                criteria.andTransfertypeIn(list);
                break;
            case CodeRepresentation.LISTDETAILDWALLET_GAME:
                list.add(CodeRepresentation.TRANSFER_TYPE_PAYGAME);
                criteria.andTransfertypeIn(list);
                break;
            case CodeRepresentation.LISTDETAILDWALLET_TRANSFEROUT:
                list.add(CodeRepresentation.TRANSFER_TYPE_ON2ON);
                list.add(CodeRepresentation.TRANSFER_TYPE_WITHDRAW_OUT);
                criteria.andTransfertypeIn(list);
                break;
            case CodeRepresentation.LISTDETAILDWALLET_AGENT:
                list.add(CodeRepresentation.TRANSFER_TYPE_BUYAGENT);
//                list.add(CodeRepresentation.TRANSFER_TYPE_AGENTPROFIT);
                criteria.andTransfertypeIn(list);
                break;
            case CodeRepresentation.LISTDETAILDWALLET_EOSSOURCE:
                list.add(CodeRepresentation.TRANSFER_TYPE_BUYEOSRAM);
                list.add(CodeRepresentation.TRANSFER_TYPE_BUYEOSCPU);
                list.add(CodeRepresentation.TRANSFER_TYPE_BUYEOSNET);
                criteria.andTransfertypeIn(list);
                break;
        }
        List<Transfer> transfers = transferMapper.selectByExample(transferExample);
        List<ResponseDWalletBillEntry> res = new ArrayList<ResponseDWalletBillEntry>();
        String transferType, transferTime;
        int transferStatus, isIncoming;
        double transferAmount, transferAmountToRMB;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取交易记录并封装
        for (Transfer row : transfers) {
            transferType = CodeRepresentation.TRANSFER_TYPE_MAPPING.get(row.getTransfertype());
            transferStatus = row.getStatus();
            isIncoming = commonService.isIncoming(row.getTransfertype());
            transferAmount = row.getAmount();
            transferAmountToRMB = tokenPrice * row.getAmount();
            transferTime = format.format(row.getCreatedtime());
            ResponseDWalletBillEntry bill = new ResponseDWalletBillEntry(transferType, transferStatus, transferAmount, transferAmountToRMB, transferTime, isIncoming);
            res.add(bill);
        }
        return res;
    }

    /**
     * 展示钱包详细信息--资产模块
     *
     * @param UID
     * @return
     */
    @Override
    public ResponseDWalletAssets listAssets(String UID) {
        double allTokenAmountToRMB = 0, totalProfitToRMB = 0;
        int listCount;
        long start, end;
//        start = System.currentTimeMillis();
        double eth_tokenPrice = commonService.getTokenPriceByType(CodeRepresentation.TOKENTYPE_ETH);
        double eos_tokenPrice = commonService.getTokenPriceByType(CodeRepresentation.TOKENTYPE_EOS);
        double bgs_tokenPrice = commonService.getTokenPriceByType(CodeRepresentation.TOKENTYPE_BGS);
//        end = System.currentTimeMillis();
//        logger.info("连接redis时间：" + (end - start));
//        System.out.println("连接redis时间：" + (end - start));
//        start = System.currentTimeMillis();
        CommonWalletInfo ethWalletInfo = commonService.getMappingDAndCWalletInfo(UID, CodeRepresentation.TOKENTYPE_ETH);
        CommonWalletInfo bgsWalletInfo = commonService.getMappingDAndCWalletInfo(UID, CodeRepresentation.TOKENTYPE_BGS);
//        end = System.currentTimeMillis();
//        logger.info("node请求时间：" + (end - start));
//        System.out.println("node请求时间：" + (end - start));
        List<ResponseDWalletAssetsEntry> walletInfo = new ArrayList<ResponseDWalletAssetsEntry>();
        //TODO 收益计算
        ResponseDWalletAssetsEntry eth = new ResponseDWalletAssetsEntry(
                CodeRepresentation.TOKENTYPE_ETH,
                ethWalletInfo.getBalance(),
                ethWalletInfo.getBalance() * eth_tokenPrice,
                ethWalletInfo.getTokenName(),
                ethWalletInfo.getCanLock(),
                ethWalletInfo.getLockedAmount(),
                0
        );
        ResponseDWalletAssetsEntry bgs = new ResponseDWalletAssetsEntry(
                CodeRepresentation.TOKENTYPE_BGS,
                bgsWalletInfo.getBalance(),
                bgsWalletInfo.getBalance() * bgs_tokenPrice,
                bgsWalletInfo.getTokenName(),
                bgsWalletInfo.getCanLock(),
                bgsWalletInfo.getLockedAmount(),
                0
        );
        //EOS钱包需要做特判
        if (commonService.hasEOSWallet(UID)) {
            CommonWalletInfo eosWalletInfo = commonService.getMappingDAndCWalletInfo(UID, CodeRepresentation.TOKENTYPE_EOS);
            ResponseDWalletAssetsEntry eos = new ResponseDWalletAssetsEntry(
                    CodeRepresentation.TOKENTYPE_EOS,
                    eosWalletInfo.getBalance(),
                    eosWalletInfo.getBalance() * eos_tokenPrice,
                    eosWalletInfo.getTokenName(),
                    eosWalletInfo.getCanLock(),
                    eosWalletInfo.getLockedAmount(),
                    0
            );
            walletInfo.add(eos);
            allTokenAmountToRMB += eos.getTokenAmountToRMB();
            totalProfitToRMB += eosWalletInfo.getcWalletAmount() * eos_tokenPrice;
        }
        walletInfo.add(eth);
        walletInfo.add(bgs);
        allTokenAmountToRMB = allTokenAmountToRMB + eth.getTokenAmountToRMB() + bgs.getTokenAmountToRMB();
        //获取收益等价RMB总额
        totalProfitToRMB += ethWalletInfo.getcWalletAmount() * eth_tokenPrice + bgsWalletInfo.getcWalletAmount() * bgs_tokenPrice;
        listCount = walletInfo.size();
        ResponseDWalletAssets result = new ResponseDWalletAssets(allTokenAmountToRMB, totalProfitToRMB, listCount, walletInfo);
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
        //根据货币类型条件查询
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
     * 展示待确认的锁仓订单--后台管理使用
     *
     * @return
     */
    @Override
    public List<Lockwarehouse> listOnOverOrders() {
        LockwarehouseExample lockwarehouseExample = new LockwarehouseExample();
        LockwarehouseExample.Criteria criteria = lockwarehouseExample.createCriteria();
        criteria.andStatusEqualTo(CodeRepresentation.LOCK_STATUS_ONOVER);
        List<Lockwarehouse> list = lockwarehouseMapper.selectByExample(lockwarehouseExample);
        return list;
    }

    /**
     * 解仓
     *
     * @param UID
     * @param LID
     * @return
     */
    @Override
    @Transactional
    public boolean lockOrdersConfirm(String UID, Long LID, Integer type) {
        LockwarehouseKey lockwarehouseKey = new LockwarehouseKey(LID, UID);
        Lockwarehouse order = lockwarehouseMapper.selectByPrimaryKey(lockwarehouseKey);
        //如果解仓失败，钱要退回，并且要生成一笔转账记录
        String addressFrom = "default", addressTo = "default";
        byte transferType = (type == CodeRepresentation.LOCKCONFIRM_FAIL ? CodeRepresentation.TRANSFER_TYPE_LOCKCONFIRM_FAIL : CodeRepresentation.TRANSFER_TYPE_LOCKPROFIT);
        double amount = (type == CodeRepresentation.LOCKCONFIRM_FAIL ? order.getAmount() : order.getAmount() + order.getFinalprofit());
        //根据币种拿到地址并且更新钱包余额
        switch (order.getTokentype()) {
            case CodeRepresentation.TOKENTYPE_ETH:
                Ethtoken ethtoken = (Ethtoken) commonService.getToken(UID, order.getTokentype());
                addressFrom = CodeRepresentation.SUPER_ETH;
                addressTo = ethtoken.getEthaddress();
                cWalletService.updateETHWalletAmount(UID, amount, CodeRepresentation.CWALLET_MONEY_INC);
                break;
            case CodeRepresentation.TOKENTYPE_EOS:
                Eostoken eostoken = (Eostoken) commonService.getToken(UID, order.getTokentype());
                addressFrom = CodeRepresentation.SUPER_EOS;
                addressTo = eostoken.getEosaccountname();
                cWalletService.updateEOSWalletAmount(UID, amount, CodeRepresentation.CWALLET_MONEY_INC);
                break;
            case CodeRepresentation.TOKENTYPE_BGS:
                Ethtoken bgstoken = (Ethtoken) commonService.getToken(UID, order.getTokentype());
                addressFrom = CodeRepresentation.SUPER_BGS;
                addressTo = bgstoken.getEthaddress();
                cWalletService.updateBGSWalletAmount(UID, amount, CodeRepresentation.CWALLET_MONEY_INC);
                break;
        }
        //生成交易记录
        RecordResult recordResult = commonService.generateRecord(
                UID,
                transferType,
                Byte.valueOf(order.getTokentype() + ""),
                CodeRepresentation.TRANSFER_SUCCESS,
                addressFrom,
                addressTo,
                amount);
        if (!recordResult.isGenerated()) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("解仓时生成转账记录失败");
            }
            return false;
        }
        //如果解仓失败，更新锁仓表状态
        if (type == CodeRepresentation.LOCKCONFIRM_FAIL) {
            order.setStatus(CodeRepresentation.LOCK_STATUS_FAIL);
            lockwarehouseMapper.updateByPrimaryKey(order);
        } else {
            order.setStatus(CodeRepresentation.LOCK_STATUS_FINISHED);
            lockwarehouseMapper.updateByPrimaryKey(order);
        }
        return true;
    }

    /**
     * 质押CPU
     *
     * @param UID
     * @return
     */
    @Override
    public SuperResult trxEOSCPU(String UID) {
        double TRX_CPU_USER;
        try {
            TRX_CPU_USER = Double.parseDouble(jedisClient.hget(CodeRepresentation.REDIS_OPTCONF, CodeRepresentation.REDIS_TRXCPU));
        } catch (Exception e) {
            TRX_CPU_USER = Double.parseDouble(optconfMapper.selectByPrimaryKey(CodeRepresentation.REDIS_TRXCPU).getConfvalue());
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(RequestParams.UID, UID);
        String requestUrl = CodeRepresentation.NODE_URL_EOS + CodeRepresentation.NODE_ACTION_EOS_CPU;
        String resp = HttpUtil.post(requestUrl, params);
        SuperResult result = JSON.parseObject(resp, SuperResult.class);
        RecordResult res;
        //如果成功，生成一笔交易记录
        if (result.getCode() == CodeRepresentation.CODE_SUCCESS) {
            Eostoken eostoken = eostokenMapper.selectByPrimaryKey(new EostokenKey(UID, CodeRepresentation.EOS_TOKEN_TYPE_EOS));
            res = commonService.generateRecord(UID,
                    CodeRepresentation.TRANSFER_TYPE_BUYEOSCPU,
                    (byte) CodeRepresentation.TOKENTYPE_EOS,
                    CodeRepresentation.TRANSFER_SUCCESS,
                    eostoken.getEosaccountname(),
                    CodeRepresentation.SUPER_EOS,
                    TRX_CPU_USER);
            if (!res.isGenerated()) {
                System.out.println("生成购买CPU交易记录失败");
            }
            result.setMsg(MessageRepresentation.DWALLET_TRXCPU_CODE_1_STATUS_0);
            return result;
        } else if (result.getCode() == CodeRepresentation.CODE_FAIL) {//质押CPU失败
            return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, MessageRepresentation.DWALLET_TRXCPU_CODE_0_STATUS_0, null);
        }
        return result;
    }

    /**
     * 质押NET
     *
     * @param UID
     * @return
     */
    @Override
    public SuperResult trxEOSNET(String UID) {
        double TRX_NET_USER;
        try {
            TRX_NET_USER = Double.parseDouble(jedisClient.hget(CodeRepresentation.REDIS_OPTCONF, CodeRepresentation.REDIS_TRXNET));
        } catch (Exception e) {
            TRX_NET_USER = Double.parseDouble(optconfMapper.selectByPrimaryKey(CodeRepresentation.REDIS_TRXNET).getConfvalue());
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(RequestParams.UID, UID);
        String requestUrl = CodeRepresentation.NODE_URL_EOS + CodeRepresentation.NODE_ACTION_EOS_NET;
        String resp = HttpUtil.post(requestUrl, params);
        SuperResult result = JSON.parseObject(resp, SuperResult.class);
        RecordResult res;
        //如果成功，生成一笔交易记录
        if (result.getCode() == CodeRepresentation.CODE_SUCCESS) {
            Eostoken eostoken = eostokenMapper.selectByPrimaryKey(new EostokenKey(UID, CodeRepresentation.EOS_TOKEN_TYPE_EOS));
            res = commonService.generateRecord(UID,
                    CodeRepresentation.TRANSFER_TYPE_BUYEOSNET,
                    (byte) CodeRepresentation.TOKENTYPE_EOS,
                    CodeRepresentation.TRANSFER_SUCCESS,
                    eostoken.getEosaccountname(),
                    CodeRepresentation.SUPER_EOS,
                    TRX_NET_USER);
            if (!res.isGenerated()) {
                System.out.println("生成购买NET交易记录失败");
            }
            result.setMsg(MessageRepresentation.DWALLET_TRXNET_CODE_1_STATUS_0);
            return result;
        } else if (result.getCode() == CodeRepresentation.CODE_FAIL) {
            return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, MessageRepresentation.DWALLET_TRXNET_CODE_0_STATUS_0, null);

        }
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

    /**
     * 购买代理人
     *
     * @param UID
     * @return
     */
    @Override
    @Transactional
    public SuperResult buyAgent(String UID) {
        RecordResult res;
        //如果用户没有EOS钱包 购买代理人失败
        if (!commonService.hasEOSWallet(UID)) {
            return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_2, MessageRepresentation.DONT_HAVE_EOSWALLET, null);
        }
        //开始购买代理人
        double PRICE_BUYAGENT, INVITING_BGS;
        try {
            PRICE_BUYAGENT = Double.parseDouble(jedisClient.hget(CodeRepresentation.REDIS_OPTCONF, CodeRepresentation.REDIS_PRICE_BUYAGENT));
            INVITING_BGS = Double.parseDouble(jedisClient.hget(CodeRepresentation.REDIS_OPTCONF, CodeRepresentation.REDIS_PROFIT_INVITING_BGS));
        } catch (Exception e) {
            PRICE_BUYAGENT = Double.parseDouble(optconfMapper.selectByPrimaryKey(CodeRepresentation.REDIS_PRICE_BUYAGENT).getConfvalue());
            INVITING_BGS = Double.parseDouble(optconfMapper.selectByPrimaryKey(CodeRepresentation.REDIS_PROFIT_INVITING_BGS).getConfvalue());
        }
        Userbasic user = userbasicMapper.selectByPrimaryKey(UID);
        if (user.getIsagency() == CodeRepresentation.USER_AGENT_ISAGENCY) {
            return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, MessageRepresentation.CWALLET_BUYAGENT_CODE_0_STATUS_0, null);
        }
        //拿到用户链上钱包余额
        CommonWalletInfo eostoken = commonService.getMappingDAndCWalletInfo(UID, CodeRepresentation.TOKENTYPE_EOS);
        //余额不足
        if (eostoken.getBalance() < PRICE_BUYAGENT) {
            return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_1, MessageRepresentation.CWALLET_BUYAGENT_CODE_0_STATUS_1, null);
        }
        //购买代理人
        SuperResult result = commonService.EOSTransfer(UID, PRICE_BUYAGENT, eostoken.getTokenAddress(), CodeRepresentation.SUPER_EOS, CodeRepresentation.EOS_TOKEN_TYPE_EOS, "购买代理人");
        //购买失败
        if (result.getCode() == CodeRepresentation.CODE_FAIL) {
            return new SuperResult(CodeRepresentation.CODE_FAIL, CodeRepresentation.STATUS_0, MessageRepresentation.CWALLET_BUYAGENT_CODE_0_STATUS_2, null);
        }
        //如果购买成功 1.设置用户为代理人 2.生成转账记录 3.使邀请该用户的人获得邀请BGS奖励
        //设置用户为代理人
        user.setIsagency(CodeRepresentation.USER_AGENT_ISAGENCY);
        userbasicMapper.updateByPrimaryKey(user);
        //转账记录
        res = commonService.generateRecord(UID,
                CodeRepresentation.TRANSFER_TYPE_BUYAGENT,
                Byte.valueOf(CodeRepresentation.TOKENTYPE_EOS + ""),
                CodeRepresentation.TRANSFER_SUCCESS,
                eostoken.getTokenAddress(),
                CodeRepresentation.SUPER_EOS,
                PRICE_BUYAGENT);
        if (!res.isGenerated()) {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println("生成购买代理人转账记录失败");
            }
        }
        //使邀请该用户的人获得邀请BGS奖励
        InviterExample inviterExample = new InviterExample();
        InviterExample.Criteria criteria = inviterExample.createCriteria();
        criteria.andBeinvitedidEqualTo(UID);
        List<Inviter> list = inviterMapper.selectByExample(inviterExample);
        if (list != null && list.size() != 0) {
            Inviter inviter = list.get(0);
            String inviterUID = inviter.getInviterid();
            //更新邀请用户者的钱包，给他BGS奖励
            cWalletService.updateBGSWalletAmount(inviterUID, INVITING_BGS, CodeRepresentation.CWALLET_MONEY_INC);
            //同时生成一笔交易记录
            EthtokenKey ethtokenKey = new EthtokenKey(inviterUID, CodeRepresentation.ETH_TOKEN_TYPE_BGS);
            Ethtoken ethtoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
            res = commonService.generateRecord(inviterUID,
                    CodeRepresentation.TRANSFER_TYPE_INVITINGBGS,
                    Byte.valueOf(CodeRepresentation.TOKENTYPE_BGS + ""),
                    CodeRepresentation.TRANSFER_SUCCESS,
                    CodeRepresentation.SUPER_BGS,
                    ethtoken.getEthaddress(),
                    INVITING_BGS);
            if (!res.isGenerated()) {
                try {
                    throw new Exception();
                } catch (Exception e) {
                    System.out.println("生成BGS奖励转账记录失败");
                }
            }
        }
        return SuperResult.ok(MessageRepresentation.CWALLET_BUYAGENT_CODE_1_STATUS_0);
    }

}
