package com.io.t11.exchangeconnectivity.service;

import com.io.t11.exchangeconnectivity.dto.CreatedOrder;
import com.io.t11.exchangeconnectivity.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Component
public class OrderQueueSubscriber implements CommandLineRunner {

    private static JedisPool jedisPool = new JedisPool("localhost");
    private static final String QUEUE = "orderQueue";
    private static final int TIMEOUT = 0;

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
            System.out.println(messages.get(1));
            OrderDto orderDto = convertToOrderDto(messages);
//            CreatedOrder order = exchangeService.callMallon(orderDto);
            //update order uid here
        }
    }

    private OrderDto convertToOrderDto(List<String> messages) {
        JSONOb
        return new OrderDto();
    }
}