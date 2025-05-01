package kookmin.kuham.chat.dto;


public record ChatRequest(
        String sender,
        String receiver
) {
}