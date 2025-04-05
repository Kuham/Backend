package kookmin.kuham.user.exception;

import kookmin.kuham.exception.ApiException;
import org.springframework.http.HttpStatus;

public class UserNotExistException extends ApiException {
    public UserNotExistException(){
        super(HttpStatus.BAD_REQUEST,"유저 데이터가 없습니다.");
    }
}
