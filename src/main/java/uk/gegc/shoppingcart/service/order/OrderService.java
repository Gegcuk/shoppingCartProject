package uk.gegc.shoppingcart.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.gegc.shoppingcart.exception.ResourceNotFoundException;
import uk.gegc.shoppingcart.model.Cart;
import uk.gegc.shoppingcart.model.Order;
import uk.gegc.shoppingcart.model.OrderItem;
import uk.gegc.shoppingcart.model.Product;
import uk.gegc.shoppingcart.repository.OrderRepository;
import uk.gegc.shoppingcart.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    @Override
    public Order placeOrder(Long userId) {
        return null;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart){
        List<OrderItem> orderItems = cart.getCartItems()
                .stream()
                .map(item -> {
                    Product product = item.getProduct();
                    product.setInventory(product.getInventory() - item.getQuantity());
                    productRepository.save(product);
                    return new OrderItem(order, product, item.getQuantity(), item.getUnitPrice());
                }).toList();
        return orderItems;
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems){
        BigDecimal totalAmount = orderItems
                .stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity()))).reduce(BigDecimal.ZERO ,BigDecimal::add);
        return totalAmount;
    }




    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }
}
