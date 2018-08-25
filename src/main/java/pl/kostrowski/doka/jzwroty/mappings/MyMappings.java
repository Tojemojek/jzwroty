package pl.kostrowski.doka.jzwroty.mappings;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class MyMappings {
	//This class is AutoGenerated from csv definition
	private static MyMappings instance = new MyMappings();

	public static MyMappings getInstance() {
		return instance;
	}	private Map<String, String> salesmanExcelColumns = new HashMap<>();
	private Map<String, String> invertedSalesmanExcelColumns;
	private Map<String, String> projectExcelColumns = new HashMap<>();
	private Map<String, String> invertedProjectExcelColumns;
	private Map<String, String> mainProductGroupExcelColumns = new HashMap<>();
	private Map<String, String> invertedMainProductGroupExcelColumns;
	private Map<String, String> discosExcelColumns = new HashMap<>();
	private Map<String, String> invertedDiscosExcelColumns;


	{
	salesmanExcelColumns.put("ColumnNameInFile","FieldNameInClass");
	salesmanExcelColumns.put("SalesmanCode","salesmanCode");
	salesmanExcelColumns.put("SalesmanName","SalesmanName");

	invertedSalesmanExcelColumns = salesmanExcelColumns.entrySet()
		.stream()
		.collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

	projectExcelColumns.put("ColumnNameInFile","FieldNameInClass");
	projectExcelColumns.put("Project leading Branch","projectLeadingBranch");
	projectExcelColumns.put("Rental Project","rentalProjectNumber");
	projectExcelColumns.put("Description","rentalProjectName");
	projectExcelColumns.put("Job Site","jobSiteNumber");
	projectExcelColumns.put("Customer account","customerNumber");
	projectExcelColumns.put("Name","customerName");
	projectExcelColumns.put("Sales man - Commission","salesManCommissionDivision");
	projectExcelColumns.put("Sales man - Turnover","salesManTurnoverDivision");

	invertedProjectExcelColumns = projectExcelColumns.entrySet()
		.stream()
		.collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

	mainProductGroupExcelColumns.put("ColumnNameInFile","FieldNameInClass");
	mainProductGroupExcelColumns.put("Main Productgroup","mainProductGroupCode");
	mainProductGroupExcelColumns.put("Text","mainProductGroupText");

	invertedMainProductGroupExcelColumns = mainProductGroupExcelColumns.entrySet()
		.stream()
		.collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

	discosExcelColumns.put("ColumnNameInFile","FieldNameInClass");
	discosExcelColumns.put("Rental Project","rentalProjectNumber");
	discosExcelColumns.put("Description","rentalProjectName");
	discosExcelColumns.put("Job Site","jobSiteNumber");
	discosExcelColumns.put("Description2","jobSiteName");
	discosExcelColumns.put("Project leading Branch","projectLeadingBranch");
	discosExcelColumns.put("Customer account","customerNumber");
	discosExcelColumns.put("Name","customerName");
	discosExcelColumns.put("Main Productgroup","mainProductGroupCode");
	discosExcelColumns.put("Item No.","itemNumber");
	discosExcelColumns.put("Text","itemName");
	discosExcelColumns.put("Type of Business (Rental/Sale)","businessType");
	discosExcelColumns.put("Quantity","quantity");
	discosExcelColumns.put("material value per unit","materialValuePerUnit");
	discosExcelColumns.put("Total weight","totalWeight");
	discosExcelColumns.put("Planned return date","plannedReturnDate");
	discosExcelColumns.put("Current planned return date","currentPlannedReturnDate");

	invertedDiscosExcelColumns = discosExcelColumns.entrySet()
		.stream()
		.collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

	}
}