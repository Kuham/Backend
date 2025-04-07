package kookmin.kuham.portfolio.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kookmin.kuham.portfolio.dto.request.EditPortfolioRequest;
import kookmin.kuham.portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
