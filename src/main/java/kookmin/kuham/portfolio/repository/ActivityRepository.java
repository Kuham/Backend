package kookmin.kuham.portfolio.repository;

import kookmin.kuham.portfolio.schema.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity,Long> {
}
