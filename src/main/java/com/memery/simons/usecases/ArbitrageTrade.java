package com.memery.simons.usecases;

import com.memery.simons.entities.MarketType;
import com.memery.simons.entities.OrderBook;
import com.memery.simons.entities.Profit;
import com.memery.simons.ports.data_access.OrderBookGateway;
import groovy.util.logging.Slf4j;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Getter
@Component
public class ArbitrageTrade {

    private final OrderBookGateway binanceOrderBookService;
    private final OrderBookGateway maxOrderBookService;

    public ArbitrageTrade(@Autowired @Qualifier("maxOrderBookService") OrderBookGateway binanceOrderBookService,
                          @Autowired @Qualifier("maxOrderBookService") OrderBookGateway maxOrderBookService) {
        this.binanceOrderBookService = binanceOrderBookService;
        this.maxOrderBookService = maxOrderBookService;
    }

    public Mono<Profit> getProfit(MarketType marketType) {
        return Mono.zip(this.binanceOrderBookService.getLatestOrderBook(marketType),
                this.maxOrderBookService.getLatestOrderBook(marketType))
            .map(tuple -> calculateProfit(tuple.getT1(), tuple.getT2()));
    }

    private Profit calculateProfit(OrderBook binanceOrderBook, OrderBook maxOrderBook) {
        System.out.printf("Binance lowest price ask: %s%n", binanceOrderBook.getLowestPriceAsk().getPrice());
        System.out.printf("Binance highest price bid: %s%n", binanceOrderBook.getHighestPriceBid().getPrice());
        System.out.printf("Max lowest price ask: %s%n", maxOrderBook.getLowestPriceAsk().getPrice());
        System.out.printf("Max highest price bid: %s%n", maxOrderBook.getHighestPriceBid().getPrice());

        List<OrderBook> orderBooks = List.of(binanceOrderBook, maxOrderBook);
        BigDecimal lowestPrice = orderBooks.stream()
                .min(Comparator.comparing(orderBook -> orderBook.getLowestPriceAsk().getPrice()))
                .map(orderBook -> orderBook.getLowestPriceAsk().getPrice()).orElseThrow();
        BigDecimal highestPrice = orderBooks.stream()
                .max(Comparator.comparing(orderBook -> orderBook.getHighestPriceBid().getPrice()))
                .map(orderBook -> orderBook.getHighestPriceBid().getPrice()).orElseThrow();
        return Profit.builder().buyPrice(lowestPrice).salePrice(highestPrice).build();
    }
}
