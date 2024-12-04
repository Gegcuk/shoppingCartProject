package uk.gegc.shoppingcart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gegc.shoppingcart.dto.OrderDTO;
import uk.gegc.shoppingcart.model.Order;
import uk.gegc.shoppingcart.response.APIResponse;
import uk.gegc.shoppingcart.service.order.IOrderService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;

    @PostMapping ("/order")
    public ResponseEntity<APIResponse> createOrder(Long userId){
        try {
            Order order = orderService.placeOrder(userId);
            return ResponseEntity.ok(new APIResponse("Success", order));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse("Error occured", e.getMessage()));
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<APIResponse> getOrderById(@PathVariable Long orderId){
        try {
            OrderDTO order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new APIResponse("Success", order));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse("Failed", e.getMessage()));
        }
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<APIResponse> getUserOrders(@PathVariable Long userId){
        try {
            List<OrderDTO> orders = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new APIResponse("Success", orders));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse("Failed", e.getMessage()));
        }
    }
}
