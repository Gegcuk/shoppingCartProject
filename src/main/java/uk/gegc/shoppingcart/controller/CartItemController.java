package uk.gegc.shoppingcart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gegc.shoppingcart.response.APIResponse;
import uk.gegc.shoppingcart.service.cart.CartItemService;
import uk.gegc.shoppingcart.service.cart.ICartItemService;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prfix}/cartitems")
public class CartItemController {
    private final ICartItemService cartItemService;

    @PostMapping("/item")
    public ResponseEntity<APIResponse> addItemToCart(@RequestParam Long cartId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity){
        try {
            cartItemService.addItemToCart(cartId, productId, quantity);
            return ResponseEntity.ok(new APIResponse("Item added", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/item")
    public ResponseEntity<APIResponse> removeItemFromCart(@RequestParam Long cartId,
                                                          @RequestParam Long productId){
        try {
            cartItemService.removeIteFromCart(cartId, productId);
            return ResponseEntity.ok(new APIResponse("Item removed", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<APIResponse> updateQuantity(@PathVariable Long cartId,
                                                      @RequestParam Long productId,
                                                      @RequestParam Integer quantity){
        try {
            cartItemService.updateItemQuantity(cartId, productId, quantity);
            return ResponseEntity.ok(new APIResponse("Quantity updated", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }
}
