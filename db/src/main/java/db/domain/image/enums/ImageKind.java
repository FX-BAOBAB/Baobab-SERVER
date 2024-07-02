package db.domain.image.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageKind {


    BASIC("기본 사진"),
    FAULT("결함 사진")
    ;

    private final String description;

}
