package store.common.error;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ChatErrorCode implements ErrorCodeIfs {

    CHAT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1700, "채팅방이 존재하지 않습니다."),
    CHAT_MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1701, "채팅 메시지가 존재하지 않습니다."),
    CHAT_ROOM_EXISTS(HttpStatus.BAD_REQUEST.value(), 1702, "이미 존재하는 채팅방 입니다."),
    SELLER_AND_BUYER_SAME(HttpStatus.BAD_REQUEST.value(), 1703, "판매자는 자신의 상품에 대해 채팅방을 생성할 수 없습니다."),
    CHAT_ROOM_INACTIVE(HttpStatus.BAD_REQUEST.value(), 1704, "채팅방이 비활성화 되어 채팅 전송이 불가능합니다."),
    CHAT_ROOM_ACCESS_DENIED(HttpStatus.BAD_REQUEST.value(), 1705, "채팅방 접근 권한이 존재하지 않습니다.")
    ;

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;

}
