package com.io.t11.exchangeconnectivity.config;

import com.io.t11.exchangeconnectivity.service.ExchangeConnectivityPublisher;
import com.io.t11.exchangeconnectivity.service.IExchangeConnectivityPublisher;
import com.io.t11.exchangeconnectivity.service.OrderQueueSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import redis.clients.jedis.Jedis;

@Configuration
public class RedisConfig {

    @Autowired
    private Environment env;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory; // redis conn for est conn to redis db

    @Bean
    String queue(){
        return new String("orderQueue");
    }

    @Bean
    Jedis jedis(){
        System.out.println("Got this " + env.getProperty("spring.redis.host"));
        return new Jedis(env.getProperty("spring.redis.host"));
    }

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
