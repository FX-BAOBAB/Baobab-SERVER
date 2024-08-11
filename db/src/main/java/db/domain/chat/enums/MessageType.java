package db.domain.chat.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageType {
    ENTER("입장"),
    QUIT("퇴장"),
    TALK("대화")
    ;

    private final String description;

}