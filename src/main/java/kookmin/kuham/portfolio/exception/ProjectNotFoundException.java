package kookmin.kuham.portfolio.exception;

import kookmin.kuham.exception.ApiException;
import org.springframework.http.HttpStatus;

public class ProjectNotFoundException extends ApiException {
    public ProjectNotFoundException() {
        super(HttpStatus.NOT_FOUND, "자신의 프로젝트만 수정 할 수 있습니다.");
    }
}
