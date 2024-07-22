package warehouse.domain.goods.service;

import db.domain.goods.GoodsEntity;
import db.domain.goods.GoodsRepository;
import db.domain.goods.enums.GoodsStatus;
import db.domain.receiving.ReceivingEntity;
import global.errorcode.ErrorCode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import warehouse.common.error.GoodsErrorCode;
import warehouse.common.error.TakeBackErrorCode;
import warehouse.common.exception.goods.InvalidGoodsStatusException;
import warehouse.common.exception.takeback.TakeBackNotAllowedException;
import warehouse.common.exception.goods.GoodsNotFoundException;

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

    public List<GoodsEntity> findAllByReceivingIdWithThrow(Long receivingId) {
        return goodsRepository.findAllByReceivingIdOrderByIdDesc(receivingId);
    }

    public void abandonment(ReceivingEntity receiving) {

        List<GoodsEntity> goodsEntityList = goodsRepository.findAllByReceivingIdOrderByIdDesc(receiving.getId());
        if (goodsEntityList.isEmpty()){
            throw new GoodsNotFoundException(ErrorCode.NULL_POINT);
        }
        // TODO 회사 아이디 생성 후 Matching 필요
        goodsEntityList.forEach(goodsEntity -> {
            setAbandonmentAtAndUserId(goodsEntity);
            goodsRepository.save(goodsEntity);
        });
    }

    public void abandonment(List<Long> goodsIdList) {
        // TODO 회사 아이디 생성 후 Matching 필요
        goodsIdList.forEach(goodsId -> {
            GoodsEntity goodsEntity = goodsRepository.findFirstById(goodsId)
                .orElseThrow(() -> new GoodsNotFoundException(ErrorCode.NULL_POINT));
            setAbandonmentAtAndUserId(goodsEntity);
        });
    }

    public void setAbandonmentAtAndUserId(GoodsEntity goodsEntity) {
        goodsEntity.setUserId(0L);
        goodsEntity.setAbandonmentAt(LocalDateTime.now());
        goodsRepository.save(goodsEntity);
    }

    public void setTakeBackIdAndStatus(List<GoodsEntity> goodsEntityList, Long id,
        GoodsStatus goodsStatus) {
        goodsEntityList.forEach(goodsEntity -> {
            goodsEntity.setTakeBackId(id);
            goodsEntity.setStatus(goodsStatus);
            goodsRepository.save(goodsEntity);
        });
    }

    public List<GoodsEntity> getGoodsListBy(List<Long> goodsIdList) {
        return goodsRepository.findAllByIdIn(goodsIdList);
    }

    public GoodsEntity getGoodsListBy(Long goodsId) {
        return goodsRepository.findFirstById(goodsId).orElseThrow(() -> new GoodsNotFoundException(ErrorCode.NULL_POINT));
    }

    public void checkStoredGoodsAndStatusWithThrowBy(Long receivingId, GoodsStatus status){
        List<GoodsEntity> goodsList = goodsRepository.findAllByReceivingIdOrderByIdDesc(
            receivingId);
        checkEmptyGoodsListWithThrow(goodsList);
        checkGoodsStatusWithThrow(goodsList, status);
    }

    public void checkStoredGoodsAndStatusWithThrowBy(List<Long> goodsIdList, GoodsStatus status) {
        List<GoodsEntity> goodsList = goodsRepository.findAllByIdIn(goodsIdList);
        checkEmptyGoodsListWithThrow(goodsList);
        checkGoodsStatusWithThrow(goodsList, status);
    }

    private void checkGoodsStatusWithThrow(List<GoodsEntity> goodsList, GoodsStatus status) {
        goodsList.forEach(goodsEntity -> {
            if (goodsEntity.getStatus() != status) {
                throw new InvalidGoodsStatusException(GoodsErrorCode.INVALID_GODOS_STATUS);
            }
        });
    }

    private void checkEmptyGoodsListWithThrow(List<GoodsEntity> goodsList) {
        if (goodsList.isEmpty()) {
            throw new GoodsNotFoundException(ErrorCode.NULL_POINT);
        }
    }

    public void setGoodsStatusBy(List<Long> goodsIdList, GoodsStatus status) {
        List<GoodsEntity> goodsEntityList = goodsRepository.findAllByIdIn(goodsIdList);
        goodsEntityList.forEach(goodsEntity -> {
            goodsEntity.setStatus(status);
            goodsRepository.save(goodsEntity);
        });
    }

}