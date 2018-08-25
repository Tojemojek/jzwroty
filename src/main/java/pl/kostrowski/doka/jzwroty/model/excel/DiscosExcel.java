package pl.kostrowski.doka.jzwroty.model.excel;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DiscosExcel {
	//This class is AutoGenerated from csv definition	private JavaType FieldNameInClass;

	private String rentalProjectNumber;

	private String rentalProjectName;

	private String jobSiteNumber;

	private String jobSiteName;

	private String projectLeadingBranch;

	private String customerNumber;

	private String customerName;

	private String mainProductGroupCode;

	private String itemNumber;

	private String itemName;

	private String businessType;

	private Double quantity;

	private Double materialValuePerUnit;

	private Double totalWeight;

	private LocalDate plannedReturnDate;

	private LocalDate currentPlannedReturnDate;

}