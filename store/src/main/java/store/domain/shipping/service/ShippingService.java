package store.domain.shipping.service;

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

    // TODO Exception 처리 필요
    public ShippingEntity getRequestShippingBy(Long shippingId) {
        return shippingRepository.findFirstById(shippingId).orElseThrow(() -> new RuntimeException("존재하지 않는 요청서입니다."));
    }

    // TODO Exception 처리 필요
    public ShippingEntity setStatus(Long shippingId, ShippingStatus shippingStatus) {
        ShippingEntity shippingEntity = getRequestShippingBy(shippingId);
        if (shippingEntity.getStatus() != ShippingStatus.REGISTERED){
            throw new RuntimeException("출고 접수 상태가 아닙니다.");
        }
        shippingEntity.setStatus(shippingStatus);
        return shippingRepository.save(shippingEntity);
    }
}
