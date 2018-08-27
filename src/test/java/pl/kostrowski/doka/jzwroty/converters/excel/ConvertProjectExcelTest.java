package pl.kostrowski.doka.jzwroty.converters.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kostrowski.doka.jzwroty.model.excel.ProjectExcel;

import java.io.File;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConvertProjectExcelTest {

    @Autowired
    ConvertProjectExcel convertProjectExcel;

    @Test
    public void printProjectsExcel() throws Exception {

        Workbook workbook = new XSSFWorkbook(new File("./pliki/03_input_projects.xlsx"));

        List<ProjectExcel> convert = convertProjectExcel.convert(workbook, "Dane");

        for (ProjectExcel projectExcel : convert) {
            System.out.println(projectExcel);
        }


    }

}