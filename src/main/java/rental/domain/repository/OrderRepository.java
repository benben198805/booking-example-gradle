package rental.domain.repository;

import rental.domain.model.Order;

public interface OrderRepository {
    Order findOrderById(Order order);
}
