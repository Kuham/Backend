package kookmin.kuham.chat.repository;

import kookmin.kuham.chat.schema.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends MongoRepository<ChatRoom,String> {
}
