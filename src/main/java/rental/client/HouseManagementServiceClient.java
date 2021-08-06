package rental.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import rental.client.model.HouseDto;
import rental.config.FeignClientConfig;

@FeignClient(name = "HouseManagementServiceClient",
        url = "${application.services.house-management}",
        configuration = FeignClientConfig.class)
public interface HouseManagementServiceClient {
    @PostMapping(value = "/houses")
    void saveHouse(@RequestBody HouseDto houseDto);
}
