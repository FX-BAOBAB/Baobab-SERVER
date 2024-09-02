package fault.domain.goods.service;

import db.domain.goods.GoodsEntity;
import db.domain.goods.GoodsRepository;
import db.domain.goods.enums.GoodsStatus;
import fault.common.error.GoodsErrorCode;
import fault.common.exception.goods.GoodsNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoodsService {

    private final GoodsRepository goodsRepository;

    private List<GoodsEntity> findAllBy(Long receivingId) {
        return goodsRepository.findAllByReceivingIdOrderByIdDesc(receivingId);
    }

    public void setGoodsStatusBy(GoodsEntity goodsEntity, GoodsStatus status) {
        goodsEntity.setStatus(status);
        goodsRepository.save(goodsEntity);
    }

    public List<GoodsEntity> findAllByReceivingIdWithThrow(Long receivingId) {
        List<GoodsEntity> goodsEntityList = findAllBy(receivingId);
        if (goodsEntityList.isEmpty()) {
            throw new GoodsNotFoundException(GoodsErrorCode.GOODS_NOT_FOUND);
        }
        return goodsEntityList;
    }

}