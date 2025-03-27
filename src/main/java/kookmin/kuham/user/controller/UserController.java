package kookmin.kuham.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kookmin.kuham.user.dto.request.RegisterInfoRequest;
import kookmin.kuham.user.exception.UserAlreadyExistException;
import kookmin.kuham.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    @Operation(summary = "회원가입",description = "회원가입을 진행합니다")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterInfoRequest registerInfoRequest) {
        boolean registerResult = userService.register(registerInfoRequest);
        if (!registerResult) {
            throw new UserAlreadyExistException();
        }
        return ResponseEntity.ok().build();
    }

    private final UserService userService;

}
