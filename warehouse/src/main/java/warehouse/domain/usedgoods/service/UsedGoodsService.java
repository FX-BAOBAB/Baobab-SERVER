package warehouse.domain.usedgoods.service;

import db.domain.usedgoods.UsedGoodsEntity;
import db.domain.usedgoods.UsedGoodsRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsedGoodsService {

    private final UsedGoodsRepository usedGoodsRepository;

    public UsedGoodsEntity post(UsedGoodsEntity usedGoodsEntity, Long userId) {
        usedGoodsEntity.setUserId(userId);
        usedGoodsEntity.setPostedAt(LocalDateTime.now());
        return usedGoodsRepository.save(usedGoodsEntity);
    }

}
