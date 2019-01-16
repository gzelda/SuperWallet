package com.superwallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.superwallet.common.*;
import com.superwallet.mapper.*;
import com.superwallet.pojo.*;
import com.superwallet.response.ResponseCWalletSimProfitEntry;
import com.superwallet.response.ResponseDWalletLockedOrderEntry;
import com.superwallet.service.CommonService;
import com.superwallet.utils.HttpUtil;
import com.superwallet.utils.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 通用服务
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private TransferMapper transferMapper;

    @Autowired
    private WithdrawmoneyMapper withdrawmoneyMapper;

    @Autowired
    private LockwarehouseMapper lockwarehouseMapper;

    @Autowired
    private EthtokenMapper ethtokenMapper;

    @Autowired
    private EostokenMapper eostokenMapper;

    @Autowired
    private InviterMapper inviterMapper;

    @Autowired
    private UserbasicMapper userbasicMapper;

    @Autowired
    private JedisClient jedisClient;

    /**
     * 转账记录生成
     *
     * @param UID
     * @param transferType
     * @param tokenType
     * @param status
     * @param addressFrom
     * @param addressTo
     * @param tokenAmount
     * @return
     */
    @Override
    public boolean generateRecord(String UID, Byte transferType, Byte tokenType,
                                  Byte status, String addressFrom, String addressTo,
                                  Double tokenAmount) {
        //转账记录
        Transfer transfer = new Transfer();
        transfer.setUid(UID);
        transfer.setTransfertype(transferType);
        transfer.setTokentype(tokenType);
        transfer.setStatus(status);
        transfer.setSource(addressFrom);
        transfer.setDestination(addressTo);
        transfer.setCreatedtime(new Date());
        transfer.setAmount(tokenAmount);
        int rows = transferMapper.insert(transfer);
        if (rows == 0) return false;
        return true;
    }

    /**
     * 提現申請
     *
     * @param UID
     * @param WID
     * @param tokenType
     * @param status
     * @param tokenAmount
     */
    @Override
    public boolean withdrawRecord(String UID, String WID, Byte tokenType, Byte status, Double tokenAmount) {
        //写入提现申请表
        Withdrawmoney record = new Withdrawmoney();
        record.setUid(UID);
        record.setWid(WID);
        record.setCreatedtime(new Date());
        record.setTokentype(tokenType);
        record.setAmount(tokenAmount);
        record.setStatus(status);
        int rows = withdrawmoneyMapper.insert(record);
        if (rows == 0) return false;
        return true;
    }

    /**
     * 生成锁仓记录
     *
     * @param UID
     * @param tokenType
     * @param period
     * @param tokenAmount
     * @param status
     */
    @Override
    public boolean lockedRecord(String UID, Integer tokenType, Integer period, Double tokenAmount, Integer status) {
        Lockwarehouse lockwarehouse = new Lockwarehouse();
        lockwarehouse.setUid(UID);
        lockwarehouse.setCreatedtime(new Date());
        lockwarehouse.setPeriod(period);
        lockwarehouse.setAmount(tokenAmount);
        lockwarehouse.setStatus(status);
        lockwarehouse.setTokentype(tokenType);
        //TODO 锁仓利润币种
        lockwarehouse.setProfittokentype(CodeRepresentation.TOKENTYPE_BGS);
        int rows = lockwarehouseMapper.insert(lockwarehouse);
        if (rows == 0) return false;
        return true;
    }

    /**
     * 拿到ETH的JSON
     *
     * @param UID
     * @return
     */
    @Override
    public SuperResult getETHInfo(String UID) {
        HashMap<String, Object> eth_params = new HashMap<String, Object>();
        eth_params.put(RequestParams.UID, UID);
        eth_params.put(RequestParams.TYPE, 0);
        String eth_resp = HttpUtil.post(CodeRepresentation.NODE_URL_ETH + CodeRepresentation.NODE_ACTION_ETH_ACCOUNTINFO, eth_params);
        SuperResult result = JSON.parseObject(eth_resp, SuperResult.class);
        return result;
    }

    /**
     * 拿到EOS的JSON
     *
     * @param UID
     * @return
     */
    @Override
    public SuperResult getEOSInfo(String UID) {
        HashMap<String, Object> eos_params = new HashMap<String, Object>();
        eos_params.put("UID", UID);
        String eos_resp = HttpUtil.post(CodeRepresentation.NODE_URL_EOS + CodeRepresentation.NODE_ACTION_EOS_ACCOUNTINFO, eos_params);
        SuperResult result = JSON.parseObject(eos_resp, SuperResult.class);
        return result;
    }

    /**
     * 拿到ETH的详细信息
     *
     * @param UID
     * @return
     */
    @Override
    public ETHWalletInfo getETHDetailInfo(String UID) {
        ETHWalletInfo ethWalletInfo;
        SuperResult response_eth = getETHInfo(UID);
        if (response_eth.getCode() == CodeRepresentation.CODE_FAIL) {
            return null;
        }
        JSONObject eth_json = JSON.parseObject(response_eth.getData().toString());
        EthtokenKey ethTokenKey = new EthtokenKey();
        ethTokenKey.setUid(UID);
        ethTokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_ETH);
        Ethtoken eth = ethtokenMapper.selectByPrimaryKey(ethTokenKey);
        String eth_address = eth.getEthaddress();
        double eth_avaAmount = Double.parseDouble(eth_json.getString("bgsBalance"));
        double eth_lockedAmount = getLockedAmount(UID, CodeRepresentation.TOKENTYPE_ETH);
        double eth_amount = eth_avaAmount + eth_lockedAmount;
        double eth_price = 1.0;
        ethWalletInfo = new ETHWalletInfo(eth_address,
                eth_amount,
                eth_lockedAmount,
                eth_avaAmount,
                eth_price,
                eth.getCanlock());
        return ethWalletInfo;
    }

    /**
     * 拿到BGS的详细信息
     *
     * @param UID
     * @return
     */
    @Override
    public ETHWalletInfo getBGSDetailInfo(String UID) {
        ETHWalletInfo bgsWalletInfo;
        SuperResult result = getETHInfo(UID);
        if (result.getCode() == CodeRepresentation.CODE_FAIL) {
            return null;
        }
        JSONObject eth_json = JSON.parseObject(result.getData().toString());
        EthtokenKey bgsTokenKey = new EthtokenKey();
        bgsTokenKey.setUid(UID);
        bgsTokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_BGS);
        Ethtoken bgs = ethtokenMapper.selectByPrimaryKey(bgsTokenKey);
        String bgs_address = bgs.getEthaddress();
        double bgs_avaAmount = Double.parseDouble(eth_json.getString("bgsBalance"));
        double bgs_lockedAmount = getLockedAmount(UID, CodeRepresentation.TOKENTYPE_BGS);
        double bgs_amount = bgs_avaAmount + bgs_lockedAmount;
        double bgs_price = 1.0;
        bgsWalletInfo = new ETHWalletInfo(bgs_address,
                bgs_amount,
                bgs_lockedAmount,
                bgs_avaAmount,
                bgs_price,
                bgs.getCanlock());
        return bgsWalletInfo;
    }

    /**
     * 拿到EOS的详细信息
     *
     * @param UID
     * @return
     */
    @Override
    public EOSWalletInfo getEOSDetailInfo(String UID) {
        EOSWalletInfo eosWalletInfo;
        SuperResult result = getEOSInfo(UID);
        if (result.getCode() == CodeRepresentation.CODE_FAIL) {
            return null;
        }
        JSONObject eos_json = JSON.parseObject(result.getData().toString());
        EostokenKey eostokenKey = new EostokenKey();
        eostokenKey.setUid(UID);
        eostokenKey.setType(CodeRepresentation.EOS_TOKEN_TYPE_EOS);
        Eostoken eos = eostokenMapper.selectByPrimaryKey(eostokenKey);
        String eos_account = eos.getEosaccountname();
        double eos_avaAmount = Double.parseDouble(eos_json.getString("core_liquid_balance").split(" ")[0]);
        double eos_lockedAmount = getLockedAmount(UID, CodeRepresentation.TOKENTYPE_EOS);
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
        return eosWalletInfo;
    }

    /**
     * 拿到中心钱包所有基本信息--不包括链上钱包信息
     *
     * @param UID
     * @param tokenType
     * @return
     */
    @Override
    public CommonWalletInfo getMappingCWalletInfo(String UID, Integer tokenType) {
        CommonWalletInfo result = new CommonWalletInfo();
        result.setTokenType(tokenType);
        String tokenName, tokenAddress;
        double lockedAmount = 0, cWalletAmount = 0;
        int canLock = 0;
        //TODO 货币价值
        double tokenPrice;
        switch (tokenType) {
            case CodeRepresentation.TOKENTYPE_ETH://ETH
                tokenName = CodeRepresentation.ETH;
                EthtokenKey ethtokenKey = new EthtokenKey(UID, CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                Ethtoken ethtoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
                //拿地址
                tokenAddress = ethtoken.getEthaddress();
                //锁仓金额
                lockedAmount = getLockedAmount(UID, CodeRepresentation.TOKENTYPE_ETH);
                //是否可以锁仓
                canLock = ethtoken.getCanlock();
                //中心钱包余额
                cWalletAmount = ethtoken.getAmount();
                result.setCanLock(canLock);
                result.setcWalletAmount(cWalletAmount);
                result.setTokenAddress(tokenAddress);
                result.setTokenName(tokenName);
                result.setLockedAmount(lockedAmount);
                tokenPrice = getTokenPriceByType(CodeRepresentation.TOKENTYPE_ETH);
                result.setTokenPrice(tokenPrice);
                break;
            case CodeRepresentation.TOKENTYPE_EOS://EOS
                tokenName = CodeRepresentation.EOS;
                EostokenKey eostokenKey = new EostokenKey(UID, CodeRepresentation.EOS_TOKEN_TYPE_EOS);
                Eostoken eostoken = eostokenMapper.selectByPrimaryKey(eostokenKey);
                //拿地址
                tokenAddress = eostoken.getEosaccountname();
                //锁仓金额
                lockedAmount = getLockedAmount(UID, CodeRepresentation.TOKENTYPE_EOS);
                //是否可以锁仓
                canLock = eostoken.getCanlock();
                //中心钱包余额
                cWalletAmount = eostoken.getAmount();
                result.setCanLock(canLock);
                result.setcWalletAmount(cWalletAmount);
                result.setTokenAddress(tokenAddress);
                result.setTokenName(tokenName);
                result.setLockedAmount(lockedAmount);
                tokenPrice = getTokenPriceByType(CodeRepresentation.TOKENTYPE_EOS);
                result.setTokenPrice(tokenPrice);
                break;
            case CodeRepresentation.TOKENTYPE_BGS://BGS
                tokenName = CodeRepresentation.BGS;
                EthtokenKey bgstokenKey = new EthtokenKey(UID, CodeRepresentation.ETH_TOKEN_TYPE_BGS);
                Ethtoken bgstoken = ethtokenMapper.selectByPrimaryKey(bgstokenKey);
                //拿地址
                tokenAddress = bgstoken.getEthaddress();
                //锁仓金额
                lockedAmount = getLockedAmount(UID, CodeRepresentation.TOKENTYPE_BGS);
                //是否可以锁仓
                canLock = bgstoken.getCanlock();
                //中心钱包余额
                cWalletAmount = bgstoken.getAmount();
                result.setCanLock(canLock);
                result.setcWalletAmount(cWalletAmount);
                result.setTokenAddress(tokenAddress);
                result.setTokenName(tokenName);
                result.setLockedAmount(lockedAmount);
                tokenPrice = getTokenPriceByType(CodeRepresentation.TOKENTYPE_BGS);
                result.setTokenPrice(tokenPrice);
                break;
        }
        return result;
    }

    /**
     * 拿到中心钱包与链上钱包所有基本信息
     *
     * @param UID
     * @param tokenType
     * @return
     */
    @Override
    public CommonWalletInfo getMappingDAndCWalletInfo(String UID, Integer tokenType) {
        SuperResult superResult;
        CommonWalletInfo result = new CommonWalletInfo();
        result.setTokenType(tokenType);
        String tokenName, tokenAddress;
        double lockedAmount = 0, cWalletAmount = 0;
        int canLock = 0;
        double tokenPrice = 1.0;
        switch (tokenType) {
            case CodeRepresentation.TOKENTYPE_ETH://ETH
                tokenName = "ETH";
                EthtokenKey ethtokenKey = new EthtokenKey(UID, CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                Ethtoken ethtoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
                //拿地址
                tokenAddress = ethtoken.getEthaddress();
                //锁仓金额
                lockedAmount = getLockedAmount(UID, CodeRepresentation.TOKENTYPE_ETH);
                //是否可以锁仓
                canLock = ethtoken.getCanlock();
                //中心钱包余额
                cWalletAmount = ethtoken.getAmount();
                //拿链上钱包余额
                superResult = getETHInfo(UID);
                if (superResult.getCode() == CodeRepresentation.CODE_SUCCESS) {
                    JSONObject eth = JSON.parseObject(superResult.getData().toString());
                    result.setBalance(Double.parseDouble(eth.getString("ethBalance")));
                } else result.setBalance(1.0);
                result.setCanLock(canLock);
                result.setcWalletAmount(cWalletAmount);
                result.setTokenAddress(tokenAddress);
                result.setTokenName(tokenName);
                result.setLockedAmount(lockedAmount);
                tokenPrice = getTokenPriceByType(CodeRepresentation.TOKENTYPE_ETH);
                result.setTokenPrice(tokenPrice);
                break;
            case CodeRepresentation.TOKENTYPE_EOS://EOS
                tokenName = "EOS";
                EostokenKey eostokenKey = new EostokenKey(UID, CodeRepresentation.EOS_TOKEN_TYPE_EOS);
                Eostoken eostoken = eostokenMapper.selectByPrimaryKey(eostokenKey);
                //拿地址
                tokenAddress = eostoken.getEosaccountname();
                //锁仓金额
                lockedAmount = getLockedAmount(UID, CodeRepresentation.TOKENTYPE_EOS);
                //是否可以锁仓
                canLock = eostoken.getCanlock();
                //中心钱包余额
                cWalletAmount = eostoken.getAmount();
                //拿链上钱包余额
                superResult = getEOSInfo(UID);
                if (superResult.getCode() == CodeRepresentation.CODE_SUCCESS) {
                    JSONObject eos = JSON.parseObject(superResult.getData().toString());
                    double balance;
                    try {
                        balance = Double.parseDouble(eos.getString("core_liquid_balance").split(" ")[0]);
                    } catch (Exception e) {
                        balance = 0;
                    }
                    result.setBalance(balance);
                }
                result.setCanLock(canLock);
                result.setcWalletAmount(cWalletAmount);
                result.setTokenAddress(tokenAddress);
                result.setTokenName(tokenName);
                result.setLockedAmount(lockedAmount);
                tokenPrice = getTokenPriceByType(CodeRepresentation.TOKENTYPE_EOS);
                result.setTokenPrice(tokenPrice);
                break;
            case CodeRepresentation.TOKENTYPE_BGS://BGS
                tokenName = "BGS";
                EthtokenKey bgstokenKey = new EthtokenKey(UID, CodeRepresentation.ETH_TOKEN_TYPE_BGS);
                Ethtoken bgstoken = ethtokenMapper.selectByPrimaryKey(bgstokenKey);
                //拿地址
                tokenAddress = bgstoken.getEthaddress();
                //锁仓金额
                lockedAmount = getLockedAmount(UID, CodeRepresentation.TOKENTYPE_BGS);
                //是否可以锁仓
                canLock = bgstoken.getCanlock();
                //中心钱包余额
                cWalletAmount = bgstoken.getAmount();
                //拿链上钱包余额
                superResult = getETHInfo(UID);
                if (superResult.getCode() == CodeRepresentation.CODE_SUCCESS) {
                    JSONObject bgs = JSON.parseObject(superResult.getData().toString());
                    result.setBalance(Double.parseDouble(bgs.getString("bgsBalance")));
                }
                result.setCanLock(canLock);
                result.setcWalletAmount(cWalletAmount);
                result.setTokenAddress(tokenAddress);
                result.setTokenName(tokenName);
                result.setLockedAmount(lockedAmount);
                tokenPrice = getTokenPriceByType(CodeRepresentation.TOKENTYPE_BGS);
                result.setTokenPrice(tokenPrice);
                break;
        }
        return result;
    }

    /**
     * 将锁仓订单转换为对应类
     *
     * @param row
     * @return
     */
    @Override
    public ResponseDWalletLockedOrderEntry lockedOrderToEntry(Lockwarehouse row) {
        String LID, lockedOrderProfitTokenName, lockedOrderStartTime, lockedOrderEndTime, lockedOrderState, tokenName;
        double lockedOrderTodayProfitToRMB, lockedAmount, lockedOrderInTimeProfit;
        int period, lockedOrderLeftDay;
        LID = String.valueOf(row.getLid());
        lockedOrderProfitTokenName = CodeRepresentation.TOKENNAME_MAPPING.get(row.getProfittokentype());
        //TODO 收益计算与货币行情
        double tokenPrice = getTokenPriceByType(row.getTokentype());
        lockedOrderTodayProfitToRMB = (row.getFinalprofit() == null ? 0 : row.getFinalprofit()) * tokenPrice;
        lockedAmount = row.getAmount();
        lockedOrderInTimeProfit = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        lockedOrderStartTime = format.format(row.getCreatedtime());
        lockedOrderEndTime = format.format(row.getCreatedtime().getTime() + row.getPeriod() * 24 * 60 * 60 * 1000);
        period = row.getPeriod();
        lockedOrderLeftDay = period - (int) ((new Date()).getTime() - row.getCreatedtime().getTime()) / (1000 * 60 * 60 * 24);
        lockedOrderState = CodeRepresentation.LOCK_STATUS_MAPPING.get(row.getStatus());
        tokenName = CodeRepresentation.TOKENNAME_MAPPING.get(row.getTokentype());
        ResponseDWalletLockedOrderEntry result = new ResponseDWalletLockedOrderEntry(LID,
                lockedOrderProfitTokenName,
                lockedOrderTodayProfitToRMB,
                lockedAmount,
                lockedOrderInTimeProfit,
                lockedOrderStartTime,
                lockedOrderEndTime,
                period,
                lockedOrderLeftDay,
                lockedOrderState,
                tokenName
        );
        return result;
    }

    /**
     * 获取用户某个币种的锁仓金额
     *
     * @param tokenType
     * @return
     */
    @Override
    public double getLockedAmount(String UID, Integer tokenType) {
        LockwarehouseExample lockwarehouseExample = new LockwarehouseExample();
        LockwarehouseExample.Criteria criteria = lockwarehouseExample.createCriteria();
        criteria.andUidEqualTo(UID);
        criteria.andTokentypeEqualTo(tokenType);
        criteria.andStatusEqualTo(CodeRepresentation.LOCK_STAUTS_ONPROFIT);
        //获取状态为正在收益中的锁仓记录
        List<Lockwarehouse> list = lockwarehouseMapper.selectByExample(lockwarehouseExample);
        double result = 0;
        for (Lockwarehouse row : list) {
            result += row.getAmount();
        }
        return result;
    }

    /**
     * 拿到用户的邀请人总数--改为一级
     *
     * @param UID
     * @return
     */
    @Override
    public int getInvitingCount(String UID) {
        int result = 0;
        //拿到一级邀请人数
        InviterExample inviterExample = new InviterExample();
        InviterExample.Criteria criteria = inviterExample.createCriteria();
        criteria.andInviteridEqualTo(UID);
        List<Inviter> firstInvitingPeople = inviterMapper.selectByExample(inviterExample);
        if (firstInvitingPeople != null) {
            result += firstInvitingPeople.size();
            //拿到二级邀请人数 -- 已取消
//            for (Inviter inviter : firstInvitingPeople) {
//                inviterExample = new InviterExample();
//                criteria = inviterExample.createCriteria();
//                criteria.andInviteridEqualTo(inviter.getInviterid());
//                List<Inviter> secondInvitingPeople = inviterMapper.selectByExample(inviterExample);
//                if (secondInvitingPeople != null) result += secondInvitingPeople.size();
//            }
        }
        return result;
    }

    /**
     * 获得代理人下级人数
     *
     * @param UID
     * @return
     */
    @Override
    public int getAgentInvitingCount(String UID) {
        Userbasic user = userbasicMapper.selectByPrimaryKey(UID);
        //如果用户不是代理人，没有下级人数
        if (user.getStatus() != CodeRepresentation.USER_AGENT_ISAGENCY) {
            return 0;
        }
        int lowerCount = 0;
        InviterExample inviterExample = new InviterExample();
        InviterExample.Criteria criteria = inviterExample.createCriteria();
        criteria.andInviteridEqualTo(UID);
        List<Inviter> list = inviterMapper.selectByExample(inviterExample);
        if (list != null) lowerCount = list.size();
        return lowerCount;
    }

    /**
     * 获取代理人总数
     *
     * @return
     */
    @Override
    public int getAgentCount() {
        UserbasicExample userbasicExample = new UserbasicExample();
        UserbasicExample.Criteria criteria = userbasicExample.createCriteria();
        criteria.andIsagencyEqualTo(CodeRepresentation.USER_AGENT_ISAGENCY);
        List<Userbasic> list = userbasicMapper.selectByExample(userbasicExample);
        int result = 0;
        if (list != null) result = list.size();
        return result;
    }

    /**
     * 获取对应token的收益基本信息
     *
     * @param UID
     * @param tokenType
     * @return
     */
    @Override
    public ResponseCWalletSimProfitEntry getCWalletTokenProfit(String UID, Integer tokenType) {
        CommonWalletInfo walletInfo = getMappingCWalletInfo(UID, tokenType);
        ResponseCWalletSimProfitEntry entry = new ResponseCWalletSimProfitEntry();
        entry.setTokenName(walletInfo.getTokenName());
        entry.setTokenProfit(walletInfo.getcWalletAmount());
        entry.setTokenProfitToRMB(walletInfo.getcWalletAmount() * walletInfo.getTokenPrice());
        entry.setTokenType(tokenType);
        return entry;
    }

    /**
     * 根据货币种类初始化中心化钱包信息
     *
     * @param UID
     * @param address
     * @param tokenType
     */
    @Override
    @Transactional
    public boolean initToken(String UID, String address, Integer tokenType) {
        int rows;
        switch (tokenType) {
            case CodeRepresentation.TOKENTYPE_ETH://ETH
                Ethtoken ethtoken = new Ethtoken(UID, CodeRepresentation.ETH_TOKEN_TYPE_ETH, 0d, CodeRepresentation.TOKEN_CANNOTLOCK);
                ethtoken.setEthaddress(address);
                rows = ethtokenMapper.insert(ethtoken);
                if (rows == 0) {
                    try {
                        throw new Exception();
                    } catch (Exception e) {
                        System.out.println("初始化ETH钱包失败");
                        return false;
                    }
                }
                break;
            case CodeRepresentation.TOKENTYPE_EOS://EOS
                Eostoken eostoken = new Eostoken(UID, CodeRepresentation.EOS_TOKEN_TYPE_EOS, 0d, CodeRepresentation.TOKEN_CANNOTLOCK);
                eostoken.setEosaccountname(address);
                rows = eostokenMapper.insert(eostoken);
                if (rows == 0) {
                    try {
                        throw new Exception();
                    } catch (Exception e) {
                        System.out.println("初始化EOS钱包失败");
                        return false;
                    }
                }
                break;
            case CodeRepresentation.TOKENTYPE_BGS://BGS
                Ethtoken bgstoken = new Ethtoken(UID, CodeRepresentation.ETH_TOKEN_TYPE_BGS, 0d, CodeRepresentation.TOKEN_CANLOCK);
                bgstoken.setEthaddress(address);
                rows = ethtokenMapper.insert(bgstoken);
                if (rows == 0) {
                    try {
                        throw new Exception();
                    } catch (Exception e) {
                        System.out.println("初始化BGS钱包失败");
                        return false;
                    }
                }
                break;
        }
        return true;
    }

    /**
     * ETH链上转账
     *
     * @param UID
     * @param tokenAmount
     * @param gasPrice
     * @param fromAddress
     * @param toAddress
     * @param type
     * @return
     */
    @Override
    public SuperResult ETHTransfer(String UID, Double tokenAmount, Double gasPrice, String fromAddress, String toAddress, Integer type) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(RequestParams.UID, UID);
        params.put(RequestParams.AMOUNT, tokenAmount);
        params.put(RequestParams.FROMADDRESS, fromAddress);
        params.put(RequestParams.TOADDRESS, toAddress);
        params.put(RequestParams.TYPE, type);
        params.put(RequestParams.GASPRICE, gasPrice);
        String requestUrl = CodeRepresentation.NODE_URL_ETH + CodeRepresentation.NODE_ACTION_ETHTRANSFER;
        String resp = HttpUtil.post(requestUrl, params);
        SuperResult result = JSON.parseObject(resp, SuperResult.class);
        return result;
    }

    /**
     * EOS链上转账
     *
     * @param UID
     * @param tokenAmount
     * @param fromAddress
     * @param toAddress
     * @param type
     * @return
     */
    @Override
    public SuperResult EOSTransfer(String UID, Double tokenAmount, String fromAddress, String toAddress, Integer type, String memo) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(RequestParams.UID, UID);
        params.put(RequestParams.AMOUNT, tokenAmount);
        params.put(RequestParams.FROMACCOUNT, fromAddress);
        params.put(RequestParams.TOACCOUNT, toAddress);
        params.put(RequestParams.TYPE, type);
        params.put(RequestParams.MEMO, memo == null ? "" : memo);
        String requestUrl = CodeRepresentation.NODE_URL_EOS + CodeRepresentation.NODE_ACTION_EOSTRANSFER;
        String resp = HttpUtil.post(requestUrl, params);
        SuperResult result = JSON.parseObject(resp, SuperResult.class);
        return result;
    }

    /**
     * 根据币种拿到对应的货币类
     *
     * @param UID
     * @param tokenType
     * @return
     */
    @Override
    public Object getToken(String UID, Integer tokenType) {
        switch (tokenType) {
            case CodeRepresentation.TOKENTYPE_ETH://ETH
                EthtokenKey ethtokenKey = new EthtokenKey(UID, CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                Ethtoken ethtoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
                return ethtoken;
            case CodeRepresentation.TOKENTYPE_EOS://EOS
                EostokenKey eostokenKey = new EostokenKey(UID, CodeRepresentation.EOS_TOKEN_TYPE_EOS);
                Eostoken eostoken = eostokenMapper.selectByPrimaryKey(eostokenKey);
                return eostoken;
            case CodeRepresentation.TOKENTYPE_BGS://BGS
                EthtokenKey bgstokenKey = new EthtokenKey(UID, CodeRepresentation.ETH_TOKEN_TYPE_BGS);
                Ethtoken bgstoken = ethtokenMapper.selectByPrimaryKey(bgstokenKey);
                return bgstoken;
        }
        return null;
    }

    /**
     * 拿到货币行情价格
     *
     * @param tokenType
     * @return
     */
    @Override
    public double getTokenPriceByType(Integer tokenType) {
        double tokenPrice = 1.0;
        switch (tokenType) {
            case CodeRepresentation.TOKENTYPE_ETH://ETH
                try {
                    tokenPrice = Double.parseDouble(jedisClient.hget(CodeRepresentation.TOKENPRICE_KEY, CodeRepresentation.TOKENPRICE_ETH));
                } catch (Exception e) {
                    tokenPrice = 1.0;
                }
                break;
            case CodeRepresentation.TOKENTYPE_EOS://EOS
                try {
                    tokenPrice = Double.parseDouble(jedisClient.hget(CodeRepresentation.TOKENPRICE_KEY, CodeRepresentation.TOKENPRICE_EOS));
                } catch (Exception e) {
                    tokenPrice = 1.0;
                }
                break;
            case CodeRepresentation.TOKENTYPE_BGS://BGS
                try {
                    tokenPrice = Double.parseDouble(jedisClient.hget(CodeRepresentation.TOKENPRICE_KEY, CodeRepresentation.TOKENPRICE_BGS));
                } catch (Exception e) {
                    tokenPrice = 1.0;
                }
                break;
        }
        return tokenPrice;
    }

    /**
     * 解析EOS信息里面众多json的公有方法--判空用
     *
     * @param value
     * @return
     */
    @Override
    public String parseEOSJson(String value) {
        if (value == null) return "0";
        return value;
    }

    /**
     * 查询用户是否拥有EOS钱包
     *
     * @param UID
     * @return
     */
    @Override
    public boolean hasEOSWallet(String UID) {
        EostokenKey eostokenKey = new EostokenKey(UID, CodeRepresentation.EOS_TOKEN_TYPE_EOS);
        Eostoken eostoken = eostokenMapper.selectByPrimaryKey(eostokenKey);
        if (eostoken.getEosaccountname() == null || eostoken.getEosaccountname().equals("")) return false;
        return true;
    }

    /**
     * 分配EOS钱包
     *
     * @param UID
     * @return
     */
    @Override
    public SuperResult allocateEOSWallet(String UID) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(RequestParams.UID, UID);
        String requestUrl = CodeRepresentation.NODE_URL_EOS + CodeRepresentation.NODE_ACTION_ALLOCATEEOSWALLET;
        String resp = HttpUtil.post(requestUrl, params);
        SuperResult result = JSON.parseObject(resp, SuperResult.class);
        return result;
    }

    /**
     * 登录时如未分配钱包则分配钱包给用户
     *
     * @param UID
     * @return
     */
    @Override
    public boolean updateUserEOSWallet(String UID) {
        //先查询用户是否已分配钱包
        boolean hasEOSWallet = hasEOSWallet(UID);
        //如果分配则直接返回
        if (hasEOSWallet) {
            return true;
        }
        //如果没有分配并且还有多余EOS钱包则给用户分配
        else if (!hasEOSWallet && hasMoreEOSWallet()) {
            SuperResult result = allocateEOSWallet(UID);
            //分配失败的情况
            if (result.getCode() == CodeRepresentation.CODE_FAIL) {
                return false;
            }
            String eosAccountName = JSONObject.parseObject(result.getData().toString()).getString("accountName");
            if (eosAccountName == null || eosAccountName.equals("")) eosAccountName = "";
            boolean initEOS = initToken(UID, eosAccountName, CodeRepresentation.TOKENTYPE_EOS);
            if (initEOS) {
                return true;
            }
        }
        return false;
    }

    /**
     * 创建ETH账户
     *
     * @param UID
     * @return
     */
    @Override
    public SuperResult createETHAddress(String UID) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(RequestParams.UID, UID);
        String requestUrl = CodeRepresentation.NODE_URL_ETH + CodeRepresentation.NODE_ACTION_CREATEETH;
        String resp = HttpUtil.post(requestUrl, params);
        SuperResult result = JSON.parseObject(resp, SuperResult.class);
        return result;
    }

    /**
     * 查询是否有多余的EOS钱包
     *
     * @return
     */
    @Override
    public boolean hasMoreEOSWallet() {
        String count = jedisClient.get(CodeRepresentation.REDIS_REMAINEOSWALLET);
        if (count == null || count.equals("")) return false;
        if (Integer.parseInt(count) > 0)
            return true;
        return false;
    }

    /**
     * 从redis里拿到对应币种的最小转账金额
     *
     * @param tokenType
     * @return
     */
    @Override
    public double getMinTransferAmount(Integer tokenType) {
        double result = 0;
        switch (tokenType) {
            case CodeRepresentation.TOKENTYPE_ETH:
                try {
                    result = Double.parseDouble(jedisClient.hget(CodeRepresentation.REDIS_OPTCONF, CodeRepresentation.REDIS_MINI_TRANSFER_ETH));
                } catch (Exception e) {
                    result = DynamicParameters.MINI_TRANSFER_ETH;
                }
                break;
            case CodeRepresentation.TOKENTYPE_EOS:
                try {
                    result = Double.parseDouble(jedisClient.hget(CodeRepresentation.REDIS_OPTCONF, CodeRepresentation.REDIS_MINI_TRANSFER_EOS));
                } catch (Exception e) {
                    result = DynamicParameters.MINI_TRANSFER_EOS;
                }
                break;
            case CodeRepresentation.TOKENTYPE_BGS:
                try {
                    result = Double.parseDouble(jedisClient.hget(CodeRepresentation.REDIS_OPTCONF, CodeRepresentation.REDIS_MINI_TRANSFER_BGS));
                } catch (Exception e) {
                    result = DynamicParameters.MINI_TRANSFER_BGS;
                }
                break;
        }
        return result;
    }

    /**
     * 从redis中拿到提现最小额度
     *
     * @param tokenType
     * @return
     */
    @Override
    public double getMinWithdrawAmount(Integer tokenType) {
        double result = 0;
        switch (tokenType) {
            case CodeRepresentation.TOKENTYPE_ETH:
                try {
                    result = Double.parseDouble(jedisClient.hget(CodeRepresentation.REDIS_OPTCONF, CodeRepresentation.REDIS_MINI_WITHDRAW_ETH));
                } catch (Exception e) {
                    result = DynamicParameters.MINI_WITHDRAW_ETH;
                }
                break;
            case CodeRepresentation.TOKENTYPE_EOS:
                try {
                    result = Double.parseDouble(jedisClient.hget(CodeRepresentation.REDIS_OPTCONF, CodeRepresentation.REDIS_MINI_WITHDRAW_EOS));
                } catch (Exception e) {
                    result = DynamicParameters.MINI_WITHDRAW_EOS;
                }
                break;
            case CodeRepresentation.TOKENTYPE_BGS:
                try {
                    result = Double.parseDouble(jedisClient.hget(CodeRepresentation.REDIS_OPTCONF, CodeRepresentation.REDIS_MINI_WITHDRAW_BGS));
                } catch (Exception e) {
                    result = DynamicParameters.MINI_WITHDRAW_BGS;
                }
                break;
        }
        return result;
    }

    /**
     * 从redis中拿到锁仓最小额度
     *
     * @param tokenType
     * @return
     */
    @Override
    public double getMinLockAmount(Integer tokenType) {
        double result = 0;
        switch (tokenType) {
            case CodeRepresentation.TOKENTYPE_ETH:
                try {
                    result = Double.parseDouble(jedisClient.hget(CodeRepresentation.REDIS_OPTCONF, CodeRepresentation.REDIS_MINI_LOCK_ETH));
                } catch (Exception e) {
                    result = DynamicParameters.MINI_LOCK_ETH;
                }
                break;
            case CodeRepresentation.TOKENTYPE_EOS:
                try {
                    result = Double.parseDouble(jedisClient.hget(CodeRepresentation.REDIS_OPTCONF, CodeRepresentation.REDIS_MINI_LOCK_EOS));
                } catch (Exception e) {
                    result = DynamicParameters.MINI_LOCK_EOS;
                }
                break;
            case CodeRepresentation.TOKENTYPE_BGS:
                try {
                    result = Double.parseDouble(jedisClient.hget(CodeRepresentation.REDIS_OPTCONF, CodeRepresentation.REDIS_MINI_LOCK_BGS));
                } catch (Exception e) {
                    result = DynamicParameters.MINI_LOCK_BGS;
                }
                break;
        }
        return result;
    }

    /**
     * 向node端回收钱包请求
     *
     * @param UID
     * @return
     */
    @Override
    public SuperResult recycleWallet(String UID) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(RequestParams.UID, UID);
        String requestUrl = CodeRepresentation.NODE_URL_EOS + CodeRepresentation.NODE_ACTION_RECYCLEWALLET;
        String resp = HttpUtil.post(requestUrl, params);
        SuperResult result = JSON.parseObject(resp, SuperResult.class);
        return result;
    }

}
