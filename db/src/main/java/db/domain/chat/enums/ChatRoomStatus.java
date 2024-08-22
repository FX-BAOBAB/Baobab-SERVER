package db.domain.chat.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChatRoomStatus {

    ACTIVE(1, "활성화 채팅방", "활성화된 채팅방입니다."),
    INACTIVE(2, "비활성화 채팅방", "비활성화된 채팅방입니다.")
    ;

    private final int current;
    private final String status;
    private final String description;
}
