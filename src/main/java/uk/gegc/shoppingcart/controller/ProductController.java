package uk.gegc.shoppingcart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;
import uk.gegc.shoppingcart.dto.ProductDTO;
import uk.gegc.shoppingcart.exception.ResourceNotFoundException;
import uk.gegc.shoppingcart.model.Product;
import uk.gegc.shoppingcart.request.AddProductRequest;
import uk.gegc.shoppingcart.request.UpdateProductRequest;
import uk.gegc.shoppingcart.response.APIResponse;
import uk.gegc.shoppingcart.service.product.IProductService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;

    @GetMapping("")
    public ResponseEntity<APIResponse> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> productDTOList = productService.getConvertedProductDTOs(products);
        return ResponseEntity.ok(new APIResponse("success", productDTOList));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<APIResponse> getProductById(@PathVariable Long productId){
        try {
            Product product = productService.getProductById(productId);
            ProductDTO productDTO = productService.convertToDTO(product);
            return ResponseEntity.ok(new APIResponse("Success", productDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PostMapping("")
    public ResponseEntity<APIResponse> addProduct(@RequestBody AddProductRequest product){
        try {
            Product savedProduct = productService.addProduct(product);
            return ResponseEntity.ok(new APIResponse("Product successfully added", savedProduct));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<APIResponse> updateProduct(@RequestBody UpdateProductRequest productRequest, @PathVariable Long productId){
        try {
            Product updatedProduct = productService.updateProduct(productRequest, productId);
            return ResponseEntity.ok(new APIResponse("Product is updated.", updatedProduct));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<APIResponse> deleteProduct(@PathVariable Long productId){
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new APIResponse("Product is removed", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/filter/name")
    public ResponseEntity<APIResponse> getProductsByName(@RequestParam(name = "name") String name){
        try {
            List<Product> productList = productService.getProductsByName(name);
            List<ProductDTO> productDTOList = productService.getConvertedProductDTOs(productList);

            return ResponseEntity.ok(new APIResponse("Success", productDTOList));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/filter/brand")
    public ResponseEntity<APIResponse> getProductByBrand(@RequestParam(name = "brand") String brand){
        try {
            List<Product> productList = productService.getProductsByBrand(brand);
            List<ProductDTO> productDTOList = productService.getConvertedProductDTOs(productList);

            return ResponseEntity.ok(new APIResponse("Success", productDTOList));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/filter/category")
    public ResponseEntity<APIResponse> getProductByCategory(@RequestParam(name = "category") String category){
        try {
            List<Product> productList = productService.getProductsByCategory(category);
            List<ProductDTO> productDTOList = productService.getConvertedProductDTOs(productList);

            return ResponseEntity.ok(new APIResponse("Success", productDTOList));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/filter/brand-and-name")
    public ResponseEntity<APIResponse> getProductByBrandAndName(@RequestParam(name = "brand") String brand,
                                                                @RequestParam(name = "name") String name){
        try {
            List<Product> productList = productService.getProductsByBrandAndName(brand, name);
            List<ProductDTO> productDTOList = productService.getConvertedProductDTOs(productList);

            return ResponseEntity.ok(new APIResponse("Success", productDTOList));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/filter/category-and-name")
    public ResponseEntity<APIResponse> getProductByCategoryAndName(@RequestParam(name = "category", required = false) String category,
                                                                @RequestParam(name = "name", required = false) String name){
        try {
            List<Product> productList = productService.getProductsByCategoryAndName(category, name);
            List<ProductDTO> productDTOList = productService.getConvertedProductDTOs(productList);

            return ResponseEntity.ok(new APIResponse("Success", productDTOList));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/filter/category-and-brand")
    public ResponseEntity<APIResponse> getProductByCategoryAndBrand(@RequestParam(name = "category", required = false) String category,
                                                                   @RequestParam(name = "brand", required = false) String brand){
        try {
            List<Product> productList = productService.getProductsByCategoryAndBrand(category, brand);
            List<ProductDTO> productDTOList = productService.getConvertedProductDTOs(productList);

            return ResponseEntity.ok(new APIResponse("Success", productDTOList));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetExchange("/count/brand-and-name")
    public ResponseEntity<APIResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name){
        try{
            var productCount = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new APIResponse("Product count!", productCount));
        } catch (Exception e){
            return ResponseEntity.ok(new APIResponse(e.getMessage(), null));
        }
    }


}
