package com.hope.payment.cache.redis;

import java.util.List;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnection;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/*
 * @author lyq
 * @description 配置Redisson客户端，使用Redisson客户端操作redis,比如加锁等
 */
@Configuration
@ConditionalOnClass({ RedisConnection.class, RedissonClient.class })
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedissonAutoConfiguration {

	@Autowired
	private RedisProperties redisProperties;
	
	@Bean
	public RedissonClient redissonClient()
	{
		Config config = new Config();
		//sentinel
		if (redisProperties.getSentinel() != null)
		{
			SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
			sentinelServersConfig.setSubscriptionsPerConnection(50);
			sentinelServersConfig.setMasterName(redisProperties.getSentinel().getMaster());
			redisProperties.getSentinel().getNodes();
			sentinelServersConfig.addSentinelAddress(redisProperties.getSentinel().getNodes().toArray(new String[0]));
			sentinelServersConfig.setDatabase(redisProperties.getDatabase());
			if (redisProperties.getPassword() != null)
			{
				sentinelServersConfig.setPassword(redisProperties.getPassword());
			}
			return Redisson.create(config);
		}
		// cluster
		if (redisProperties.getCluster() != null)
		{
			ClusterServersConfig clusterServersConfig = config.useClusterServers();
			clusterServersConfig.setSubscriptionsPerConnection(50);
			List<String> nodes = redisProperties.getCluster().getNodes();
			for (String address : nodes)
			{
				clusterServersConfig.addNodeAddress(address);
			}
			Redisson.create(config);
			return Redisson.create(config);
		}
		// single redis
		SingleServerConfig singleServerConfig = config.useSingleServer();
		singleServerConfig.setSubscriptionsPerConnection(50);
		singleServerConfig.setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort());
		singleServerConfig.setDatabase(redisProperties.getDatabase());
		if (redisProperties.getPassword() != null)
		{
			singleServerConfig.setPassword(redisProperties.getPassword());
		}
		return Redisson.create(config);
	}
}
