package rental.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rental.domain.model.House;
import rental.domain.repository.HouseRepository;
import rental.presentation.exception.NotFoundException;


@Service
@RequiredArgsConstructor
public class HouseApplicationService {
    private final HouseRepository houseRepository;

    public House findHouseById(Long id) {
        return houseRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "NOT_FOUND", String.format("house id(%s) not found", id)));
    }

    public Page<House> queryAllHouses(Pageable pageable) {
        return houseRepository.queryAllHouses(pageable);
    }
}
