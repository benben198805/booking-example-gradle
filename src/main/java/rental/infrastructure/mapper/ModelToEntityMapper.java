package rental.infrastructure.mapper;

import rental.domain.model.PromotionProposal;
import rental.infrastructure.dataentity.PromotionProposalEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ModelToEntityMapper {

    PromotionProposalEntity mapToEntity(PromotionProposal promotionProposal);

}
