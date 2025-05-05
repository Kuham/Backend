package kookmin.kuham.chat.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import kookmin.kuham.chat.schema.ChatMessage;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ChatMessageRequest {
    @Schema(description = "채팅방 ID")
    @NotNull
    private String roomId;
    @Schema(description = "메시지 내용")
    private String message;


    public ChatMessage toEntity(){
        return ChatMessage.builder().messageId(UUID.randomUUID().toString())
                .roomId(roomId)
                .message(message)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
