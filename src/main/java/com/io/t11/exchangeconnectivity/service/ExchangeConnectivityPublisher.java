package com.io.t11.exchangeconnectivity.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.io.t11.exchangeconnectivity.model.OrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

public class ExchangeConnectivityPublisher implements  IExchangeConnectivityPublisher {

    private static Logger logger = LoggerFactory.getLogger((ExchangeConnectivityPublisher.class));

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    public ExchangeConnectivityPublisher(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    ChannelTopic registerTopic() {
        return new ChannelTopic("exchangeService");
    }

    @Override
    public void publishToRegister(OrderDto orderDto) throws JsonProcessingException {
        logger.info("Publishing: {}",orderDto," to register");
        redisTemplate.convertAndSend(registerTopic().getTopic(),orderDto);
    }
}
