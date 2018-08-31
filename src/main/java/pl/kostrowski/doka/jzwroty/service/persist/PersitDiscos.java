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
import pl.kostrowski.doka.jzwroty.exceptions.DataProblemException;
import pl.kostrowski.doka.jzwroty.model.db.DiscosData;
import pl.kostrowski.doka.jzwroty.model.excel.DiscosExcel;

import java.io.File;
import java.util.List;

import static pl.kostrowski.doka.jzwroty.koconfig.ReadExternalProperties.*;

@Service
public class PersitDiscos {

    private final static Logger LOG = LoggerFactory.getLogger(PersitDiscos.class);

    private ConvertDiscos convertDiscos;
    private ConvertDiscosExcel convertDiscosExcel;
    private DiscosDao discosDao;

    @Autowired
    public PersitDiscos(ConvertDiscos convertDiscos, ConvertDiscosExcel convertDiscosExcel, DiscosDao discosDao) {
        this.convertDiscos = convertDiscos;
        this.convertDiscosExcel = convertDiscosExcel;
        this.discosDao = discosDao;
    }

    public void persist(String folderName) {
        try {
            String path = "." + File.separator + getFileSaveFolder() + File.separator + folderName + File.separator + getDiscosDataFileName();
            File inputFile = new File(path);
            LOG.info("Przetwarzam plik " + inputFile.getName());
            Workbook projectWorkbook = new XSSFWorkbook(inputFile);
            List<DiscosExcel> discosExcels = convertDiscosExcel.convert(projectWorkbook, getDiscosDataSheetName());
            List<DiscosData> convert = convertDiscos.convert(discosExcels);
            LOG.info("Znaleziono " + convert.size() + " unikalnych linii z danymi");
            discosDao.saveAll(convert);
            discosDao.flush();
            projectWorkbook.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataProblemException("Jest problem z danymi w pliku z danymi Handlowców");
            //Todo implement if workbooks cant be opened
        }
    }


}
