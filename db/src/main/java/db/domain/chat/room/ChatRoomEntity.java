package db.domain.chat.room;

import db.common.BaseEntity;
import db.domain.chat.enums.ChatRoomStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat_room")
@SuperBuilder
public class ChatRoomEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private Long userId; // 구매자 id

    private Long usedGoodsId; // 중고 판매 글 ID

    private ChatRoomStatus status;

    private LocalDateTime createdAt;

}
