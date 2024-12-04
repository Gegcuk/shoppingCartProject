package uk.gegc.shoppingcart.service.order;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uk.gegc.shoppingcart.dto.OrderDTO;
import uk.gegc.shoppingcart.enums.OrderStatus;
import uk.gegc.shoppingcart.exception.ResourceNotFoundException;
import uk.gegc.shoppingcart.model.Cart;
import uk.gegc.shoppingcart.model.Order;
import uk.gegc.shoppingcart.model.OrderItem;
import uk.gegc.shoppingcart.model.Product;
import uk.gegc.shoppingcart.repository.OrderRepository;
import uk.gegc.shoppingcart.repository.ProductRepository;
import uk.gegc.shoppingcart.service.cart.CartService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;


    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order, cart) ;
        order.setOrderItemSet(new HashSet<>(orderItemList));
        order.setOrderTotalAmount(calculateTotalAmount(orderItemList));
        Order savedOrder = orderRepository.save(order);

        cartService.clearCart(cart.getId());

        return savedOrder;
    }

    private Order createOrder(Cart cart){
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
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
    public OrderDTO getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<OrderDTO> getUserOrders(Long userId){

        return  orderRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    private OrderDTO convertToDTO(Order order){
        return modelMapper.map(order, OrderDTO.class);
    }
}
