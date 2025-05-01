package kookmin.kuham.chat.service;

import kookmin.kuham.chat.dto.response.ChatRoomReponse;
import kookmin.kuham.chat.repository.ChatRepository;
import kookmin.kuham.chat.schema.ChatRoom;
import kookmin.kuham.user.exception.UserNotExistException;
import kookmin.kuham.user.repository.UserRepository;
import kookmin.kuham.user.schema.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;


    public ChatRoomReponse createOrFindRoom(String userA, String userB) {
        String roomId = generateRoomId(userA, userB);
        // 만약 ChatRoom이 이미 있는 경우에는 저장소에서 찾기
        ChatRoom chatRoom = chatRepository.findById(roomId).orElseGet(() -> {
            // 없으면 새로운 ChatRoom 생성
            ChatRoom newRoom = ChatRoom.builder()
                    .roomId(roomId)
                    .messages(new ArrayList<>())
                    .build();
            // 채팅방을 생성한 후, 두 사용자의 참여 채팅방 목록에 추가
            ChatRoom saved = chatRepository.save(newRoom);
            addRoomToUser(userA,roomId);
            addRoomToUser(userB,roomId);
            return saved;
        });
        List<String> participants = List.of(userA,userB);
        return ChatRoomReponse.builder()
                .roomId(chatRoom.getRoomId())
                .participants(participants)
                .build();

    }

    private String generateRoomId(String senderUid, String receiverUid) {
        List<String> users = new ArrayList<>(List.of(senderUid, receiverUid));
        Collections.sort(users); // 항상 같은 순서
        String raw = users.get(0) + "_" + users.get(1);
        return DigestUtils.sha256Hex(raw).substring(0, 16);
    }

    //유저의 참여 채팅방 id 목록에 채팅방 id 추가
    private void addRoomToUser(String userId, String roomId){
        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        user.getChatRooms().add(roomId);
        userRepository.save(user);
    }
}
