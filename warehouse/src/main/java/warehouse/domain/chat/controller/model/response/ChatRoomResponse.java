package warehouse.domain.chat.controller.model.response;

import db.domain.chat.enums.ChatRoomStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomResponse {

    private Long chatRoomId;
    private Long usedGoodsId; // 중고 판매 글 ID
    private ChatRoomStatus status;
    private LocalDateTime createdAt;

}
