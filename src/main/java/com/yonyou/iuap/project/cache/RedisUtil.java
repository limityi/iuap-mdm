package com.yonyou.iuap.project.cache;

/**
 * redis工具类
 * Created by XiongYi on 2018/11/27.
 */
public class RedisUtil {

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
}
