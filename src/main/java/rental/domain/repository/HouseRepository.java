package rental.domain.repository;

import rental.domain.model.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface HouseRepository {
    Optional<House> findById(Long id);

    Page<House> queryAllHouses(Pageable pageable);

    House saveHouse(House house);

    void deleteById(Long id);
}
