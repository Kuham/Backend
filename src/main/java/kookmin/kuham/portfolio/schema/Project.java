package kookmin.kuham.portfolio.schema;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String oneLineDescription;

    @ElementCollection(fetch = FetchType.LAZY)
    List<String> stacks;

    private String description;
    private String startDate;
    private String endDate;
    private boolean inProgress;

    @ElementCollection(fetch = FetchType.LAZY)
    List<String> images;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    Portfolio portfolio;
}
