package pl.kostrowski.doka.jzwroty.model.db;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "main_product_group")
public class MainProductGroupDb {

    @Id
    private String mainProductGroupCode;

    private String mainProductGroupText;

}