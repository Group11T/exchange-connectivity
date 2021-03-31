package com.io.t11.exchangeconnectivity.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.io.t11.exchangeconnectivity.model.ExchangeDetails;
import com.io.t11.exchangeconnectivity.model.Order;
import com.io.t11.exchangeconnectivity.model.OrderDto;
import com.io.t11.exchangeconnectivity.model.StockDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UtilityService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    IExchangeConnectivityPublisher exchangeConnectivityPublisher;

    public UtilityService(RestTemplate restTemplate, IExchangeConnectivityPublisher exchangeConnectivityPublisher) {
        this.restTemplate = restTemplate;
        this.exchangeConnectivityPublisher = exchangeConnectivityPublisher;
    }

    private static Logger logger = LoggerFactory.getLogger((UtilityService.class));

    public void tradeOnExchange1(Order order,StockDto stockDto, int quantity){
        order.setQuantity(quantity);
        stockDto.setExchangeTradedOn(ExchangeDetails.EXCHANGE_1.getUrl());
        tradeStep(order,stockDto,ExchangeDetails.EXCHANGE_1.getUrl());
    }

    public void tradeOnExchange2(Order order,StockDto stockDto, int quantity){
        order.setQuantity(quantity);
        stockDto.setExchangeTradedOn(ExchangeDetails.EXCHANGE_2.getUrl());
        tradeStep(order,stockDto,ExchangeDetails.EXCHANGE_2.getUrl());
    }

    public void tradeStep(Order order, StockDto stockDto, String url){
        try{
            String uniqueId = callMallon(order,url+"order");
            stockDto.setUniqueId(uniqueId);
            sendStockToClientConnectivity(stockDto);
            exchangeConnectivityPublisher.publishToRecords(stockDto);
            logger.info("client order traded successfully");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public String callMallon(Order order, String mallonUrl) {
        return restTemplate.postForObject(mallonUrl, order, String.class);
    }

    public List sendStockToClientConnectivity(StockDto stockDto){
        String baseUrl = "http://localhost:8091/portfolio/add/";
        String attachedUrl = stockDto.getUniqueId() + "/"+ stockDto.getUserId();
        final String clientUrl = baseUrl+attachedUrl;
        return restTemplate.postForObject(clientUrl,stockDto,List.class);
    }

    public OrderDto convertToOrderDto(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OrderDto orderDto = objectMapper.readValue(message, OrderDto.class);
        return orderDto;
    }

    public Order convertToOrder(OrderDto orderDto){
        Order order = new Order();
        order.setPrice(orderDto.getPrice());
        order.setProduct(orderDto.getProduct());
        order.setSide(orderDto.getSide());
        return order;
    }

    public StockDto convertToStock(OrderDto orderDto){
        StockDto stockDto = new StockDto();
        stockDto.setOrderid(orderDto.getOrderId());
        stockDto.setUserId(orderDto.getUserId());
        return stockDto;
    }
}
