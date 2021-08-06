package rental.domain.model;

import org.junit.Test;
import rental.domain.model.enums.HouseStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class HouseTest {

    @Test
    public void should_init_inquiry_order() {
        // given

        // when
        LocalDateTime establishedTime = LocalDateTime.now().minusYears(10);
        House house = House.init("house-1", "chengdu", BigDecimal.TEN, establishedTime);

        // then
        assertEquals("house-1", house.getName());
        assertEquals("chengdu", house.getLocation());
        assertEquals(BigDecimal.TEN, house.getPrice());
        assertEquals(establishedTime, house.getEstablishedTime());
        assertEquals(HouseStatus.PENDING, house.getStatus());
    }

}
