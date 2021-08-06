package rental.application;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import rental.client.HouseManagementServiceClient;
import rental.domain.model.House;
import rental.domain.repository.HouseRepository;
import rental.presentation.dto.command.CreateHouseCommand;
import rental.presentation.exception.InternalServerException;
import rental.presentation.exception.NotFoundException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HouseApplicationServiceTest {
    @InjectMocks
    private HouseApplicationService applicationService;

    @Mock
    private HouseRepository repository;
    @Mock
    private HouseManagementServiceClient houseManagementServiceClient;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void should_get_all_houses() {
        // given
        List<House> houseList = Arrays.asList(
                House.builder().id(1L).name("name-1").build(),
                House.builder().id(2L).name("name-2").build());
        Page<House> housePage = new PageImpl<>(houseList);
        when(repository.queryAllHouses(any())).thenReturn(housePage);
        PageRequest pageable = PageRequest.of(0, 20);

        // when
        Page<House> result = applicationService.queryAllHouses(pageable);

        // then
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
    }

    @Test
    public void should_get_house_by_id() {
        // given
        String expectedName = "hosue-1";
        House house = House.builder().id(1L).name(expectedName).build();
        when(repository.findById(eq(1L))).thenReturn(Optional.of(house));

        // when
        House result = applicationService.findHouseById(1L);

        // then
        assertEquals(expectedName, result.getName());
    }

    @Test(expected = NotFoundException.class)
    public void should_throw_not_found_exception_when_not_exist_id() {
        // given
        when(repository.findById(eq(2L))).thenReturn(Optional.empty());

        // when
        applicationService.findHouseById(2L);

        // then
    }

    @Test
    public void should_save_house_and_send_house_to_house_client() {
        // given
        House house = House.builder().name("house-1").price(BigDecimal.TEN).location("location").build();
        when(repository.saveHouse(any())).thenReturn(house);

        doNothing().when(houseManagementServiceClient).saveHouse(any());

        CreateHouseCommand command = CreateHouseCommand.builder().name("house-1").price(BigDecimal.TEN).location("location").build();

        // when
        applicationService.saveHouse(command);

        // then
        verify(houseManagementServiceClient, times(1)).saveHouse(any());
    }

    @Test(expected = InternalServerException.class)
    public void should_rollback_when_client_throw_exception() {
        // given
        House house = House.builder().name("house-1").price(BigDecimal.TEN).location("location").build();
        when(repository.saveHouse(any())).thenReturn(house);
        doNothing().when(repository).deleteById(any());

        doThrow(new InternalServerException(400, "INTERNAL_SERVER_EXCEPTION", "internal exception"))
                .when(houseManagementServiceClient).saveHouse(any());

        CreateHouseCommand command = CreateHouseCommand.builder().name("house-1").price(BigDecimal.TEN).location("location").build();

        // when
        try {
            applicationService.saveHouse(command);
        } finally {
            // then
            verify(repository, times(1)).deleteById(any());
        }
    }
}
