package warehouse.domain.receiving.service;

import db.domain.receiving.ReceivingEntity;
import db.domain.receiving.ReceivingRepository;
import db.domain.receiving.enums.ReceivingStatus;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceivingService {

    private final ReceivingRepository receivingRepository;

    public ReceivingEntity receivingRequest(ReceivingEntity receivingEntity) {

        if (receivingEntity.getGuaranteeAt() == null) {
            receivingEntity.setGuaranteeAt(LocalDateTime.now());
        }

        receivingEntity.setStatus(ReceivingStatus.TAKING);

        // TODO user System 완료 후 로그인 유저 아이디 입력 필요
        receivingEntity.setUserId(1L);

        return receivingRepository.save(receivingEntity);
    }
}
