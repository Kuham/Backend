package kookmin.kuham.portfolio.schema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "license")
public class License {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String licenseName;
    private String licenseOrganization;
    private String licenseDate;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
}
