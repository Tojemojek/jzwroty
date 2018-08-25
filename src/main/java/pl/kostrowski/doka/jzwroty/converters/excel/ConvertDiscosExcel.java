package pl.kostrowski.doka.jzwroty.converters.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzwroty.model.excel.DiscosExcel;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static pl.kostrowski.doka.jzwroty.service.excel.ParseFromExcelHelper.*;

@Service
public class ConvertDiscosExcel {

    public List<DiscosExcel> convert(Map<String, Integer> columnNumbers, Sheet sheet){

        int lastRowNum = sheet.getLastRowNum();
        List<DiscosExcel> results = new LinkedList<>();
        Iterator<Row> rowIterator = sheet.rowIterator();
        //aby uniknąć konwertowania wiersza nagłówka
		Row currentRow = rowIterator.next();

        while (rowIterator.hasNext()) {
			currentRow = rowIterator.next();
            DiscosExcel result = new DiscosExcel();

			String rentalProjectNumberTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("rentalProjectNumber")));
			result.setRentalProjectNumber(rentalProjectNumberTmp);

			String rentalProjectNameTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("rentalProjectName")));
			result.setRentalProjectName(rentalProjectNameTmp);

			String jobSiteNumberTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("jobSiteNumber")));
			result.setJobSiteNumber(jobSiteNumberTmp);

			String jobSiteNameTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("jobSiteName")));
			result.setJobSiteName(jobSiteNameTmp);

			String projectLeadingBranchTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("projectLeadingBranch")));
			result.setProjectLeadingBranch(projectLeadingBranchTmp);

			String customerNumberTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("customerNumber")));
			result.setCustomerNumber(customerNumberTmp);

			String customerNameTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("customerName")));
			result.setCustomerName(customerNameTmp);

			String mainProductGroupCodeTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("mainProductGroupCode")));
			result.setMainProductGroupCode(mainProductGroupCodeTmp);

			String itemNumberTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("itemNumber")));
			result.setItemNumber(itemNumberTmp);

			String itemNameTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("itemName")));
			result.setItemName(itemNameTmp);

			String businessTypeTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("businessType")));
			result.setBusinessType(businessTypeTmp);

			Double quantityTmp = parseDoubleFromCell(currentRow.getCell(columnNumbers.get("quantity")));
			result.setQuantity(quantityTmp);

			Double materialValuePerUnitTmp = parseDoubleFromCell(currentRow.getCell(columnNumbers.get("materialValuePerUnit")));
			result.setMaterialValuePerUnit(materialValuePerUnitTmp);

			Double totalWeightTmp = parseDoubleFromCell(currentRow.getCell(columnNumbers.get("totalWeight")));
			result.setTotalWeight(totalWeightTmp);

			LocalDate plannedReturnDateTmp = parseLocalDateFromCell(currentRow.getCell(columnNumbers.get("plannedReturnDate")));
			result.setPlannedReturnDate(plannedReturnDateTmp);

			LocalDate currentPlannedReturnDateTmp = parseLocalDateFromCell(currentRow.getCell(columnNumbers.get("currentPlannedReturnDate")));
			result.setCurrentPlannedReturnDate(currentPlannedReturnDateTmp);


            results.add(result);
        }
            
        return results;
    }

}
