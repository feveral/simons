package com.memery.simons;

import com.memery.simons.entities.MarketType;
import com.memery.simons.entities.Profit;
import com.memery.simons.usecases.ArbitrageTrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.memery.simons" })
public class SimonsApplication implements CommandLineRunner {

	@Autowired
	private ArbitrageTrade arbitrageTrade;

	public static void main(String[] args) {
		SpringApplication.run(SimonsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		while (true) {
			Profit profit = arbitrageTrade.getProfit(MarketType.ETHUSDT).block();
			System.out.printf("buyPrice: %s%n", profit.getBuyPrice());
			System.out.printf("salePrice: %s%n", profit.getSalePrice());
			System.out.printf("profit: %s%n", profit.getPoint());
			System.out.println("----------------------------");
		}
	}
}
