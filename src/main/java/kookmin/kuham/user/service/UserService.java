package kookmin.kuham.user.service;

import kookmin.kuham.user.dto.request.RegisterInfoRequest;
import kookmin.kuham.user.exception.LoginFailedException;
import kookmin.kuham.user.exception.UserNotExistException;
import kookmin.kuham.user.repository.UserRepository;
import kookmin.kuham.user.schema.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public boolean register(RegisterInfoRequest registerInfoRequest) {

        //이미 유저가 존재하는 경우
        if (userRepository.existsByEmail(registerInfoRequest.email())) {
            return false;
        }

        String uuid = UUID.randomUUID().toString();

        //초기 회원 등록, 포트폴리오는 null로 초기화
        User newUser = User.builder()
                .id(uuid)
                .password(passwordEncoder.encode(registerInfoRequest.password()))
                .name(registerInfoRequest.name())
                .email(registerInfoRequest.email())
                .portfolio(null)
                .build();
        userRepository.save(newUser);
        return true;
    }

    public void login(String email,String password){
        if (!userRepository.existsByEmail(email)){
            throw new UserNotExistException();
        }
        User user = userRepository.findByEmail(email);
        if (passwordEncoder.matches(password,user.getPassword())){
            //TODO: 로그인 후 인증 인가 추가
            return;
        }else {
            throw new LoginFailedException();
        }

    }
}
