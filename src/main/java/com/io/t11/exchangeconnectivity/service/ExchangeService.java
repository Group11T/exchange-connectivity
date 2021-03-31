package com.io.t11.exchangeconnectivity.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.io.t11.exchangeconnectivity.error.MalFormedOrderException;
import com.io.t11.exchangeconnectivity.model.ExchangeDetails;
import com.io.t11.exchangeconnectivity.model.Order;
import com.io.t11.exchangeconnectivity.model.OrderDto;
import com.io.t11.exchangeconnectivity.model.StockDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ExchangeService implements Runnable{

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    UtilityService utilityService;

    private static Logger logger = LoggerFactory.getLogger((OrderQueueSubscriber.class));
    private static final String QUEUE = "orderQueue";
    private static final int TIMEOUT = 0;

    public ExchangeService(JedisPool jedisPool, UtilityService utilityService) {
        this.jedisPool = jedisPool;
        this.utilityService = utilityService;
    }

    public CompletableFuture<String> checkForMessages() throws InterruptedException, JsonProcessingException, MalFormedOrderException {
        logger.info("Waiting for a message in the queue");

        List<String> messages = jedisPool.getResource().blpop(TIMEOUT, QUEUE);
        OrderDto orderDto = utilityService.convertToOrderDto(messages.get(1));
        Order order = utilityService.convertToOrder(orderDto);
        StockDto stockDto = utilityService.convertToStock(orderDto);

        Integer exchangeQuantity1= orderDto.getTradeDetails().get(ExchangeDetails.EXCHANGE_1.getExchangeName());
        Integer exchangeQuantity2= orderDto.getTradeDetails().get(ExchangeDetails.EXCHANGE_2.getExchangeName());

        //process order to correct exchange
        if(exchangeQuantity1 > 0){
            utilityService.tradeOnExchange1(order,stockDto,exchangeQuantity1);
        }

        if(exchangeQuantity2 > 0){
           utilityService.tradeOnExchange2(order,stockDto,exchangeQuantity2);
        }

        if(exchangeQuantity1 ==0 && exchangeQuantity2 == 0){
            throw new MalFormedOrderException("The order has no quantity assigned");
        }

        Thread.sleep(1000L);
        return CompletableFuture.completedFuture("Trades successful");
    }


    @Override
    public void run() {
        try {
            checkForMessages();
        } catch (InterruptedException e) {
            logger.error("Process interrupted",e);
        } catch (JsonProcessingException e) {
            logger.error("Error processing Json",e);
        }
        catch (MalFormedOrderException e){
            logger.error("order not well formed",e);
        }
    }
}
