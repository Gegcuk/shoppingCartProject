package uk.gegc.shoppingcart.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private Long userId;
    private LocalDateTime orderDate;;
    private BigDecimal totalAmount;
    private String status;
    private List<OrderItemDTO> items;

}
