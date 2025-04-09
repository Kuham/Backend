package kookmin.kuham.portfolio.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SaveProjectRequest(
        @NotNull
        @Schema(description = "프로젝트 명")
        String projectName,
        
        @Schema( description = "스택 명")
        @Nullable
        List<String> stacks,

        @Schema(description = "프로젝트 설명")
        @Nullable
        String description,

        @Schema(description = "프로젝트 시작일")
        @NotNull
        String startDate,
        
        @Schema(description = "프로젝트 종료일")
        @Nullable
        String endDate,

        @Schema(description = "프로젝트 진행 중 여부")
        @NotNull
        boolean inProgress

) {
}
