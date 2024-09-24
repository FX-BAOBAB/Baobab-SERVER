package delivery.domain.shipping.controller.model;

import db.domain.shipping.enums.ShippingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShippingResponse {

    private Long id;

    private ShippingStatus status;

    private LocalDateTime deliveryDate;

    private String deliveryAddress;

    private String userName;

    private String deliveryMan;

    private List<Long> goodsIdList;


}
