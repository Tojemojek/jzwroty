package pl.kostrowski.doka.jzwroty.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.kostrowski.doka.jzwroty.model.db.DivisionId;
import pl.kostrowski.doka.jzwroty.model.db.ProjectToSalesManWithDivision;

public interface ProjectToSalesManWithDivisionDao extends JpaRepository<ProjectToSalesManWithDivision, DivisionId> {

}
