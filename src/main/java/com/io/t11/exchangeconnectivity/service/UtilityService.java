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

@Service
public class UtilityService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    IExchangeConnectivityPublisher exchangeConnectivityPublisher;

    public UtilityService(RestTemplate restTemplate, IExchangeConnectivityPublisher exchangeConnectivityPublisher, String uid) {
        this.restTemplate = restTemplate;
        this.exchangeConnectivityPublisher = exchangeConnectivityPublisher;
        this.uid = uid;
    }

    private static Logger logger = LoggerFactory.getLogger((UtilityService.class));
    String uid = null;
    private final String clientUrl = "";


    public void tradeOnExchange1(Order order,StockDto stockDto, int quantity){
        order.setQuantity(quantity);
        stockDto.setQuantity(quantity);
        stockDto.setExchangeName(ExchangeDetails.EXCHANGE_1.getExchangeName());
        tradeStep(order,stockDto,ExchangeDetails.EXCHANGE_1.getUrl());
    }

    public void tradeOnExchange2(Order order,StockDto stockDto, int quantity){
        order.setQuantity(quantity);
        stockDto.setQuantity(quantity);
        stockDto.setExchangeName(ExchangeDetails.EXCHANGE_2.getExchangeName());
        tradeStep(order,stockDto,ExchangeDetails.EXCHANGE_2.getUrl());
    }

    public void tradeStep(Order order, StockDto stockDto, String url){
        try{
            uid = callMallon(order,url);
            stockDto.setUid(uid);
//          sendStockToClientConnectivity(stockDto);
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

    public String sendStockToClientConnectivity(StockDto stockDto){
        return restTemplate.postForObject(clientUrl,stockDto,String.class);
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
        stockDto.setPrice(orderDto.getPrice());
        stockDto.setProduct(orderDto.getProduct());
        stockDto.setSide(orderDto.getSide());
        return stockDto;
    }
}
