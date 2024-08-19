package delivery.domain.shipping.service;

import db.domain.receiving.ReceivingEntity;
import db.domain.receiving.enums.ReceivingStatus;
import db.domain.shipping.ShippingEntity;
import db.domain.shipping.ShippingRepository;
import db.domain.shipping.enums.ShippingStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShippingService {

    private final ShippingRepository shippingRepository;

    public List<ShippingEntity> getRequestList() {

        List<ShippingEntity> shippingEntityList = shippingRepository.findAllByStatusOrderByDeliveryDate(
            ShippingStatus.PENDING);

        // TODO Exception 처리 필요
        if (shippingEntityList.isEmpty()) {
            throw new RuntimeException("존재하지 않음");
        }

        return shippingEntityList;
    }

}
