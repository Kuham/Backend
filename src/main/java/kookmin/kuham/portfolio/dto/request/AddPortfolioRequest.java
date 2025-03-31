package kookmin.kuham.portfolio.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;

import java.util.List;

public record AddPortfolioRequest(
        @Nullable
        @Schema(name = "주요 스택",description = "스택 내용을 리스트 형식으로 전달")
        List<String> stacks,

        @Nullable
        @Schema(name="프로필 사진 주소")
        String profileUrl,

        @Nullable
        @Schema(name = "관심 분야",description = "관심 분야를 리스트 형식으로 전달")
        List<String> interests,
        @Nullable
        @Schema(name = "자기소개",description = "자기소개 설명")
        String introduce

) {
}
