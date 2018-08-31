package pl.kostrowski.doka.jzwroty.service.persist;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzwroty.converters.excel.ConvertMainProductGroupExcel;
import pl.kostrowski.doka.jzwroty.converters.exceldb.ConvertMainProductGroup;
import pl.kostrowski.doka.jzwroty.dao.MainProductGroupDao;
import pl.kostrowski.doka.jzwroty.model.db.MainProductGroupDb;
import pl.kostrowski.doka.jzwroty.model.excel.MainProductGroupExcel;

import java.io.File;
import java.util.List;

@Service
public class PersistMainProductGroup {

    private final static Logger LOG = LoggerFactory.getLogger(PersistMainProductGroup.class);
    private final String WORKSHEET_WITH_DATA_NAME = "Dane";
    private ConvertMainProductGroupExcel convertMainProductGroupExcel;
    private ConvertMainProductGroup convertMainProductGroup;
    private MainProductGroupDao mainProductGroupDao;

    @Autowired
    public PersistMainProductGroup(ConvertMainProductGroupExcel convertMainProductGroupExcel, ConvertMainProductGroup convertMainProductGroup, MainProductGroupDao mainProductGroupDao) {
        this.convertMainProductGroupExcel = convertMainProductGroupExcel;
        this.convertMainProductGroup = convertMainProductGroup;
        this.mainProductGroupDao = mainProductGroupDao;
    }


    public void persist() {
        try {
            File inputFile = new File("./pliki/02_main_product_group.xlsx");
            LOG.info("Przetwarzam plik " + inputFile.getName());
            Workbook projectWorkbook = new XSSFWorkbook(inputFile);
            List<MainProductGroupExcel> mainProductGroupExcels = convertMainProductGroupExcel.convert(projectWorkbook, WORKSHEET_WITH_DATA_NAME);
            List<MainProductGroupDb> convert = convertMainProductGroup.convert(mainProductGroupExcels);
            LOG.info("Znaleziono " + convert.size() + " grup produktowych");
            mainProductGroupDao.saveAll(convert);
            mainProductGroupDao.flush();
            projectWorkbook.close();
        } catch (Exception e) {
            e.printStackTrace();
            //Todo implement if workbooks cant be opened
        }
    }


}
