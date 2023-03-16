package com.memery.simons.entities;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Builder
@Value
public class Profit {

    private BigDecimal buyPrice;
    private BigDecimal salePrice;

    public BigDecimal getPoint() {
        return salePrice.subtract(buyPrice);
    }
}
