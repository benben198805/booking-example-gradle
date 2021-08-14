package rental.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import rental.client.application.ETicketApplicationService;
import rental.presentation.dto.command.CreateETicketCommand;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ETicketControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ETicketApplicationService applicationService;
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

        // when
        mvc.perform(
                post("/booking-orders/1/e-tickets")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isCreated());

        // then
        verify(applicationService, times(1)).create(eq(1L),
                eq(flight), eq(userName), eq(userId));
    }

}
