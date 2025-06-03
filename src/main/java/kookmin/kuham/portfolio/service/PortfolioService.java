package kookmin.kuham.portfolio.service;

import kookmin.kuham.portfolio.dto.request.EditPortfolioRequest;
import kookmin.kuham.portfolio.dto.request.SaveActivityRequest;
import kookmin.kuham.portfolio.dto.request.SaveLicenseRequest;
import kookmin.kuham.portfolio.dto.request.SaveProjectRequest;
import kookmin.kuham.portfolio.dto.response.getPortfolioContentResponse;
import kookmin.kuham.portfolio.dto.response.userActivityResponse;
import kookmin.kuham.portfolio.dto.response.userLicenseResponse;
import kookmin.kuham.portfolio.dto.response.userProjectsResponse;
import kookmin.kuham.portfolio.exception.ActivityNotExsitException;
import kookmin.kuham.portfolio.exception.LicenseNotFoundException;
import kookmin.kuham.portfolio.exception.PortfolioNotExistException;
import kookmin.kuham.portfolio.exception.ProjectNotFoundException;
import kookmin.kuham.portfolio.repository.ActivityRepository;
import kookmin.kuham.portfolio.repository.PortfolioRepository;
import kookmin.kuham.portfolio.repository.ProjectRepository;
import kookmin.kuham.portfolio.schema.Activity;
import kookmin.kuham.portfolio.schema.License;
import kookmin.kuham.portfolio.schema.Portfolio;
import kookmin.kuham.portfolio.schema.Project;
import kookmin.kuham.user.dto.request.RegisterInfoRequest;
import kookmin.kuham.user.exception.UserNotExistException;
import kookmin.kuham.user.repository.UserRepository;
import kookmin.kuham.user.schema.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final ProjectRepository projectRepository;
    private final ActivityRepository activityRepository;

    public Portfolio addPortfolio(RegisterInfoRequest registerInfoRequest){
        //입력 받은 정보를 토대로 포트폴리오 객체 생성
        return Portfolio.builder()
                .stacks(registerInfoRequest.stacks())
                .links(registerInfoRequest.links())
                .characters(registerInfoRequest.characters())
                .introduce(registerInfoRequest.introduce())
                .build();


    }
    public void editPortfolio( EditPortfolioRequest editPortfolioRequest,String uid) {

        User user = userRepository.findById(uid).orElseThrow(UserNotExistException::new);
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

    public void addProject(SaveProjectRequest saveProjectRequest, MultipartFile[] images,String uid)throws IOException {

        User user = userRepository.findById(uid).orElseThrow(UserNotExistException::new);
        Portfolio portfolio = user.getPortfolio();
        if (portfolio == null){
            throw new PortfolioNotExistException();
        }
        Project newProject = Project.builder()
                .title(saveProjectRequest.projectName())
                .stacks(saveProjectRequest.stacks())
                .description(saveProjectRequest.description())
                .oneLineDescription(saveProjectRequest.oneLineDescription())
                .startDate(saveProjectRequest.startDate())
                .endDate(saveProjectRequest.endDate())
                .inProgress(saveProjectRequest.inProgress())
                .portfolio(portfolio)
                .roles(saveProjectRequest.roles())
                .build();

        portfolio.getProjects().add(newProject);
        projectRepository.save(newProject);

        newProject.setImages(uploadImage(uid, newProject.getId(), "project", images));
        projectRepository.save(newProject); // 다시 저장
    }

    public void addLicense(SaveLicenseRequest saveLicenseRequest,String userId){

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

    public void addActivity(SaveActivityRequest saveActivityRequest, MultipartFile[] images,String userId) throws IOException {

        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        Portfolio portfolio = user.getPortfolio();
        if (portfolio == null){
            throw new PortfolioNotExistException();
        }

        Activity newActivity = Activity.builder()
                .name(saveActivityRequest.title())
                .description(saveActivityRequest.description())
                .oneLineDescription(saveActivityRequest.oneLineDescription())
                .startDate(saveActivityRequest.startDate())
                .endDate(saveActivityRequest.endDate())
                .inProgress(saveActivityRequest.inProgress())
                .portfolio(portfolio)
                .roles(saveActivityRequest.roles())
                .build();
        portfolio.getActivities().add(newActivity);
        activityRepository.save(newActivity);

        newActivity.setImages(uploadImage(userId, newActivity.getId(), "activity", images));
        activityRepository.save(newActivity);


    }

    public void editProject(SaveProjectRequest saveProjectRequest,MultipartFile[] images,Long projectId,String userId) throws IOException{

        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        Portfolio portfolio = user.getPortfolio();
        if (portfolio == null){
            throw new PortfolioNotExistException();
        }

        Project project = portfolio.getProjects().stream()
                .filter(p -> Objects.equals(p.getId(),projectId))
                .findFirst()
                .orElseThrow(ProjectNotFoundException::new);

        deleteImage(userId,project.getId(),"project");

        project.setTitle(saveProjectRequest.projectName());
        project.setStacks(saveProjectRequest.stacks());
        project.setDescription(saveProjectRequest.description());
        project.setOneLineDescription(saveProjectRequest.oneLineDescription());
        project.setStartDate(saveProjectRequest.startDate());
        project.setEndDate(saveProjectRequest.endDate());
        project.setInProgress(saveProjectRequest.inProgress());
        project.setRoles(saveProjectRequest.roles());

        project.setImages(uploadImage(userId, project.getId(), "project", images));

        portfolioRepository.save(portfolio);
    }

    public void editLicense(SaveLicenseRequest saveLicenseRequest, Long licenseId,String userId){

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

    public void editActivity(SaveActivityRequest saveActivityRequest,MultipartFile[] images, Long activityId,String userId) throws IOException{

        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        Portfolio portfolio = user.getPortfolio();
        if (portfolio == null){
            throw new PortfolioNotExistException();
        }

        Activity activity = portfolio.getActivities().stream()
                .filter((a) -> Objects.equals(a.getId(), activityId))
                .findFirst()
                .orElseThrow(ActivityNotExsitException::new);

        deleteImage(userId,activityId,"activity");

        activity.setDescription(saveActivityRequest.description());
        activity.setOneLineDescription(saveActivityRequest.oneLineDescription());
        activity.setName(saveActivityRequest.title());
        activity.setStartDate(saveActivityRequest.startDate());
        activity.setEndDate(saveActivityRequest.endDate());
        activity.setInProgress(saveActivityRequest.inProgress());
        activity.setRoles(saveActivityRequest.roles());

        activity.setImages(uploadImage(userId,activityId,"activity",images));



    }

    public void deleteProject(Long projectId,String userId){

        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        Portfolio portfolio = user.getPortfolio();
        if (portfolio == null){
            throw new PortfolioNotExistException();
        }

        Project project = portfolio.getProjects().stream()
                .filter(p -> Objects.equals(p.getId(),projectId))
                .findFirst()
                .orElseThrow(ProjectNotFoundException::new);
        // 프로젝트 경로의 이미지 삭제
        deleteImage(userId,project.getId(),"project");

        portfolio.getProjects().remove(project);
        portfolioRepository.save(portfolio);
    }

    public void deleteLicense(Long licenseId,String userId){

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

    public void deleteActivity(Long activityId,String userId){

        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        Portfolio portfolio = user.getPortfolio();
        if (portfolio == null){
            throw new PortfolioNotExistException();
        }

        Activity activity = portfolio.getActivities().stream()
                .filter(p -> Objects.equals(p.getId(),activityId))
                .findFirst()
                .orElseThrow(ActivityNotExsitException::new);

        portfolio.getActivities().remove(activity);
        deleteImage(userId,activityId,"activity");
        portfolioRepository.save(portfolio);
    }

    public List<String> uploadImage(String userId, Long projectId, String path,MultipartFile[] images) throws IOException{
        if (images != null && images.length > 0) {
            String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" +
                    File.separator + path + File.separator +userId+ File.separator+ projectId;

            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            List<String> imageUrls = new ArrayList<>();

            for (int i = 0; i < images.length; i++) {
                MultipartFile file = images[i];
                // 파일이 비어있거나 null인 경우 건너뛰기
                if (file == null || file.isEmpty()) continue;

                String fileName = i + "_" + file.getOriginalFilename();
                file.transferTo(new File(uploadDir, fileName));
                imageUrls.add("http://localhost:8080/project/" + projectId + "/" + fileName);
            }

           return imageUrls;
        }
        //images 필드 자체가 없는 경우, 어짜피 파일 없으니 null 반환
        return null;
    }

    public void deleteImage(String userId, Long projectId,String path){
        String uploadDirPath = System.getProperty("user.dir") + File.separator +
                "uploads" + File.separator + path + File.separator +userId+ File.separator+ projectId;

        File uploadDir = new File(uploadDirPath);
        if (uploadDir.exists()) {
            File[] files = uploadDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete(); // 파일 개별 삭제
                }
            }
            uploadDir.delete(); // 이미지 폴더 자체도 삭제
        }
    }

    public getPortfolioContentResponse getPortfolioContent(String userId){

        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        Portfolio portfolio = user.getPortfolio();
        if (portfolio == null){
            throw new PortfolioNotExistException();
        }
        return new getPortfolioContentResponse(user, portfolio);

    }

    public userProjectsResponse getMyProjects(String userId){

        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);

        return new userProjectsResponse(user.getPortfolio().getProjects());

    }

    public userActivityResponse getMyActivities(String userId) {

        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);

        return new userActivityResponse(user.getPortfolio().getActivities());

    }

    public userLicenseResponse getMyLicenses(String userId) {

        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);

        return new userLicenseResponse(user.getPortfolio().getLicenses());

    }

}
