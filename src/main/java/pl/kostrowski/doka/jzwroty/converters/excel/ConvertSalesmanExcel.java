package pl.kostrowski.doka.jzwroty.converters.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzwroty.mappings.MyMappings;
import pl.kostrowski.doka.jzwroty.model.excel.SalesmanExcel;
import pl.kostrowski.doka.jzwroty.service.excel.ParseFromExcelHelper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static pl.kostrowski.doka.jzwroty.service.excel.ParseFromExcelHelper.parseStringFromCell;

@Service
public class ConvertSalesmanExcel {

    private MyMappings myMappings = MyMappings.getInstance();

    public List<SalesmanExcel> convert(Workbook workbook, String worksheetName) {

        Sheet sheet = workbook.getSheet(worksheetName);
        Map<String, String> excelColumns = myMappings.getSalesmanExcelColumns();

        List<SalesmanExcel> results = new LinkedList<>();
        Iterator<Row> rowIterator = sheet.rowIterator();

        Row currentRow = rowIterator.next();
        Map<String, Integer> columnNumbers = ParseFromExcelHelper.findColumnNumbers(excelColumns, currentRow);

        while (rowIterator.hasNext()) {
            currentRow = rowIterator.next();
            SalesmanExcel result = new SalesmanExcel();

            String salesmanCodeTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("salesmanCode")));
            result.setSalesmanCode(salesmanCodeTmp);

            String salesmanNameTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("salesmanName")));
            result.setSalesmanName(salesmanNameTmp);

            String salesmanBranch = parseStringFromCell(currentRow.getCell(columnNumbers.get("salesmanBranch")));
            result.setBranchName(salesmanBranch);

            if (salesmanCodeTmp == null
                    || salesmanNameTmp == null
                    || salesmanBranch == null
                    || StringUtils.isBlank(salesmanCodeTmp)
                    || StringUtils.isBlank(salesmanNameTmp)
                    || StringUtils.isBlank(salesmanBranch)) {
                continue;
            }

            results.add(result);
        }

        return results;
    }

}
