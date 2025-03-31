package kookmin.kuham.portfolio.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kookmin.kuham.portfolio.dto.request.AddPortfolioRequest;
import kookmin.kuham.portfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @PostMapping("/add")
    @Operation(summary = "포트폴리오 등록",description = "포트폴리오 등록 api")
    public ResponseEntity<?> add(@RequestBody @Valid AddPortfolioRequest addPortfolioRequest){
        portfolioService.addPortfolio(addPortfolioRequest);
        return ResponseEntity.ok().build();
    }
}
