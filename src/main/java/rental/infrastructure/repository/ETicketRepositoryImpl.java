package rental.infrastructure.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import rental.domain.model.ETicket;
import rental.domain.repository.ETicketRepository;
import rental.infrastructure.dataentity.ETicketEntity;
import rental.infrastructure.mapper.EntityToModelMapper;
import rental.infrastructure.mapper.ModelToEntityMapper;
import rental.infrastructure.persistence.ETicketJpaPersistence;

@Component
@Slf4j
@AllArgsConstructor
public class ETicketRepositoryImpl implements ETicketRepository {
    private final ETicketJpaPersistence persistence;

    @Override
    public ETicket create(ETicket house) {
        ETicketEntity savedETicketEntity = this.persistence.save(ModelToEntityMapper.INSTANCE.mapToEntity(house));
        return EntityToModelMapper.INSTANCE.mapToModel(savedETicketEntity);
    }
}
