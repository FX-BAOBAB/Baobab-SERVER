package store.domain.loading.controller.model;

import db.domain.store.enums.StoreLocation;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreRequest {

    private List<Long> goodsIds;

    private List<StoreLocation> storeLocation;

}
