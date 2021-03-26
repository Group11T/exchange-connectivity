package com.io.t11.exchangeconnectivity.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.io.t11.exchangeconnectivity.error.MalFormedOrderException;
import com.io.t11.exchangeconnectivity.model.ExchangeDetails;
import com.io.t11.exchangeconnectivity.model.Order;
import com.io.t11.exchangeconnectivity.model.OrderDto;
import com.io.t11.exchangeconnectivity.model.StockDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ExchangeService implements Runnable{

    private static Logger logger = LoggerFactory.getLogger((OrderQueueSubscriber.class));

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private JedisPool jedisPool;

    private static final String QUEUE = "orderQueue";
    private static final int TIMEOUT = 0;
    private final String clientUrl = "";

    public ExchangeService(RestTemplate restTemplate, JedisPool jedisPool) {
        this.restTemplate =  restTemplate;
        this.jedisPool = jedisPool;
        System.out.println();
    }

    public CompletableFuture<String> checkForMessages() throws InterruptedException, JsonProcessingException, MalFormedOrderException {
        List<String> messages;
        String uid = null;
        logger.info("Waiting for a message in the queue");

        messages = jedisPool.getResource().blpop(TIMEOUT, QUEUE);
        OrderDto orderDto = convertToOrderDto(messages.get(1));
        Order order = convertToOrder(orderDto);
        StockDto stockDto = convertToStock(orderDto);

        Integer exchangeQuantity1=orderDto.getExchangeNumber1().get(ExchangeDetails.EXCHANGE_1.getExchangeName());
        Integer exchangeQuantity2=orderDto.getExchangeNumber2().get(ExchangeDetails.EXCHANGE_2.getExchangeName());

        //process to corect exchage
        if(exchangeQuantity1 !=null){
            order.setQuantity(exchangeQuantity1);
            try{
                uid = callMallon(order,ExchangeDetails.EXCHANGE_1.getUrl());
                stockDto.setUid(uid);
                stockDto.setQuantity(exchangeQuantity1);
                stockDto.setExchangeName(ExchangeDetails.EXCHANGE_1.getExchangeName());
//                sendStockToClientConnectivity(stockDto);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(exchangeQuantity2 != null){
            order.setQuantity(exchangeQuantity2);
            try{
                uid = callMallon(order,ExchangeDetails.EXCHANGE_2.getUrl());
                stockDto.setUid(uid);
                stockDto.setQuantity(exchangeQuantity2);
                stockDto.setExchangeName(ExchangeDetails.EXCHANGE_2.getExchangeName());
//                sendStockToClientConnectivity(stockDto);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        else{
            throw new MalFormedOrderException("The order has no quantity assigned");
        }

        logger.info("client order traded successfully");
        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(uid);
    }

    public String callMallon(Order order,String mallonUrl) {
        return restTemplate.postForObject(mallonUrl, order, String.class);
    }

    public String sendStockToClientConnectivity(StockDto stockDto){
        return restTemplate.postForObject(clientUrl,stockDto,String.class);
    }

    private OrderDto convertToOrderDto(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OrderDto orderDto = objectMapper.readValue(message, OrderDto.class);
        return orderDto;
    }

    private Order convertToOrder(OrderDto orderDto){
        Order order = new Order();
        order.setPrice(orderDto.getPrice());
        order.setProduct(orderDto.getProduct());
        order.setSide(orderDto.getSide());
        return order;
    }

    private StockDto convertToStock(OrderDto orderDto){
        StockDto stockDto = new StockDto();
        stockDto.setPrice(orderDto.getPrice());
        stockDto.setProduct(orderDto.getProduct());
        stockDto.setSide(orderDto.getSide());
        return stockDto;
    }

    @Override
    public void run() {
        try {
            checkForMessages();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        catch (MalFormedOrderException e){
            e.printStackTrace();
        }
    }
}
