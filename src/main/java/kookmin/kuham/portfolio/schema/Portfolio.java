package kookmin.kuham.portfolio.schema;

import jakarta.persistence.*;
import kookmin.kuham.user.schema.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = " portfolio")
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> stacks;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> links;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> characters;
    private String introduce;

    @OneToOne(mappedBy = "portfolio")
    private User user;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL,orphanRemoval = true)
    List<Project> projects = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL,orphanRemoval = true)
    List<License> licenses = new ArrayList<>();
}
