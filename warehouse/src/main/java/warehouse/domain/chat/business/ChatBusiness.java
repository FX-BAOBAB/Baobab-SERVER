package warehouse.domain.chat.business;

import db.domain.chat.enums.ChatRoomStatus;
import db.domain.chat.room.ChatRoomEntity;
import db.domain.usedgoods.enums.UsedGoodsStatus;
import global.annotation.Business;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import db.domain.chat.message.ChatMessageEntity;
import warehouse.common.error.ChatErrorCode;
import warehouse.common.exception.chat.ChatRoomAccessDeniedException;
import warehouse.common.exception.chat.ChatRoomInactiveException;
import warehouse.common.exception.chat.SellerAndBuyerSameException;
import warehouse.domain.chat.controller.model.request.ChatMessageRequest;
import warehouse.domain.chat.controller.model.response.ChatMessageResponse;
import warehouse.domain.chat.controller.model.response.ChatRoomResponse;
import warehouse.domain.chat.controller.model.response.MessageResponse;
import warehouse.domain.chat.converter.ChatConverter;
import warehouse.domain.chat.service.ChatService;
import warehouse.domain.usedgoods.service.UsedGoodsService;
import warehouse.domain.users.service.UsersService;

@Business
@RequiredArgsConstructor
@Slf4j
public class ChatBusiness {

    private final ChatService chatService;
    private final UsedGoodsService usedGoodsService;
    private final UsersService usersService;
    private final ChatConverter chatConverter;


    /**
     * 1. 판매자가 자신의 글에 채팅을 할 수 없도록 제한
     * 2. 채팅방이 이미 존재하는지 확인 -> 있으면 예외
     * 3. ChatRoom 반환
     */
    public ChatRoomResponse createChatRoom(Long usedGoodsId, String email) {

        Long sellerId = usedGoodsService.getUsedGoodsBy(usedGoodsId)
            .getUserId();

        Long userId = usersService.getUserWithThrow(email).getId();

        validateSellerAndBuyerNotSameWithThrow(sellerId, userId); // 판매자와 구매자가 동일하지 않음을 확인

        chatService.existsChatRoomWithThrow(usedGoodsId, userId); // 채팅방이 존재하는지 확인 -> 있으면 error

        ChatRoomEntity chatRoomEntity = chatConverter.toEntity(usedGoodsId, userId);

        ChatRoomEntity createdChatRoom = chatService.createChatRoomBy(chatRoomEntity); // 채팅방 생성

        return chatConverter.toResponse(createdChatRoom);

    }

    /**
     * 1. chatRoomId & userId 로 채팅방 접근 권한 체크
     * 2. 채팅방 구독
     */
    public List<ChatMessageResponse> enterChatRoom(Long chatRoomId, String email) {

        Long userId = usersService.getUserWithThrow(email).getId();

        ChatRoomEntity chatRoomEntity = chatService.getChatRoomBy(chatRoomId);

        validateUserAccessToChatRoom(userId, chatRoomEntity);

        chatService.subscribe(chatRoomId);

        List<ChatMessageEntity> chatMessage = chatService.getChatMessage(chatRoomId);

        return chatConverter.toChatMessageListResponse(chatMessage);

    }

    /**
     * 1. 채팅방 status 점검
     * 2. chatRoomId & userId 로 채팅방 접근 권한 체크
     * 3. 채팅 메시지 전송
     */
    public void sendChatMessage(ChatMessageRequest message, String email) {

        Long userId = usersService.getUserWithThrow(email).getId();

        ChatRoomEntity chatRoomEntity = chatService.getChatRoomBy(message.getChatRoomId());

        checkChatRoomInactiveWithThrow(chatRoomEntity.getStatus()); // INACTIVE -> 예외

        validateUserAccessToChatRoom(userId, chatRoomEntity);

        ChatMessageEntity chatMessageEntity = chatConverter.toChatMessage(message, userId);

        chatService.sendChatMessage(chatMessageEntity);

    }

    /**
     * 1. chatRoomId & userId 로 채팅방 접근 권한 체크
     * 2. 채팅방 status -> INACTIVATE 로 변경
     */
    public MessageResponse quitChatRoom(Long chatRoomId, String email) { // 채팅 비활성화

        Long userId = usersService.getUserWithThrow(email).getId();

        ChatRoomEntity chatRoomEntity = chatService.getChatRoomBy(chatRoomId);

        validateUserAccessToChatRoom(userId, chatRoomEntity);

        chatService.setChatRoomStatusBy(chatRoomEntity, ChatRoomStatus.INACTIVE);

        return chatConverter.toMessageResponse("채팅방이 비활성화 되었습니다.");
    }

    public List<ChatRoomResponse> getBuyerChatRoom(String email) {
        Long userId = usersService.getUserWithThrow(email).getId();
        List<ChatRoomEntity> createdChatRooms = chatService.getChatRoomListBy(userId);
        return chatConverter.toResponse(createdChatRooms);
    }


    public List<ChatRoomResponse> getSellerChatRoom(String email) {
        Long userId = usersService.getUserWithThrow(email).getId();

        List<Long> usedGoodsIdList = usedGoodsService.getUsedGoodsListBy(userId).stream()
            .map(usedGoodsEntity -> usedGoodsEntity.getId()).toList();

        List<ChatRoomEntity> chatRoomEntityList = chatService.getChatRoomListBy(usedGoodsIdList);

        return chatConverter.toResponse(chatRoomEntityList);
    }

    // TODO - TEST 용
    public List<ChatMessageResponse> getChatMessage(Long chatRoomId) {
        List<ChatMessageEntity> chatMessageEntityList = chatService.getChatMessage(chatRoomId);
        return chatConverter.toChatMessageListResponse(chatMessageEntityList);
    }


    // TODO - TEST 용
    public List<ChatRoomResponse> findAllChatRoom() { // 테스트용
        List<ChatRoomEntity> chatRoomEntity = chatService.findAllChatRoom();
        return chatConverter.toResponse(chatRoomEntity);

    }

    // 판매자 또는 구매자의 권한 검증
    private void validateUserAccessToChatRoom(Long userId, ChatRoomEntity chatRoomEntity) {
        Long usedGoodsId = chatRoomEntity.getUsedGoodsId();
        Long sellerId = usedGoodsService.getUsedGoodsBy(usedGoodsId).getUserId();
        if (!sellerId.equals(userId) && !chatRoomEntity.getUserId().equals(userId)) {
            throw new ChatRoomAccessDeniedException(ChatErrorCode.CHAT_ROOM_ACCESS_DENIED);
        }
    }

    // 판매자와 구매자가 동일한지 검증
    private void validateSellerAndBuyerNotSameWithThrow(Long sellerId, Long userId) {
        if (sellerId.equals(userId)) {
            throw new SellerAndBuyerSameException(ChatErrorCode.SELLER_AND_BUYER_SAME);
        }
    }

    // 비활성화된 상태인지 확인
    private void checkChatRoomInactiveWithThrow(ChatRoomStatus status) {
        if(status.equals(ChatRoomStatus.INACTIVE)) {
            throw new ChatRoomInactiveException(ChatErrorCode.CHAT_ROOM_INACTIVE);
        }
    }

}
