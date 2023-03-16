package com.memery.simons.ports.data_access;

import com.memery.simons.entities.MarketType;
import com.memery.simons.entities.OrderBook;
import reactor.core.publisher.Mono;

public interface OrderBookGateway {
    Mono<OrderBook> getLatestOrderBook(MarketType marketType);
}
