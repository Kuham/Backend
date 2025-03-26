package kookmin.kuham.user.controller;

import jakarta.validation.Valid;
import kookmin.kuham.user.dto.request.RegisterInfoRequest;
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
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterInfoRequest registerInfoRequest) {
        boolean registerResult = userService.register(registerInfoRequest);
        if (!registerResult) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
