package kookmin.kuham.chat.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatContentResponse(
        String message,
        String senderId,
        LocalDateTime createdAt,
        Boolean isReadByOtherUser

) {
}
