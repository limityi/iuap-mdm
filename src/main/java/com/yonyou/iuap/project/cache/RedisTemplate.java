package com.yonyou.iuap.project.cache;

import org.springframework.stereotype.Repository;
import org.springside.modules.nosql.redis.JedisTemplate;
import org.springside.modules.nosql.redis.pool.JedisPool;
import redis.clients.jedis.Jedis;

/**
 * 自定义RedisTemplate
 * 继承org.springside.modules.nosql.redis.JedisTemplate,加入rpush()方法
 * Created by XiongYi on 2018/11/21.
 *
 */
@Repository
public class RedisTemplate extends JedisTemplate{

    public RedisTemplate(JedisPool jedisPool) {
        super(jedisPool);
    }

    public Long rpush(final  String key,final String... values){
        return execute(new JedisAction<Long>() {
            @Override
            public Long action(Jedis jedis) {
                return jedis.rpush(key,values);
            }
        });
    }

}
