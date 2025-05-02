package kookmin.kuham.chat.exception;

import kookmin.kuham.exception.ApiException;
import org.springframework.http.HttpStatus;

public class RoomNotExistException extends ApiException {
    public RoomNotExistException(){
        super(HttpStatus.NOT_FOUND,"채팅방이 존재하지 않습니다.");
    }
}
