package kookmin.kuham.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import kookmin.kuham.user.dto.request.EditUserRequest;
import kookmin.kuham.user.dto.request.RegisterInfoRequest;
import kookmin.kuham.user.dto.response.RegisterSuccessResponse;
import kookmin.kuham.user.dto.response.UserRegisterResponse;
import kookmin.kuham.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Value("${google.client.id}")
    private String googleClientId;
    @Value("${google.client.redirect-uri}")
    private String googleRedirectUri;

    @Operation(summary = "회원가입",description = "회원가입을 진행합니다")
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<RegisterSuccessResponse> register(@RequestBody RegisterInfoRequest registerInfoRequest) {
        return ResponseEntity.ok(userService.register(registerInfoRequest));

    }


    @GetMapping("/google/login")
    public String googleLogin() {
        String reqUrl = "https://accounts.google.com/o/oauth2/v2/auth?client_id=" + googleClientId
                + "&redirect_uri="+googleRedirectUri+"&response_type=code&scope=email%20profile%20openid&access_type=offline";

        return "redirect:" + reqUrl;
    }

    @Operation(summary = "로그인",description = "로그인 api")
    @Parameter(name = "code", description = "구글 로그인 후 받은 코드")
    @ResponseBody
    @GetMapping("/login")
    public UserRegisterResponse login(@RequestParam("code") String code){
        String accessToken = userService.requestAccessToken(code);
         return userService.getUserInfo(accessToken);
    }

    @Operation(summary = "회원 정보 수정",description = "회원 정보 수정 api")
    @PutMapping("/edit")
    public ResponseEntity<String> updateUserInfo(@ModelAttribute EditUserRequest editUserRequest, @RequestPart("file")MultipartFile file, @AuthenticationPrincipal String userId) throws IOException {

        userService.updateUserInfo(editUserRequest,file,userId);

        return ResponseEntity.ok("회원 정보 수정 완료");
    }

    @Operation(summary = "회원 탈퇴",description = "회원 탈퇴 api")
    @DeleteMapping("withdraw")
    public ResponseEntity<String>withdrawUser(@AuthenticationPrincipal String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("회원 탈퇴 완료");
    }



}
