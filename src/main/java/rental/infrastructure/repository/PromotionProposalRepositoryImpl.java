package rental.infrastructure.repository;

import rental.domain.model.PromotionProposal;
import rental.domain.repository.PromotionProposalRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class PromotionProposalRepositoryImpl implements PromotionProposalRepository {

    @Override
    public Optional<PromotionProposal> findById(Long id) {
        return null;
    }

    @Override
    public Page<PromotionProposal> queryAllPromotionProposal(Pageable pageable) {
        return null;
    }
}
