package kookmin.kuham.chat.service;

import kookmin.kuham.chat.dto.response.ChatContentResponse;
import kookmin.kuham.chat.dto.response.ChatListResponse;
import kookmin.kuham.chat.dto.response.ChatRoomReponse;
import kookmin.kuham.chat.repository.ChatRepository;
import kookmin.kuham.chat.schema.ChatMessage;
import kookmin.kuham.chat.schema.ChatRoom;
import kookmin.kuham.user.exception.UserNotExistException;
import kookmin.kuham.user.repository.UserRepository;
import kookmin.kuham.user.schema.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
                    .userIds(List.of(userA,userB))
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

    public List<ChatListResponse> getChats(){
        //TODO: authentication에서 userId를 가져오도록 수정
        String userId = "993e64e7-40b0-4c9d-afc0-5d34ced2a210";
        User currentUser = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        // 채팅방 목록을 생성
        List<ChatListResponse> chatList = new ArrayList<>();
        //채팅방 id 목록
        List<String> chatRoomIds = currentUser.getChatRooms();
        chatRoomIds.stream().map(chatRepository::findByRoomId).forEach(
                chatRoom -> {
                    String otherUserid  = chatRoom.getUserIds().stream()
                            .filter(id -> !id.equals(userId))
                            .findFirst()
                            .orElseThrow(UserNotExistException::new);
                    // 채팅 상대 유저의 정보를 가져오기
                    User otherUser = userRepository.findById(otherUserid).orElseThrow(UserNotExistException::new);
                    // 채팅방의 마지막 메시지를 가져오기
                    ChatMessage lastMessage = chatRoom.getMessages().get(chatRoom.getMessages().size() - 1);
                    //마지막 메세지를 현재 유저가 안읽었으면 안읽으 메세지 있다고 판단
                    Boolean isRead = lastMessage.getReadBy().contains(userId);
                    ChatListResponse chat = ChatListResponse.builder()
                            .roomId(chatRoom.getRoomId())
                            .otherUserId(otherUserid)
                            .otherUserName(otherUser.getName())
                            .otherUserMajor(otherUser.getMajor())
                            .lastMessageTime(lastMessage.getCreatedAt())
                            .lastMessage(lastMessage.getMessage())
                            //안 읽은 메세지가 있는지
                            .isRead(isRead)
                            .build();
                    chatList.add(chat);
                }
        );
        chatList.sort(Comparator.comparing(ChatListResponse::lastMessageTime).reversed());
        return chatList;
    }

    public List<ChatContentResponse> getChatContents(ChatRoom room){
        //TODO: authentication에서 userId를 가져오도록 수정
        String userId = "993e64e7-40b0-4c9d-afc0-5d34ced2a210";
        List<ChatContentResponse> responseList = new ArrayList<>();

        for (ChatMessage message : room.getMessages()) {
            // 읽음 처리
            if (!message.getReadBy().contains(userId)) {
                message.getReadBy().add(userId);
            }

            // isReadByOtherUser 판단
            boolean isReadByOther =
                    message.getSender().equals(userId) && message.getReadBy().size() > 1;

            // 응답 객체 생성
            ChatContentResponse response = ChatContentResponse.builder()
                    .message(message.getMessage())
                    .senderId(message.getSender())
                    .createdAt(message.getCreatedAt())
                    .isReadByOtherUser(isReadByOther)
                    .build();

            responseList.add(response);
        }

        chatRepository.save(room);

        return responseList;

    }
}
