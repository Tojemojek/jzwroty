package pl.kostrowski.doka.jzwroty.dto;


import lombok.Data;

@Data
public class ProjectLinesDto {

    String customerNumber;
    String customerName;
    String projectNumber;
    String projectName;
    String siteNumber;
    String siteName;
    String mainProductGroup;
    String mainProductGroupName;
    Double materialValue;
    Double totalWeight;
    Double commissionPercentage;
    Double turnoverPercentage;

}
