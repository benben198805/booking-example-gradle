package rental.domain.repository;

import rental.domain.model.PromotionProposal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PromotionProposalRepository {
    Optional<PromotionProposal> findById(Long id);

    Page<PromotionProposal> queryAllPromotionProposal(Pageable pageable);
}
