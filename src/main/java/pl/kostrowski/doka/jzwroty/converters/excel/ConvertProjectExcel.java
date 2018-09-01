package pl.kostrowski.doka.jzwroty.converters.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzwroty.mappings.MyMappings;
import pl.kostrowski.doka.jzwroty.model.excel.ProjectExcel;
import pl.kostrowski.doka.jzwroty.service.excel.ParseFromExcelHelper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static pl.kostrowski.doka.jzwroty.service.excel.ParseFromExcelHelper.parseStringFromCell;

@Service
public class ConvertProjectExcel {

    private MyMappings myMappings = MyMappings.getInstance();

    public List<ProjectExcel> convert(Workbook workbook, String worksheetName) {

        Sheet sheet = workbook.getSheet(worksheetName);
        Map<String, String> excelColumns = myMappings.getProjectExcelColumns();

        List<ProjectExcel> results = new LinkedList<>();
        Iterator<Row> rowIterator = sheet.rowIterator();

		Row currentRow = rowIterator.next();
        Map<String, Integer> columnNumbers = ParseFromExcelHelper.findColumnNumbers(excelColumns, currentRow);

        while (rowIterator.hasNext()) {
			currentRow = rowIterator.next();
            ProjectExcel result = new ProjectExcel();

			String projectLeadingBranchTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("projectLeadingBranch")));
			result.setProjectLeadingBranch(projectLeadingBranchTmp);

			String rentalProjectNumberTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("rentalProjectNumber")));
			result.setRentalProjectNumber(rentalProjectNumberTmp);

			String rentalProjectNameTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("rentalProjectName")));
			result.setRentalProjectName(rentalProjectNameTmp);

			String customerNumberTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("customerNumber")));
			result.setCustomerNumber(customerNumberTmp);

			String customerNameTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("customerName")));
			result.setCustomerName(customerNameTmp);

			String salesManCommissionDivisionTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("salesManCommissionDivision")));
			result.setSalesManCommissionDivision(salesManCommissionDivisionTmp);

			String salesManTurnoverDivisionTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("salesManTurnoverDivision")));
			result.setSalesManTurnoverDivision(salesManTurnoverDivisionTmp);


            results.add(result);
        }
            
        return results;
    }

}
