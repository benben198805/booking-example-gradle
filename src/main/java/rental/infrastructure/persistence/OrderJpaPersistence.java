package rental.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import rental.infrastructure.dataentity.ETicketEntity;
import rental.infrastructure.dataentity.OrderEntity;

public interface OrderJpaPersistence extends JpaRepository<OrderEntity, Long> {
}
