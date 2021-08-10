package rental.client;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rental.RentalServiceApplication;
import rental.client.model.HouseDto;
import rental.config.WireMockConfig;
import rental.config.client.HouseManagementServiceClientMocks;
import rental.presentation.exception.InternalServerException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = RentalServiceApplication.class
)
@ContextConfiguration(classes = {WireMockConfig.class})
public class HouseManagementServiceClientTest {
    @Autowired
    private WireMockServer mockServer;
    @Autowired
    private HouseManagementServiceClient client;

    @Test
    public void should_save_house() throws Exception {
        // given
        HouseDto house = HouseDto.builder().name("house-1").location("location").build();
        HouseManagementServiceClientMocks.setupSuccessMockBooksResponse(mockServer);

        // when
        client.saveHouse(house);
    }

    @Test(expected = InternalServerException.class)
    public void should_throw_exception_when_save_house_has_feign_exception() throws Exception {
        // given
        HouseDto house = HouseDto.builder().name("house-1").location("location").build();
        HouseManagementServiceClientMocks.setupFailMockBooksResponse(mockServer);

        // when
        client.saveHouse(house);
    }


}
