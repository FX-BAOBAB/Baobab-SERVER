package fault.domain.receiving.service;

import db.domain.receiving.ReceivingEntity;
import db.domain.receiving.ReceivingRepository;
import db.domain.receiving.enums.ReceivingStatus;
import fault.common.exception.receiving.ReceivingNotFoundException;
import global.errorcode.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceivingService {

    private final ReceivingRepository receivingRepository;

    public ReceivingEntity getReceivingBy(Long receivingId) {
        return receivingRepository.findFirstById(receivingId)
            .orElseThrow((() -> new ReceivingNotFoundException(ErrorCode.NULL_POINT)));
    }

    public void setReceivingStatusBy(Long receivingId, ReceivingStatus status) {
        ReceivingEntity receivingEntity = this.getReceivingBy(receivingId);
        receivingEntity.setStatus(ReceivingStatus.DELIVERY);
        receivingRepository.save(receivingEntity);
    }

    public ReceivingEntity rejectFault(ReceivingEntity receivingEntity, ReceivingStatus status) {
        receivingEntity.setStatus(status);
        return receivingRepository.save(receivingEntity);
    }

}
