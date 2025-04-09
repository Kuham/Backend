package kookmin.kuham.portfolio.service;

import kookmin.kuham.portfolio.dto.request.EditPortfolioRequest;
import kookmin.kuham.portfolio.dto.request.SaveProjectRequest;
import kookmin.kuham.portfolio.exception.PortfolioNotExistException;
import kookmin.kuham.portfolio.repository.PortfolioRepository;
import kookmin.kuham.portfolio.schema.Portfolio;
import kookmin.kuham.portfolio.schema.Project;
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
    private final PortfolioRepository portfolioRepository;

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
        //TODO: authentication에서 userId를 가져오도록 수정
        String userId = "993e64e7-40b0-4c9d-afc0-5d34ced2a210";
        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        Portfolio portfolio = user.getPortfolio();
        if (portfolio == null){
            throw new PortfolioNotExistException();
        }

        portfolio.setStacks(editPortfolioRequest.stacks());
        portfolio.setLinks(editPortfolioRequest.links());
        portfolio.setIntroduce(editPortfolioRequest.introduce());
        portfolio.setCharacters(editPortfolioRequest.characters());


        user.setPortfolio(portfolio);
        userRepository.save(user);
    }

    public void addProject(SaveProjectRequest SaveProjectRequest){
        //TODO: authentication에서 userId를 가져오도록 수정
        String userId = "993e64e7-40b0-4c9d-afc0-5d34ced2a210";
        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        Portfolio portfolio = user.getPortfolio();
        if (portfolio == null){
            throw new PortfolioNotExistException();
        }

        portfolio.getProjects().add(Project.builder()
                        .title(SaveProjectRequest.projectName())
                        .stacks(SaveProjectRequest.stacks())
                        .description(SaveProjectRequest.description())
                        .startDate(SaveProjectRequest.startDate())
                        .endDate(SaveProjectRequest.endDate())
                        .inProgress(SaveProjectRequest.inProgress())
                        .portfolio(portfolio)
                .build());
        portfolioRepository.save(portfolio);


    }

}
