package com.hope.payment.controller.trade;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

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

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@RestController
@Log4j2
public class WithdrawTradeController implements ApplicationContextAware {

	private ApplicationContext applicationContext;
	
	@Autowired
	private BalanceLocalCache balanceLocalCache;
	
	@RequestMapping("/withdraw")
	public String withdraw(@RequestParam String userId) throws InterruptedException {
		
		BigDecimal bigDecimal = balanceLocalCache.get(userId);
		

		return String.valueOf(bigDecimal.longValue());
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
