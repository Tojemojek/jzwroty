package pl.kostrowski.doka.jzwroty.service.excel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kostrowski.doka.jzwroty.service.persist.SaveAllExcelDataToDb;


@RunWith(SpringRunner.class)
@SpringBootTest
public class WynikiCreatorTest {

    @Autowired
    SaveAllExcelDataToDb saveAllExcelDataToDb;

    @Autowired
    WynikiCreator wynikiCreator;

    @Test(expected = Test.None.class)
    public void newFileCreationTest() {
        saveAllExcelDataToDb.convertExcelToDb("20180831");
        wynikiCreator.createResultFile("20180831");
    }

}