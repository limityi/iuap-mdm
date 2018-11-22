package com.yonyou.iuap.station.cache;

import com.google.gson.Gson;
import com.yonyou.iuap.station.entity.NbStation;
import org.springside.modules.nosql.redis.pool.JedisDirectPool;
import org.springside.modules.nosql.redis.pool.JedisPool;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;

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

        //jedisTemplate.set("test:test","iuap-test");

        //redisTemplate.del("test");
        //redisTemplate.del("test:test");

        NbStation nbStation=new NbStation();
        nbStation.setId(UUID.randomUUID().toString());
        nbStation.setCode("001");
        nbStation.setName("广州站");

        NbStation nbStation2=new NbStation();
        nbStation2.setId(UUID.randomUUID().toString());
        nbStation2.setCode("002");
        nbStation2.setName("广州东站");

        List<NbStation> nbStations=new ArrayList<>();

        Gson gson=new Gson();

        nbStations.add(nbStation);
        nbStations.add(nbStation2);

        Map<String,List<NbStation>> stationMap=new HashMap<>();


        //jedisTemplate.hset("nbStationByCode","nbStation",gson.toJson(nbStation));
        //jedisTemplate.hset("nbStationByCode","nbStation2",gson.toJson(nbStation2));

        //jedisTemplate.lpush("nbStationByCode",gson.toJson(nbStation2));

        // jedisTemplate.zadd("nbStationByCode",1,gson.toJson(nbStation));
        //jedisTemplate.zadd("nbStationByCode",2,gson.toJson(nbStation2));

        redisTemplate.rpush("nbStationByCode",gson.toJson(nbStation));
        redisTemplate.rpush("nbStationByCode",gson.toJson(nbStation2));

        //System.out.println(jedisTemplate.llen("nbStationByCode").toString());

        List<String> result=redisTemplate.lrange("nbStationByCode",0,2);

        //Set<String> result=jedisTemplate.zrange("nbStationByCode",0,7);

        System.out.println(result);

    }
}
