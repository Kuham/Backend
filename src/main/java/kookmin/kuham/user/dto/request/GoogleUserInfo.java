package kookmin.kuham.user.dto.request;

public record GoogleUserInfo(
        String id,
        String email,
        String name,
        String picture
) {
}
