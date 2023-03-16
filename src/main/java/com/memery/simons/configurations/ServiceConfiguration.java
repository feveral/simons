package com.memery.simons.configurations;

import com.memery.simons.adpaters.BinanceOrderBookService;
import com.memery.simons.adpaters.MaxOrderBookService;
import com.memery.simons.ports.data_access.OrderBookGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
public class ServiceConfiguration {

    @Autowired
    private HttpClient httpClient;

    @Bean
    @Qualifier("binanceOrderBookService")
    public OrderBookGateway getBinanceOrderBookService() {
        return new BinanceOrderBookService(httpClient);
    }

    @Bean
    @Qualifier("maxOrderBookService")
    public OrderBookGateway getMaxOrderBookService() {
        return new MaxOrderBookService(httpClient);
    }
}
