package rental.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import rental.domain.model.ETicket;
import rental.domain.model.Order;
import rental.infrastructure.dataentity.ETicketEntity;
import rental.infrastructure.dataentity.OrderEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ModelToEntityMapper {
    ModelToEntityMapper INSTANCE = Mappers.getMapper(ModelToEntityMapper.class);

    ETicketEntity mapToEntity(ETicket eTicket);

    OrderEntity mapToEntity(Order order);
}
