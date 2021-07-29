package leasing.infrastructure.persistence;

import leasing.infrastructure.dataentity.PromotionProposalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionProposalJpaPersistence extends
        JpaRepository<PromotionProposalEntity, Long> {
}
