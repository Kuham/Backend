package kookmin.kuham.portfolio.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;

import java.util.List;

public record EditPortfolioRequest(
        @Nullable
        @Schema(name = "주요 스택",description = "스택 내용을 리스트 형식으로 전달")
        List<String> stacks,

        @Nullable
        @Schema(name = "관심 분야",description = "관심 분야를 리스트 형식으로 전달")
        List<String> links,

        @Nullable
        @Schema(name = "성격",description = "성격을 리스트 형식으로 전달")
        List<String> characters,

        @Nullable
        @Schema(name = "자기소개",description = "자기소개 설명")
        String introduce
) {
}
