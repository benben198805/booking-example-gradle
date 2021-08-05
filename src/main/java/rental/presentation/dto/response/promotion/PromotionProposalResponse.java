package rental.presentation.dto.response.promotion;

import rental.domain.model.enums.PromotionProposalType;
import rental.domain.model.enums.PromotionProposalStatus;
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
