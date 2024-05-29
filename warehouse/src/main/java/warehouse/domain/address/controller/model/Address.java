package warehouse.domain.address.controller.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Address {

    // AddressEntity 를 사용하면 필요하지 않은 user 필드값이 null이 나옴
    private Long id;

    private String address;

    private String detailAddress;

    private int post;

    private Boolean basicAddress;
}
