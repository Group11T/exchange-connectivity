package com.io.t11.exchangeconnectivity.service;

import com.io.t11.exchangeconnectivity.dao.CreatedOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService implements IOrderService {

    @Autowired
    CreatedOrderRepository orderRepository;

    @Autowired
    RestTemplate restTemplate;

    private final String clientConnectivityUrl="localhost:8091/api/trades/add/portfolio";

    public OrderService(CreatedOrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

//    @Override
//    public Stock updateOrderUniqueId(Long id, String uid) {
//        Optional<Stock> createdOrder = orderRepository.findById(id);
//        createdOrder.ifPresent(order->{
//            order.setUniqueOrderId(uid);
//            orderRepository.save(order);
//        });
//        return createdOrder.get();
//    }
//
//    @Override
//    public String addTradedOrderToPortfolio(Stock stock) {
//        HttpEntity<Stock> entity = new HttpEntity<>(new Stock());
//        return restTemplate.postForObject(clientConnectivityUrl, entity, String.class);
//    }

}
