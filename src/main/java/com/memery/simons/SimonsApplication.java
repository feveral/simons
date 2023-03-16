package com.memery.simons;

import com.memery.simons.entities.MarketType;
import com.memery.simons.ports.data_access.OrderBookGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimonsApplication implements CommandLineRunner {

	@Autowired
	@Qualifier("binanceOrderBookService")
	private OrderBookGateway binanceOrderBookService;

	@Autowired
	@Qualifier("maxOrderBookService")
	private OrderBookGateway maxOrderBookService;

	public static void main(String[] args) {
		SpringApplication.run(SimonsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var binanceOrderBook = binanceOrderBookService.getLatestOrderBook(MarketType.BTCUSDT).block();
		var maxOrderBook = maxOrderBookService.getLatestOrderBook(MarketType.BTCUSDT).block();
		System.out.println(binanceOrderBook);
		System.out.println(maxOrderBook);
	}
}
