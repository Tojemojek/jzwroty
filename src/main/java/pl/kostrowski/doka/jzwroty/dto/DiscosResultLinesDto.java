package pl.kostrowski.doka.jzwroty.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

@Data
public class DiscosResultLinesDto implements Comparable<DiscosResultLinesDto> {


    private String salesmanCode;
    private String fileName;
    private String tabName;
    private String comments;
    private String projectNumber;
    private String jobsite;
    private String mpgNumber;
    private LocalDate returnDate;
    private Double salesmanTurnoverPercentage;
    private Double salesmanCommissionPercentage;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscosResultLinesDto that = (DiscosResultLinesDto) o;
        return Objects.equals(projectNumber, that.projectNumber) &&
                Objects.equals(jobsite, that.jobsite) &&
                Objects.equals(mpgNumber, that.mpgNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectNumber, jobsite, mpgNumber);
    }


    @Override
    public int compareTo(DiscosResultLinesDto o) {

        if (projectNumber.compareTo(o.getProjectNumber()) != 0) {
            return projectNumber.compareTo(o.getProjectNumber());
        }

        if (jobsite.compareTo(o.getJobsite()) != 0) {
            return jobsite.compareTo(o.getJobsite());
        }

        if (mpgNumber.compareTo(o.getMpgNumber()) != 0) {
            return mpgNumber.compareTo(o.getMpgNumber());
        }
        if (salesmanCommissionPercentage.compareTo(o.getSalesmanCommissionPercentage()) != 0) {
            return salesmanCommissionPercentage.compareTo(o.getSalesmanCommissionPercentage());
        }
        if (salesmanTurnoverPercentage.compareTo(o.getSalesmanTurnoverPercentage()) != 0) {
            return salesmanTurnoverPercentage.compareTo(o.getSalesmanTurnoverPercentage());
        }
        if (salesmanCode.compareTo(o.getSalesmanCode()) != 0) {
            return salesmanCode.compareTo(o.getSalesmanCode());
        }
        return 0;
    }
}
