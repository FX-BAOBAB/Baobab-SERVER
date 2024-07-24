package warehouse.domain.shipping.controller.model.response;

import db.domain.shipping.enums.ShippingStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingResponse {

    private Long shippingId;

    private LocalDateTime deliveryDate;

    private String deliveryAddress;

    private ShippingStatus status;

    private Long deliveryMan;

}
