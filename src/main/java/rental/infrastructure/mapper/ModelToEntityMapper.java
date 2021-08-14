package rental.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import rental.domain.model.ETicket;
import rental.infrastructure.dataentity.ETicketEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ModelToEntityMapper {
    ModelToEntityMapper INSTANCE = Mappers.getMapper(ModelToEntityMapper.class);

    ETicketEntity mapToEntity(ETicket eTicket);
}
