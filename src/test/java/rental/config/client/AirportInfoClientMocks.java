package rental.config.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import rental.client.model.SettingsResponse;
import rental.presentation.dto.response.common.ErrorResponse;

import java.io.IOException;

public class AirportInfoClientMocks {


    public static void setupSuccessMockResponse(WireMockServer mockService) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SettingsResponse successResponse = SettingsResponse.builder().status("SUCCESS")
                                                           .callbackId("s3641729289463").build();
        mockService.stubFor(WireMock.post(WireMock.urlMatching("/flight/?.*/settings"))
                                    .willReturn(WireMock.aResponse()
                                                        .withStatus(HttpStatus.OK.value())
                                                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                                        .withBody(objectMapper.writeValueAsString(successResponse))
                                    ));
    }


    public static void setupFailMockResponse(WireMockServer mockService) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.of(500, "Internal_Server_Exception", "InternalServerException");
        ObjectMapper objectMapper = new ObjectMapper();
        mockService.stubFor(WireMock.post(WireMock.urlMatching("/flight/?.*/settings"))
                                    .willReturn(WireMock.aResponse()
                                                        .withStatus(HttpStatus.BAD_REQUEST.value())
                                                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                                        .withStatusMessage("message")
                                                        .withBody(objectMapper.writeValueAsString(errorResponse))
                                    ));
    }
}
