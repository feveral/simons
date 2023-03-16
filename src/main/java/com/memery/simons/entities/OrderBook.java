package com.memery.simons.entities;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@Builder
@Value
public class OrderBook {
    private List<Order> bids;
    private List<Order> asks;
    private Instant time;

    public Order getHighestPriceBid() {
        return bids.stream()
                .max(Comparator.comparing(Order::getPrice))
                .orElse(null);
    }

    public Order getLowestPriceAsk() {
        return asks.stream()
                .min(Comparator.comparing(Order::getPrice))
                .orElse(null);
    }
}
