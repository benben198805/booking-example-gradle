package rental.infrastructure.mapper;

import rental.domain.model.House;
import rental.infrastructure.dataentity.HouseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ModelToEntityMapper {

    HouseEntity mapToEntity(House promotionProposal);

}
