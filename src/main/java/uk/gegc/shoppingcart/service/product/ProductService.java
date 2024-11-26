package uk.gegc.shoppingcart.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.gegc.shoppingcart.exception.ResourceNotFoundException;
import uk.gegc.shoppingcart.model.Category;
import uk.gegc.shoppingcart.model.Product;
import uk.gegc.shoppingcart.repository.CategoryRepository;
import uk.gegc.shoppingcart.repository.ProductRepository;
import uk.gegc.shoppingcart.request.AddProductRequest;
import uk.gegc.shoppingcart.request.UpdateProductRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName())).orElseGet(() -> {
            Category newCategory = new Category(request.getCategory().getName());
            return categoryRepository.save(newCategory);
        });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("Product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete, ()-> {throw new ResourceNotFoundException("Product not found!");});
    }

    @Override
    public Product updateProduct(UpdateProductRequest product, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, product))
                .map(productRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest productUpdateRequest){
        existingProduct.setName(productUpdateRequest.getName());
        existingProduct.setBrand(productUpdateRequest.getBrand());
        existingProduct.setPrice(productUpdateRequest.getPrice());
        existingProduct.setInventory(productUpdateRequest.getInventory());
        existingProduct.setDescription(productUpdateRequest.getDescription());

        Category category = categoryRepository.findByName(productUpdateRequest.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategoryName(category);
        if(products.isEmpty()) throw new ResourceNotFoundException("Products not found");
        else return products;
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        List<Product> products = productRepository.findByBrand(brand);
        if(products.isEmpty()) throw new ResourceNotFoundException("Products not found");
        else return products;
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        List<Product> products = productRepository.findByCategoryNameAndBrand(category, brand);
        if(products.isEmpty()) throw new ResourceNotFoundException("Products not found");
        else return products;
    }

    @Override
    public List<Product> getProductsByName(String name) {
        List<Product> products = productRepository.findByCategoryName(name);
        if(products.isEmpty()) throw new ResourceNotFoundException("Products not found");
        else return products;
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        List<Product> products = productRepository.findByBrandAndName(brand, name);
        if(products.isEmpty()) throw new ResourceNotFoundException("Products not found");
        else return products;
    }

    @Override
    public List<Product> getProductsByCategoryAndName(String category, String name) {
        List<Product> products = productRepository.findByCategoryNameAndName(category, name);
        if(products.isEmpty()) throw new ResourceNotFoundException("Products not found");
        else return products;
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
