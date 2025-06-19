package kookmin.kuham.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import kookmin.kuham.chat.dto.request.ReadMessageRequest;
import kookmin.kuham.chat.dto.response.ChatListResponse;
import kookmin.kuham.chat.exception.RoomNotExistException;
import kookmin.kuham.chat.exception.UserNotContainException;
import kookmin.kuham.chat.repository.ChatRepository;
import kookmin.kuham.chat.dto.request.ChatMessageRequest;
import kookmin.kuham.chat.schema.ChatRoom;
import kookmin.kuham.chat.schema.ChatMessage;
import kookmin.kuham.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRepository chatRepository;
    private final ChatService chatService;

    // 클라이언트에서 "/app/chat/message"로 메시지를 보내면 이 메서드가 호출됨
    @MessageMapping("chat/message")
    public void sendMessage(ChatMessageRequest message, Principal principal) {

        // 메시지를 보낸 사용자의 ID를 가져옴
        String userId = principal.getName();
        // 메시지를 데이터베이스에 저장
        ChatRoom room = chatRepository.findById(message.getRoomId()).orElseThrow(RoomNotExistException::new);
        // 만약 해당 사용자가 채팅방에 포함되어 있지 않다면 예외 발생
        if (!room.getUserIds().contains(userId)){
            throw new UserNotContainException();
        }
        // 채팅방에 메시지 추가
        ChatMessage newMessage = message.toEntity();
        newMessage.setSender(userId);
        newMessage.setReadBy(List.of(userId));

        room.getMessages().add(newMessage);
        chatRepository.save(room);

        // 메시지를 수신한 후, 해당 방의 모든 사용자에게 메시지를 전송
        messagingTemplate.convertAndSend("/topic/chatroom/" + message.getRoomId(), newMessage);
    }

    @Operation(summary = "메시지 읽음 처리")
    @PutMapping("chat/read")
    public ResponseEntity<?> readMessage(@RequestBody ReadMessageRequest req,@AuthenticationPrincipal String userId) {

        // 메시지 읽음 처리 로직
        ChatRoom room = chatRepository.findByRoomId(req.roomId());
        room.getMessages().stream().filter(message ->message.getMessageId().equals(req.messageId()))
                .findFirst()
                .ifPresent(message -> {
                    if (message.getReadBy().contains(userId)) return;
                    message.getReadBy().add(userId);
                    chatRepository.save(room);
                });
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "채팅방 입장")
    @GetMapping("/chat/enter/{roomId}")
    public ResponseEntity<?> enterChatRoom(@PathVariable("roomId") String roomId,@AuthenticationPrincipal String userId) {
        // 채팅방 입장 처리 로직
        ChatRoom room = chatRepository.findById(roomId).orElseThrow(RoomNotExistException::new);
        //채팅방의 메세지 기록 가져오기
        return ResponseEntity.ok(chatService.getChatContents(room,userId));
    }

    @Operation(summary = "채팅방 목록")
    @GetMapping("/chat/lists")
    public ResponseEntity<List<ChatListResponse>> getChatList(@AuthenticationPrincipal String userId) {
        // 채팅방 목록 조회 처리 로직
        return ResponseEntity.ok(chatService.getChats(userId));
    }


}
