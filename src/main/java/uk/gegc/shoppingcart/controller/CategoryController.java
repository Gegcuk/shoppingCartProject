package uk.gegc.shoppingcart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gegc.shoppingcart.exception.ResourceNotFoundException;
import uk.gegc.shoppingcart.model.Category;
import uk.gegc.shoppingcart.response.APIResponse;
import uk.gegc.shoppingcart.service.category.ICategoryService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<APIResponse> getAllCategories(){
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new APIResponse("Found!", categories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("")
    public ResponseEntity<APIResponse> addCategory(@RequestBody Category category){
        try {
            Category addedCategory = categoryService.addCategory(category);
            return ResponseEntity.ok(new APIResponse("Success!", addedCategory));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<APIResponse> getCategoryById(@PathVariable Long categoryId){
        try {
            Category category = categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(new APIResponse("Found", category));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping(params = "name")
    public ResponseEntity<APIResponse> getCategoryByName(@RequestParam String name){
        try {
            Category category = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new APIResponse("Found", category));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<APIResponse> deleteCategory(@PathVariable Long categoryId){
        try{
            categoryService.deleteCategoryById(categoryId);
            return ResponseEntity.ok(new APIResponse("Deleted", null));
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<APIResponse> updateCategory(@PathVariable Long categoryId, @RequestBody Category category){
        try{
            Category updatedCategory = categoryService.updateCategory(category, categoryId);
            return ResponseEntity.ok(new APIResponse("Category updated", updatedCategory));
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

}
