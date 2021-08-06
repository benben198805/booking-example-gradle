package rental.presentation.controller;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("local")
@Transactional
@Rollback
public class IntegrationTestBase {

    private static final String UTF_8 = "UTF-8";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }

    protected MockMvcRequestSpecification given() {
        return RestAssuredMockMvc
                .given()
                .header("Accept", ContentType.JSON.withCharset("UTF-8"))
                .header("Content-Type", ContentType.JSON.withCharset("UTF-8"));
    }
}
