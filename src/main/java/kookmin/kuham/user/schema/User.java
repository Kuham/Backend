package kookmin.kuham.user.schema;


import jakarta.persistence.*;
import kookmin.kuham.portfolio.schema.Portfolio;
import kookmin.kuham.post.schema.Post;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "유저")
public class User {
    @Id
    private String id;
    private String profileUrl;
    private String name;
    private String email;
    private String studentNumber;
    private String grade;
    private String major;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> chatRooms = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();
}
