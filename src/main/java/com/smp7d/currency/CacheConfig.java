package com.smp7d.currency;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.cache.CacheBuilder;

@Configuration
@EnableCaching
public class CacheConfig {

	public final static String LATEST_CACHE = "latest-rates-cache";
	public final static String HISTORICAL_CACHE = "historical-rates-cache";

	@Bean
	public Cache latestRatesCache() {
		return new GuavaCache(LATEST_CACHE, CacheBuilder.newBuilder()
				.expireAfterWrite(15, TimeUnit.MINUTES).build());
	}

	@Bean
	public Cache historicalRatesCache() {
		return new GuavaCache(HISTORICAL_CACHE, CacheBuilder.newBuilder()
				.expireAfterWrite(1, TimeUnit.DAYS).build());
	}

}
