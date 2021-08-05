package rental.domain.model;

import rental.domain.model.enums.PromotionProposalStatus;
import rental.domain.model.enums.PromotionProposalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PromotionProposal {
    private Long id;

    private String proposalNo;

    private String name;

    private LocalDateTime establishedTime;

    private PromotionProposalType type;
    private PromotionProposalStatus status;

    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
