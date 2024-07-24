package warehouse.domain.shipping.controller.model.response;

import db.domain.receiving.enums.ReceivingStatus;
import db.domain.shipping.enums.ShippingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingStatusResponse {

    private Long shippingId;
    private long total;
    private ShippingStatus status;
    private int current;
    private String description;

}
