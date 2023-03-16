package com.memery.simons.adpaters;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.memery.simons.entities.MarketType;
import com.memery.simons.entities.Order;
import com.memery.simons.entities.OrderBook;
import com.memery.simons.ports.data_access.OrderBookGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class MaxOrderBookService implements OrderBookGateway {

    @Autowired
    private final HttpClient httpClient;

    public MaxOrderBookService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Mono<OrderBook> getLatestOrderBook(MarketType marketType) {
        return Mono.defer(() -> {
            try {
                var request = HttpRequest.newBuilder()
                        .uri(new URI(String.format("https://max-api.maicoin.com/api/v2/depth?market=%s",
                                marketType.toString().toLowerCase())))
                        .GET()
                        .build();
                var response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                return Mono.just(parseResponse(response.body()));
            } catch (URISyntaxException | IOException | InterruptedException e) {
                return Mono.error(e);
            }
        });
    }

    private OrderBook parseResponse(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        JsonArray bidsJsonArray = jsonObject.getAsJsonArray("bids");
        JsonArray asksJsonArray = jsonObject.getAsJsonArray("asks");
        List<Order> bids = StreamSupport.stream(bidsJsonArray.spliterator(), false)
                .map(item -> Order.builder()
                        .price(item.getAsJsonArray().get(0).getAsBigDecimal())
                        .volume(item.getAsJsonArray().get(1).getAsBigDecimal())
                        .build())
                .collect(Collectors.toList());
        List<Order> asks = StreamSupport.stream(asksJsonArray.spliterator(), false)
                .map(item -> Order.builder()
                        .price(item.getAsJsonArray().get(0).getAsBigDecimal())
                        .volume(item.getAsJsonArray().get(1).getAsBigDecimal())
                        .build())
                .collect(Collectors.toList());
        return OrderBook.builder().bids(bids).asks(asks).time(Instant.now()).build();
    }
}
