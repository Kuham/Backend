package kookmin.kuham.chat.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatListResponse(
        String roomId,
        String otherUserId,
        String otherUserName,
        String otherUserMajor,
        String lastMessage,
        LocalDateTime lastMessageTime
) {
}
