package uk.gegc.shoppingcart.dto;

import lombok.Data;
import uk.gegc.shoppingcart.model.Category;
import uk.gegc.shoppingcart.model.Image;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
    private List<ImageDTO> images;
}
