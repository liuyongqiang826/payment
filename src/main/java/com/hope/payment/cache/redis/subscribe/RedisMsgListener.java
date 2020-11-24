package com.hope.payment.cache.redis.subscribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.hope.payment.controller.trade.DepositTradeController;

import lombok.extern.log4j.Log4j2;

/*
 * @author lyq
 * @description 订阅-消费redis发布的channel信息
 */
@Service
@Log4j2
public class RedisMsgListener implements MessageListener {

	@Autowired
	private StringRedisTemplate redisTemplate;
	@Override
	public void onMessage(Message message, byte[] pattern) {
		log.info("hhhhh {},{}",message,pattern);
		String key = (String)redisTemplate.getValueSerializer().deserialize(pattern); // 消息通道名称
		String msg = (String)redisTemplate.getValueSerializer().deserialize(message.getBody()); // 消息内容
		log.info("hhhhh {},{}",key,msg);

	}

}
