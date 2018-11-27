package com.yonyou.iuap.project.cache;

import org.springside.modules.nosql.redis.pool.JedisDirectPool;
import org.springside.modules.nosql.redis.pool.JedisPool;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;

/**
 * Created by XiongYi on 2018/11/20.
 */
public class RedisTest {


    
    public static void main(String[] args){

        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();

        String poolName="mypool";

        HostAndPort hostAndPort=new HostAndPort("localhost",6379);

        JedisPool jedisPool=new JedisDirectPool(poolName,hostAndPort,jedisPoolConfig);

        RedisTemplate redisTemplate=new RedisTemplate(jedisPool);

        List<String> result=redisTemplate.lrange("nbStationByCode",0,2);


        System.out.println(result);

    }
}
