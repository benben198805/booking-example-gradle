package rental.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rental.client.AirportInfoServiceClient;
import rental.client.model.SettingsDto;
import rental.client.model.SettingsResponse;
import rental.domain.model.ETicket;
import rental.domain.model.enums.ETicketStatus;
import rental.domain.repository.ETicketRepository;
import rental.domain.repository.OrderRepository;
import rental.presentation.exception.NotFoundException;


@Service
@RequiredArgsConstructor
public class ETicketApplicationService {
    private final ETicketRepository repository;
    private final OrderRepository orderRepository;
    private final AirportInfoServiceClient airportInfoServiceClient;

    public ETicket create(Long orderId, String flightId, String userName, String userID) {
        orderRepository.findOrderById(orderId)
                       .orElseThrow(() ->
                               new NotFoundException("NOT_FOUND", String.format("order id: %d not found", orderId)));

        SettingsDto settingsDto = SettingsDto.builder().userName(userName).userID(userID).build();
        SettingsResponse settingsResponse;
        try {
            settingsResponse = airportInfoServiceClient.takeSettings(flightId, settingsDto);
        } catch (Exception e) {
            ETicket eTicket = ETicket.init(orderId);
            eTicket.setStatus(ETicketStatus.FAIL);
            return this.repository.create(eTicket);
        }

        ETicket eTicket = ETicket.init(orderId);
        eTicket.setCallbackId(settingsResponse.getCallbackId());

        return this.repository.create(eTicket);
    }
}
