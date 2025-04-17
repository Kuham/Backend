package kookmin.kuham.post.schema;

import jakarta.persistence.*;
import kookmin.kuham.user.schema.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String title;
    private String description;
    private String domain;
    private LocalDateTime createdAt;
    private String startDate;
    private String endDate;
    private Integer maxMember;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ElementCollection(fetch = FetchType.LAZY)
    List<String> roles;

    @ElementCollection
    List<String> preferredCharacters;

    @ElementCollection
    List<String> images;

    @ElementCollection
    List<String> stacks;






    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
