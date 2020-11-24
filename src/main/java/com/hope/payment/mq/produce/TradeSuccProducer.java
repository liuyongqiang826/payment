package com.hope.payment.mq.produce;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class TradeSuccProducer {

	@Autowired
	private RocketMQTemplate rocketMQTemplate;

	public void sendMsg(String msg) {
		try {
			rocketMQTemplate.syncSend("rocket-new", msg);
		} catch (Exception e) {
			log.error("send message error", e);
		}
	}
}
