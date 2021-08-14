package rental.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import rental.infrastructure.dataentity.ETicketEntity;

public interface ETicketJpaPersistence extends JpaRepository<ETicketEntity, Long> {
}
