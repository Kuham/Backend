package kookmin.kuham.user.service;

import kookmin.kuham.portfolio.schema.Portfolio;
import kookmin.kuham.portfolio.service.PortfolioService;
import kookmin.kuham.user.dto.request.EditUserRequest;
import kookmin.kuham.user.dto.request.GoogleUserInfo;
import kookmin.kuham.user.dto.request.RegisterInfoRequest;
import kookmin.kuham.user.dto.response.RegisterSuccessResponse;
import kookmin.kuham.user.dto.response.UserRegisterResponse;
import kookmin.kuham.user.exception.UserNotExistException;
import kookmin.kuham.user.repository.UserRepository;
import kookmin.kuham.user.schema.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.IOException;
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

    public void updateUserInfo(EditUserRequest editUserRequest, MultipartFile file) throws IOException {
        //TODO: authentication에서 userId를 가져오도록 수정
        String userId = "49d2c0a5-b081-4eeb-8e71-cdba12a6b3cc";
        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        // 업로드할 경로 설정
        String rootPath = System.getProperty("user.dir"); // 예: C:/Users/moong/Backend
        String uploadDir = rootPath + File.separator + "uploads" + File.separator + "profile" + File.separator + userId;

        if(file != null && !file.isEmpty()){
            File dir = new File(uploadDir);
            // 기존 파일 삭제
            if (dir.exists()){
                File[] files = dir.listFiles();
                if (files != null) {
                    for (File f : files) {
                        f.delete();
                    }
                }
            }else dir.mkdirs();
            //이미지 업로드
            String fileName = file.getOriginalFilename();
            file.transferTo(new File(uploadDir , fileName));
            user.setProfileUrl("http://localhost:8080/profile/"+userId+"/"+fileName);
        }


        user.setName(editUserRequest.name());
        user.setStudentNumber(editUserRequest.studentNumber());
        user.setGrade(editUserRequest.grade());
        user.setMajor(editUserRequest.major());

        userRepository.save(user);
    }
}
