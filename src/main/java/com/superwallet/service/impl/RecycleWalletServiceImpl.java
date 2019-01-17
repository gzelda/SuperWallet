package com.superwallet.service.impl;

import com.superwallet.common.CodeRepresentation;
import com.superwallet.common.CommonWalletInfo;
import com.superwallet.common.SuperResult;
import com.superwallet.mapper.EostokenMapper;
import com.superwallet.mapper.OptconfMapper;
import com.superwallet.mapper.UserbasicMapper;
import com.superwallet.pojo.Eostoken;
import com.superwallet.pojo.EostokenKey;
import com.superwallet.pojo.Userbasic;
import com.superwallet.pojo.UserbasicExample;
import com.superwallet.service.CommonService;
import com.superwallet.service.RecycleWalletService;
import com.superwallet.utils.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecycleWalletServiceImpl implements RecycleWalletService {

    @Autowired
    private UserbasicMapper userbasicMapper;

    @Autowired
    private EostokenMapper eostokenMapper;

    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private CommonService commonService;

    @Autowired
    private OptconfMapper optconfMapper;

    /**
     * 钱包回收
     *
     * @return
     */
    @Override
    public SuperResult recycleWallet() {
        //先找到所有不是会员的用户
        UserbasicExample userbasicExample = new UserbasicExample();
        UserbasicExample.Criteria criteria = userbasicExample.createCriteria();
        criteria.andIsagencyEqualTo(CodeRepresentation.USER_AGENT_NOTAGENCY);
        List<Userbasic> users = userbasicMapper.selectByExample(userbasicExample);
        List<String> recycleUsers = new ArrayList<String>();
        double recycleMinAmount;
        long recycleIntervalTime;
        try {
            recycleMinAmount = Double.parseDouble(jedisClient.hget(CodeRepresentation.REDIS_OPTCONF, CodeRepresentation.REDIS_RECYCLE_MIN_AMOUNT));
            recycleIntervalTime = Long.parseLong(jedisClient.hget(CodeRepresentation.REDIS_OPTCONF, CodeRepresentation.REDIS_RECYCLE_INTERVALTIME));
        } catch (Exception e) {
            recycleMinAmount = Double.parseDouble(optconfMapper.selectByPrimaryKey(CodeRepresentation.REDIS_RECYCLE_MIN_AMOUNT).getConfvalue());
            recycleIntervalTime = Long.parseLong(optconfMapper.selectByPrimaryKey(CodeRepresentation.REDIS_RECYCLE_INTERVALTIME).getConfvalue());
        }
        //转换成毫秒
        recycleIntervalTime = recycleIntervalTime * 60 * 60 * 1000;
        //拿到当前时间
        long time = System.currentTimeMillis();
        //遍历每个用户
        for (Userbasic user : users) {
            String uid = user.getUid();
            EostokenKey eostokenKey = new EostokenKey(uid, CodeRepresentation.EOS_TOKEN_TYPE_EOS);
            Eostoken eostoken = eostokenMapper.selectByPrimaryKey(eostokenKey);
            //如果这个用户已经没有EOS账户直接跳过
            if (eostoken.getEosaccountname() == null || eostoken.getEosaccountname().equals("")) continue;
            CommonWalletInfo wallet = commonService.getMappingDAndCWalletInfo(uid, CodeRepresentation.TOKENTYPE_EOS);
            //查看余额是否超过最小限额
            if (wallet.getBalance() >= recycleMinAmount) continue;
            //此时余额已低于回收额度
            //查看用户活跃间隔是否低于回收间隔
            try {
                long lastOp = Long.parseLong(jedisClient.get(CodeRepresentation.REDIS_PRE_LASTOP + uid));
                time -= lastOp;
                //满足回收条件
                if (time >= recycleIntervalTime) {
                    commonService.recycleWallet(uid);
                    recycleUsers.add(uid);
                }
            } catch (Exception e) {
                //如果解析不到redis数据，则不回收
                System.out.println("解析钱包回收key失败");
            }
        }
        return SuperResult.ok(recycleUsers);
    }
}
