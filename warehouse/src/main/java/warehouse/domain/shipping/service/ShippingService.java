package warehouse.domain.shipping.service;

import db.domain.shipping.ShippingEntity;
import db.domain.shipping.ShippingRepository;
import db.domain.shipping.enums.ShippingStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import warehouse.common.error.ShippingErrorCode;
import warehouse.common.exception.shipping.ShippingNotFoundException;

@Service
@RequiredArgsConstructor
public class ShippingService {

    private final ShippingRepository shippingRepository;

    public ShippingEntity shippingRequest(ShippingEntity shippingEntity, Long userId) {
        shippingEntity.setUserId(userId);
        shippingEntity.setStatus(ShippingStatus.PENDING); // PENDING(출고 대기) 으로 변경
        return shippingRepository.save(shippingEntity);
    }

