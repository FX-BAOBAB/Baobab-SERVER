package delivery.domain.shipping.service;

import db.domain.receiving.ReceivingEntity;
import db.domain.receiving.enums.ReceivingStatus;
import db.domain.shipping.ShippingEntity;
import db.domain.shipping.ShippingRepository;
import db.domain.shipping.enums.ShippingStatus;
import delivery.common.error.ReceivingErrorCode;
import delivery.common.error.ShippingErrorCode;
import delivery.common.exception.receiving.ReceivingNotFoundException;
import delivery.common.exception.shipping.ShippingNotFoundException;
import delivery.common.exception.shipping.ShippingNotInPendingException;
import java.time.LocalDateTime;
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

        if (shippingEntityList.isEmpty()) {
            throw new ShippingNotFoundException(ShippingErrorCode.SHIPPING_REQUEST_NOT_FOUND);
        }

        return shippingEntityList;
    }

    public ShippingEntity getRequest(Long requestId) {
        return shippingRepository.findFirstById(requestId).orElseThrow(() -> new ShippingNotFoundException(ShippingErrorCode.SHIPPING_REQUEST_NOT_FOUND));
    }

    public ShippingEntity reservationConfirmed(Long requestId) {
        ShippingEntity shippingEntity = getRequest(requestId);

        if (shippingEntity.getStatus() != ShippingStatus.PENDING) {
            throw new ShippingNotInPendingException(ShippingErrorCode.SHIPPING_NOT_IN_PENDING);
        }

        shippingEntity.setStatus(ShippingStatus.REGISTERED);

        return shippingRepository.save(shippingEntity);

    }

    public List<ShippingEntity> getRequestListByDate(LocalDateTime startDate, LocalDateTime dueDate) {
        List<ShippingEntity> receivingEntityList = shippingRepository.findAllByStatusAndDeliveryDateBetweenOrderByUserId(
            ShippingStatus.REGISTERED, startDate, dueDate);

        if (receivingEntityList.isEmpty()) {
            throw new ReceivingNotFoundException(ReceivingErrorCode.RECEIVING_REQUEST_NOT_FOUND);
        }
        return receivingEntityList;
    }

    public ShippingEntity startDelivery(ShippingEntity shippingEntity) {
        shippingEntity.setStatus(ShippingStatus.DELIVERY);
        return shippingRepository.save(shippingEntity);
    }

    public ShippingEntity deliveryComplete(ShippingEntity shippingEntity) {
        shippingEntity.setStatus(ShippingStatus.SHIPPED);
        return shippingRepository.save(shippingEntity);
    }
}
