package warehouse.domain.address.controller.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BasicAddressResponse {
    private Address address;
}
