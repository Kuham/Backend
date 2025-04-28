package kookmin.kuham.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SavePostRequest(
        @NotNull
        @Schema(description = "공고 제목")
        String title,

        @NotNull
        @Schema(description = "공고 내용")
        String description,

        @NotNull
        @Schema(description = "공고 도메인")
        String domain,

        @NotNull
        @Schema(description = "공고 시작일")
        String startDate,

        @NotNull
        @Schema(description = "공고 종료일")
        String endDate,

        @NotNull
        @Schema(description = "공고 모집 인원")
        Integer maxMember,

        @Nullable
        @Schema(description = "필요 역할")
        List<String> roles,

        @Nullable
        @Schema(description = "선호하는 성격")
        List<String> preferredCharacters,

        @Nullable
        @Schema(description = "기술 스택")
        List<String> stacks

) {
}
