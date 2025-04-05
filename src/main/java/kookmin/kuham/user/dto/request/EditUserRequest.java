package kookmin.kuham.user.dto.request;

public record EditUserRequest(
        String name,
        String studentNumber,
        String grade,
        String major
) {
}
