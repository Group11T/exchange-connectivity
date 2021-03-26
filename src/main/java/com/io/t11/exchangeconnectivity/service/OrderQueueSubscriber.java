package com.io.t11.exchangeconnectivity.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class OrderQueueSubscriber implements CommandLineRunner{

    private static Logger logger = LoggerFactory.getLogger((OrderQueueSubscriber.class));

    @Autowired
    ExchangeService exchangeService;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    public OrderQueueSubscriber(ExchangeService exchangeService, ThreadPoolTaskScheduler taskScheduler) {
        this.exchangeService = exchangeService;
        this.taskScheduler = taskScheduler;
    }

    @Override
    public void run(String... args) throws Exception {
        Runnable  thread = this.exchangeService;
        Runnable thread2= this.exchangeService;
        taskScheduler.scheduleWithFixedDelay( thread, 1000);
        taskScheduler.scheduleWithFixedDelay( thread2, 1000);
    }

}