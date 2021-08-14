package rental.domain.model;

import org.junit.Test;
import rental.domain.model.enums.ETicketStatus;

import static org.junit.Assert.assertEquals;

public class ETicketTest {

    @Test
    public void should_init_e_ticket() {
        // given
        long orderId = 1l;

        // when
        ETicket eTicket = ETicket.init(orderId);

        // then
        assertEquals(orderId, (long) eTicket.getOrderId());
        assertEquals(ETicketStatus.PENDING, eTicket.getStatus());
    }

}
