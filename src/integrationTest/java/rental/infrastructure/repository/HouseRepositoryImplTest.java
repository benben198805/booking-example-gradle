package rental.infrastructure.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import rental.domain.model.House;
import rental.infrastructure.dataentity.HouseEntity;
import rental.infrastructure.persistence.HouseJpaPersistence;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class HouseRepositoryImplTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private HouseJpaPersistence persistence;

    private HouseRepositoryImpl repository;

    @Before
    public void setUp() {
        repository = new HouseRepositoryImpl(persistence);
    }

    @Test
    public void should_find_all_houses() {
        // given
        entityManager.persistAndFlush(HouseEntity.builder().name("house-1").build());
        entityManager.persistAndFlush(HouseEntity.builder().name("house-2").build());
        PageRequest pageable = PageRequest.of(0, 20);

        // when
        Page<House> result = this.repository.queryAllHouses(pageable);

        // then
        assertEquals(2, result.getTotalElements());
        assertEquals(2, result.getContent().size());
    }
}
