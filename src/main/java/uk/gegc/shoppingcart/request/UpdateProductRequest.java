package uk.gegc.shoppingcart.request;

import lombok.Data;
import uk.gegc.shoppingcart.model.Category;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
