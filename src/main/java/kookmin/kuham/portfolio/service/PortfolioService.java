package kookmin.kuham.portfolio.service;

import kookmin.kuham.portfolio.dto.request.AddPortfolioRequest;
import kookmin.kuham.portfolio.schema.Portfolio;
import kookmin.kuham.user.dto.request.RegisterInfoRequest;
import kookmin.kuham.user.exception.UserNotExistException;
import kookmin.kuham.user.repository.UserRepository;
import kookmin.kuham.user.schema.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final UserRepository userRepository;

    public Portfolio addPortfolio(RegisterInfoRequest registerInfoRequest){
        //입력 받은 정보를 토대로 포트폴리오 객체 생성
        return Portfolio.builder()
                .email(registerInfoRequest.email())
                .name(registerInfoRequest.name())
                .profileUrl(registerInfoRequest.profileUrl())
                .stacks(registerInfoRequest.stacks())
                .links(registerInfoRequest.links())
                .introduce(registerInfoRequest.introduce())
                .build();


    }
}
