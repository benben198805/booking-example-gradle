package rental.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
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
import rental.infrastructure.dataentity.OrderEntity;
import rental.infrastructure.persistence.OrderJpaPersistence;
import rental.presentation.dto.command.CreateETicketCommand;

import static org.hamcrest.Matchers.is;

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

    private OrderJpaPersistence orderJpaPersistence;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        orderJpaPersistence = applicationContext.getBean(OrderJpaPersistence.class);
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
        OrderEntity orderEntity = orderJpaPersistence.save(OrderEntity.builder().name("name").build());
        AirportInfoClientMocks.setupSuccessMockResponse(mockServer);

        // when
        given()
                .body(objectMapper.writeValueAsString(command))
                .when()
                .post("/booking-orders/" + orderEntity.getId() + "/e-tickets")
                .then()
                .statusCode(201);
    }

    @Test
    public void should_create_fail_eTicket_when_client_throw_exception() throws Exception {
        // given
        String flight = "CA123";
        String userName = "user-name";
        String userId = "user-id";
        CreateETicketCommand command = CreateETicketCommand.builder().flight(flight)
                                                           .userID(userId).userName(userName).build();
        OrderEntity orderEntity = orderJpaPersistence.save(OrderEntity.builder().name("name").build());
        AirportInfoClientMocks.setupFailMockResponse(mockServer);

        // when
        given()
                .body(objectMapper.writeValueAsString(command))
                .when()
                .post("/booking-orders/" + orderEntity.getId() + "/e-tickets")
                .then()
                .statusCode(201)
                .body("status", is("FAIL"));
    }

    @Test
    public void should_return_error_response_when_order_is_not_exist() throws Exception {
        // given
        String flight = "CA123";
        String userName = "user-name";
        String userId = "user-id";
        CreateETicketCommand command = CreateETicketCommand.builder().flight(flight)
                                                           .userID(userId).userName(userName).build();

        // when
        given()
                .body(objectMapper.writeValueAsString(command))
                .when()
                .post("/booking-orders/1/e-tickets")
                .then()
                .statusCode(404)
                .body("code", is("NOT_FOUND"));
    }
}
