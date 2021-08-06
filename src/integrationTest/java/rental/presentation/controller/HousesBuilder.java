package rental.presentation.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rental.SpringApplicationContext;
import rental.infrastructure.dataentity.HouseEntity;
import rental.infrastructure.persistence.HouseJpaPersistence;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HousesBuilder {
    private HouseEntity house = new HouseEntity();

    public static HousesBuilder withDefault() {
        return new HousesBuilder();
    }

    public HouseEntity build() {
        return house;
    }

    public HouseEntity persist() {
        HouseJpaPersistence persistence = SpringApplicationContext.getBean(HouseJpaPersistence.class);
        return persistence.saveAndFlush(house);
    }

    public HousesBuilder withName(String name) {
        this.house.setName(name);
        return this;
    }
}
