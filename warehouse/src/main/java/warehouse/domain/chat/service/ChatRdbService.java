package warehouse.domain.chat.service;

import db.domain.chat.message.ChatMessageEntity;
import db.domain.chat.message.ChatMessageRepository;
import db.domain.chat.room.ChatRoomRepository;
import db.domain.chat.room.ChatRoomEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import warehouse.common.error.ChatErrorCode;
import warehouse.common.exception.chat.ChatMessageNotFoundException;
import warehouse.common.exception.chat.ChatRoomNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRdbService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatRoomEntity getChatRoomBy(Long chatRoomId) {
        return chatRoomRepository.findFirstById(chatRoomId)
            .orElseThrow(() -> new ChatRoomNotFoundException(ChatErrorCode.CHAT_ROOM_NOT_FOUND));
    }

    public List<ChatRoomEntity> getChatRoomListBy(Long userId) {
        List<ChatRoomEntity> chatRoomEntityList = chatRoomRepository.findByUserId(userId);
        if (chatRoomEntityList.isEmpty()) {
            throw new ChatRoomNotFoundException(ChatErrorCode.CHAT_ROOM_NOT_FOUND);
        }
        return chatRoomEntityList;
    }

    public ChatRoomEntity getChatRoomBy(Long usedGoodsId, Long userId) {
        return chatRoomRepository.findFirstByUsedGoodsIdAndUserId(usedGoodsId, userId)
            .orElseThrow(() -> new ChatRoomNotFoundException(ChatErrorCode.CHAT_ROOM_NOT_FOUND));
    }

    public List<ChatRoomEntity> getChatRoomBy(List<Long> usedGoodsIdList) {
        List<ChatRoomEntity> chatRoomEntityList = chatRoomRepository.findByUsedGoodsIdIn(
            usedGoodsIdList);
        if (chatRoomEntityList.isEmpty()) {
            throw new ChatRoomNotFoundException(ChatErrorCode.CHAT_ROOM_NOT_FOUND);
        }
        return chatRoomEntityList;
    }

    public List<ChatMessageEntity> getChatMessageBy(Long chatRoomId) {
        List<ChatMessageEntity> chatMessageEntityList = chatMessageRepository.findAllByChatRoomId(
            chatRoomId);
        if(chatMessageEntityList.isEmpty()) {
            throw new ChatMessageNotFoundException(ChatErrorCode.CHAT_MESSAGE_NOT_FOUND);
        }
        return chatMessageEntityList;
    }

    public void saveChatRoom(List<ChatRoomEntity> chatRoomEntityList) {
        chatRoomRepository.saveAll(chatRoomEntityList);
    }

    public void saveChatMessage(List<ChatMessageEntity> chatMessageEntityList) {
        chatMessageRepository.saveAll(chatMessageEntityList);
    }
}
