package delivery.domain;

import db.domain.goods.GoodsEntity;
import db.domain.goods.GoodsRepository;
import db.domain.goods.enums.GoodsStatus;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO 삭제 예정
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final GoodsRepository goodsRepository;

    /**
     * TODO 삭제 필요 - 임시
     * 물품 id를 이용하여 입고 진행 중인 물품의 상태를 입고 완료로 변경하는 테스트 api 추가 요청합니다.
     * (클라이언트에서 입고 완료된 물품을 출고, 중고 등으로 전환 UI 테스트용)
     * by. iOS
     */
    @PostMapping("/goods/{goodsId}")
    public String changGoodsStatus(@PathVariable Long goodsId){

        Optional<GoodsEntity> goods = goodsRepository.findById(goodsId);

        if(goods.isPresent()){
            GoodsEntity goodsEntity = goods.get();
            goodsEntity.setStatus(GoodsStatus.STORAGE);
            goodsRepository.save(goodsEntity);
            return "goods status changed !";
        }

        return "goods not found";
    }

}
