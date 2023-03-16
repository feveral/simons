package com.memery.simons.entities

import spock.lang.Specification

class OrderBookTest extends Specification {

    def "test getting lowest ask order"() {
        given:
        def asks = [
                Order.builder().price(new BigDecimal(30)).volume(new BigDecimal(1)).build(),
                Order.builder().price(new BigDecimal(10)).volume(new BigDecimal(3)).build(),
                Order.builder().price(new BigDecimal(20)).volume(new BigDecimal(2)).build()
        ]
        def sut = OrderBook.builder().bids([]).asks(asks).build()

        when:
        def ask = sut.getLowestPriceAsk()

        then:
        ask == Order.builder().price(new BigDecimal(10)).volume(new BigDecimal(3)).build()
    }

    def "test getting highest price bid order"() {
        given:
        def bids = [
                Order.builder().price(new BigDecimal(30)).volume(new BigDecimal(1)).build(),
                Order.builder().price(new BigDecimal(10)).volume(new BigDecimal(3)).build(),
                Order.builder().price(new BigDecimal(20)).volume(new BigDecimal(2)).build()
        ]
        def sut = OrderBook.builder().bids(bids).asks([]).build()

        when:
        def bid = sut.getHighestPriceBid()

        then:
        bid == Order.builder().price(new BigDecimal(30)).volume(new BigDecimal(1)).build()
    }
}
