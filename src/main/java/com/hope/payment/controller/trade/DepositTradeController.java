package com.hope.payment.controller.trade;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hope.payment.cache.localcache.BalanceLocalCache;
import com.hope.payment.mq.produce.TradeSuccProducer;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@RestController
@Log4j2
public class DepositTradeController implements ApplicationContextAware {

	@Autowired
	private StringRedisTemplate redisTemplate;
	@Autowired
	private RedissonClient redissonClient;
	@Autowired
	private BalanceLocalCache balanceLocalCache;
	@Autowired
	private TradeSuccProducer tradeSuccProducer;

	private ApplicationContext applicationContext;

	@RequestMapping("/deposit")
	public String deposit(@RequestParam String userId) throws InterruptedException {
		
		redisTemplate.opsForValue().set("dee", "12");
		RedissonClient redissonClient = applicationContext.getBean(RedissonClient.class);
		RLock lock = redissonClient.getLock("123");

		boolean tryLock = lock.tryLock(20, TimeUnit.SECONDS);
		log.info("test application tryLock: {}");
		balanceLocalCache.put(userId, new BigDecimal(5L));
		
		
		tradeSuccProducer.sendMsg("Nothing is difficult if you put your heart into it");
		
		
		return redisTemplate.opsForValue().get("testredis");
	}
	
	
	
	
	

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
