package pl.kostrowski.doka.jzwroty.service.persist;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzwroty.converters.excel.ConvertDiscosExcel;
import pl.kostrowski.doka.jzwroty.converters.exceldb.ConvertDiscos;
import pl.kostrowski.doka.jzwroty.dao.DiscosDao;
import pl.kostrowski.doka.jzwroty.model.db.DiscosData;
import pl.kostrowski.doka.jzwroty.model.excel.DiscosExcel;

import java.io.File;
import java.util.List;

@Service
public class PersitDiscos {

    private final static Logger LOG = LoggerFactory.getLogger(PersitDiscos.class);

    private ConvertDiscos convertDiscos;
    private ConvertDiscosExcel convertDiscosExcel;
    private DiscosDao discosDao;

    final String WORKSHEET_WITH_DATA_NAME = "Dane";

    @Autowired
    public PersitDiscos(ConvertDiscos convertDiscos, ConvertDiscosExcel convertDiscosExcel, DiscosDao discosDao) {
        this.convertDiscos = convertDiscos;
        this.convertDiscosExcel = convertDiscosExcel;
        this.discosDao = discosDao;
    }

    public void persist() {
        try {
            File inputFile = new File("./pliki/04_input_material_on_site.xlsx");
            LOG.info("Przetwarzam plik " + inputFile.getName());
            Workbook projectWorkbook = new XSSFWorkbook(inputFile);
            List<DiscosExcel> discosExcels = convertDiscosExcel.convert(projectWorkbook, WORKSHEET_WITH_DATA_NAME);
            List<DiscosData> convert = convertDiscos.convert(discosExcels);
            LOG.info("Znaleziono " + convert.size() + " unikalnych linii z danymi");
            discosDao.saveAll(convert);
            discosDao.flush();
            projectWorkbook.close();
        } catch (Exception e) {
            e.printStackTrace();
            //Todo implement if workbooks cant be opened
        }
    }


}
