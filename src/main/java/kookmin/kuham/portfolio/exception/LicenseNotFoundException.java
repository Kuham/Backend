package kookmin.kuham.portfolio.exception;

import kookmin.kuham.exception.ApiException;
import org.springframework.http.HttpStatus;

public class LicenseNotFoundException extends ApiException {
    public LicenseNotFoundException(){
        super(HttpStatus.NOT_FOUND,"자신의 자격증만 수정 할 수 있습니다.");
    }
}
