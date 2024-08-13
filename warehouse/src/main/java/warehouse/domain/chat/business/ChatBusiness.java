package warehouse.domain.chat.business;

import db.domain.chat.room.ChatRoomEntity;
import db.domain.usedgoods.UsedGoodsEntity;
import db.domain.usedgoods.enums.UsedGoodsStatus;
import global.annotation.Business;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import db.domain.chat.message.ChatMessageEntity;
import org.springframework.security.core.userdetails.User;
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
     * 1. 구매자 ID 존재하는지 확인.
     * 2. 채팅방이 이미 존재하는지 확인 -> 있으면 예외
     * 3. 채팅방 고유 ID 반환
     */
    public ChatRoomResponse createChatRoom(Long usedGoodsId, String email) {

        Long userId = usersService.getUserWithThrow(email).getId(); // 사용자 인증

        chatService.existsChatRoomWithThrow(usedGoodsId, userId); // 채팅방이 존재하는지 확인 -> 있으면 error

        ChatRoomEntity chatRoomEntity = chatConverter.toEntity(usedGoodsId, userId);

        ChatRoomEntity createdChatRoom = chatService.createChatRoomBy(chatRoomEntity); // 채팅방 생성

//        chatService.subscribe(createdChatRoom.getId()); // Topic 생성 후 채팅방 구독 -> 두 번 발송되는 문제 발생

        return chatConverter.toResponse(createdChatRoom);

    }

    public MessageResponse subscribeChatRoom(Long chatRoomId) {
        chatService.subscribe(chatRoomId);
        return chatConverter.toMessageResponse("채팅방 구독이 완료되었습니다.");
    }

    public void sendChatMessage(ChatMessageRequest message, Long userId) {

//        Long userId = usersService.getUserWithThrow(email).getId();
        ChatMessageEntity chatMessageEntity = chatConverter.toChatMessage(message, null);

        chatService.sendChatMessage(chatMessageEntity);

    }

    public List<ChatRoomResponse> findAllChatRoom() { // 테스트용
        List<ChatRoomEntity> chatRoomEntity = chatService.findAllChatRoom();
        return chatConverter.toResponse(chatRoomEntity);

    }

    /**
     * INACTIVATE 채팅방 -> 채팅 불가 -> 예외
     * TODO - chatRoom 에 관련된 sellerId 와 buyerId 만 사용하도록 해야 함
     */
    public MessageResponse quitChatRoom(Long chatRoomId) { // 채팅 비활성화
        ChatRoomEntity chatRoomEntity = chatService.getChatRoomBy(chatRoomId);
        chatService.setChatRoomStatusBy(chatRoomEntity, ChatRoomStatus.INACTIVATE);
        return chatConverter.toMessageResponse("채팅방이 비활성화 되었습니다.");
    }

    public MessageResponse deleteChatRoom(Long chatRoomId) { // 채팅 비활성화
        ChatRoomEntity chatRoomEntity = chatService.getChatRoomBy(chatRoomId);
        chatService.setChatRoomStatusBy(chatRoomEntity, ChatRoomStatus.DELETED);
        return chatConverter.toMessageResponse("채팅방이 삭제되었습니다.");
    }

    public List<ChatRoomResponse> getBuyerChatRoom(String email) {
        Long userId = usersService.getUserWithThrow(email).getId(); // 사용자 인증
        List<ChatRoomEntity> createdChatRooms = chatService.getChatRoomListBy(userId);
        return chatConverter.toResponse(createdChatRooms);
    }


    public List<ChatRoomResponse> getSellerChatRoom(String email) {
        Long userId = usersService.getUserWithThrow(email).getId(); // 사용자 인증

        List<Long> usedGoodsIdList = usedGoodsService.getUsedGoodsListBy(userId).stream()
            .map(usedGoodsEntity -> usedGoodsEntity.getId()).toList();

        List<ChatRoomEntity> chatRoomEntityList = chatService.getChatRoomListBy(usedGoodsIdList);

        return chatConverter.toResponse(chatRoomEntityList);
    }

    public List<ChatMessageResponse> getChatMessage(Long chatRoomId) {
        List<ChatMessageEntity> chatMessageEntityList = chatService.getChatMessage(chatRoomId);
        return chatConverter.toChatMessageResponse(chatMessageEntityList);
    }

}
