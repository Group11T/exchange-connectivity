package com.io.t11.exchangeconnectivity.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.io.t11.exchangeconnectivity.model.OrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Component
public class OrderQueueSubscriber implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger((OrderQueueSubscriber.class));

    private static JedisPool jedisPool = new JedisPool("localhost");
    private static final String QUEUE = "orderQueue";
    private static final int TIMEOUT = 0;
    private Long createdOrderId;

    @Autowired
    ExchangeService exchangeService;

    @Autowired
    IOrderService orderService;

    public OrderQueueSubscriber(ExchangeService exchangeService, IOrderService orderService) {
        this.exchangeService = exchangeService;
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> messages = null;
        while(true){
            System.out.println("Waiting for a message in the queue");
            messages  = jedisPool.getResource().blpop(TIMEOUT,QUEUE);
            OrderDto orderDto = convertToOrderDto(messages.get(1));
            String uid = exchangeService.callMallon(orderDto);
            logger.info("client order traded successfully");
        }
    }

    private OrderDto convertToOrderDto(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        OrderDto orderDto = objectMapper.readValue(message, OrderDto.class);
        return orderDto;
    }
}