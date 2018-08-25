package pl.kostrowski.doka.jzwroty.converters.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzwroty.model.excel.ProjectExcel;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static pl.kostrowski.doka.jzwroty.service.excel.ParseFromExcelHelper.*;

@Service
public class ConvertProjectExcel {

    public List<ProjectExcel> convert(Map<String, Integer> columnNumbers, Sheet sheet){

        int lastRowNum = sheet.getLastRowNum();
        List<ProjectExcel> results = new LinkedList<>();
        Iterator<Row> rowIterator = sheet.rowIterator();
        //aby uniknąć konwertowania wiersza nagłówka
		Row currentRow = rowIterator.next();

        while (rowIterator.hasNext()) {
			currentRow = rowIterator.next();
            ProjectExcel result = new ProjectExcel();

			String projectLeadingBranchTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("projectLeadingBranch")));
			result.setProjectLeadingBranch(projectLeadingBranchTmp);

			String rentalProjectNumberTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("rentalProjectNumber")));
			result.setRentalProjectNumber(rentalProjectNumberTmp);

			String rentalProjectNameTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("rentalProjectName")));
			result.setRentalProjectName(rentalProjectNameTmp);

			String jobSiteNumberTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("jobSiteNumber")));
			result.setJobSiteNumber(jobSiteNumberTmp);

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
