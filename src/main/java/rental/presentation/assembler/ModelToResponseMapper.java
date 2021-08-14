package rental.presentation.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import rental.domain.model.ETicket;
import rental.presentation.dto.response.eticket.ETicketResponse;
import rental.utils.DateUtils;

import java.time.LocalDateTime;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ModelToResponseMapper {

    ModelToResponseMapper INSTANCE = Mappers.getMapper(ModelToResponseMapper.class);

    @Mapping(target = "createdTime", source = "model.createdTime", qualifiedByName = "toTimestamp")
    @Mapping(target = "updatedTime", source = "model.createdTime", qualifiedByName = "toTimestamp")
    ETicketResponse mapToETicketResponse(ETicket model);

    @Named("toTimestamp")
    default long toTimestamp(LocalDateTime localDateTime) {
        return DateUtils.toTimestamp(localDateTime);
    }
}
