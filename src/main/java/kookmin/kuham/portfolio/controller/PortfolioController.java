package kookmin.kuham.portfolio.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kookmin.kuham.portfolio.dto.request.EditPortfolioRequest;
import kookmin.kuham.portfolio.dto.request.SaveActivityRequest;
import kookmin.kuham.portfolio.dto.request.SaveLicenseRequest;
import kookmin.kuham.portfolio.dto.request.SaveProjectRequest;
import kookmin.kuham.portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @Operation(summary = "포트폴리오 수정")
    @PutMapping("/edit")
    public ResponseEntity<String> updatePortfolio(@Valid @RequestBody EditPortfolioRequest editPortfolioRequest, @AuthenticationPrincipal String uid) {
        portfolioService.editPortfolio(editPortfolioRequest,uid);
        return ResponseEntity.ok("포트폴리오 수정 완료");
    }

    @Operation(summary = "프로젝트 추가")
    @PostMapping("/project/add")
    public ResponseEntity<String> addProject(@Valid @RequestPart("project") SaveProjectRequest SaveProjectRequest,
                                             @RequestPart("images") MultipartFile[] images,
                                             @AuthenticationPrincipal String uid) throws IOException {
        portfolioService.addProject(SaveProjectRequest, images,uid);
        return ResponseEntity.ok("프로젝트 추가 완료");
    }

    @Operation(summary = "자격증 추가")
    @PostMapping("/license/add")
    public ResponseEntity<?> addLicense(@Valid @RequestBody SaveLicenseRequest saveLicenseRequest,@AuthenticationPrincipal String userId){
        portfolioService.addLicense(saveLicenseRequest,userId);
        return ResponseEntity.ok("자격증 추가 완료");
    }

    @Operation(summary = "활동 추가")
    @PostMapping("/activity/add")
    public ResponseEntity<String> addActivity(@Valid @RequestPart("activity")SaveActivityRequest saveActivityRequest,
                                              @RequestPart("images") MultipartFile[] images,
                                              @AuthenticationPrincipal String userId
    ) throws IOException {
        portfolioService.addActivity(saveActivityRequest,images,userId);
        return ResponseEntity.ok("활동 추가 완료");
    }

    @Operation(summary = "프로젝트 수정")
    @PutMapping("/project/{projectId}/edit")
    public ResponseEntity<String> editProject(@Valid @RequestPart("project") SaveProjectRequest SaveProjectRequest,
                                              @RequestPart("images") MultipartFile[] images,
                                              @PathVariable("projectId")Long projectId,
                                              @AuthenticationPrincipal String userId
    ) throws IOException {
            portfolioService.editProject(SaveProjectRequest,images,projectId,userId);
            return ResponseEntity.ok("프로젝트 수정 완료");
    }

    @Operation(summary = "자격증 수정")
    @PutMapping("/license/{licenseId}/edit")
    public ResponseEntity<String> editLicense(@Valid @RequestBody SaveLicenseRequest saveLicenseRequest,
                                              @PathVariable("licenseId")Long licenseId,
                                              @AuthenticationPrincipal String userId
    ) {
        portfolioService.editLicense(saveLicenseRequest,licenseId,userId);
        return ResponseEntity.ok("자격증 수정 완료");
    }

    @Operation(summary = "활동 수정")
    @PutMapping("/activity/{activityId}/edit")
    public ResponseEntity<String> editActivity(@Valid @RequestPart("activity") SaveActivityRequest saveActivityRequest,
                                                @RequestPart("images") MultipartFile[] images,
                                                @PathVariable("activityId")Long activityId,
                                               @AuthenticationPrincipal String userId) throws IOException {
        portfolioService.editActivity(saveActivityRequest,images,activityId,userId);
        return ResponseEntity.ok("활동 수정 완료");
    }

    @Operation(summary = "프로젝트 삭제")
    @DeleteMapping("/project/{projectId}/delete")
    public ResponseEntity<String> deleteProject(@PathVariable("projectId") Long projectId,
                                                @AuthenticationPrincipal String userId) {
        portfolioService.deleteProject(projectId,userId);
        return ResponseEntity.ok("프로젝트 삭제 완료");
    }

    @Operation(summary = "자격증 삭제")
    @DeleteMapping("/license/{licenseId}/delete")
    public ResponseEntity<String> deleteLicense(@PathVariable("licenseId") Long licenseId,
                                                @AuthenticationPrincipal String userId) {
        portfolioService.deleteLicense(licenseId,userId);
        return ResponseEntity.ok("프로젝트 삭제 완료");
    }

    @Operation(summary = "활동 삭제")
    @DeleteMapping("/activity/{activityId}/delete")
    public ResponseEntity<String> deleteActivity(@PathVariable("activityId") Long activityId,
                                                 @AuthenticationPrincipal String userId) {
        portfolioService.deleteActivity(activityId,userId);
        return ResponseEntity.ok("활동 삭제 완료");
    }

}
