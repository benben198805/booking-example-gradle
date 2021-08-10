package rental.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import rental.application.HouseApplicationService;
import rental.domain.model.House;
import rental.presentation.dto.command.CreateHouseCommand;
import rental.presentation.exception.InternalServerException;
import rental.presentation.exception.NotFoundException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HouseControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private HouseApplicationService applicationService;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void should_get_all_houses() throws Exception {
        // given
        List<House> houseList = Arrays.asList(
                House.builder().id(1L).name("name-1").build(),
                House.builder().id(2L).name("name-2").build());
        Page<House> housePage = new PageImpl<>(houseList);
        when(applicationService.queryAllHouses(any())).thenReturn(housePage);

        // when
        mvc.perform(
                get("/houses")
                        .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.totalElements").value(2))
           .andExpect(jsonPath("$.content", hasSize(2)));
    }

    @Test
    public void should_find_house_by_id() throws Exception {
        // given
        String houseName = "house-1";
        long houseId = 1L;
        House house = House.builder().id(houseId).name(houseName).build();
        when(applicationService.findHouseById(eq(houseId))).thenReturn(house);

        // when
        mvc.perform(
                get("/houses/" + houseId)
                        .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.name").value(houseName));
    }

    @Test
    public void should_throw_exception_when_not_found_house_by_id() throws Exception {
        // given
        when(applicationService.findHouseById(any())).thenThrow(new NotFoundException(
                "NOT_FOUND", "house id(%s) not found"));

        // when
        mvc.perform(
                get("/houses/1")
                        .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isNotFound())
           .andExpect(jsonPath("$.code").value("NOT_FOUND"));
    }

    @Test
    public void should_save_house() throws Exception {
        // given
        doNothing().when(applicationService).saveHouse(any());
        CreateHouseCommand command = CreateHouseCommand.builder().name("house-1")
                                                       .price(BigDecimal.TEN).location("location").build();

        // when
        mvc.perform(
                post("/houses")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk());

        // then
        verify(applicationService, times(1)).saveHouse(any());
    }

    @Test
    public void should_throw_exception_when_save_house_without_required_attr() throws Exception {
        // given
        CreateHouseCommand command = CreateHouseCommand.builder().name("house-1").build();

        // when
        mvc.perform(
                post("/houses")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isBadRequest())
           .andExpect(jsonPath("$.code").value("INVALID_PARAM"));

        // then
    }

    @Test
    public void should_throw_exception_when_save_house_has_feign_exception() throws Exception {
        // given

        doThrow(new InternalServerException(400, "INTERNAL_SERVER_EXCEPTION", "internal exception"))
                .when(applicationService).saveHouse(any());
        CreateHouseCommand command = CreateHouseCommand.builder().name("house-1")
                                                       .price(BigDecimal.TEN).location("location").build();
        // when
        mvc.perform(
                post("/houses")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isBadRequest())
           .andExpect(jsonPath("$.code").value("INTERNAL_SERVER_EXCEPTION"));
    }
}
