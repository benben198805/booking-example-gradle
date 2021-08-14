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
import rental.domain.model.ETicket;
import rental.domain.model.enums.ETicketStatus;
import rental.infrastructure.dataentity.ETicketEntity;
import rental.infrastructure.persistence.ETicketJpaPersistence;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ETicketRepositoryImplTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ETicketJpaPersistence persistence;

    private ETicketRepositoryImpl repository;

    @Before
    public void setUp() {
        repository = new ETicketRepositoryImpl(persistence);
    }

    @Test
    public void should_create_eTicket() {
        // given
        ETicket house = ETicket.builder().status(ETicketStatus.PENDING).build();

        // when
        ETicket result = this.repository.create(house);

        // then
        ETicketEntity houseEntity = entityManager.getEntityManager().find(ETicketEntity.class, result.getId());
        assertEquals(ETicketStatus.PENDING, houseEntity.getStatus());
    }
}
