package com.io.t11.exchangeconnectivity.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.io.t11.exchangeconnectivity.dto.OrderDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;

@Component
public class OrderQueueSubscriber implements CommandLineRunner {

    private static JedisPool jedisPool = new JedisPool("localhost");
    private static final String QUEUE = "orderQueue";
    private static final int TIMEOUT = 0;

    @Override
    public void run(String... args) throws Exception {
        List<String> message = null;
        while(true){
            System.out.println("Waiting for a message in the queue");
            message  = jedisPool.getResource().blpop(TIMEOUT,QUEUE);
            System.out.println(message.get(1));
//            OrderDto orderDto = convertToObject(message);
//            System.out.println(orderDto);
//            call method that sends to exchange
        }
    }

    public OrderDto convertToObject(List<String> message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        return new OrderDto();
    }
}