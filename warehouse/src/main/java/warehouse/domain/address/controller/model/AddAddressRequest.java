package warehouse.domain.address.controller.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AddAddressRequest {

    private String address;
    private String detailAddress;
    private int post;
    private boolean basicAddress;
}
