package pl.kostrowski.doka.jzwroty.service.excel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kostrowski.doka.jzwroty.service.persist.SaveAllExcelDataToDb;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CreatorTest {

    @Autowired
    SaveAllExcelDataToDb saveAllExcelDataToDb;

    @Autowired
    Creator creator;

    @Test
    public void newFileCreationTest() {
        saveAllExcelDataToDb.convertExcelToDb();
        creator.createResultFile();
    }

}