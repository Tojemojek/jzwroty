package pl.kostrowski.doka.jzwroty.converters.excel;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kostrowski.doka.jzwroty.mappings.MyMappings;
import pl.kostrowski.doka.jzwroty.model.excel.SalesmanExcel;
import pl.kostrowski.doka.jzwroty.service.excel.ParseFromExcelHelper;

import java.io.File;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConvertSalesmanExcelTest {


    @Autowired
    ConvertSalesmanExcel convertSalesmanExcel;

    @Test
    public void printSalesmanExcel() throws Exception {

        MyMappings instance = MyMappings.getInstance();
        Map<String, String> salesmanExcelColumns = instance.getSalesmanExcelColumns();

        Workbook workbook = new XSSFWorkbook(new File("./pliki/01_salesman.xlsx"));
        Sheet dane = workbook.getSheet("Dane");

        Map<String, Integer> columnNumbers = ParseFromExcelHelper.findColumnNumbers(salesmanExcelColumns, dane.getRow(0));

        List<SalesmanExcel> convert = convertSalesmanExcel.convert(columnNumbers, dane);

        for (SalesmanExcel salesmanExcel : convert) {
            System.out.println(salesmanExcel);
        }


    }

}