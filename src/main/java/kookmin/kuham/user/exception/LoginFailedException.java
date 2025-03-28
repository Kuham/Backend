package kookmin.kuham.user.exception;

import kookmin.kuham.exception.ApiException;
import org.springframework.http.HttpStatus;

public class LoginFailedException extends ApiException {
    public LoginFailedException(){
        super(HttpStatus.BAD_REQUEST,"아이디와 비밀번호가 일치하지 않습니다.");
    }
}
