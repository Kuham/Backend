package kookmin.kuham.portfolio.schema;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "activity")
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String oneLineDescription;
    private String startDate;
    private String endDate;
    private String description;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    Portfolio portfolio;

    @ElementCollection(fetch = FetchType.LAZY)
    List<String> images;

    @ElementCollection(fetch = FetchType.LAZY)
    List<String> roles;

    private boolean inProgress;


}
