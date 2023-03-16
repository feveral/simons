package com.memery.simons.adapters

import com.memery.simons.adpaters.BinanceOrderBookService
import com.memery.simons.entities.MarketType
import com.memery.simons.entities.Order
import spock.lang.Specification

import java.net.http.HttpClient
import java.net.http.HttpResponse

class BinanceOrderBookServiceTest extends Specification {

    def "test getting response from binance depth api"() {
        given:
        def responseData = """{"lastUpdateId": 35146006914, "bids":[["24289.83000000","0.14812000"]],"asks": [["24289.82000000","0.06805000"]]}"""
        def httpClient = Mock(HttpClient) {
            send(_, _) >> Mock(HttpResponse<String>) {
                body() >> responseData
            }
        }
        def sut = new BinanceOrderBookService(httpClient)

        when:
        def orderBook = sut.getLatestOrderBook(MarketType.BTCUSDT).block()

        then:
        orderBook.getBids() == [Order.builder().price(24289.83000000).volume(0.14812000).build()]
        orderBook.getAsks() == [Order.builder().price(24289.82000000).volume(0.06805000).build()]
    }
}
