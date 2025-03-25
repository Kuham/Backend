package kookmin.kuham.user.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record RegisterInfoRequest(

        @NotBlank(message = "이름을 입력해주세요.")
        String name,
        @NotBlank(message = "이메일을 입력해주세요.")
        String email,
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password
) {
}
