package kookmin.kuham.portfolio.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kookmin.kuham.portfolio.dto.request.EditPortfolioRequest;
import kookmin.kuham.portfolio.dto.request.SaveProjectRequest;
import kookmin.kuham.portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> addProject(@Valid @RequestBody SaveProjectRequest SaveProjectRequest) {
        portfolioService.addProject(SaveProjectRequest);
        return ResponseEntity.ok("프로젝트 추가 완료");
    }

        @Operation(summary = "프로젝트 수정")
        @PutMapping("/project/{projectId}/edit")
        public ResponseEntity<String> editProject(@Valid @RequestBody SaveProjectRequest SaveProjectRequest, @PathVariable("projectId")Long projectId) {
            portfolioService.editProject(SaveProjectRequest,projectId);
            return ResponseEntity.ok("프로젝트 수정 완료");
        }

}
