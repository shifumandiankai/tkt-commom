package com.mtl.cypw.common.redis.config;

import com.alibaba.fastjson.JSON;

/**
 * @author
 */
public class RedisProperties {

    /**
     * 单节点
     */
    public static final String REDIS_TYPE_SINGLE = "single";
    /**
     * 主从
     */
    public static final String REDIS_TYPE_SENTINEL = "sentinel";
    /**
     * 集群
     */
    public static final String REDIS_TYPE_CLUSTER = "cluster";


    public static final String DEFAULT_REDIS_TYPE = REDIS_TYPE_SINGLE;

    private int maxIdle = 300;

    /**
     * 最大连接数
     */
    private int maxTotal = 600;

    private int maxWaitMillis = 3000;

    private boolean testOnBorrow = true;

    private String hostName;

    private String password;

    private int maxRedirects = 10;

    private String masterName;

    /**
     * Single    单节点
     * Cluster   集群
     * Sentinel  主从
     */
    private String redisType = DEFAULT_REDIS_TYPE;

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(int maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxRedirects() {
        return maxRedirects;
    }

    public void setMaxRedirects(int maxRedirects) {
        this.maxRedirects = maxRedirects;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public boolean getTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public String getRedisType() {
        return redisType;
    }

    public void setRedisType(String redisType) {
        this.redisType = redisType;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
