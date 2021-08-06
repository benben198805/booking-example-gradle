package rental.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rental.domain.model.enums.HouseStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class House {
    private Long id;

    private String name;

    private String location;

    private BigDecimal price;

    private LocalDateTime establishedTime;

    private HouseStatus status;

    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    public static House init(String name, String location, BigDecimal price, LocalDateTime establishedTime) {
        return House.builder()
                    .name(name)
                    .location(location)
                    .price(price)
                    .establishedTime(establishedTime)
                    .status(HouseStatus.PENDING)
                    .build();
    }
}
