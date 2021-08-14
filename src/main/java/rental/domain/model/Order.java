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
public class Order {
    private Long id;

    private String name;

    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
