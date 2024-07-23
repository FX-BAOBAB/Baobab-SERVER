package warehouse.domain.shipping.controller.model.request;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShippingRequest {

    private LocalDateTime deliveryDate;

    private String deliveryAddress;

    private List<Long> goodsId;

}
