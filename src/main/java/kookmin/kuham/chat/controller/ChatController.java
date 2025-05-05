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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRepository chatRepository;
    private final ChatService chatService;

    // 클라이언트에서 "/app/chat/message"로 메시지를 보내면 이 메서드가 호출됨
    @MessageMapping("chat/message")
    public void sendMessage(ChatMessageRequest message) {
        //Todo: jwt에서 실제 사용자 ID로 변경해야 함
        String userId = "993e64e7-40b0-4c9d-afc0-5d34ced2a210";
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
    public ResponseEntity<?> readMessage(@RequestBody ReadMessageRequest req) {
        String userId = "userId"; // jwt에서 실제 사용자 ID로 변경해야 함
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
    @GetMapping("/chat/enter")
    public ResponseEntity<?> enterChatRoom(String roomId) {
        // 채팅방 입장 처리 로직
        ChatRoom room = chatRepository.findById(roomId).orElseThrow(RoomNotExistException::new);
        return ResponseEntity.ok(room);
    }

    @Operation(summary = "채팅방 목록")
    @GetMapping("/chat/lists")
    public ResponseEntity<List<ChatListResponse>> getChatList() {
        // 채팅방 목록 조회 처리 로직
        return ResponseEntity.ok(chatService.getChats());
    }

}
