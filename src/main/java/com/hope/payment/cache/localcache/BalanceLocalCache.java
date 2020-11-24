package com.hope.payment.cache.localcache;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

@Service
public class BalanceLocalCache {
	@Autowired
	private StringRedisTemplate redisTemplate;

	private LoadingCache<String, BigDecimal> balanceDataCache = Caffeine.newBuilder().maximumSize(3)
			.refreshAfterWrite(20, TimeUnit.SECONDS).build(k -> {
				if (!redisTemplate.hasKey(k))
					redisTemplate.opsForValue().set(k, "3");

				return new BigDecimal(redisTemplate.opsForValue().get(k));

			});
//	private Cache<String, BigDecimal> balanceDataCache = Caffeine.newBuilder().maximumSize(3)
//			.expireAfterWrite(5, TimeUnit.SECONDS).build();

	public void put(String userId, BigDecimal bigDecimal) {
		balanceDataCache.put(userId, bigDecimal);
	}

	public BigDecimal get(String userId) {

		return balanceDataCache.getIfPresent(userId);
	}

}
