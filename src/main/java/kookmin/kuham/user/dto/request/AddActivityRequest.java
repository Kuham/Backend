package kookmin.kuham.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record AddActivityRequest(
        @NotNull
        @Schema(description = "활동 명")
        String title,

        @NotNull
        @Schema(description = "설명")
        String description
) {
}
