package kookmin.kuham.exception;

import io.swagger.v3.oas.annotations.Hidden;
import kookmin.kuham.user.exception.LoginFailedException;
import kookmin.kuham.user.exception.UserAlreadyExistException;
import kookmin.kuham.user.exception.UserNotExistException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> userAlreadyExistException(UserAlreadyExistException e){
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<?> loginFailedException(LoginFailedException e){
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<?> userNotExistException(UserNotExistException e){
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}
