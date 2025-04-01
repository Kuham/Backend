package kookmin.kuham.user.dto.response;

import lombok.Builder;

@Builder
public record RegisterSuccessResponse(
        String uid,
        String name,
        String email,
        String profileUrl
) {
}
