package rental.infrastructure.mapper;

import rental.domain.model.PromotionProposal;
import rental.infrastructure.dataentity.PromotionProposalEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EntityToModelMapper {

    EntityToModelMapper INSTANCE = Mappers.getMapper(EntityToModelMapper.class);

    PromotionProposal mapToModel(PromotionProposalEntity entity);

}
