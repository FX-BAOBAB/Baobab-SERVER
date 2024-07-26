package warehouse.domain.goods.service;

import db.domain.goods.GoodsEntity;
import db.domain.goods.GoodsRepository;
import db.domain.goods.enums.GoodsStatus;
import db.domain.receiving.ReceivingEntity;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import warehouse.common.error.GoodsErrorCode;
import warehouse.common.exception.Goods.InvalidGoodsStatusException;
import warehouse.common.exception.goods.GoodsNotFoundException;
import warehouse.domain.shipping.controller.model.request.ShippingRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoodsService {

    private final GoodsRepository goodsRepository;

    public GoodsEntity save(GoodsEntity goodsEntity, Long receivingId, Long userId) {
        goodsEntity.setReceivingId(receivingId);
        goodsEntity.setUserId(userId);
        goodsEntity.setStatus(GoodsStatus.RECEIVING);
        return goodsRepository.save(goodsEntity);
    }

    public void abandonment(ReceivingEntity receiving) {
        List<GoodsEntity> goodsEntityList = findAllBy(receiving.getId());
        checkEmptyGoodsListWithThrow(goodsEntityList);
        // TODO 회사 아이디 생성 후 Matching 필요
        goodsEntityList.forEach(goodsEntity -> {
            setAbandonmentAtAndUserId(goodsEntity);
            goodsRepository.save(goodsEntity);
        });
    }

    private List<GoodsEntity> findAllBy(Long receivingId) {
        return goodsRepository.findAllByReceivingIdOrderByIdDesc(receivingId);
    }

    public void abandonment(List<Long> goodsIdList) {
        // TODO 회사 아이디 생성 후 Matching 필요
        goodsIdList.forEach(goodsId -> {
            GoodsEntity goodsEntity = getGoodsBy(goodsId);
            setAbandonmentAtAndUserId(goodsEntity);
        });
    }

    private void setAbandonmentAtAndUserId(GoodsEntity goodsEntity) {
        goodsEntity.setUserId(0L);
        goodsEntity.setAbandonmentAt(LocalDateTime.now());
        goodsRepository.save(goodsEntity);
    }

    public void setTakeBackIdAndStatus(List<GoodsEntity> goodsEntityList, Long id,
        GoodsStatus goodsStatus) {
        goodsEntityList.forEach(goodsEntity -> {
            goodsEntity.setTakeBackId(id);
            setGoodsStatusBy(goodsStatus, goodsEntity);
        });
    }

    public List<GoodsEntity> getGoodsListBy(List<Long> goodsIdList) {
        return goodsRepository.findAllByIdIn(goodsIdList);
    }

    public GoodsEntity getGoodsBy(Long goodsId) {
        return goodsRepository.findFirstById(goodsId)
            .orElseThrow(() -> new GoodsNotFoundException(GoodsErrorCode.GOODS_NOT_FOUND));
    }

    public void checkGoodsStatusWithThrow(Long goodsId, GoodsStatus status) {
        GoodsEntity goodsEntity = this.getGoodsBy(goodsId);
        checkGoodsStatusWithThrow(goodsEntity, status);
    }

    public void checkGoodsStatusWithThrow(List<Long> goodsIdList, GoodsStatus status) {
        goodsIdList.forEach((goodsId) -> this.checkGoodsStatusWithThrow(goodsId, status));
    }

    private void checkGoodsStatusWithThrow(GoodsEntity goodsEntity, GoodsStatus status) {
        if (goodsEntity.getStatus() != status) {
            throw new InvalidGoodsStatusException(GoodsErrorCode.INVALID_GOODS_STRATEGY);
        }
    }

    private void checkEmptyGoodsListWithThrow(List<GoodsEntity> goodsList) {
        if (goodsList.isEmpty()) {
            throw new GoodsNotFoundException(GoodsErrorCode.GOODS_NOT_FOUND);
        }
    }

    public void setGoodsStatusBy(Long goodsId, GoodsStatus status) {
        GoodsEntity goodsEntity = getGoodsBy(goodsId);
        setGoodsStatusBy(status, goodsEntity);
    }

    private void setGoodsStatusBy(GoodsStatus status, GoodsEntity goodsEntity) {
        goodsEntity.setStatus(status);
        goodsRepository.save(goodsEntity);
    }

    public List<GoodsEntity> findAllByGoodsStatusWithThrow(GoodsStatus status) {
        List<GoodsEntity> goodsEntityList = goodsRepository.findAllByStatusOrderByIdDesc(status);
        checkEmptyGoodsListWithThrow(goodsEntityList);
        return goodsEntityList;
    }

    public List<GoodsEntity> findAllByReceivingIdWithThrow(Long receivingId) {
        List<GoodsEntity> goodsEntityList = findAllBy(receivingId);
        if (goodsEntityList.isEmpty()) {
            throw new GoodsNotFoundException(GoodsErrorCode.GOODS_NOT_FOUND);
        }
        return goodsEntityList;
    }

    public List<GoodsEntity> findAllByTakeBackIdWithThrow(Long takeBackId) {

        List<GoodsEntity> goodsEntityList = goodsRepository.findAllByTakeBackIdOrderByIdDesc(
            takeBackId);

        if (goodsEntityList.isEmpty()) {
            throw new GoodsNotFoundException(GoodsErrorCode.GOODS_NOT_FOUND);
        }
        return goodsEntityList;
    }

    public List<GoodsEntity> findAllByShippingIdWithThrow(Long shippingId) {

        List<GoodsEntity> goodsEntityList = goodsRepository.findAllByShippingIdOrderByIdDesc(
            shippingId);

        if (goodsEntityList.isEmpty()) {
            throw new GoodsNotFoundException(GoodsErrorCode.GOODS_NOT_FOUND);
        }
        return goodsEntityList;
    }

    public void setShippingId(ShippingRequest request, Long shippingId) {
        // GoodsStatus 를 SHIPPING_ING 으로 변경
        request.getGoodsIdList().forEach(goodsId -> {
            setGoodsStatusBy(goodsId, GoodsStatus.SHIPPING_ING);
        });

        List<GoodsEntity> goodsEntityList = getGoodsListBy(request.getGoodsIdList());

        goodsEntityList.forEach(goodsEntity -> {
            goodsEntity.setShippingId(shippingId);
            goodsRepository.save(goodsEntity);
        });
    }

    public void setUserId(GoodsEntity goodsEntity, Long userId) {
        goodsEntity.setUserId(userId);
        goodsRepository.save(goodsEntity);
    }

}