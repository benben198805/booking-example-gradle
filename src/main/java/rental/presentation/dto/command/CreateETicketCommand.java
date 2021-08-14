package rental.presentation.dto.command;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class CreateETicketCommand {

    private String flight;

    private String userName;

    private String userID;
}
