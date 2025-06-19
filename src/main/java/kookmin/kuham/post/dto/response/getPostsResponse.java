package kookmin.kuham.post.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record getPostsResponse(
        Long id,
        String title,
        String description,
        String domain,
        LocalDateTime createdAt,
        List<String> postImageUrl,
        String userName,
        String userMajor,
        List<String> roles,
        List<String> preferredCharacters,
        Integer maxMember,
        String startDate,
        String endDate,
        String profileImage
) {
}
