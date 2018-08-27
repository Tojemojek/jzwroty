package pl.kostrowski.doka.jzwroty.converters.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzwroty.mappings.MyMappings;
import pl.kostrowski.doka.jzwroty.model.excel.MainProductGroupExcel;
import pl.kostrowski.doka.jzwroty.service.excel.ParseFromExcelHelper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static pl.kostrowski.doka.jzwroty.service.excel.ParseFromExcelHelper.parseStringFromCell;

@Service
public class ConvertMainProductGroupExcel {

    private MyMappings myMappings = MyMappings.getInstance();

    public List<MainProductGroupExcel> convert(Workbook workbook, String worksheetName) {

        Sheet sheet = workbook.getSheet(worksheetName);
        Map<String, String> excelColumns = myMappings.getMainProductGroupExcelColumns();

        List<MainProductGroupExcel> results = new LinkedList<>();
        Iterator<Row> rowIterator = sheet.rowIterator();

		Row currentRow = rowIterator.next();
        Map<String, Integer> columnNumbers = ParseFromExcelHelper.findColumnNumbers(excelColumns, currentRow);

        while (rowIterator.hasNext()) {
			currentRow = rowIterator.next();
            MainProductGroupExcel result = new MainProductGroupExcel();

			String mainProductGroupCodeTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("mainProductGroupCode")));
			result.setMainProductGroupCode(mainProductGroupCodeTmp);

			String mainProductGroupTextTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("mainProductGroupText")));
			result.setMainProductGroupText(mainProductGroupTextTmp);


            results.add(result);
        }
            
        return results;
    }

}
