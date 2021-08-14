package rental.presentation.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import rental.client.application.ETicketApplicationService;
import rental.domain.model.ETicket;
import rental.presentation.assembler.ModelToResponseMapper;
import rental.presentation.dto.command.CreateETicketCommand;
import rental.presentation.dto.response.eticket.ETicketResponse;

import javax.validation.Valid;

@RestController
@RequestMapping("/booking-orders")
@Slf4j
@Validated
@AllArgsConstructor
public class ETicketController {
    private final ETicketApplicationService applicationService;

    @PostMapping("/{oid}/e-tickets")
    @ResponseStatus(HttpStatus.CREATED)
    public ETicketResponse create(
            @PathVariable(value = "oid") Long orderId,
            @Valid @RequestBody CreateETicketCommand command) {
        ETicket eTicket = applicationService.create(orderId, command.getFlight(), command.getUserName(), command.getUserID());

        return ModelToResponseMapper.INSTANCE.mapToETicketResponse(eTicket);
    }
}
