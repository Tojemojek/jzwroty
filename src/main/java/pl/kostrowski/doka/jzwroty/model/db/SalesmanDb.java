package pl.kostrowski.doka.jzwroty.model.db;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "salesman")
public class SalesmanDb {

    @Id
    private String salesmanCode;

    private String salesmanName;

    private String branchName;

}