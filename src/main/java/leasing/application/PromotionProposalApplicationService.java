package leasing.application;

import leasing.domain.model.PromotionProposal;
import leasing.domain.repository.PromotionProposalRepository;
import leasing.presentation.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PromotionProposalApplicationService {
    private final PromotionProposalRepository promotionProposalRepository;

    public PromotionProposal queryPromotionProposal(Long id) {
        return promotionProposalRepository.findById(id)
                                          .orElseThrow(() ->
                new NotFoundException("PROMOTION_PROPOSAL_NOT_FOUND", "cannot found promotion proposal id: " + id));
    }

    public Page<PromotionProposal> queryAllPromotionProposal(Pageable pageable) {
        return promotionProposalRepository.queryAllPromotionProposal(pageable);
    }
}
