package pl.kostrowski.doka.jzwroty.model.db;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "discos_data")
public class DiscosData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "project_number")
    private ProjectDb projectDb;

    private String jobSiteNumber;

    private String jobSiteName;

    private String customerNumber;

    private String customerName;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "main_product_group_code")
    private MainProductGroupDb mainProductGroupDb;

    private String itemNumber;

    private String itemName;

    private String businessType;

    private Double quantity;

    private Double materialValuePerUnit;

    private Double totalWeight;

    private LocalDate plannedReturnDate;

    private LocalDate currentPlannedReturnDate;

    @Override
    public String toString() {
        return "DiscosData{" +
                "id=" + id +
                ", jobSiteNumber='" + jobSiteNumber + '\'' +
                ", jobSiteName='" + jobSiteName + '\'' +
                ", customerNumber='" + customerNumber + '\'' +
                ", customerName='" + customerName + '\'' +
                ", mainProductGroupDb=" + mainProductGroupDb +
                ", itemNumber='" + itemNumber + '\'' +
                ", itemName='" + itemName + '\'' +
                ", businessType='" + businessType + '\'' +
                ", quantity=" + quantity +
                ", materialValuePerUnit=" + materialValuePerUnit +
                ", totalWeight=" + totalWeight +
                ", plannedReturnDate=" + plannedReturnDate +
                ", currentPlannedReturnDate=" + currentPlannedReturnDate +
                '}';
    }
}