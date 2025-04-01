package kookmin.kuham.portfolio.schema;

import jakarta.persistence.*;
import kookmin.kuham.user.schema.User;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "portfolio")
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String profileUrl;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> stacks;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> links;
    private String introduce;

    @OneToOne(mappedBy = "portfolio")
    private User user;
}
