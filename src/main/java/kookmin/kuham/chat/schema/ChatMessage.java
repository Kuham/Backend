package kookmin.kuham.chat.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private String roomId;
    private String sender;
    private String message;
    private List<String> readBy = new ArrayList<>();
}
