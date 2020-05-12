package com.mtl.cypw.common.redis.connection;

import com.mtl.cypw.common.redis.config.RedisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.util.Assert;
import redis.clients.jedis.JedisPoolConfig;


public abstract class RedisConnectionFactoryBuild {

    protected Logger LOG = LoggerFactory.getLogger(this.getClass());

    protected RedisProperties redisProperties = null;

    protected JedisPoolConfig jedisPoolConfig = null;

    public void setRedisProperties(RedisProperties redisProperties) {
        validate(redisProperties);
        this.redisProperties = redisProperties;
        LOG.info("redisProperties={}", redisProperties);
    }

    public void validate(RedisProperties properties) {
        Assert.notNull(properties, "redisProperties can't be null");
        Assert.hasText(properties.getHostName(), "redisProperties.hostName can't be null");
        Assert.hasText(properties.getRedisType(), "redisProperties.redisType can't be null");
        if (RedisProperties.REDIS_TYPE_SENTINEL.equalsIgnoreCase(properties.getRedisType())) {
            Assert.hasText(properties.getMasterName(), "redisProperties.masterName can't be null");
        }
    }

    public JedisConnectionFactory build() {
        buildJedisPoolConfig();
        buildRedisConfiguration();
        return buildJedisConnectionFactory();
    }

    public void buildJedisPoolConfig() {
        jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisProperties.getMaxIdle());
        jedisPoolConfig.setMaxTotal(redisProperties.getMaxTotal());
        jedisPoolConfig.setMaxWaitMillis(redisProperties.getMaxWaitMillis());
    }

    protected abstract void buildRedisConfiguration();

    protected abstract JedisConnectionFactory buildJedisConnectionFactory();

}
