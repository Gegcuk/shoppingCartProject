package uk.gegc.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gegc.shoppingcart.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
