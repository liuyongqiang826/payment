package com.hope.payment.cache.redis;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/*
 * @author lyq
 * @description redis发布订阅channel(topic)配置
 *
 */
@Configuration
public class RedisChannelConfig {

	@Autowired(required = false)
	private MessageListener listener;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Bean
	public RedisMessageListenerContainer redisMessageListenerContainer() {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(redisTemplate.getConnectionFactory());
		 //监听一个topic
//        container.addMessageListener(listener, new ChannelTopic("mm"));
		// 监听多个topic
		container.addMessageListener(listener, Arrays.asList(new ChannelTopic("mm"),new ChannelTopic("nn")));

		return container;
	}

}
