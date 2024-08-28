package image.common.exception;

import global.api.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import image.common.error.ChatErrorCode;
import image.common.exception.chat.ChatMessageNotFoundException;
import image.common.exception.chat.ChatRoomAccessDeniedException;
import image.common.exception.chat.ChatRoomExistsException;
import image.common.exception.chat.ChatRoomInactiveException;
import image.common.exception.chat.ChatRoomNotFoundException;
import image.common.exception.chat.SellerAndBuyerSameException;

@Slf4j
@RestControllerAdvice
public class ChatExceptionHandler {

    @ExceptionHandler(value = ChatRoomNotFoundException.class)
    public ResponseEntity<Api<Object>> chatRoomNotFoundException(ChatRoomNotFoundException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Api.ERROR(ChatErrorCode.CHAT_ROOM_NOT_FOUND));
    }

    @ExceptionHandler(value = ChatMessageNotFoundException.class)
    public ResponseEntity<Api<Object>> chatMessageNotFoundException(
        ChatMessageNotFoundException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Api.ERROR(ChatErrorCode.CHAT_MESSAGE_NOT_FOUND));
    }

    @ExceptionHandler(value = ChatRoomExistsException.class)
    public ResponseEntity<Api<Object>> chatRoomExistsException(ChatRoomExistsException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(ChatErrorCode.CHAT_ROOM_EXISTS));
    }

    @ExceptionHandler(value = SellerAndBuyerSameException.class)
    public ResponseEntity<Api<Object>> sellerAndBuyerSameException(SellerAndBuyerSameException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(ChatErrorCode.SELLER_AND_BUYER_SAME));
    }

    @ExceptionHandler(value = ChatRoomInactiveException.class)
    public ResponseEntity<Api<Object>> chatRoomInactiveException(ChatRoomInactiveException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(ChatErrorCode.CHAT_ROOM_INACTIVE));
    }

    @ExceptionHandler(value = ChatRoomAccessDeniedException.class)
    public ResponseEntity<Api<Object>> chatRoomAccessDeniedException(
        ChatRoomAccessDeniedException e) {
        log.info("", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Api.ERROR(ChatErrorCode.CHAT_ROOM_ACCESS_DENIED));
    }

}