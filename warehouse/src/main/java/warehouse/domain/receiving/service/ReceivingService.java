package warehouse.domain.receiving.service;

import db.domain.receiving.ReceivingEntity;
import db.domain.receiving.ReceivingRepository;
import db.domain.receiving.enums.ReceivingStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceivingService {

    private final ReceivingRepository receivingRepository;

    public ReceivingEntity receivingRequest(ReceivingEntity receivingEntity, Long userId) {

        if (receivingEntity.getGuaranteeAt() == null) {
            receivingEntity.setGuaranteeAt(LocalDateTime.now());
        }

        receivingEntity.setStatus(ReceivingStatus.TAKING);

        receivingEntity.setUserId(userId);

        return receivingRepository.save(receivingEntity);
    }

    public ReceivingEntity getReceivingById(Long receivingId) {
        // TODO receiving null Exception 처리 필요
        return receivingRepository.findFirstById(receivingId)
            .orElseThrow((() -> new NullPointerException("receiving Null Exception")));
    }

    public ReceivingEntity setGuarantee(Long receivingId) {
        ReceivingEntity entity = getReceivingById(receivingId);
        entity.setGuaranteeAt(LocalDateTime.now());
        return receivingRepository.save(entity);
    }

    public void initReceivingStatus(Long receivingId) {
        // TODO Exception 수정 예정
        ReceivingEntity receivingEntity = receivingRepository.findFirstById(receivingId)
            .orElseThrow(() -> new NullPointerException("receiving 요청서가 존재하지 않습니다."));
        receivingEntity.setStatus(null);
        receivingRepository.save(receivingEntity);
    }

}
