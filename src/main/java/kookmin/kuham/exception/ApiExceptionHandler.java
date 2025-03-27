package kookmin.kuham.exception;

import kookmin.kuham.user.exception.UserAlreadyExistException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> userAlreadyExistException(UserAlreadyExistException e){
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}
