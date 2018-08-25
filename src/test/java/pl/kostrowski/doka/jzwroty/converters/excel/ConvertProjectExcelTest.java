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
import pl.kostrowski.doka.jzwroty.model.excel.MainProductGroupExcel;
import pl.kostrowski.doka.jzwroty.model.excel.ProjectExcel;
import pl.kostrowski.doka.jzwroty.service.excel.ParseFromExcelHelper;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConvertProjectExcelTest {

    @Autowired
    ConvertProjectExcel convertProjectExcel;

    @Test
    public void printProjectsExcel() throws Exception {

        MyMappings instance = MyMappings.getInstance();
        Map<String, String> projectExcelColumns = instance.getProjectExcelColumns();

        Workbook workbook = new XSSFWorkbook(new File("./pliki/03_input_projects.xlsx"));
        Sheet dane = workbook.getSheet("Dane");

        Map<String, Integer> columnNumbers = ParseFromExcelHelper.findColumnNumbers(projectExcelColumns, dane.getRow(0));

        List<ProjectExcel> convert = convertProjectExcel.convert(columnNumbers, dane);

        for (ProjectExcel projectExcel : convert) {
            System.out.println(projectExcel);
        }


    }

}