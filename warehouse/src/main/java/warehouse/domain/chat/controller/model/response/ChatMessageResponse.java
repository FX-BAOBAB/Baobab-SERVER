package warehouse.domain.chat.controller.model.response;

import db.domain.chat.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageResponse {

    private String message; // 메시지 내용
    private MessageType type; // 메시지 타입
    private Long chatRoomId; // 방 ID
    private Long userId; // 발신자 ID
    private String createdAt;

}
