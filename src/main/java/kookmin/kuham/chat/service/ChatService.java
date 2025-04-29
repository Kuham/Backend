package kookmin.kuham.chat.service;

import kookmin.kuham.chat.schema.ChatRoom;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatService {

    private final Map<String, ChatRoom> roomStorage = new ConcurrentHashMap<>();

    public ChatRoom createOrFindRoom(String userA, String userB) {
        String roomId = generateRoomId(userA, userB);
        return roomStorage.computeIfAbsent(roomId, id -> new ChatRoom(id, List.of(userA, userB)));
    }

    private String generateRoomId(String a, String b) {
        List<String> users = List.of(a, b);
        Collections.sort(users); // 항상 같은 순서
        return "room-" + users.get(0) + "-" + users.get(1);
    }
}
