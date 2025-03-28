package kookmin.kuham.user.exception;

import kookmin.kuham.exception.ApiException;
import org.springframework.http.HttpStatus;

public class UserNotExistException extends ApiException {
    public UserNotExistException(){
        super(HttpStatus.BAD_REQUEST,"해당 이메일을 가진 유저가 없습니다");
    }
}
