package com.yonyou.iuap.project.cache;

import org.springside.modules.nosql.redis.pool.JedisDirectPool;
import org.springside.modules.nosql.redis.pool.JedisPool;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XiongYi on 2018/11/20.
 *
 */
public class RedisTest {


    
    public static void main(String[] args){

        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();

        String poolName="mypool";

        HostAndPort hostAndPort=new HostAndPort("localhost",6379);

        JedisPool jedisPool=new JedisDirectPool(poolName,hostAndPort,jedisPoolConfig);

        RedisTemplate redisTemplate=new RedisTemplate(jedisPool);

        Map<String,String> map=new HashMap<>();

        String name="limit";

        redisTemplate.rpush("station-"+name,"1"+name);
        redisTemplate.rpush("station-"+name,"2"+name);
        redisTemplate.rpush("station-"+name,"3"+name);

        map.put("1"+name,"apple");
        map.put("2"+name,"orange");
        map.put("3"+name,"banlanla");
        redisTemplate.hmset("station:limit",map);

        redisTemplate.hmset("station:limit1",map);

        List<String> result=redisTemplate.hmget("station:limit","1"+name,"2"+name);

        //redisTemplate.set

        System.out.println(result.toString());

    }
}
