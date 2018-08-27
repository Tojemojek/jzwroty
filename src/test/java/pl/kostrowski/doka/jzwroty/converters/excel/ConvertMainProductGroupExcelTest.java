package pl.kostrowski.doka.jzwroty.converters.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kostrowski.doka.jzwroty.model.excel.MainProductGroupExcel;

import java.io.File;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ConvertMainProductGroupExcelTest {

    @Autowired
    ConvertMainProductGroupExcel mainProductGroupExcel;

    @Test
    public void printDiscosResults() throws Exception {

        Workbook workbook = new XSSFWorkbook(new File("./pliki/02_main_product_group.xlsx"));

        List<MainProductGroupExcel> convert = mainProductGroupExcel.convert(workbook, "Dane");

        for (MainProductGroupExcel productGroupExcel : convert) {
            System.out.println(productGroupExcel);
        }


    }

}