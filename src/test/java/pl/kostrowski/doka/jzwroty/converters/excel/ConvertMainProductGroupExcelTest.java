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
import pl.kostrowski.doka.jzwroty.model.excel.MainProductGroupExcel;
import pl.kostrowski.doka.jzwroty.service.excel.ParseFromExcelHelper;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ConvertMainProductGroupExcelTest {

    @Autowired
    ConvertMainProductGroupExcel mainProductGroupExcel;

    @Test
    public void printDiscosResults() throws Exception {

        MyMappings instance = MyMappings.getInstance();
        Map<String, String> mainProductGroupExcelColumns = instance.getMainProductGroupExcelColumns();

        Workbook workbook = new XSSFWorkbook(new File("./pliki/02_main_product_group.xlsx"));
        Sheet dane = workbook.getSheet("Dane");

        Map<String, Integer> columnNumbers = ParseFromExcelHelper.findColumnNumbers(mainProductGroupExcelColumns, dane.getRow(0));

        List<MainProductGroupExcel> convert = mainProductGroupExcel.convert(columnNumbers, dane);

        for (MainProductGroupExcel productGroupExcel : convert) {
            System.out.println(productGroupExcel);
        }


    }

}