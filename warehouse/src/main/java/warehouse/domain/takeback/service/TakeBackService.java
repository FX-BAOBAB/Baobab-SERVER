package warehouse.domain.takeback.service;

import db.domain.takeback.TakeBackEntity;
import db.domain.takeback.TakeBackRepository;
import db.domain.takeback.enums.TakeBackStatus;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TakeBackService {

    private final TakeBackRepository takeBackRepository;

    public TakeBackEntity takeBackRequest(TakeBackEntity takeBackEntity) {
        takeBackEntity.setStatus(TakeBackStatus.TAKE_BACK_REGISTERED);
        takeBackEntity.setTakeBackRequestAt(LocalDateTime.now());
        return takeBackRepository.save(takeBackEntity);
    }
}
