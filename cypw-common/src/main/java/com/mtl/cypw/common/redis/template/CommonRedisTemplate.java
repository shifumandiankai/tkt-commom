package com.mtl.cypw.common.redis.template;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.mtl.cypw.common.exception.GeneralException;
import com.mtl.cypw.common.redis.config.RedisProperties;
import com.mtl.cypw.common.redis.connection.RedisClusterConnectionFactoryBuild;
import com.mtl.cypw.common.redis.connection.RedisConnectionFactoryBuild;
import com.mtl.cypw.common.redis.connection.RedisSentinelConnectionFactoryBuild;
import com.mtl.cypw.common.redis.connection.RedisSingleConnectionFactoryBuild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author
 */

public class CommonRedisTemplate extends RedisTemplate<String, Object> implements InitializingBean, DisposableBean {

    private static final Logger LOG = LoggerFactory.getLogger(CommonRedisTemplate.class);

    RedisSerializer<String> stringSerializer = new StringRedisSerializer();

    private RedisProperties redisProperties;

    private boolean isInit = false;

    @Override
    public void afterPropertiesSet() {
        if (isInit) {
            return;
        }
        init();
        setKeySerializer(stringSerializer);
        setHashKeySerializer(stringSerializer);
        super.afterPropertiesSet();
        isInit = true;
    }

    private void init() {
        JedisConnectionFactory jedisConnectionFactory = buildConnectionFactory();
        super.setConnectionFactory(jedisConnectionFactory);
    }

    private JedisConnectionFactory buildConnectionFactory() throws GeneralException {
        String redisType = this.redisProperties.getRedisType();
        RedisConnectionFactoryBuild connection = null;
        switch (redisType) {
            case RedisProperties.REDIS_TYPE_SINGLE:
                connection = new RedisSingleConnectionFactoryBuild();
                break;
            case RedisProperties.REDIS_TYPE_SENTINEL:
                connection = new RedisSentinelConnectionFactoryBuild();
                break;
            case RedisProperties.REDIS_TYPE_CLUSTER:
                connection = new RedisClusterConnectionFactoryBuild();
                break;
            default:
                LOG.error("redisType error {}", redisType);
                throw new GeneralException(-1);
        }

        connection.setRedisProperties(this.redisProperties);
        JedisConnectionFactory connectionFactory = connection.build();
        connectionFactory.afterPropertiesSet();
        return connectionFactory;
    }

//=======================save start===============

    /**
     * 将数据存入缓存
     *
     * @param key
     * @param val
     * @return
     */
    public void saveString(String key, String val) {
        ValueOperations<String, Object> vo = opsForValue();
        vo.set(key, val);
    }

    /**
     * 将数据存入缓存（并设置失效时间）
     *
     * @param key
     * @param val
     * @param seconds
     * @return
     */
    public void saveString(String key, String val, int seconds) {
        opsForValue().set(key, val, seconds, TimeUnit.SECONDS);
    }

    /**
     * 将数据存入缓存的集合中
     *
     * @param key
     * @param val
     * @return
     */
    public void saveToSet(String key, String val) {
        SetOperations<String, Object> so = opsForSet();
        so.add(key, val);
    }

    /**
     * 向set中追加一个值
     *
     * @param setName set名
     * @param value
     */
    public void setSave(String setName, String value) {
        execute((RedisCallback<Long>) connection -> connection.sAdd(setName.getBytes(), value.getBytes()));
    }

    /**
     * 保存到hash集合中
     *
     * @param hName 集合名
     * @param key
     * @param value
     */
    public void hashSet(String hName, String key, String value) {
        opsForHash().put(hName, key, value);
    }

    /**
     * 保存到hash集合中
     *
     * @param <T>
     * @param hName 集合名
     * @param key
     */
    public <T> void hashSet(String hName, String key, T t) {
        hashSet(hName, key, JSON.toJSONString(t));
    }

    /**
     * 将 key的值保存为 value ，当且仅当 key 不存在。 若给定的 key 已经存在，则 SETNX 不做任何动作。 SETNX 是『SET if Not eXists』(如果不存在，则 SET)的简写。 <br>
     * 保存成功，返回 true <br>
     * 保存失败，返回 false
     */
    public boolean saveNX(String key, String val) {
        /** 设置成功，返回 1 设置失败，返回 0 **/
        return execute((RedisCallback<Boolean>) connection -> {
            return connection.setNX(key.getBytes(), val.getBytes());
        });

    }

    /**
     * 将 key的值保存为 value ，当且仅当 key 不存在。 若给定的 key 已经存在，则 SETNX 不做任何动作。 SETNX 是『SET if Not eXists』(如果不存在，则 SET)的简写。 <br>
     * 保存成功，返回 true <br>
     * 保存失败，返回 false
     *
     * @param key
     * @param val
     * @param expire 超时时间
     * @return 保存成功，返回 true 否则返回 false
     */
    public boolean saveNX(String key, String val, int expire) {
        boolean ret = saveNX(key, val);
        if (ret) {
            expire(key, expire, TimeUnit.SECONDS);
        }
        return ret;
    }

    /**
     * 将自增变量存入缓存
     */
    public void saveSeq(String key, long seqNo) {
        delete(key);
        opsForValue().increment(key, seqNo);
    }

    /**
     * 将递增浮点数存入缓存
     */
    public void saveFloat(String key, float data) {
        delete(key);
        opsForValue().increment(key, data);
    }

    /**
     * 保存复杂类型数据到缓存
     *
     * @param key
     * @param obj
     * @return
     */
    public void saveBean(String key, Object obj) {
        opsForValue().set(key, JSON.toJSONString(obj));
    }

    /**
     * 保存复杂类型数据到缓存（并设置失效时间）
     *
     * @param key
     * @param obj
     * @param seconds
     * @return
     */
    public void saveBean(String key, Object obj, int seconds) {
        opsForValue().set(key, JSON.toJSONString(obj), seconds, TimeUnit.SECONDS);
    }

    /**
     * 功能: 存到指定的队列中<br />
     * 左近右出<br\> 作者: 耿建委
     *
     * @param key
     * @param val
     * @param size 队列大小限制 0：不限制
     */
    public void saveToQueue(String key, String val, long size) {
        ListOperations<String, Object> lo = opsForList();
        if (size > 0 && lo.size(key) >= size) {
            lo.rightPop(key);
        }
        lo.leftPush(key, val);
    }

    /**
     * 向sorted set中追加一个值
     *
     * @param key    set名
     * @param score  分数
     * @param member 成员名称
     */
    public void saveToSortedset(String key, Double score, String member) {
        execute((RedisCallback<Boolean>) connection -> connection.zAdd(key.getBytes(), score, member.getBytes()));
    }

    /**
     * 将所有指定的值插入到存于 key 的列表的头部。如果 key 不存在，那么在进行 push 操作前会创建一个空列表
     *
     * @param <T>
     * @param key
     * @param value
     * @return
     */
    public <T> Long lpush(String key, T value) {

        return opsForList().leftPush(key, JSON.toJSONString(value));
    }

    /**
     * 只有当 key 已经存在并且存着一个 list 的时候，在这个 key 下面的 list 的头部插入 value。 与 LPUSH 相反，当 key 不存在的时候不会进行任何操作
     *
     * @param key
     * @param value
     * @return
     */
    public <T> Long lpushx(String key, T value) {

        return opsForList().leftPushIfPresent(key, JSON.toJSONString(value));
    }

    /**
     * 保存到hash集合中 只在 key 指定的哈希集中不存在指定的字段时，设置字段的值。如果 key 指定的哈希集不存在，会创建一个新的哈希集并与 key 关联。如果字段已存在，该操作无效果。
     *
     * @param hName 集合名
     * @param key
     * @param value
     */
    public void hsetnx(String hName, String key, String value) {
        execute((RedisCallback<Boolean>) connection -> connection.hSetNX(key.getBytes(), key.getBytes(), value.getBytes()));
    }

    /**
     * 保存到hash集合中 只在 key 指定的哈希集中不存在指定的字段时，设置字段的值。如果 key 指定的哈希集不存在，会创建一个新的哈希集并与 key 关联。如果字段已存在，该操作无效果。
     *
     * @param <T>
     * @param hName 集合名
     * @param key
     * @param t
     */
    public <T> void hsetnx(String hName, String key, T t) {
        hsetnx(hName, key, JSON.toJSONString(t));
    }

//=======================save end===============

//=======================get start==============

    /**
     * @param key 缓存Key
     * @return keyValue
     * @author:mijp
     * @since:2017/1/16 13:23
     */
    public Object getFromSet(String key) {
        return opsForSet().pop(key);
    }

    /**
     * 根据key获取所以值
     *
     * @param key
     * @return
     */
    public Map<Object, Object> hgetAll(String key) {
        return opsForHash().entries(key);
    }

    /**
     * 取得复杂类型数据
     *
     * @param key
     * @param clazz
     * @return
     */
    public <T> T getBean(String key, Class<T> clazz) {
        Object value = opsForValue().get(key);
        if (value == null) {
            return null;
        }
        return JSON.parseObject(value.toString(), clazz);
    }

    /**
     * 从缓存中取得字符串数据
     *
     * @param key
     * @return 数据
     */
    public String getString(String key) {
        Object o = opsForValue().get(key);
        if (o == null) {
            return null;
        }
        return String.valueOf(o);
    }

    /**
     * 功能: 从指定队列里取得数据<br />
     * 作者: 耿建委
     *
     * @param key
     * @param size 数据长度
     * @return
     */
    public List<Object> getFromQueue(String key, long size) {
        boolean flag = execute((RedisCallback<Boolean>) connection -> {
            return connection.exists(key.getBytes());
        });
        if (flag) {
            return new ArrayList<Object>();
        }
        ListOperations<String, Object> lo = opsForList();
        if (size > 0) {
            return lo.range(key, 0, size - 1);
        } else {
            return lo.range(key, 0, lo.size(key) - 1);
        }
    }

    /**
     * 功能: 从指定队列里取得数据<br />
     * 作者: 耿建委
     *
     * @param key
     * @return
     */
    public Object popQueue(String key) {
        return opsForList().rightPop(key);
    }

    /**
     * 取得序列值的下一个
     *
     * @param key
     * @return
     */
    public Long getSeqNext(String key) {
        return execute((RedisCallback<Long>) connection -> {
            return connection.incr(key.getBytes());
        });
    }

    /**
     * 取得序列值的下一个
     *
     * @param key
     * @return
     */
    public Long getSeqNext(String key, long value) {
        return execute((RedisCallback<Long>) connection -> {
            return connection.incrBy(key.getBytes(), value);
        });
    }

    /**
     * 将序列值回退一个
     *
     * @param key
     * @return
     */
    public void getSeqBack(String key) {
        execute((RedisCallback<Long>) connection -> connection.decr(key.getBytes()));
    }

    /**
     * 从hash集合里取得
     *
     * @param hName
     * @param key
     * @return
     */
    public Object hashGet(String hName, String key) {
        return opsForHash().get(hName, key);
    }

    public <T> T hashGet(String hName, String key, Class<T> clazz) {
        return JSON.parseObject((String) hashGet(hName, key), clazz);
    }

    /**
     * 增加浮点数的值
     *
     * @param key
     * @return
     */
    public Double incrFloat(String key, double incrBy) {
        return execute((RedisCallback<Double>) connection -> {
            return connection.incrBy(key.getBytes(), incrBy);
        });
    }

    /**
     * 判断是否缓存了数据
     *
     * @param key 数据KEY
     * @return 判断是否缓存了
     */
    public boolean isCached(String key) {
        return execute((RedisCallback<Boolean>) connection -> {
            return connection.exists(key.getBytes());
        });
    }

    @Override
    public Long getExpire(String key) {
        return super.getExpire(key);
    }

    @Override
    public Long getExpire(String key, final TimeUnit timeUnit) {
        return super.getExpire(key, timeUnit);
    }

    /**
     * 判断hash集合中是否缓存了数据
     *
     * @param hName
     * @param key   数据KEY
     * @return 判断是否缓存了
     */
    public boolean hashCached(String hName, String key) {
        return execute((RedisCallback<Boolean>) connection -> {
            return connection.hExists(key.getBytes(), key.getBytes());
        });
    }

    /**
     * 判断是否缓存在指定的集合中
     *
     * @param key 数据KEY
     * @param val 数据
     * @return 判断是否缓存了
     */
    public boolean isMember(String key, String val) {
        return execute((RedisCallback<Boolean>) connection -> {
            return connection.sIsMember(key.getBytes(), val.getBytes());
        });
    }

    /**
     * 逆序列出sorted set包括分数的set列表
     *
     * @param key   set名
     * @param start 开始位置
     * @param end   结束位置
     * @return 列表
     */
    public Set<Tuple> listSortedsetRev(String key, int start, int end) {
        return execute((RedisCallback<Set<Tuple>>) connection -> {
            return connection.zRevRangeWithScores(key.getBytes(), start, end);
        });
    }

    /**
     * 逆序取得sorted sort排名
     *
     * @param key    set名
     * @param member 成员名
     * @return 排名
     */
    public Long getRankRev(String key, String member) {
        return execute((RedisCallback<Long>) connection -> {
            return connection.zRevRank(key.getBytes(), member.getBytes());
        });
    }

    /**
     * 根据成员名取得sorted sort分数
     *
     * @param key    set名
     * @param member 成员名
     * @return 分数
     */
    public Double getMemberScore(String key, String member) {
        return execute((RedisCallback<Double>) connection -> {
            return connection.zScore(key.getBytes(), member.getBytes());
        });
    }

    /**
     * 从hash map中取得复杂类型数据
     *
     * @param key
     * @param field
     * @param clazz
     */
    public <T> T getBeanFromMap(String key, String field, Class<T> clazz) {
        byte[] input = execute((RedisCallback<byte[]>) connection -> {
            return connection.hGet(key.getBytes(), field.getBytes());
        });
        return JSON.parseObject(input, clazz, Feature.AutoCloseSource);
    }

    /**
     * 列出set中所有成员
     *
     * @param setName set名
     * @return
     */
    public Set<Object> listSet(String setName) {
        return opsForHash().keys(setName);
    }

    /**
     * 获取匹配的key
     *
     * @param key
     */
    @Override
    public Set<String> keys(String key) {
        return super.keys(key);
    }

    /**
     * @param key
     * @return
     * @Description: 根据key增长 ，计数器
     * @author clg
     * @date 2016年6月30日 下午2:37:52
     */
    public long incr(String key) {
        return execute((RedisCallback<Long>) connection -> {
            return connection.incr(key.getBytes());
        });
    }

    /**
     * @param key
     * @return
     * @Description: 根据key获取当前计数结果
     * @author clg
     * @date 2016年6月30日 下午2:38:20
     */
    public Object getCount(String key) {
        return opsForValue().get(key);
    }

    /**
     * 返回存储在 key 里的list的长度。 如果 key 不存在，那么就被看作是空list，并且返回长度为 0
     *
     * @param key
     * @return
     */
    public Long llen(String key) {

        return opsForList().size(key);
    }

    /**
     * 返回存储在 key 的列表里指定范围内的元素。 start 和 end 偏移量都是基于0的下标，即list的第一个元素下标是0（list的表头），第二个元素下标是1，以此类推
     *
     * @param key
     * @return
     */
    public List<Object> lrange(String key, long start, long end) {

        return opsForList().range(key, start, end);

    }

    public Set<Object> zRange(String key, long start, long end) {
        return opsForZSet().range(key, start, end);
    }
// =======================get end==============

//========================del start============

    /**
     * 从缓存中删除数据
     *
     * @return
     */
    public void delKey(String key) {
        execute((RedisCallback<Long>) connection -> connection.del(key.getBytes()));
    }

    /**
     * 从sorted set删除一个值
     *
     * @param key    set名
     * @param member 成员名称
     */
    public void delFromSortedset(String key, String member) {
        execute((RedisCallback<Long>) connection -> connection.zRem(key.getBytes(), member.getBytes()));
    }

    /**
     * 从hashmap中删除一个值
     *
     * @param key   map名
     * @param field 成员名称
     */
    public void delFromMap(String key, String field) {
        execute((RedisCallback<Long>) connection -> connection.hDel(key.getBytes(), field.getBytes()));
    }

    /**
     * 移除并且返回 key 对应的 list 的第一个元素
     *
     * @param key
     * @return
     */
    public Object lpop(String key) {
        return opsForList().leftPop(key);
    }

//========================del end===============

//=======================other==================

    /**
     * 设置超时时间
     *
     * @param key
     * @param seconds
     */
    public void expire(String key, int seconds) {
        execute((RedisCallback<Boolean>) connection -> connection.expire(key.getBytes(), seconds));
    }

    public List<String> geoRadius(String coordinateSet, double longitude, double latitude, double radius) {
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().sortAscending();
        GeoResults<RedisGeoCommands.GeoLocation<Object>> geoResults = super.opsForGeo().geoRadius(coordinateSet, new Circle(new Point(longitude, latitude), radius), args);
        List results = new ArrayList();
        geoResults.forEach(n -> {
            String name = n.getContent().getName() + "";
            results.add(name);
        });
        return results;
    }

    public Long geoAdd(String coordinateSet, String name, double longitude, double latitude) {
        return super.opsForGeo().geoAdd(coordinateSet, new Point(longitude, latitude), name);
    }

    public Long geoRemove(String coordinateSet, String... name) {
        return super.opsForGeo().geoRemove(coordinateSet, name);
    }

    public RedisProperties getRedisProperties() {
        return redisProperties;
    }

    public void setRedisProperties(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    @Override
    public void destroy() throws Exception {
        JedisConnectionFactory connectionFactory = (JedisConnectionFactory) getConnectionFactory();
        connectionFactory.destroy();
    }
}
