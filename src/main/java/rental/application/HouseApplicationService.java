package rental.application;

import feign.FeignException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.encoding.FeignClientEncodingProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rental.client.HouseManagementServiceClient;
import rental.client.assembler.ModelToClientDtoMapper;
import rental.domain.model.House;
import rental.domain.repository.HouseRepository;
import rental.presentation.dto.command.CreateHouseCommand;
import rental.presentation.exception.InternalServerException;
import rental.presentation.exception.NotFoundException;


@Service
@RequiredArgsConstructor
public class HouseApplicationService {
    private final HouseRepository houseRepository;
    private final HouseManagementServiceClient houseManagementServiceClient;

    public House findHouseById(Long id) {
        return houseRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "NOT_FOUND", String.format("house id(%s) not found", id)));
    }

    public Page<House> queryAllHouses(Pageable pageable) {
        return houseRepository.queryAllHouses(pageable);
    }

    public void saveHouse(CreateHouseCommand command) {
        House house = House.init(command.getName(), command.getLocation(),
                command.getPrice(), command.getEstablishedTime());
        House savedHouse = this.houseRepository.saveHouse(house);

        try {
            houseManagementServiceClient.saveHouse(ModelToClientDtoMapper.INSTANCE.mapToClientDto(savedHouse));
        } catch (InternalServerException e) {
            this.houseRepository.deleteById(savedHouse.getId());
            throw e;
        }
    }
}
