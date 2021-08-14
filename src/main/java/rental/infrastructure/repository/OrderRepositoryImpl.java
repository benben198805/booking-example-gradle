package rental.infrastructure.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import rental.domain.model.Order;
import rental.domain.repository.OrderRepository;
import rental.infrastructure.mapper.EntityToModelMapper;
import rental.infrastructure.persistence.OrderJpaPersistence;

import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderJpaPersistence persistence;

    @Override
    public Optional<Order> findOrderById(Long id) {
        return persistence.findById(id).map(EntityToModelMapper.INSTANCE::mapToModel);
    }
}
