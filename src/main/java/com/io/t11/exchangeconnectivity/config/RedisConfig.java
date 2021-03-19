package com.io.t11.exchangeconnectivity.config;

import com.io.t11.exchangeconnectivity.service.OrderQueueSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    private RedisConnectionFactory redisConnectionFactory; // redis conn for est conn to redis db

    @Bean
    String queue(){
        return new String("orderQueue");
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("exchange");
    }

    @Bean
    Jedis jedis(){
        return new Jedis("localhost");
    }


    @Bean
    RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    //

//    @Bean
//    ITradePublisher tradeObjectPublisher(){
//        ChannelTopic publisherTopic=new ChannelTopic("trade-orders");
//        return new TradePublisher(redisTemplate(redisConnectionFactory),publisherTopic);
//    }

}
