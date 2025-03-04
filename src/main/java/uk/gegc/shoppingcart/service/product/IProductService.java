package uk.gegc.shoppingcart.service.product;

import uk.gegc.shoppingcart.dto.ProductDTO;
import uk.gegc.shoppingcart.model.Product;
import uk.gegc.shoppingcart.request.AddProductRequest;
import uk.gegc.shoppingcart.request.UpdateProductRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest request);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(UpdateProductRequest product, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    List<Product> getProductsByCategoryAndName(String category, String name);
    Long countProductsByBrandAndName(String brand, String name);

    List<ProductDTO> getConvertedProductDTOs(List<Product> products);

    ProductDTO convertToDTO(Product product);
}
