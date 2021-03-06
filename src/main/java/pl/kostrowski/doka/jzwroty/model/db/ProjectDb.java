package pl.kostrowski.doka.jzwroty.model.db;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "project")
public class ProjectDb {

    @Id
    private String rentalProjectNumber;

    private String rentalProjectName;

    private String customerNumber;

    private String customerName;

    private String projectLeadingBranch;

    @OneToMany(mappedBy = "projectDb", fetch = FetchType.EAGER)
    private List<DiscosData> discosData;

    @Override
    public String toString() {
        return "ProjectDb{" +
                "rentalProjectNumber='" + rentalProjectNumber + '\'' +
                ", rentalProjectName='" + rentalProjectName + '\'' +
                ", customerNumber='" + customerNumber + '\'' +
                ", customerName='" + customerName + '\'' +
                ", projectLeadingBranch='" + projectLeadingBranch + '\'' +
                '}';
    }
}