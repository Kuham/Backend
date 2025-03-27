package kookmin.kuham.user.exception;

import kookmin.kuham.exception.ApiException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends ApiException {
    public UserAlreadyExistException(){

        super(HttpStatus.BAD_REQUEST,"유저가 이미 존재합니다");
    }
}
