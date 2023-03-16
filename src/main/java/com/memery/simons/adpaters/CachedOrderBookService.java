package com.memery.simons.adpaters;

import com.memery.simons.entities.MarketType;
import com.memery.simons.entities.OrderBook;
import com.memery.simons.ports.data_access.OrderBookGateway;
import reactor.core.publisher.Mono;

public class CachedOrderBookService implements OrderBookGateway {

    private final OrderBookGateway implementation;
    private OrderBook orderBook;

    public CachedOrderBookService(OrderBookGateway implementation) {
        this.implementation = implementation;
    }

    @Override
    public Mono<OrderBook> getLatestOrderBook(MarketType marketType) {
        return implementation.getLatestOrderBook(marketType);
    }
}
