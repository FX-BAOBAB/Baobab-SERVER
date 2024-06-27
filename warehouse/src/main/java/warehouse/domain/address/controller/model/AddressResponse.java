package warehouse.domain.address.controller.model;

import db.domain.address.AddressEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AddressResponse {

    private String address;
    private String detailAddress;
    private int post;
    private boolean basicAddress;

}
