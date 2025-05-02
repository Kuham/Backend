package kookmin.kuham.chat.dto.request;

import kookmin.kuham.chat.schema.ChatMessage;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class ChatMessageRequest {
    private String roomId;
    private String sender;
    private String message;
    private List<String> readBy = new ArrayList<>();

    public ChatMessage toEntitiy(){
        return ChatMessage.builder().messageId(UUID.randomUUID().toString()).roomId(roomId)
                .sender(sender)
                .message(message)
                .readBy(readBy)
                .build();
    }
}
