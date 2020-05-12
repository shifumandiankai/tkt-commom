package com.mtl.cypw.common.redis.connection;

import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;


public class RedisSentinelConnectionFactoryBuild extends RedisConnectionFactoryBuild {

    protected RedisSentinelConfiguration configuration = null;

    @Override
    protected void buildRedisConfiguration() {
        String[] hosts = redisProperties.getHostName().split(",");
        Set<String> sentinelHostAndPorts = new HashSet<String>();
        for (String hn : hosts) {
            sentinelHostAndPorts.add(hn);
        }
        configuration = new RedisSentinelConfiguration(redisProperties.getMasterName(), sentinelHostAndPorts);
    }

    @Override
    protected JedisConnectionFactory buildJedisConnectionFactory() {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(configuration,
                jedisPoolConfig);
        if (!StringUtils.isEmpty(redisProperties.getPassword())) {
            connectionFactory.setPassword(redisProperties.getPassword());
        }
        return connectionFactory;
    }

}
