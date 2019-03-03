package com.superwallet.jedis;

import com.superwallet.common.CodeRepresentation;
import com.superwallet.utils.SHA1;
import org.junit.Test;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class RedisInit {


    @Test
    public void initRedis() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(50);
        config.setMaxWaitMillis(3000);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        JedisShardInfo info = new JedisShardInfo("18.223.112.79", 6379);
        info.setPassword("bgsgameRedis");
        List<JedisShardInfo> list = new LinkedList<JedisShardInfo>();
        list.add(info);
        ShardedJedisPool pool = new ShardedJedisPool(config, list);
        ShardedJedis jedis = pool.getResource();
        //声明Connection对象
        Connection con;
        //驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        //URL指向要访问的数据库名mydata
        String url = "jdbc:mysql://18.217.113.241:3306/superwallet";
        //MySQL配置时的用户名
        String user = "root";
        //MySQL配置时的密码
        String password = "Superwallet";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
            System.out.println("mysql连接成功");
            Statement statement = con.createStatement();
            String sql = "select * from optconf";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String key = rs.getString("confName");
                String value = rs.getString("confValue");
                jedis.hset(CodeRepresentation.REDIS_OPTCONF, key, value);
            }
            System.out.println("配置redis结束");
            rs.close();
            con.close();
        } catch (Exception e) {
            System.out.println("连接异常");
        }
    }


    @Test
    public void getRedis() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(50);
        config.setMaxWaitMillis(3000);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        JedisShardInfo info = new JedisShardInfo("3.17.79.21", 6379);
        info.setPassword("tygavingavin");
        List<JedisShardInfo> list = new LinkedList<JedisShardInfo>();
        list.add(info);
        ShardedJedisPool pool = new ShardedJedisPool(config, list);
        ShardedJedis jedis = pool.getResource();
//        String restWallet = jedis.hget("operationCode", "PRICE_BUYAGENT");
//        String restWallet = jedis.get("restWallet");
//        System.out.println(restWallet);
//        jedis.set("restWallet","54");
//        String s = jedis.get("lastOp:b53037c1-d1b6-4314-b647-142d626bb0e8");
//        System.out.println(s);
        String encode = SHA1.encode("fd62bc0b-06fa-4ce8-965b-04aed4fa1e48");
        System.out.println(encode);
    }

}
