package store.domain.shipping.service;

import db.domain.shipping.ShippingEntity;
import db.domain.shipping.ShippingRepository;
import db.domain.shipping.enums.ShippingStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.common.error.ShippingErrorCode;
import store.common.exception.shipping.ShippingNotFoundException;

@Service
@RequiredArgsConstructor
public class ShippingService {

    private final ShippingRepository shippingRepository;

    public List<ShippingEntity> getRequestShippingBy(ShippingStatus status) {
        return shippingRepository.findAllByStatusOrderByDeliveryDate(status);
    }

    public ShippingEntity getRequestShippingBy(Long shippingId) {
        return shippingRepository.findFirstById(shippingId).orElseThrow(() -> new ShippingNotFoundException(
            ShippingErrorCode.SHIPPING_REQUEST_NOT_FOUND));
    }

    public ShippingEntity setStatus(Long shippingId, ShippingStatus shippingStatus) {
        ShippingEntity shippingEntity = getRequestShippingBy(shippingId);
        if (shippingEntity.getStatus() != ShippingStatus.REGISTERED){
            throw new ShippingNotFoundException(ShippingErrorCode.SHIPPING_REQUEST_NOT_FOUND);
        }
        shippingEntity.setStatus(shippingStatus);
        return shippingRepository.save(shippingEntity);
    }
}
