package warehouse.domain.chat.controller;

import global.api.Api;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warehouse.domain.chat.business.ChatBusiness;
import warehouse.domain.chat.controller.model.request.ChatMessageRequest;
import warehouse.domain.chat.controller.model.response.ChatMessageResponse;
import warehouse.domain.chat.controller.model.response.ChatRoomResponse;
import warehouse.domain.chat.controller.model.response.MessageResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
@Slf4j
public class ChatApiController {

    private final ChatBusiness chatBusiness;

    @GetMapping("/rooms") // 모든 채팅방 조회 test 용
    public Api<List<ChatRoomResponse>> rooms() {
        List<ChatRoomResponse> responses = chatBusiness.findAllChatRoom();
        return Api.OK(responses);
    }

    @PostMapping("/{usedGoodsId}") //채팅방 개설 -> 구매자가
    public Api<ChatRoomResponse> enterChatRoom(@PathVariable Long usedGoodsId,
        @AuthenticationPrincipal User user) {
        ChatRoomResponse response = chatBusiness.createChatRoom(usedGoodsId, user.getUsername());
        return Api.OK(response);
    }

    @PostMapping("/room/{chatRoomId}") // 채팅방 퇴장
    public Api<MessageResponse> quitChatRoom(@PathVariable Long chatRoomId) {
        MessageResponse response = chatBusiness.quitChatRoom(chatRoomId);
        return Api.OK(response);
    }

    @GetMapping("/room/buy") // 구매 채팅방 조회
    public Api<List<ChatRoomResponse>> getBuyerChatRoom(@AuthenticationPrincipal User user) {
        List<ChatRoomResponse> response = chatBusiness.getBuyerChatRoom(user.getUsername());
        return Api.OK(response);
    }

    @GetMapping("/room/sell") // 판매 채팅방 조회
    public Api<List<ChatRoomResponse>> getSellerChatRoom(@AuthenticationPrincipal User user) {
        List<ChatRoomResponse> response = chatBusiness.getSellerChatRoom(user.getUsername());
        return Api.OK(response);
    }

}
