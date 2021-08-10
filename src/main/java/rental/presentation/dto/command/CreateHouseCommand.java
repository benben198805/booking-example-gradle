package rental.presentation.dto.command;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class CreateHouseCommand {

    private String name;

    @NotNull
    private String location;

    @NotNull
    private BigDecimal price;

    @NotNull
    private LocalDateTime establishedTime;
}
