package leasing.infrastructure.mapper;

import leasing.domain.model.PromotionProposal;
import leasing.infrastructure.dataentity.PromotionProposalEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ModelToEntityMapper {

    PromotionProposalEntity mapToEntity(PromotionProposal promotionProposal);

}
