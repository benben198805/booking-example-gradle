package rental.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rental.ServiceApplication;
import rental.client.model.SettingsDto;
import rental.client.model.SettingsResponse;
import rental.config.WireMockConfig;
import rental.config.client.AirportInfoClientMocks;
import rental.presentation.exception.InternalServerException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ServiceApplication.class
)
@ContextConfiguration(classes = {WireMockConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
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
        SettingsResponse settingsResponse = client.takeSettings("1", settingsDto);

        // then
        assertEquals("s3641729289463",  settingsResponse.getCallbackId());
        assertEquals("SUCCESS",  settingsResponse.getStatus());
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
