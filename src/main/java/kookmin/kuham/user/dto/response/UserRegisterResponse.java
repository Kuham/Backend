package kookmin.kuham.user.dto.response;

import lombok.Builder;

@Builder
public record UserRegisterResponse(
        boolean newUser,
        String uid,
        String name,
        String email,
        String profileUrl,
        String token
) {
}
