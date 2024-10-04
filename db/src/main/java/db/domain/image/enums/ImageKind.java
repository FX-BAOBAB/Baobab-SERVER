package db.domain.image.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageKind {


    BASIC("기본 사진"),
    FAULT("결함 사진"),
    PROFILE("프로필 사진"),
    DELIVERY("배송 결함"),
    AR("3D 모델링")
    ;

    private final String description;

}
