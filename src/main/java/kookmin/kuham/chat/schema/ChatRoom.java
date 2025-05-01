package kookmin.kuham.chat.schema;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "chatroom")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoom {

    @Id
    private String roomId;

    private List<ChatMessage> messages = new ArrayList<>();
}
