package rental.application;

import rental.domain.model.House;
import rental.domain.repository.HouseRepository;
import rental.presentation.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class HouseApplicationService {
    private final HouseRepository houseRepository;

    public House queryPromotionProposal(Long id) {
        return houseRepository.findById(id)
                              .orElseThrow(() ->
                new NotFoundException("PROMOTION_PROPOSAL_NOT_FOUND", "cannot found promotion proposal id: " + id));
    }

    public Page<House> queryAllHouses(Pageable pageable) {
        return houseRepository.queryAllHouses(pageable);
    }
}
