package rental.client.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import rental.client.model.HouseDto;
import rental.domain.model.House;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ModelToClientDtoMapper {

    ModelToClientDtoMapper INSTANCE = Mappers.getMapper(ModelToClientDtoMapper.class);

    HouseDto mapToClientDto(House house);

}
