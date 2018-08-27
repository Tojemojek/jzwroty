package pl.kostrowski.doka.jzwroty.converters.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kostrowski.doka.jzwroty.model.excel.DiscosExcel;

import java.io.File;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConvertDiscosExcelTest {


    @Autowired
    ConvertDiscosExcel convertDiscosExcel;

    @Test
    public void printDiscosResults() throws Exception {

        Workbook workbook = new XSSFWorkbook(new File("./pliki/04_input_material_on_site.xlsx"));

        List<DiscosExcel> convert = convertDiscosExcel.convert(workbook, "Dane");
        for (DiscosExcel discosExcel : convert) {
            System.out.println(discosExcel);
        }
    }
}