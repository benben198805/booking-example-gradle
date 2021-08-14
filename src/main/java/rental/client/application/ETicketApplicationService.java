package rental.client.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rental.client.AirportInfoServiceClient;
import rental.client.model.SettingsDto;
import rental.client.model.SettingsResponse;
import rental.domain.model.ETicket;
import rental.domain.repository.ETicketRepository;


@Service
@RequiredArgsConstructor
public class ETicketApplicationService {
    private final ETicketRepository repository;
    private final AirportInfoServiceClient airportInfoServiceClient;

    public ETicket create(Long orderId, String flightId, String userName, String userID) {
        SettingsDto settingsDto = SettingsDto.builder().userName(userName).userID(userID).build();
        SettingsResponse settingsResponse = airportInfoServiceClient.takeSettings(flightId, settingsDto);

        ETicket eTicket = ETicket.init(orderId);
        eTicket.setCallbackId(settingsResponse.getCallbackId());

        return this.repository.create(eTicket);
    }
}
