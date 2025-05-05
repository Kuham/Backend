package kookmin.kuham.chat.exception;

import kookmin.kuham.exception.ApiException;
import org.springframework.http.HttpStatus;

public class UserNotContainException extends ApiException {
    public UserNotContainException(){
        super(HttpStatus.BAD_REQUEST,"해당 채팅방에 사용자가 포함되어 있지 않습니다.");
    }
}
