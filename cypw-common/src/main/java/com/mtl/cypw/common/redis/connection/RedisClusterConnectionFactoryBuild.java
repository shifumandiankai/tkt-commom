package com.mtl.cypw.common.redis.connection;

import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author tang
 */
public class RedisClusterConnectionFactoryBuild extends RedisConnectionFactoryBuild {

    private RedisClusterConfiguration configuration = null;

    @Override
    protected void buildRedisConfiguration() {
        configuration = new RedisClusterConfiguration();
        String[] hosts = redisProperties.getHostName().split(":");
        Set<RedisNode> redisNodes = new HashSet<RedisNode>();
        redisNodes.add(new RedisClusterNode(hosts[0], Integer.valueOf(hosts[1])));
        configuration.setClusterNodes(redisNodes);
        configuration.setMaxRedirects(redisProperties.getMaxRedirects());
    }

    @Override
    protected JedisConnectionFactory buildJedisConnectionFactory() {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(configuration, jedisPoolConfig);
        if (!StringUtils.isEmpty(redisProperties.getPassword())) {
            connectionFactory.setPassword(redisProperties.getPassword());
        }
        return connectionFactory;
    }

}
