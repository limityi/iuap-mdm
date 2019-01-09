package com.yonyou.iuap.project.cache;

import java.util.Collections;

/**
 * redis工具类
 * Created by XiongYi on 2018/11/27.
 */
public class RedisUtil {

    /**
     * 锁过期时间
     */
    private static final int lock_timeout=120;

    private static final String lockName="lock";


    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * 获取同步时间
     * @param redisTemplate
     * @param fieldName
     * @param key
     * @return
     */
    public static String getSyncTime(RedisTemplate redisTemplate,String key,String fieldName){
        return String.valueOf(redisTemplate.hget(key,fieldName));
    }

    /**
     * 设置同步时间
     * @param redisTemplate
     * @param key
     * @param fieldName
     * @param value
     */
    public static void setSyncTime(RedisTemplate redisTemplate,String key,String fieldName,String value){
        redisTemplate.hset(key,fieldName,value);
    }

    /**
     * 尝试获取分布式锁
     * @param redisTemplate
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static boolean tryGetDistributedLock(RedisTemplate redisTemplate,String lockKey,String requestId,int expireTime) {
        return redisTemplate.setnxex(lockName+lockKey,requestId,expireTime);
    }

    /**
     * 释放分布式锁
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock(RedisTemplate redisTemplate, String lockKey, String requestId) {

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result=redisTemplate.eval(script, Collections.singletonList(lockName+lockKey), Collections.singletonList(requestId));
        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    public static int getLock_timeout() {
        return lock_timeout;
    }
}
