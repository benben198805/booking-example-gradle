package leasing.presentation.dto.response.promotion;

import leasing.domain.model.enums.PromotionProposalType;
import leasing.domain.model.enums.PromotionProposalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PromotionProposalResponse {
    private Long id;

    private String proposalNo;

    private String name;

    private long establishedTime;

    private PromotionProposalType type;
    private PromotionProposalStatus status;

    private long createdTime;
    private long updatedTime;

}
