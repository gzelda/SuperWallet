package com.superwallet.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

public class RedisInit {


    @Test
    public void initRedis() {
//        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxTotal(100);
//        config.setMaxIdle(50);
//        config.setMaxWaitMillis(3000);
//        config.setTestOnBorrow(true);
//        config.setTestOnReturn(true);
//        JedisShardInfo info = new JedisShardInfo("aws", 6379);
//        info.setPassword("root");
//        List<JedisShardInfo> list = new LinkedList<JedisShardInfo>();
//        list.add(info);
//        ShardedJedisPool pool = new ShardedJedisPool(config, list);
//        ShardedJedis jedis = pool.getResource();
        Jedis jedis = new Jedis("aws", 6379);
        //初始化开发人员专用数据信息
//        jedis.hset("developerCode", "CODE_FAIL", "0");
//        jedis.hset("developerCode", "CODE_SUCCESS", "1");
//        jedis.hset("developerCode", "CODE_ERROR", "2");
//        jedis.hset("developerCode", "STATUS_0", "0");
//        jedis.hset("developerCode", "STATUS_1", "1");
//        jedis.hset("developerCode", "STATUS_2", "2");
//        jedis.hset("developerCode", "STATUS_3", "3");
//        jedis.hset("developerCode", "USER_SEX_WOMAN", "0");
//        jedis.hset("developerCode", "USER_SEX_MAN", "1");
//        jedis.hset("developerCode", "USER_STATUS_NOIDVALIDATION", "1");
//        jedis.hset("developerCode", "USER_STATUS_VERIFIED", "2");
//        jedis.hset("developerCode", "USER_AGENT_NOTAGENCY", "0");
//        jedis.hset("developerCode", "USER_AGENT_ISAGENCY", "1");
//        jedis.hset("developerCode", "TOKEN_CANNOTLOCK", "0");
//        jedis.hset("developerCode", "TOKEN_CANLOCK", "1");
//        jedis.hset("developerCode", "TOKENTYPE_ETH", "1");
//        jedis.hset("developerCode", "TOKENTYPE_EOS", "2");
//        jedis.hset("developerCode", "TOKENTYPE_BGS", "3");
//        jedis.hset("developerCode", "ETH_TOKEN_TYPE_ETH", "0");
//        jedis.hset("developerCode", "ETH_TOKEN_TYPE_BGS", "1");
//        jedis.hset("developerCode", "EOS_TOKEN_TYPE_EOS", "0");
//        jedis.hset("developerCode", "TRANSFER_TYPE_ON2OFF", "1");
//        jedis.hset("developerCode", "TRANSFER_TYPE_ON2ON", "2");
//        jedis.hset("developerCode", "TRANSFER_TYPE_PAYLOCK", "3");
//        jedis.hset("developerCode", "TRANSFER_TYPE_PAYGAME", "4");
//        jedis.hset("developerCode", "TRANSFER_TYPE_BUYAGENT", "5");
//        jedis.hset("developerCode", "TRANSFER_TYPE_BUYEOSRAM", "6");
//        jedis.hset("developerCode", "TRANSFER_TYPE_BUYEOSCPUNET", "7");
//        jedis.hset("developerCode", "TRANSFER_TYPE_WITHDRAW", "8");
//        jedis.hset("developerCode", "TRANSFER_TYPE_REGISTERBGS", "9");
//        jedis.hset("developerCode", "TRANSFER_TYPE_INVITINGBGS", "10");
//        jedis.hset("developerCode", "TRANSFER_TYPE_LOCKPROFIT", "11");
//        jedis.hset("developerCode", "TRANSFER_TYPE_AGENTPROFIT", "12");
//        jedis.hset("developerCode", "LOCK_STAUTS_ONPROFIT", "1");
//        jedis.hset("developerCode", "LOCK_STATUS_ONOVER", "2");
//        jedis.hset("developerCode", "LOCK_STATUS_FINISHED", "3");
//        jedis.hset("developerCode", "PROFIT_STATUS_ONPROFIT", "0");
//        jedis.hset("developerCode", "PROFIT_STATUS_FINISHED", "1");
//        jedis.hset("developerCode", "PROFIT_NOTFINISHED", "0");
//        jedis.hset("developerCode", "PROFIT_FINISHED", "1");
//        jedis.hset("developerCode", "PROFIT_TYPE_LOCK", "1");
//        jedis.hset("developerCode", "PROFIT_TYPE_AGENT", "2");
//        jedis.hset("developerCode", "PROFIT_TYPE_WITHDRAW", "3");
//        jedis.hset("developerCode", "PROFIT_TYPE_INVITING", "4");
//        jedis.hset("developerCode", "ETHINFO", "ethInfo");
//        jedis.hset("developerCode", "EOSINFO", "eosInfo");
//        jedis.hset("developerCode", "BGSINFO", "bgsInfo");
//        jedis.hset("developerCode", "COUNT_WALLETS", "3");
//        jedis.hset("developerCode", "SESSION_EXPIRE", "1800");
//        jedis.hset("developerCode", "TOKEN_KEY", "superwallet_token");
//        jedis.hset("developerCode", "DEFAULT_ADDRESS", "default");
//        jedis.hset("developerCode", "SUPER_UID", "10000");
//        jedis.hset("developerCode", "TRANSFER_FAIL", "0");
//        jedis.hset("developerCode", "TRANSFER_SUCCESS", "1");
//        jedis.hset("developerCode", "WITHDRAW_WAIT", "1");
//        jedis.hset("developerCode", "WITHDRAW_SUCCESS", "2");
//        jedis.hset("developerCode", "WITHDRAW_FAIL", "3");
//        jedis.hset("developerCode", "CWALLET_MONEY_INC", "0");
//        jedis.hset("developerCode", "CWALLET_MONEY_DEC", "1");
//        jedis.hset("developerCode", "SUPER_ETH", "0x47B9Be7A0FC74Be3fccdECfC6d41d21D24D4a672");
//        jedis.hset("developerCode", "SUPER_BGS", "0x47B9Be7A0FC74Be3fccdECfC6d41d21D24D4a672");
//        jedis.hset("developerCode", "SUPER_EOS", "tygavingavin");
//        jedis.hset("developerCode", "NODE_URL_ETH", "http://localhost:4000");
//        jedis.hset("developerCode", "NODE_URL_EOS", "http://localhost:3000");
//        jedis.hset("developerCode", "NODE_ACTION_CREATEETH", "/eth/createAccount");
//        jedis.hset("developerCode", "NODE_ACTION_CREATEEOS", "/eos/createAccount");
//        jedis.hset("developerCode", "NODE_ACTION_ETHTRANSFER", "/eth/transfer");
//        jedis.hset("developerCode", "NODE_ACTION_EOSTRANSFER", "/eos/transfer");
//        jedis.hset("developerCode", "NODE_ACTION_EOS_NETCPU", "/eos/trxNetCpu");
//        jedis.hset("developerCode", "NODE_ACTION_EOS_RAM", "/eos/trxRam");
//        jedis.hset("developerCode", "NODE_ACTION_EOS_ACCOUNTINFO", "/eos/getAccount");
//        jedis.hset("developerCode", "NODE_ACTION_ETH_ACCOUNTINFO", "/eth/getBalance");
        //初始化业务人员专用信息
        jedis.hset("operationCode", "MINI_TRANSFER_ETH", "0");
        jedis.hset("operationCode", "MINI_TRANSFER_EOS", "0");
        jedis.hset("operationCode", "MINI_TRANSFER_BGS", "0");
        jedis.hset("operationCode", "MINI_LOCK_ETH", "0");
        jedis.hset("operationCode", "MINI_LOCK_EOS", "0");
        jedis.hset("operationCode", "MINI_LOCK_BGS", "0");
        jedis.hset("operationCode", "PROFITWEIGHT_30", "1");
        jedis.hset("operationCode", "PROFITWEIGHT_90", "1");
        jedis.hset("operationCode", "PROFIT_BGS_LOCK_07", "1");
        jedis.hset("operationCode", "PROFIT_BGS_LOCK_30", "2");
        jedis.hset("operationCode", "PROFIT_BGS_LOCK_90", "3");
        jedis.hset("operationCode", "MINI_WITHDRAW_ETH", "0");
        jedis.hset("operationCode", "MINI_WITHDRAW_EOS", "0");
        jedis.hset("operationCode", "MINI_WITHDRAW_BGS", "0");
        jedis.hset("operationCode", "PRICE_BUYAGENT_BGS", "2");
        jedis.hset("operationCode", "MAX_DAILY_INVITING_COUNT", "2");
        jedis.hset("operationCode", "PROFIT_INVITING_BGS", "50");
        jedis.hset("operationCode", "PROFIT_DAPP_LOCK", "0.1");
        jedis.hset("operationCode", "PROFIT_DAPP_AGENT", "0.2");
        jedis.hset("operationCode", "PROFIT_DAPP_PLATFORM", "0.1");
    }


}
