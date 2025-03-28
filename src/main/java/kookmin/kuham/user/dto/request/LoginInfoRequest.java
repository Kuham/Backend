package kookmin.kuham.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginInfoRequest(
        @NotBlank(message = "이메일을 입력해주세요")
        @Schema(name = "이메일", description = "사용자 아이디(이메일)")
        String email,

        @NotBlank(message = "비밀번호를 입력해주세요")
        @Schema(name = "비밀번호", description = "사용자 비밀번호")
        String password
) {
}
