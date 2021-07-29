package leasing.infrastructure.dataentity;

import leasing.domain.model.enums.PromotionProposalStatus;
import leasing.domain.model.enums.PromotionProposalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "promotion_proposal")
@EntityListeners(AuditingEntityListener.class)
public class PromotionProposalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String proposalNo;
    private String name;

    private LocalDateTime establishedTime;

    @Enumerated(EnumType.STRING)
    private PromotionProposalStatus status;

    @Enumerated(EnumType.STRING)
    private PromotionProposalType type;

    @CreatedDate
    private LocalDateTime createdTime;
    @LastModifiedDate
    private LocalDateTime updatedTime;
}
