package kookmin.kuham.portfolio.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SaveActivityRequest(

        @Schema(description = "활동 명")
        @NotNull
        String title,

        @Schema(description = "한 줄 소개")
        @Nullable
        String oneLineDescription,

        @Schema(description = "활동 설명")
        @Nullable
        String description,

        @Schema(description = "활동 시작일")
        @NotNull
        String startDate,

        @Schema(description = "활동 종료일")
        @Nullable
        String endDate,

        @Schema(description = "활동 진행 중 여부")
        @NotNull
        boolean inProgress,

        @Schema(description = "자기 역할")
        @Nullable
        List<String> roles
) {
}
