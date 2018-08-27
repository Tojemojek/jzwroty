package pl.kostrowski.doka.jzwroty.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kostrowski.doka.jzwroty.model.db.ProjectDb;

public interface ProjectDao extends JpaRepository<ProjectDb, String> {

}
