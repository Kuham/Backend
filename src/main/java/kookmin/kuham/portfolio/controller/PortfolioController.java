package kookmin.kuham.portfolio.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kookmin.kuham.portfolio.dto.request.EditPortfolioRequest;
import kookmin.kuham.portfolio.dto.request.SaveLicenseRequest;
import kookmin.kuham.portfolio.dto.request.SaveProjectRequest;
import kookmin.kuham.portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> updatePortfolio(@Valid @RequestBody EditPortfolioRequest editPortfolioRequest) {
        portfolioService.editPortfolio(editPortfolioRequest);
        return ResponseEntity.ok("포트폴리오 수정 완료");
    }

    @Operation(summary = "프로젝트 추가")
    @PostMapping("/project/add")
    public ResponseEntity<String> addProject(@Valid @RequestPart("project") SaveProjectRequest SaveProjectRequest,
                                             @RequestPart("images") MultipartFile[] images) throws IOException {
        portfolioService.addProject(SaveProjectRequest, images);
        return ResponseEntity.ok("프로젝트 추가 완료");
    }

    @Operation(summary = "자격증 추가")
    @PostMapping("/license/add")
    public ResponseEntity<?> addLicense(@Valid @RequestBody SaveLicenseRequest saveLicenseRequest){
        portfolioService.addLicense(saveLicenseRequest);
        return ResponseEntity.ok("자격증 추가 완료");
    }

    @Operation(summary = "프로젝트 수정")
    @PutMapping("/project/{projectId}/edit")
    public ResponseEntity<String> editProject(@Valid @RequestPart("project") SaveProjectRequest SaveProjectRequest,
                                              @RequestPart("images") MultipartFile[] images,
                                              @PathVariable("projectId")Long projectId) throws IOException {
            portfolioService.editProject(SaveProjectRequest,images,projectId);
            return ResponseEntity.ok("프로젝트 수정 완료");
    }

    @Operation(summary = "자격증 수정")
    @PutMapping("/license/{licenseId}/edit")
    public ResponseEntity<String> editLicense(@Valid @RequestBody SaveLicenseRequest saveLicenseRequest, @PathVariable("licenseId")Long licenseId) {
        portfolioService.editLicense(saveLicenseRequest,licenseId);
        return ResponseEntity.ok("자격증 수정 완료");
    }

    @Operation
    @DeleteMapping("/project/{projectId}/delete")
    public ResponseEntity<String> deleteProject(@PathVariable("projectId") Long projectId) {
        portfolioService.deleteProject(projectId);
        return ResponseEntity.ok("프로젝트 삭제 완료");
    }

    @Operation
    @DeleteMapping("/license/{licenseId}/delete")
    public ResponseEntity<String> deleteLicense(@PathVariable("licenseId") Long licenseId) {
        portfolioService.deleteLicense(licenseId);
        return ResponseEntity.ok("프로젝트 삭제 완료");
    }

}
