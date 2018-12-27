package com.superwallet.utils;

import redis.clients.jedis.*;

public class JedisTest {

    private static Jedis jedis;//非切片额客户端连接
    private static JedisPool jedisPool;//非切片连接池
    private static ShardedJedis shardedJedis;//切片额客户端连接
    private static ShardedJedisPool shardedJedisPool;//切片连接池

    public static void main(String[] args) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(5);
        jedisPool = new JedisPool(config, "192.168.0.77", 6379);
        jedis = jedisPool.getResource();
        jedis.set("CODE_ERROR", "2");
        jedis.close();
        jedisPool.close();
    }
}
