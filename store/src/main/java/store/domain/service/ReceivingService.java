package store.domain.service;

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
}
