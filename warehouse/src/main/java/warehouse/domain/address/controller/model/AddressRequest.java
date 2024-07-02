package warehouse.domain.address.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {

    private String address;
    private String detailAddress;
    private int post;
    private boolean basicAddress;

}
