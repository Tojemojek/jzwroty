package pl.kostrowski.doka.jzwroty.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kostrowski.doka.jzwroty.model.db.MainProductGroupDb;

public interface MainProductGroupDao extends JpaRepository<MainProductGroupDb, String> {

}
