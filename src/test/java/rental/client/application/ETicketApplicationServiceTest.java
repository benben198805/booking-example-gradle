package rental.client.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import rental.client.AirportInfoServiceClient;
import rental.client.model.SettingsResponse;
import rental.domain.model.ETicket;
import rental.domain.model.enums.ETicketStatus;
import rental.domain.repository.ETicketRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ETicketApplicationServiceTest {
    @InjectMocks
    private ETicketApplicationService applicationService;

    @Mock
    private ETicketRepository repository;
    @Mock
    private AirportInfoServiceClient airportInfoServiceClient;

    @Test
    public void should_save_house_and_send_house_to_house_client() {
        // given
        String callbackId = "19282";
        ETicket build = ETicket.builder().orderId(1L).callbackId(callbackId).orderId(1L)
                               .status(ETicketStatus.PENDING).build();
        when(repository.create(any())).thenReturn(build);

        SettingsResponse response = SettingsResponse.builder().callbackId(callbackId).build();
        when(airportInfoServiceClient.takeSettings(any(), any())).thenReturn(response);

        // when
        ETicket eTicket = applicationService.create(1L, "AC83", "user-name", "user-id");

        // then
        verify(airportInfoServiceClient, times(1)).takeSettings(any(), any());
        assertEquals(ETicketStatus.PENDING, eTicket.getStatus());
        assertEquals(1l, (long) eTicket.getOrderId());
        assertEquals(callbackId, eTicket.getCallbackId());
    }
}
