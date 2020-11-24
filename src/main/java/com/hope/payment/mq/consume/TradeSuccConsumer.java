
package com.hope.payment.mq.consume;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
@RocketMQMessageListener(topic = "rocket-new", consumerGroup = "string_consumer")
public class TradeSuccConsumer implements RocketMQListener<String> {

	@Override
	public void onMessage(String message) {
		log.info("hello + " + message);
	}

}
