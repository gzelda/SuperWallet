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
import com.superwallet.utils.CoinConvertUtils;
import com.superwallet.utils.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
     * 链上钱包详细信息
     *
     * @param UID
     * @return
     */
    @Override
    public List<BasicWalletInfo> listDetailWalletInfo(String UID) {
        //返回的对象
        EOSWalletInfo eosWalletInfo;
        ETHWalletInfo ethWalletInfo;
        ETHWalletInfo bgsWalletInfo;
        List<BasicWalletInfo> result = new ArrayList<BasicWalletInfo>();
        //远程请求参数
        HashMap<String, Object> eth_params = new HashMap<String, Object>();
        HashMap<String, Object> eos_params = new HashMap<String, Object>();
        eth_params.put("UID", UID);
        eos_params.put("UID", UID);
        String eth_resp = HttpUtil.post(CodeRepresentation.NODE_URL_ETH + CodeRepresentation.NODE_ACTION_ETH_ACCOUNTINFO, eth_params);
        String eos_resp = HttpUtil.post(CodeRepresentation.NODE_URL_EOS + CodeRepresentation.NODE_ACTION_EOS_ACCOUNTINFO, eos_params);
        SuperResult response_eth = JSON.parseObject(eth_resp, SuperResult.class);
        SuperResult response_eos = JSON.parseObject(eos_resp, SuperResult.class);
        JSONObject eth_json = JSON.parseObject(response_eth.getData().toString());
        JSONObject eos_json = JSON.parseObject(response_eos.getData().toString());
        //-----开始设置ETH链上钱包信息-----
        //拿到ETH地址信息
        EthtokenKey ethtokenKey = new EthtokenKey();
        ethtokenKey.setUid(UID);
        ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_ETH);
        Ethtoken eth = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
        String eth_address = eth.getEthaddress();
        double eth_avaAmount = Double.parseDouble(eth_json.getString("ethBalance"));
        double eth_lockedAmount = eth.getLockedamount();
        double eth_amount = eth_avaAmount + eth_lockedAmount;
        double eth_price = 1.0;
        ethWalletInfo = new ETHWalletInfo(eth_address,
                eth_amount,
                eth_lockedAmount,
                eth_avaAmount,
                eth_price,
                eth.getCanlock());
        //-----设置ETH链上钱包信息结束-----

        //-----开始设置BGS链上钱包信息-----
        //拿到ETH地址信息
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
        //-----设置BGS链上钱包信息结束-----

        //-----开始设置EOS链上钱包信息-----
        //拿到EOS账户信息
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
        //-----设置EOS链上钱包信息结束-----
        result.add(ethWalletInfo);
        result.add(bgsWalletInfo);
        result.add(eosWalletInfo);
        return result;
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
        EthtokenKey ethtokenKey = new EthtokenKey();
        EostokenKey eostokenKey = new EostokenKey();
        ethtokenKey.setUid(UID);
        eostokenKey.setUid(UID);
        String addressFrom;
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("UID", UID);
        params.put(RequestParams.TOADDRESS, addressTo);
        params.put(RequestParams.AMOUNT, tokenAmount);
        String resp;
        SuperResult result;
        //转账类型 -- 链上转链上
        Byte transferType = CodeRepresentation.CHAIN_ON2ON;
        //转账币种
        Byte token = new Byte(tokenType + "");
        switch (tokenType) {
            //转入eth钱包
            case 0:
                ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                Ethtoken ethtoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
                addressFrom = ethtoken.getEthaddress();
                params.put(RequestParams.FROMADDRESS, addressFrom);
                params.put(RequestParams.TYPE, CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                resp = HttpUtil.post(CodeRepresentation.NODE_URL_ETH + CodeRepresentation.NODE_ACTION_ETHTRANSFER, params);
                result = JSON.parseObject(resp, SuperResult.class);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
                //请求成功则记录一笔交易记录
                commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_SUCCESS, addressFrom, addressTo, tokenAmount);
                break;
            //转入eos钱包
            case 1:
                eostokenKey.setType(CodeRepresentation.EOS_TOKEN_TYPE_EOS);
                Eostoken eostoken = eostokenMapper.selectByPrimaryKey(eostokenKey);
                addressFrom = eostoken.getEosaccountname();
                //链上请求
                params.put(RequestParams.FROMADDRESS, addressFrom);
                params.put(RequestParams.TYPE, CodeRepresentation.EOS_TOKEN_TYPE_EOS);
                resp = HttpUtil.post(CodeRepresentation.NODE_URL_EOS + CodeRepresentation.NODE_ACTION_EOSTRANSFER, params);
                result = JSON.parseObject(resp, SuperResult.class);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
                //请求成功则记录一笔交易记录
                commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_SUCCESS, addressFrom, addressTo, tokenAmount);
                break;
            //转入bgs钱包
            case 2:
                ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_BGS);
                Ethtoken bgstoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
                addressFrom = bgstoken.getEthaddress();
                //链上请求
                params.put(RequestParams.FROMADDRESS, addressFrom);
                params.put(RequestParams.TYPE, CodeRepresentation.ETH_TOKEN_TYPE_BGS);
                resp = HttpUtil.post(CodeRepresentation.NODE_URL_ETH + CodeRepresentation.NODE_ACTION_ETHTRANSFER, params);
                result = JSON.parseObject(resp, SuperResult.class);
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
        EthtokenKey ethtokenKey = new EthtokenKey();
        EostokenKey eostokenKey = new EostokenKey();
        ethtokenKey.setUid(UID);
        eostokenKey.setUid(UID);
        String addressFrom, addressTo;
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(RequestParams.AMOUNT, tokenAmount);
        params.put(RequestParams.UID, UID);
        String resp;
        SuperResult result;
        Double amount, lockedAmount;
        //转账类型 -- 链上转链上
        Byte transferType = CodeRepresentation.CHAIN_ON2ON;
        //转账币种
        Byte token = new Byte(tokenType + "");
        //锁仓状态
        Byte status = CodeRepresentation.LOCK_ON;
        switch (tokenType) {
            //转入eth钱包
            case 0:
                addressTo = CodeRepresentation.SUPER_ETH;
                ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                Ethtoken ethtoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
                addressFrom = ethtoken.getEthaddress();
                addressTo = CodeRepresentation.SUPER_ETH;
                params.put(RequestParams.FROMADDRESS, addressFrom);
                params.put(RequestParams.TOADDRESS, addressTo);
                params.put(RequestParams.TYPE, CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                resp = HttpUtil.post(CodeRepresentation.NODE_URL_ETH + CodeRepresentation.NODE_ACTION_ETHTRANSFER, params);
                result = JSON.parseObject(resp, SuperResult.class);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
                //请求成功--生成1.锁仓记录 2.交易记录 3.更新token表
                amount = ethtoken.getAmount() + tokenAmount;
                lockedAmount = ethtoken.getLockedamount() + tokenAmount;
                ethtoken.setLockedamount(lockedAmount);
                ethtoken.setAmount(amount);
                ethtokenMapper.updateByPrimaryKey(ethtoken);
                commonService.lockedRecord(UID, period, tokenAmount, status);
                commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_SUCCESS, addressFrom, addressTo, tokenAmount);
                break;
            //转入eos钱包
            case 1:
                addressTo = CodeRepresentation.SUPER_EOS;
                eostokenKey.setType(CodeRepresentation.EOS_TOKEN_TYPE_EOS);
                Eostoken eostoken = eostokenMapper.selectByPrimaryKey(eostokenKey);
                addressFrom = eostoken.getEosaccountname();
                addressTo = CodeRepresentation.SUPER_EOS;
                //链上请求
                params.put(RequestParams.FROMADDRESS, addressFrom);
                params.put(RequestParams.TOADDRESS, addressTo);
                params.put(RequestParams.TYPE, CodeRepresentation.EOS_TOKEN_TYPE_EOS);
                resp = HttpUtil.post(CodeRepresentation.NODE_URL_EOS + CodeRepresentation.NODE_ACTION_EOSTRANSFER, params);
                result = JSON.parseObject(resp, SuperResult.class);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
                //请求成功--生成1.锁仓记录 2.交易记录 3.更新token表
                amount = eostoken.getAmount() + tokenAmount;
                lockedAmount = eostoken.getLockedamount() + tokenAmount;
                eostoken.setLockedamount(lockedAmount);
                eostoken.setAmount(amount);
                eostokenMapper.updateByPrimaryKey(eostoken);
                commonService.lockedRecord(UID, period, tokenAmount, status);
                commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_SUCCESS, addressFrom, addressTo, tokenAmount);
                break;
            //转入bgs钱包
            case 2:
                addressTo = CodeRepresentation.SUPER_BGS;
                ethtokenKey.setType(CodeRepresentation.ETH_TOKEN_TYPE_BGS);
                Ethtoken bgstoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
                addressFrom = bgstoken.getEthaddress();
                addressTo = CodeRepresentation.SUPER_BGS;
                //链上请求
                params.put(RequestParams.FROMADDRESS, addressFrom);
                params.put(RequestParams.TOADDRESS, addressTo);
                params.put(RequestParams.TYPE, CodeRepresentation.ETH_TOKEN_TYPE_BGS);
                resp = HttpUtil.post(CodeRepresentation.NODE_URL_ETH + CodeRepresentation.NODE_ACTION_ETHTRANSFER, params);
                result = JSON.parseObject(resp, SuperResult.class);
                //链上转账请求失败
                if (result.getCode() == 0) return false;
                //请求成功--生成1.锁仓记录 2.交易记录 3.更新token表
                amount = bgstoken.getAmount() + tokenAmount;
                lockedAmount = bgstoken.getLockedamount() + tokenAmount;
                bgstoken.setLockedamount(lockedAmount);
                bgstoken.setAmount(amount);
                ethtokenMapper.updateByPrimaryKey(bgstoken);
                commonService.lockedRecord(UID, period, tokenAmount, status);
                commonService.generateRecord(UID, transferType, token, CodeRepresentation.TRANSFER_SUCCESS, addressFrom, addressTo, tokenAmount);
                break;
        }
        return true;
    }

    /**
     * 锁仓订单列表展示
     *
     * @param UID
     * @param tokenType
     * @return
     */
    @Override
    public ResponseDWalletLockedOrder listOrders(String UID, int tokenType) {
        LockwarehouseExample lockwarehouseExample = new LockwarehouseExample();
        LockwarehouseExample.Criteria criteria = lockwarehouseExample.createCriteria();
        criteria.andUidEqualTo(UID);
        //TODO 根据货币类型条件查询
//        criteria.andTokentypeEqualTo(new Byte(tokenType + ""));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        lockwarehouseExample.setOrderByClause("createdTime DESC");
        List<Lockwarehouse> list = lockwarehouseMapper.selectByExample(lockwarehouseExample);
        int listCount = list.size();
        List<ResponseDWalletLockedOrderEntry> orders = new ArrayList<ResponseDWalletLockedOrderEntry>();
        for (Lockwarehouse row : list) {
            String dealId = String.valueOf(row.getLid());
            String coinName = CoinConvertUtils.type2nameMapping((int) row.getTokentype());
            String dealState = row.getStatus() + "";
            double dealLockCount = row.getAmount();
            //TODO 收益计算
            double dealInstanceProfit = 0;
            double dealTodayProfit = 0;
            int dealInstanceDay = (int) ((new Date()).getTime() - row.getCreatedtime().getTime()) / (1000 * 60 * 60 * 24);
            int dealTotalDay = row.getPeriod();
            int dealLeftDay = dealTotalDay - dealInstanceDay;
            String dealStartTime = format.format(row.getCreatedtime());
            String dealEndTime = format.format(row.getCreatedtime().getTime() + row.getPeriod() * 24 * 60 * 60 * 1000);
            String dealProfitType = CoinConvertUtils.type2nameMapping((int) row.getTokentype());
            orders.add(
                    new ResponseDWalletLockedOrderEntry(
                            dealId, coinName,
                            dealState, dealLockCount,
                            dealInstanceProfit, dealTodayProfit,
                            dealInstanceDay, dealTotalDay,
                            dealLeftDay, dealStartTime, dealEndTime, dealProfitType
                    )
            );
        }
        ResponseDWalletLockedOrder result = new ResponseDWalletLockedOrder(listCount, orders);
        return result;
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
        //-----设置EOS链上钱包信息结束-----
        return eosWalletInfo;
    }

    /**
     * 展示链上钱包基本信息
     *
     * @param UID
     * @return
     */
    @Override
    public List<ResponseDWalletSimpleInfo> listWalletInfo(String UID) {
        List<ResponseDWalletSimpleInfo> list = new ArrayList<ResponseDWalletSimpleInfo>();
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
        double price_eth = Double.parseDouble(eth_price.substring(1));
        double price_eos = Double.parseDouble(eos_price.substring(1));
        double price_bgs = 1.0;
        ResponseDWalletSimpleInfo ethInfo = new ResponseDWalletSimpleInfo(CodeRepresentation.COINTYPE_ETH, "ETH", ethtoken.getEthaddress() == null ? "default" : ethtoken.getEthaddress(),
                String.valueOf(price_eth));
        ethtoken.setType(CodeRepresentation.ETH_TOKEN_TYPE_BGS);
        Ethtoken bgstoken = ethtokenMapper.selectByPrimaryKey(ethtoken);
        ResponseDWalletSimpleInfo bgsInfo = new ResponseDWalletSimpleInfo(CodeRepresentation.COINTYPE_BGS, "BGS", ethtoken.getEthaddress() == null ? "default" : ethtoken.getEthaddress(),
                String.valueOf(price_bgs));
        EostokenKey eostokenKey = new EostokenKey();
        eostokenKey.setUid(UID);
        eostokenKey.setType(CodeRepresentation.EOS_TOKEN_TYPE_EOS);
        Eostoken eostoken = eostokenMapper.selectByPrimaryKey(eostokenKey);
        ResponseDWalletSimpleInfo eosInfo = new ResponseDWalletSimpleInfo(CodeRepresentation.COINTYPE_EOS, "EOS", ethtoken.getEthaddress() == null ? "default" : ethtoken.getEthaddress(),
                String.valueOf(price_eos));
        list.add(ethInfo);
        list.add(bgsInfo);
        list.add(eosInfo);
        return list;
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
    public ResponseDWalletBill listBills(String UID, Integer tokenType, Integer type) {
        String coinName = CoinConvertUtils.type2nameMapping(tokenType);
        //根据货币类型拿到对应的地址、余额、锁仓余额
        String coinAddress = "default";
        //TODO 货币价格
        double coinTotal = 0, lockCount = 0, coinRmbTotal, coinPrice = 1.0;
        switch (tokenType) {
            case 0://ETH
                //拿地址和锁仓余额
                EthtokenKey ethtokenKey = new EthtokenKey(UID, CodeRepresentation.ETH_TOKEN_TYPE_ETH);
                Ethtoken ethtoken = ethtokenMapper.selectByPrimaryKey(ethtokenKey);
                coinAddress = ethtoken.getEthaddress();
                lockCount = ethtoken.getLockedamount();
                //拿到链上钱包余额
                JSONObject eth_json = commonService.getETHInfo(UID);
                coinTotal = Double.parseDouble(eth_json.getString("ethBalance"));
                break;
            case 1://EOS
                //拿到地址和锁仓余额
                EostokenKey eostokenKey = new EostokenKey(UID, CodeRepresentation.EOS_TOKEN_TYPE_EOS);
                Eostoken eostoken = eostokenMapper.selectByPrimaryKey(eostokenKey);
                coinAddress = eostoken.getEosaccountname();
                lockCount = eostoken.getLockedamount();
                //拿到链上钱包余额
                JSONObject eos_json = commonService.getEOSInfo(UID);
                coinTotal = Double.parseDouble(eos_json.getString("core_liquid_balance").split(" ")[0]);
                break;
            case 2://BGS
                //拿地址和锁仓余额
                EthtokenKey bgstokenKey = new EthtokenKey(UID, CodeRepresentation.ETH_TOKEN_TYPE_BGS);
                Ethtoken bgstoken = ethtokenMapper.selectByPrimaryKey(bgstokenKey);
                coinAddress = bgstoken.getEthaddress();
                lockCount = bgstoken.getLockedamount();
                //拿到链上钱包余额
                JSONObject bgs_json = commonService.getETHInfo(UID);
                coinTotal = Double.parseDouble(bgs_json.getString("bgsBalance"));
                break;
        }
        coinRmbTotal = coinPrice * coinTotal;
        TransferExample transferExample = new TransferExample();
        TransferExample.Criteria criteria = transferExample.createCriteria();
        criteria.andUidEqualTo(UID);
        criteria.andTokentypeEqualTo(new Byte(tokenType + ""));
        //TODO type条件查询定义
        List<Transfer> transfers = transferMapper.selectByExample(transferExample);
        int listCount = transfers.size();
        List<ResponseDWalletBillEntry> bills = new ArrayList<ResponseDWalletBillEntry>();
        //TODO 字段转译
        String dealType, dealState, dealTime;
        double dealCount, dealRmbCount;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (Transfer row : transfers) {
            dealType = row.getTransfertype() + "";
            dealState = row.getStatus() + "";
            dealCount = row.getAmount();
            dealRmbCount = coinPrice * row.getAmount();
            dealTime = format.format(row.getCreatedtime());
            ResponseDWalletBillEntry bill = new ResponseDWalletBillEntry(dealType, dealState, dealCount, dealRmbCount, dealTime);
            bills.add(bill);
        }
        ResponseDWalletBill result = new ResponseDWalletBill(coinName, coinTotal, coinRmbTotal, lockCount, coinAddress, listCount, bills);
        return result;
    }

    /**
     * 展示钱包详细信息--资产模块
     *
     * @param UID
     * @return
     */
    @Override
    public ResponseDWalletAssets listDWalletInfo(String UID) {
        double allCoinTotal;
        int listCount;
        ETHWalletInfo ethWalletInfo = commonService.getETHDetailInfo(UID);
        ETHWalletInfo bgsWalletInfo = commonService.getBGSDetailInfo(UID);
        EOSWalletInfo eosWalletInfo = commonService.getEOSDetailInfo(UID);
        List<ResponseDWalletAssetsEntry> walletInfo = new ArrayList<ResponseDWalletAssetsEntry>();
        //TODO 收益计算
        ResponseDWalletAssetsEntry eth = new ResponseDWalletAssetsEntry(
                ethWalletInfo.getAvailableAmount(),
                ethWalletInfo.getAvailableAmount() * ethWalletInfo.getPrice(),
                "ETH",
                ethWalletInfo.getCanLock(),
                ethWalletInfo.getLockedAmount(),
                0
        );
        ResponseDWalletAssetsEntry bgs = new ResponseDWalletAssetsEntry(
                bgsWalletInfo.getAvailableAmount(),
                bgsWalletInfo.getAvailableAmount() * bgsWalletInfo.getPrice(),
                "BGS",
                bgsWalletInfo.getCanLock(),
                bgsWalletInfo.getLockedAmount(),
                0
        );
        ResponseDWalletAssetsEntry eos = new ResponseDWalletAssetsEntry(
                eosWalletInfo.getAvailableAmount(),
                eosWalletInfo.getAvailableAmount() * eosWalletInfo.getPrice(),
                "EOS",
                eosWalletInfo.getCanLock(),
                eosWalletInfo.getLockedAmount(),
                0
        );
        walletInfo.add(eth);
        walletInfo.add(bgs);
        walletInfo.add(eos);
        allCoinTotal = eth.getCoinRmbTotal() + bgs.getCoinRmbTotal() + eos.getCoinRmbTotal();
        listCount = walletInfo.size();
        ResponseDWalletAssets result = new ResponseDWalletAssets(allCoinTotal, listCount, walletInfo);
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取锁仓详情
        String dealId = String.valueOf(row.getLid());
        String coinName = CoinConvertUtils.type2nameMapping((int) row.getTokentype());
        String dealState = row.getStatus() + "";
        double dealLockCount = row.getAmount();
        //TODO 收益计算
        double dealInstanceProfit = 0;
        double dealTodayProfit = 0;
        int dealInstanceDay = (int) ((new Date()).getTime() - row.getCreatedtime().getTime()) / (1000 * 60 * 60 * 24);
        int dealTotalDay = row.getPeriod();
        int dealLeftDay = dealTotalDay - dealInstanceDay;
        String dealStartTime = format.format(row.getCreatedtime());
        String dealEndTime = format.format(row.getCreatedtime().getTime() + row.getPeriod() * 24 * 60 * 60 * 1000);
        String dealProfitType = CoinConvertUtils.type2nameMapping((int) row.getTokentype());
        ResponseDWalletLockedOrderEntry result = new ResponseDWalletLockedOrderEntry(
                dealId, coinName,
                dealState, dealLockCount,
                dealInstanceProfit, dealTodayProfit,
                dealInstanceDay, dealTotalDay,
                dealLeftDay, dealStartTime, dealEndTime, dealProfitType
        );
        return result;
    }
}
