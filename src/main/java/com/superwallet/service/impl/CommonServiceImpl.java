package com.superwallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.superwallet.common.CodeRepresentation;
import com.superwallet.common.EOSWalletInfo;
import com.superwallet.common.ETHWalletInfo;
import com.superwallet.common.SuperResult;
import com.superwallet.mapper.*;
import com.superwallet.pojo.*;
import com.superwallet.service.CommonService;
import com.superwallet.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

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
    public void lockedRecord(String UID, Integer period, Double tokenAmount, Byte status) {
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
    public JSONObject getETHInfo(String UID) {
        HashMap<String, Object> eth_params = new HashMap<String, Object>();
        eth_params.put("UID", UID);
        String eth_resp = HttpUtil.post(CodeRepresentation.NODE_URL_ETH + CodeRepresentation.NODE_ACTION_ETH_ACCOUNTINFO, eth_params);
        SuperResult response_eth = JSON.parseObject(eth_resp, SuperResult.class);
        if (response_eth.getCode() == CodeRepresentation.CODE_FAIL) {
            return null;
        }
        JSONObject eth_json = JSON.parseObject(response_eth.getData().toString());
        return eth_json;
    }

    /**
     * 拿到EOS的JSON
     *
     * @param UID
     * @return
     */
    @Override
    public JSONObject getEOSInfo(String UID) {
        HashMap<String, Object> eos_params = new HashMap<String, Object>();
        eos_params.put("UID", UID);
        String eos_resp = HttpUtil.post(CodeRepresentation.NODE_URL_EOS + CodeRepresentation.NODE_ACTION_EOS_ACCOUNTINFO, eos_params);
        SuperResult response_eos = JSON.parseObject(eos_resp, SuperResult.class);
        if (response_eos.getCode() == CodeRepresentation.CODE_FAIL) {
            return null;
        }
        JSONObject eos_json = JSON.parseObject(response_eos.getData().toString());
        return eos_json;
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
        JSONObject eth_json = getETHInfo(UID);
        EthtokenKey ethTokenKey = new EthtokenKey();
        ethTokenKey.setUid(UID);
        ethTokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_ETH);
        Ethtoken eth = ethtokenMapper.selectByPrimaryKey(ethTokenKey);
        String eth_address = eth.getEthaddress();
        double eth_avaAmount = Double.parseDouble(eth_json.getString("bgsBalance"));
        double eth_lockedAmount = eth.getLockedamount();
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
        JSONObject eth_json = getETHInfo(UID);
        EthtokenKey bgsTokenKey = new EthtokenKey();
        bgsTokenKey.setUid(UID);
        bgsTokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_BGS);
        Ethtoken bgs = ethtokenMapper.selectByPrimaryKey(bgsTokenKey);
        String bgs_address = bgs.getEthaddress();
        double bgs_avaAmount = Double.parseDouble(eth_json.getString("bgsBalance"));
        double bgs_lockedAmount = bgs.getLockedamount();
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
        JSONObject eos_json = getEOSInfo(UID);
        EostokenKey eostokenKey = new EostokenKey();
        eostokenKey.setUid(UID);
        eostokenKey.setType(CodeRepresentation.EOS_TOKEN_TYPE_EOS);
        Eostoken eos = eostokenMapper.selectByPrimaryKey(eostokenKey);
        String eos_account = eos.getEosaccountname();
        double eos_avaAmount = Double.parseDouble(eos_json.getString("core_liquid_balance").split(" ")[0]);
        double eos_lockedAmount = eos.getLockedamount();
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

}
