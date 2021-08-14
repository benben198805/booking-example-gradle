package rental.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rental.BookingTicketServiceApplication;
import rental.client.model.SettingsDto;
import rental.config.WireMockConfig;
import rental.config.client.AirportInfoClientMocks;
import rental.presentation.exception.InternalServerException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BookingTicketServiceApplication.class
)
@ContextConfiguration(classes = {WireMockConfig.class})
public class AirportInfoServiceClientTest {

    @Autowired
    private WireMockServer mockServer;
    @Autowired
    private AirportInfoServiceClient client;

    @Test
    public void should_save_lock_settings() throws Exception {
        // given
        SettingsDto settingsDto = SettingsDto.builder().userID("410123123412341234").userName("XXX").build();
        AirportInfoClientMocks.setupSuccessMockResponse(mockServer);

        // when
        client.takeSettings("1", settingsDto);
    }

    @Test(expected = InternalServerException.class)
    public void should_throw_exception_when_call_airport_info_fail() throws Exception {
        // given
        SettingsDto settingsDto = SettingsDto.builder().userID("410123123412341234").userName("XXX").build();
        AirportInfoClientMocks.setupFailMockResponse(mockServer);

        // when
        client.takeSettings("1", settingsDto);
    }
}
