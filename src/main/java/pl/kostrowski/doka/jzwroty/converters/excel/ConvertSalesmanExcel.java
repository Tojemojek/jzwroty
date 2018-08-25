package pl.kostrowski.doka.jzwroty.converters.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzwroty.model.excel.SalesmanExcel;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static pl.kostrowski.doka.jzwroty.service.excel.ParseFromExcelHelper.*;

@Service
public class ConvertSalesmanExcel {

    public List<SalesmanExcel> convert(Map<String, Integer> columnNumbers, Sheet sheet){

        int lastRowNum = sheet.getLastRowNum();
        List<SalesmanExcel> results = new LinkedList<>();
        Iterator<Row> rowIterator = sheet.rowIterator();
        //aby uniknąć konwertowania wiersza nagłówka
		Row currentRow = rowIterator.next();

        while (rowIterator.hasNext()) {
			currentRow = rowIterator.next();
            SalesmanExcel result = new SalesmanExcel();

			String salesmanCodeTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("salesmanCode")));
			result.setSalesmanCode(salesmanCodeTmp);

			String SalesmanNameTmp = parseStringFromCell(currentRow.getCell(columnNumbers.get("SalesmanName")));
			result.setSalesmanName(SalesmanNameTmp);


            results.add(result);
        }
            
        return results;
    }

}
