package kookmin.kuham.user.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;


public record RegisterInfoRequest(

        @NotBlank(message = "이름을 입력해주세요.")
        @Schema(name = "name",description = "사용자 이름")
        String name,

        @NotBlank(message = "이메일을 입력해주세요.")
        @Schema(name = "email",description = "사용자 이메일")
        String email,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Schema(name = "password",description = "사용자 비밀번호")
        String password
) {
}
