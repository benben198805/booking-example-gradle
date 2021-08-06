package rental.presentation.dto.response.promotion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rental.domain.model.enums.HouseStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HouseResponse {
    private Long id;

    private String name;

    private String location;

    private BigDecimal price;

    private LocalDateTime establishedTime;

    private HouseStatus status;

    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
