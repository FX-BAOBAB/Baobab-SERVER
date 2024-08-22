package warehouse.domain.chat.converter;

import db.domain.chat.enums.ChatRoomStatus;
import db.domain.chat.message.ChatMessageEntity;
import db.domain.chat.room.ChatRoomEntity;
import global.annotation.Converter;
import java.time.LocalDateTime;
import java.util.List;
import warehouse.domain.chat.controller.model.request.ChatMessageRequest;
import warehouse.domain.chat.controller.model.response.ChatMessageResponse;
import warehouse.domain.chat.controller.model.response.ChatRoomResponse;
import warehouse.domain.chat.controller.model.response.MessageResponse;

@Converter
public class ChatConverter {

    public ChatRoomEntity toEntity(Long usedGoodsId, Long userId) {
        return ChatRoomEntity.builder()
            .userId(userId)
            .createdAt(LocalDateTime.now())
            .usedGoodsId(usedGoodsId)
            .status(ChatRoomStatus.ACTIVE)
            .build();
    }

    public ChatRoomResponse toResponse(ChatRoomEntity chatRoomEntity) {
        return ChatRoomResponse.builder()
            .chatRoomId(chatRoomEntity.getId())
            .usedGoodsId(chatRoomEntity.getUsedGoodsId())
            .status(chatRoomEntity.getStatus())
            .createdAt(chatRoomEntity.getCreatedAt())
            .build();
    }

    public List<ChatRoomResponse> toResponse(List<ChatRoomEntity> chatRoomEntityList) {
        return chatRoomEntityList.stream().map(chatRoomEntity -> toResponse(chatRoomEntity))
            .toList();
    }

    public List<ChatMessageResponse> toChatMessageListResponse(
        List<ChatMessageEntity> chatMessageEntityList) {
        return chatMessageEntityList.stream().map(chatMessageEntity -> ChatMessageResponse.builder()
            .message(chatMessageEntity.getMessage())
            .chatRoomId(chatMessageEntity.getChatRoomId())
            .userId(chatMessageEntity.getUserId())
            .createdAt(chatMessageEntity.getCreatedAt())
            .build()).toList();
    }

    public ChatMessageEntity toChatMessage(ChatMessageRequest message, Long userId) {
        return ChatMessageEntity.builder()
            .message(message.getMessage())
            .chatRoomId(message.getChatRoomId())
            .userId(userId)
            .createdAt(String.valueOf(LocalDateTime.now()))
            .build();
    }

    public MessageResponse toMessageResponse(String message) {
        return MessageResponse.builder()
            .message(message)
            .build();

    }

}
