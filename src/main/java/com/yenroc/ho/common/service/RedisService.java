package com.yenroc.ho.common.service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

/**
 * 
 * @author hyp
 * @date 2020年2月12日
 *
 */
@Service
public class RedisService {
    
    private final Logger log = LoggerFactory.getLogger(RedisService.class);
    
    @Autowired
    private RedisTemplate redisTemplate;
    
    private static final String KEYLIST = "KEYLIST";
    
    public Long incr(String key, long liveTime) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        Long increment = entityIdCounter.getAndIncrement();

        if ((null == increment || increment.longValue() == 0) && liveTime > 0) {//初始设置过期时间
            entityIdCounter.expire(liveTime, TimeUnit.SECONDS);
        }

        return increment;
    }
    
    public Long incr(String key) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        Long increment = entityIdCounter.getAndIncrement();
        return increment;
    }
    
    private void pushKey(final String key, boolean bool) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        // 获取KEYLIST的数据
        ArrayList<String> keylist = (ArrayList<String>) redisTemplate.opsForValue().get(KEYLIST);
        if (null == keylist) {
            keylist = new ArrayList<>();
        }
        // 如果KEYLIST包含此key 则无需加入到KEYLIST。此时如果bool为false表示从KEYLIST删除
        if (keylist.contains(key) && !bool) {
            keylist.remove(key);
            redisTemplate.opsForValue().set(KEYLIST, keylist);
        }
        // 如果KEYLIST不包含此key 且 bool为true表示添加key到KEYLIST
        if (!keylist.contains(key) && bool) {
            keylist.add(key);
            redisTemplate.opsForValue().set(KEYLIST, keylist);
        }
    }
    
    /**
     * 写入缓存
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            // pushKey(key, true);
            log.info("RedisService本次插入缓存数据"+key+"成功，值为:{}", value);
            result = true;
        } catch (Exception e) {
            log.error("RedisService本次插入缓存数据"+key+"失败，值为:"+value+"---异常:{}", e);
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 写入缓存设置时效时间
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            // pushKey(key, true);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 批量删除对应的value
     * @param keys
     * @return 
     */
    public int remove(final String... keys) {
        int i = 0;
        for (String key : keys) {
            if (remove(key)) {
                log.info("RedisService本次删除缓存key"+key+"成功");
                i++;
            }
        }
        log.info("RedisService本次删除缓存key的数量"+i+"数据:{}", keys);
        return i;
    }

    /**
     * 批量删除key
     * @param pattern
     */
    public Integer removePattern(final String pattern) {
        ArrayList<String> keylist = (ArrayList<String>) redisTemplate.opsForValue().get(KEYLIST);
        int i = 0;
        if (null == keylist) {
            log.info("RedisService目前缓存的KEYLIST中还没有数据");
            return i;
        }
        for (String key : keylist) {
            if (key.indexOf(pattern) > -1) {
                redisTemplate.delete(key);
                i++;
            }
        }
        log.info("RedisService本次清理的缓存的key满足"+pattern+"有"+i+"个:{}", keylist);
        return i;
    }
    /**
     * 删除对应的value
     * @param key
     */
    public boolean remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
            // pushKey(key, false);
            return true;
        }
        return false;
    }
    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }
    /**
     * 读取缓存
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }


    /**
     * 哈希 添加
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmSet(String key, Object hashKey, Object value){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key,hashKey,value);
    }

    /**
     * 哈希 删除
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmDel(String key, Object hashKey){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.delete(key,hashKey);
    }

    /**
     * 哈希获取数据
     * @param key
     * @param hashKey
     * @return
     */
    public Object hmGet(String key, Object hashKey){
        HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
        return hash.get(key,hashKey);
    }
    
    /**
     * 修改下标为index的列表数据类型的数据
     * @param k
     * @param v
     */
    public void lSet(String k, long index, Object v){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.set(k, index, v);
    }
    
    /**
     * 列表添加
     * @param k
     * @param v
     */
    public void lPush(String k,Object v){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k,v);
    }
    
    /**
     * 列表添加(带过期时间)
     * @param k
     * @param v
     */
    public void lPush(String k,Object v,long s){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        if (s > 0) {
            redisTemplate.expire(k, s, TimeUnit.MILLISECONDS);
        }
        list.rightPush(k,v);
    }

    /**
     * 列表获取
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public List<Object> lRange(String k, long l, long l1){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k,l,l1);
    }
    
    /**
     * 通过下标获取列表元素
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public Object lIndex(String k, long l){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.index(k, l);
    }
    
    /**
     * 获取列表长度
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public long lSize(String k){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.size(k) == null ? 0 : list.size(k);
    }

    /**
     * 集合添加
     * @param key
     * @param value
     */
    public void add(String key,Object value){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key,value);
    }

    /**
     * 集合获取
     * @param key
     * @return
     */
    public Set<Object> setMembers(String key){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     * @param key
     * @param value
     * @param scoure
     */
    public void zAdd(String key,Object value,double scoure){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key,value,scoure);
    }

    /**
     * 有序集合获取
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    public Set<Object> rangeByScore(String key,double scoure,double scoure1){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }
}