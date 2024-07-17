package warehouse.domain.goods.service;

import db.domain.goods.GoodsEntity;
import db.domain.goods.GoodsRepository;
import db.domain.goods.enums.GoodsStatus;
import db.domain.receiving.ReceivingEntity;
import global.errorcode.ErrorCode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import warehouse.common.exception.goods.GoodsNotFoundException;

@Service
@RequiredArgsConstructor
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

    //TODO 예외처리
    public void validateTakeBackStatusWithThrow(Long receivingId){
        Optional<GoodsEntity> optionalGoodsEntity = goodsRepository.findFirstByReceivingId(receivingId);
        GoodsEntity goodsEntity = optionalGoodsEntity.orElseThrow(() ->
            new RuntimeException("반품 접수 상품이 존재하지 않습니다."));

        if (goodsEntity.getStatus() != GoodsStatus.RECEIVING) {
            throw new RuntimeException("이미 반품이 접수된 상품입니다.");
        }
    }

    //TODO 예외처리
    public void validateTakeBackStatusWithThrow(List<Long> goodsIdList) {
        List<GoodsEntity> goodsEntityList = goodsRepository.findAllByIdIn(goodsIdList);
        goodsEntityList.forEach(goodsEntity -> {
            if (goodsEntity.getStatus() != GoodsStatus.RECEIVING) {
                throw new RuntimeException("이미 반품이 접수된 상품입니다.");
            }
        });
    }

}
