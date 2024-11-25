package uk.gegc.shoppingcart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
        return ResponseEntity.ok(new APIResponse("success", products));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<APIResponse> getProductById(@PathVariable Long productId){
        try {
            Product product = productService.getProductById(productId);
            return ResponseEntity.ok(new APIResponse("Success", product));
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


    @GetMapping("/filter")
    public ResponseEntity<APIResponse> getProductByBrandAndName(@RequestParam(name = "brand", required = false) String brand,
                                                                @RequestParam(name = "name", required = false) String name){
        try {
            List<Product> productList = productService.getProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new APIResponse("Success", productList));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }
}
