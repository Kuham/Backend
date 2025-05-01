package kookmin.kuham.chat.controller;

import kookmin.kuham.chat.dto.ChatRequest;
import kookmin.kuham.chat.dto.response.ChatRoomReponse;
import kookmin.kuham.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatService chatService;

    @PostMapping("/start")
    public ResponseEntity<ChatRoomReponse> startChat(@RequestBody ChatRequest chatRequest){
            ChatRoomReponse room = chatService.createOrFindRoom(chatRequest.sender(), chatRequest.receiver());
            return ResponseEntity.ok(room);
    }
}
