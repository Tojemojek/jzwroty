package pl.kostrowski.doka.jzwroty.converters.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzwroty.model.excel.MainProductGroupExcel;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static pl.kostrowski.doka.jzwroty.service.excel.ParseFromExcelHelper.*;

@Service
public class ConvertMainProductGroupExcel {

    public List<MainProductGroupExcel> convert(Map<String, Integer> columnNumbers, Sheet sheet){

        int lastRowNum = sheet.getLastRowNum();
        List<MainProductGroupExcel> results = new LinkedList<>();
        Iterator<Row> rowIterator = sheet.rowIterator();
        //aby uniknąć konwertowania wiersza nagłówka
		Row currentRow = rowIterator.next();

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
