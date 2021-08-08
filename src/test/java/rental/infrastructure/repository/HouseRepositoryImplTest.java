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

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
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
    public void should_find_2_houses_with_page() {
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

    @Test
    public void should_find_21_houses_with_page() {
        // given
        for (int index = 1; index <= 21; index++) {
            entityManager.persistAndFlush(HouseEntity.builder().name("house-" + index).build());
        }
        PageRequest pageable = PageRequest.of(0, 20);

        // when
        Page<House> result = this.repository.queryAllHouses(pageable);

        // then
        assertEquals(21, result.getTotalElements());
        assertEquals(20, result.getContent().size());
        assertEquals(2, result.getTotalPages());
    }

    @Test
    public void should_get_house_with_id() {
        // given
        HouseEntity houseEntity = entityManager.persistAndFlush(HouseEntity.builder().name("house-1").build());

        // when
        Optional<House> result = this.repository.findById(houseEntity.getId());

        // then
        assertTrue(result.isPresent());
        assertEquals("house-1", result.get().getName());
    }

    @Test
    public void should_get_house_with_not_exist_id() {
        // given
        HouseEntity houseEntity = entityManager.persistAndFlush(HouseEntity.builder().name("house-1").build());

        // when
        Optional<House> result = this.repository.findById(houseEntity.getId() + 1);

        // then
        assertFalse(result.isPresent());
    }

    @Test
    public void should_save_house() {
        // given
        String houseName = "house-1";
        House house = House.builder().name(houseName).build();

        // when
        House result = this.repository.saveHouse(house);

        // then
        HouseEntity houseEntity = entityManager.getEntityManager().find(HouseEntity.class, result.getId());
        assertEquals(houseName, houseEntity.getName());
    }

    @Test
    public void should_delete_house_by_id() {
        // given
        HouseEntity houseEntity = HouseEntity.builder().name("house-1").build();
        entityManager.persist(houseEntity);

        // when
        this.repository.deleteById(houseEntity.getId());

        // then
        HouseEntity result = entityManager.getEntityManager().find(HouseEntity.class, houseEntity.getId());
        assertNull(result);
    }
}