package uk.gegc.shoppingcart.service.order;

import uk.gegc.shoppingcart.model.Order;

public interface IOrderService {
    Order placeOrder(Long userId);
    Order getOrder(Long orderId);
}
