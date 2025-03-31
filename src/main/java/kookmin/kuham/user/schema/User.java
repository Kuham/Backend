package kookmin.kuham.user.schema;


import jakarta.persistence.*;
import kookmin.kuham.portfolio.schema.Portfolio;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "유저")
public class User {
    @Id
    private String id;
    private String password;
    private String name;
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

}
