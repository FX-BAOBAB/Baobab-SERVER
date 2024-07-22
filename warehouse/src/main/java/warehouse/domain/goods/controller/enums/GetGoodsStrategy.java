package warehouse.domain.goods.controller.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GetGoodsStrategy {

    RECEIVING("RECEIVING"),
    TAKE_BACK("TAKE_BACK"),
    SHIPPING("SHIPPING"),
    ;

    private final String description;

}
