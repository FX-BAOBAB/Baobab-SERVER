package warehouse.domain.chat.service;

import db.domain.chat.room.ChatRoomEntity;
import db.domain.chat.enums.ChatRoomStatus;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import db.domain.chat.message.ChatMessageEntity;
import warehouse.common.error.ChatErrorCode;
import warehouse.common.exception.chat.ChatRoomExistsException;
import warehouse.domain.chat.pusbsub.RedisPublisher;
import warehouse.domain.chat.pusbsub.RedisSubscriber;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final ChatRdbService chatRdbService;
    private final RedisMessageListenerContainer redisMessageListener; // 채팅방(topic)에 발행되는 메시지를 처리할 Listener
    private final RedisSubscriber redisSubscriber; // 구독 처리 서비스
    private final RedisPublisher redisPublisher; // 발행
    private static final String CHAT_ROOMS = "CHAT_ROOM";
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<Long, ChatMessageEntity> chatMessageRedisTemplate;

    private HashOperations<String, Long, ChatRoomEntity> opsHashChatRoom;
    private ListOperations<Long, ChatMessageEntity> opsListChatMessage;

    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
        opsListChatMessage = chatMessageRedisTemplate.opsForList();
        redisMessageListener.addMessageListener(redisSubscriber, new PatternTopic("*"));
    }

    // TODO 테스트용
    public List<ChatRoomEntity> findAllChatRoom() {
        return opsHashChatRoom.entries(CHAT_ROOMS).values().stream().toList();
    }

    /**
     * Redis 에 해당 채팅방 정보를 확인 Redis 에 채팅방 정보가 존재하지 않다면 RDB 에서 가져오고 Redis 에 저장 둘 다 존재하지 않을 경우 예외 발생
     */
    public ChatRoomEntity getChatRoomBy(Long chatRoomId) {
        ChatRoomEntity chatRoomEntity = opsHashChatRoom.get(CHAT_ROOMS, chatRoomId);
        if (chatRoomEntity == null) {
            chatRoomEntity = getChatRoomEntityFromRdb(chatRoomId);
        }
        return chatRoomEntity;
    }

    /**
     * 판매 채팅방 조회(Delete 제외) Redis 에 해당 채팅방 정보를 확인 Redis 에 채팅방 정보가 존재하지 않다면 RDB 에서 가져오고 Redis 에 저장 둘 다
     * 존재하지 않을 경우 예외 발생
     */
    public List<ChatRoomEntity> getChatRoomListBy(List<Long> usedGoodsIdList) {
        List<ChatRoomEntity> chatRoomEntityList = opsHashChatRoom.entries(CHAT_ROOMS).values()
            .stream()
            .filter(
                chatRoomEntity -> usedGoodsIdList.contains(chatRoomEntity.getUsedGoodsId()))
            .toList();
        if (chatRoomEntityList.isEmpty()) {
            chatRoomEntityList = getChatRoomEntityListFromRdb(usedGoodsIdList);
        }
        return chatRoomEntityList;
    }

    /**
     * 구매 채팅방 조회(Delete 제외) Redis 에 해당 채팅방 정보를 확인 Redis 에 채팅방 정보가 존재하지 않다면 RDB 에서 가져오고 Redis 에 저장 둘 다
     * 존재하지 않을 경우 예외 발생
     */
    public List<ChatRoomEntity> getChatRoomListBy(Long userId) {
        List<ChatRoomEntity> chatRoomEntityList = opsHashChatRoom.entries(CHAT_ROOMS).values()
            .stream().filter(chatRoomEntity -> chatRoomEntity.getUserId().equals(userId)).toList();
        if (chatRoomEntityList.isEmpty()) {
            chatRoomEntityList = getChatRoomEntityListFromRdb(userId);
        }
        return chatRoomEntityList;
    }

    /**
     * 채팅방 생성 : 서버간 채팅방 공유를 위해 redis hash 에 저장한다.
     */
    public ChatRoomEntity createChatRoomBy(ChatRoomEntity chatRoomEntity) {
        Long chatRoomId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        chatRoomEntity.setId(chatRoomId);
        opsHashChatRoom.put(CHAT_ROOMS, chatRoomId, chatRoomEntity);
        return chatRoomEntity;
    }

    /**
     * 채팅방 입장 : redis 에 topic 을 만들고 pub/sub 통신을 하기 위해 리스너를 설정한다.
     */
    public void subscribe(Long chatRoomId) {
        ChannelTopic topic = new ChannelTopic(String.valueOf(chatRoomId));
        redisMessageListener.addMessageListener(redisSubscriber, topic);
    }

    /**
     * usedGoodsId 와 userId 로 채팅방이 존재하는지 확인하고, 존재할 경우 예외 발생 Redis, RDB 둘 다 조회
     */
    public void existsChatRoomWithThrow(Long usedGoodsId, Long userId) {
        boolean chatRoom = opsHashChatRoom.entries(CHAT_ROOMS).values().stream().anyMatch(
            chatRoomEntity -> chatRoomEntity.getUsedGoodsId().equals(usedGoodsId)
                && chatRoomEntity.getUserId().equals(userId));
        if (chatRoom) {
            throw new ChatRoomExistsException(ChatErrorCode.CHAT_ROOM_EXISTS);
        }
        try {
            if (chatRdbService.getChatRoomBy(usedGoodsId, userId) != null) {
                throw new ChatRoomExistsException(ChatErrorCode.CHAT_ROOM_EXISTS);
            }
        } catch (RuntimeException e) {}
    }

    /**
     * Topic 을 구독한 사용자에게 message 전송 Redis 에 삽입
     */
    public void sendChatMessage(ChatMessageEntity message) {
        ChannelTopic topic = new ChannelTopic(String.valueOf(message.getChatRoomId()));
        Long chatMessageId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        message.setId(chatMessageId);
        redisPublisher.publish(topic, message);
        opsListChatMessage.rightPush(message.getChatRoomId(), message);
    }

    public void setChatRoomStatusBy(ChatRoomEntity chatRoomEntity, ChatRoomStatus status) {
        chatRoomEntity.setStatus(status);
        opsHashChatRoom.put(CHAT_ROOMS, chatRoomEntity.getId(), chatRoomEntity);
    }

    /**
     * 채팅 메시지 전체 조회
     */
    public List<ChatMessageEntity> getChatMessage(Long chatRoomId) {
        List<ChatMessageEntity> chatMessageEntityList = opsListChatMessage.range(chatRoomId, 0,
            -1);
        if (chatMessageEntityList.isEmpty()) {
            chatMessageEntityList = chatRdbService.getChatMessageBy(chatRoomId);
        }
        return chatMessageEntityList;
    }


    /**
     * ChatRoom 정보와 ChatMessage 정보를 저정하고 캐시에서 삭제
     */
    @Scheduled(cron = "0 0 2 ? * 7") // 매주 토요일 오전 2시 저장
    private void saveRedisToRdb() {
        // chatRoom 저장
        List<ChatRoomEntity> chatRoomEntityList = opsHashChatRoom.values(CHAT_ROOMS);
        chatRdbService.saveChatRoom(chatRoomEntityList);

        // chatMessage 저장
        List<ChatMessageEntity> chatMessageEntityList = new ArrayList<>();
        chatRoomEntityList.forEach(chatRoomEntity -> {
            List<ChatMessageEntity> chatMessageList = opsListChatMessage.range(
                chatRoomEntity.getId(), 0, -1);
            if (chatMessageList != null) {
                chatMessageEntityList.addAll(chatMessageList);
            }
        });
        chatRdbService.saveChatMessage(chatMessageEntityList);
        this.deleteRedisData();
    }

    private void deleteRedisData() {
        log.info("Delete redis data ...");
        List<ChatRoomEntity> chatRoomEntityList = opsHashChatRoom.values(CHAT_ROOMS);
        chatRoomEntityList.forEach(chatRoomEntity ->
            opsListChatMessage.getOperations().delete(chatRoomEntity.getId())
        );
        opsHashChatRoom.getOperations().delete(CHAT_ROOMS);
    }

    private ChatRoomEntity getChatRoomEntityFromRdb(Long chatRoomId) {
        ChatRoomEntity chatRoomEntity;
        chatRoomEntity = chatRdbService.getChatRoomBy(chatRoomId);
        opsHashChatRoom.put(CHAT_ROOMS, chatRoomEntity.getId(), chatRoomEntity);
        return chatRoomEntity;
    }

    private List<ChatRoomEntity> getChatRoomEntityListFromRdb(List<Long> usedGoodsIdList) {
        List<ChatRoomEntity> chatRoomEntityList;
        chatRoomEntityList = chatRdbService.getChatRoomBy(usedGoodsIdList).stream().toList();
        chatRoomEntityList.forEach(chatRoomEntity -> {
            opsHashChatRoom.put(CHAT_ROOMS, chatRoomEntity.getId(), chatRoomEntity);
        });
        return chatRoomEntityList;
    }

    private List<ChatRoomEntity> getChatRoomEntityListFromRdb(Long userId) {
        List<ChatRoomEntity> chatRoomEntityList;
        chatRoomEntityList = chatRdbService.getChatRoomListBy(userId).stream().toList();
        chatRoomEntityList.forEach(chatRoomEntity -> {
            opsHashChatRoom.put(CHAT_ROOMS, chatRoomEntity.getId(), chatRoomEntity);
        });
        return chatRoomEntityList;
    }

}
