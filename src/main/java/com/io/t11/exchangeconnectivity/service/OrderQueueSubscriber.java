package com.io.t11.exchangeconnectivity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.List;

@Component
public class OrderQueueSubscriber implements CommandLineRunner {

    private static JedisPool jedisPool = new JedisPool("localhost");
    private static final String QUEUE = "orderQueue";
    private static final int TIMEOUT = 0;

    @Autowired
    ExchangeService exchangeService;

    @Override
    public void run(String... args) throws JedisConnectionException {
        List<String> messages = null;
        while(true){
            System.out.println("Waiting for a message in the queue");
            messages  = jedisPool.getResource().blpop(TIMEOUT,QUEUE);
            System.out.println("received message with key:" + messages.get(0) + " with value:" + messages.get(1)); //<- implementation to call external exchange

            //serialize message to orderDto
            // exchangeService.callMallon(messages.get(1));
        }
    }
}