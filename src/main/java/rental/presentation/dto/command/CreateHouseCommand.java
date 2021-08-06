package rental.presentation.dto.command;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class CreateHouseCommand {
    private String name;

    private String location;

    private BigDecimal price;

    private LocalDateTime establishedTime;
}
