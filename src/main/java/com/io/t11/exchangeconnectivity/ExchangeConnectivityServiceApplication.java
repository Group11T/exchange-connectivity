package com.io.t11.exchangeconnectivity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@SpringBootApplication
public class ExchangeConnectivityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeConnectivityServiceApplication.class, args);
	}
}

