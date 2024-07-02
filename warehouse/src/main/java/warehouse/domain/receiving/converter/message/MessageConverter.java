package warehouse.domain.receiving.converter.message;

import global.annotation.Converter;
import warehouse.domain.receiving.controller.model.common.MessageResponse;

@Converter
public class MessageConverter {
    public MessageResponse toMassageResponse(String message) {
        return MessageResponse.builder()
            .Message(message)
            .build();
    }
}
