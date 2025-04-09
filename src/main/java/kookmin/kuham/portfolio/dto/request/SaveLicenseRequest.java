package kookmin.kuham.portfolio.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record SaveLicenseRequest(
        @NotNull
        @Schema(description = "자격증 명")
        String licenseName,

        @NotNull
        @Schema(description = "발급기관")
        String licenseOraganization,

        @NotNull
        @Schema(description = "취득 일")
        String licenseDate
) {
}
