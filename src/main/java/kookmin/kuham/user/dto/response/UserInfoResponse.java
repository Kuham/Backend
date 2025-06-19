package kookmin.kuham.user.dto.response;

import lombok.Builder;

@Builder
public record UserInfoResponse(
        String name,
        String oneLineIntroduction,
        String profileUrl,
        String grade,
        String major

) {
}
