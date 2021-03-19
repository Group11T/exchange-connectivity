package com.io.t11.exchangeconnectivity.service;

import com.io.t11.exchangeconnectivity.dao.OrderRepository;
import com.io.t11.exchangeconnectivity.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeService {

    @Autowired
    private final OrderRepository orderRepository;

    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public ExchangeService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDto callMallon(OrderDto orderDto) {
        System.out.println(orderDto);

        orderDto = restTemplate.postForObject("", HttpMethod.POST, OrderDto.class);

        return orderDto;
    }
}
