package kookmin.kuham.portfolio.repository;

import kookmin.kuham.portfolio.schema.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
