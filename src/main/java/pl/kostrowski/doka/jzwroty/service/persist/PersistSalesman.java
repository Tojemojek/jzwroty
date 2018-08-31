package pl.kostrowski.doka.jzwroty.service.persist;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzwroty.converters.excel.ConvertSalesmanExcel;
import pl.kostrowski.doka.jzwroty.converters.exceldb.ConvertSalesman;
import pl.kostrowski.doka.jzwroty.dao.SalesmanDao;
import pl.kostrowski.doka.jzwroty.model.db.SalesmanDb;
import pl.kostrowski.doka.jzwroty.model.excel.SalesmanExcel;

import java.io.File;
import java.util.List;

@Service
public class PersistSalesman {

    private final static Logger LOG = LoggerFactory.getLogger(PersistSalesman.class);
    private final String WORKSHEET_WITH_DATA_NAME = "Dane";
    private ConvertSalesmanExcel convertSalesmanExcel;
    private ConvertSalesman convertSalesman;
    private SalesmanDao salesmanDao;

    @Autowired
    public PersistSalesman(ConvertSalesmanExcel convertSalesmanExcel, ConvertSalesman convertSalesman, SalesmanDao salesmanDao) {
        this.convertSalesmanExcel = convertSalesmanExcel;
        this.convertSalesman = convertSalesman;
        this.salesmanDao = salesmanDao;
    }

    public void persist() {
        try {
            File inputFile = new File("./pliki/01_salesman.xlsx");
            LOG.info("Przetwarzam plik " + inputFile.getName());
            Workbook salesmanWorkbook = new XSSFWorkbook(inputFile);
            List<SalesmanExcel> salesmanExcel = convertSalesmanExcel.convert(salesmanWorkbook, WORKSHEET_WITH_DATA_NAME);
            List<SalesmanDb> salesmanDb = convertSalesman.convert(salesmanExcel);
            SalesmanDb fallbackSalesman = new SalesmanDb();
            fallbackSalesman.setSalesmanCode("PL-0");
            fallbackSalesman.setSalesmanName("NN");
            salesmanDb.add(fallbackSalesman);
            LOG.info("Znaleziono " + salesmanDb.size() + " unikalnych handlowców");
            salesmanDao.saveAll(salesmanDb);
            salesmanDao.flush();
            salesmanWorkbook.close();
        } catch (Exception e) {
            //Todo implement if workbooks cant be opened
        }
    }


}
