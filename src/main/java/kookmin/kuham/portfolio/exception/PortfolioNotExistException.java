package kookmin.kuham.portfolio.exception;

import kookmin.kuham.exception.ApiException;
import org.springframework.http.HttpStatus;

public class PortfolioNotExistException extends ApiException {
    public PortfolioNotExistException(){
        super(HttpStatus.BAD_REQUEST, "포트폴리오가 존재하지 않습니다.");
    }
}
