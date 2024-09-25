package store.domain.goods.service;

import db.domain.goods.GoodsEntity;
import db.domain.goods.GoodsRepository;
import db.domain.goods.enums.GoodsStatus;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.common.error.GoodsErrorCode;
import store.common.exception.goods.GoodsNotFoundException;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;

    public List<GoodsEntity> getGoodsListBy(GoodsStatus status) {
        return goodsRepository.findAllByStatusOrderByIdDesc(status);
    }

    public List<GoodsEntity> getGoodsListBy(Long receivingId) {
        return goodsRepository.findAllByReceivingIdOrderByIdDesc(receivingId);
    }

    public List<GoodsEntity> getGoodsListBy(List<Long> goodsIdList) {
        return goodsIdList.stream().map(goodsId -> goodsRepository.findById(goodsId).orElseThrow(()-> new GoodsNotFoundException(
            GoodsErrorCode.GOODS_NOT_FOUND))).toList();
    }

    public Optional<GoodsEntity> getGoodsBy(Long goodsId) {
        return goodsRepository.findById(goodsId);
    }

    public GoodsEntity setStatus(Long goodsId, GoodsStatus goodsStatus) {
        GoodsEntity goodsEntity = getGoodsBy(goodsId).orElseThrow(
            () -> new GoodsNotFoundException(GoodsErrorCode.GOODS_NOT_FOUND));
        goodsEntity.setStatus(goodsStatus);
        return goodsRepository.save(goodsEntity);
    }

    public List<GoodsEntity> getShippingGoodsListBy(Long receivingId) {
        return goodsRepository.findAllByShippingIdOrderByIdDesc(receivingId);
    }
}
