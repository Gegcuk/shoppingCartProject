package uk.gegc.shoppingcart.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDTO {
    private Long productId;
    private String productName;
    private int quantity;;
    private BigDecimal price;

}
