package rental.presentation.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import rental.application.HouseApplicationService;
import rental.domain.model.House;
import rental.presentation.dto.response.promotion.HouseResponse;
import rental.presentation.exception.NotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HouseControllerTest {
    @InjectMocks
    private HouseController controller;

    @Mock
    private HouseApplicationService applicationService;

    @Test
    public void should_get_all_houses() {
        // given
        List<House> houseList = Arrays.asList(
                House.builder().id(1L).name("name-1").build(),
                House.builder().id(2L).name("name-2").build());
        Page<House> housePage = new PageImpl<>(houseList);
        when(applicationService.queryAllHouses(any())).thenReturn(housePage);
        PageRequest pageable = PageRequest.of(0, 20);

        // when
        Page<HouseResponse> result = controller.queryAllHouses(pageable);

        // then
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
    }

    @Test
    public void should_find_house_by_id() {
        // given
        String houseName = "house-1";
        long houseId = 1L;
        House house = House.builder().id(houseId).name(houseName).build();
        when(applicationService.findHouseById(eq(houseId))).thenReturn(house);

        // when
        HouseResponse result = controller.findHouseById(houseId);

        // then
        assertEquals(houseName, result.getName());
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_exception_when_not_found_house_by_id() {
        // given
        when(applicationService.findHouseById(any())).thenThrow(new NotFoundException(
                "NOT_FOUND", "house id(%s) not found"));

        // when
        controller.findHouseById(1L);

        // then
    }
}
