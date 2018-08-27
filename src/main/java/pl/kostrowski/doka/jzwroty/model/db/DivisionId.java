package pl.kostrowski.doka.jzwroty.model.db;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;


@Data
@Embeddable
public class DivisionId implements Serializable {

    private String salesmanCode;

    private String rentalProjectNumber;

}
