package store.domain.receiving.service;

import db.domain.receiving.ReceivingEntity;
import db.domain.receiving.ReceivingRepository;
import db.domain.receiving.enums.ReceivingStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceivingService {

    private final ReceivingRepository receivingRepository;

    public List<ReceivingEntity> getRequestReceivingBy(ReceivingStatus status) {
        return receivingRepository.findAllByStatusOrderByVisitDate(status);
    }

    public List<ReceivingEntity> getRequestReceiving() {
        return receivingRepository.findAll();
    }

    // TODO Exception 처리 필요
    public ReceivingEntity getRequestReceivingBy(Long receivingId) {
        return receivingRepository.findFirstById(receivingId).orElseThrow(() -> new RuntimeException("존재하지 않는 요청서입니다."));
    }

    // TODO Exception 처리 필요
    public ReceivingEntity setStatus(Long receivingId, ReceivingStatus receivingStatus) {
        ReceivingEntity receivingEntity = receivingRepository.findFirstById(receivingId)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 요청서입니다."));

        receivingEntity.setStatus(receivingStatus);
        return receivingRepository.save(receivingEntity);
    }
}
