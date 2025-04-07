package kookmin.kuham.portfolio.service;

import kookmin.kuham.portfolio.dto.request.EditPortfolioRequest;
import kookmin.kuham.portfolio.schema.Portfolio;
import kookmin.kuham.user.dto.request.RegisterInfoRequest;
import kookmin.kuham.user.exception.UserNotExistException;
import kookmin.kuham.user.repository.UserRepository;
import kookmin.kuham.user.schema.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final UserRepository userRepository;

    public Portfolio addPortfolio(RegisterInfoRequest registerInfoRequest){
        //입력 받은 정보를 토대로 포트폴리오 객체 생성
        return Portfolio.builder()
                .stacks(registerInfoRequest.stacks())
                .links(registerInfoRequest.links())
                .characters(registerInfoRequest.characters())
                .introduce(registerInfoRequest.introduce())
                .build();


    }

    public void editPortfolio( EditPortfolioRequest editPortfolioRequest) {
        //TODO: authentica  tion에서 userId를 가져오도록 수정
        String userId = "993e64e7-40b0-4c9d-afc0-5d34ced2a210";
        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        Portfolio portfolio = user.getPortfolio();

        portfolio.setStacks(editPortfolioRequest.stacks());
        portfolio.setLinks(editPortfolioRequest.links());
        portfolio.setIntroduce(editPortfolioRequest.introduce());
        portfolio.setCharacters(editPortfolioRequest.characters());

        user.setPortfolio(portfolio);
        userRepository.save(user);
    }
}
