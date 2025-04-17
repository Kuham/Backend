package kookmin.kuham.post.exception;

import kookmin.kuham.exception.ApiException;
import org.springframework.http.HttpStatus;

public class PostNotFoundException extends ApiException {
    public PostNotFoundException(){
        super(HttpStatus.BAD_REQUEST,"자신의 글만 수정할 수 있습니다");
    }
}
