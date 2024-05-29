package warehouse.domain.address.errorcode;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum AddressErrorCode implements ErrorCodeIfs {

    //TODO 에러코드 수정
    NOT_FOUND_ADDRESS(404, 404, "주소 정보를 찾을 수 없습니다."),
    INVALID_ADDRESS_DATA(HttpStatus.BAD_REQUEST.value(), 1105, "잘못된 주소 데이터입니다.");

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;
}
