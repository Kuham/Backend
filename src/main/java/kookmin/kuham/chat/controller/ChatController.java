package kookmin.kuham.chat.controller;

import kookmin.kuham.chat.exception.RoomNotExistException;
import kookmin.kuham.chat.repository.ChatRepository;
import kookmin.kuham.chat.dto.request.ChatMessageRequest;
import kookmin.kuham.chat.schema.ChatRoom;
import kookmin.kuham.chat.schema.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRepository chatRepository;

    // 클라이언트에서 "/app/chat/message"로 메시지를 보내면 이 메서드가 호출됨
    @MessageMapping("chat/message")
    public void sendMessage(ChatMessageRequest message) {
        // 메시지를 데이터베이스에 저장
        ChatRoom room = chatRepository.findById(message.getRoomId()).orElseThrow(RoomNotExistException::new);
        ChatMessage newMessage = message.toEntitiy();
        room.getMessages().add(newMessage);
        chatRepository.save(room);

        // 메시지를 수신한 후, 해당 방의 모든 사용자에게 메시지를 전송
        messagingTemplate.convertAndSend("/topic/chatroom/" + message.getRoomId(), message);
    }

}
