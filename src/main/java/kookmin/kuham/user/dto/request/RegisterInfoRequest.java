package kookmin.kuham.user.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

import java.util.List;


public record RegisterInfoRequest(

        @NotBlank(message = "이름을 입력해주세요.")
        @Schema(name = "name",description = "사용자 이름")
        String name,

        @NotBlank(message = "이메일을 입력해주세요.")
        @Schema(name = "email",description = "사용자 이메일")
        String email,

        @NotBlank(message = "학번을 입력해주세요")
        @Schema(name = "studentNum",description = "사용자 학번")
        String studentNum,

        @NotBlank(message = "학년을 입력해주세요.")
        @Schema(name = "grade",description = "사용자 학년")
        String grade,

        @NotBlank(message = "전공을 입력해주세요.")
        @Schema(name = "major",description = "사용자 전공")
        String major,

        @Nullable
        @Schema(name = "profileUrl",description = "프로필 url")
        String profileUrl,

        @Nullable
        @Schema(name = "stacks",description = "기술 스택 (리스트 형식)")
        List<String> stacks,

        @Nullable
        @Schema(name = "links",description = "링크 (리스트 형식)")
        List<String> links,

        @Nullable
        @Schema(name = "introduce",description = "자기소개")
        String introduce

) {
}
