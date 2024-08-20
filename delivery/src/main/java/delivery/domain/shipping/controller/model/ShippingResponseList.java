package delivery.domain.shipping.controller.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShippingResponseList {

    List<ShippingResponse> reservationResponseList;

}
