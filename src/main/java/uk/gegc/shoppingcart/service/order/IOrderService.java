package uk.gegc.shoppingcart.service.order;

import uk.gegc.shoppingcart.dto.OrderDTO;
import uk.gegc.shoppingcart.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDTO getOrder(Long orderId);

    List<OrderDTO> getUserOrders(Long userId);
}
