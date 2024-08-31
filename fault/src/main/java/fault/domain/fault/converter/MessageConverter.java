package fault.domain.fault.converter;

import fault.domain.fault.controller.model.common.MessageResponse;
import global.annotation.Converter;

@Converter
public class MessageConverter {
    public MessageResponse toMassageResponse(String message) {
        return MessageResponse.builder()
            .Message(message)
            .build();
    }
}
