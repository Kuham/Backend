package kookmin.kuham.portfolio.schema;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import kookmin.kuham.user.schema.User;

@Entity
public class Portfolio {
    @Id
    private long id;
    private String name;
    private String email;
    private String job;
    private String personality;

    @OneToOne(mappedBy = "portfolio")
    private User user;
}
