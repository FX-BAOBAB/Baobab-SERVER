package db.domain.chat.message;

import db.domain.chat.enums.MessageType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat_message")
@SuperBuilder
public class ChatMessageEntity implements Serializable  {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    private String message; // 메시지 내용
    private MessageType type; // 메시지 타입
    private Long chatRoomId; // 방 ID
    private Long userId; // 발신자 ID
    private String createdAt;

}