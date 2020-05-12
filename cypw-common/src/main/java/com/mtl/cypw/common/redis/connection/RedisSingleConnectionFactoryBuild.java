package com.mtl.cypw.common.redis.connection;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.util.StringUtils;


public class RedisSingleConnectionFactoryBuild extends RedisConnectionFactoryBuild {


    @Override
    protected void buildRedisConfiguration() {

    }

    @Override
    protected JedisConnectionFactory buildJedisConnectionFactory() {
        String[] hosts = redisProperties.getHostName().split(":");
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        jedisConnectionFactory.setHostName(hosts[0]);
        jedisConnectionFactory.setPort(Integer.valueOf(hosts[1]));
        if (!StringUtils.isEmpty(redisProperties.getPassword())) {
            jedisConnectionFactory.setPassword(redisProperties.getPassword());
        }
        return jedisConnectionFactory;
    }

}
