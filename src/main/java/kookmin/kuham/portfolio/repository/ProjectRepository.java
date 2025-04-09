package kookmin.kuham.portfolio.repository;

import kookmin.kuham.portfolio.schema.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long> {
}
