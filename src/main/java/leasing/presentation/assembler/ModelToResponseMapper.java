package leasing.presentation.assembler;

import leasing.domain.model.PromotionProposal;
import leasing.presentation.dto.response.promotionProposal.PromotionProposalResponse;
import leasing.utils.DateUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ModelToResponseMapper {

    ModelToResponseMapper INSTANCE = Mappers.getMapper(ModelToResponseMapper.class);

    @Mapping(target = "establishedTime", source = "model.establishedTime", qualifiedByName = "toTimestamp")
    @Mapping(target = "createdTime", source = "model.createdTime", qualifiedByName = "toTimestamp")
    @Mapping(target = "updatedTime", source = "model.createdTime", qualifiedByName = "toTimestamp")
    PromotionProposalResponse mapToPromotionProposalResponse(PromotionProposal model);

    @Named("toTimestamp")
    default long toTimestamp(LocalDateTime localDateTime) {
        return DateUtils.toTimestamp(localDateTime);
    }
}
