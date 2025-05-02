package kookmin.kuham.chat.dto.request;

public record ReadMessageRequest(
        String roomId,
        String messageId
) {
}
