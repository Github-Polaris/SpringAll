package com.springboot.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig  { //extends CachingConfigurerSupport


	/**
	 * 自定义缓存key生成策略
	 * 默认情况下，@Bean 注解未显式指定名称时，Spring 会使用方法名作为 Bean 的名称。
	 * */
	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, java.lang.reflect.Method method, Object... params) {
				StringBuffer sb = new StringBuffer();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for (Object obj : params) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};
	}

	// 缓存管理器
	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		// 创建默认的缓存配置（可自定义过期时间、序列化方式等）
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
				// 设置默认缓存过期时间为100秒
				.entryTtl(Duration.ofSeconds(100));

		// 构建 RedisCacheManager
		return RedisCacheManager.builder(redisConnectionFactory)
				.cacheDefaults(config)
				.build();
	}

	/**
	 * 这个 Bean 是 固定使用 StringRedisTemplate
	 * 只能处理 key 和 value 都是 字符串类型 的 Redis 操作，看泛型<String, String> 这是key和value的类型
	 * */
	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
		StringRedisTemplate template = new StringRedisTemplate(factory);
		// 设置序列化工具
		setSerializer(template);
		template.afterPropertiesSet();
		return template;
	}

	private void setSerializer(StringRedisTemplate template) {
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);

		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(om, Object.class);
		template.setValueSerializer(jackson2JsonRedisSerializer);
	}

	/**
	 * 一个额外定义的通用RedisTemplate
	 * */
//	@Bean
//	public RedisTemplate<String, Object> redisObjectTemplate(RedisConnectionFactory factory) {
//		RedisTemplate<String, Object> template = new RedisTemplate<>();
//		template.setConnectionFactory(factory);
//		template.setKeySerializer(new StringRedisSerializer());
//		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//		template.setHashKeySerializer(new StringRedisSerializer());
//		template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
//		template.afterPropertiesSet();
//		return template;
//	}

}
