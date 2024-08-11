package warehouse.common.error;

import global.errorcode.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ChatErrorCode implements ErrorCodeIfs {

    CHAT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1700, "채팅방이 존재하지 않습니다."),
    CHAT_MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1701, "채팅 메시지가 존재하지 않습니다."),
    CHAT_ROOM_EXISTS(HttpStatus.BAD_REQUEST.value(), 1702, "이미 존재하는 채팅방 입니다.");
    ;

    private final Integer httpCode;
    private final Integer errorCode;
    private final String description;

}
