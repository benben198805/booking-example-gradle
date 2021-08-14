package rental.infrastructure.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import rental.domain.model.Order;
import rental.infrastructure.persistence.OrderJpaPersistence;

import java.util.Optional;

import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class OrderRepositoryImplTest {
    @Autowired
    private OrderJpaPersistence persistence;

    private OrderRepositoryImpl repository;

    @Before
    public void setUp() {
        repository = new OrderRepositoryImpl(persistence);
    }

    @Test
    public void should_return_empty_when_no_order_for_ID_1() {
        // given

        // when
        Optional<Order> result = this.repository.findOrderById(1L);

        // then
        assertFalse(result.isPresent());
    }
}
