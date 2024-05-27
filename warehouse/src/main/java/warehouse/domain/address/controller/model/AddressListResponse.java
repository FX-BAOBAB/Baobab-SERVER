package warehouse.domain.address.controller.model;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AddressListResponse {

    List<AddressResponse> addressResponses;

}
