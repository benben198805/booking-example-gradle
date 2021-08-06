package rental.infrastructure.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import rental.domain.model.House;
import rental.domain.repository.HouseRepository;
import rental.infrastructure.dataentity.HouseEntity;
import rental.infrastructure.mapper.EntityToModelMapper;
import rental.infrastructure.mapper.ModelToEntityMapper;
import rental.infrastructure.persistence.HouseJpaPersistence;

import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class HouseRepositoryImpl implements HouseRepository {
    private final HouseJpaPersistence persistence;

    @Override
    public Optional<House> findById(Long id) {
        return this.persistence.findById(id).map(EntityToModelMapper.INSTANCE::mapToModel);
    }

    @Override
    public Page<House> queryAllHouses(Pageable pageable) {
        return this.persistence.findAll(pageable).map(EntityToModelMapper.INSTANCE::mapToModel);
    }

    @Override
    public House saveHouse(House house) {
        HouseEntity savedHouse = this.persistence.save(ModelToEntityMapper.INSTANCE.mapToEntity(house));
        return EntityToModelMapper.INSTANCE.mapToModel(savedHouse);
    }

    @Override
    public void deleteById(Long id) {
        this.persistence.deleteById(id);
    }
}
