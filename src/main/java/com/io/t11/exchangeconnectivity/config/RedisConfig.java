package com.io.t11.exchangeconnectivity.config;

import com.io.t11.exchangeconnectivity.service.ExchangeConnectivityPublisher;
import com.io.t11.exchangeconnectivity.service.IExchangeConnectivityPublisher;
import com.io.t11.exchangeconnectivity.service.OrderQueueSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    String queue(){
        return new String("orderQueue");
    }

    @Bean
    JedisPool jedisPool(){
           return new JedisPool("redis-18040.c257.us-east-1-3.ec2.cloud.redislabs.com", 18040, "default", "TGYqAObAPjsrZEd5KbDnzBexK5MYWTBS");
    }

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    };

    @Bean
    RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    //

    @Bean
    IExchangeConnectivityPublisher exchangeConnectivityObjectPublisher(){
        return new ExchangeConnectivityPublisher(redisTemplate(redisConnectionFactory));
    }

}
