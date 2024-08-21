package delivery.domain.goods.service;

import db.domain.goods.GoodsEntity;
import db.domain.goods.GoodsRepository;
import db.domain.goods.enums.GoodsStatus;
import delivery.common.error.GoodsErrorCode;
import delivery.common.exception.goods.GoodsNotFoundException;
import delivery.common.exception.goods.GoodsNotInShippingIngException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository goodsRepository;

    public List<GoodsEntity> getReceivingGoodsList(Long receivingId) {
        return goodsRepository.findAllByReceivingIdOrderByIdDesc(receivingId);
    }

    public List<GoodsEntity> getShippingGoodsList(Long receivingId) {
        return goodsRepository.findAllByShippingIdOrderByIdDesc(receivingId);
    }

    public GoodsEntity getGoodsBy(Long goodsId) {
        return goodsRepository.findById(goodsId).orElseThrow(() -> new GoodsNotFoundException(
            GoodsErrorCode.GOODS_NOT_FOUND));
    }

    public GoodsEntity startShipping(GoodsEntity goodsEntity) {
        goodsEntity.setStatus(GoodsStatus.SHIPPING_ING);
        return goodsRepository.save(goodsEntity);
    }

    public GoodsEntity changeShippingComplete(GoodsEntity goodsEntity) {

        if (goodsEntity.getStatus() != GoodsStatus.SHIPPING_ING){
            throw new GoodsNotInShippingIngException(GoodsErrorCode.GOODS_NOT_IN_SHIPPING_ING);
        }

        goodsEntity.setStatus(GoodsStatus.SHIPPING);
        return goodsRepository.save(goodsEntity);
    }

    public List<GoodsEntity> getGoodsListBy(Long requestId) {

        List<GoodsEntity> goodsEntityList = goodsRepository.findAllByReceivingIdOrderByIdDesc(
            requestId);

        if (goodsEntityList.isEmpty()){
            throw new GoodsNotFoundException(GoodsErrorCode.GOODS_NOT_FOUND);
        }

        return goodsEntityList;
    }
}
