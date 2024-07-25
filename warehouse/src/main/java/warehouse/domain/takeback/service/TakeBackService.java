package warehouse.domain.takeback.service;

import db.domain.takeback.TakeBackEntity;
import db.domain.takeback.TakeBackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import warehouse.common.error.TakeBackErrorCode;
import warehouse.common.exception.takeback.NotFoundRequestException;

@Service
@RequiredArgsConstructor
public class TakeBackService {

    private final TakeBackRepository takeBackRepository;

    public TakeBackEntity takeBackRequest(TakeBackEntity takeBackEntity) {
        return takeBackRepository.save(takeBackEntity);
    }

    public TakeBackEntity getTakeBackById(Long requestId) {
        return takeBackRepository.findFirstById(requestId).orElseThrow(() -> new NotFoundRequestException(
            TakeBackErrorCode.NOT_FOUNT_REQUEST));
    }

}
