package pl.kostrowski.doka.jzwroty.converters.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kostrowski.doka.jzwroty.model.excel.SalesmanExcel;

import java.io.File;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConvertSalesmanExcelTest {


    @Autowired
    ConvertSalesmanExcel convertSalesmanExcel;

    @Test
    public void printSalesmanExcel() throws Exception {

        Workbook workbook = new XSSFWorkbook(new File("./pliki/01_salesman.xlsx"));

        List<SalesmanExcel> convert = convertSalesmanExcel.convert(workbook, "Dane");

        for (SalesmanExcel salesmanExcel : convert) {
            System.out.println(salesmanExcel);
        }
    }

}