package com.memery.simons.entities;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.math.BigDecimal;

@Builder
@Value
public class Order {
    private BigDecimal price;
    private BigDecimal volume;
}
