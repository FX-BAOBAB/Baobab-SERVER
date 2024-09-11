package db.domain.store.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoreLocation {

    S1( "서울"),
    S2( "부산"),
    S3("대구"),
    S4( "대전"),
    S5( "목포"),
    ;

    private final String location;

}
