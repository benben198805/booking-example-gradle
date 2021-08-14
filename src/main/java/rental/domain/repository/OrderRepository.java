package rental.domain.repository;

import rental.domain.model.Order;

import java.util.Optional;

public interface OrderRepository {
    Optional<Order> findOrderById(Long id);
}
