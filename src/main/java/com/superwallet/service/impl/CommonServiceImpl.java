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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void generateRecord(String UID, Byte transferType, Byte tokenType,
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
        transferMapper.insert(transfer);
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
    public void withdrawRecord(String UID, String WID, Byte tokenType, Byte status, Double tokenAmount) {
        //写入提现申请表
        Withdrawmoney record = new Withdrawmoney();
        record.setUid(UID);
        record.setWid(WID);
        record.setCreatedtime(new Date());
        record.setTokentype(tokenType);
        record.setAmount(tokenAmount);
        record.setStatus(status);
        withdrawmoneyMapper.insert(record);
    }

    /**
     * 生成锁仓记录
     *
     * @param UID
     * @param period
     * @param tokenAmount
     * @param status
     */
    @Override
    public void lockedRecord(String UID, Integer period, Double tokenAmount, Integer status) {
        Lockwarehouse lockwarehouse = new Lockwarehouse();
        lockwarehouse.setUid(UID);
        lockwarehouse.setCreatedtime(new Date());
        lockwarehouse.setPeriod(period);
        lockwarehouse.setAmount(tokenAmount);
        lockwarehouse.setStatus(status);
        lockwarehouseMapper.insert(lockwarehouse);
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
        eth_params.put("UID", UID);
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
        switch (tokenType) {
            case 1://ETH
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
                result.setCanLock(canLock);
                result.setcWalletAmount(cWalletAmount);
                result.setTokenAddress(tokenAddress);
                result.setTokenName(tokenName);
                result.setLockedAmount(lockedAmount);
                break;
            case 2://EOS
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
                result.setCanLock(canLock);
                result.setcWalletAmount(cWalletAmount);
                result.setTokenAddress(tokenAddress);
                result.setTokenName(tokenName);
                result.setLockedAmount(lockedAmount);
                break;
            case 3://BGS
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
                result.setCanLock(canLock);
                result.setcWalletAmount(cWalletAmount);
                result.setTokenAddress(tokenAddress);
                result.setTokenName(tokenName);
                result.setLockedAmount(lockedAmount);
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
        switch (tokenType) {
            case 1://ETH
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
                }
                result.setCanLock(canLock);
                result.setcWalletAmount(cWalletAmount);
                result.setTokenAddress(tokenAddress);
                result.setTokenName(tokenName);
                result.setLockedAmount(lockedAmount);
                break;
            case 2://EOS
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
                superResult = getETHInfo(UID);
                if (superResult.getCode() == CodeRepresentation.CODE_SUCCESS) {
                    JSONObject eos = JSON.parseObject(superResult.getData().toString());
                    result.setBalance(Double.parseDouble(eos.getString("core_liquid_balance").split(" ")[0]));
                }
                result.setCanLock(canLock);
                result.setcWalletAmount(cWalletAmount);
                result.setTokenAddress(tokenAddress);
                result.setTokenName(tokenName);
                result.setLockedAmount(lockedAmount);
                break;
            case 3://BGS
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
        double lockedOrderTodayProfitToRMB, lockedAmount, lockedOrderInTimeProfit, tokenPrice = 1.0;
        int period, lockedOrderLeftDay;
        LID = String.valueOf(row.getLid());
        //TODO 货币名称字典
        lockedOrderProfitTokenName = row.getProfittokentype() + "";
        //TODO 收益计算与货币行情
        lockedOrderTodayProfitToRMB = row.getFinalprofit() * tokenPrice;
        lockedAmount = row.getAmount();
        lockedOrderInTimeProfit = 0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        lockedOrderStartTime = format.format(row.getCreatedtime());
        lockedOrderEndTime = format.format(row.getCreatedtime().getTime() + row.getPeriod() * 24 * 60 * 60 * 1000);
        period = row.getPeriod();
        lockedOrderLeftDay = period - (int) ((new Date()).getTime() - row.getCreatedtime().getTime()) / (1000 * 60 * 60 * 24);
        //TODO 锁仓状态字典
        lockedOrderState = row.getStatus() + "";
        tokenName = row.getTokentype() + "";
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
     * 拿到用户的邀请人总数--二级
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
        //拿到二级邀请人数
        if (firstInvitingPeople != null) {
            result += firstInvitingPeople.size();
            for (Inviter inviter : firstInvitingPeople) {
                inviterExample = new InviterExample();
                criteria = inviterExample.createCriteria();
                criteria.andInviteridEqualTo(inviter.getInviterid());
                List<Inviter> secondInvitingPeople = inviterMapper.selectByExample(inviterExample);
                if (secondInvitingPeople != null) result += secondInvitingPeople.size();
            }
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
        return entry;
    }

}
