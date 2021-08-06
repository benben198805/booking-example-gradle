package rental.presentation.controller;

import org.junit.Test;

public class HouseControllerIntegrationTest extends IntegrationTestBase {
    @Test
    public void should_get_all_houses() {
        HousesBuilder.withDefault().withName("name-1").persist();
        HousesBuilder.withDefault().withName("name-2").persist();

        given()
                .when()
                .get("/api/houses")
                .then()
                .statusCode(200);
    }
}
