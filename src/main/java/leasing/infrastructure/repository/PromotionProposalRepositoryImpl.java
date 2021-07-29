package leasing.infrastructure.repository;

import leasing.domain.model.PromotionProposal;
import leasing.domain.repository.PromotionProposalRepository;
import leasing.infrastructure.mapper.EntityToModelMapper;
import leasing.infrastructure.persistence.PromotionProposalJpaPersistence;
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

    private final PromotionProposalJpaPersistence persistence;

    @Override
    public Optional<PromotionProposal> findById(Long id) {
        return persistence.findById(id).map(EntityToModelMapper.INSTANCE::mapToModel);
    }

    @Override
    public Page<PromotionProposal> queryAllPromotionProposal(Pageable pageable) {
        return persistence.findAll(pageable)
                          .map(EntityToModelMapper.INSTANCE::mapToModel);
    }
}
