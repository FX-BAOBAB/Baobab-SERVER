package warehouse.domain.usedgoods.service;

import db.domain.usedgoods.UsedGoodsEntity;
import db.domain.usedgoods.UsedGoodsRepository;
import db.domain.usedgoods.enums.UsedGoodsStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import warehouse.common.error.UsedGoodsErrorCode;
import warehouse.common.exception.usedGoods.UsedGoodsNotFoundException;

@Service
@RequiredArgsConstructor
public class UsedGoodsService {

    private final UsedGoodsRepository usedGoodsRepository;

    public void register(UsedGoodsEntity usedGoodsEntity, Long userId) {
        usedGoodsEntity.setUserId(userId);
        usedGoodsEntity.setPostedAt(LocalDateTime.now());
        usedGoodsEntity.setStatus(UsedGoodsStatus.REGISTERED);
        usedGoodsRepository.save(usedGoodsEntity);
    }

    public UsedGoodsEntity getUsedGoodsBy(Long usedGoodsId, UsedGoodsStatus status) {
        return usedGoodsRepository.findFirstByIdAndStatus(usedGoodsId, status).orElseThrow(
            () -> new UsedGoodsNotFoundException(UsedGoodsErrorCode.USED_GOODS_NOT_FOUND));
    }

    public List<UsedGoodsEntity> getUsedGoodsListBy(List<Long> usedGoodsIdList,
        UsedGoodsStatus status) {
        return usedGoodsIdList.stream()
            .map(usedGoodsId -> this.getUsedGoodsBy(usedGoodsId, status))
            .collect(Collectors.toList());
    }

    public void setUsedGoodsStatusBy(UsedGoodsEntity usedGoodsEntity, UsedGoodsStatus status) {
        usedGoodsEntity.setStatus(status);
        usedGoodsRepository.save(usedGoodsEntity);
    }

    public void setUsedGoodsStatusBy(List<UsedGoodsEntity> usedGoodsEntityList,
        UsedGoodsStatus status) {
        usedGoodsEntityList.forEach(usedGoodsEntity -> {
            this.setUsedGoodsStatusBy(usedGoodsEntity, status);
        });
    }
}
