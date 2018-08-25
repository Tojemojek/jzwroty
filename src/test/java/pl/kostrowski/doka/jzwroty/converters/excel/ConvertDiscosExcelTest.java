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
import pl.kostrowski.doka.jzwroty.model.excel.DiscosExcel;
import pl.kostrowski.doka.jzwroty.service.excel.ParseFromExcelHelper;

import java.io.File;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConvertDiscosExcelTest {


    @Autowired
    ConvertDiscosExcel convertDiscosExcel;

    @Test
    public void printDiscosResults() throws Exception {

        MyMappings instance = MyMappings.getInstance();
        Map<String, String> discosExcelColumns = instance.getDiscosExcelColumns();

        Workbook workbook = new XSSFWorkbook(new File("./pliki/04_input_material_on_site.xlsx"));
        Sheet dane = workbook.getSheet("Dane");

        Map<String, Integer> columnNumbers = ParseFromExcelHelper.findColumnNumbers(discosExcelColumns, dane.getRow(0));

        List<DiscosExcel> convert = convertDiscosExcel.convert(columnNumbers, dane);
        for (DiscosExcel discosExcel : convert) {
            System.out.println(discosExcel);
        }


    }


}