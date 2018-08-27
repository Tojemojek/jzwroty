package pl.kostrowski.doka.jzwroty.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kostrowski.doka.jzwroty.model.db.DiscosData;

public interface DiscosDao extends JpaRepository<DiscosData, Long> {

}
