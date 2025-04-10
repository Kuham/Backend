package kookmin.kuham.portfolio.service;

import kookmin.kuham.portfolio.dto.request.EditPortfolioRequest;
import kookmin.kuham.portfolio.dto.request.SaveLicenseRequest;
import kookmin.kuham.portfolio.dto.request.SaveProjectRequest;
import kookmin.kuham.portfolio.exception.LicenseNotFoundException;
import kookmin.kuham.portfolio.exception.PortfolioNotExistException;
import kookmin.kuham.portfolio.exception.ProjectNotFoundException;
import kookmin.kuham.portfolio.repository.PortfolioRepository;
import kookmin.kuham.portfolio.schema.License;
import kookmin.kuham.portfolio.schema.Portfolio;
import kookmin.kuham.portfolio.schema.Project;
import kookmin.kuham.user.dto.request.RegisterInfoRequest;
import kookmin.kuham.user.exception.UserNotExistException;
import kookmin.kuham.user.repository.UserRepository;
import kookmin.kuham.user.schema.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

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

    public void addProject(SaveProjectRequest saveProjectRequest){
        //TODO: authentication에서 userId를 가져오도록 수정
        String userId = "993e64e7-40b0-4c9d-afc0-5d34ced2a210";
        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        Portfolio portfolio = user.getPortfolio();
        if (portfolio == null){
            throw new PortfolioNotExistException();
        }

        portfolio.getProjects().add(Project.builder()
                        .title(saveProjectRequest.projectName())
                        .stacks(saveProjectRequest.stacks())
                        .description(saveProjectRequest.description())
                        .oneLineDescription(saveProjectRequest.oneLineDescription())
                        .startDate(saveProjectRequest.startDate())
                        .endDate(saveProjectRequest.endDate())
                        .inProgress(saveProjectRequest.inProgress())
                        .portfolio(portfolio)
                .build());
        portfolioRepository.save(portfolio);
    }

    public void addLicense(SaveLicenseRequest saveLicenseRequest){
        //TODO: authentication에서 userId를 가져오도록 수정
        String userId = "993e64e7-40b0-4c9d-afc0-5d34ced2a210";
        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        Portfolio portfolio = user.getPortfolio();
        if (portfolio == null){
            throw new PortfolioNotExistException();
        }
        portfolio.getLicenses().add(License.builder()
                        .licenseName(saveLicenseRequest.licenseName())
                        .licenseOrganization(saveLicenseRequest.licenseOraganization())
                        .licenseDate(saveLicenseRequest.licenseDate())
                        .portfolio(portfolio)
                .build());
        portfolioRepository.save(portfolio);

    }

    public void editProject(SaveProjectRequest saveProjectRequest,Long projectId){
        //TODO: authentication에서 userId를 가져오도록 수정
        String userId = "993e64e7-40b0-4c9d-afc0-5d34ced2a210";
        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        Portfolio portfolio = user.getPortfolio();
        if (portfolio == null){
            throw new PortfolioNotExistException();
        }

        Project project = portfolio.getProjects().stream()
                .filter(p -> Objects.equals(p.getId(),projectId))
                .findFirst()
                .orElseThrow(ProjectNotFoundException::new);

        project.setTitle(saveProjectRequest.projectName());
        project.setStacks(saveProjectRequest.stacks());
        project.setDescription(saveProjectRequest.description());
        project.setOneLineDescription(saveProjectRequest.oneLineDescription());
        project.setStartDate(saveProjectRequest.startDate());
        project.setEndDate(saveProjectRequest.endDate());
        project.setInProgress(saveProjectRequest.inProgress());

        portfolioRepository.save(portfolio);
    }

    public void editLicense(SaveLicenseRequest saveLicenseRequest, Long licenseId){
        //TODO: authentication에서 userId를 가져오도록 수정
        String userId = "993e64e7-40b0-4c9d-afc0-5d34ced2a210";
        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        Portfolio portfolio = user.getPortfolio();
        if (portfolio == null){
            throw new PortfolioNotExistException();
        }

        License license = portfolio.getLicenses().stream()
                .filter((l) -> Objects.equals(l.getId(), licenseId))
                .findFirst()
                .orElseThrow(LicenseNotFoundException::new);

        license.setLicenseName(saveLicenseRequest.licenseName());
        license.setLicenseOrganization(saveLicenseRequest.licenseOraganization());
        license.setLicenseDate(saveLicenseRequest.licenseDate());

        portfolioRepository.save(portfolio);
    }

    public void deleteProject(Long projectId){
        //TODO: authentication에서 userId를 가져오도록 수정
        String userId = "993e64e7-40b0-4c9d-afc0-5d34ced2a210";
        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        Portfolio portfolio = user.getPortfolio();
        if (portfolio == null){
            throw new PortfolioNotExistException();
        }

        Project project = portfolio.getProjects().stream()
                .filter(p -> Objects.equals(p.getId(),projectId))
                .findFirst()
                .orElseThrow(ProjectNotFoundException::new);

        portfolio.getProjects().remove(project);
        portfolioRepository.save(portfolio);
    }

    public void deleteLicense(Long licenseId){
        //TODO: authentication에서 userId를 가져오도록 수정
        String userId = "993e64e7-40b0-4c9d-afc0-5d34ced2a210";
        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        Portfolio portfolio = user.getPortfolio();
        if (portfolio == null){
            throw new PortfolioNotExistException();
        }

        License license = portfolio.getLicenses().stream()
                .filter(p -> Objects.equals(p.getId(),licenseId))
                .findFirst()
                .orElseThrow(LicenseNotFoundException::new);

        portfolio.getLicenses().remove(license);
        portfolioRepository.save(portfolio);
    }

}
