package pl.kostrowski.doka.jzwroty.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kostrowski.doka.jzwroty.model.db.SalesmanDb;

public interface SalesmanDao extends JpaRepository<SalesmanDb, String> {

}
