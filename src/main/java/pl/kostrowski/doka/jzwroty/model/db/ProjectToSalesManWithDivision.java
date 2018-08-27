package pl.kostrowski.doka.jzwroty.model.db;


import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "project_to_salesman")
public class ProjectToSalesManWithDivision implements Serializable {


    @EmbeddedId
    private DivisionId divisionId;

    private Double commisionPercentage;

    private Double turnoverPercentage;


}
