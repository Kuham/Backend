package kookmin.kuham.chat.repository;

import kookmin.kuham.chat.schema.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends MongoRepository<ChatRoom,String> {
    // MongoDB에서 메시지를 ID로 찾는 메서드
    ChatRoom findByRoomId(String roomId);
}
