package rental.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rental.BookingTicketServiceApplication;
import rental.config.BaseIntegrationTest;
import rental.config.WireMockConfig;
import rental.config.client.AirportInfoClientMocks;
import rental.presentation.dto.command.CreateETicketCommand;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = BookingTicketServiceApplication.class
)
@ContextConfiguration(classes = {WireMockConfig.class})
public class ETicketControllerAPITest extends BaseIntegrationTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private WireMockServer mockServer;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void should_create_eTicket() throws Exception {
        // given
        String flight = "CA123";
        String userName = "user-name";
        String userId = "user-id";
        CreateETicketCommand command = CreateETicketCommand.builder().flight(flight)
                                                           .userID(userId).userName(userName).build();
        AirportInfoClientMocks.setupSuccessMockResponse(mockServer);

        // when
        given()
                .body(objectMapper.writeValueAsString(command))
                .when()
                .post("/booking-orders/1/e-tickets")
                .then()
                .statusCode(201);
    }
}
