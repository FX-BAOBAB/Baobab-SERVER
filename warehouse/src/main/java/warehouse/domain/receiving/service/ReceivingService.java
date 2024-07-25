package warehouse.domain.receiving.service;

import db.domain.receiving.ReceivingEntity;
import db.domain.receiving.ReceivingRepository;
import db.domain.receiving.enums.ReceivingStatus;
import global.errorcode.ErrorCode;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import warehouse.common.exception.receiving.ReceivingNotFoundException;

@Service
@RequiredArgsConstructor
public class ReceivingService {

    private final ReceivingRepository receivingRepository;

    public ReceivingEntity receivingRequest(ReceivingEntity receivingEntity) {

        if (receivingEntity.getGuaranteeAt() == null) {
            receivingEntity.setGuaranteeAt(LocalDateTime.now());
        }

        receivingEntity.setStatus(ReceivingStatus.TAKING);

        return receivingRepository.save(receivingEntity);
    }

    public ReceivingEntity getReceivingById(Long receivingId) {
        return receivingRepository.findFirstById(receivingId)
            .orElseThrow((() -> new ReceivingNotFoundException(ErrorCode.NULL_POINT)));
    }

    public ReceivingEntity setGuarantee(Long receivingId) {
        ReceivingEntity entity = getReceivingById(receivingId);
        entity.setGuaranteeAt(LocalDateTime.now());
        return receivingRepository.save(entity);
    }

    public void initReceivingStatus(Long receivingId) {
        ReceivingEntity receivingEntity = receivingRepository.findFirstById(receivingId)
            .orElseThrow((() -> new ReceivingNotFoundException(ErrorCode.NULL_POINT)));
        receivingEntity.setStatus(ReceivingStatus.CLOSE);
        receivingRepository.save(receivingEntity);
    }

}
