package warehouse.domain.chat.controller;

import global.api.Api;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/{usedGoodsId}") //채팅방 개설 -> 구매자가
    @Operation(summary = "[채팅방 생성]", description = "물품 구매자가 채팅방을 생성함, ws://localhost:8080/chatting")
    public Api<ChatRoomResponse> createChatRoom(@PathVariable Long usedGoodsId,
        @AuthenticationPrincipal User user) {
        ChatRoomResponse response = chatBusiness.createChatRoom(usedGoodsId, user.getUsername());
        return Api.OK(response);
    }

    @PostMapping("/enter/{chatRoomId}") // 채팅방 입장
    @Operation(summary = "[채팅방 입장]", description = "구독 - /sub/chat/{chatRoomId}")
    public Api<List<ChatMessageResponse>> enterChatRoom(@PathVariable Long chatRoomId,
        @AuthenticationPrincipal User user) {
        List<ChatMessageResponse> response = chatBusiness.enterChatRoom(chatRoomId,
            user.getUsername());
        return Api.OK(response);
    }

    @PostMapping("/quit/{chatRoomId}") // 채팅방 퇴장
    @Operation(summary = "[채팅방 퇴장]")
    public Api<MessageResponse> quitChatRoom(@PathVariable Long chatRoomId,
        @AuthenticationPrincipal User user) {
        MessageResponse response = chatBusiness.quitChatRoom(chatRoomId, user.getUsername());
        return Api.OK(response);
    }

    @PostMapping("/message")
    @Operation(summary = "[message 전송]", description = "/pub/chat")
    public void sendMessage(@RequestBody ChatMessageRequest message, @AuthenticationPrincipal User user) {
        log.info(message.toString());
        chatBusiness.sendChatMessage(message, user.getUsername());
    }

    @GetMapping("/buy") // 구매 채팅방 조회
    @Operation(summary = "[구매 채팅방 조회]")
    public Api<List<ChatRoomResponse>> getBuyerChatRoom(@AuthenticationPrincipal User user) {
        List<ChatRoomResponse> response = chatBusiness.getBuyerChatRoom(user.getUsername());
        return Api.OK(response);
    }

    @GetMapping("/sell") // 판매 채팅방 조회
    @Operation(summary = "[판매 채팅방 조회]")
    public Api<List<ChatRoomResponse>> getSellerChatRoom(@AuthenticationPrincipal User user) {
        List<ChatRoomResponse> response = chatBusiness.getSellerChatRoom(user.getUsername());
        return Api.OK(response);
    }

    // TODO - TEST 용
    @GetMapping("/rooms") // 모든 채팅방 조회 test 용
    @Operation(summary = "[TEST 모든 채팅방 조회(redis)]")
    public Api<List<ChatRoomResponse>> rooms() {
        List<ChatRoomResponse> responses = chatBusiness.findAllChatRoom();
        return Api.OK(responses);
    }

    // TODO - TEST 용
    @GetMapping("/{chatRoomId}") // 모든 채팅 메시지 조회
    @Operation(summary = "[TEST chatRoomId 로 모든 채팅 조회]")
    public Api<List<ChatMessageResponse>> getChatMessage(@PathVariable Long chatRoomId) {
        List<ChatMessageResponse> response = chatBusiness.getChatMessage(chatRoomId);
        return Api.OK(response);
    }

}
