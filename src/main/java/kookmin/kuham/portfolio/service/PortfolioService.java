package kookmin.kuham.portfolio.service;

import kookmin.kuham.portfolio.dto.request.AddPortfolioRequest;
import kookmin.kuham.portfolio.schema.Portfolio;
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

    @Transactional
    public void addPortfolio(AddPortfolioRequest addPortfolioRequest){
        //TODO: 추후 authentication에서 유저 정보 가져오기
        User user = userRepository.findById("af92f3f6-c8ee-4a4b-85c5-00fe5fddc859").orElseThrow(UserNotExistException::new);
        //입력 받은 정보를 토대로 포트폴리오 객체 생성
        Portfolio newPortfolio = Portfolio.builder()
                .email(user.getEmail())
                .name(user.getName())
                .profileUrl(addPortfolioRequest.profileUrl())
                .stacks(addPortfolioRequest.stacks())
                .interests(addPortfolioRequest.interests())
                .introduce(addPortfolioRequest.introduce())
                .build();
        // 사용자의 포트폴리오에 추가
        user.setPortfolio(newPortfolio);
        userRepository.save(user);

    }
}
