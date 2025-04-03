package kookmin.kuham.user.service;

import kookmin.kuham.portfolio.schema.Portfolio;
import kookmin.kuham.portfolio.service.PortfolioService;
import kookmin.kuham.user.dto.request.GoogleUserInfo;
import kookmin.kuham.user.dto.request.RegisterInfoRequest;
import kookmin.kuham.user.dto.response.RegisterSuccessResponse;
import kookmin.kuham.user.dto.response.UserRegisterResponse;
import kookmin.kuham.user.exception.LoginFailedException;
import kookmin.kuham.user.exception.UserNotExistException;
import kookmin.kuham.user.repository.UserRepository;
import kookmin.kuham.user.schema.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PortfolioService portfolioService;
    private final WebClient webClient = WebClient.create();

    @Value("${google.client.id}")
    private String googleClientId;
    @Value("${google.client.secret}")
    private String googleClientSecret;
    @Value("${google.client.redirect-uri}")
    private String googleRedirectUri;
    
    public RegisterSuccessResponse register(RegisterInfoRequest registerInfoRequest) {
        //새로운 유저 등록
        String uuid = UUID.randomUUID().toString();

        Portfolio newPortfolio = portfolioService.addPortfolio(registerInfoRequest);

        User newUser = User.builder()
                .id(uuid)
                .name(registerInfoRequest.name())
                .email(registerInfoRequest.email())
                .profileUrl(registerInfoRequest.profileUrl())
                .studentNumber(registerInfoRequest.studentNum())
                .grade(registerInfoRequest.grade())
                .major(registerInfoRequest.major())
                .portfolio(newPortfolio)
                .build();
        userRepository.save(newUser);

        //TODO: 회원 가입 성공시 jwt생성
        return RegisterSuccessResponse.builder()
                .uid(newUser.getId())
                .email(newUser.getEmail())
                .name(newUser.getName())
                .profileUrl(newUser.getProfileUrl())
                .build();

    }

    public String requestAccessToken(String code){
        return webClient.post()
                .uri("https://oauth2.googleapis.com/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("code", code)
                        .with("client_id", googleClientId)
                        .with("client_secret", googleClientSecret)
                        .with("redirect_uri", googleRedirectUri)
                        .with("grant_type", "authorization_code"))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.get("access_token"))
                .block();
    }

    public UserRegisterResponse getUserInfo(String accessToken){
        GoogleUserInfo googleUserInfo =  webClient.get()  // ✅ 여기서 재사용
                .uri("https://www.googleapis.com/oauth2/v2/userinfo")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(GoogleUserInfo.class)
                .block();

        if (userRepository.existsByEmail(Objects.requireNonNull(googleUserInfo).email())){
            User user = userRepository.findByEmail(googleUserInfo.email());
            //TODO: 유저가 이미 존재하는 경우도 바로 로그인 성공, JWT생성
            return UserRegisterResponse.builder()
                    .newUser(false)
                    .uid(user.getId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .profileUrl(user.getProfileUrl())
                    .build();
        }else {
            return UserRegisterResponse.builder()
                    .newUser(true)
                    .uid(null)
                    .email(googleUserInfo.email())
                    .name(googleUserInfo.name())
                    .profileUrl(googleUserInfo.picture())
                    .build();
        }
    }

    public void updateUserInfo(RegisterInfoRequest registerInfoRequest) {
        User user = userRepository.findByEmail(registerInfoRequest.email());
        if (user == null) {
            throw new UserNotExistException();
        }
        user.setName(registerInfoRequest.name());
        user.setProfileUrl(registerInfoRequest.profileUrl());
        user.setStudentNumber(registerInfoRequest.studentNum());
        user.setGrade(registerInfoRequest.grade());
        user.setMajor(registerInfoRequest.major());

        userRepository.save(user);
    }


}
