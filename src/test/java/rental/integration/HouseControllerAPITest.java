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
import rental.RentalServiceApplication;
import rental.config.BaseIntegrationTest;
import rental.config.WireMockConfig;
import rental.config.client.HouseManagementServiceClientMocks;
import rental.infrastructure.dataentity.HouseEntity;
import rental.infrastructure.persistence.HouseJpaPersistence;
import rental.presentation.dto.command.CreateHouseCommand;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = RentalServiceApplication.class
)
@ContextConfiguration(classes = {WireMockConfig.class})
public class HouseControllerAPITest extends BaseIntegrationTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private WireMockServer mockServer;

    private HouseJpaPersistence persistence;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        persistence = applicationContext.getBean(HouseJpaPersistence.class);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void should_get_all_houses() throws Exception {
        // given
        persistence.saveAndFlush(HouseEntity.builder().name("house-1").build());
        persistence.saveAndFlush(HouseEntity.builder().name("house-2").build());

        // when
        given()
                .when()
                .get("/houses")
                .then()
                .statusCode(200)
                .body("totalElements", is(2))
                .body("content", hasSize(2));
    }

    @Test
    public void should_get_house_detail() {
        HouseEntity houseEntity = persistence.saveAndFlush(HouseEntity.builder().name("house-1").build());

        given()
                .when()
                .get("/houses/" + houseEntity.getId())
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", is("house-1"));
    }

    @Test
    public void should_throw_exception_when_not_found_house_by_id() throws Exception {
        // given

        // when
        given()
                .when()
                .get("/houses/" + Integer.MAX_VALUE)
                .then()
                .statusCode(404)
                .body("code", is("NOT_FOUND"));
    }

    @Test
    public void should_save_house() throws Exception {
        // given
        CreateHouseCommand command = CreateHouseCommand.builder().name("house-1")
                                                       .price(BigDecimal.TEN).location("location").build();
        HouseManagementServiceClientMocks.setupSuccessMockBooksResponse(mockServer);

        // when
        given()
                .body(objectMapper.writeValueAsString(command))
                .when()
                .post("/houses")
                .then()
                .statusCode(200);
    }

    @Test
    public void should_throw_exception_when_save_house_without_command() throws Exception {
        // given
        CreateHouseCommand command = CreateHouseCommand.builder().name("house-1").build();

        // when
        given()
                .body(objectMapper.writeValueAsString(command))
                .when()
                .post("/houses")
                .then()
                .statusCode(400)
                .body("code", is("INVALID_PARAM"));
    }

    @Test
    public void should_throw_exception_when_save_house_has_feign_exception() throws Exception {
        // given
        CreateHouseCommand command = CreateHouseCommand.builder().name("house-1")
                                                       .price(BigDecimal.TEN).location("location").build();
        HouseManagementServiceClientMocks.setupFailMockBooksResponse(mockServer);

        // when
        given()
                .body(objectMapper.writeValueAsString(command))
                .when()
                .post("/houses")
                .then()
                .statusCode(400)
                .body("code", is("Internal_Server_Exception"));
    }
}
