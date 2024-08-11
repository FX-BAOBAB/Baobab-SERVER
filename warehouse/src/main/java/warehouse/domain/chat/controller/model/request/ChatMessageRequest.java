package warehouse.domain.chat.controller.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import db.domain.chat.enums.MessageType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageRequest {

    private String message; // 메시지 내용
    private MessageType type; // 메시지 타입 //TODO MessageType 에 대한 기능 개발 필요
    private Long chatRoomId; // 방 ID

}