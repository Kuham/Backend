package kookmin.kuham.portfolio.exception;

import kookmin.kuham.exception.ApiException;
import org.springframework.http.HttpStatus;

public class ActivityNotExsitException extends ApiException {
    public ActivityNotExsitException(){
        super(HttpStatus.NOT_FOUND, "자신의 활동만 수정 할 수 있습니다.");
    }
}
