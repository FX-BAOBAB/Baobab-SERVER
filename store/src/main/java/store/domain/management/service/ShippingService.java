package store.domain.management.service;

import db.domain.receiving.ReceivingEntity;
import db.domain.receiving.ReceivingRepository;
import db.domain.receiving.enums.ReceivingStatus;
import db.domain.shipping.ShippingEntity;
import db.domain.shipping.ShippingRepository;
import db.domain.shipping.enums.ShippingStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingService {

    private final ShippingRepository shippingRepository;

    public List<ShippingEntity> getRequestShippingBy(ShippingStatus status) {
        return shippingRepository.findAllByStatusOrderByDeliveryDate(status);
    }
}
