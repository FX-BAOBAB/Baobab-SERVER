package warehouse.domain.address.controller.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AddAddressResponse {

    private String address;
    private String detailAddress;
    private boolean basicAddress;
}
