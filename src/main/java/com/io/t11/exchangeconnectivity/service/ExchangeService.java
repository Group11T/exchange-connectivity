package com.io.t11.exchangeconnectivity.service;

import com.io.t11.exchangeconnectivity.dao.CreatedOrderRepository;
import com.io.t11.exchangeconnectivity.dto.CreatedOrder;
import com.io.t11.exchangeconnectivity.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeService {

    @Autowired
    private final CreatedOrderRepository orderRepository;

    RestTemplate restTemplate = new RestTemplate();

    private String exchangeUrl="/a";

    @Autowired
    public ExchangeService(CreatedOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public CreatedOrder callMallon(OrderDto orderDto) {
        HttpEntity<OrderDto> entity = new HttpEntity<>(new OrderDto());
        return restTemplate.postForObject(exchangeUrl, entity, CreatedOrder.class);
    }
}
