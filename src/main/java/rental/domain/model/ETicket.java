package rental.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rental.domain.model.enums.ETicketStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ETicket {
    private Long id;

    private Long orderId;

    private ETicketStatus status;

    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    public static ETicket init(Long orderId) {
        return ETicket.builder()
                      .orderId(orderId)
                      .status(ETicketStatus.PENDING)
                      .build();
    }
}
