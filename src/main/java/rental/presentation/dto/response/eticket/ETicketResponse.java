package rental.presentation.dto.response.eticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rental.domain.model.enums.ETicketStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ETicketResponse {
    private Long id;

    private Long orderId;

    private ETicketStatus status;

    private String callbackId;

    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
