package kookmin.kuham.post.dto.response;

import java.time.LocalDateTime;

public record getPostsResponse(
        Long id,
        String title,
        String description,
        String domain,
        LocalDateTime createdAt,
        String userName,
        String userMajor
) {
}
