package com.swpu.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 向redis存值
     * @param key
     * @param value
     * @return
     */
    public boolean setValue(String key,Object value){
        try{
            redisTemplate.opsForValue().set(key, value);
            return true;
        }catch (Exception e){
            log.error("向redis存入值异常-->{}",e.getMessage());
            return false;
        }
    }

    /**
     * 存值并设置过期时间
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean setValueTime(String key,Object value,long time){
        try{
            if (time > 0){
                redisTemplate.opsForValue().set(key,value,time,TimeUnit.MINUTES);
            }else {
                redisTemplate.opsForValue().set(key,value);
            }
            return true;
        }catch (Exception e){
            log.error("设置缓存并指定过期时间异常-->{}",e.getMessage());
            return false;
        }
    }

    /**
     * 根据key获取redis的值
     * @param key
     * @return
     */
    public Object getValue(String key){
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 根据key删除值
     * @param keys
     */
    public void delKey(String... keys) {
        if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
                redisTemplate.delete(keys[0]);
            } else {
                for (int i = 0; i < keys.length; i++) {
                    redisTemplate.delete(keys[i]);
                }
            }
        }
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("redis值不存在-->{}",e.getMessage());
            return false;
        }
    }

    /**
     * 获取redis键的过期时间
     * 0代表永久取消
     * 大于0就剩多少分钟失效
     * @param key
     * @return
     */
    public long isExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.MINUTES);
    }


    /**
     * 给key设置过期时间
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key,long time){
        try{
            if (time > 0){
                redisTemplate.expire(key,time,TimeUnit.MINUTES);
            }
            return true;
        }catch (Exception e){
            log.error("给旧缓存设置新过期时间异常--> {}",e.getMessage());
            return false;
        }
    }

//    /**
//     * 指定缓存的时间
//     * @param key
//     * @param time
//     * @return
//     */
//    public boolean isExpire(String key, long time) {
//        try {
//            if (time > 0) {
//                redisTemplate.expire(key, time, TimeUnit.SECONDS);
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 获取过期时间 判断 key是否存在
//     * @param key
//     * @return 0 代表永久有效
//     */
//    public long getExpire(String key) {
//        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
//    }
//
//
//
//
//
//
//
//    /**
//     * 获取值
//     * @param key
//     * @return
//     */
//    public Object getObject(String key) {
//        return key == null ? null : redisTemplate.opsForValue().get(key);
//    }
//
//    /**
//     * 向缓存存值
//     * @param key
//     * @param value
//     * @return
//     */
//    public boolean setObject(String key, Object value) {
//        try {
//            redisTemplate.opsForValue().set(key, value);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 普通缓存存入并设置时间
//     * 如果time小于或等于0则设置无限期
//     */
//    public boolean setObjectTime(String key, Object value, long time){
//        try{
//            if (time > 0){
//                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
//            }else {
//                redisTemplate.opsForValue().set(key, value);
//            }
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 递增
//     * @param key
//     * @param data
//     * @return
//     */
//    public long incr(String key, long data) {
//        if (data < 0) {
//            throw new RuntimeException("递增因子必须大于0");
//        }
//        return redisTemplate.opsForValue().increment(key, data);
//    }
//
//    /**
//     * 递减
//     * key 键
//     * data 值 要减少几大于0
//     */
//    public long decr(String key, long data){
//        if (data < 0){
//            throw new RuntimeException("递减因子必须大于0");
//        }
//        return redisTemplate.opsForValue().decrement(key, -data);
//    }
//
//    /**
//     * 获取hashmap
//     * @param key
//     * @param item
//     * @return
//     */
//    public Object getHash(String key, String item) {
//        return redisTemplate.opsForHash().get(key, item);
//    }
//
//    /**
//     * 获取hashMap对应的所有键值
//     * key 键
//     */
//    public Map<Object, Object> hashGetAll(String key){
//        return redisTemplate.opsForHash().entries(key);
//    }
//
//    /**
//     * 存储hashMap
//     * key 键
//     * item 项
//     */
//    public boolean hashSet(String key, Map<String, Object> map){
//        try{
//            redisTemplate.opsForHash().putAll(key, map);
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 存入hash并设置时间
//     */
//    public boolean hashSetTime(String key, Map<String, Object> map, long time){
//        try{
//            redisTemplate.opsForHash().putAll(key, map);
//            if (time > 0){
//                redisTemplate.expire(key, time,TimeUnit.SECONDS);
//            }
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 获取list集合
//     * @param key
//     * @return
//     */
//    public Object lGet(String key){
//        return redisTemplate.opsForList().leftPop(key);
//    }
//
//    /**
//     * 设置集合
//     * @param key
//     * @param list
//     * @param time
//     * @return
//     */
//    public boolean lSet(String key, List<Object> list, long time){
//        try{
//            if (time > 0){
//                redisTemplate.opsForList().set(key,time, list);
//            }else {
//                redisTemplate.opsForList().leftPushAll(key, list);
//            }
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//    }
}
